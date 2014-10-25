package org.local.test;

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
		BaseUser user = new BaseUser();
		user.setName("calle");
		user.setPassword("secret");
		user.setHomeDirectory("/home/user/Arbeitsfläche");

		List<Authority> auths = new ArrayList<>();
		auths.addAll(user.getAuthorities());
		auths.add(new WritePermission());

		user.setAuthorities(auths);
		um.save(user);

		serverFactory.setUserManager(um);
		FtpServer server = serverFactory.createServer();
		server.start();
		
		JOptionPane.showMessageDialog(null, "stop server");
	}
}
