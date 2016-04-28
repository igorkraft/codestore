package de.at.home.maventest;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import org.junit.Test;

public class ClipboardTests
{
	private String readFromClipboard() throws UnsupportedFlavorException, IOException
	{
		Transferable data = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
		for (DataFlavor flavor : data.getTransferDataFlavors())
		{
			if (!(data.getTransferData(flavor) instanceof String)) continue;
			return data.getTransferData(flavor).toString();
		}
		return "";
	}
	
	private void writeToClipboard(String data)
	{
		StringSelection contents = new StringSelection(data);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(contents, null);
	}
	
//	@Test
	public void readFromClipboardTest() throws UnsupportedFlavorException, IOException
	{
		System.out.println(this.readFromClipboard());
	}
	
	@Test
	public void writeToClipboardTest()
	{
		this.writeToClipboard("someData");
	}
}
