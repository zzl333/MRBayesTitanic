package com.zxl.titanic.predict;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/*
 * 数据格式
 * text  \t  DoubleWritable
 */
public class PredictMapper extends Mapper<Text, DoubleWritable, IntWritable, DoubleWritable>{
	
	private IntWritable target = new IntWritable();
	private String datas;
	@Override
	
	protected void setup(Mapper<Text, DoubleWritable, IntWritable, DoubleWritable>.Context context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
			datas = context.getConfiguration().get("DATA");
	}
	
	
	protected void map(Text key, DoubleWritable value, Context context) throws IOException ,InterruptedException {
		List<String> list = new ArrayList<>();
		String[] fields = {"Pclass","Sex","Age","SibSp","Parch","Fare ","Embarked"};
		//测验数据格式Survived \t  1,2,3,4,5,6,7
		//0	3,1,0,0,0,8,2
		String[] data = datas.split("\t")[1].split(",");
//		System.out.println(Arrays.toString(data));
		for (int i = 0; i < fields.length; i++) {
			list.add(fields[i].trim() + "-" + data[i].trim());
		}
//		System.out.println(list.toString());
		for (String str : list) {
			if(key.toString().substring(2).equals(str)){
				target.set(Integer.parseInt(key.toString().split("-")[0]));
//				System.out.println(target.get() + " " +str + " "+ value.get());
				context.write(target, value);
			}
		}
	}
	
	
}

