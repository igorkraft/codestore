package de.at.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.zuul.ZuulFilter;

@RestController
public class WebController
{
	@Autowired
	@Qualifier("postZuulFilter")
	private ZuulFilter postZuulFilter;
	
	public void setPostZuulFilter(ZuulFilter postZuulFilter)
	{
		this.postZuulFilter = postZuulFilter;
	}

	@RequestMapping("/")
	String root() 
	{
		return "Hello World!";
	}
}
