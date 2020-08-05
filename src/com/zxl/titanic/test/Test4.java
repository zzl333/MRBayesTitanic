package com.zxl.titanic.test;

import java.util.ArrayList;
import java.util.List;

import com.zxl.titanic.utils.ModelAssess;

public class Test4 {
	public static void main(String[] args) {
		
		List<Integer> list1 = new ArrayList<>();
		List<Integer> list2 = new ArrayList<>();
		list1.add(0);
		list1.add(0);
		list1.add(1);
		list1.add(1);
		
		list2.add(1);
		list2.add(0);
		list2.add(1);
		list2.add(1);
		
		
		ModelAssess modelAssess = new ModelAssess(list1,list2);
		
		System.out.println(modelAssess.getAccuracy());
		System.out.println(modelAssess.getPrecision());
		System.out.println(modelAssess.getRecall());
	}
}
