package org.example.spring;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainClass 
{
	private String str;
	
	public static void main(String[] args) throws IOException 
	{
		// hier können auch mehrere Kontextdateien übergeben werden
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        context.getBean(MainClass.class).mainInternal(args);
	}
	
	public void mainInternal(String args[])
	{
		System.out.println(this.str);
	}

	public void setStr(String str) 
	{
		this.str = str;
	}
}
