package de.at.home.saveuncommittedchanges;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Main
{
	public static void main(String[] args) throws ParseException
	{
		// todo Projekt umbenennen
		CommandLine line;
		try
		{
			line = (new DefaultParser()).parse(createOptions(), args);
		}
		catch (Exception exp)
		{
			System.err.println("Command line parsing failed. Reason: " + exp.getMessage());
			(new HelpFormatter()).printHelp("gitpot", createOptions());
			return;
		}
		
		if (line.hasOption("rescue"))
		{
			String commitMessage = line.getOptionValue("rescue");
			
			if (commitMessage == null)
			{
				commitMessage = "check point " /*todo insert current time here*/;
			}
		}
		
		if (line.hasOption("pot"))
		{
			
		}
		
		if (line.hasOption("recover"))
		{
			
		}
		
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
