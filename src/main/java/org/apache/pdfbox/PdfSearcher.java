package org.apache.pdfbox;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	public static List<String> searchLines(String filePath, String regex) {
		buildContentArray(filePath);
		List<String> foundLines = new ArrayList<String>();
		Pattern pattern = Pattern.compile(regex);
		for (String line : contentArray) {
			Matcher matcher = pattern.matcher(line);
			if (matcher.matches()) foundLines.add(line);
		}
		return foundLines;
 	}

}
