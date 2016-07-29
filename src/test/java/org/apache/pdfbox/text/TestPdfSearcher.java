package org.apache.pdfbox.text;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.apache.pdfbox.PdfSearcher;

public class TestPdfSearcher {
	private static String sampleFilePath;
	private static URL sampleUrl;
	
	@BeforeClass
	public static void setupOnce() {
		try {
			sampleUrl = new URL("http://www.eon.com/content/dam/eon-com/ueber-uns/publications/EON_Annual_Report_2015_EN.pdf");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sampleFilePath  = "src/test/resources/EON_Annual_Report_2015_EN.pdf";
	}
	
	@Test
	public void testDownloadBytes() {
		FileInputStream inputStream = null;
		try {
			byte[] eon2015 = PdfSearcher.downloadBytes(sampleUrl);
			File file = new File(sampleFilePath);
			inputStream = new FileInputStream(file);
			for (int i = 0; i < eon2015.length; i++)
				assertEquals(eon2015[i], (byte) inputStream.read());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
				try {
					if (inputStream != null) inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		  }
		
	}
	
	@Test
	public void testSearchLines() {
		List<String> result = PdfSearcher.searchLines(sampleUrl, "Total assets [0-9,]+? [0-9,]+?.*+");
		assertTrue(!result.isEmpty());
		assertEquals(result.get(result.size()-1), "Total assets 152,872 140,426 132,330 125,690 113,693");	
		result = PdfSearcher.searchLines(sampleUrl, "Equity [0-9,]+? [0-9,]+?.*+");
		assertTrue(!result.isEmpty());
		assertEquals(result.get(result.size()-1), "Equity 39,613 38,820 36,638 26,713 19,077");
		result = PdfSearcher.searchLines(sampleUrl, "Equity attributable to shareholders of E.ON SE");
	}
}
