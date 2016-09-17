package org.apache.finance.pdfbox.text;

import org.apache.pdfbox.text.TextPosition;

public class WordPosition {
	private String word;
	private float xStart;
	private float xEnd;
	private float y;
	public WordPosition(TextPosition startTextPosition) {
		this.word = startTextPosition.getUnicode();
		this.xStart = startTextPosition.getX();
		this.xEnd = this.xStart + startTextPosition.getWidth();
	}
	public void appendTextPosition(TextPosition textPosition) {
		
	}
	@Override
	public String toString() {
		return this.word;
	}
	
	public float getXStart() {
		return this.xStart;
	}
	public float getXEnd() {
		return this.xEnd;
	}
	public float getY() {
		return this.y;
	}
}
