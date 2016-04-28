package de.at.home;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.http.fileupload.FileItemIterator;
import org.apache.tomcat.util.http.fileupload.FileItemStream;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FileUploadController 
{
	@Autowired
	private StatusService statusService;

	@RequestMapping("/status")
	public @ResponseBody ResponseEntity<String> status()
	{
		// Json laden
		//JsonAdapter<Map> adapter = (new Moshi.Builder()).build().adapter(Map.class);
		//String result = adapter.toJson(this.statusService.getUploads());

		return new ResponseEntity<String>(this.statusService.toString(), HttpStatus.OK);
	}

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
				// todo: darauf hinweisen, dass das mit einer Datei nicht geklappt hat
				if (!this.statusService.addUploadWhenNotExistent(item.getName(), request.getContentLength())) continue;

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
		UploadStatus uploadStatus = this.statusService.getUploads().get(item.getName());

		// hier nicht FileUtils.copyInputStreamToFile(source, destination); benutzen, weil auf 2 GB begrenzt
		InputStream inStream = null;
		OutputStream outStream = null;
		try
		{
			inStream = item.openStream();
			outStream = new FileOutputStream(item.getName());
			this.copyLarge(inStream, outStream, uploadStatus);
		}
		finally
		{
			IOUtils.closeQuietly(outStream);
			IOUtils.closeQuietly(inStream);
			uploadStatus.isRunning(false);
		}
	}

	private long copyLarge(InputStream input, OutputStream output, UploadStatus uploadStatus) throws IOException
	{
		byte[] buffer = new byte[1024 * 4];
		long count = 0;
		int n = 0;
		while (-1 != (n = input.read(buffer)))
		{
			output.write(buffer, 0, n);
			count += n;
			uploadStatus.setCountOfWrittenBytes(count);
		}
		return count;
	}

}
