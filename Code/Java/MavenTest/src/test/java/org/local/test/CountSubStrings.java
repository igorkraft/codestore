package org.local.test;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

public class CountSubStrings
{

	public static void main(String[] args) throws IOException
	{
		String content = FileUtils.readFileToString(new File("/home/user/methods.txt"));
		int count = StringUtils.countMatches(content, "@Override");
		System.out.println(count);
		
		

	}

}
