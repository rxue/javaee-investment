package org.apache.finance.pdfbox.text;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

public class PdfTextProcessor implements AutoCloseable {
	private PDDocument pdDocument;
	public PdfTextProcessor(String pdfSourceURLStr) throws 
			IllegalArgumentException, IOException {
		URL pdfFilePath = new URL(pdfSourceURLStr);
		byte[] pdfContentBytes = IOUtils.toByteArray(pdfFilePath);
		this.pdDocument = PDDocument.load(pdfContentBytes);
	}
	public PdfTextProcessor(PDDocument pdDocument) throws IOException {
		this.pdDocument = pdDocument;
	}
		
	private Iterator<PDPage> getPageIterator() {
		PDPageTree pageTree = this.pdDocument.getPages();
		return pageTree.iterator();
	}
	
	private Stream<PDPage> getPageListStream() {
		Iterable<PDPage> pageIterable = () -> this.getPageIterator();
		return StreamSupport.stream(pageIterable.spliterator(), false);
	}

 	/**
	 * 
	 * @param lineKeyword - keyword, which can determine a unique line
	 * @param pages - iterator of pages
	 * @return
	 * @throws IOException
	 */
	private Object searchFirstPage(String lineKeyword, Class<?> returnType) 
			throws IOException {
		Iterator<PDPage> pages = this.getPageIterator();
		PDFTextStripperWrapper textStripper = new PDFTextStripperWrapper();
		PDDocument doc = new PDDocument();
		while (pages.hasNext()) {
			PDPage currentPage = pages.next();
			doc.addPage(currentPage);
			String currentPageText = textStripper.getText(doc);
			doc.removePage(currentPage);
			if (currentPageText.contains(lineKeyword)) {
				doc.close();
				if (returnType.equals(PDFTextStripperWrapper.class))
					return textStripper;
				else if (returnType.equals(List.class))
					return textStripper.getCharactersByArticle();
			}
		}
		doc.close();
		return null;
	}
	/*
	public PDPage searchFistPDPage(String lineKeyword) throws IOException
	{
		return (PDPage) searchFirstPage(lineKeyword, PDPage.class);
	}*/

	@SuppressWarnings("unchecked")
	public List<List<TextPosition>> searchFirstTextPositionList(String lineKeyword) 
			throws IOException {
		return (List<List<TextPosition>>) searchFirstPage(lineKeyword, List.class);
	}
	
	public PDFTextStripperWrapper searchFistPageTextStripper(String lineKeyword) 
			throws IOException {
		return (PDFTextStripperWrapper) searchFirstPage(lineKeyword, PDFTextStripperWrapper.class);
	}
	
	//pdfTextStripper.stripper.getCharactersByArticle() remember to use this!
//	public List<List<TextPosition>> searchFirstTextPositionList(String lineKeyword) {
//		return this.searchFirstPage(lineKeyword).getCh
//	}
//	public TextPosition getTableHeaderTexts(String lineKeyword, PDPage page) {
//		List<>
//	}
	/**
	 * 
	 * @param lineKeyword
	 * @param textPositionLists
	 * @param columns
	 * @param lastLine
	 * @param includeLast
	 * @return
	 */
	/*
	protected Table getTableTextPositionList(String lineKeyword, 
			List<List<TextPosition>> textPositionLists, String lastLine) {
		Table tableResult = new Table();
		GroupedLineTextPosition currentGroupedText = null;
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
					if (previous.getY() < current.getY() && toLastRow) return tableResult;	
					if (previous.getY() < current.getY() 
							|| current.getX() - previous.getX() > 2*previous.getWidth())
					{
						currentGroupedText = new GroupedLineTextPosition(current);
						tableResult.appendCellData(currentGroupedText);
					}
					else if (previous.getY() == current.getY()) currentGroupedText.appendTextPosition(current);
					if (lastLine != null && currentGroupedText.toString().contains(lastLine))
						toLastRow = true;
					previous = current;
				} 
			}
		}
		return tableResult;
	}
	*/

	public void close() throws IOException {
		// TODO Auto-generated method stub
		this.pdDocument.close();
	}
}
