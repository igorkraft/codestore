package de.at.home.saveuncommittedchanges;

import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class MainTest
{
	@Test
	public void backup() throws Exception
	{
		// todo commit message variabel machen (Sicherungsvermerk ###check_point###)
		String reference = StringUtils.substringBefore(callProgram("git status"), "\n");
		reference = StringUtils.substringAfterLast(reference, " ");
		String commitId = callProgram("git rev-parse HEAD");
		
		System.out.println(callProgram("git checkout " + commitId));
		System.out.println(callProgram("git add -A"));
		// todo prüfen, warum in der commit message keine Leerzeichen enthalten sein dürfen
		// die escapten Gänsefüßchen werden als Zeichen mit in die commit message übernommen
		System.out.println(callProgram("git commit -a -m \"check_point\""));
		
		System.out.println("check point id is: " + callProgram("git rev-parse HEAD"));
		
		callProgram("git checkout " + reference);
	}
	
//	@Test
	public void show()
	{
		//git reflog show -n 1000
	}
	
//	@Test
	public void restore()
	{
		
	}
	
	private String callProgram(String command) throws Exception
	{
		String[] envp = { "LANG=en_US.UTF-8" };
		Process p = Runtime.getRuntime().exec(command, envp);
		p.waitFor();
		String result = IOUtils.toString(p.getInputStream(), StandardCharsets.UTF_8);
		System.out.println(command);
		System.out.println(result);
		System.out.println(IOUtils.toString(p.getErrorStream(), StandardCharsets.UTF_8));
		return StringUtils.replace(result, "\r", "");
	}
}
