package org.local.test;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.swing.JOptionPane;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.junit.Test;

public class TomcatTests
{
	@Test
	public void simpleServerTest() throws Exception
	{
		Servlet servlet = new HttpServlet()
		{
			@Override
			public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException
			{
				response.getWriter().write("Test Test Tomcat2");
				response.getWriter().flush();
				response.getWriter().close();
			}
		};
		
		Tomcat tomcat = new Tomcat();
		tomcat.setPort(8080);
		
		Context ctx = tomcat.addContext("/", "C:/Users/schuett/Desktop/exchange/compare");
		Tomcat.addServlet(ctx, "test", servlet);
		ctx.addServletMapping("/*", "test");
		
		tomcat.start();
		
		JOptionPane.showMessageDialog(null, "stop server");
	}
}
