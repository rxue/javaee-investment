package org.apache.hadoop.io;

public class FinancialStatementItemGroupComparator extends WritableComparator {

	public FinancialStatementItemGroupComparator() {
		super(FinancialStatementItemCompositeKeyWritable.class, true);
	}
	
	public int compare(WritableComparable writableComparable1, WritableComparable writableComparable2) {
		FinancialStatementItemCompositeKeyWritable key1 = 
				(FinancialStatementItemCompositeKeyWritable) writableComparable1;
		FinancialStatementItemCompositeKeyWritable key2 = 
				(FinancialStatementItemCompositeKeyWritable) writableComparable2;
		return key1.getFinancialStatementItem().compareTo(key2.getFinancialStatementItem());
	}
}
