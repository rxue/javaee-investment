package org.apache.commons.nio;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.nio.FileUtility;
import org.junit.Test;

public class FileUtilityTest {

	@Test
	public void testCopyFromUrl() {
		Path target = Paths.get("copied.pdf");
		try {
			URL sourceURL = new URL("https://www.eon.com/content/dam/eon/eon-com/investors/annual-report/EON_Annual_Report_2015.pdf");
			assertNotNull(FileUtility.copy(sourceURL, target));
			Files.delete(target);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
