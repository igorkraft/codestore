package de.at.home.androidhelper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

public class AppDataBackup
{
	public static String getAsBase64(File appDataDir) throws IOException
	{
		Collection<File> list = AppDataBackup.listFiles(appDataDir);
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		ZipOutputStream zipOut = new ZipOutputStream(byteOut);
		for (File file : list) 
		{
			String entryPath = StringUtils.substringAfter(file.getAbsolutePath() ,appDataDir.getAbsolutePath());
			zipOut.putNextEntry(new ZipEntry(StringUtils.right(entryPath,entryPath.length()-1)));
			FileUtils.copyFile(file, zipOut);
		}
		IOUtils.closeQuietly(zipOut);
		IOUtils.closeQuietly(byteOut);
		return Base64InnerEncoder.encodeBytes(byteOut.toByteArray());
	}
	
	public static void backupToSdCard(File appDataDir) throws IOException
	{
		String timeStamp = DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMdd-HHmmss");
		File destination = new File("/sdcard/backup/" + timeStamp);
		destination.mkdirs();
		FileUtils.copyDirectoryToDirectory(appDataDir, destination);
	}
	
	private static Collection<File> listFiles(File file)
	{
		if (file == null || !file.exists()) return new ArrayList<>();
		if (file.isFile()) return Arrays.asList(new File[]{file});
		return FileUtils.listFiles(file,null,true);
	}
	
