package org.apache.hadoop.mapreduce;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.my_components.FinancialTupleWritableComparator;
import org.apache.commons.collections.IteratorUtils;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.FinancialStatementItemCompositeKeyWritable;
import org.apache.hadoop.io.Text;

public class ReducerEon extends Reducer<FinancialStatementItemCompositeKeyWritable, DoubleWritable, Text, Text> {
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
