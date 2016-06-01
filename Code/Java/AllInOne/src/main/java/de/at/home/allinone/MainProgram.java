package de.at.home;

import org.apache.commons.io.IOUtils;

public class MainProgram
{
	public static void main(String[] args) throws Exception
	{
		String e = IOUtils.toString(IOUtils.toInputStream("Test"));
		System.out.println(e);
	}
}
