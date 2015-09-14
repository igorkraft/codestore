package de.at.home.androidhelper;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.swing.JOptionPane;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

public class Base64Decoder
{
	public static void main(String[] args) throws IOException
	{
		String base64 = JOptionPane.showInputDialog("Put in base64 string.");
		if (base64 == null) return;
		Base64Decoder.decode(base64);
	}
	
	public static void decode(String base64) throws IOException
	{
		String date = DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMdd-HHmmss");
		File destination = new File(new File(System.getProperty("user.home"),"Desktop"),date);
		
		byte[] zipData = Base64.decodeBase64(base64);
		ByteArrayInputStream byteStream = new ByteArrayInputStream(zipData);
		ZipInputStream zipStream = new ZipInputStream(byteStream);
		
		ZipEntry entry = zipStream.getNextEntry();
		while(entry != null)
		{
			File outFile = new File(destination,entry.getName());
			outFile.getParentFile().mkdirs();
			FileOutputStream outStream = new FileOutputStream(outFile);
			IOUtils.copy(zipStream, outStream);
			IOUtils.closeQuietly(outStream);
			entry = zipStream.getNextEntry();
		}
		
		IOUtils.closeQuietly(zipStream);
		IOUtils.closeQuietly(byteStream);
		
		System.out.println(destination.getAbsolutePath() + " erfolgreich angelegt.");
	}
}
