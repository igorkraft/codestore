package org.local.test;

import org.apache.log4j.Logger;
import org.junit.Test;

public class LoggerTests
{
	private Logger logger = Logger.getLogger(LoggerTests.class);
	
	@Test
	public void consoleLoggerTest()
	{
		logger.debug("test");
	}
}
