package com.zxl.titanic.train;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/*
 * Survived \t  1,2,3,4,5,6,7
 */
public class TrainMapper extends Mapper<Object, Text, Text, IntWritable>{
	
	private Text text = new Text();
	private  IntWritable count = new IntWritable(1);
	
	protected void map(Object key, Text value, Context context) throws IOException ,InterruptedException {
		String[] fields = {"Pclass","Sex","Age","SibSp","Parch","Fare ","Embarked"};
		String[] split = value.toString().split("\t");
		String[] vals = split[1].split(",");
		for(int i=0; i < vals.length;i++){
			text.set(split[0].trim() + "-" + fields[i].trim() + "-" +  vals[i].trim());
			context.write(text, count);
		}
	}
}

