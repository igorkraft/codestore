package org.local.test;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import org.junit.Test;

public class JavaCompilerTests
{
	@Test
	public void compilerTest()
	{
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
	}
}
