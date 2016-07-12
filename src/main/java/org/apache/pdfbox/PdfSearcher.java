package org.apache.pdfbox;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class PdfSearcher {
	private static String currentFilePath = null;
	private static String[] contentArray = null;
	
	private static void buildContentArray(String filePath) {
		if (! filePath.equals(currentFilePath)) {
			File file = new File(filePath);
			PDFTextStripper pdfStripper = null;
	        PDDocument pdfDocument = null;
	        try {
				pdfStripper = new PDFTextStripper();
				pdfDocument = PDDocument.load(file);
				String content = pdfStripper.getText(pdfDocument);
				contentArray = content.split(pdfStripper.getLineSeparator());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (pdfDocument != null) {
					try {
						pdfDocument.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
	/**
	 * Search by force. Fit for small pdf
	 * @param filePath
	 * @param keyword
	 * @return
	 */
	public static List<String> searchLines(String filePath, String keyword) {
		buildContentArray(filePath);
		List<String> foundLines = new ArrayList<String>();
		for (String line : contentArray) {
			if (line.contains(keyword)) foundLines.add(line);
		}
		return foundLines;
 	}

}
