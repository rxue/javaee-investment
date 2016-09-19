package org.apache.finance.pdfbox.text;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

public class MyPDFTextStripper extends PDFTextStripper {

	public MyPDFTextStripper() throws IOException {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Map<Float,LinkedList<WordPosition>> getWordPositionMap(float ratio) {
		List<List<TextPosition>> chars = super.getCharactersByArticle();
		Map<Float,LinkedList<WordPosition>> wordPositionMap = new HashMap<Float,LinkedList<WordPosition>>();
		chars.forEach(list ->
			list.forEach(textPosition -> {
				if (!textPosition.toString().equals(" ")) {
					LinkedList<WordPosition> line = wordPositionMap.get(textPosition.getY());
					if (line == null) {
						line = new LinkedList<WordPosition>();
						line.add(new WordPosition(textPosition, ratio));
						wordPositionMap.put(textPosition.getY(), line);
					}
					else {
						WordPosition lastWord = line.getLast();
						if(!lastWord.appendTextPosition(textPosition))
							line.addLast(new WordPosition(textPosition, ratio));
					}
				}
			})
		);
		return wordPositionMap;
	}
	
	public String getText(PDPage page) throws IOException {
		PDDocument doc = new PDDocument();
		doc.addPage(page);
		String text = super.getText(doc);
		doc.close();
		return text;
	}
	

}
