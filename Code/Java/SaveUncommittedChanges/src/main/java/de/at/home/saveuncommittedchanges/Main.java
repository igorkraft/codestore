package de.at.home.saveuncommittedchanges;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class Main
{
	public static final String CHECK_POINT_MARK = "###check_point###: ";
	
	//todo Log einbauen, mit dem man steuern kann, wie gesprächig die callProgram-Methode ist

	public static void main(String[] args) throws Exception
	{
		// todo Projekt umbenennen
		CommandLine line;
		try
		{
			if (!args[0].startsWith("--")) args[0] = "--" + args[0];
			line = (new DefaultParser()).parse(createOptions(), args);
		}
		catch (Exception exp)
		{
			System.err.println("Command line parsing failed. Reason: " + exp.getMessage());
			(new HelpFormatter()).printHelp("gitpot", createOptions());
			return;
		}
		
		if (line.hasOption("rescue"))  rescue(line.getOptionValue("rescue"));
		if (line.hasOption("pot"))     pot();
		if (line.hasOption("recover")) recover(line.getOptionValue("recover"));
	}
	
	private static void rescue(String commitMessage) throws Exception
	{
		if (commitMessage == null)
		{
			commitMessage = "check point " + DateTimeFormat.forStyle("MM").print(new DateTime());
		}
		
		callProgram("git add -A");
		callProgram(new String[]{"git", "commit", "-m", CHECK_POINT_MARK + commitMessage});
		
		System.out.println(callProgram("git rev-parse HEAD"));
		
//	verschiebt die aktuelle Zweigmarkierung zum direken Parent das aktuellen Commits
//	--hard sorgt dafür, dass der Dateisystemzustand des Parents hergestellt wird (der Parent wird nach der Verschiebung ausgecheckt)
		callProgram("git reset HEAD~1 --hard");
	}
	
	private static void pot() throws Exception
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
	
	private static void recover(String commitId)
	{
		
	}
	
	private static String callProgram(String command) throws Exception
	{
		String[] envp = { "LANG=en_US.UTF-8" };
		Process p = Runtime.getRuntime().exec(command, envp);
		p.waitFor();
		String result = IOUtils.toString(p.getInputStream(), StandardCharsets.UTF_8);
//		System.out.println(command);
//		System.out.println(result);
		System.out.println(IOUtils.toString(p.getErrorStream(), StandardCharsets.UTF_8));
		return StringUtils.replace(result, "\r", "");
	}
	
	private static String callProgram(String[] command) throws Exception
	{
		String[] envp = { "LANG=en_US.UTF-8" };
		Process p = Runtime.getRuntime().exec(command, envp);
		p.waitFor();
		String result = IOUtils.toString(p.getInputStream(), StandardCharsets.UTF_8);
//		System.out.println(Arrays.toString(command));
//		System.out.println(result);
		System.out.println(IOUtils.toString(p.getErrorStream(), StandardCharsets.UTF_8));
		return StringUtils.replace(result, "\r", "");
	}
	
	private static Options createOptions()
	{
		Options result = new Options();
		
		OptionGroup options = new OptionGroup();
		options.setRequired(true);
		options.addOption(Option.builder().longOpt("rescue").hasArg(true).optionalArg(true).build());
		options.addOption(Option.builder().longOpt("pot").hasArg(false).build());
		options.addOption(Option.builder().longOpt("recover").hasArg(true).build());
		result.addOptionGroup(options);
		
		return result;
	}
}
