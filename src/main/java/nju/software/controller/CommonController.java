package nju.software.controller;

import net.sf.json.JSONObject;
import nju.software.dao.impl.CraftDAO;
import nju.software.dao.impl.DesignCadDAO;
import nju.software.dao.impl.OrderDAO;
import nju.software.dataobject.Account;
import nju.software.dataobject.Craft;
import nju.software.dataobject.DesignCad;
import nju.software.dataobject.Order;
import nju.software.service.CommonService;
import nju.software.service.impl.BuyServiceImpl;
import nju.software.service.impl.DesignServiceImpl;
import nju.software.service.impl.FinanceServiceImpl;
import nju.software.service.impl.LogisticsServiceImpl;
import nju.software.service.impl.MarketServiceImpl;
import nju.software.service.impl.ProduceServiceImpl;
import nju.software.service.impl.QualityServiceImpl;
import nju.software.service.impl.SweaterMakeServiceImpl;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private static List<String> departments = new ArrayList<String>();
	private static Map<String, Object> map = new HashMap<String,Object>();

	static {
		departments.add(MarketServiceImpl.ACTOR_MARKET_MANAGER);
		departments.add(DesignServiceImpl.ACTOR_DESIGN_MANAGER);
		departments.add(BuyServiceImpl.ACTOR_PURCHASE_MANAGER);
		departments.add(ProduceServiceImpl.ACTOR_PRODUCE_MANAGER);
		departments.add(FinanceServiceImpl.ACTOR_FINANCE_MANAGER);
		departments.add(LogisticsServiceImpl.ACTOR_LOGISTICS_MANAGER);
		departments.add(QualityServiceImpl.ACTOR_QUALITY_MANAGER);
		departments.add(SweaterMakeServiceImpl.ACTOR_SWEATER_MANAGER);

		map.put(MarketServiceImpl.TASK_VERIFY_QUOTE,
				MarketServiceImpl.ACTOR_MARKET_MANAGER);
		
		map.put(DesignServiceImpl.TASK_COMPUTE_DESIGN_COST,
				DesignServiceImpl.ACTOR_DESIGN_MANAGER);
		map.put(DesignServiceImpl.TASK_UPLOAD_DESIGN,
				DesignServiceImpl.ACTOR_DESIGN_MANAGER);
		map.put(DesignServiceImpl.TASK_MODIFY_DESIGN,
				DesignServiceImpl.ACTOR_DESIGN_MANAGER);
		map.put(DesignServiceImpl.TASK_CONFIRM_DESIGN,
				DesignServiceImpl.ACTOR_DESIGN_MANAGER);
		map.put(DesignServiceImpl.TASK_CRAFT_SAMPLE,
				DesignServiceImpl.ACTOR_DESIGN_MANAGER);
		map.put(DesignServiceImpl.TASK_CRAFT_PRODUCT,
				DesignServiceImpl.ACTOR_DESIGN_MANAGER);		
		map.put(DesignServiceImpl.TASK_TYPESETTING_SLICE, 
				DesignServiceImpl.ACTOR_DESIGN_MANAGER);
		map.put(DesignServiceImpl.TASK_CONFIRM_CAD , 
				DesignServiceImpl.ACTOR_DESIGN_MANAGER);		
		
		map.put(BuyServiceImpl.TASK_VERIFY_PURCHASE,
				BuyServiceImpl.ACTOR_PURCHASE_MANAGER);
		map.put(BuyServiceImpl.TASK_COMPUTE_PURCHASE_COST,
				BuyServiceImpl.ACTOR_PURCHASE_MANAGER);
		map.put(BuyServiceImpl.TASK_PURCHASE_SAMPLE_MATERIAL,
				BuyServiceImpl.ACTOR_PURCHASE_MANAGER);
		map.put(BuyServiceImpl.TASK_CONFIRM_PURCHASE,
				BuyServiceImpl.ACTOR_PURCHASE_MANAGER);
		map.put(BuyServiceImpl.TASK_PURCHASE_MATERIAL,
				BuyServiceImpl.ACTOR_PURCHASE_MANAGER);
        map.put(BuyServiceImpl.TASK_BUY_SWEATER_MATERIAL,
        		BuyServiceImpl.ACTOR_PURCHASE_MANAGER);
		
		
		map.put(ProduceServiceImpl.TASK_VERIFY_PRODUCE,
				ProduceServiceImpl.ACTOR_PRODUCE_MANAGER);
		map.put(ProduceServiceImpl.TASK_COMPUTE_PRODUCE_COST,
				ProduceServiceImpl.ACTOR_PRODUCE_MANAGER);
		map.put(ProduceServiceImpl.TASK_PRODUCE_SAMPLE,
				ProduceServiceImpl.ACTOR_PRODUCE_MANAGER);
		map.put(ProduceServiceImpl.TASK_PRODUCE,
				ProduceServiceImpl.ACTOR_PRODUCE_MANAGER);

		map.put(FinanceServiceImpl.TASK_CONFIRM_SAMPLE_MONEY,
				FinanceServiceImpl.ACTOR_FINANCE_MANAGER);
		map.put(FinanceServiceImpl.TASK_CONFIRM_DEPOSIT,
				FinanceServiceImpl.ACTOR_FINANCE_MANAGER);
		map.put(FinanceServiceImpl.TASK_RETURN_DEPOSIT, 
				FinanceServiceImpl.ACTOR_FINANCE_MANAGER);

		map.put(FinanceServiceImpl.TASK_CONFIRM_FINAL_PAYMENT,
				FinanceServiceImpl.ACTOR_FINANCE_MANAGER);

		map.put(LogisticsServiceImpl.TASK_RECEIVE_SAMPLE,
				LogisticsServiceImpl.ACTOR_LOGISTICS_MANAGER);
		map.put(LogisticsServiceImpl.TASK_SEND_SAMPLE,
				LogisticsServiceImpl.ACTOR_LOGISTICS_MANAGER);
		map.put(LogisticsServiceImpl.TASK_WAREHOUSE,
				LogisticsServiceImpl.ACTOR_LOGISTICS_MANAGER);
		map.put(LogisticsServiceImpl.TASK_SEND_CLOTHES,
				LogisticsServiceImpl.ACTOR_LOGISTICS_MANAGER);
		map.put(LogisticsServiceImpl.TASK_WAREHOUSE_HAODUOYI,
				LogisticsServiceImpl.ACTOR_LOGISTICS_MANAGER);
		
		map.put(QualityServiceImpl.TASK_CHECK_QUALITY,
				QualityServiceImpl.ACTOR_QUALITY_MANAGER);
		
		map.put(SweaterMakeServiceImpl.TASK_CONFIRM_SWEATER_SAMPLE_AND_CRAFT,
				SweaterMakeServiceImpl.ACTOR_SWEATER_MANAGER);
		map.put(SweaterMakeServiceImpl.TASK_SEND_SWEATER, 
				SweaterMakeServiceImpl.ACTOR_SWEATER_MANAGER);
	}

	@RequestMapping(value = "/common/getTaskNumber.do")
	@Transactional(rollbackFor = Exception.class)
	@ResponseBody
	public void getTaskNumber(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String actorId = getActorId(request);
		Integer number = getTaskNumber(actorId);
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("taskNumber", number);
		for (String department : departments) {
//			if(department.equals(MarketServiceImpl.ACTOR_MARKET_MANAGER)){
//				Integer verifyQuoteTaskNumber = getTaskNumber((String) map.get(MarketServiceImpl.TASK_VERIFY_QUOTE), MarketServiceImpl.TASK_VERIFY_QUOTE);
//				Integer marketDepartmentTasks = getTaskNumber(MarketServiceImpl.ACTOR_MARKET_MANAGER);
//				int result = marketDepartmentTasks.intValue() - verifyQuoteTaskNumber.intValue();
//				Integer marketStaffTasks = new Integer(result);
//				System.out.println("marketDepartmentTasks"+marketDepartmentTasks);
//				jsonobj.put(MarketServiceImpl.ACTOR_MARKET_MANAGER,marketStaffTasks );
//			} 
				
				jsonobj.put(department, getTaskNumber(department));
//				System.out.println(department+"任务数量"+getTaskNumber(department));
 
		}
		//market department task, include all tasks of all marketStaffs and admin;
		if (isAdmin(request)) {
			jsonobj.put("taskNumber", commonService.getAllTaskNumber());
//			jsonobj.put(MarketServiceImpl.ACTOR_MARKET_MANAGER, commonService.getMarketStaffTaskNumber());
			
			jsonobj.put(MarketServiceImpl.TASK_CONFIRM_PRODUCE_ORDER, commonService.getMarketStaffTaskNumber(MarketServiceImpl.TASK_CONFIRM_PRODUCE_ORDER));
			jsonobj.put(MarketServiceImpl.TASK_CONFIRM_QUOTE, commonService.getMarketStaffTaskNumber(MarketServiceImpl.TASK_CONFIRM_QUOTE));
			jsonobj.put(MarketServiceImpl.TASK_MERGE_QUOTE, commonService.getMarketStaffTaskNumber(MarketServiceImpl.TASK_MERGE_QUOTE));
			jsonobj.put(MarketServiceImpl.TASK_MODIFY_ORDER, commonService.getMarketStaffTaskNumber(MarketServiceImpl.TASK_MODIFY_ORDER));
			jsonobj.put(MarketServiceImpl.TASK_MODIFY_PRODUCE_ORDER, commonService.getMarketStaffTaskNumber(MarketServiceImpl.TASK_MODIFY_PRODUCE_ORDER));
			jsonobj.put(MarketServiceImpl.TASK_MODIFY_QUOTE, commonService.getMarketStaffTaskNumber(MarketServiceImpl.TASK_MODIFY_QUOTE));
			jsonobj.put(MarketServiceImpl.TASK_PUSH_REST, commonService.getMarketStaffTaskNumber(MarketServiceImpl.TASK_PUSH_REST));
			jsonobj.put(MarketServiceImpl.TASK_SIGN_CONTRACT, commonService.getMarketStaffTaskNumber(MarketServiceImpl.TASK_SIGN_CONTRACT));
		}
		// jsonobj.put(MarketServiceImpl.ACTOR_MARKET_MANAGER,
		// getTaskNumber(actorId));
		if (isMarketStaff(request)) {
			map.put(MarketServiceImpl.TASK_MODIFY_ORDER, actorId);
			map.put(MarketServiceImpl.TASK_MERGE_QUOTE, actorId);
			map.put(MarketServiceImpl.TASK_CONFIRM_QUOTE, actorId);
			map.put(MarketServiceImpl.TASK_MODIFY_QUOTE, actorId);
			map.put(MarketServiceImpl.TASK_CONFIRM_PRODUCE_ORDER, actorId);
			map.put(MarketServiceImpl.TASK_MODIFY_PRODUCE_ORDER, actorId);
			map.put(MarketServiceImpl.TASK_SIGN_CONTRACT, actorId);
			map.put(MarketServiceImpl.TASK_PUSH_REST, actorId);
//			map.put(MarketServiceImpl.TASK_VERIFY_QUOTE, actorId);
			jsonobj.put(MarketServiceImpl.ACTOR_MARKET_MANAGER,number );
 
		}

		for (String task : map.keySet()) {
			jsonobj.put(task, getTaskNumber((String) map.get(task), task));
 			if(task.equals(MarketServiceImpl.TASK_VERIFY_QUOTE)){
				//市场主管的task class 设为"marketManager2",设置为市场主管的审核报价的任务数 为市场主管所有的任务数
				jsonobj.put("marketManager2", getTaskNumber((String) map.get(task), task));
 				
			}
		}
		//设计部门分为设计部和工艺部
		int totalNumber = jsonobj.getInt(DesignServiceImpl.ACTOR_DESIGN_MANAGER);
		// 工艺部任务数量
		int craftNumber = jsonobj.getInt(DesignServiceImpl.TASK_COMPUTE_DESIGN_COST)
				+ jsonobj.getInt(DesignServiceImpl.TASK_CRAFT_SAMPLE)
				+ jsonobj.getInt(DesignServiceImpl.TASK_CRAFT_PRODUCT);
		// 设计部任务数量
		int designNumber = totalNumber - craftNumber;
		jsonobj.put(DesignServiceImpl.ACTOR_DESIGN_MANAGER, designNumber);
		jsonobj.put(DesignServiceImpl.ACTOR_CRAFT_MANAGER, craftNumber);
		
		//入库包括好多衣和非好多衣客户
		int warehouseNum = jsonobj.getInt(LogisticsServiceImpl.TASK_WAREHOUSE)
				+ jsonobj.getInt(LogisticsServiceImpl.TASK_WAREHOUSE_HAODUOYI);
		jsonobj.put(LogisticsServiceImpl.TASK_WAREHOUSE, warehouseNum);
		sendJson(response, jsonobj);
	}

	@RequestMapping(value = "/common/getPic.do")
	@Transactional(rollbackFor = Exception.class)
	public void getPic(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		response.setContentType("image/jpg"); // 设置返回的文件类型
		Integer orderId =Integer.parseInt(request.getParameter("orderId"));
		Order order=orderDAO.findById(orderId);
		String type = request.getParameter("type");
		String file = null;
		if (type.equals("sample")) {
			file = order.getSampleClothesPicture();
		}else if(type.equals("confirmSampleMoney")){
			file = order.getConfirmSampleMoneyFile();
		}else if(type.equals("confirmDepositFile")){
			file = order.getConfirmDepositFile();
		}else if(type.equals("confirmFinalPaymentFile")){
			file = order.getConfirmFinalPaymentFile();
		}else if(type.equals("craftFileUrl")){
			Craft craft = craftDAO.findByOrderId(orderId).get(0);
			file = craft.getCraftFileUrl();
		}else if(type.equals("CADFile")){
			DesignCad cad = designCadDAO.findByOrderId(orderId).get(0);
 			file = cad.getCadUrl();
		}else {
			file = order.getReferencePicture();
		}
		
		try {
			DataInputStream in = new DataInputStream(new FileInputStream(file));
			OutputStream os = response.getOutputStream();
			byte[] b = new byte[2048];
			while ((in.read(b)) != -1) {
				os.write(b);
				os.flush();
			}
			in.close();
			os.close();
			os.flush();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	private String getActorId(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Account account = (Account) session.getAttribute("cur_user");
		if (account.getUserRole().equals(MarketServiceImpl.ACTOR_MARKET_STAFF)) {
			return account.getUserId() + "";
		} else {
			return account.getUserRole();
		}
	}
	
	private boolean isAdmin(HttpServletRequest request){
		HttpSession session = request.getSession();
		Account account = (Account) session.getAttribute("cur_user");
		return account.getUserRole().equals("ADMIN");
	}

	private boolean isMarketStaff(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Account account = (Account) session.getAttribute("cur_user");
		return account.getUserRole().equals(
				MarketServiceImpl.ACTOR_MARKET_STAFF);
	}
    private boolean isMarketManager(HttpServletRequest request){
    	HttpSession session = request.getSession();
		Account account = (Account) session.getAttribute("cur_user");
		return account.getUserRole().equals(
				MarketServiceImpl.ACTOR_MARKET_MANAGER);
    }
    
    private boolean isDesignManager(HttpServletRequest request){
    	HttpSession session = request.getSession();
		Account account = (Account) session.getAttribute("cur_user");
		return account.getUserRole().equals(
				DesignServiceImpl.ACTOR_DESIGN_MANAGER);
    }
    
	private Integer getTaskNumber(String actorId) {
		return commonService.getTaskNumber(actorId);
	}

	private Integer getTaskNumber(String actorId, String taskName) {
		return commonService.getTaskNumber(actorId, taskName);
	}

	/**
	 * 封装返回Json数据的方法
	 */
	private void sendJson(HttpServletResponse response, JSONObject jsonobj) {
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
	private OrderDAO orderDAO;
	@Autowired
	private CraftDAO craftDAO;
	@Autowired
	private DesignCadDAO designCadDAO;
	
	@Autowired
	private CommonService commonService;
}
