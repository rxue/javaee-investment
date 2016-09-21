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
	}

	public Map<Float, LinkedList<TextSection>> getWordPositionMap(float ratio) {
		List<List<TextPosition>> chars = super.getCharactersByArticle();
		Map<Float, LinkedList<TextSection>> wordPositionMap = new HashMap<Float, LinkedList<TextSection>>();
		chars.forEach(list -> list.forEach(textPosition -> {
			LinkedList<TextSection> line = wordPositionMap.get(textPosition.getY());
			if (line == null) {
				line = new LinkedList<TextSection>();
				line.add(new TextSection(textPosition, ratio));
				wordPositionMap.put(textPosition.getY(), line);
			} else {
				TextSection lastWord = line.getLast();
				if (!lastWord.appendTextPosition(textPosition) &&
						!textPosition.getUnicode().equals(" "))
					line.addLast(new TextSection(textPosition, ratio));
			}
		}));
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
