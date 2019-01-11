package de.priv.icalbackup;


import lombok.Cleanup;
import lombok.Getter;
import org.apache.commons.crypto.stream.CryptoInputStream;
import org.apache.commons.crypto.stream.CryptoOutputStream;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Component
public class Password
{
	private SecretKey key;
	private IvParameterSpec iv;
	@Getter private byte[] password;

	@Value("file:config.b64")
	private Resource config;

	@PostConstruct
	private void generateSecretKey() throws Exception
	{
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		keyGen.init(256);
		this.key = keyGen.generateKey();

		byte[] bytes = new byte[256];
		new SecureRandom(){{nextBytes(bytes);}};
		this.iv = new IvParameterSpec(bytes);
	}

	private String validate(String password)
	{
		try
		{
			if (!this.config.exists()) return "config.b64 not found!";



//			Resource configB64 = this.resourceLoader.getResource("config.b64");
//
//			credentials.delete(0, credentials.length());
//			credentials.append(":");
//			Request request = new Request.Builder().url("https://192.168.0.5:8443/remote.php/dav/principals/users/backupper/").build();
//			Response response = this.client.newCall(request).execute();
//			System.out.println(response.code() + " : \n" + StringUtils.replace(response.body().string(), "\r\n" , ", "));

			return null;
		}
		catch (Exception e)
		{
			return ExceptionUtils.getMessage(e);
		}
	}

	private byte[] encrypt(String data) throws Exception
	{
		@Cleanup
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		@Cleanup
		CryptoOutputStream cos = new CryptoOutputStream("AES/CBC/PKCS5Padding", new Properties(), outputStream, this.key, this.iv);
		cos.write(data.getBytes(StandardCharsets.UTF_8));
		cos.flush();

		return outputStream.toByteArray();
	}

	private String decrypt(byte[] data) throws Exception
	{
		@Cleanup
		InputStream inputStream = new ByteArrayInputStream(data);
		@Cleanup
		CryptoInputStream cis = new CryptoInputStream("AES/CBC/PKCS5Padding", new Properties(), inputStream, this.key, this.iv);

		return IOUtils.toString(cis,StandardCharsets.UTF_8);
	}

	public String setPassword(String password) throws Exception
	{
		String error = this.validate(password);

		if (error != null) return error;

		this.password = this.encrypt(password);

		return null;
	}

}