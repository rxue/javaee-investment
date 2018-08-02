package org.apache.commons.nio;

import java.nio.file.Path;

public class PathUtils {

	private PathUtils() {}
	/**
	 * This method was inspired by the fact that the endIndex argument of the 
	 * original subpath method in Path is exclusive
	 *  
	 * @param path
	 * @param beginIndex
	 * @param endIndex
	 * @return
	 */
	public static Path inclusiveSubpath(Path path, int beginIndex, int endIndex) {
		return path.subpath(beginIndex, endIndex+1);
	}

}
