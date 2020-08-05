package com.zxl.titanic.train;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;

public class TrainMR {
	/**
	 * 
	 * @param args  [输入路径，输出路径，deadcount， survivedcount]
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws InterruptedException
	 */
	public static int run(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		//设置分类统计参数
		Configuration conf = new Configuration();
		conf.setStrings("DEAD", args[2]);
		conf.setStrings("SURVIVED", args[3]);
		
		//设置跨平台
		conf.set("mapreduce.app-submission.cross-platform", "true");
		//设置本地模式
		conf.set("mapreduce.framework.name", "local");
		
		//得到job实例
		Job job = Job.getInstance(conf);
		//设置job名称
		job.setJobName("TrainMR");
		//job.setJarByClass(LogFilter.class);
		
		//设置mapper类
		job.setMapperClass(TrainMapper.class);
		//设置输入格式
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		
		//设置输出key value的类型
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(DoubleWritable.class);
		
		job.setReducerClass(TrainReducer.class);
		
		//设置输入路径
		Path input = new Path(args[0]);
		FileInputFormat.addInputPath(job, input);
		
		//设置输出路径
		Path output = new Path(args[1]);
		if(output.getFileSystem(conf).exists(output)) {
			output.getFileSystem(conf).delete(output, true);
		}
		job.setOutputFormatClass(SequenceFileOutputFormat.class);
		SequenceFileOutputFormat.setOutputPath(job, output);
		//执行任务
		
		
		
		return job.waitForCompletion(true)? 1:0;
	}
	
	
	public static void main(String[] args) throws ClassNotFoundException, IOException, InterruptedException {
		int run = run(new String[]{"/user/root/etcoutput/part-r-00000","/user/root/trainout","549","342"});
		System.out.println(run);
	}
}
