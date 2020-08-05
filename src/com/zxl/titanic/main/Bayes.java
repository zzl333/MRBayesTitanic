package com.zxl.titanic.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.fs.FSDataOutputStream;

import com.zxl.titanic.etc.ETCMR;
import com.zxl.titanic.predict.PredictMR;
import com.zxl.titanic.train.TrainMR;
import com.zxl.titanic.utils.HdfsUtil;

/**
 * 使用bayes算法实现titanic生存预测分类任务
 * 
 * @author zxl
 * @date 2020年5月30日
 */
public class Bayes {

	/**
	 * 存储先验分类的count DEAD:count(DAED) SURVIVED:count(SURVIVED)
	 */
	private  Map<String, Long> run = null;
	/**
	 * 临时存储输出目录
	 */
	private String tempoutput = "/user/root/tempredict";
	
	public String getTempoutput() {
		return tempoutput;
	}

	public void setTempoutput(String tempoutput) {
		this.tempoutput = tempoutput;
	}
	
	

	public Map<String, Long> getRun() {
		return run;
	}

	public void setRun(Map<String, Long> run) {
		this.run = run;
	}

	/**
	 * 
	 * <p>Title: etc</p>  
	 * <p>Description: 清洗数据</p>  
	 * @param args 输入路径  输出路径 超参数
	 */
	public void etc(String[] args) {
		try {
			run = ETCMR.run(args);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		tempoutput = args[1];
	}

	/**
	 * 
	 * <p>Title: train</p>  
	 * <p>Description: 训练模型</p>  
	 * @param outputpath	模型数据保存路径
	 * @throws Exception
	 */
	public void train(String outputpath) {
		try {
			if (run == null)
				throw new Exception("Please clean the model data first");
			String[] args = new String[4];
			args[0] = tempoutput;
			args[1] = outputpath;
			args[2] = run.get("DAED").toString();
			args[3] = run.get("SURVIVED").toString();
			if (TrainMR.run(args) == 0)
				throw new Exception("Execution failed due to unknown error");
			tempoutput = outputpath;
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	/**
	 * 
	 * <p>Title: predict</p>  
	 * <p>Description: 预测数据</p>  
	 * @param inputpath	输入数据路径
	 * @param outputpath 输出数据路径
	 * @return	预测结果list
	 */
	public List<Integer> predict(String inputpath,String outputpath) {
		//[输入路径，输出，deadcount， survivedcount,etc后测验的数据]
		List<Integer> list = new ArrayList<>();
		//1、清洗数据
		String[] args = new String[5];
		args[0] = inputpath;
		args[1] = tempoutput;
		etc(args);
		//2、读取数据
		BufferedReader inputStream = null;
		try {
			inputStream = HdfsUtil.getInputStream(args[1] + "/part-r-00000");
		
			//3、遍历数据，并分类测验
			String str = null;
			args[0] = "/user/root/trainout/part-r-00000";
			args[1] = outputpath;
			args[2] = run.get("DEAD").toString();
			args[3] = run.get("SURVIVED").toString();
			
			while((str = inputStream.readLine()) != null){
				args[4] = str;
				if(PredictMR.run(args) != 0 && PredictMR.run(args) != 1)
					list.add(-1);
				else
					list.add(PredictMR.run(args));
			}
				//4、将预测结果放入文件中存储
				FSDataOutputStream outputStream = HdfsUtil.getOutputStream(tempoutput + "/list2");
				outputStream.writeUTF(list.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		//5、返回预测结果以list形式
		return list;
	}
	
}
