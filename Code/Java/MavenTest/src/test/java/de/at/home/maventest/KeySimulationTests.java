package de.at.home.maventest;

import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import org.junit.Test;

public class KeySimulationTests
{
	private Robot robot;
	
	public KeySimulationTests()
	{
		try
		{
			this.robot = new Robot();
		}catch (Exception e) {/* ignore */}
	}
	
	@Test
	public void keyPressTest() throws Exception
	{
		// Simulate a key press
		this.robot.keyPress(KeyEvent.VK_A);
		this.robot.keyRelease(KeyEvent.VK_A);
	}
	
//	@Test
	public void mouseClickTest() throws Exception
	{
		// Simulate a mouse click
		this.robot.mousePress(InputEvent.BUTTON1_MASK);
		this.robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
}
