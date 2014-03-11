package nju.software.util;

import java.io.File;
import java.util.ArrayList;

public class Constants {
	public final static String USER_TYPE_EMPLOYEE="EMPLYEE";
	public final static String USER_TYPE_CUSTOMER="CUSTOMER";
	public final static String USER_TYPE_ADMIN="ADMIN";
	public final static String USER_ROLE_ADMIN="ADMIN";
	public final static String USER_ROLE_CUSTOMER="CUSTOMER";
	public final static String CUSTOMER_DEFAULT_PASSWORD="123456";
	
	//开题报告状态
	public final static int PROPOSAL_UNCHECKED = 0;
	public final static int PROPOSAL_PASSED = 1;
	public final static int PROPOSAL_FAILED = 2;
	
	//中期检查状态
	public final static int INTERIM_REPORT_UNCHECKED = 0;
	public final static int INTERIM_REPORT_PASSED = 1;
	public final static int INTERIM_REPORT_FAILED = 2;
	
	//论文状态
	public final static String UNCOMMITTED = "uncommitted";//未提交
	public final static String DRAFT_COMPLETED = "draft completed";//完成初稿
	public final static String THE_FIRST_FINALIZED = "first finalized";//初步定稿
	public final static String DETECTION_PASSED = "detection passed";//重复率检测通过
	public final static String JUDGE_READY ="judge ready";//评审分配完成，待评审
	public final static String JUDGE_PASSED ="judge passed";//评审通过
	public final static String DEFENSE_FINALIZED_BEFORE ="defense finalized before";//答辩前定稿完成
	public final static String DEFENSE_READY ="defense ready";//答辩分配完成，待答辩 
	public final static String DEFENSE_SUCCESS ="defense success"; //通过答辩
	public final static String REPRIEVE ="reprieve"; //暂缓通过
	public final static String FINALIZED_AFTER_REPRIEVE = "finalized after reprieve";//暂缓后定稿完成
	public final static String FINALIZED_AFTER_DEFENSE = "finalized after defense";//答辩后定稿完成
	
	//Account Level
	public final static int DEAN = 1;//1代表教务员
	public final static int TEACHER = 2;//2代表指导老师
	public final static int STUDENT = 3;//3代表学生
	public final static int ADMIN = 4;//管理员
	
	public final static String ACTOR_DEAN = "dean";//actorLabel中代表教务员
	public final static String ACTOR_TEACHER = "teacher";//actorLabel中代表指导老师
	public final static String ACTOR_STUDENT = "student";//actorLabel中代表学生
	public final static String ACTOR_ADMIN = "admin";
	
	//Teacher
	//类型
	public final static int INNERTEACHER = 1;//校内教师
	public final static int OUTERTEACHER = 2;//校外教师
	//学历
	public final static int BACHELOR = 1;//本科
	public final static int MASTER = 2;//硕士
	public final static int DOCTOR = 3;//博士
	public final static int POSTDOCTOR = 4;//博士后
	//职称
	public final static int INSTRUCTOR = 1;//讲师
	public final static int SENIORENGINEER = 2;//高级工程师
	public final static int ASSOCIATEPROFESSOR = 3;//副教授
	public final static int FULLPROFESSOR = 4;//教授
	//资质
	public final static int ENGINEERING_MASTER_TUTOR = 1;//工程硕士导师
	public final static int ACADEMIC_MASTER_TUTOR = 2;//工学硕士导师
	public final static int ACADEMIC_DOCTOR_TUTOR = 3;//博导
	
	//Student
	public final static int FULL_TIME = 1;//全日制
	public final static int PART_TIME = 2;//在职
	
	public final static int ENGINEERING_MASTER = 1;//工程硕士
	public final static int ACADEMIC_MASTER = 2;//工学硕士
	public final static int ACADEMIC_DOCTOR = 3;//3代表工学博士
	public final static int ERROR_INPUT=-1;

	//Fee
	public final static ArrayList<String> GRANT_TYPE = new ArrayList<String>(){{ 
		add("学院记账");
		add("学院现金");
		add("个人现金");
	}};
	
	public final static ArrayList<String> FEE_TYPE = new ArrayList<String>(){{
		add("导师费");
		add("挂名导师费");
		add("评审费");
		add("答辩主席费");
		add("答辩评委费");
		add("答辩秘书费");
		add("教务员费");
	}};
	
	//Excel
	public final static ArrayList<String> TEACHER_HEADER = new ArrayList<String>(){{ 
		add("编号");
		add("姓名");
		add("类别");
		add("学历");
		add("职称");
		add("资质");
		add("联系电话");
		add("邮件地址");
	}};
	
	public final static ArrayList<String> STUDENT_HEADER = new ArrayList<String>(){{ 
		add("学号");
		add("姓名");
		add("年级");
		add("班级");
		add("攻读学位");
		add("类型");
		add("联系电话");
		add("备用电话");
		add("邮件地址");
	}};
	public final static ArrayList<String> DEFENSE_FEE_HEADER = new ArrayList<String>(){{ 
		add("学号");
		add("姓名");
		add("答辩评委1");
		add("答辩费1");
		add("答辩评委2");
		add("答辩费2");
		add("答辩评委3");
		add("答辩费3");
		add("答辩秘书");
		add("答辩秘书费");
		add("发放形式");
		add("发放日期");
	}};
	public final static ArrayList<String> _FEE_HEADER = new ArrayList<String>(){{ 
		add("学号");
		add("姓名");
		add("评审人");
		add("评审费");
		add("发放形式");
		add("发放日期");
		
	}};
	public final static int EXCEL_COLUMN_WITH1 = 3000;// 普通表格宽度
	public final static int EXCEL_COLUMN_WITH2 = 6000;// 邮件选项表格宽度

	public final static String SAVE_DIRECTORY = System.getProperty("web.root")
			+ "document" + File.separatorChar + "excels";

}
