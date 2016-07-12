package org.apache.pdfbox;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.apache.pdfbox.PdfSearcher;

public class TestPdfSearcher {
	
	@Test
	public void testSearchLines() {
		List<String> result = PdfSearcher.searchLines("src/test/resources/EON_Annual_Report_2015_EN.pdf", "Total assets");
		assertTrue(!result.isEmpty());
		assertEquals(result.get(0), "Total assets 113,693 125,690 -10");
		assertEquals(result.get(7), "Total assets and liabilities 152,872 140,426 132,330 125,690 113,693");
	}
}
