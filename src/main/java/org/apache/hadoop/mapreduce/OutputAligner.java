package org.apache.hadoop.mapreduce;

import java.util.List;

import org.apache.hadoop.io.DoubleWritable;

public class OutputAligner {
	private int companyNameStrLen;
	private String companyName;
	private int itemStrLen;
	private String financialItem;
	private int valueStrLen;
	public OutputAligner() {
		this(20, 30, 10);
	}
	
	public OutputAligner(int companyNameStrLen, int itemStrLen, int valueStrLen) {
		this.companyNameStrLen = companyNameStrLen;
		this.itemStrLen = itemStrLen;
		this.valueStrLen = valueStrLen;
	}
	
	public String getFormattedKey(String companyName, String financialItem) {
		this.companyName = companyName;
		this.financialItem = financialItem;
		return String.format("%-" + this.companyNameStrLen + "s%-" + this.itemStrLen + "s", 
				this.companyName, this.financialItem);
	}
	
	public String getFormattedValues(List<Double> values) {
		Double[] valueArray = new Double[values.size()];
		values.toArray(valueArray);
		String format = "";
		for (int i = 0; i < values.size(); i++)
			format += "%-" + this.valueStrLen + "s";
		return String.format(format, (Object[])valueArray);
	}

}
