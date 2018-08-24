package org.tests;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SimpleTests
{
	@Test
	public void test() throws Exception
	{
		CloseableHttpClient client = Application.createHttpClient();
		String title = "Lotus_(genus)";
		String url = "https://en.wikipedia.org/w/index.php?title=" + title + "&printable=yes";

		String htmlBody = Application.streamToString(client.execute(new HttpGet(url)).getEntity().getContent());

		htmlBody = this.cutOffUnimportantContent(htmlBody, "a", "\"mw-jump-link\"");
		htmlBody = this.cutOffUnimportantContent(htmlBody, "a", "\"mw-jump-link\"");
		htmlBody = this.cutOffUnimportantContent(htmlBody, "div", "\"siteSub\"");
		htmlBody = this.cutOffUnimportantContent(htmlBody, "div", "\"mw-navigation\"");
		htmlBody = this.cutOffUnimportantContent(htmlBody, "div", "\"footer\"");
		htmlBody = this.cutOffUnimportantContent(htmlBody, "div", "\"printfooter\"");
		htmlBody = this.cutOffUnimportantContent(htmlBody, "div", "\"catlinks\"");
		htmlBody = this.cutOffUnimportantContent(htmlBody, "div", "\"mw-hidden-catlinks\"");

		Map sourceMap = new HashMap();
		int lastEndIndex = 0;
		while (true)
		{
			int srcStartIndex = htmlBody.indexOf("<img ", lastEndIndex);
			if (srcStartIndex == -1) break;
			srcStartIndex = srcStartIndex + 5;
			int srcEndIndex = htmlBody.indexOf(">", srcStartIndex);
			String imgElement = htmlBody.substring(srcStartIndex,srcEndIndex);
			String src = this.getSource(imgElement);
			String imageId = UUID.randomUUID().toString();
			sourceMap.put("cid:" + imageId,src);

			lastEndIndex = srcEndIndex;
		}

		for (Object entryObj : sourceMap.entrySet())
		{
			Map.Entry entry = (Map.Entry)entryObj;
			htmlBody = htmlBody.replace((String)entry.getValue(),(String)entry.getKey());
			String src = (String)entry.getValue();
			if (src.startsWith("//")) src = src.replace("//","https://");
			if (src.startsWith("/")) src = src.replace("/","https://en.wikipedia.org/");
			entry.setValue(src);
		}

		System.out.println(htmlBody);
	}

	public String cutOffUnimportantContent(String htmlBody, String tag, String id)
	{
		try
		{
			int startIndex = htmlBody.lastIndexOf("<", htmlBody.indexOf(id));
			int endIndex = this.getEndIndex(htmlBody, tag, startIndex, 0);

			return htmlBody.replace(htmlBody.substring(startIndex, endIndex), "");
		}
		catch (Exception e)
		{
			return htmlBody;
		}
	}

	public int getEndIndex(String htmlBody, String tag, int lastStartIndex, int depth)
	{
		if (lastStartIndex == -1) return -1;

		String startTag = "<" + tag + " ";//todo Leerzeichen oder schließende Klammer
		String endTag = "</" + tag + ">";

		int nextStartIndex = htmlBody.indexOf(startTag, lastStartIndex);
		int nextEndIndex = htmlBody.indexOf(endTag, lastStartIndex);

		if (nextStartIndex == -1) nextStartIndex = htmlBody.length();

		if (nextStartIndex < nextEndIndex) return this.getEndIndex(htmlBody, tag, nextStartIndex + 1, depth + 1);

		if (depth != 1) return this.getEndIndex(htmlBody, tag, nextEndIndex + 1, depth - 1);

		return nextEndIndex + endTag.length();
	}

	public String getSource(String imgElement)
	{
		int srcStartIndex = imgElement.indexOf("src=\"");
		if (srcStartIndex == -1) return null;
		srcStartIndex = srcStartIndex + 5;
		int srcEndIndex = imgElement.indexOf("\"", srcStartIndex);
		String src = imgElement.substring(srcStartIndex,srcEndIndex);
		return src.length() > 0 ? src : null;
	}

	public void writeByteArrayToFile(File file, byte[] data)
	{
		FileOutputStream out = null;
		try
		{
			out = new FileOutputStream(file);
			out.write(data);
			out.close();
		}
		catch (Exception e){}
		finally {try { out.close();} catch (Exception e2){}}
	}
}
