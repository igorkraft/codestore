package de.at.home;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration // konfiguriert die Anwendung abhängig von ihren Startern
@ComponentScan
public class ApplicationMain
{

	public static void main(String[] args) throws Exception 
	{
		SpringApplication.run(ApplicationMain.class, args);
	}

}