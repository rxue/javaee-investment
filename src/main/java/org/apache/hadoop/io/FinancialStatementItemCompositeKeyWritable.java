package org.apache.hadoop.io;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class FinancialStatementItemCompositeKeyWritable implements 
 		WritableComparable<FinancialStatementItemCompositeKeyWritable> {
	private int period;
	private String financiaStatementlItem;
	public FinancialStatementItemCompositeKeyWritable() {
	}
	
	public FinancialStatementItemCompositeKeyWritable(
			String financialStatementItem, int period) {
		this.financiaStatementlItem = financialStatementItem;
		this.period = period;
	}

	public void readFields(DataInput in) throws IOException {
		this.financiaStatementlItem = in.readUTF();
		this.period = in.readInt();
	}

	public void write(DataOutput out) throws IOException {
		out.writeUTF(this.financiaStatementlItem);
		out.writeInt(this.period);
	}
	
	public String getFinancialStatementItem() {
		return this.financiaStatementlItem;
	}
	
	public int getPeriod() {
		return this.period;
	}

	public int compareTo(FinancialStatementItemCompositeKeyWritable o) {
		return this.financiaStatementlItem.compareTo(o.getFinancialStatementItem());
	}
	
	/**
	 * for unit test purpose (JUNIT + MRUNIT)
	 * @param o
	 * @return
	 */
	@Override
	public boolean equals(Object o) {
		FinancialStatementItemCompositeKeyWritable compared = 
				(FinancialStatementItemCompositeKeyWritable) o;
		return this.financiaStatementlItem.equals(compared.getFinancialStatementItem()) &&
				this.period == compared.getPeriod();
	}
}
