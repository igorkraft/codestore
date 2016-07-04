package de.at.home;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.awt.*;

@SpringBootApplication
public class ApplicationMain
{
	public static Robot robot;

	public static void main(String[] args) throws Exception 
	{
		robot = new Robot();
		SpringApplication.run(ApplicationMain.class, args);
	}

}