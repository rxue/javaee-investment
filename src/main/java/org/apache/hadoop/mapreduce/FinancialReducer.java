package org.apache.hadoop.mapreduce;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.FinancialStatementItemCompositeKeyWritable;
import org.apache.hadoop.io.Text;

public class FinancialReducer extends Reducer<FinancialStatementItemCompositeKeyWritable, DoubleWritable, Text, Text> {
	public void reduce(FinancialStatementItemCompositeKeyWritable key, Iterable<DoubleWritable> values, Context context) throws IOException, InterruptedException {
		Iterator<DoubleWritable> it = values.iterator();
		String value = "";
		int valueCounter = 0;
		List<Double> valueList = new ArrayList<Double>();
		while (it.hasNext()) {
			valueList.add(it.next().get());
			valueCounter++;
		}
		OutputAligner aligner= new OutputAligner();
		context.write(new Text(aligner.getFormattedKey(key.getCompanyName(), key.getFinancialStatementItem())), 
				new Text(aligner.getFormattedValues(valueList)));
	}
}
