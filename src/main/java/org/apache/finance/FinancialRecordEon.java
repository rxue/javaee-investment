package org.apache.finance;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FinancialRecordEon implements FinancialRecord {
	private String url;
	public FinancialRecordEon(String url) {
		this.url = url;
	}

	public int getYear() {
		return -1;
	}

	public double getTotalAssets() throws MalformedURLException {
		return 0;
	}

	public double getEquity() throws MalformedURLException {
		return 0;
	}

}
