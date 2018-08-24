package org.tests;

//import org.apache.commons.lang3.StringUtils;

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
			System.out.println(src);
		}



//		System.out.println(htmlBody);
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
