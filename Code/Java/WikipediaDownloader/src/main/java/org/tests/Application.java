package org.tests;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Application
{
	private static CloseableHttpClient client = createHttpClient();

	public static void main(String[] args) throws Exception
	{
		String randomUrl = client.execute(new HttpGet("https://en.wikipedia.org/wiki/Special:Random")).getFirstHeader("Location").getValue();
		String title = StringUtils.substringAfterLast(randomUrl,"/");
		generateEmlFileForTitle(title);
	}

	private static void generateEmlFileForTitle(String title) throws Exception
	{
		String url = "https://en.wikipedia.org/w/index.php?title=" + title + "&printable=yes";

		String htmlBody = streamToString(client.execute(new HttpGet(url)).getEntity().getContent());
		Document doc = Jsoup.parse(htmlBody);

		doc.select("#bodyContent > .mw-jump-link").remove();
		removeQuietly("#siteSub", doc);
		removeQuietly("#mw-navigation", doc);
		removeQuietly("#footer", doc);
		removeQuietly(".printfooter", doc);
		removeQuietly("#catlinks", doc);
		removeQuietly("#mw-hidden-catlinks", doc);

		Map<String, String> imageMap = new HashMap<>();

		for (Element img : doc.select("img"))
		{
			String src = img.attr("src");
			if (src.startsWith("//")) src = StringUtils.replaceOnce(src,"//","https://");
			if (src.startsWith("/")) src = StringUtils.replaceOnce(src,"/","https://en.wikipedia.org/");
			byte[] image = streamToBytes(client.execute(new HttpGet(src)).getEntity().getContent());
			String image64 = Base64.encodeBase64String(image);
			String imageTemplate = streamToString(Application.class.getClassLoader().getResource("template.img").openStream());

			String imageId = UUID.randomUUID().toString();
			imageTemplate = StringUtils.replace(imageTemplate,"${image}",image64);
			imageTemplate = StringUtils.replace(imageTemplate,"${imageId}",imageId);

			imageMap.put("cid:" + imageId, imageTemplate);
			img.attr("src","cid:" + imageId);
		}

		htmlToEml(title,doc, imageMap);
		System.out.println(title);

		Elements headLines = doc.select("h2:has(span.mw-headline)");
		if (headLines.size() < 2) return;

		// Unterartikel als separate Dateien anlegen
		removeQuietly("#toc", doc);

		for (Element headLine : headLines)
		{
			Document smallDoc = Jsoup.parse(doc.toString());
			boolean remove = false;
			for (Element elem : smallDoc.selectFirst("h2:has(span.mw-headline)").parent().children())
			{
				if (elem.nodeName().equalsIgnoreCase("h2") && elem.selectFirst(".mw-headline") != null)
				{
					remove = !elem.selectFirst(".mw-headline").html().equalsIgnoreCase(headLine.selectFirst(".mw-headline").html());
				}
				if (remove) elem.remove();
			}
			htmlToEml(title + " [" + headLine.selectFirst(".mw-headline").html() + "]", smallDoc, imageMap);
			System.out.println(title + " [" + headLine.selectFirst(".mw-headline").html() + "]");
		}
	}

	private static void htmlToEml(String title, Document doc, Map<String, String> imageMap) throws Exception
	{
		Elements imgs = doc.select("img");
		String images = "";
		for (Element img : imgs)
		{
			images = images + imageMap.get(img.attr("src"));
		}
		images = StringUtils.trim(images);

		String mail = streamToString(Application.class.getClassLoader().getResource("template.eml").openStream());

		mail = StringUtils.replace(mail,"${images}",images);
		mail = StringUtils.replace(mail,"${html}",doc.toString());
		mail = StringUtils.replace(mail,"${subject}",title);
		mail = StringUtils.replace(mail,"${messageId}",UUID.randomUUID().toString());

		FileUtils.writeStringToFile(new File(title + ".eml"),mail,StandardCharsets.UTF_8);
	}

	private static String streamToString(InputStream stream) throws Exception
	{
		try
		{
			return IOUtils.toString(stream, StandardCharsets.UTF_8);
		}
		finally
		{
			IOUtils.closeQuietly(stream);
		}
	}

	private static byte[] streamToBytes(InputStream stream) throws Exception
	{
		try
		{
			return IOUtils.toByteArray(stream);
		}
		finally
		{
			IOUtils.closeQuietly(stream);
		}
	}

	private static void removeQuietly(String query, Document doc)
	{
		try
		{
			doc.selectFirst(query).remove();
		}
		catch (Exception e) {}
	}

	private static CloseableHttpClient createHttpClient()
	{
		try
		{
			// Create a trust manager that does not validate certificate chains
			X509TrustManager trustManager = new X509TrustManager() {
				public X509Certificate[] getAcceptedIssuers() {
					return new X509Certificate[0];
				}

				public void checkClientTrusted(X509Certificate[] certs, String authType) {
				}

				public void checkServerTrusted(X509Certificate[] certs, String authType) {
				}
			};

			// Install the all-trusting trust manager
			SSLContext context = SSLContext.getInstance("SSL");
			context.init(null, new TrustManager[]{trustManager}, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());

			return HttpClientBuilder.create()
					.setSSLContext(context)
					.setSSLHostnameVerifier((s, sslSession) -> true)
					.setDefaultCookieStore(new BasicCookieStore())
					.disableRedirectHandling()
					.setDefaultRequestConfig(RequestConfig.custom()
							.setConnectTimeout(30 * 1000)
							.setConnectionRequestTimeout(30 * 1000)
							.setSocketTimeout(30 * 1000)
							.build())
					.build();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
}