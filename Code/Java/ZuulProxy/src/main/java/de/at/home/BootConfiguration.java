package de.at.home;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.zuul.ZuulFilter;

@Configuration
public class BootConfiguration
{
	@Bean
	public ZuulFilter zuulTestFilter()
	{
		return new ZuulTestFilter();
	}

}
