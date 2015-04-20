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
}