	private static class Base64InnerEncoder
	{
		public final static int NO_OPTIONS = 0;
		public final static int ENCODE = 1;
		public final static int GZIP = 2;
		public final static int DO_BREAK_LINES = 8;
		public final static int URL_SAFE = 16;
		public final static int ORDERED = 32;
		private final static int MAX_LINE_LENGTH = 76;
		private final static byte EQUALS_SIGN = (byte) '=';
		private final static byte NEW_LINE = (byte) '\n';
		private final static String PREFERRED_ENCODING = "US-ASCII";
		private final static byte WHITE_SPACE_ENC = -5; // Indicates white space in encoding
		private final static byte[] _STANDARD_ALPHABET = {
				(byte) 'A', (byte) 'B', (byte) 'C', (byte) 'D', (byte) 'E', (byte) 'F', (byte) 'G',
				(byte) 'H', (byte) 'I', (byte) 'J', (byte) 'K', (byte) 'L', (byte) 'M', (byte) 'N',
				(byte) 'O', (byte) 'P', (byte) 'Q', (byte) 'R', (byte) 'S', (byte) 'T', (byte) 'U',
				(byte) 'V', (byte) 'W', (byte) 'X', (byte) 'Y', (byte) 'Z',
				(byte) 'a', (byte) 'b', (byte) 'c', (byte) 'd', (byte) 'e', (byte) 'f', (byte) 'g',
				(byte) 'h', (byte) 'i', (byte) 'j', (byte) 'k', (byte) 'l', (byte) 'm', (byte) 'n',
				(byte) 'o', (byte) 'p', (byte) 'q', (byte) 'r', (byte) 's', (byte) 't', (byte) 'u',
				(byte) 'v', (byte) 'w', (byte) 'x', (byte) 'y', (byte) 'z',
				(byte) '0', (byte) '1', (byte) '2', (byte) '3', (byte) '4', (byte) '5',
				(byte) '6', (byte) '7', (byte) '8', (byte) '9', (byte) '+', (byte) '/'
		};
		private final static byte[] _STANDARD_DECODABET = {
				-9, -9, -9, -9, -9, -9, -9, -9, -9,				 // Decimal  0 -  8
				-5, -5,									  // Whitespace: Tab and Linefeed
				-9, -9,									  // Decimal 11 - 12
				-5,										 // Whitespace: Carriage Return
				-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,	 // Decimal 14 - 26
				-9, -9, -9, -9, -9,							 // Decimal 27 - 31
				-5,										 // Whitespace: Space
				-9, -9, -9, -9, -9, -9, -9, -9, -9, -9,			  // Decimal 33 - 42
				62,										 // Plus sign at decimal 43
				-9, -9, -9,								   // Decimal 44 - 46
				63,										 // Slash at decimal 47
				52, 53, 54, 55, 56, 57, 58, 59, 60, 61,			  // Numbers zero through nine
				-9, -9, -9,								   // Decimal 58 - 60
				-1,										 // Equals sign at decimal 61
				-9, -9, -9,									  // Decimal 62 - 64
				0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13,			// Letters 'A' through 'N'
				14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25,		// Letters 'O' through 'Z'
				-9, -9, -9, -9, -9, -9,						  // Decimal 91 - 96
				26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38,	 // Letters 'a' through 'm'
				39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51,	 // Letters 'n' through 'z'
				-9, -9, -9, -9, -9							  // Decimal 123 - 127
				, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,	   // Decimal 128 - 139
				-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,	 // Decimal 140 - 152
				-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,	 // Decimal 153 - 165
				-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,	 // Decimal 166 - 178
				-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,	 // Decimal 179 - 191
				-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,	 // Decimal 192 - 204
				-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,	 // Decimal 205 - 217
				-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,	 // Decimal 218 - 230
				-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,	 // Decimal 231 - 243
				-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9		 // Decimal 244 - 255
		};
		private final static byte[] _URL_SAFE_ALPHABET = {
				(byte) 'A', (byte) 'B', (byte) 'C', (byte) 'D', (byte) 'E', (byte) 'F', (byte) 'G',
				(byte) 'H', (byte) 'I', (byte) 'J', (byte) 'K', (byte) 'L', (byte) 'M', (byte) 'N',
				(byte) 'O', (byte) 'P', (byte) 'Q', (byte) 'R', (byte) 'S', (byte) 'T', (byte) 'U',
				(byte) 'V', (byte) 'W', (byte) 'X', (byte) 'Y', (byte) 'Z',
				(byte) 'a', (byte) 'b', (byte) 'c', (byte) 'd', (byte) 'e', (byte) 'f', (byte) 'g',
				(byte) 'h', (byte) 'i', (byte) 'j', (byte) 'k', (byte) 'l', (byte) 'm', (byte) 'n',
				(byte) 'o', (byte) 'p', (byte) 'q', (byte) 'r', (byte) 's', (byte) 't', (byte) 'u',
				(byte) 'v', (byte) 'w', (byte) 'x', (byte) 'y', (byte) 'z',
				(byte) '0', (byte) '1', (byte) '2', (byte) '3', (byte) '4', (byte) '5',
				(byte) '6', (byte) '7', (byte) '8', (byte) '9', (byte) '-', (byte) '_'
		};
		private final static byte[] _URL_SAFE_DECODABET = {
				-9, -9, -9, -9, -9, -9, -9, -9, -9,				 // Decimal  0 -  8
				-5, -5,									  // Whitespace: Tab and Linefeed
				-9, -9,									  // Decimal 11 - 12
				-5,										 // Whitespace: Carriage Return
				-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,	 // Decimal 14 - 26
				-9, -9, -9, -9, -9,							 // Decimal 27 - 31
				-5,										 // Whitespace: Space
				-9, -9, -9, -9, -9, -9, -9, -9, -9, -9,			  // Decimal 33 - 42
				-9,										 // Plus sign at decimal 43
				-9,										 // Decimal 44
				62,										 // Minus sign at decimal 45
				-9,										 // Decimal 46
				-9,										 // Slash at decimal 47
				52, 53, 54, 55, 56, 57, 58, 59, 60, 61,			  // Numbers zero through nine
				-9, -9, -9,								   // Decimal 58 - 60
				-1,										 // Equals sign at decimal 61
				-9, -9, -9,								   // Decimal 62 - 64
				0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13,			// Letters 'A' through 'N'
				14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25,		// Letters 'O' through 'Z'
				-9, -9, -9, -9,								// Decimal 91 - 94
				63,										 // Underscore at decimal 95
				-9,										 // Decimal 96
				26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38,	 // Letters 'a' through 'm'
				39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51,	 // Letters 'n' through 'z'
				-9, -9, -9, -9, -9							  // Decimal 123 - 127
				, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,	 // Decimal 128 - 139
				-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,	 // Decimal 140 - 152
				-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,	 // Decimal 153 - 165
				-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,	 // Decimal 166 - 178
				-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,	 // Decimal 179 - 191
				-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,	 // Decimal 192 - 204
				-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,	 // Decimal 205 - 217
				-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,	 // Decimal 218 - 230
				-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,	 // Decimal 231 - 243
				-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9		 // Decimal 244 - 255
		};

