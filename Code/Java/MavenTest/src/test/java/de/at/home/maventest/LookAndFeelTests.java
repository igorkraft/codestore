package de.at.home.maventest;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.junit.Test;

public class LookAndFeelTests
{
	@Test
	public void lookAndFeelTest() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException
	{
//	UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//	UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
	UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
//	UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
//	UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
	JPasswordField passwordField = new JPasswordField(20);
	passwordField.setEchoChar('*');
	JOptionPane.showMessageDialog(null,passwordField,"Enter password",JOptionPane.OK_OPTION);
	String password = new String(passwordField.getPassword());
	}
}
