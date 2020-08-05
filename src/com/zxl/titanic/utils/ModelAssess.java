package com.zxl.titanic.utils;

import java.util.List;

/**
 * 
 * <p>Title: ModelAssess</p>  
 * <p>Description: 模型评估</p>  
 * @author zxl 
 * @date 2020年5月30日
 */
public class ModelAssess {
	
	//样本值
	private List<Integer> list1 = null;
	//预测值
	private List<Integer> list2 = null;
	//真正值
	private  Integer TP = 0;
	//假正值
	private  Integer FP = 0;
	//假正值
	private  Integer FN = 0;
	//真负值
	private  Integer TN = 0;
	
	
	public ModelAssess(List<Integer> list1,List<Integer> list2){
		this.list1 = list1;
		this.list2 = list2;
		setup();
	}
	
	private void setup() {
		// TODO Auto-generated method stub
		for(int i = 0; i< list1.size(); i++){
			if(list1.get(i) == list2.get(i)){
				if(list1.get(i) == 1)
					TP ++;
				else
					TN ++;
			}else{
				if(list1.get(i) == 0)
					FN ++;
				else
					FP ++;
			}

		}
	}

	/**
	 * <p>Title: getAccuracy</p>  
	 * <p>Description: 准确率</p>  
	 * @return
	 */
	public float getAccuracy(){
		return (float)(TP + TN) / list1.size(); 
	}
	
	/**
	 * 
	 * <p>Title: getErrorRate</p>   
	 * <p>Description: 准确率</p>  
	 * @return	错误率
	 */
	public  float getErrorRate(){
		return 1 - getAccuracy();
	}
	
	/**
	 * 
	 * <p>Title: getPrecision</p>  
	 * <p>Description: 精度</p>  
	 * @return
	 */
	public  float getPrecision(){
		return (float)TP/(TP+FP);
	}
	
	/**
	 * 
	 * <p>Title: getRecall</p>  
	 * <p>Description: 召回率</p>  
	 * @return
	 */
	public  float getRecall(){
		return (float)TP/(TP+FN);
	}
	
	/**
	 * 
	 * <p>Title: getSensitivity</p>  
	 * <p>Description: 灵敏度</p>  
	 * @return
	 */
	public  float getSensitivity(){
		return (float)TP/(TP+FN);
	}
	
}
