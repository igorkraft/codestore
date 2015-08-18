package de.at.home;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

public class PostZuulFilter extends ZuulFilter
{
	@Override
	public boolean shouldFilter()
	{
		HttpServletResponse response = RequestContext.getCurrentContext().getResponse();
		return response instanceof ResponseWrapper;
	}

	@Override
	public Object run()
	{
		try
		{
			ResponseWrapper responseWrapper = (ResponseWrapper)RequestContext.getCurrentContext().getResponse();
			ServletResponse response = responseWrapper.getResponse();
			
			String content = responseWrapper.getCaptureAsString();
			response.getWriter().write(content);
			response.getWriter().write("test content");
		}
		catch (Exception e) { /* TODO */ }
		return null;
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
