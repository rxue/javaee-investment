package org.apache.pdfbox.text;

public class GroupTextPosition {
	private float xStart;
	private float xEnd;
	private String content;
	public GroupTextPosition() {
		this.xStart = 0;
		this.xEnd = 0;
		this.content = "";
	}
	
	
	@Override
	public String toString() {
		return this.content;
	}

}
