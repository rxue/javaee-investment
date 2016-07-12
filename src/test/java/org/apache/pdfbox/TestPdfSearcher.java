package org.apache.pdfbox;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.apache.pdfbox.PdfSearcher;

public class TestPdfSearcher {
	
	@Test
	public void testSearchLines() {
		List<String> result = PdfSearcher.searchLines("src/test/resources/EON_Annual_Report_2015_EN.pdf", "Total assets [0-9,]+? [0-9,]+? [0-9,]+? [0-9,]+? [0-9,]+?");
		assertTrue(!result.isEmpty());
		assertEquals(result.get(result.size()-1), "Total assets 152,872 140,426 132,330 125,690 113,693");	
		result = PdfSearcher.searchLines("src/test/resources/EON_Annual_Report_2015_EN.pdf", "Equity [0-9,]+? [0-9,]+? [0-9,]+? [0-9,]+? [0-9,]+?");
		assertTrue(!result.isEmpty());
		assertEquals(result.get(result.size()-1), "Equity 39,613 38,820 36,638 26,713 19,077");
	}
}
