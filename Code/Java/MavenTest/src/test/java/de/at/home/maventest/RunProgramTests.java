package de.at.home.maventest;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

public class RunProgramTests
{
    @Test
    public void simpleCallTest() throws Exception
    {
        Process process = Runtime.getRuntime().exec("netstat -a -n -o");

        String output = IOUtils.toString(process.getInputStream(), StandardCharsets.UTF_8);
        String errors = IOUtils.toString(process.getErrorStream(), StandardCharsets.UTF_8);

        process.waitFor();

        IOUtils.closeQuietly(process.getInputStream());
        IOUtils.closeQuietly(process.getErrorStream());

        System.out.println(output);
        System.out.println(errors);
    }
}
