package nju.software.util;

import java.io.File;


public class Constants {
	public final static String USER_TYPE_EMPLOYEE="EMPLYEE";
	public final static String USER_TYPE_CUSTOMER="CUSTOMER";
	public final static String USER_TYPE_ADMIN="ADMIN";
	public final static String USER_ROLE_ADMIN="ADMIN";
	public final static String USER_ROLE_CUSTOMER="CUSTOMER";
	public final static String CUSTOMER_DEFAULT_PASSWORD="123456";
	
	
	public final static int EXCEL_COLUMN_WITH1 = 3000;// 普通表格宽度
	public final static int EXCEL_COLUMN_WITH2 = 6000;// 邮件选项表格宽度

	public final static String SAVE_DIRECTORY = System.getProperty("web.root")
			+ "document" + File.separatorChar + "excels";
	
	/**当前登录用户*/
	public final static String PARAM_CUR_ACCOUNT = "cur_user";
	//json返回数据参数说明
	public final static String JSON_IS_SUCCESS = "isSuccess";
	public final static String JSON_ACCOUNT = "account";
	/**客户列表*/
	public final static String JSON_CUSTOMERS = "customers";
	/**单个客户*/
	public final static String JSON_CUSTOMER = "customer";
	/**消息*/
	public final static String JSON_NOTIFY = "notify";
	/**员工列表*/
	public final static String JSON_EMPLOYEE_LIST = "employeeList";
	/**员工名字*/
	public final static String JSON_EMPLOYEE_NAME = "employee_name";
	
	//request参数说明
	/**sessionId*/
	public final static String PARAM_SESSION_ID = "jsessionId";
	public final static String PARAM_PAGE = "page";
	
}
