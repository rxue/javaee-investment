package org.apache.pdfbox.text;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
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
	public PDPage searchFirstPage(String lineKeyword) throws IOException {
		Optional<PDPage> page= this.getPageListStream().filter(p -> {
			PDDocument currentDoc = null;
			try (InputStream currentInputStream = p.getContents()) {
				PDFTextStripper pdfTextStripper = new PDFTextStripper();
				currentDoc = new PDDocument();
				currentDoc.addPage(p);
				String currentPageText = pdfTextStripper.getText(currentDoc);
				currentDoc.close();
				if(currentPageText.contains(lineKeyword))
					return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (currentDoc != null)
					try {
						currentDoc.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			return false;
		}).findFirst();
		if (page.isPresent()) return page.get();
		else return null;
	}
	//pdfTextStripper.stripper.getCharactersByArticle() remember to use this!
	public List<List<TextPosition>> searchFirstTextPositionList(String lineKeyword) {
		return this.searchFirstPage(lineKeyword).getCh
	}
	public TextPosition getTableHeaderTexts(String lineKeyword, PDPage page) {
		List<>
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
