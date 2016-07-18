package org.apache.hadoop.mapreduce;

import java.net.MalformedURLException;

public interface FinancialRecord {
	
	int getYear(String httpUrl);
	double getTotalAssets(String httpUrl) throws MalformedURLException;
	double getEquity(String httpUrl) throws MalformedURLException;
}
