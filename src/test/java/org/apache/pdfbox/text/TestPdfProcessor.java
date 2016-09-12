package org.apache.pdfbox.text;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PdfProcessor;
import org.apache.pdfbox.text.TextPosition;

public class TestPdfProcessor {
	//private static String sampleFilePath;
	private static URL sampleUrl;
	private static PdfProcessor processor;
	/*
	
	@BeforeClass
	public static void setupOnce() {
		try {
			sampleUrl = new URL("http://www.eon.com/content/dam/eon-com/ueber-uns/publications/EON_Annual_Report_2015_EN.pdf");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//sampleFilePath  = "src/test/resources/EON_Annual_Report_2015_EN.pdf";
		try {
			processor = new PdfProcessor(sampleUrl);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSearchUniqePageAndGetTextPositionList() {
		Iterator<PDPage>  it = this.processor.getPageIterator();
		
		List<List<TextPosition>> pageContent = null;
		try {
			pageContent = this.processor.searchUniquePage("E.ON SE and Subsidiaries Consolidated Balance Sheets—Assets", it);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int[] index = {-1};
		/*
		List<TextPosition> textPositionList = this.processor.getTextPositionList("E.ON SE and Subsidiaries Consolidated Balance Sheets—Assets", pageContent, index);
		assertEquals("D", textPositionList.get(index[0]).toString());
		for (int i = index[0]; i < index[0]+500; i++) {
			TextPosition current = textPositionList.get(i);
			System.out.println(current + "[" + current.getX() + ", " + current.getY() + ", " + current.getWidth() + ", " + current.getXScale() + ", " + current.getFontSizeInPt() + "]");
		}
		
	}
*/
}
