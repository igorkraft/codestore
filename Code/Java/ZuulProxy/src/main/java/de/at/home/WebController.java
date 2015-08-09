package de.at.home;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController
{
	@RequestMapping("/")
	String root() 
	{
		return "Hello World!";
	}
}
