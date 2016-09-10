package org.apache.commons.io;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;

public class IOUtils {
	public static final int EOF = -1;
	/**
	 * 
	 * @param inputStream
	 * @param size
	 * @return
	 * @throws IOException
	 * @throws IllegalArgumentException
	 */
	public static byte[] toByteArray(InputStream inputStream, int size) 
			throws IOException, IllegalArgumentException {
		if (size <= 0) throw new IllegalArgumentException();
		byte[] byteArray = new byte[size];
		try(BufferedInputStream bufferedInputStream = buffer(inputStream)) {
			int offset = 0;
			while (offset < size)
				offset += bufferedInputStream.read(byteArray, offset, size-offset);
		}
		return byteArray;
	}
	/**
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 * @throws IllegalArgumentException
	 */
	public static byte[] toByteArray (URL url) throws IOException, IllegalArgumentException {
		return toByteArray(url.openStream(), url.openConnection().getContentLength());
	}
	public static ByteArrayInputStream toByteArrayInputStream(URL url) 
			throws IllegalArgumentException, IOException {
		return new ByteArrayInputStream(toByteArray(url));
	}
	/**
	 * Returns the given InputStream if it is already a BufferedInputStream, otherwise creates a BufferedInputStream from the given InputStream
	 * @param inputStream - the InputStream to wrap or return (not null)
	 * @return the given InputStream or a new BufferedInputStream for the given InputStream
	 * 
	 * 
	 */
	public static BufferedInputStream buffer (InputStream inputStream) {
		if (inputStream == null) throw new NullPointerException();
		if (inputStream instanceof BufferedInputStream) return (BufferedInputStream) inputStream;
		return new BufferedInputStream(inputStream);
	}
	/**
	 * 
	 * @param uri - has to be hierarchical
	 * @return
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	public static BufferedReader getBufferedReader (URI uri) 
			throws MalformedURLException, IOException {
		URLConnection urlConn = uri.toURL().openConnection();
		return new BufferedReader (new InputStreamReader(urlConn.getInputStream()));
	}
	
	/**
	 * 
	 * @param uri
	 * @return
	 */
	public static String getBasename (URI uri) {
		String path = uri.getPath();
		return path.substring(path.lastIndexOf('/') + 1);
	}
	
	/**
	 * 
	 * 
	 * @param uri
	 * @return null if there is no extension
	 */
	public static String getFileExtension (URI uri) {
		String path = uri.getPath();
		if (path.indexOf('.') >= 0)
			return path.substring(path.lastIndexOf('.') + 1);
		return null;
	}
	
	public static String getContentType (URI uri) throws MalformedURLException, IOException {
		String scheme = uri.getScheme();
		if (scheme.equals("http") || scheme.equals("https"))
			return uri.toURL().openConnection().getContentType();
		if (scheme.equals("file"))
			return Files.probeContentType(Paths.get(uri));
		return null;
	}
}
