package de.at.home;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpClientTest
{
	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		try
		{
			CloseableHttpResponse response = HttpClients.createDefault().execute(new HttpGet("http://localhost:8765"));
			String body = EntityUtils.toString(response.getEntity());		
			response.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
