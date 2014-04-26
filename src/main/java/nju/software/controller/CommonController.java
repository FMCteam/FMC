package nju.software.controller;

import net.sf.json.JSONObject;
import nju.software.dataobject.Account;
import nju.software.service.BuyService;
import nju.software.service.impl.MarketServiceImpl;
import nju.software.util.JbpmAPIUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jbpm.task.query.TaskSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CommonController {
	@RequestMapping(value = "/common/getTaskNumber.do")
	@Transactional(rollbackFor = Exception.class)
	@ResponseBody
	public void verifyPurchaseList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String actorId=getActorId(request);
		List<TaskSummary>task=jbpmAPIUtil.getAssignedTasks(actorId);
		Integer number=0;
		if(task!=null){
			number=task.size();
		}
		JSONObject jsonobj = new JSONObject();
        jsonobj.put("list", number);
		sendJson(response, jsonobj);	
	}
	
	
	
	
	private String getActorId(HttpServletRequest request){
		HttpSession session=request.getSession();
		Account account = (Account) session.getAttribute("cur_user");
		if(account.getUserRole().equals(MarketServiceImpl.ACTOR_MARKET_STAFF)){
			return account.getUserId()+"";
		}else{
			return account.getUserRole();
		}
	}
	
	
	/**
	 * 封装返回Json数据的方法
	 */
	private void sendJson(HttpServletResponse response,JSONObject jsonobj ){
		try {
			PrintWriter out = response.getWriter();
			out.print(jsonobj);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Autowired
	private JbpmAPIUtil jbpmAPIUtil;
}
