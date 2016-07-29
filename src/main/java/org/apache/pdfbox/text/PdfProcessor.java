package org.apache.pdfbox.text;


import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

public class PdfProcessor implements Closeable {
	private byte[] inMemoryByteContent;
	private PDDocument pdDocument;
	public PdfProcessor(URL url) throws IOException {
		this.inMemoryByteContent = this.downloadBytes(url);
		this.pdDocument = PDDocument.load(inMemoryByteContent);
	}
	
	private byte[] downloadBytes(URL url) {
		int len = 0;//limit: maximum of int, i.e. 2GB
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
	
	public Iterator<PDPage> getPageIterator() {
		PDPageTree pageTree = this.pdDocument.getPages();
		return pageTree.iterator();
	}
	/**
	 * Search for a keyword or regular expression page by page
	 * 
	 * @param keyword
	 * @return page number, on which contains the keyword or regular expression
	 */
	public List<Integer> searchPage(String keyword) {
		List<Integer> pageNumbers = new ArrayList<Integer>();
        try {
			int pages = this.pdDocument.getNumberOfPages();
			for (int pageNumber = 1; pageNumber < pages; pageNumber ++) {
				PDFTextStripper stripper = new PDFTextStripper();
				stripper.setStartPage(pageNumber);
				stripper.setEndPage(pageNumber);
				String content = stripper.getText(this.pdDocument);
				if (content.contains(keyword)) pageNumbers.add(pageNumber);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return pageNumbers;
	}
	/**
	 * 
	 * @param lineKeyword - keyword, which can determine a unique line
	 * @param pages
	 * @return
	 * @throws IOException
	 */
	public List<List<TextPosition>> searchUniquePage(String lineKeyword, Iterator<PDPage> pages) throws IOException {
		PDFTextStripper stripper = null;
		stripper = new PDFTextStripper();
		stripper.setSortByPosition(true);
		while (pages.hasNext()) {
			PDPage currentPage = pages.next();
			PDDocument doc = new PDDocument();
			doc.addPage(currentPage);
			String text = stripper.getText(doc);
			if (text.contains(lineKeyword + stripper.getLineSeparator())) 
				return stripper.getCharactersByArticle();
		}
		return null;
	}
	
	/**
	 * 
	 * @param lineKeyword
	 * @param textPositionLists
	 * @param index - the index of the returned List of TextPosition objects, starting from which is the table content
	 * @return
	 */
	protected List<TextPosition> getTextPositionList(String lineKeyword, List<List<TextPosition>> textPositionLists, int[] index) {
		for (List<TextPosition> current : textPositionLists) {
			int prob = 0;
			for (int i = 0; i < current.size(); i++) {
				if (prob == 0 && lineKeyword.charAt(prob) == current.get(i).toString().charAt(0))
					prob++;
				else if (lineKeyword.charAt(prob) == current.get(i).toString().charAt(0)) {
					if (++prob == lineKeyword.length()) {
						index[0] = i+1;
						return current;
					}
				}
				else if (lineKeyword.charAt(prob) != current.get(i).toString().charAt(0)) {
					prob = 0;
				}
				
			}
		}
		return null;
	}
	
	
	
	public int searchLine(String keywordLine) {
		int pageNumber = -1;
		try {				//keywordLine += stripper.LINE_SEPARATOR;
			PDFTextStripper stripper = new PDFTextStripper();
			keywordLine += stripper.getLineSeparator();
			int pages = this.pdDocument.getNumberOfPages();
			for (int i = 1; i <= pages; i ++) {
				stripper.setStartPage(i);
				stripper.setEndPage(i);
				String content = stripper.getText(this.pdDocument);
				if (content.contains(keywordLine)) {
					List<List<TextPosition>> l = stripper.getCharactersByArticle();
					for (List<TextPosition> c : l) {
						for (TextPosition current : c) {
							System.out.println(current.toString());
						}
					}
					return i;
				}
				}
		} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		return pageNumber;
	}

	public void close() throws IOException {
		// TODO Auto-generated method stub
		this.pdDocument.close();
	}
}
