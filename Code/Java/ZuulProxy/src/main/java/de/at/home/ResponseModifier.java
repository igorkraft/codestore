package de.at.home;

import java.io.IOException;

import javax.servlet.ServletResponse;

public interface ResponseModifier
{
	public void modify(ServletResponse response, String content) throws IOException;
}
