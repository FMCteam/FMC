package nju.software.util;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
/**
 * 
 * @author margine
 *
 */

public class SessionUtil {
	
	public static HttpSession getSession(HttpServletRequest request, String jsessionId){
		ServletContext context = request.getSession().getServletContext();
		if (jsessionId == null || jsessionId.equals("")) {
			return null;
		}
		final HttpSession session = (HttpSession) context.getAttribute(jsessionId);
    	return session;
	}

}
