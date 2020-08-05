package com.zxl.titanic.test;

import java.awt.Font;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.zxl.titanic.main.Bayes;
import com.zxl.titanic.utils.CharReport_ZZT;
import com.zxl.titanic.utils.HdfsUtil;
import com.zxl.titanic.utils.ModelAssess;

public class Test3 {
	
	@Test
	public void textBayes() throws Exception {
		Bayes bayes = new Bayes();
		Map<String, Long> run = new HashMap<>();
		run.put("DEAD", (long) 549);
		run.put("SURVIVED", (long) 342);
		bayes.setRun(run );
		List<Integer> list2 = bayes.predict("/user/root/predictdata/train.csv", "/user/root/predictoutput");
		BufferedReader reader = HdfsUtil.getInputStream("/user/root/tempredict/part-r-00000");
		List<Integer> list1 = new ArrayList<>();
		String str = null;
		reader.readLine();
		while((str = reader.readLine()) != null){
			list1.add(Integer.parseInt(str.split("\t")[0]));
		}
		ModelAssess modelAssess = new ModelAssess(list1, list2);
		System.out.println("===================");
		System.out.println(modelAssess.getAccuracy());
		System.out.println(modelAssess.getPrecision());
		System.out.println(modelAssess.getRecall());
		System.out.println("===================");
		float[] data = {modelAssess.getAccuracy(),modelAssess.getPrecision(),modelAssess.getRecall()};
		show(data);
		
	}
	public static void show(float[] data){
        Map<String, Map<String, Double>> datas =new HashMap<String, Map<String,Double>>();
        
        Map<String, Double> map1=new HashMap<String, Double>();
        Map<String, Double> map2=new HashMap<String, Double>();
        Map<String, Double> map3=new HashMap<String, Double>();
        
        map1.put("", (double) data[0]);
        map2.put("", (double) data[1]);
        map3.put("", (double) data[2]);
        
        datas.put("准确率", map1);
        datas.put("精确率", map2);
        datas.put("召回率", map3);
        Font font = new Font("宋体", Font.BOLD, 20);
        CharReport_ZZT.createPort("beyes model access",datas,"指标","百分比",font);
	}
}
