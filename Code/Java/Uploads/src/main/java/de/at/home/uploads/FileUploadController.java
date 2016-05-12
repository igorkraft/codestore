package de.at.home.uploads;

import com.squareup.moshi.Moshi;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.http.fileupload.FileItemIterator;
import org.apache.tomcat.util.http.fileupload.FileItemStream;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.*;

@Controller
public class FileUploadController implements InitializingBean
{
	@Value("${upload.root.directory:${user.dir}}")
	private File uploadRoot;

	@Autowired
	private StatusService statusService;

	@RequestMapping(value = "/status", produces = "application/json")
	public @ResponseBody ResponseEntity<String> status()
	{
		this.statusService.updateCurrentTime();
		String result = ((new Moshi.Builder()).build().adapter(StatusService.class)).toJson(this.statusService);
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}

	@RequestMapping(value = "/reset")
	public @ResponseBody ResponseEntity<String> reset()
	{
		this.statusService.reset();
		return new ResponseEntity<String>("Success", HttpStatus.OK);
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
		catch (Exception e)
		{
			return new ResponseEntity<String>("Internal server IO error", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		// TODO: Status zurück schicken
		return new ResponseEntity<String>("Success", HttpStatus.OK);
	}
	
	private void writeToFile(FileItemStream item)
	{
		UploadStatus uploadStatus = this.statusService.getUploads().get(item.getName());

		// hier nicht FileUtils.copyInputStreamToFile(source, destination); benutzen, weil auf 2 GB begrenzt
		InputStream inStream = null;
		OutputStream outStream = null;
		try
		{
			inStream = item.openStream();
			outStream = new FileOutputStream(new File(this.uploadRoot, item.getName()));
			this.copyLarge(inStream, outStream, uploadStatus);
			uploadStatus.isRunning(false);
		}
		catch (Exception e)
		{
			//todo angemessen reagieren + Status failed setzen + erneuten Versuch ermöglichen
		}
		finally
		{
			IOUtils.closeQuietly(outStream);
			IOUtils.closeQuietly(inStream);
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

	@Override
	public void afterPropertiesSet() throws Exception
	{
		if (!this.uploadRoot.exists()) throw new Exception("Please configure a valid 'upload.root.directory'.");
		String uploadFolder = DateTimeFormat.forPattern("yyyyMMdd_HHmmss").print(new DateTime());
		this.uploadRoot = new File(this.uploadRoot, uploadFolder);
		this.uploadRoot.mkdir();
	}
}
