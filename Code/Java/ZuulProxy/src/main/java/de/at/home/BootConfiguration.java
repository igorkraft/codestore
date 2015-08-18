package de.at.home;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.zuul.ZuulFilter;

@Configuration
public class BootConfiguration
{
	@Bean
	public ZuulFilter preZuulFilter()
	{
		return new PreZuulFilter();
	}

	@Bean
	public ZuulFilter postZuulFilter()
	{
		return new PostZuulFilter();
	}
}
