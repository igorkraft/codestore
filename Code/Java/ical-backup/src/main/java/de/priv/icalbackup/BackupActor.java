package de.priv.icalbackup;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@EnableScheduling
public class BackupActor
{
	@Autowired
	OkHttpClient client;

	@Autowired
	StringBuilder credentials;

	@Scheduled(fixedDelay = 5000000, initialDelay = 1000)
	void scheduler() throws Exception
	{
		credentials.delete(0, credentials.length());
		credentials.append(":");
		Request request = new Request.Builder().url("https://192.168.0.5:8443/remote.php/dav/principals/users/backupper/").build();
		Response response = this.client.newCall(request).execute();
		System.out.println(response.code() + " : \n" + StringUtils.replace(response.body().string(), "\r\n" , ", "));

		credentials.delete(0, credentials.length());
		credentials.append("gggg:jjjjj");
		request = new Request.Builder().url("https://192.168.0.5:8443/remote.php/dav/principals/users/backupper/").build();
		response = this.client.newCall(request).execute();
		System.out.println(response.code() + " : \n" + StringUtils.replace(response.body().string(), "\r\n" , ", "));

		// TODO nur einmal die Credentials mitschicken; nextcloud abmelden
	}
}
