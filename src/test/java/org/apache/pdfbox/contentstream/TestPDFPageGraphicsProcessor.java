package org.apache.pdfbox.contentstream;

import java.awt.geom.GeneralPath;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.debugger.GraphicsDebugger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestPDFPageGraphicsProcessor {
//	private static PDDocument pdDocument;
//	@BeforeClass
//	public static void setUpOnce() throws IllegalArgumentException, IOException {
//		URL url = new URL("http://www.eon.com/content/dam/eon-com/ueber-uns/publications/EON_Annual_Report_2015_EN.pdf");
//		byte[] contents = IOUtils.toByteArray(url);
//		pdDocument = PDDocument.load(contents);
//	}
//	
//	@Test
//	public void testGetPageGraphics() {
//		PDPageTree pageTree = this.pdDocument.getPages();
//		Iterator<PDPage> pages = pageTree.iterator();
//		pages.next();
//		PDPage page = pages.next();
//		PDRectangle cropBox = page.getCropBox();
//		PDFPageGraphicsProcessor p = new PDFPageGraphicsProcessor(page);
//		try {
//			p.processPage();
//			p.transformGeneralPathsToTextQuadrant();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		List<GeneralPath> generalPaths = p.getGeneralPathList();
//		GraphicsDebugger debugger = new GraphicsDebugger();
//		debugger.showGraphics(Math.round(cropBox.getWidth()), Math.round(cropBox.getHeight()), generalPaths);
//		
//	}

}
