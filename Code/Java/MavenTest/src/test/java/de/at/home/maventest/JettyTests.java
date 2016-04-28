package de.at.home.maventest;

import java.io.IOException;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.Test;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.swing.JOptionPane;

public class JettyTests
{
	@Test
	public void simpleServerTest() throws Exception
	{
		Servlet servlet = new HttpServlet()
		{
			@Override
			public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException
			{
				String json = "{\"attachments\":false}";
				response.setContentType("application/json");
				response.getWriter().write(json);
				response.getWriter().flush();
				response.getWriter().close();
			}
		};
		
		final ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
		handler.setContextPath("/");
		handler.addServlet(new ServletHolder(servlet), "/");
		Server server = new Server(8080);
		server.setHandler(handler);
		server.start();
		(new Thread(){public void run(){try{handler.getServer().join();}catch(Exception e){}}}).start();
		
		JOptionPane.showMessageDialog(null, "stop server");
	}
}
