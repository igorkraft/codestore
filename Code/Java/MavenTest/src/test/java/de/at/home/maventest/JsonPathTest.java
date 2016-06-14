package de.at.home.maventest;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class JsonPathTest
{
	public static final String JSON_TEXT = "{\"store\": {\"book\": [{\"category\": \"reference\",\"author\": \"Nigel Rees\",\"title\": \"Sayings of the Century\",\"price\": 8.95},{\"category\": \"fiction\",\"author\": \"Evelyn Waugh\",\"title\": \"Sword of Honour\",\"price\": 12.99},{\"category\": \"fiction\",\"author\": \"Herman Melville\",\"title\": \"Moby Dick\",\"isbn\": \"0-553-21311-3\",\"price\": 8.99},{\"category\": \"fiction\",\"author\": \"J. R. R. Tolkien\",\"title\": \"The Lord of the Rings\",\"isbn\": \"0-395-19395-8\",\"price\": 22.99}],\"bicycle\": {\"color\": \"red\",\"price\": 19.95}},\"expensive\": 10}";

	@SuppressWarnings({ "unused" })
	@Test
	public void jsonPathTest1() throws Exception
	{
		// hier kann man auch eine von Moshi erzeugte Map übergeben
		DocumentContext context = JsonPath.parse(JSON_TEXT);

		String firstAuthor = context.read("$.store.book[0].author");
		List<String> authorsOfBooksWithISBN = context.read("$.store.book[?(@.isbn)].author");
		List<Map<String, Object>> expensiveBooks = context.read("$.store.book[?(@.price > 10)]", List.class);
		List<String> allAuthors = context.read("$..author");
		List<Object> everyThing = context.read("$..*");

		System.out.println(allAuthors);
	}

}
