package org.local.test;

import java.text.ParseException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.Charsets;

public class Base64Test
{

	public static void main(String[] args) throws ParseException
	{
		System.out.println(new String(Base64.decodeBase64("c2NodWV0dDojRU5DI09UTDQ5Q0l0UkYrUGhVc1BRdE94S0ZxVTRBVDA4NTFw".getBytes())));
		System.out.println(new String(Base64.encodeBase64("umlaute:öäüß".getBytes(Charsets.ISO_8859_1))));
	}
}
