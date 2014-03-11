package nju.software.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nju.software.dataobject.Pagination;

import org.apache.commons.beanutils.BeanUtils;


public class CommonUtil {
	public static java.sql.Date getSqlDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = sdf.format(new Date());
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			throw new AppException("字符串转换日期类型异常");
		}
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		return sqlDate;
	}
   public static String getMaxDay(){
		String[] dates = CommonUtil.getSqlDate().toString().split("-");
		return dates[0]+"-"+dates[1]+"-"+"1";
   } 

	public static java.sql.Date stringToSqlDate(String da) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = dateFormat.parse(da);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			throw new AppException("字符串转换日期类型异常");
		}
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		return sqlDate;
	}
	public static Date s2d(String da) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = null;
		try {
			date = dateFormat.parse(da);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			throw new AppException("字符串转换日期类型异常");
		}
		return date;
	}

	public static Pagination getPagination(int totalNum) {
		Pagination page = new Pagination();
		if (totalNum % page.getPageSize()== 0) {
			page.setMaxPage(totalNum / page.getPageSize());
		}
		if (totalNum % page.getPageSize() != 0) {
			page.setMaxPage(totalNum / page.getPageSize() + 1);
		}
		return page;
	}
	public static Pagination getPagination(Pagination page,int totalNum) {
		if (totalNum % page.getPageSize() == 0) {
			page.setMaxPage(totalNum / page.getPageSize());
		}
		if (totalNum % page.getPageSize() != 0) {
			page.setMaxPage(totalNum / page.getPageSize() + 1);
		}
		return page;
	}
   public static List<Object> getPaginationList(List<Object> list,Pagination page){
	   List<Object> newList=new ArrayList<Object>();
	   int j=0;
	   if(list!=null){
           for(int i=0;i<list.size();i++){
        	   if(i>=(page.getPage()-1) * page.getPageSize()){
        		   newList.add(list.get(i));
        		   j++;
        		   if(j==page.getPageSize()){
        			   break;
        		   }
        	   }
           }   
           
	   }
	   return newList;
   }

	public static String[] getFromTo(String dateTo) {
		Integer year = new Integer(dateTo.substring(0, 4));
		Integer month = new Integer(dateTo.substring(4));
		String[] date = new String[2];
		date[0] = year + "-" + month + "-" + "01";
		String[] days = new String[] { "0", "31", "28", "31", "30", "31", "30",
				"31", "31", "30", "31", "30", "31" };
		if (month == 2) {
			if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
				days[2] = "29";
			}
		}
		for (int i = 1; i <= 12; i++) {
			if (month == i) {
				date[1] = year + "-" + month + "-" + days[i];
			}
		}
		return date;
	}

	public static String getDay() {
		String[] dates = CommonUtil.getSqlDate().toString().split("-");
		int year = new Integer(dates[0]);
		int month = new Integer(dates[1]).intValue() - 1;
		String day = null;
		if (month == 0) {
			year = year - 1;
			month = 12;
		}
		String[] days = new String[] { "0", "31", "28", "31", "30", "31", "30",
				"31", "31", "30", "31", "30", "31" };
		if (month == 2) {
			if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
				days[2] = "29";
			}
		}
		for (int i = 1; i < 13; i++) {
			if (month == i) {
				day = days[i];
				break;
			}
		}
		return day;
	}
	public static String getDays() {
		String[] dates = CommonUtil.getSqlDate().toString().split("-");
		int year = new Integer(dates[0]);
		int month = new Integer(dates[1]).intValue();
		String day = null;
		
		String[] days = new String[] { "0", "31", "28", "31", "30", "31", "30",
				"31", "31", "30", "31", "30", "31" };
		if (month == 2) {
			if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
				days[2] = "29";
			}
		}
		for (int i = 1; i < 13; i++) {
			if (month == i) {
				day = days[i];
				break;
			}
		}
		return day;
	}
	public static String getDate() {
		String date = CommonUtil.getSqlDate().toString();
		String[] dates = date.split("-");
		int year = new Integer(dates[0]).intValue();
		int month = new Integer(dates[1]).intValue() - 1;
		if (month == 0) {
			year = new Integer(year) - 1;
			month = 12;
		}
		String m=month+"";
		if(month<10){
			m="0"+m;
		}
		return "" + year + m;
	}
   public static double getDayScale(String[] date){
	   String[] days = new String[] { "0", "31", "28", "31", "30", "31", "30",
				"31", "31", "30", "31", "30", "31" };
	   String day = null;
	   for(int i=1;i<days.length;i++){
		   if(new Integer(date[1]).intValue()==i){
			   day=days[i];
			   break;
		   }
	   }
	   return (new Integer(day)-new Integer(date[2]))*1.0/new Integer(day);
   }
	public static double change(double va) {
		String[] value = new Double(va).toString().split("\\.");
		int decimal1 = new Integer(value[1].substring(0, 1)).intValue();
		double integer = new Double(value[0]);
		int decimal2 = 0;
		int decimal3 = 0;
		if (value[1].length() > 1) {
			decimal2 = new Integer(value[1].substring(1, 2)).intValue();
		}
		if (value[1].length() > 2) {
			decimal3 = new Integer(value[1].substring(2, 3)).intValue();
			if(decimal3>4){
				decimal2++;
			}
		}
		return new Double(integer*100+decimal1*10+decimal2)/100;
	}
 
	public static boolean isYearVacation(Date startDate) {
		String[] end = CommonUtil.getSqlDate().toString().split("-");
		String[] start = startDate.toString().split("-");
		if (new Integer(end[0]).intValue() - new Integer(start[0]).intValue() > 0) {
			if (new Integer(end[1]).intValue()
					- new Integer(start[1]).intValue() == 0) {
				if (new Integer(end[2]).intValue()
						- new Integer(start[2]).intValue()>=0) {
					return true;
				}
			}
			if (new Integer(end[1]).intValue()
					- new Integer(start[1]).intValue() > 0) {
				return true;
			}
		}
		return false;
	}

	public static Short getYear() {
		String[] date = CommonUtil.getSqlDate().toString().split("-");
		return new Short(date[0]);
	}

	@SuppressWarnings("unchecked")
	public static void copy(Object obj, Object objf) throws Exception {
		Class stu = obj.getClass();

		// Method[] mStu=stu.getMethods();
		Field[] fStu = stu.getDeclaredFields();
		Class stuForm = objf.getClass();
		// Method[] mStuForm=stu.getMethods();
		Field[] fStuForm = stuForm.getDeclaredFields();
		for (Field fstuForm : fStuForm) {
			for (Field fstu : fStu) {
				if (fstuForm.getName().equals("serialVersionUID")) {
					continue;
				}
				if (fstuForm.getName().equals(fstu.getName())) {
					Method mStuFormMethod = stuForm.getMethod("get"
							+ change(fstuForm.getName()), null);
					Method mStuMethod = stu.getMethod("set"
							+ change(fstuForm.getName()), fstu.getType());
					if (fstu.getType().toString()
							.equals("class java.util.Date")
							|| fstu.getType().toString().equals(
									"class java.sql.Date")) {

						if (mStuFormMethod.invoke(objf, null) != null
								&& !""
										.equals(mStuFormMethod.invoke(objf,
												null))) {
							mStuMethod.invoke(obj,s2d(mStuFormMethod.invoke(
											objf, null).toString()));

						}
					} else if (fstu.getType().toString().equals("int")
							|| fstu.getType().toString().equals("double")
							|| fstu.getType().toString().equals("float")
							|| fstu.getType().toString().equals("boolean")
							|| fstu.getType().toString().equals("long")
							|| fstu.getType().toString().equals("short")
							|| fstu.getType().toString().equals(
									"class java.lang.Integer")
							|| fstu.getType().toString().equals(
									"class java.lang.Double")
							|| fstu.getType().toString().equals(
									"class java.lang.Float")
							|| fstu.getType().toString().equals(
									"class java.lang.Boolean")
							|| fstu.getType().toString().equals(
									"class java.lang.Long")
							|| fstu.getType().toString().equals(
									"class java.lang.Short")
							|| fstu.getType().toString().equals(
									"class java.lang.String")) {

						if (mStuFormMethod.invoke(objf, null) != null
								&& !""
										.equals(mStuFormMethod.invoke(objf,
												null))) {
						
							mStuMethod.invoke(obj, mStuFormMethod.invoke(objf,
									null));
							
						}
					} else {
						if (fstuForm.getType() == fstu.getType()) {
							mStuMethod.invoke(obj, mStuFormMethod.invoke(objf,
									null));
							continue;
						}
						Class clazz = Class.forName(fstu.getType().toString()
								.substring(6));
						Object children = clazz.newInstance();
						Field[] fchild = clazz.getDeclaredFields();
						for (Field fch : fchild) {
							if (fch.getName().substring(
									fch.getName().length() - 2).equals("Id")) {
								Method mch = clazz.getMethod("set"
										+ change(fch.getName()), fch.getType());
								if (mStuFormMethod.invoke(objf, null) != null
										&& !"".equals(mStuFormMethod.invoke(
												objf, null))) {
									
									mch.invoke(children, mStuFormMethod.invoke(
											objf, null));
								}
								break;
							}
						}
						mStuMethod.invoke(obj, children);
					}
				}
			}
		}

	}

	public static String change(String src) {
		if (src != null) {
			StringBuffer sb = new StringBuffer(src);
			sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
			return sb.toString();
		} else {
			return null;
		}
	}
	// --------------------@author kaka开始----------------------
	/**
	 * 
	 * @param dest
	 * @param orig
	 */
	public static void copyProperties(Object dest, Object orig) {
		try {
			BeanUtils.copyProperties(dest, orig);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	// --------------------@author kaka结束----------------------

}
