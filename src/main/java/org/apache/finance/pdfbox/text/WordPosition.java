package org.apache.finance.pdfbox.text;

import org.apache.pdfbox.text.TextPosition;

public class WordPosition {
	private String word;
	private float xStart;
	private float xEnd;
	private float y;
	private float ratio;
	public WordPosition(TextPosition textPosition, float ratio) {
		this.ratio = ratio;
		this.appendTextPosition(textPosition);
	}
	/**
	 * 
	 * @param textPosition
	 * @return
	 */
	public boolean appendTextPosition(TextPosition textPosition) {
		if (this.y != textPosition.getY())
			return false;
		if ((textPosition.getX() - this.xEnd) / textPosition.getWidth() > this.ratio)
			return false;
		if (this.word == null) {
			this.word = textPosition.getUnicode();
			this.y = textPosition.getY();
			this.xStart = textPosition.getX();
			this.xEnd = this.xStart + textPosition.getWidth();
		}
		else {
			this.word += textPosition.getUnicode();
			this.xEnd = textPosition.getX() + textPosition.getWidth();
		}
		return true;
		
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
	public float getWidth() {
		return this.xEnd - this.xEnd;
	}
}
