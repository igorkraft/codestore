package de.at.home;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.zuul.ZuulFilter;

import de.at.home.filter.PostZuulFilter;
import de.at.home.filter.TestFilter;

@Configuration
public class BootConfiguration
{
	@Bean
	public ZuulFilter testFilter()
	{
		return new TestFilter();
	}

	@Bean
	public ZuulFilter postZuulFilter()
	{
		return new PostZuulFilter();
	}
}
