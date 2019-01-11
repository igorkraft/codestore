package de.priv.icalbackup;

import lombok.Data;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.nio.charset.StandardCharsets;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Data
@org.springframework.context.annotation.Configuration
public class Configuration
{
//	private Map<String, String> calendars;

//	@Value("${repo}")
	private String repo;

	@Bean
	StringBuilder credentials()
	{
		return new StringBuilder();
	}

	@Bean
	OkHttpClient createOkHttpClient(StringBuilder credentials) throws Exception
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
			.hostnameVerifier((s, sslSession) -> true)
			.addNetworkInterceptor(chain ->
					chain.proceed(chain.request().newBuilder()
							.addHeader(HttpHeaders.AUTHORIZATION, "Basic " + new String(Base64.encodeBase64(credentials.toString().getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8))
							.build())
			)
			.sslSocketFactory(context.getSocketFactory(), trustManager)
			.build();
	}
}