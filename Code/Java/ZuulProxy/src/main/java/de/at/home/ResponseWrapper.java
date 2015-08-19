package de.at.home;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.netflix.zuul.http.HttpServletResponseWrapper;

public class ResponseWrapper extends HttpServletResponseWrapper
{
	private final ByteArrayOutputStream capture;
	private ServletOutputStream output;
	private PrintWriter writer;
	private ResponseModifier responseModifier;

	public ResponseWrapper(HttpServletResponse response, ResponseModifier responseModifier)
	{
		super(response);
		this.capture = new ByteArrayOutputStream(response.getBufferSize());
		this.responseModifier = responseModifier;
	}

	@Override
	public ServletOutputStream getOutputStream()
	{
		if (this.writer != null) throw new IllegalStateException("getWriter() has already been called on this response.");
	
		if (this.output == null) this.output = new CaptureOutputStream();
		
		return this.output;
	}

	@Override
	public PrintWriter getWriter() throws IOException
	{
		if (this.output != null) throw new IllegalStateException("getOutputStream() has already been called on this response.");
	
		if (this.writer == null) this.writer = new PrintWriter(new OutputStreamWriter(this.capture, getCharacterEncoding()));
	
		return this.writer;
	}

	@Override
	public void flushBuffer() throws IOException
	{
		super.flushBuffer();
	
		if (this.writer != null) this.writer.flush();
		if (this.output != null) this.output.flush();
	}

	public String getCaptureAsString() throws UnsupportedEncodingException
	{
		if (this.writer != null) IOUtils.closeQuietly(this.writer);
		if (this.output != null) IOUtils.closeQuietly(this.output);
		
		return new String(this.capture.toByteArray(), getCharacterEncoding());
	}
	
	public ResponseModifier getResponseModifier()
	{
		return responseModifier;
	}
	
	// inner classes #######################################################################

	private class CaptureOutputStream extends ServletOutputStream
	{
		@Override
		public void write(int b) throws IOException
		{
			ResponseWrapper.this.capture.write(b);
		}

		@Override
		public void flush() throws IOException
		{
			ResponseWrapper.this.capture.flush();
		}

		@Override
		public void close() throws IOException
		{
			ResponseWrapper.this.capture.close();
		}

		@Override
		public boolean isReady()
		{
			return false;
		}

		@Override
		public void setWriteListener(WriteListener arg0)
		{
		}
	}
}