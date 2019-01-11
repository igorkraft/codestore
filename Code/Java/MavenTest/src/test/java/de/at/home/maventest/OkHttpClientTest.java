package de.at.home.maventest;

import com.google.common.net.HttpHeaders;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.JavaNetCookieJar;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.nio.charset.StandardCharsets;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;


public class OkHttpClientTest
{
	private OkHttpClient client = null;
	
	public OkHttpClientTest() throws Exception
	{
		this.client = this.createOkHttpClient("user", "pwd");
	}
	
	@Test
	public void simpleRequestTest() throws Exception
	{
		Request request = new Request.Builder().url("https://www.wikipedia.org/").build();
		Response response = this.client.newCall(request).execute();
		System.out.println(response.body().string());
	}
	
//	@Test
	public void uploadTestSynchronous() throws Exception
	{
		String url = "http://localhost:8081/upstream/";
		byte[] file = new byte[]{84, 101, 115, 116, 10, -16, -97, -104, -76, -30, -106, -120, -30, -84, -92, 10, 84, 101, 115, 116, 10};
		
		RequestBody requestBody = new MultipartBody.Builder()
			.setType(MultipartBody.FORM)
			.addPart(
					Headers.of(HttpHeaders.CONTENT_DISPOSITION, "form-data; name=\"file\"; filename=\"test.txt\""),
					RequestBody.create(MediaType.parse("application/octet-stream"), file))
			.build();
		
		Request request = new Request.Builder().url(url).post(requestBody).build();
		
		Response response = this.client.newCall(request).execute();
		System.out.println(response.body().string());
	}

//	@Test
	public void upstreamTestAsynchronous() throws Exception
	{
		String url = "http://localhost:8081/upstream/";
		final File file = new File("/path/to/large/file");

		RequestBody requestBody = new MultipartBody.Builder()
			.setType(MultipartBody.FORM)
			.addPart(
					Headers.of(HttpHeaders.CONTENT_DISPOSITION, "form-data; name=\"file\"; filename=\"" + file.getName() + "\""),
					new RequestBody()
					{
						@Override public MediaType contentType() {return MediaType.parse("application/octet-stream");}
						
						@Override
						public void writeTo(BufferedSink sink) throws IOException
						{
							FileInputStream fileStream = null;
							try
							{
								fileStream = new FileInputStream(file);
								IOUtils.copyLarge(fileStream, new BufferedSinkOutputStream(sink));
							}
							finally
							{
								IOUtils.closeQuietly(fileStream);
								// der OutputStream/Sink wird vom HttpClient geschlossen
							}
						}
					})
			.build();

		this.client.newCall(new Request.Builder().url(url).post(requestBody).build()).enqueue(
			new Callback()
			{
				@Override
				public void onResponse(Call call, Response response) throws IOException
				{
					System.out.println(response.body().string());
				}

				@Override
				public void onFailure(Call call, IOException e)
				{
					e.printStackTrace();
				}
			}
		);
	}

	private OkHttpClient createOkHttpClient(final String userName, final String userPassword) throws Exception
	{
		// Create a trust manager that does not validate certificate chains
		X509TrustManager trustManager = new X509TrustManager()
		{
			public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
			public void checkClientTrusted(X509Certificate[] certs, String authType) {}
			public void checkServerTrusted(X509Certificate[] certs, String authType) {}
		};

		// Install the all-trusting trust manager
		SSLContext context = SSLContext.getInstance("SSL");
		context.init(null, new TrustManager[]{trustManager}, new java.security.SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());

		return new OkHttpClient.Builder()
				.connectTimeout(1, TimeUnit.MINUTES)
				.readTimeout(10, TimeUnit.MINUTES)
				.writeTimeout(10, TimeUnit.MINUTES)
				.cookieJar(new JavaNetCookieJar(new CookieManager(){{setCookiePolicy(CookiePolicy.ACCEPT_ALL);}}))
//				.proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("some_proxy", 1234)))
				.hostnameVerifier((s, sslSession) -> true)
				.authenticator((route, response) ->
				{
					String credentials = new String(Base64.encodeBase64((userName + ":" + userPassword).getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
					return response.request().newBuilder().header(HttpHeaders.AUTHORIZATION, "Basic " + credentials).build();
				})
				.addNetworkInterceptor(chain ->
						chain.proceed(chain.request().newBuilder()
								.removeHeader(HttpHeaders.USER_AGENT)
								.addHeader(HttpHeaders.USER_AGENT, "some user agent")
								.build())
				)
				.sslSocketFactory(context.getSocketFactory(), trustManager)
				.build();
	}
	
	private class BufferedSinkOutputStream extends OutputStream 
	{
		private BufferedSink sink = null;
		
		public BufferedSinkOutputStream(BufferedSink sink) { this.sink = sink; }
		@Override public void write(int b) throws IOException { this.sink.writeInt(b); }
		@Override public void write(byte[] b) throws IOException { this.sink.write(b); }
		@Override public void write(byte[] b, int off, int len) throws IOException { this.sink.write(b, off, len); }
		@Override public void flush() throws IOException { this.sink.flush(); }
		@Override public void close() throws IOException { this.sink.close(); }
	}
}
