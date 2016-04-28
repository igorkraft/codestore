package de.at.home.maventest;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;
import org.junit.Test;

public class FtpTests
{
	@Test
	public void ftpServerTest() throws FtpException
	{
		FtpServerFactory serverFactory = new FtpServerFactory();
		ListenerFactory factory = new ListenerFactory();
		factory.setPort(8022);
		serverFactory.addListener("default", factory.createListener());
		PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();

		UserManager um = userManagerFactory.createUserManager();
		BaseUser user1 = new BaseUser();
		user1.setName("calle");
		user1.setPassword("secret");
		user1.setHomeDirectory(System.getProperty("user.home"));
		
		BaseUser user2 = new BaseUser();
		user2.setName("calle2");
		user2.setPassword("secret");
		user2.setHomeDirectory(System.getProperty("user.dir")); // working directory

		List<Authority> auths = new ArrayList<>();
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
		
		JOptionPane.showMessageDialog(null, "stop server");
	}
}