		private final static byte[] _ORDERED_ALPHABET = {
				(byte) '-',
				(byte) '0', (byte) '1', (byte) '2', (byte) '3', (byte) '4',
				(byte) '5', (byte) '6', (byte) '7', (byte) '8', (byte) '9',
				(byte) 'A', (byte) 'B', (byte) 'C', (byte) 'D', (byte) 'E', (byte) 'F', (byte) 'G',
				(byte) 'H', (byte) 'I', (byte) 'J', (byte) 'K', (byte) 'L', (byte) 'M', (byte) 'N',
				(byte) 'O', (byte) 'P', (byte) 'Q', (byte) 'R', (byte) 'S', (byte) 'T', (byte) 'U',
				(byte) 'V', (byte) 'W', (byte) 'X', (byte) 'Y', (byte) 'Z',
				(byte) '_',
				(byte) 'a', (byte) 'b', (byte) 'c', (byte) 'd', (byte) 'e', (byte) 'f', (byte) 'g',
				(byte) 'h', (byte) 'i', (byte) 'j', (byte) 'k', (byte) 'l', (byte) 'm', (byte) 'n',
				(byte) 'o', (byte) 'p', (byte) 'q', (byte) 'r', (byte) 's', (byte) 't', (byte) 'u',
				(byte) 'v', (byte) 'w', (byte) 'x', (byte) 'y', (byte) 'z'
		};
		private final static byte[] _ORDERED_DECODABET = {
				-9, -9, -9, -9, -9, -9, -9, -9, -9,				 // Decimal  0 -  8
				-5, -5,									  // Whitespace: Tab and Linefeed
				-9, -9,									  // Decimal 11 - 12
				-5,										 // Whitespace: Carriage Return
				-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,	 // Decimal 14 - 26
				-9, -9, -9, -9, -9,							 // Decimal 27 - 31
				-5,										 // Whitespace: Space
				-9, -9, -9, -9, -9, -9, -9, -9, -9, -9,			  // Decimal 33 - 42
				-9,										 // Plus sign at decimal 43
				-9,										 // Decimal 44
				0,										  // Minus sign at decimal 45
				-9,										 // Decimal 46
				-9,										 // Slash at decimal 47
				1, 2, 3, 4, 5, 6, 7, 8, 9, 10,					   // Numbers zero through nine
				-9, -9, -9,								   // Decimal 58 - 60
				-1,										 // Equals sign at decimal 61
				-9, -9, -9,								   // Decimal 62 - 64
				11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23,	 // Letters 'A' through 'M'
				24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36,	 // Letters 'N' through 'Z'
				-9, -9, -9, -9,								// Decimal 91 - 94
				37,										 // Underscore at decimal 95
				-9,										 // Decimal 96
				38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50,	 // Letters 'a' through 'm'
				51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63,	 // Letters 'n' through 'z'
				-9, -9, -9, -9, -9								 // Decimal 123 - 127
				, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,	 // Decimal 128 - 139
				-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,	 // Decimal 140 - 152
				-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,	 // Decimal 153 - 165
				-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,	 // Decimal 166 - 178
				-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,	 // Decimal 179 - 191
				-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,	 // Decimal 192 - 204
				-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,	 // Decimal 205 - 217
				-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,	 // Decimal 218 - 230
				-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,	 // Decimal 231 - 243
				-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9		 // Decimal 244 - 255
		};
		private Base64InnerEncoder() {
		}
		private final static byte[] getAlphabet(int options) {
			if ((options & URL_SAFE) == URL_SAFE) {
				return _URL_SAFE_ALPHABET;
			} else if ((options & ORDERED) == ORDERED) {
				return _ORDERED_ALPHABET;
			} else {
				return _STANDARD_ALPHABET;
			}
		}	// end getAlphabet
		private final static byte[] getDecodabet(int options) {
			if ((options & URL_SAFE) == URL_SAFE) {
				return _URL_SAFE_DECODABET;
			} else if ((options & ORDERED) == ORDERED) {
				return _ORDERED_DECODABET;
			} else {
				return _STANDARD_DECODABET;
			}
		}	// end getAlphabet
		private static byte[] encode3to4(byte[] b4, byte[] threeBytes, int numSigBytes, int options) {
			encode3to4(threeBytes, 0, numSigBytes, b4, 0, options);
			return b4;
		}   // end encode3to4
		private static byte[] encode3to4(
				byte[] source, int srcOffset, int numSigBytes,
				byte[] destination, int destOffset, int options) {

			byte[] ALPHABET = getAlphabet(options);

			int inBuff = (numSigBytes > 0 ? ((source[srcOffset] << 24) >>> 8) : 0)
					| (numSigBytes > 1 ? ((source[srcOffset + 1] << 24) >>> 16) : 0)
					| (numSigBytes > 2 ? ((source[srcOffset + 2] << 24) >>> 24) : 0);

			switch (numSigBytes) {
				case 3:
					destination[destOffset] = ALPHABET[(inBuff >>> 18)];
					destination[destOffset + 1] = ALPHABET[(inBuff >>> 12) & 0x3f];
					destination[destOffset + 2] = ALPHABET[(inBuff >>> 6) & 0x3f];
					destination[destOffset + 3] = ALPHABET[(inBuff) & 0x3f];
					return destination;

				case 2:
					destination[destOffset] = ALPHABET[(inBuff >>> 18)];
					destination[destOffset + 1] = ALPHABET[(inBuff >>> 12) & 0x3f];
					destination[destOffset + 2] = ALPHABET[(inBuff >>> 6) & 0x3f];
					destination[destOffset + 3] = EQUALS_SIGN;
					return destination;

				case 1:
					destination[destOffset] = ALPHABET[(inBuff >>> 18)];
					destination[destOffset + 1] = ALPHABET[(inBuff >>> 12) & 0x3f];
					destination[destOffset + 2] = EQUALS_SIGN;
					destination[destOffset + 3] = EQUALS_SIGN;
					return destination;

				default:
					return destination;
			}   // end switch
		}   // end encode3to4

