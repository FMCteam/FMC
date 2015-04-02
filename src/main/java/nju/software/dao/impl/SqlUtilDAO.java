package nju.software.dao.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import nju.software.dataobject.Account;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class SqlUtilDAO extends HibernateDaoSupport{
	/*private String url = "jdbc:mysql://localhost:3306/fmc?useUnicode=true&amp;characterEncoding=UTF-8";
	private String driver = "com.mysql.jdbc.Driver";
	private String userName = "root";
	private String password = "123";
	static String filePathIn = "";
	*/
	private String url ;
	private String driver;
	private String userName;
	private String password ;
	static String filePathIn ;
	String PROPERTY_PATH_NAME="jdbc.properties";
	String INIT_SQL_All="init.sql";
	String INIT_SQL_other="init_other.sql";
	
	static AccountDAO accountDao ;
	




	/*
	 * properties 初始化 配置
	 */
	   private void init(){
		   try {    
			    String property_path=    getPath("/classes")+PROPERTY_PATH_NAME;
			  // System.out.println("=================="+property_path);
	           // InputStream in = getClass().getResourceAsStream(property_path);    
	            Properties props = new Properties();    
	          //  in.close();
	            props.load(new FileInputStream(property_path));    
	                
	            driver = props.getProperty("jdbc.driverClassName");    
	            url = props.getProperty("jdbc.url");    
	            userName = props.getProperty("jdbc.username");    
	            password = props.getProperty("jdbc.password");    
	        } catch (IOException e) {    
	            e.printStackTrace();    
	        }    
	   }
	
	
	
	
	/*
	 38      * 读取sql文件,获取sql语句
	 39      * 返回所有sql语句的list集合
	 40      * */
	      private List<String> loadSql(String sqlFile) throws Exception {
	         List<String> sqlList = new ArrayList<String>();
	         filePathIn =  setPath(sqlFile);
	         
	         /*
	           * 读取文件的内容并写道StringBuffer中去
	           * */
	         InputStream sqlFileIn = new FileInputStream(filePathIn);
	          StringBuffer sqlSb = new StringBuffer();
	          byte[] buff = new byte[sqlFileIn.available()];
	          int byteRead = 0;
	          while((byteRead = sqlFileIn.read(buff)) != -1) {
	             sqlSb.append(new String(buff, "utf-8"));
	          }
	          /*
	 54          * windows下换行是/r/n，Linux下是/n，
	 55          * 此处需要根据导出的sql文件进行具体的处理，我在处理的时候
	 56          * 也遇到了很多的问题，如果我这个不行可以在网上找找别的解析方法
	 57          * */
	          String sqlArr[] = sqlSb.toString().split("(;\\s*\\rr\\n)|(;\\s*\\n)");
	          for(int i = 0; i<sqlArr.length; i++) {
	              String sql = sqlArr[i].replaceAll("--.*`", "").trim();
	              if(!"".equals(sql)) {
	                  sqlList.add(sql);
	              }
	          }
	          return sqlList;
	          
     }
	      /*
	       * 设置路径
	       */
	      private String setPath(String sqlfile){
	    	  
	    	 return getPath("/nju")+sqlfile;
	    	 	
	    	 	      }
	      /*
	       * 获取路径
	       */
	      private String getPath(String sytnx){
	    	  URL url = this.getClass().getResource("");
	    	  String path =url.getPath();
	    	 //System.out.println("========================="+path);
	    	  return path.substring(0,(path.lastIndexOf(sytnx) + 1));
	    	// return path.substring(1,path.indexOf("classes"));
	      }
	      
	     /*
	 70      * 传入文件执行sql语句
	 71      * 
	 72      * */
	      private void execute(String sqlFile) throws SQLException {
	          Statement stmt = null;
	          List<String> sqlList = new ArrayList<String>();
	         Connection conn = getConnection();
	        /* if(isEmpty(conn)){
	        	 return ;
	         }*/
	        try {
	             sqlList = loadSql(sqlFile);
	             conn.setAutoCommit(false);
	             stmt = conn.createStatement();
	              for (String sql : sqlList) {
	                  //System.out.println(sql);
	                  stmt.addBatch(sql);
	              }
             int[] rows = stmt.executeBatch();
	            // System.out.println("Row count:" + Arrays.toString(rows));
	             conn.commit();
	            System.out.println("数据更新成功");
	         } catch (Exception e) {
	             e.printStackTrace();
	             conn.rollback();
	         }finally{
	              stmt.close();
	             conn.close();
	         }
	         
	      }
	      /*
	       * 批量插入的尝试
	       */
	      private void BatchInsert(String sqlFile){
	    	
	    	  try {
				
	    		  final List<String> sqlList = loadSql(sqlFile);
				
				 this .getHibernateTemplate().execute( new HibernateCallback<Object>(){

					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						// TODO Auto-generated method stub
						
						Transaction transaction = session.beginTransaction(); 
                        for (int i=0;i<sqlList.size();i++){
                        	//System.out.println(sqlList.get(i));
                                   session.createSQLQuery(sqlList.get(i));
                                   
                            if (i%20==0){
                                          session.flush();
                                          session.clear();
                                   }
                             }
                                  
                            transaction.commit();
                            session.close();

						
						
						
						
						return true;
					}
					 
				 }
                     
                ) ;

				
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	      
	      
	      }
	      
	    
	     /*
	100      * 获取sql连接
	101      * */
	     private Connection getConnection(){
	    	 init();
	         Connection conn = null;
	         try {
	             Class.forName(driver);
	             conn = DriverManager.getConnection(url, userName, password);
	             if(!conn.isClosed()) {
	                 System.out.println("==============================数据库连接成功!");
	             }
	         } catch (Exception e) {
	             e.printStackTrace();
	        }
	         return conn;
	    }
	     
	 public void initSQL_All(){
		// setPath();
		 try {
			execute(INIT_SQL_All);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// BatchInsert(filePathIn);
	 }
	/*
	 * 
	 */
	
	 public void initSQL_other(){
		// setPath();
		 try {
			execute(INIT_SQL_other);
			initAccountRole();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	
	 public void initAccountRole(){
			List<Account> list =accountDao.findAll();
			List<Account> newList = new ArrayList();
			Map map = new HashMap();
			map.put("marketManager", "市场主管");
			map.put("marketStaff", "市场专员");
			map.put("designManager", "设计部");
			map.put("purchaseManager", "采购部");
			map.put("produceManager", "生产部");
			map.put("logisticsManager", "物流部");
			map.put("financeManager", "财务部");
			map.put("qualityManager", "质检部");
			map.put("SweaterMakeManager", "毛衣部");
			map.put("ADMIN", "管理员");
			/*String roleList[] ={"marketManager","marketStaff","designManager","purchaseManager","produceManager",
					"logisticsManager","financeManager","qualityManager","SweaterMakeManager","ADMIN"};
			String roleName[]={"市场主管","市场专员","设计部","采购部","生产部","物流部","财务部","质检部","毛衣部","管理员"};
		*/
			System.out.println();
			for(Account b : list){
				
				Object role =  map.get(b.getUserRole());
				if(role!=null){
				//System.out.print(role+",");
				b.setUserRole((String)role);
				newList.add(b);
				accountDao.SaveOrUpDate(b);
				}
			}
			
			
			for(Account a :newList){
				accountDao.addAccountRole(a.getUserRole(), a.getAccountId());
			}
		}
	    
	    
	 public static void setAccountDao(AccountDAO accountDao) {
			SqlUtilDAO.accountDao = accountDao;
		}
	
	

}
