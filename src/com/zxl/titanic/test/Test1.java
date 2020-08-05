package com.zxl.titanic.test;

import java.util.HashMap;

/*
 * PassengerId,Survived,Pclass,Name,Sex,Age,SibSp,Parch,Ticket,Fare,Cabin,Embarked
 * 43,0,3,"Kraeff, Mr. Theodor",male,,0,0,349253,7.8958,,C
 */
public class Test1 {
	public static void main(String[] args) {
//		String str = "43,0,3,\"Kraeff, Mr. Theodor\",male,,0,0,349253,7.8958,,C";
//		for (String string : str.split(",")) {
//			System.out.println(string);
//		}
//		System.out.println(str.split(",").length);
//		String a = "aaa";
//		String b = "bbbb";
//		System.out.println(a+b);
		int a = 5;
		if(a > 4){
			a=4;
		}else if(a >3){
			a = 3;
		}else{
			a=2;
		}
		System.out.println(a);
	}
}
