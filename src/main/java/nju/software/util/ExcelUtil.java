package nju.software.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.lang.reflect.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.Cell;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


public class ExcelUtil
{
    /** *//** 总行数 */
    private int totalRows = 0;
    
    /** *//** 总列数 */
    private int totalCells = 0;
    
    /** *//** 构造方法 */
    public ExcelUtil()
    {}
    
	/**
	 * 增加一个学生的信息
	 * @param fliename
	 * 要增加的学生的信息对象
	 * @return
	 * 成功返回增加的学生信息对象，失败返回null
	 * @throws Exception 
	 */
    public List<ArrayList<String>> read(String fileName)
    {
        List<ArrayList<String>> dataLst = new ArrayList<ArrayList<String>>();
        
        /** *//** 检查文件名是否为空或者是否是Excel格式的文件 */
        if (fileName == null || !fileName.matches("^.+\\.(?i)((xls)|(xlsx))$"))
        {
            return dataLst;
        }
        
        boolean isExcel2003 = true;
        /** *//** 对文件的合法性进行验证 */
        if (fileName.matches("^.+\\.(?i)(xlsx)$"))
        {
            isExcel2003 = false;
        }
        
        /** *//** 检查文件是否存在 */
        File file =new File(fileName);
        if (file == null || !file.exists())
        {
            return dataLst;
        }
        
        try
        {
            /** *//** 调用本类提供的根据流读取的方法 */
            dataLst = read(new FileInputStream(file), isExcel2003);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
 
        /** *//** 返回最后读取的结果 */
        return dataLst;
    }
    
    /** *//**
     * <ul>
     * <li>Description:[根据流读取Excel文件]</li>
     * <li>Created by [Huyvanpull] [Jan 20, 2010]</li>
     * <li>Midified by [modifier] [modified time]</li>
     * <ul>
     * 
     * @param inputStream
     * @param isExcel2003
     * @return
     */
    public List<ArrayList<String>> read(InputStream inputStream,
            boolean isExcel2003)
    {
        List<ArrayList<String>> dataLst = null;
        try
        {
            /** *//** 根据版本选择创建Workbook的方式 */
            Workbook wb = isExcel2003 ? new HSSFWorkbook(inputStream)
                    : new XSSFWorkbook(inputStream);
            dataLst = read(wb);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return dataLst;
    }
    
    /** *//**

     * 
     * @return
     */
    public int getTotalRows()
    {
        return totalRows;
    }
    
    /** *//**
 
     * 
     * @return
     */
    public int getTotalCells()
    {
        return totalCells;
    }
    
    /** *//**

     * 
     * @param wb
     * @return
     */
    private List<ArrayList<String>> read(Workbook wb)
    {
        List<ArrayList<String>> dataLst = new ArrayList<ArrayList<String>>();
        
        /** *//** 得到第一个shell */
        Sheet sheet = wb.getSheetAt(0);
        this.totalRows = sheet.getPhysicalNumberOfRows();
        if (this.totalRows >= 1 && sheet.getRow(0) != null)
        {
            this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
        }
        
        /** *//** 循环Excel的行 */
        for (int r = 0; r < this.totalRows; r++)
        {
            Row row = sheet.getRow(r);
            if (row == null)
            {
                continue;
            }
            
            ArrayList<String> rowLst = new ArrayList<String>();
            /** *//** 循环Excel的列 */
            for (short c = 0; c < this.getTotalCells(); c++)
            {
                Cell cell = row.getCell(c);
                String cellValue = "";
                if (cell == null)
                {
                    rowLst.add(cellValue);
                    continue;
                }
                
                /** *//** 处理数字型的,自动去零 */
                if (Cell.CELL_TYPE_NUMERIC == cell.getCellType())
                {
                	 if (DateUtil.isCellDateFormatted(cell)) {   //如果是日期
                        double d = cell.getNumericCellValue();   
                       
                    Timestamp time=new Timestamp( DateUtil.getJavaDate(d).getTime());   
                     
                       cellValue=time.toString();
                       System.out.println(cellValue);
                    } else{  
                        cellValue = getRightStr(cell.getNumericCellValue() + "");
                        
                }}
                /** *//** 处理字符串型 */
                else if (Cell.CELL_TYPE_STRING == cell.getCellType())
                {
                    cellValue = cell.getStringCellValue();
                }
                /** *//** 处理布尔型 */
                else if (Cell.CELL_TYPE_BOOLEAN == cell.getCellType())
                {
                    cellValue = cell.getBooleanCellValue() + "";
                }
                /** *//** 其它的,非以上几种数据类型 */
                else
                {
                    cellValue = cell.toString() + "";
                }
                
                rowLst.add(cellValue);
            }
            dataLst.add(rowLst);
        }
        return dataLst;
    }
    
    
    public List<ArrayList<String>> importExcel(HttpServletRequest request,
			HttpServletResponse response,String para) throws IllegalStateException, IOException{
		try {
			request.setCharacterEncoding("UTF-8");
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			// 获得文件：
			MultipartFile mfile = multipartRequest.getFile(para);
			String filename = mfile.getOriginalFilename();
             System.out.println(filename);
			File save = new File(Constants.SAVE_DIRECTORY,filename);
			if (save.exists() == false) {
				save.mkdirs();
			}

			mfile.transferTo(save);
			List<ArrayList<String>> dataLst = read(Constants.SAVE_DIRECTORY + File.separatorChar + filename);
			return dataLst;
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			return null;
		}
	   
	}
    //
    
    
  	/**
  	 * 将数据库对象导出为Excel
  	 * @param fliename
  	 * 对象链表
  	 * @return
  	 *workBook失败返回null
  	 * @throws Exception 
  	 */
    public  HSSFWorkbook exportExcel(List<Object> list){
    	Class<? extends Object> head=list.get(0).getClass();
    	HSSFWorkbook wb = new HSSFWorkbook();  
         HSSFSheet sheet = wb.createSheet(head.getName()+"列表");  //Java反射
         HSSFRow row0 = sheet.createRow(0);  
        HSSFCellStyle style = wb.createCellStyle();  
        style.setAlignment(CellStyle.ALIGN_CENTER);  
        Field[] fields = head.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {  //添加表头
              HSSFCell cell = row0.createCell(i);  
              Field field = fields[i];
              String fieldName = field.getName();
              cell.setCellValue(fieldName);
              
        }
        int index=0;
        for(Object o:list){
        for (int i = 0; i < fields.length; i++) {
         index++;
         Class<? extends Object> body=o.getClass();
         HSSFRow rowIndex = sheet.createRow(index);
		HSSFCell cell = rowIndex.createCell(i);  
          Field field = fields[i];
            String fieldName = field.getName();
              String getMethodName = "get"
                + fieldName.substring(0, 1).toUpperCase()
                + fieldName.substring(1);
              try {
                Method getMethod = body.getMethod(getMethodName,new Class[] {});
                Object value = getMethod.invoke(body, new Object[] {});

                  //判断值的类型后进行强制类型转换

                  String textValue = null;


                   if (value instanceof Date) {

                     Date date = (Date) value;

                     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                      textValue = sdf.format(date);

                  } else{

                     //其它数据类型都当作字符串简单处理

                     textValue = value.toString();

                  }

                  //如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成

                  if(textValue!=null){

                     Pattern p = Pattern.compile("^//d+(//.//d+)?$");   

                     Matcher matcher = p.matcher(textValue);

                     if(matcher.matches()){

                        //是数字当作double处理

                        cell.setCellValue(Double.parseDouble(textValue));

                     }else{

                        HSSFRichTextString richString = new HSSFRichTextString(textValue);

                        cell.setCellValue(richString);

                     }

                  }

              } catch (SecurityException e) {

                  // TODO Auto-generated catch block

                  e.printStackTrace();

              } catch (NoSuchMethodException e) {

                  // TODO Auto-generated catch block

                  e.printStackTrace();

              } catch (IllegalArgumentException e) {

                  // TODO Auto-generated catch block

                  e.printStackTrace();

              } catch (IllegalAccessException e) {

                  // TODO Auto-generated catch block

                  e.printStackTrace();

              } catch (InvocationTargetException e) {

                  // TODO Auto-generated catch block

                  e.printStackTrace();

              } finally {

                  //清理资源

              }

           }

        }
        return wb;  


        

      
    	
    }
    @SuppressWarnings("deprecation")
	public    HSSFWorkbook getThree_one(){
    	 HSSFWorkbook wb = new HSSFWorkbook();  
         
         HSSFSheet sheet = wb.createSheet("三个一");  
        
       //设置每列的宽度
         sheet.setColumnWidth(0,Constants.EXCEL_COLUMN_WITH1); 
         sheet.setColumnWidth(1,Constants.EXCEL_COLUMN_WITH1); 
         sheet.setColumnWidth(2,Constants.EXCEL_COLUMN_WITH1); 
         sheet.setColumnWidth(3,Constants.EXCEL_COLUMN_WITH1*3); 
         sheet.setColumnWidth(4,Constants.EXCEL_COLUMN_WITH1/2); 
         sheet.setColumnWidth(5,Constants.EXCEL_COLUMN_WITH1/2); 
         sheet.setColumnWidth(6,Constants.EXCEL_COLUMN_WITH1/2); 
         sheet.setColumnWidth(7,Constants.EXCEL_COLUMN_WITH1/2); 
         sheet.setColumnWidth(8,Constants.EXCEL_COLUMN_WITH1*6); 
         sheet.setColumnWidth(9,Constants.EXCEL_COLUMN_WITH1); 
         sheet.setColumnWidth(10,Constants.EXCEL_COLUMN_WITH1); 
         
       //设置标题
         HSSFCellStyle style = wb.createCellStyle(); 
         style.setAlignment(CellStyle.ALIGN_CENTER);
         HSSFFont font1 = wb.createFont();
         font1.setFontName("黑体");
         font1.setFontHeightInPoints((short) 24);//设置字体大小 
         sheet.addMergedRegion(new Region(0,(short)0,0,(short)10));
         HSSFRow title=sheet.createRow(0);
         title.setHeight((short) 1000);
         HSSFCell titlecell=title.createCell(0); 
         titlecell.setCellValue("南京大学2010年申请工程硕士学位答辩情况汇总表");
         style.setFont(font1);
         titlecell.setCellStyle(style);
         
         
       //设置列标题
         HSSFCellStyle style2 = wb.createCellStyle();
         HSSFFont font2 = wb.createFont();
         font2.setFontName("黑体");
         font2.setFontHeightInPoints((short) 12);//设置字体大小 
         style2.setFont(font2);
         style2.setWrapText(true);
         sheet.addMergedRegion(new Region(1,(short)0,2,(short)0));//需要合并的单元格
         sheet.addMergedRegion(new Region(1,(short)1,2,(short)1));
         sheet.addMergedRegion(new Region(1,(short)2,2,(short)2));
         sheet.addMergedRegion(new Region(1,(short)3,2,(short)3));
         sheet.addMergedRegion(new Region(1,(short)4,1,(short)7));
         sheet.addMergedRegion(new Region(1,(short)8,2,(short)8));
         sheet.addMergedRegion(new Region(1,(short)9,2,(short)9));
         sheet.addMergedRegion(new Region(1,(short)10,2,(short)10));
         HSSFRow header = sheet.createRow(1);
         String[] data={"导师姓名","姓名","申请学位","申请硕士学位论文题目","课程成绩","三个一的具体体现：\n  1.一个工程背景的项目； " +
         		" 2.采用一种新的技术；  3.一个能实际运行的系统","鉴定情况","答辩时间"};
         header.setHeight((short) 500);
         header.setRowStyle(style2);
         int index=0;
         for(int i=0;i<data.length;i++){
        	
        	 if(i==5) index=index+3;    //跳过小标题
        	 HSSFCell cell=header.createCell(index++);
        	 cell.setCellValue(data[i]);
        	 cell.setCellStyle(style2);
         }
       
         HSSFRow header1 = sheet.createRow(2);
         header1.setRowStyle(style);
         header1.setHeight((short) 500);
         header1.createCell(4).setCellValue("优");
         header1.createCell(5).setCellValue("良");
         header1.createCell(6).setCellValue("中");
         header1.createCell(7).setCellValue("及格");
  	    
         
        
  	  return wb;
    }
    /** *//**
     * <ul>
     * <li>Description:[正确地处理整数后自动加零的情况]</li>
     * <li>Created by [Huyvanpull] [Jan 20, 2010]</li>
     * <li>Midified by [modifier] [modified time]</li>
     * <ul>
     * 
     * @param sNum
     * @return
     */
	public HSSFWorkbook getStyle(){
	    File file =new File(Constants.SAVE_DIRECTORY + File.separatorChar+"三个一.xls");
		HSSFWorkbook wb = null;
	    try {
		wb=new HSSFWorkbook(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return wb;
	}
    private String getRightStr(String sNum)
    {
        DecimalFormat decimalFormat = new DecimalFormat("#.000000");
        String resultStr = decimalFormat.format(new Double(sNum));
        if (resultStr.matches("^[-+]?\\d+\\.[0]+$"))
        {
            resultStr = resultStr.substring(0, resultStr.indexOf("."));
        }
        return resultStr;
    }
    
    /** *//**
     * <ul>
     * <li>Description:[测试main方法]</li>
     * <li>Created by [Huyvanpull] [Jan 20, 2010]</li>
     * <li>Midified by [modifier] [modified time]</li>
     * <ul>
     * 
     * @param args
     * @throws Exception
     */
 
}
