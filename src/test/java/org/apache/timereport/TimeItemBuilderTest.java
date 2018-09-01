package org.apache.timereport;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class TimeItemBuilderTest {
	
	@Test
	public void testPrivateRoundHours() {
		TimeItemBuilder builder = new TimeItemBuilder();
		builder.setHours(5.26);
		TimeItem item = builder.toTimeItem();
		assertEquals(5.5, item.getHours());
		builder.setHours(5.25);
		item = builder.toTimeItem();
		assertEquals(5.5, item.getHours());
		builder.setHours(5.1);
		item = builder.toTimeItem();
		assertEquals(5., item.getHours());
	}

}
