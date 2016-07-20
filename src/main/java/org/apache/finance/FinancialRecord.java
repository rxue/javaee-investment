package org.apache.finance;

import java.net.MalformedURLException;

public interface FinancialRecord {
	int getYear();
	double getTotalAssets() throws MalformedURLException;
	double getEquity() throws MalformedURLException;
}
