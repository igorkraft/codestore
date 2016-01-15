package de.at.home;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Proxy;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okio.BufferedSink;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import com.google.common.base.Charsets;
import com.google.common.net.HttpHeaders;
import com.squareup.okhttp.Authenticator;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

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
	public void uploadTest() throws Exception
	{
		String url = "http://localhost:8081/upstream/";
		byte[] file = new byte[]{84, 101, 115, 116, 10, -16, -97, -104, -76, -30, -106, -120, -30, -84, -92, 10, 84, 101, 115, 116, 10};
		
		RequestBody requestBody = new MultipartBuilder()
			.type(MultipartBuilder.FORM)
			.addPart(
					Headers.of(HttpHeaders.CONTENT_DISPOSITION, "form-data; name=\"file\"; filename=\"test.txt\""),
					RequestBody.create(MediaType.parse("application/octet-stream"), file))
			.build();
		
		Request request = new Request.Builder().url(url).post(requestBody).build();
		
		Response response = this.client.newCall(request).execute();
		System.out.println(response.body().string());
	}

//	@Test
	public void upstreamTest() throws Exception
	{
		String url = "http://localhost:8081/upstream/";
		final File file = new File("/path/to/large/file");
		
		RequestBody requestBody = new MultipartBuilder()
			.type(MultipartBuilder.FORM)
			.addPart(
					Headers.of(HttpHeaders.CONTENT_DISPOSITION, "form-data; name=\"file\"; filename=\"" + file.getName() + "\""),
					new RequestBody()
					{
						@Override public MediaType contentType() {return MediaType.parse("application/octet-stream");};
						
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
		
		Request request = new Request.Builder().url(url).post(requestBody).build();
		
		Response response = this.client.newCall(request).execute();
		System.out.println(response.body().string());
	}
	
	private OkHttpClient createOkHttpClient(final String userName, final String userPassword) throws Exception
	{
		OkHttpClient client = new OkHttpClient();
		
//		client.setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("some_proxy", 1234)));
		
		client.setHostnameVerifier(new HostnameVerifier()
		{
			@Override
			public boolean verify(String hostname, SSLSession session)
			{
				return true;
			}
		});
		
		client.setAuthenticator(new Authenticator()
		{
			@Override
			public Request authenticate(Proxy proxy, Response response)
			{
				String credentials = new String(Base64.encodeBase64((userName + ":" + userPassword).getBytes(Charsets.UTF_8)), Charsets.UTF_8);
				return response.request().newBuilder().header(HttpHeaders.AUTHORIZATION, "Basic " + credentials).build();
			}
			
			@Override 
			public Request authenticateProxy(Proxy proxy, Response response) 
			{
				return null; // Null indicates no attempt to authenticate.
			}
		});

		client.networkInterceptors().add(new Interceptor()
		{
			@Override
			public Response intercept(Chain chain) throws IOException
			{
				return chain.proceed(chain.request().newBuilder()
						.removeHeader(HttpHeaders.USER_AGENT)
						.addHeader(HttpHeaders.USER_AGENT, "some user agent")
						.build());
			}
		});
		
		// Create a trust manager that does not validate certificate chains
		TrustManager[] trustAllCerts = new TrustManager[] 
		{
				new X509TrustManager() 
				{
					public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
					public void checkClientTrusted(X509Certificate[] certs, String authType) {}
					public void checkServerTrusted(X509Certificate[] certs, String authType) {}
				}
		};

		// Install the all-trusting trust manager
		SSLContext context = SSLContext.getInstance("SSL");
		context.init(null, trustAllCerts, new java.security.SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory()); // besser man macht das nur einmal
		client.setSslSocketFactory(context.getSocketFactory());

		return client;
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
