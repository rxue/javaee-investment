package org.apache.pdfbox.text;

public class GroupedLineTextPosition {
	private String text;
	private float xStart;
	private float xEnd;
	private float y;
	public GroupedLineTextPosition(TextPosition textPosition) {
		this.text = null;
		this.appendTextPosition(textPosition);
	}
	public void appendTextPosition(TextPosition textPosition) {
		if (this.text == null) {
			this.text = textPosition.getUnicode();
			this.y = textPosition.getY();
			this.xStart = textPosition.getX();
			this.xEnd = this.xStart + textPosition.getWidth();
		}
		else {
			this.text += textPosition.getUnicode();
			this.xEnd = textPosition.getX() + textPosition.getWidth();
		}
		
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
}
