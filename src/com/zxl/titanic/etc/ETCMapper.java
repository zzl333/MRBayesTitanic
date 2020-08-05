package com.zxl.titanic.etc;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.zxl.titanic.utils.POJO;

public class ETCMapper extends Mapper<Object, Text, IntWritable, POJO>{
	private IntWritable target = new IntWritable();
	private POJO pojo = new POJO();
	//计数器
	enum counter{
		DEAD,
		SURVIVED
	}
	
	protected void map(Object key, Text value, Context context) throws IOException ,InterruptedException {
		//首行去除
		if(value.toString().contains("PassengerId,Survived,Pclass,Name,Sex,Age,SibSp,Parch,Ticket,Fare,Cabin,Embarked"))
			return;
		setAll(value,context);
		context.write(target, pojo);
		
	}

	/**
	 * 为POJO设置参数
	 * @param value
	 * @param context
	 */
	private void setAll(Text value, Context context) {
		// TODO Auto-generated method stub
		String[] split = value.toString().split(",");
		
//		System.out.println("============================================");
//		System.out.println(Arrays.toString(split));
//		System.out.println("============================================");
		
		if(split[1].trim().equals("0")){
			context.getCounter(counter.DEAD).increment(1);
		}else{
			context.getCounter(counter.SURVIVED).increment(1);
		}
		//1
		target.set(Integer.parseInt(split[1]));
		//2
		pojo.setPclass(Integer.parseInt(split[2]));
		//3
//		System.out.println(split[5]);
		if(split[5].trim().equalsIgnoreCase("male"))
			pojo.setSex(1);
		else
			pojo.setSex(0);
		//4
		if(split[6].equals(""))
			pojo.setAge(0);
		else{
			int temp = (int)Math.floor(Float.parseFloat(split[6]));
			if(temp >=1 && temp <= 10)
				pojo.setAge(1);
			else if(temp >=11 && temp <= 20)
				pojo.setAge(2);
			else if(temp >=21 && temp <= 30)
				pojo.setAge(3);
			else if(temp >=31 && temp <= 40)
				pojo.setAge(4);
			else if(temp >=41 && temp <= 50)
				pojo.setAge(5);
			else if(temp >=51 && temp <= 60)
				pojo.setAge(6);
			else if(temp >=61 && temp <= 70)
				pojo.setAge(7);
			else
				pojo.setAge(8);
		}
		//5、SibSp存在数据空白问题  5 和8并入4中
		int a = Integer.parseInt(split[7]);
		if(a == 4 || a == 5 ||a==8){
			pojo.setSibSp(4);
		}else{
			pojo.setSibSp(Integer.parseInt(split[7]));
		}
		//6、4，5，6 ==4
		a = Integer.parseInt(split[8]);
		if(a==4 || a ==5|| a == 6){
			pojo.setParch(4);
		}else{
			pojo.setParch(Integer.parseInt(split[8]));
		}
		//7
		pojo.setFare(getFare(split[10]));
		//8 Embarked港口1c，2q，3s  空白为0
		
		//最后一个数据为空的时候数组会越界
		
		if(split.length < 13){
			pojo.setEmbarked(0);
		}else if(split[12].trim().equalsIgnoreCase("C")){
			pojo.setEmbarked(1);
		}else if(split[12].trim().equalsIgnoreCase("Q")){
			pojo.setEmbarked(2);
		}else{
			pojo.setEmbarked(3);
		}
	}
	
	/**
	 * 获取价格标记值
	 * <p>Title: fun</p>  
	 * <p>Description: </p>  
	 * @return	价格标记值
	 */
	public static int getFare(String fare){
		int a = (int)Math.floor(Double.parseDouble(fare));
		if(a > 210){
			a = 210;
		}else if(a > 180){
			a = 180;
		}else if(a > 150){
			a = 150;
		}else if(a > 120){
			a = 120;
		}else if(a > 90){
			a = 90;
		}else if(a > 60){
			a = 60;
		}else if(a > 30){
			a = 30;
		}else{
			a = 0;
		}	
		return a;
	}
}
