package org.local.test;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.joda.time.format.ISODateTimeFormat;
import org.junit.Test;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class JodaTimeTests
{	
//	@Test
	public void test1_localToUtc() throws ParseException
	{
		DateTime localTime = DateTimeFormat
				.forPattern("yyyy-MM-dd HH:mm:ss.SSS")
//				.withZone(DateTimeZone.forID("Europe/Berlin"))
				.withZone(DateTimeZone.UTC)
				.parseDateTime("1970-01-01 01:00:00.000");
		System.out.println("UTC time stamp: " + localTime.getMillis());

		Date date = DateUtils.parseDate("1970-01-01 01:00:00",Locale.ENGLISH, new String[]{"yyyy-MM-dd' 'HH:mm:ss"});
		System.out.println(date.getTime());
	}
	
//	@Test
	public void test2_localToUtc()
	{
		DateTime time = DateTimeFormat
				.forPattern("yyyy-MM-dd HH:mm:ss.SSS")
				.withZone(DateTimeZone.forID("Europe/Berlin"))
				.parseDateTime("1970-01-01 12:58:24.000");
		
		
		
		System.out.println(time.getMillis());
		System.out.println(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS").print(time));
	}
	
//	@Test
	public void test3_utcToLocal()
	{
		DateTime time =  new DateTime(43104000
				);
//				,DateTimeZone.forID("Europe/Berlin"));
//				,DateTimeZone.UTC);
		
		System.out.println(DateTimeFormat
				.forPattern("yyyy-MM-dd HH:mm:ss.SSS")
//				.withZone(DateTimeZone.forID("Europe/Berlin"))
				.print(time));
	}
	
//	@Test
	public void test4_formatFromTimestamp()
	{
//		Locale.setDefault(Locale.CHINESE);
//		Locale.setDefault(Locale.US);
//		Locale.setDefault(Locale.ENGLISH);
		DateTime time = new DateTime(51960 * 1000L);

		String result = DateTimeFormat.forPattern("h:mm a").print(time);
		
		System.out.println(result);
	}
	
	@Test
	public void test5_localDateTimeFormat()
	{
//		Locale.setDefault(Locale.CHINESE);
		Locale.setDefault(Locale.KOREA);
//		Locale.setDefault(Locale.US);
//		Locale.setDefault(Locale.ENGLISH);
		String dateStyle = "M-";
		String timeStyle = "-M";
		String dateTimeStyle = "MM";
		String style = "-S";
		
		String pattern = DateTimeFormat.patternForStyle(style, Locale.getDefault());
		boolean is24HourView = pattern.contains("a");
		DateTime time = new DateTime(/*0/**//*System.currentTimeMillis()/**/51960*1000L/**/);
		String result = DateTimeFormat
				.forStyle(style)
//				.forPattern(pattern)
//				.patternForStyle(style, Locale.getDefault())
//				.withLocale(Locale.getDefault())
				.print(time);
		System.out.println(pattern);
		System.out.println(result);
		System.out.println(is24HourView);
	}

}
