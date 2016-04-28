package de.at.home.maventest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

public class RandomTests
{
	String chars = "+-_!=" + "0123456789" + "abcdefghijklmnopqrstuvwxyz" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	@Test
	public void randomTest()
	{
		List<Character> chars = Arrays.asList(ArrayUtils.toObject(this.chars.toCharArray()));
		Collections.shuffle(chars);
		
		for (int i = 0; i < 20; i++)
		{
			System.out.print(chars.get(((int)(Math.random() * 1000)) % chars.size()));
		}
	}
}
