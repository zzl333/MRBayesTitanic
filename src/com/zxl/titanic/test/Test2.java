package com.zxl.titanic.test;

import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

import com.zxl.titanic.utils.CharReport_ZZT;

public class Test2 {
	public static void main(String[] args) {
        Map<String, Map<String, Double>> datas =new HashMap<String, Map<String,Double>>();
        
        Map<String, Double> map1=new HashMap<String, Double>();
        Map<String, Double> map2=new HashMap<String, Double>();
        Map<String, Double> map3=new HashMap<String, Double>();
        Map<String, Double> map4=new HashMap<String, Double>();
        
        map1.put("", (double) 0.81111f);
        map2.put("", (double) 0.81111f);
        map3.put("", (double) 0.81111f);
        map4.put("", (double) 0.81111f);
        
        datas.put("准确性", map1);
        datas.put("精度", map2);
        datas.put("错误", map3);
        datas.put("敏感度", map4);
        
        
        Font font = new Font("宋体", Font.BOLD, 20);
        CharReport_ZZT.createPort("beyes model access",datas,"指标","百分比",font);
	}
}
