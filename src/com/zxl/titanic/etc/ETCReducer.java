package com.zxl.titanic.etc;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;

import com.zxl.titanic.utils.POJO;

public class ETCReducer extends Reducer<IntWritable, POJO, IntWritable, POJO>{
	@Override
	protected void reduce(IntWritable key, Iterable<POJO> values,Context context) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		for (POJO pojo : values) {
			context.write(key, pojo);
		}
	}
}
