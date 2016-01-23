package de.at.home;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration // konfiguriert die Anwendung abhängig von ihren Startern
@ComponentScan // sorgt dafür, dass die Controller-Klassen automatisch gefunden werden
public class ApplicationMain
{
	public static void main(String[] args) throws Exception
	{
		SpringApplication.run(ApplicationMain.class, args);
	}
}