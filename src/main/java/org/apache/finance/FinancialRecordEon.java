package org.apache.finance;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.PdfSearcher;

public class FinancialRecordEon implements FinancialRecord {
	private String url;
	public FinancialRecordEon(String url) {
		this.url = url;
	}

	public int getYear() {
		Pattern yearPattern = Pattern.compile("([0-9]{4}?)");//bracketed as a group
		Matcher matcher = yearPattern.matcher(this.url);
		if (matcher.find()) return Integer.parseInt(matcher.group(1));
		else return -1;
	}

	public double getTotalAssets() throws MalformedURLException {
		List<String> linesTotalAssets = PdfSearcher.searchLines(new URL(this.url), 
				"Total assets [0-9,]+? [0-9,]+? [0-9,]+? [0-9,]+? [0-9,]+?");
		String lineTotalAssets = linesTotalAssets.get(linesTotalAssets.size() - 1);
		String totalAssets = lineTotalAssets.substring(lineTotalAssets.lastIndexOf(" ") + 1);
		totalAssets = totalAssets.replaceAll(",", "");
		return Double.parseDouble(totalAssets);
	}

	public double getEquity() throws MalformedURLException {
		List<String> linesTotalAssets = PdfSearcher.searchLines(new URL(this.url), 
				"Equity [0-9,]+? [0-9,]+? [0-9,]+? [0-9,]+? [0-9,]+?");
		String lineTotalAssets = linesTotalAssets.get(linesTotalAssets.size() - 1);
		String totalAssets = lineTotalAssets.substring(lineTotalAssets.lastIndexOf(" ") + 1);
		totalAssets = totalAssets.replaceAll(",", "");
		return Double.parseDouble(totalAssets);
	}

}
