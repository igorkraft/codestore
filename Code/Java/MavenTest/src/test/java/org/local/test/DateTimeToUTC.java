package org.local.test;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;

public class DateTimeToUTC
{
	public static void main(String[] args)
	{
//		DateTime localTime = new DateTime(1970,1,1,0,0,0,0,DateTimeZone.forID("Europe/Berlin"));
		
		DateTime localTime = DateTimeFormat
				.forPattern("yyyy-MM-dd HH:mm:ss.SSS")
				.withZone(DateTimeZone.forID("Europe/Berlin"))
				.parseDateTime("1970-01-01 00:00:00.000");
		
		DateTime utcTime = localTime.withZoneRetainFields(DateTimeZone.forID("UTC"));
		
		System.out.println("UTC time stamp:   " + utcTime.getMillis());
		System.out.println("local time stamp: " + localTime.getMillis());
	}
}
