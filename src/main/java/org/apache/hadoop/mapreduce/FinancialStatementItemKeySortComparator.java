package org.apache.hadoop.mapreduce;

import org.apache.hadoop.io.FinancialStatementItemCompositeKeyWritable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * Define the comparator that controls how the keys are sorted before they are passed to the Reducer.
 * @author ruixueru
 *
 */
public class FinancialStatementItemKeySortComparator extends WritableComparator {

	protected FinancialStatementItemKeySortComparator() {
		super(FinancialStatementItemCompositeKeyWritable.class, true);
	}
	@SuppressWarnings("rawtypes")
	@Override
	public int compare(WritableComparable w1, WritableComparable w2) {
		FinancialStatementItemCompositeKeyWritable key1 = (FinancialStatementItemCompositeKeyWritable) w1;
		FinancialStatementItemCompositeKeyWritable key2 = (FinancialStatementItemCompositeKeyWritable) w2;
		return
		key1.getFinancialStatementItem().compareTo(key2.getFinancialStatementItem()) == 0 ?
				key1.getPeriod() - key2.getPeriod() : key1.getFinancialStatementItem().compareTo(key2.getFinancialStatementItem());
	}	
}
