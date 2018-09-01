package org.apache.finance.timereport;

import java.util.function.DoubleUnaryOperator;

public class TimeItemBuilder {
	private String itemCode;
	private double hours;
	private DoubleUnaryOperator firstRoundRule;
	private DoubleUnaryOperator secondRoundRule;
	
	public TimeItemBuilder() {
		firstRoundRule = input -> Math.round(input*20);
		secondRoundRule = intermediate -> Math.round(intermediate/10)/2.;
	}
	
	public TimeItemBuilder setItemCode(String itemCode) {
		this.itemCode = itemCode;
		return this;
	}
	
	private double roundHours(double hours) {
		return firstRoundRule
				.andThen(secondRoundRule)
				.applyAsDouble(hours);
	} 
	
	public TimeItemBuilder setHours(double hours) {
		this.hours = roundHours(hours);
		return this;
	}
	public TimeItem toTimeItem() {
		return new TimeItem() {
			@Override
			public double getHours() {
				return hours;
			}
			@Override
			public String getItemCode() {
				return itemCode;
			}
			
		};
	}
}
