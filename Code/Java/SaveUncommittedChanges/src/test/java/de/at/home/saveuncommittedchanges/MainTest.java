package de.at.home.saveuncommittedchanges;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class MainTest
{
	public static final String CHECK_POINT_MARK = "###check_point###: ";
	
	//todo Log einbauen, mit dem man steuern kann, wie gesprächig die callProgram-Methode ist
	
//	@Test
	public void backup() throws Exception
	{
		this.backup("wichtige Sicherung");
	}
	
	private void backup(String commitMessage) throws Exception
	{
		String reference = StringUtils.substringBefore(callProgram("git status"), "\n");
		reference = StringUtils.substringAfterLast(reference, " ");
		String commitId = callProgram("git rev-parse HEAD");
		
		callProgram("git checkout " + commitId);
		callProgram("git add -A");
		callProgram(new String[]{"git", "commit", "-a", "-m", CHECK_POINT_MARK + commitMessage});
		
		System.out.println("check point id is: " + callProgram("git rev-parse HEAD"));
		
		callProgram("git checkout " + reference);
		
//		TODO: Das geht auch einfacher
//		# Variante 1
//		git add -A
//		git commit -m "check_point"
//		# --mixed sorgt dafür, dass der Dateisystemzustand des aktuellen Commits erhalten bleibt (der Parent wird nach der Verschiebung nicht ausgecheckt)
//		git reset HEAD~1 --mixed # verschiebt die aktuelle Zweigmarkierung zum direken Parent das aktuellen Commits
//		git reset --hard # löscht alle lokalen Änderungen an beobachteten Dateien
//		git clean -f -d # unbeobachtete Dateien löschen
//
//		# Variante 2
//		git add -A
//		git commit -m "check_point"
//		# --hard sorgt dafür, dass der Dateisystemzustand des Parents hergestellt wird (der Parent wird nach der Verschiebung ausgecheckt)
//		git reset HEAD~1 --hard # verschiebt die aktuelle Zweigmarkierung zum direken Parent das aktuellen Commits
//		
//		Bei Wiederherstellung, würde man den Checkpoint-Commit auschecken und den mixed-Befehl ausführen
	}
	
	@Test
	public void show() throws Exception
	{
		// todo Java 1.8 Stream-Filter einsetzen
		String refLog = callProgram("git reflog show -n 1000");
		List<String> refLogs = Arrays.asList(StringUtils.split(refLog, '\n'));
		
		for (String line : refLogs)
		{
			if (!line.contains(CHECK_POINT_MARK)) continue;
			System.out.println(line.replaceAll(" HEAD@\\{.*\\}: commit: " + CHECK_POINT_MARK, ": "));
		}
	}
	
//	@Test
	public void restore() throws Exception
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
	
	private String callProgram(String[] command) throws Exception
	{
		String[] envp = { "LANG=en_US.UTF-8" };
		Process p = Runtime.getRuntime().exec(command, envp);
		p.waitFor();
		String result = IOUtils.toString(p.getInputStream(), StandardCharsets.UTF_8);
		System.out.println(Arrays.toString(command));
		System.out.println(result);
		System.out.println(IOUtils.toString(p.getErrorStream(), StandardCharsets.UTF_8));
		return StringUtils.replace(result, "\r", "");
	}

}
