package com.zxl.titanic.predict;

import java.io.BufferedReader;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.zxl.titanic.utils.HdfsUtil;

public class PredictMR {
	/**
	 * 
	 * @param args  [输入路径，输出，deadcount， survivedcount,etc后测验的数据]
	 * @return
	 * @throws Exception 
	 */
	public static int run(String[] args) throws Exception {
		//设置分类统计参数
		Configuration conf = new Configuration();
		conf.setInt("DEAD", Integer.parseInt(args[2]));
		conf.setInt("SURVIVED", Integer.parseInt(args[3]));
		conf.set("DATA", args[4]);
		
		//设置跨平台
		conf.set("mapreduce.app-submission.cross-platform", "true");
		//设置本地模式
		conf.set("mapreduce.framework.name", "local");
		
		//得到job实例
		Job job = Job.getInstance(conf);
		//设置job名称
		job.setJobName("PredictMR");
		//job.setJarByClass(LogFilter.class);
		
		//设置mapper类
		job.setMapperClass(PredictMapper.class);
		//设置输入格式
		
		job.setMapOutputKeyClass(IntWritable.class);
		job.setMapOutputValueClass(DoubleWritable.class);
		
		//设置输出key value的类型
		job.setOutputKeyClass(IntWritable.class);
		job.setOutputValueClass(DoubleWritable.class);
		
		job.setReducerClass(PredictReducer.class);
		
		
		
		//设置输入路径
		Path input = new Path(args[0]);
		job.setInputFormatClass(SequenceFileInputFormat.class);
		SequenceFileInputFormat.addInputPath(job, input);
		
		//设置输出路径
		Path output = new Path(args[1]);
		if(output.getFileSystem(conf).exists(output)) {
			output.getFileSystem(conf).delete(output, true);
		}
		FileOutputFormat.setOutputPath(job, output);
		//执行任务
		if(job.waitForCompletion(true)){
			BufferedReader inputStream = HdfsUtil.getInputStream(args[1]+"/part-r-00000");
			String str = null;
			double dp = 0;
			double sp = 0;
			while((str = (inputStream.readLine())) != null){
				String[] split = str.split("\t");
				if(split[0].equals("0"))
					dp = Double.parseDouble(split[1]);
				else
					sp = Double.parseDouble(split[1]);
			}
			
			
			if(dp >= sp)
				return 0;
			else
				return 1;
		}else{
			return -1;
		}
			
		
	}
	
	/*
0	3,1,4,1,5,31,3
0	3,1,2,0,0,8,3
0	3,1,1,3,1,21,3
0	1,1,6,0,0,51,3
0	3,1,0,0,0,8,2
0	3,1,4,0,0,8,3
0	3,1,3,1,0,7,3
1	1,0,4,1,0,53,3
1	3,0,3,0,0,7,3
1	1,0,6,0,0,26,3
1	3,0,1,1,1,16,3
1	2,0,2,1,0,30,1
1	3,0,3,0,2,11,3
1	1,0,4,1,0,71,1
	 */
	public static void main(String[] args) throws Exception {
		int run = run(new String[]{"/user/root/trainout/part-r-00000",
				"/user/root/trainout/predictout","549","342","0	3,1,4,0,0,8,3"});
		System.out.println("========" + run + "==============");
	}
}
