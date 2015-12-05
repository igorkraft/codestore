package de.at.home;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.http.fileupload.FileItemIterator;
import org.apache.tomcat.util.http.fileupload.FileItemStream;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FileUploadController 
{
	@RequestMapping(value="/upstream", method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> upstream(HttpServletRequest request) 
	{
		try 
		{
			ServletFileUpload upload = new ServletFileUpload();

			FileItemIterator iter = upload.getItemIterator(request);
			while (iter.hasNext()) 
			{
				FileItemStream item = iter.next();
				
				if (item.isFormField()) continue;
				
				this.writeToFile(item);
			}
		} 
		catch (FileUploadException e) 
		{
			return new ResponseEntity<String>("File upload error", HttpStatus.INTERNAL_SERVER_ERROR);
		} 
		catch (IOException e) 
		{
			return new ResponseEntity<String>("Internal server IO error", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<String>("Success", HttpStatus.OK);
	}
	
	private void writeToFile(FileItemStream item) throws IOException
	{
		// hier nicht FileUtils.copyInputStreamToFile(source, destination); benutzen, weil auf 2 GB begrenzt
		InputStream inStream = null;
		OutputStream outStream = null;
		try
		{
			inStream = item.openStream();
			outStream = new FileOutputStream(item.getName());
			IOUtils.copyLarge(inStream, outStream);
		}
		finally
		{
			IOUtils.closeQuietly(outStream);
			IOUtils.closeQuietly(inStream);
		}
	}
}
