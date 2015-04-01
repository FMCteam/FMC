package nju.software.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * List工具类
 * 
 * 
 * 
 */
public class ListUtil {

	
	public static List<Map<String, Object>> reserveList(List<Map<String, Object>> list){
		List<Map<String, Object>> newList = new ArrayList<Map<String, Object>>();
		if(list.size() > 0){
			for(int i = list.size()-1 ;i >= 0;i--){
				newList.add(list.get(i));
			}
		}else{
			newList = list;
		}
		
		return newList;
	} 
	
	/*public static void main(String[] args) {
		
		List<People> list = new ArrayList<People>();
		People people1 = new People("1");
		People people2 = new People("2");
		People people3 = new People("3");
		People people4 = new People("4");
		People people5 = new People("5");
		list.add(people1);
		list.add(people2);
		list.add(people3);
		list.add(people4);
		list.add(people5);
		
		JSONObject jsonObject = new JSONObject();
		String string = jsonObject.fromObject(list).toString();
		System.out.println(string);
		
	}*/
}
