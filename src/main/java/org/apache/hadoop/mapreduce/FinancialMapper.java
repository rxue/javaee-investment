package org.apache.hadoop.mapreduce;

import java.net.MalformedURLException;

public interface FinancialMapper {
	
	int getYear(String httpUrl);
	double getTotalAssets(String httpUrl) throws MalformedURLException;

}
