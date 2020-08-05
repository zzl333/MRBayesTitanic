package com.zxl.titanic.test;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

import com.zxl.titanic.main.Bayes;
import com.zxl.titanic.utils.HdfsUtil;
import com.zxl.titanic.utils.ModelAssess;

public class Test {
	public static void main(String[] args) throws Exception {
		Bayes bayes = new Bayes();
		bayes.etc(new String[]{"输入路径","输出路径"});
		bayes.train("输入数据路径");
		List<Integer> list2 = bayes.predict("输入路径", "输出路径");
		System.out.println(list2.toString());
		BufferedReader reader = HdfsUtil.getInputStream("输入的数据位置");
		List<Integer> list1 = new ArrayList<>();
		String str = null;
		while((str = reader.readLine()) != null){
			list1.add(Integer.parseInt(str.split(",")[1]));
		}
		ModelAssess modelAssess = new ModelAssess(list1, list2);
		
	}
	
}
