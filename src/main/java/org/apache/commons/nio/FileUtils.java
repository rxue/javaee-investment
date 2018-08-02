package org.apache.commons.nio;

import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtils {

	private FileUtils() {}
	
	public static long copy(URL url, Path path, CopyOption... options) throws IOException {
		InputStream bin = new BufferedInputStream(url.openStream());
		return Files.copy(bin, path, options);
	}
	
	public static long copyFromURL(String urlString, Path path, CopyOption... options) throws IOException {
		URL url = new URL(urlString);
		return copy(url, path, options);
	}
	/**
	 * without following symbolic link
	 * @param path
	 */
	public static boolean deleteRecursively(Path path) throws IOException {
		int errorCount[] = {0};
		if (Files.isDirectory(path))
			Files.list(path).forEach(t -> {
				try {
					deleteRecursively(t);
				} catch (IOException e) {
					e.printStackTrace();
					errorCount[0]++;
				}
			});
		if (errorCount[0] > 0) return false;
		return Files.deleteIfExists(path); 
	}

}