		public static String encodeBytes(byte[] source) {
			// Since we're not going to have the GZIP encoding turned on,
			// we're not going to have an java.io.IOException thrown, so
			// we should not force the user to have to catch it.
			String encoded = null;
			try {
				encoded = encodeBytes(source, 0, source.length, NO_OPTIONS);
			} catch (java.io.IOException ex) {
				assert false : ex.getMessage();
			}   // end catch
			assert encoded != null;
			return encoded;
		}   // end encodeBytes
		public static String encodeBytes(byte[] source, int off, int len, int options) throws java.io.IOException {
			byte[] encoded = encodeBytesToBytes(source, off, len, options);

			// Return value according to relevant encoding.
			try {
				return new String(encoded, PREFERRED_ENCODING);
			}   // end try
			catch (java.io.UnsupportedEncodingException uue) {
				return new String(encoded);
			}   // end catch

		}   // end encodeBytes
		public static byte[] encodeBytesToBytes(byte[] source, int off, int len, int options) throws java.io.IOException {

			if (source == null) {
				throw new NullPointerException("Cannot serialize a null array.");
			}   // end if: null

			if (off < 0) {
				throw new IllegalArgumentException("Cannot have negative offset: " + off);
			}   // end if: off < 0

			if (len < 0) {
				throw new IllegalArgumentException("Cannot have length offset: " + len);
			}   // end if: len < 0

			if (off + len > source.length) {
				throw new IllegalArgumentException(
						String.format("Cannot have offset of %d and length of %d with array of length %d", off, len, source.length));
			}   // end if: off < 0


			// Compress?
			if ((options & GZIP) != 0) {
				java.io.ByteArrayOutputStream baos = null;
				java.util.zip.GZIPOutputStream gzos = null;
				Base64InnerEncoder.OutputStream b64os = null;

				try {
					// GZip -> Base64 -> ByteArray
					baos = new java.io.ByteArrayOutputStream();
					b64os = new Base64InnerEncoder.OutputStream(baos, ENCODE | options);
					gzos = new java.util.zip.GZIPOutputStream(b64os);

					gzos.write(source, off, len);
					gzos.close();
				}   // end try
				catch (java.io.IOException e) {
					// Catch it and then throw it immediately so that
					// the finally{} block is called for cleanup.
					throw e;
				}   // end catch
				finally {
					try {
						gzos.close();
					} catch (Exception e) {
					}
					try {
						b64os.close();
					} catch (Exception e) {
					}
					try {
						baos.close();
					} catch (Exception e) {
					}
				}   // end finally

				return baos.toByteArray();
			}   // end if: compress

			// Else, don't compress. Better not to use streams at all then.
			else {
				boolean breakLines = (options & DO_BREAK_LINES) != 0;

				int encLen = (len / 3) * 4 + (len % 3 > 0 ? 4 : 0); // Bytes needed for actual encoding
				if (breakLines) {
					encLen += encLen / MAX_LINE_LENGTH; // Plus extra newline characters
				}
				byte[] outBuff = new byte[encLen];


				int d = 0;
				int e = 0;
				int len2 = len - 2;
				int lineLength = 0;
				for (; d < len2; d += 3, e += 4) {
					encode3to4(source, d + off, 3, outBuff, e, options);

					lineLength += 4;
					if (breakLines && lineLength >= MAX_LINE_LENGTH) {
						outBuff[e + 4] = NEW_LINE;
						e++;
						lineLength = 0;
					}   // end if: end of line
				}   // en dfor: each piece of array

				if (d < len) {
					encode3to4(source, d + off, len - d, outBuff, e, options);
					e += 4;
				}   // end if: some padding needed


				// Only resize array if we didn't guess it right.
				if (e <= outBuff.length - 1) {
					byte[] finalOut = new byte[e];
					System.arraycopy(outBuff, 0, finalOut, 0, e);
					//System.err.println("Having to resize array from " + outBuff.length + " to " + e );
					return finalOut;
				} else {
					//System.err.println("No need to resize array.");
					return outBuff;
				}

			}   // end else: don't compress

		}   // end encodeBytesToBytes
		private static int decode4to3(
				byte[] source, int srcOffset,
				byte[] destination, int destOffset, int options) {

			// Lots of error checking and exception throwing
			if (source == null) {
				throw new NullPointerException("Source array was null.");
			}   // end if
			if (destination == null) {
				throw new NullPointerException("Destination array was null.");
			}   // end if
			if (srcOffset < 0 || srcOffset + 3 >= source.length) {
				throw new IllegalArgumentException(String.format(
						"Source array with length %d cannot have offset of %d and still process four bytes.", source.length, srcOffset));
			}   // end if
			if (destOffset < 0 || destOffset + 2 >= destination.length) {
				throw new IllegalArgumentException(String.format(
						"Destination array with length %d cannot have offset of %d and still store three bytes.", destination.length, destOffset));
			}   // end if


			byte[] DECODABET = getDecodabet(options);

			// Example: Dk==
			if (source[srcOffset + 2] == EQUALS_SIGN) {
				int outBuff = ((DECODABET[source[srcOffset]] & 0xFF) << 18)
						| ((DECODABET[source[srcOffset + 1]] & 0xFF) << 12);

				destination[destOffset] = (byte) (outBuff >>> 16);
				return 1;
			}

			// Example: DkL=
			else if (source[srcOffset + 3] == EQUALS_SIGN) {
				int outBuff = ((DECODABET[source[srcOffset]] & 0xFF) << 18)
						| ((DECODABET[source[srcOffset + 1]] & 0xFF) << 12)
						| ((DECODABET[source[srcOffset + 2]] & 0xFF) << 6);

				destination[destOffset] = (byte) (outBuff >>> 16);
				destination[destOffset + 1] = (byte) (outBuff >>> 8);
				return 2;
			}

			// Example: DkLE
			else {
				int outBuff = ((DECODABET[source[srcOffset]] & 0xFF) << 18)
						| ((DECODABET[source[srcOffset + 1]] & 0xFF) << 12)
						| ((DECODABET[source[srcOffset + 2]] & 0xFF) << 6)
						| ((DECODABET[source[srcOffset + 3]] & 0xFF));


				destination[destOffset] = (byte) (outBuff >> 16);
				destination[destOffset + 1] = (byte) (outBuff >> 8);
				destination[destOffset + 2] = (byte) (outBuff);

				return 3;
			}
		}   // end decodeToBytes

