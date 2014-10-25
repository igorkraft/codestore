package org.local.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;

public class FreeTests
{
	@Test
	public void freeTest1()
	{
		String s = DateTimeFormat.patternForStyle("FF", Locale.getDefault());
		String s2 = DateTimeFormat.forStyle("FF").withLocale(Locale.getDefault()).print(new DateTime());
		System.out.println(s);
		System.out.println(s2);
	}
	
	private static class Build
	{
		public static class VERSION
		{
			public static final int SDK_INT=0;
		}
	}
}

class R
{
	public static final class attr
	{
	}

	public static final class drawable
	{
		public static final int ic_launcher = 0x7f020000;
		public static final int icon = 0x7f020000;
	}

	public static final class id
	{
		public static final int content = 0x7f040000;
	}

	public static final class layout
	{
		public static final int some_layout = 0x7f030000;
	}
}
