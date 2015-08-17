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
	@Qualifier("zuulTestFilter")
	private ZuulFilter zuulFilter;
	
	public void setZuulFilter(ZuulFilter zuulFilter)
	{
		this.zuulFilter = zuulFilter;
	}

	@RequestMapping("/")
	String root() 
	{
		return "Hello World!";
	}
}
