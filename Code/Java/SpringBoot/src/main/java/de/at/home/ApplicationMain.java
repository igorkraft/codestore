package de.at.home;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration // konfiguriert die Anwendung abhängig von ihren Startern
public class ApplicationMain
{

	@RequestMapping("/")
	String root() 
	{
		return "Hello World!";
	}

	public static void main(String[] args) throws Exception 
	{
		SpringApplication.run(ApplicationMain.class, args);
	}

}