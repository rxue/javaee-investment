package org.apache.hadoop;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.junit.Before;
import org.junit.Test;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.MapperEon;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;

public class TestMapperEon {
	private MapDriver<LongWritable, Text, IntWritable, MapWritable> mapDriver;
	@Before
	public void setUp() {
		Mapper<LongWritable, Text, IntWritable, MapWritable> mapper = new MapperEon();
		mapDriver = MapDriver.newMapDriver(mapper);
	}
	@Test
	public void testMapper() throws IOException {
		mapDriver.withInput(new LongWritable(0), 
				new Text("http://www.eon.com/content/dam/eon-com/ueber-uns/publications/EON_Annual_Report_2015_EN.pdf"));
		MapWritable map = new MapWritable();
		map.put(new Text("Total Assets"), new DoubleWritable(113693));
		mapDriver.withOutput(new IntWritable(2015), map);
		mapDriver.runTest();
	}      

}
