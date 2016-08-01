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
	 * 
	 * @param lineKeyword - keyword, which can determine a unique line
	 * @param pages - iterator of pages
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
	 * @param columns
	 * @param lastLine
	 * @param includeLast
	 * @return
	 */
	protected List<List<GroupedLineTextPosition>> getTableTextPositionList(String lineKeyword, 
			List<List<TextPosition>> textPositionLists, String lastLine) {
		List<List<GroupedLineTextPosition>> tableResult = new ArrayList<List<GroupedLineTextPosition>>();
		GroupedLineTextPosition currentGroupedText = null;
		List<GroupedLineTextPosition> currentLine = null;
		boolean toLastRow = false;
		for (List<TextPosition> currentList : textPositionLists) {
			int keywordIndex = 0;
			boolean keywordDetected = false;
			TextPosition previous = null;
			for (TextPosition current : currentList) {
				if (!keywordDetected) {
					if (keywordIndex == 0 && 
							lineKeyword.charAt(keywordIndex) == current.toString().charAt(0)) {
						keywordIndex++;
					}
					else if (lineKeyword.charAt(keywordIndex) == current.toString().charAt(0)) {
						if (++keywordIndex == lineKeyword.length()) {
							keywordDetected = true;
							previous = current;
						}
					}
					else if (lineKeyword.charAt(keywordIndex) != current.toString().charAt(0)) {
						keywordIndex = 0;
					}
				}
				//Detected keyword, process the text in the table
				else if (keywordDetected) {
					if (previous.getY() < current.getY()) {
						if (toLastRow) return tableResult;
						currentLine = new ArrayList<GroupedLineTextPosition>();
						tableResult.add(currentLine);
					}
					if (previous.getY() < current.getY() 
							|| current.getX() - previous.getX() > 2*previous.getWidth())
					{
						currentGroupedText = new GroupedLineTextPosition(current);
						currentLine.add(currentGroupedText);
					}
					else currentGroupedText.appendTextPosition(current);
					if (lastLine != null && currentGroupedText.toString().contains(lastLine))
						toLastRow = true;
					
					previous = current;
				} 
			}
		}
		return tableResult;
	}
	
	/**
	 * 
	 * @param tableContent
	 * @param startIndex
	 * @param headerLines - how many lines belong to the header
	 * @return
	 */
	/*
	protected JSONArray getTableHeader(List<TextPosition> tableContent, int startIndex, float xLeft) {
		JSONArray array = null;
		
		return array;
	}*/
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
