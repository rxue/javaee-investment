package org.apache.finance.pdfbox;

import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;

public abstract class TableSearchEngineTemplate {
	private String pdfSourceURLStr;
	private PDDocument pdDocument;
	
	public void setPDDocument(String pdfSourceURLStr) 
			throws IllegalArgumentException, IOException {
		this.pdfSourceURLStr = pdfSourceURLStr;
		URL pdfFilePath = new URL(this.pdfSourceURLStr);
		byte[] pdfContentBytes = IOUtils.toByteArray(pdfFilePath);
		this.pdDocument = PDDocument.load(pdfContentBytes);
	}
	
	public abstract PDPage getPage(String tableKeyword);
	
	public abstract void getTable(PDPage page, String tableKeyword);
	
	public void getTable(String tableKeyword) {
		 PDPage page = this.getPage(tableKeyword);
		 getTable(page, tableKeyword);
	}
	
	public void getTable(String pdfSourceURLStr, String tableKeyword) 
			throws IllegalArgumentException, IOException {
		if (! pdfSourceURLStr.equals(this.pdfSourceURLStr))
			this.setPDDocument(pdfSourceURLStr);
		this.getTable(tableKeyword);
	}
}
