package com.zxl.titanic.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class HdfsUtil {
    public static FileSystem getFileSystem() throws Exception{
        Configuration conf = new Configuration();
        FileSystem fileSystem = FileSystem.get(conf);
        return fileSystem;
    }

    public static BufferedReader getInputStream(String filename) throws Exception{
        FileSystem fileSystem = getFileSystem();
        Path readPath = new Path(filename);
        FSDataInputStream inStream = fileSystem.open(readPath);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inStream));
        return bufferedReader;
    }
    
    public static FSDataOutputStream getOutputStream(String filename) throws Exception{
        FileSystem fs = getFileSystem();
        Path path = new Path(filename);
        if(fs.exists(path)){
        	fs.delete(path, true);
        }
        return fs.create(path);
    }



	public static void main(String[] args) throws Exception {

//        String filename = "/user/beifeng/mapreduce/input/word";
//        FSDataInputStream inputStream = getInputStream(filename);
//        //从inputstream中读取一行数据，最好使用转换流转换到bufferedreader包装流
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//        String str;
//        while((str = bufferedReader.readLine()) != null){
//        	System.out.println(str);
//        }
   }
}