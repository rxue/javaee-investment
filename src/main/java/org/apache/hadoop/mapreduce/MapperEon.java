package org.apache.hadoop.mapreduce;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.pdfbox.PdfSearcher;

public class MapperEon extends Mapper<LongWritable, Text, IntWritable, MapWritable> implements FinancialMapper {

	public int getYear(String pdfURL) {
		Pattern yearPattern = Pattern.compile("([0-9]{4}?)");//bracketed as a group
		Matcher matcher = yearPattern.matcher(pdfURL);
		if (matcher.find()) return Integer.parseInt(matcher.group(1));
		else return -1;
	}
	public double getTotalAssets(String pdfUrl) throws MalformedURLException {
		List<String> linesTotalAssets = PdfSearcher.searchLines(new URL(pdfUrl), 
				"Total assets [0-9,]+? [0-9,]+? [0-9,]+? [0-9,]+? [0-9,]+?");
		String lineTotalAssets = linesTotalAssets.get(linesTotalAssets.size() - 1);
		String totalAssets = lineTotalAssets.substring(lineTotalAssets.lastIndexOf(" ") + 1);
		totalAssets = totalAssets.replaceAll(",", "");
		return Double.parseDouble(totalAssets);
	}
	public void map(LongWritable key, Text value, Context context
            ) throws IOException, InterruptedException {
		String pdfHttpAddress = value.toString();
		int year = this.getYear(pdfHttpAddress);
		MapWritable map = new MapWritable();
		double totalAssets = this.getTotalAssets(pdfHttpAddress);
		map.put(new Text("Total Assets"), new DoubleWritable(totalAssets));
		context.write(new IntWritable(year), map);
	}
}
