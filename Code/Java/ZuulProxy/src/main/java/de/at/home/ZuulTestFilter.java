package de.at.home;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

public class ZuulTestFilter extends ZuulFilter
{
	@Override
	public boolean shouldFilter()
	{
		HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
		
		return request.getRequestURI().contains("test1.txt");
	}

	@Override
	public Object run()
	{
		HttpServletResponse response = RequestContext.getCurrentContext().getResponse();
		return response;
	}

	@Override
	public String filterType()
	{
		return "post";
	}

	@Override
	public int filterOrder()
	{
		return 1;
	}

}
