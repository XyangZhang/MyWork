package com.hoom.detectingforelder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppUtil {

	public List<Map<String, Object>> getData() {            
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();             
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("addr", "医院1");            //列表每个选项包含2个对象
		map.put("info", "地址:******");   
		map.put("go", R.drawable.list_go);
		list.add(map);                 
		map = new HashMap<String, Object>();   
		map.put("addr", "医院2");            
		map.put("info", "地址:******");  
		map.put("go", R.drawable.list_go);
		list.add(map);                 
		map = new HashMap<String, Object>();  
		map.put("addr", "医院3");            
		map.put("info", "地址:******");
		map.put("go", R.drawable.list_go);
		list.add(map);         
		map = new HashMap<String, Object>();  
		map.put("addr", "医院4");            
		map.put("info", "地址:******");
		map.put("go", R.drawable.list_go);
		list.add(map);
		map = new HashMap<String, Object>();  
		map.put("addr", "医院5");            
		map.put("info", "地址:******");
		map.put("go", R.drawable.list_go);
		list.add(map);
		return list;        
		}
	
	public static ArrayList<Integer> getImg1(){
		ArrayList<Integer> imgList=new ArrayList<Integer>();
		imgList.add(R.drawable.story1_1);
		imgList.add(R.drawable.story1_2);
		imgList.add(R.drawable.story1_3);
		imgList.add(R.drawable.story1_4);
		imgList.add(R.drawable.story1_5);
		imgList.add(R.drawable.story1_6);
		imgList.add(R.drawable.story1_7);
		return  imgList;
		
	}
}
