package de.priv.icalbackup;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Component
public class TestClass
{
	@PostConstruct
	void testInit() throws Exception
	{
		try {
			System.out.println();

			//		Process process = Runtime.getRuntime().exec("base64 -d /media/data/Code/codestore/Code/Java/ical-backup/README.b64 | openssl enc -d -aes256 | gunzip");
			//		IOUtils.write("test\n", process.getOutputStream(), StandardCharsets.UTF_8);

			//		Process process = Runtime.getRuntime().exec("base64 -d /media/data/Code/codestore/Code/Java/ical-backup/README.b64 | openssl enc -d -aes256 | gunzip");efault
			//
			//		String output = IOUtils.toString(process.getInputStream(), StandardCharsets.UTF_8);
			//		String errors = IOUtils.toString(process.getErrorStream(), StandardCharsets.UTF_8);
			//
			//		process.waitFor();
			//
			//		IOUtils.closeQuietly(process.getInputStream());
			//		IOUtils.closeQuietly(process.getErrorStream());
			//
			//		System.out.println(output);
			//		System.out.println(errors);

			byte[] buffer = Base64.decodeBase64("WIar3LuWzi0E/IPbPOdHAQ==".getBytes(StandardCharsets.UTF_8));
			//		Process process = Runtime.getRuntime().exec("openssl enc -d -aes256 -pass pass:a");


			System.out.println();


			byte[] firstBuffer = FileUtils.readFileToByteArray(new File("/media/data/Code/codestore/Code/Java/ical-backup/README.b64"));
			byte[] secondBuffer = ArrayUtils.addAll("test\n".getBytes(StandardCharsets.UTF_8), Base64.decodeBase64(firstBuffer));

			ByteArrayInputStream inputStream = new ByteArrayInputStream(buffer);
			ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			CommandLine commandline = CommandLine.parse("openssl enc -d -aes256 -pass pass:a");
			DefaultExecutor exec = new DefaultExecutor();
			PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream, errorStream, inputStream);
			exec.setStreamHandler(streamHandler);
			exec.execute(commandline);
			String o = outputStream.toString();

			// base64 -d file.b64 | openssl enc -d -aes256 | gunzip > file.txt
		}
		catch (Exception e)
		{
			System.out.println();
		}
	}

}
