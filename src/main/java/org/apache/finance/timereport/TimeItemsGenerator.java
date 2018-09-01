package org.apache.finance.timereport;

import java.util.List;
import static java.util.stream.Collectors.*;
import java.util.stream.DoubleStream;

public class TimeItemsGenerator {
	
	public List<TimeItem> getTimeItems(String itemCode, double hours, int days) {
		return DoubleStream.generate(() -> hours)
			.limit(days)
			.mapToObj(h -> new TimeItem() {//DoubleFunction
					@Override
					public String getItemCode() {
						return itemCode;
					}
					@Override
					public double getHours() {
						return h;
					}
			}).collect(toList());
	}

}