package org.apache.hadoop.mapreduce;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.apache.finance.Company;
import org.apache.hadoop.io.FinancialStatementItemCompositeKeyWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.junit.Before;
import org.junit.Test;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.FinancialMapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.FinancialReducer;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.mapreduce.MapReduceDriver;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;

public class TestMapReduceEon {
	private FinancialMapper mapper;
	private Reducer<FinancialStatementItemCompositeKeyWritable, DoubleWritable, Text, Text> reducer;
	@Before
	public void setUp() {
		this.mapper = new FinancialMapper();
		this.reducer = new FinancialReducer();
	}
	@Test
	public void testMapperGetCompany() {
		String url_1 = "http://www.eon.com/content/dam/eon-com/ueber-uns/publications/EON_Annual_Report_2015_EN.pdf";
		Company company = this.mapper.getCompany(url_1);
		assertEquals(Company.EON, company);
	}
	@Test
	public void testMapper() throws IOException {
		MapDriver<LongWritable, Text, FinancialStatementItemCompositeKeyWritable, DoubleWritable> mapDriver
			= MapDriver.newMapDriver(this.mapper);
		mapDriver.withInput(new LongWritable(0), 
				new Text("http://www.eon.com/content/dam/eon-com/ueber-uns/publications/EON_Annual_Report_2015_EN.pdf"));
		mapDriver.withOutput(new FinancialStatementItemCompositeKeyWritable("Total Assets", 2015), 
				new DoubleWritable(113693));
		mapDriver.withOutput(new FinancialStatementItemCompositeKeyWritable("Equity", 2015), 
				new DoubleWritable(19077));
		mapDriver.runTest();
	}  
	/*
	@Test
	public void testReducer() throws IOException {
		ReduceDriver<FinancialStatementItemCompositeKeyWritable, DoubleWritable, Text, Text> reduceDriver
			= ReduceDriver.newReduceDriver(this.reducer);
		FinancialStatementItemCompositeKeyWritable k = new FinancialStatementItemCompositeKeyWritable("Total Assets", 2015);
		List<DoubleWritable> v = new ArrayList<DoubleWritable>();
		v.add(new DoubleWritable(0));
		reduceDriver.withInput(k, v);
		reduceDriver.withOutput(new Text("Total Assets"), new Text(" 0.0"));//NB! TupleWritable element value not tested exhaustively 
		reduceDriver.runTest();
	}*/
	@Test
	public void testMapReduce() throws IOException {
		MapReduceDriver<LongWritable,Text,FinancialStatementItemCompositeKeyWritable,DoubleWritable,Text,Text> 
			mapReduceDriver = new MapReduceDriver(this.mapper, this.reducer);
		mapReduceDriver.setKeyOrderComparator(new FinancialStatementItemKeySortComparator());
		mapReduceDriver.withInput(new LongWritable(0), new Text("http://www.eon.com/content/dam/eon-com/ueber-uns/publications/EON_Annual_Report_2015_EN.pdf"));
		mapReduceDriver.withInput(new LongWritable(1), new Text("http://www.eon.com/content/dam/eon-com/ueber-uns/publications/150312_EON_Annual_Report_2014_EN.pdf"));
		mapReduceDriver.withOutput(new Text("Equity"), new Text(" 26713.0 19077.0"));
		mapReduceDriver.withOutput(new Text("Total Assets"), new Text(" 125690.0 113693.0"));
		mapReduceDriver.runTest();
	}
	
	
}
