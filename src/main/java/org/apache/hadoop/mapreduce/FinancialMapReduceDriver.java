package org.apache.hadoop.mapreduce;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FinancialStatementItemCompositeKeyWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.NLineInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class FinancialMapReduceDriver extends Configured implements Tool {

	
	@SuppressWarnings("deprecation")
	public int run(String[] args) throws Exception {
		  // check the CLI
	      if (args.length != 2) {
	         System.err.println("usage: hadoop jar -classpath $CLASSPATH:java-hadoop2-practices-1.0-SNAPSHOT.jar org.apache.hadoop.mapreduce.FinancialMapReduceDriver <inputfile> <outputdir>");
	         System.exit(1);
	      }
	      // setup the Job   
	      Job job = new Job(getConf());
	 
	      job.setJarByClass(FinancialMapReduceDriver.class);
	      job.setMapperClass(FinancialMapper.class);
	      job.setReducerClass(FinancialReducer.class);
	      job.setInputFormatClass(NLineInputFormat.class);//
	      job.setOutputKeyClass(FinancialStatementItemCompositeKeyWritable.class);
	      //job.setOutputValueClass(Text.class);

	      // setup input and output paths
	      FileInputFormat.addInputPath(job, new Path(args[0]));
	      FileOutputFormat.setOutputPath(job, new Path(args[1])); 

	      // launch job syncronously
	      return job.waitForCompletion(true) ? 0 : 1;
	}

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
	    //conf.set("mapreduce.output.key.field.separator", ",");
	    System.exit(ToolRunner.run(conf, new FinancialMapReduceDriver(), args));
	}

}
