package nju.software.util;

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
import java.util.List;
import java.util.Properties;

public class SqlUtil {
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
	String INIT_SQL_NAME="init.sql";
	/*
	 * properties 初始化 配置
	 */
	   private void init(){
		   try {    
			    String property_path=    getPath("/classes")+PROPERTY_PATH_NAME;
			   System.out.println("=================="+property_path);
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
	        /*
	           * 读取文件的内容并写道StringBuffer中去
	           * */
	         InputStream sqlFileIn = new FileInputStream(sqlFile);
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
	              String sql = sqlArr[i].replaceAll("--.*", "").trim();
	              if(!"".equals(sql)) {
	                  sqlList.add(sql);
	              }
	          }
	          return sqlList;
	          
     }
	      /*
	       * 设置路径
	       */
	      private void setPath(){
	    	  
	    	  filePathIn= getPath("/nju")+INIT_SQL_NAME;
	    	 	
	    	 	      }
	      /*
	       * 获取路径
	       */
	      private String getPath(String sytnx){
	    	  URL url = this.getClass().getResource("");
	    	  String path =url.getPath();
	    	 System.out.println("========================="+path);
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
	                  System.out.println(sql);
	                  stmt.addBatch(sql);
	              }
             int[] rows = stmt.executeBatch();
	             System.out.println("Row count:" + Arrays.toString(rows));
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
	      * 判断是否可以插入
	      * 
	      */
	      public boolean isEmpty(Connection conn) throws SQLException{
	    	  Statement st2=  conn.createStatement();
		         String query="select * from order";
		         ResultSet rs =st2.executeQuery(query);
		         boolean is_prepared=rs.first();
		         return is_prepared;
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
	 public void initSQL(){
		 try {
			 setPath();
			execute(filePathIn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			new SqlUtil().execute(filePathIn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
