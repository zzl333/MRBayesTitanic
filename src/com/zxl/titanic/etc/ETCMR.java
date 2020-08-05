package com.zxl.titanic.etc;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.Counters;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.zxl.titanic.etc.ETCMapper.counter;
import com.zxl.titanic.utils.POJO;

/*
 * 字段设计
 * key  int  两个reduce根据key来划分
 * value自定义类型 
 * 自定义计数器count
 * 
 * 得到数据为 survived Pclass Sex Age SibSp Parch Embarked
 * 
 */

/**
 * 
 * <p>Title: ETCMR</p>  
 * <p>Description: </p>  
 * @author zxl 
 * @date 2020年5月30日
 */
public class ETCMR {
	/**
	 * 
	 * <p>Title: run</p>  
	 * <p>Description: </p>  
	 * @param args [输入目录，输出目录，全局参数。。。]
	 * @return
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @throws ClassNotFoundException 
	 */
	public static Map<String, Long> run(String args[]) throws IOException, ClassNotFoundException, InterruptedException{
		Configuration conf = new Configuration();
		
		//设置跨平台
		conf.set("mapreduce.app-submission.cross-platform", "true");
		//设置本地模式
		conf.set("mapreduce.framework.name", "local");
		
		//得到job实例
		Job job = Job.getInstance(conf);
		//设置job名称
		job.setJobName("ETCMR");
		//job.setJarByClass(LogFilter.class);
		
		//设置mapper类
		job.setMapperClass(ETCMapper.class);
		//设置输入格式
		
		job.setMapOutputKeyClass(IntWritable.class);
		job.setMapOutputValueClass(POJO.class);
		
		//设置输出key value的类型
		job.setOutputKeyClass(IntWritable.class);
		job.setOutputValueClass(POJO.class);
		
		job.setReducerClass(ETCReducer.class);
		
		//设置reducer个数,默认根据map key来划分reduce
		
		//设置输入路径
		Path input = new Path(args[0]);
		FileInputFormat.addInputPath(job, input);
		
		//设置输出路径
		Path output = new Path(args[1]);
		if(output.getFileSystem(conf).exists(output)) {
			output.getFileSystem(conf).delete(output, true);
		}
		FileOutputFormat.setOutputPath(job, output );
		//执行任务
		if(!job.waitForCompletion(true)){
			return null;
		}
		Counters counters = job.getCounters();
		Counter dead = counters.findCounter(counter.DEAD);
		Counter survived = counters.findCounter(counter.SURVIVED);
		Map<String,Long> map = new HashMap<>();
		map.put("DEAD", dead.getValue());
		map.put("SURVIVED", survived.getValue());
		return map;
	}
	
	public static void main(String[] args) throws ClassNotFoundException, IOException, InterruptedException {
		Map<String, Long> run = run(new String[]{"/user/root/etcinput/*","/user/root/etcoutput"});
		for (String str : run.keySet()) {
			System.out.println(str + ":" + run.get(str));
		}
	}
	
}
