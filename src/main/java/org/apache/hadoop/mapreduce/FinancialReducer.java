package org.apache.hadoop.mapreduce;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.FinancialStatementItemCompositeKeyWritable;
import org.apache.hadoop.io.Text;

public class FinancialReducer extends Reducer<FinancialStatementItemCompositeKeyWritable, DoubleWritable, Text, Text> {
	public void reduce(FinancialStatementItemCompositeKeyWritable key, Iterable<DoubleWritable> values, Context context) throws IOException, InterruptedException {
		Iterator<DoubleWritable> it = values.iterator();
		String value = "";
		while (it.hasNext())
		{
			DoubleWritable currentValue = it.next();
			value += " " + currentValue;
		}
		context.write(new Text(key.getFinancialStatementItem()), new Text(value));
	}
}
