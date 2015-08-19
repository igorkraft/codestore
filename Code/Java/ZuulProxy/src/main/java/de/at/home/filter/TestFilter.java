package de.at.home.filter;

import java.io.IOException;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class TestFilter extends PreZuulFilter
{
	@Override
	public boolean condition(HttpServletRequest request)
	{
		return request.getRequestURI().contains("test1.txt");
	}
	
	@Override
	public void modify(ServletResponse response, String content) throws IOException
	{
		response.getWriter().write(content);
		response.getWriter().write("manipulated content");
		response.getWriter().close();
	}
	
	@Override
	public int filterOrder()
	{
		return 1;
	}
}
