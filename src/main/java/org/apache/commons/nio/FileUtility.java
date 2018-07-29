package org.apache.commons.nio;

import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtility {

	private FileUtility() {}
	
	public static long copy(URL url, Path path, CopyOption... options) throws IOException {
		InputStream bin = new BufferedInputStream(url.openStream());
		return Files.copy(bin, path, options);
	}

}
