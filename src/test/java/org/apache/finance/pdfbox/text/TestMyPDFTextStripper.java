package org.apache.finance.pdfbox.text;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestMyPDFTextStripper {
	private static MyPDFTextStripper textStripper;
	@BeforeClass
	public static void setup() throws IOException {
		textStripper = new MyPDFTextStripper();
	} 
	
	@Test
	public void testGetWordPositionMap() {
		try {
			byte[] bytes = IOUtils.toByteArray(new URL("http://www.eon.com/content/dam/eon-com/ueber-uns/publications/EON_Annual_Report_2015_EN.pdf"));
			PDDocument fullDoc = PDDocument.load(bytes);
			PDPage page = fullDoc.getPage(2);
			this.textStripper.getText(page);
			Map<Float,LinkedList<WordPosition>> map = this.textStripper.getWordPositionMap(0.3f);
			map.forEach((f, m) -> {
					System.out.print("DEBUG:");
					m.forEach(e -> System.out.print(e.toString() + " "));
					System.out.println();
			});
		} catch (IllegalArgumentException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
