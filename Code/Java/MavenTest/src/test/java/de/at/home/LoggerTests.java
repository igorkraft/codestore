package de.at.home;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.junit.Test;

public class LoggerTests
{
	private Logger logger = Logger.getLogger(LoggerTests.class);
	
	@Test
	public void loggerTest1()
	{
		(new Thread(new LoggingThread())).start();
		
		JOptionPane.showMessageDialog(null, "stop server");
	}
	
	private class LoggingThread implements Runnable
	{
		@Override
		public void run()
		{
			while (true)
			{
				logger.debug("test");
				try {Thread.sleep(1000);} catch (Exception e){return;}
			}
		}
		
	}
}
