package com.zxl.titanic.predict;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;

//输出为 target-field-value \t count
public class PredictReducer extends Reducer<IntWritable, DoubleWritable, IntWritable, DoubleWritable>{
	private DoubleWritable pre = new DoubleWritable();
	
	private Integer DEAD;
	private Integer SURVIVED;
	
	protected void setup(Context context) throws IOException ,InterruptedException {
		DEAD = context.getConfiguration().getInt("DEAD", 1);
		SURVIVED = context.getConfiguration().getInt("SURVIVED", 1);
	}
	
	protected void reduce(IntWritable key, Iterable<DoubleWritable> values,Context context)
			throws IOException, InterruptedException {
		
//		System.out.println(key);
		double temp = 1;
		if(key.get() == 0) 
			temp = (double) DEAD/ (DEAD + SURVIVED);
		else
			temp = (double) SURVIVED/ (DEAD + SURVIVED);
		System.out.println(key.toString()+"的先验概率"+temp);
		
//		Double db = (double) 0f;
		System.out.println("========================");
		System.out.println(context.getConfiguration().get("DATA"));
		for (DoubleWritable dw : values) {
			System.out.println(dw);
			temp *= dw.get();
		}
//		temp *= db;

		System.out.println(key.toString()+":"+temp);
		System.out.println("========================");
		pre.set(temp);
		context.write(key, pre);
	}
}
