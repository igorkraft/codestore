package org.test.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
// vorgegebener Kontext
//@ContextConfiguration(locations = {"/spring-context.xml"})
// Standardkontext wird hier gesucht: /src/test/resources/org/test/spring/ExampleTest-context.xml
@ContextConfiguration
public class ExampleTest 
{
	@Autowired @Qualifier("str")
	private String testStr;
	
	@Test
	public void test()
	{
		System.out.println(this.testStr);
	}
}
