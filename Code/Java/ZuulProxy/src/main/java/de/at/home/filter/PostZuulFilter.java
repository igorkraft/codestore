package de.at.home.filter;

import javax.servlet.http.HttpServletResponse;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import de.at.home.ResponseWrapper;

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
			ResponseWrapper wrapper = (ResponseWrapper)RequestContext.getCurrentContext().getResponse();
			
			wrapper.getResponseModifier().modify(wrapper.getResponse(), wrapper.getCaptureAsString());
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
		// der SendResponseFilter hat die Position 1000. Filter, die die Response verarbeiten, 
		// müssen eine Position > 1000 haben 
		return 1001;
	}
}
