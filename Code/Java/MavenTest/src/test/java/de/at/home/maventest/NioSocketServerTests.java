package de.at.home.maventest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang.ArrayUtils;
import org.junit.Test;

import javax.swing.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;

public class NioSocketServerTests
{
	// Quelle: http://www.javaworld.com/article/2853780/core-java/socket-programming-for-scalable-systems.html?page=2

	public final static String BODY =
			"<html>\n"
			+"\t<head></head><body bgcolor=\"yellow\"></body></html>"
			;

	public final static String ANSWER =
			"HTTP/1.1 200 OK\n"
			+"Date: Mon, 27 Jul 2009 12:28:53 GMT\n"
			+"Server: Apache/2.2.14 (Win32)\n"
			+"Last-Modified: Wed, 22 Jul 2009 19:15:56 GMT\n"
			+"Content-Length: "+String.valueOf(BODY.getBytes(StandardCharsets.UTF_8).length)+"\n"
			+"Content-Type: text/html\n\n" + BODY
 			;

	@Test
	public void simpleHttpServerTest() throws Exception
	{
		AsynchronousServerSocketChannel listener = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(4444));

		// Listen for a new request
		listener.accept(null, new IncommingConnectionHandler(listener));

		JOptionPane.showMessageDialog(null,"stop server");
	}

	public class IncommingConnectionHandler implements CompletionHandler<AsynchronousSocketChannel, Void>
	{
		private AsynchronousServerSocketChannel listener;

		public IncommingConnectionHandler(AsynchronousServerSocketChannel listener)
		{
			this.listener = listener;
		}

		@Override
		public void completed(AsynchronousSocketChannel ch, Void attachment)
		{
			// Accept the next connection
			this.listener.accept(null, this);

			AsyncByteConsumer asyncByteConsumer = new AsyncByteConsumer(ch);

			// Allocate a byte buffer (4K) to read from the client
//			ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
//			ByteBuffer byteBuffer = ByteBuffer.allocate(355);
			ByteBuffer byteBuffer = ByteBuffer.allocate(100);

			// read füllt den Puffer mit eingehenden Bytes vom Client
			ch.read(byteBuffer, null, new IncommingBytesHandler(ch, byteBuffer, asyncByteConsumer));
		}

		@Override
		public void failed(Throwable exc, Void attachment)
		{
			throw new RuntimeException(exc);
		}
	}

	public class IncommingBytesHandler implements CompletionHandler<Integer, Object>
	{
		private AsynchronousSocketChannel ch;
		private ByteBuffer byteBuffer;
		private AsyncByteConsumer asyncByteConsumer;

		public IncommingBytesHandler(AsynchronousSocketChannel ch, ByteBuffer byteBuffer, AsyncByteConsumer asyncByteConsumer)
		{
			// todo um diese Objekte zu übergeben, könnte man auch den attachment-Parameter verwenden
			this.ch = ch;
			this.byteBuffer = byteBuffer;
			this.asyncByteConsumer = asyncByteConsumer;
		}

		@Override
		public void completed(Integer bytesRead, Object attachment)
		{
			// Make the buffer ready to read
			this.byteBuffer.flip();

			// read the buffer
			this.asyncByteConsumer.onBytesReceived(ArrayUtils.subarray(this.byteBuffer.array(), 0, bytesRead));

			// Make the buffer ready to write
			this.byteBuffer.clear();

			// Diese Prüfung geschieht erst nach dem Auslesen des Puffers, denn auch wenn bytesRead = -1 ist,
			// wurde dennoch der gesamte Puffer gefüllt. Die Situation, dass bytesRead = -1 ist, tritt ein,
			// wenn die Anzahl der lesbaren Bytes im Stream restlos durch die Größe des Puffers teilbar ist.
			if (bytesRead == -1 || bytesRead < this.byteBuffer.capacity())
			{
				this.asyncByteConsumer.onComplete();
				return;
			}

			this.ch.read(this.byteBuffer, null, this);
		}

		@Override
		public void failed(Throwable exc, Object attachment)
		{
			throw new RuntimeException(exc);
		}
	}

	public class AsyncByteConsumer
	{
		private ByteArrayOutputStream content = new ByteArrayOutputStream();
		private AsynchronousSocketChannel ch;

		public AsyncByteConsumer(AsynchronousSocketChannel ch)
		{
			this.ch = ch;
		}

		void onBytesReceived(byte[] bytes)
		{
			try
			{
				content.write(bytes);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		void onComplete()
		{
			try
			{
				JOptionPane.showMessageDialog(null,Thread.currentThread().getName());
				System.out.println(this.content.toString(StandardCharsets.UTF_8.displayName()));

				InputStream outgoingData = new ByteArrayInputStream(ANSWER.getBytes(StandardCharsets.UTF_8));

				(new ChannelWriter(this.ch, 100, outgoingData)).copyStream();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public class ChannelWriter
	{
		private AsynchronousSocketChannel ch;
		private ByteBuffer byteBuffer;
		private InputStream outgoingData;
		private OutgoingByteHandler outgoingByteHandler;

		public ChannelWriter(AsynchronousSocketChannel ch, int byteBufferCapacity, InputStream outgoingData)
		{
			this.ch = ch;
			this.byteBuffer = ByteBuffer.allocate(byteBufferCapacity);
			this.outgoingData = outgoingData;
			this.outgoingByteHandler = new OutgoingByteHandler(this);
		}

		public void copyStream() throws IOException
		{
			this.byteBuffer.clear();
			byte[] dataChunk = new byte[this.byteBuffer.capacity()];
			int chunkLength = outgoingData.read(dataChunk);
			if (chunkLength == -1)
			{
				IOUtils.closeQuietly(this.ch);
				return;
			}
			this.byteBuffer.put(dataChunk, 0 , chunkLength);
			this.byteBuffer.flip();

			// Bytes aus dem Puffer lesen und zum Client senden
			this.ch.write(this.byteBuffer, null, this.outgoingByteHandler);
		}
	}

	public class OutgoingByteHandler implements CompletionHandler<Integer, Object>
	{
		private ChannelWriter channelWriter;

		public OutgoingByteHandler(ChannelWriter channelWriter)
		{
			this.channelWriter = channelWriter;
		}

		@Override
		public void completed(Integer result, Object attachment)
		{
			try
			{
				this.channelWriter.copyStream();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		@Override
		public void failed(Throwable exc, Object attachment)
		{
			exc.printStackTrace();
		}
	}
}
