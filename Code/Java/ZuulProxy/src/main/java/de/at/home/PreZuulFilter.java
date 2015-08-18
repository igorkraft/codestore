package de.at.home;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

public class PreZuulFilter extends ZuulFilter
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
		RequestContext.getCurrentContext().setResponse(new ResponseWrapper(response));
		
		return null;
	}

	@Override
	public String filterType()
	{
		return "pre";
	}

	@Override
	public int filterOrder()
	{
		return 1;
	}

}
