package de.at.home.maventest;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

public class MoshiTest
{
	public static final String JSON_TEXT = StringUtils.replace(
					"{" +
					"  'id': 112345678913," +
					"  'empty': []," +
					"  'flag' : true," +
					"  'nothing' : null," +
					"  'name': 'Foo'," +
					"  'price': 123," +
					"  'tags': [" +
					"    'Bar'," +
					"    'Eek'" +
					"  ]," +
					"  'stock': {" +
					"    'warehouse': 300," +
					"    'retail': 20" +
					"  }" +
					"}"
					,"'","\"");
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void moshiTest1() throws Exception
	{
		// Json laden
		JsonAdapter<Map> adapter = (new Moshi.Builder()).build().adapter(Map.class);
		Map struct = adapter.fromJson(JSON_TEXT);
		
		// Modifikation des Jsons
		struct.put("id", 123.456);
		System.out.println(adapter.toJson(struct));
		
		// Zugriff auf Listen
		List tags = (List)struct.get("tags");
		System.out.println(tags.get(0));
		
		// Map-Iteration
		for (Object entry : ((Map)struct.get("stock")).entrySet())
		{
			Map.Entry stock = (Map.Entry)entry;
			System.out.println(stock.getKey() + " = " + Math.round((Double)stock.getValue()));
		}
	}
}