		public static class OutputStream extends java.io.FilterOutputStream {

			private boolean encode;
			private int position;
			private byte[] buffer;
			private int bufferLength;
			private int lineLength;
			private boolean breakLines;
			private byte[] b4;		 // Scratch used in a few places
			private boolean suspendEncoding;
			private int options;	// Record for later
			private byte[] decodabet;  // Local copies to avoid extra method calls
			public OutputStream(java.io.OutputStream out, int options) {
				super(out);
				this.breakLines = (options & DO_BREAK_LINES) != 0;
				this.encode = (options & ENCODE) != 0;
				this.bufferLength = encode ? 3 : 4;
				this.buffer = new byte[bufferLength];
				this.position = 0;
				this.lineLength = 0;
				this.suspendEncoding = false;
				this.b4 = new byte[4];
				this.options = options;
				this.decodabet = getDecodabet(options);
			}   // end constructor

			@Override
			public void write(int theByte)
					throws java.io.IOException {
				// Encoding suspended?
				if (suspendEncoding) {
					this.out.write(theByte);
					return;
				}   // end if: supsended

				// Encode?
				if (encode) {
					buffer[position++] = (byte) theByte;
					if (position >= bufferLength) { // Enough to encode.

						this.out.write(encode3to4(b4, buffer, bufferLength, options));

						lineLength += 4;
						if (breakLines && lineLength >= MAX_LINE_LENGTH) {
							this.out.write(NEW_LINE);
							lineLength = 0;
						}   // end if: end of line

						position = 0;
					}   // end if: enough to output
				}   // end if: encoding

				// Else, Decoding
				else {
					// Meaningful Base64 character?
					if (decodabet[theByte & 0x7f] > WHITE_SPACE_ENC) {
						buffer[position++] = (byte) theByte;
						if (position >= bufferLength) { // Enough to output.

							int len = Base64InnerEncoder.decode4to3(buffer, 0, b4, 0, options);
							out.write(b4, 0, len);
							position = 0;
						}   // end if: enough to output
					}   // end if: meaningful base64 character
					else if (decodabet[theByte & 0x7f] != WHITE_SPACE_ENC) {
						throw new java.io.IOException("Invalid character in Base64 data.");
					}   // end else: not white space either
				}   // end else: decoding
			}   // end write

