package org.apache.pdfbox;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class PdfSearcher {
	private static URL currentUrl = null;
	private static String[] contentArray = null;
	/**
	 * 
	 * @param url
	 * @return
	 */
	public static byte[] downloadBytes(URL url) {
		int len = 0;//limit is 2GB
		byte[] content = null;
		InputStream inputStream = null;
		BufferedInputStream bufferedInputStream = null;
		try {
			len = url.openConnection().getContentLength();
			content = new byte[len];
			inputStream = url.openStream();
			bufferedInputStream = new BufferedInputStream(inputStream);
			int read = 0;
			int offset = 0;
			while ((read = bufferedInputStream.read(content, offset, len-offset)) > 0) {
				offset += read;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
				try {
					if (inputStream != null) inputStream.close();
					if (bufferedInputStream != null) bufferedInputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return content;
	}
	
	private static void buildContentArray(URL url) {
		if (! url.equals(currentUrl)) {
			byte[] bytes = downloadBytes(url);
			PDFTextStripper pdfStripper = null;
	        PDDocument pdfDocument = null;
	        try {
				pdfStripper = new PDFTextStripper();
				pdfDocument = PDDocument.load(bytes);
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
	 * Search by force. Fit for small size pdf
	 * @param filePath
	 * @param keyword
	 * @return
	 */
	public static List<String> searchLines(URL url, String regex) {
		buildContentArray(url);
		List<String> foundLines = new ArrayList<String>();
		Pattern pattern = Pattern.compile(regex);
		for (String line : contentArray) {
			Matcher matcher = pattern.matcher(line);
			if (matcher.matches()) foundLines.add(line);
		}
		return foundLines;
 	}
}
