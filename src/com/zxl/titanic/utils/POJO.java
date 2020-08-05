package com.zxl.titanic.utils;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

/**
 * 数据清洗model对象
 * <p>Title: POJO</p>  
 * <p>Description: </p>  
 * @author zxl 
 * @date 2020年6月6日
 */
public class POJO implements Writable {
	// 乘客舱位等级（包含1/2/3级舱位）
	private Integer Pclass;
	// 性别0男1
	private Integer Sex;
	/*
	 * 年龄区间映射 1-10，11-20，21-30，31-40，41-50，51-60，61-70，71-80， 空白 1
	 * ，2，3，4，5，6，7，8，0
	 */
	private Integer Age;
	// 堂兄妹个数（平辈，相当于同龄人）
	private Integer SibSp;
	// 父母与孩子个数
	private Integer Parch;
	// 船票价格
	private Integer Fare;
	// 登船港口 1c，2q，3s 空白为0
	private Integer Embarked;

	public Integer getPclass() {
		return Pclass;
	}

	public void setPclass(Integer pclass) {
		Pclass = pclass;
	}

	public Integer getSex() {
		return Sex;
	}

	public void setSex(Integer sex) {
		Sex = sex;
	}

	public Integer getAge() {
		return Age;
	}

	public void setAge(Integer age) {
		Age = age;
	}

	public Integer getSibSp() {
		return SibSp;
	}

	public void setSibSp(Integer sibSp) {
		SibSp = sibSp;
	}

	public Integer getParch() {
		return Parch;
	}

	public void setParch(Integer parch) {
		Parch = parch;
	}

	public Integer getFare() {
		return Fare;
	}

	public void setFare(Integer fare) {
		Fare = fare;
	}

	public Integer getEmbarked() {
		return Embarked;
	}

	public void setEmbarked(Integer embarked) {
		Embarked = embarked;
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		// TODO Auto-generated method stub
		this.Pclass = in.readInt();
		this.Sex = in.readInt();
		this.Age = in.readInt();
		this.SibSp = in.readInt();
		this.Parch = in.readInt();
		this.Fare = in.readInt();
		this.Embarked = in.readInt();
	}

	@Override
	public void write(DataOutput out) throws IOException {
		// TODO Auto-generated method stub
		out.writeInt(this.Pclass);
		out.writeInt(this.Sex);
		out.writeInt(this.Age);
		out.writeInt(this.SibSp);
		out.writeInt(this.Parch);
		out.writeInt(this.Fare);
		out.writeInt(this.Embarked);
	}

	@Override
	public String toString() {
		return Pclass + "," + Sex + "," + Age + "," + SibSp + "," + Parch + "," + Fare + "," + Embarked;
	}

}
