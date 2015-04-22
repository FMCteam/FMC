package nju.software.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class JSONUtil {
	
	/**
	 * 封装返回JSON数据的方法
	 * @param response
	 * @param jsonobj
	 */
	public void sendJson(HttpServletResponse response, Object object){
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
		JSONObject jsonobj = JSONObject.fromObject(object, config);
		response.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			out.print(jsonobj);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Date, TimeStamp转换为Json
	 * @author margine
	 *
	 */
	public class DateJsonValueProcessor implements JsonValueProcessor{
		public static final String DEAFAULT_DATE_PATTERN = "yyyy-MM-dd";
		private DateFormat dateFormat;

		public DateJsonValueProcessor(String datePattern){
			try {
				dateFormat = new SimpleDateFormat(datePattern);
			} catch (Exception e) {
				dateFormat = new SimpleDateFormat(DEAFAULT_DATE_PATTERN);
			}
		}
		@Override
		public Object processArrayValue(Object value, JsonConfig jsonConfig) {
			return process(value);
		}

		@Override
		public Object processObjectValue(String key, Object value,
				JsonConfig jsonConfig) {
			return process(value);
		}
		
		private Object process(Object value){
			return dateFormat.format((Date)value);
		}
	}
}
