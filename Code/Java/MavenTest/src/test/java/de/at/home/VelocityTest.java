package de.at.home;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import com.google.gson.Gson;

public class VelocityTest
{
	public static void main(String[] args)
	{
		Velocity.init();
		
		VelocityEngine engine = new VelocityEngine();
		
		
		
		Template template = Velocity.getTemplate("table_pattern.html");
		Map json = (new Gson()).fromJson(JsonPathTest.JSON_TEXT, Map.class);
		VelocityContext context = new VelocityContext();
		context.put("content",json);
		
		Writer writer = new StringWriter();
		template.merge(context, writer);
		
		System.out.println(writer);
	}

}
