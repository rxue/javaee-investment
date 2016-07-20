package org.apache.hadoop.mapreduce;

import java.io.IOException;

import org.apache.finance.Company;
import org.apache.finance.FinancialRecord;
import org.apache.finance.FinancialRecordEon;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.FinancialStatementItemCompositeKeyWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

public class FinancialMapper extends Mapper<LongWritable, Text, FinancialStatementItemCompositeKeyWritable, DoubleWritable> {
	/**
	 * 
	 * @param pdfLinkUrlString
	 * @return
	 */
	protected Company getCompany(String pdfLinkUrlString) {
		String dirName = pdfLinkUrlString.substring(0, pdfLinkUrlString.lastIndexOf("/")-1);
		dirName = dirName.toUpperCase();
		for (Company current : Company.values()) {
			if (dirName.indexOf(current.toString()) >= 0) return current;
		}
		return null;
	}
	
	public void map(LongWritable key, Text value, Context context
            ) throws IOException, InterruptedException {
		String urlString = value.toString();
		Company company = this.getCompany(urlString);
		FinancialRecord financialRecord = null;
		switch (company) {
			case EON:
				financialRecord = new FinancialRecordEon(urlString);
				break;
			case FORTUM:
				break;
		}
		int year = financialRecord.getYear();
		double totalAssets = financialRecord.getTotalAssets();
		context.write(new FinancialStatementItemCompositeKeyWritable("Total Assets", year), 
				new DoubleWritable(totalAssets));
		double equity = financialRecord.getEquity();
		context.write(new FinancialStatementItemCompositeKeyWritable("Equity", year), new DoubleWritable(equity));
	}
}