			/**
			 * Calls {@link #write(int)} repeatedly until <var>len</var>
			 * bytes are written.
			 *
			 * @param theBytes array from which to read bytes
			 * @param off	  offset for array
			 * @param len	  max number of bytes to read into array
			 * @since 1.3
			 */
			@Override
			public void write(byte[] theBytes, int off, int len)
					throws java.io.IOException {
				// Encoding suspended?
				if (suspendEncoding) {
					this.out.write(theBytes, off, len);
					return;
				}   // end if: supsended

				for (int i = 0; i < len; i++) {
					write(theBytes[off + i]);
				}   // end for: each byte written

			}   // end write

			/**
			 * Method added by PHIL. [Thanks, PHIL. -Rob]
			 * This pads the buffer without closing the stream.
			 *
			 * @throws java.io.IOException if there's an error.
			 */
			public void flushBase64() throws java.io.IOException {
				if (position > 0) {
					if (encode) {
						out.write(encode3to4(b4, buffer, position, options));
						position = 0;
					}   // end if: encoding
					else {
						throw new java.io.IOException("Base64 input not properly padded.");
					}   // end else: decoding
				}   // end if: buffer partially full

			}   // end flush

			/**
			 * Flushes and closes (I think, in the superclass) the stream.
			 *
			 * @since 1.3
			 */
			@Override
			public void close() throws java.io.IOException {
				// 1. Ensure that pending characters are written
				flushBase64();

				// 2. Actually close the stream
				// Base class both flushes and closes.
				super.close();

				buffer = null;
				out = null;
			}   // end close

		}   // end inner class OutputStream
	}

}
