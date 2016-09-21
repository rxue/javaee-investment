package org.apache.finance.pdfbox.text;

import org.apache.pdfbox.text.TextPosition;
import org.checkerframework.checker.nullness.qual.NonNull;

public class TextSection {
	private String text;
	private float xStart;
	private float xEnd;
	private float y;
	private float ratio;
	public TextSection(TextPosition textPosition, float ratio) {
		this.ratio = ratio;
		this.appendTextPosition(textPosition);
	}
	/**
	 * Append a TextPosition to the end of this WordPosition
	 * 
	 * @param textPosition
	 * @return
	 */
	public boolean appendTextPosition(@NonNull TextPosition textPosition) {
		if ((this.y > 0 && this.y != textPosition.getY()) || 
				(this.xEnd > 0 && 
				(textPosition.getX() - this.xEnd) / textPosition.getWidth() > this.ratio))
			return false;
		if (this.text == null) {
			this.text = textPosition.getUnicode();
			this.y = textPosition.getY();
			this.xStart = textPosition.getX();
			this.xEnd = this.xStart + textPosition.getWidth();
		} else {
			this.text += textPosition.getUnicode();
			this.xEnd = textPosition.getX() + textPosition.getWidth();
		}
		return true;
		
	}
	@Override
	public String toString() {
		return this.text;
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
		return this.xEnd - this.xStart;
	}
}
