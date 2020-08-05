package com.zxl.titanic.train;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

//输出为 target-field-value \t count
public class TrainReducer extends Reducer<Text, IntWritable, Text, DoubleWritable>{
	
	private  DoubleWritable count = new DoubleWritable();
	private Integer DEAD;
	private Integer SURVIVED;
	
	@Override
	protected void setup(Context context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		Configuration conf = context.getConfiguration();
		DEAD = Integer.parseInt(conf.get("DEAD"));
		SURVIVED = Integer.parseInt(conf.get("SURVIVED"));
		
	}
	
	@Override
	protected void reduce(Text key, Iterable<IntWritable> values,Context context) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		int sum = 0;
		for (@SuppressWarnings("unused") IntWritable iw : values) {
			sum ++;
		}
		if(key.toString().split("-")[0].contains("0"))
			count.set((double)100 * sum / DEAD);
		else
			count.set((double)100 * sum / SURVIVED);
		
		context.write(key, count);
		
	}
}
