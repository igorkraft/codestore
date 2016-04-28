package de.at.home.maventest;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

public class ThymeleafTest
{

	public static void main(String[] args) throws IOException
	{
		File patternFile = new File("table_pattern.html");
		String pattern = FileUtils.readFileToString(patternFile, Charset.forName("UTF-8"));
		
		ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
		resolver.setTemplateMode("XHTML");
		resolver.setSuffix(".html");
		TemplateEngine engine = new TemplateEngine();
		engine.setTemplateResolver(resolver);
		
	}

}
