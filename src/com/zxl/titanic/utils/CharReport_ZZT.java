package com.zxl.titanic.utils;
import java.awt.Font;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.List;
import java.util.Map.Entry;


/**
 *    Created by Jason  2016-7-18  上午8:12:38
 */
public class CharReport_ZZT {

    /**
     * 提供静态方法：获取报表图形2：柱状图
     * @param title        标题
     * @param datas        数据
     * @param type        分类（第一季，第二季.....）
     * @param danwei    柱状图的数量单位
     * @param font        字体
     */
    public static void createPort(String title,Map<String,Map<String,Double>> datas,String type,String danwei,Font font){
          try {
                //种类数据集
                DefaultCategoryDataset ds = new DefaultCategoryDataset();
                
                
                //获取迭代器：
                   Set<Entry<String, Map<String, Double>>> set1 =  datas.entrySet();    //总数据
                   Iterator iterator1=(Iterator) set1.iterator();                        //第一次迭代
                   Iterator iterator2=null;                                                
                   HashMap<String, Double> map =  null;
                   Set<Entry<String,Double>> set2=null;
                   Entry entry1=null;
                   Entry entry2=null;
                   
                   while(iterator1.hasNext()){
                       entry1=(Entry) iterator1.next();                    //遍历分类                    
                      
                       map=(HashMap<String, Double>) entry1.getValue();//得到每次分类的详细信息    
                       set2=map.entrySet();                               //获取键值对集合
                       iterator2=set2.iterator();                        //再次迭代遍历
                       while (iterator2.hasNext()) {
                           entry2= (Entry) iterator2.next();
                           ds.setValue(Double.parseDouble(entry2.getValue().toString()),//每次统计数量
                                          entry2.getKey().toString(),                         //名称
                                          entry1.getKey().toString());                        //分类
                           System.out.println("当前：--- "+entry2.getKey().toString()+"--"
                                         +entry2.getValue().toString()+"--"
                                         +entry1.getKey().toString());
                       }
                       System.out.println("-------------------------------------");
                   }               
            
                //创建柱状图,柱状图分水平显示和垂直显示两种
                JFreeChart chart = ChartFactory.createBarChart(title, type, danwei, ds, PlotOrientation.VERTICAL, true, true, true);
     
                //设置整个图片的标题字体
                chart.getTitle().setFont(font);
     
                //设置提示条字体
                font = new Font("宋体", Font.BOLD, 15);
                chart.getLegend().setItemFont(font);
     
                //得到绘图区
                CategoryPlot plot = (CategoryPlot) chart.getPlot();
                //得到绘图区的域轴(横轴),设置标签的字体
                plot.getDomainAxis().setLabelFont(font);
     
                //设置横轴标签项字体
                plot.getDomainAxis().setTickLabelFont(font);
     
                //设置范围轴(纵轴)字体
                plot.getRangeAxis().setLabelFont(font);
                //存储成图片
     
                //设置chart的背景图片
                chart.setBackgroundImage(ImageIO.read(new File("E:\\eclipsemars\\workspace\\titanic_survived\\src\\com\\zxl\\titanic\\utils\\as.png")));
     
                plot.setBackgroundImage(ImageIO.read(new File("E:\\eclipsemars\\workspace\\titanic_survived\\src\\com\\zxl\\titanic\\utils\\as.png")));
     
                plot.setForegroundAlpha(1.0f);
     
                ChartUtilities.saveChartAsJPEG(new File("E:\\eclipsemars\\workspace\\titanic_survived\\src\\com\\zxl\\titanic\\utils\\new.png"), chart, 600, 400);
            } catch (Exception e) {
                e.printStackTrace();
            }
     }
    
    public static void main(String[] args) {
        
        
        Map<String, Map<String, Double>> datas =new HashMap<String, Map<String,Double>>();
        
        Map<String, Double> map1=new HashMap<String, Double>();
        Map<String, Double> map2=new HashMap<String, Double>();
        Map<String, Double> map3=new HashMap<String, Double>();
        Map<String, Double> map4=new HashMap<String, Double>();
      
        //设置第一期的投票信息
         map1.put("天使-彦", (double) 1000);
         map1.put("雄兵连-蔷薇", (double) 700);
         map1.put("太阳之光-蕾娜", (double) 600);
         map1.put("辅助-琴女", (double) 400);    
         
        //设置第二期的投票信息
         map2.put("天使-彦", (double) 1300);
         map2.put("雄兵连-蔷薇", (double) 900);
         map2.put("太阳之光-蕾娜", (double) 800);
         map2.put("辅助-琴女", (double) 500);    
         
        //设置第三期的投票信息
         map2.put("天使-彦", (double) 2000);
         map3.put("雄兵连-蔷薇", (double) 1700);
         map3.put("太阳之光-蕾娜", (double) 1000);
         map3.put("辅助-琴女", (double) 1000);    
         
        //设置第四期的投票信息
         map4.put("天使-彦", (double) 3000);
         map4.put("雄兵连-蔷薇", (double) 2500);
         map4.put("太阳之光-蕾娜", (double) 1600);
         map4.put("辅助-琴女", (double) 1400);    
         
         //压入数据
         datas.put("第一季", map1);
         datas.put("第二季", map2);
         datas.put("第三季-神与神", map3);
        // datas.put("第四季-黑甲", map4);

        Font font = new Font("宋体", Font.BOLD, 20);
        CharReport_ZZT.createPort("超神学院前四季最受欢迎的女性角色投票结果",datas,"超神纪元","数量单位（票）",font);
    }

}