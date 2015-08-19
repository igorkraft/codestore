package de.at.home.filter;

import java.io.IOException;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import de.at.home.ResponseModifier;
import de.at.home.ResponseWrapper;

public abstract class PreZuulFilter extends ZuulFilter implements ResponseModifier
{
	@Override
	public boolean shouldFilter()
	{
		HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
		return this.condition(request);
	}

	@Override
	public Object run()
	{
		HttpServletResponse response = RequestContext.getCurrentContext().getResponse();
		RequestContext.getCurrentContext().setResponse(new ResponseWrapper(response, this));
		
		return null;
	}

	@Override
	public String filterType()
	{
		return "pre";
	}

	public abstract boolean condition(HttpServletRequest request);
	public abstract void modify(ServletResponse response, String content) throws IOException;
	public abstract int filterOrder();
}
