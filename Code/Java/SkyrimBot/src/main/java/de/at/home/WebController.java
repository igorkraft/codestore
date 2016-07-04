package de.at.home;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

@RestController
public class WebController implements InitializingBean
{
	private static volatile boolean active = false;
	private OkHttpClient client = new OkHttpClient();

	@Value("${url}")
	private String url;

	@RequestMapping("/")
	public String root()
	{
		String result =   "<p>";
		result = result + "<h4>Ablauf zum Starten des Bots</h4>";
		result = result + "- Holzhacken manuell starten<br>";
		result = result + "- Maus ausschalten<br>";
		result = result + "- Pointer manuell auf (0,0) setzen (mit Tablet) '/mouse/0/0'<br>";
		result = result + "- warten bis Holzhacken vorbei ist<br>";
		result = result + "- den Bot starten '/start'<br>";
		result = result + "</p>";
		result = result + "<p>";
		result = result + "<h4>Sonstiges</h4>";
		result = result + "- der Bot kann mit '/stop' angehalten werden<br>";
		result = result + "- einmal Holzhacken dauert 36 Sekunden und bringt 30 Gold ein<br>";
		result = result + "- 24 Stunden Holzhacken bringt 72.000 Gold ein<br>";
		result = result + "- der FTP-Server läuft auf Port 8022<br>";
		result = result + "- der FTP-Benutzer 'c' hat Zugriff auf Laufwerk C:<br>";
		result = result + "- der FTP-Benutzer 'd' hat Zugriff auf Laufwerk D:<br>";
		result = result + "- Savegames liegen unter 'c:/Users/user/Documents/My Games/Skyrim/Saves'<br>";
		result = result + "- Screenshots liegen unter 'd:/Program Files/Steam/steamapps/common/Skyrim'<br>";
		result = result + "</p>";
		return result;
	}

	@RequestMapping("/start")
	public String start()
	{
		if (active) return "err";
		active = true;
		(new Thread(new Worker())).start();
		return "start";
	}

	@RequestMapping("/stop")
	public String stop()
	{
		active = false;
		return "stop";
	}

	@RequestMapping("/pressE")
	public String pressE()
	{
		ApplicationMain.robot.keyPress(KeyEvent.VK_E);
		ApplicationMain.robot.keyRelease(KeyEvent.VK_E);
		return "pressE";
	}

	@RequestMapping("/pressI")
	public String pressI()
	{
		ApplicationMain.robot.keyPress(KeyEvent.VK_I);
		ApplicationMain.robot.keyRelease(KeyEvent.VK_I);
		return "pressI";
	}

	@RequestMapping("/mouse/{x}/{y}")
	public String mouse(@PathVariable int x, @PathVariable int y)
	{
		ApplicationMain.robot.mouseMove(x,y);
		return "mouse";
	}

	@RequestMapping("/mouse")
	public String pointerInfo()
	{
		String result = String.valueOf(MouseInfo.getPointerInfo().getLocation().getX());
		result = result + "|";
		result = result + String.valueOf(MouseInfo.getPointerInfo().getLocation().getY());
		return result;
	}

	@Override
	public void afterPropertiesSet() throws Exception
	{
		FtpServerFactory serverFactory = new FtpServerFactory();
		ListenerFactory factory = new ListenerFactory();
		factory.setPort(8022);
		serverFactory.addListener("default", factory.createListener());
		PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();

		UserManager um = userManagerFactory.createUserManager();
		BaseUser user1 = new BaseUser();
		user1.setName("c");
		user1.setPassword("secret");
		user1.setHomeDirectory("c:/");

		BaseUser user2 = new BaseUser();
		user2.setName("d");
		user2.setPassword("secret");
		user2.setHomeDirectory("d:/");

		java.util.List<Authority> auths = new ArrayList<Authority>();
		auths.addAll(user1.getAuthorities());
		auths.addAll(user2.getAuthorities());
		auths.add(new WritePermission());

		user1.setAuthorities(auths);
		um.save(user1);

		user2.setAuthorities(auths);
		um.save(user2);

		serverFactory.setUserManager(um);
		FtpServer server = serverFactory.createServer();
		server.start();
	}

	private class Worker implements Runnable
	{
		@Override
		public void run()
		{
			try
			{
				while (true)
				{
					if (!active) return;
					// auf 230 setzen
					client.newCall(new Request.Builder().url(url + "/mouse/0/230").build()).execute();
					Thread.sleep(500);
					// E drücken
					client.newCall(new Request.Builder().url(url + "/pressE").build()).execute();
					Thread.sleep(500);
					// auf 0 setzen
					client.newCall(new Request.Builder().url(url + "/mouse/0/0").build()).execute();
					// sleep
					Thread.sleep(35000);
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				active = false;
			}
		}
	}
}
