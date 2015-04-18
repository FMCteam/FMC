package nju.software.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

public class JSONUtil {
	
	/**
	 * 封装返回JSON数据的方法
	 * @param response
	 * @param jsonobj
	 */
	public void sendJson(HttpServletResponse response, Object object){
		JSONObject jsonobj = JSONObject.fromObject(object);
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

}
