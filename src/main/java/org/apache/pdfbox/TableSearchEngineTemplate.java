package org.apache.pdfbox;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDPage;

public abstract class TableSearchEngineTemplate {
	private String pdfSourceURLStr;
	private byte[] pdfContentBytes;
	
	public void setContentBytes(String pdfSourceURLStr) 
			throws IllegalArgumentException, IOException {
		this.pdfSourceURLStr = pdfSourceURLStr;
		URL pdfFilePath = new URL(this.pdfSourceURLStr);
		this.pdfContentBytes = IOUtils.toByteArray(pdfFilePath);
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
			this.setContentBytes(pdfSourceURLStr);
		this.getTable(tableKeyword);
	}
}
