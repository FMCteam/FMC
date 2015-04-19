package nju.software.controller.mobile;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nju.software.dataobject.Account;
import nju.software.dataobject.Employee;
import nju.software.dataobject.Order;
import nju.software.dataobject.Produce;
import nju.software.dataobject.SearchInfo;
import nju.software.service.EmployeeService;
import nju.software.service.OrderService;
import nju.software.service.ProduceService;
import nju.software.util.JSONUtil;
import nju.software.util.ListUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ProduceMobileController {

	@Autowired
	private ProduceService produceService;
	
	@Autowired
	private OrderService orderService;
	
	public final static String ISSUCCESS="isSuccess";

	@RequestMapping(value = "/produce/mobile_verifyProduceList.do", method= RequestMethod.GET)
	//@Transactional(rollbackFor = Exception.class)
	public void verifyProduceList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		System.out.println("produce verify ================ show task");
		Account account = (Account) request.getSession().getAttribute("cur_user");
//		String actorId = account.getUserRole();

		List<Map<String,Object>> orderList = produceService.getVerifyProduceList();
		orderList = ListUtil.reserveList(orderList);
		model.addAttribute("list", orderList);
		model.addAttribute("taskName", "生产验证");
		model.addAttribute("url", "/produce/mobile_verifyProduceDetail.do");
		model.addAttribute("searchurl", "/produce/mobile_verifyProduceListSearch.do");

		jsonUtil.sendJson(response, model);
	}
	@Autowired
	private EmployeeService employeeService;
	@RequestMapping(value = "/produce/mobile_verifyProduceListSearch.do")
	//@Transactional(rollbackFor = Exception.class)
	public void verifyProduceListSearch(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String ordernumber = request.getParameter("ordernumber");
		String customername = request.getParameter("customername");
		String stylename = request.getParameter("stylename");
		String employeename = request.getParameter("employeename");
		String startdate = request.getParameter("startdate");
		String enddate = request.getParameter("enddate");
		//将用户输入的employeeName转化为employeeId,因为order表中没有employeeName属性
		List<Employee> employees = employeeService.getEmployeeByName(employeename);
		Integer[] employeeIds = new Integer[employees.size()];
		for(int i=0;i<employeeIds.length;i++){
			employeeIds[i] = employees.get(i).getEmployeeId();
		}
    	System.out.println("produce verify ================ show task");
		Account account = (Account) request.getSession().getAttribute("cur_user");
//		String actorId = account.getUserRole();

		List<Map<String,Object>> orderList = produceService.getSearchVerifyProduceList(ordernumber,customername,stylename,startdate,enddate,employeeIds);
		orderList = ListUtil.reserveList(orderList);
		model.addAttribute("list", orderList);
		model.addAttribute("taskName", "生产验证");
		model.addAttribute("url", "/produce/mobile_verifyProduceDetail.do");
		model.addAttribute("searchurl", "/produce/mobile_verifyProduceListSearch.do");
		model.addAttribute("info", new SearchInfo(ordernumber, customername, stylename, employeename, startdate, enddate));//将查询条件传回页面  hcj
		jsonUtil.sendJson(response, model);
	}
	
	/**
	 * 生产验证
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/produce/mobile_verifyProduceSubmit.do", method= RequestMethod.POST)
	//@Transactional(rollbackFor = Exception.class)
	public void verifyProduceSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		boolean productVal = Boolean.parseBoolean(request.getParameter("productVal"));
		String taskId = (request.getParameter("taskId"));
		String comment = request.getParameter("suggestion");
		boolean isSuccess=produceService.verifyProduceSubmit(taskId, productVal, comment);
		model.addAttribute(ISSUCCESS, isSuccess);
		jsonUtil.sendJson(response, model);
	}
	
	/**
	 * 显示订单详细信息
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/produce/mobile_verifyProduceDetail.do", method= RequestMethod.GET)
	//@Transactional(rollbackFor = Exception.class)
	public void verifyProduceDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		String s_orderId_request = request.getParameter("orderId");
		int orderId = Integer.parseInt(s_orderId_request);
		Map<String,Object> orderInfo = produceService.getVerifyProduceDetail(orderId);
		model.addAttribute("orderInfo", orderInfo);
		jsonUtil.sendJson(response, model);
	}

	/**
	 * 生产成本核算跳转链接
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/produce/mobile_computeProduceCostList.do", method= RequestMethod.GET)
	//@Transactional(rollbackFor = Exception.class)
	public void computeProduceCostList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		List<Map<String,Object>> list = produceService.getComputeProduceCostList();
		list = ListUtil.reserveList(list);
		model.addAttribute("list", list);
		model.addAttribute("taskName", "生产成本验证并核算");
		model.addAttribute("url", "/produce/mobile_computeProduceCostDetail.do");
		model.addAttribute("searchurl", "/produce/mobile_computeProduceCostListSearch.do");

		jsonUtil.sendJson(response, model);
	}
	
	@RequestMapping(value = "/produce/mobile_computeProduceCostListSearch.do" )
	//@Transactional(rollbackFor = Exception.class)
	public void computeProduceCostListSearch(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String ordernumber = request.getParameter("ordernumber");
		String customername = request.getParameter("customername");
		String stylename = request.getParameter("stylename");
		String employeename = request.getParameter("employeename");
		String startdate = request.getParameter("startdate");
		String enddate = request.getParameter("enddate");
		//将用户输入的employeeName转化为employeeId,因为order表中没有employeeName属性
		List<Employee> employees = employeeService.getEmployeeByName(employeename);
		Integer[] employeeIds = new Integer[employees.size()];
		for(int i=0;i<employeeIds.length;i++){
			employeeIds[i] = employees.get(i).getEmployeeId();
		}
		List<Map<String,Object>> list=produceService.getSearchComputeProduceCostList(ordernumber,customername,stylename,startdate,enddate,employeeIds);
		list = ListUtil.reserveList(list);
		model.addAttribute("list", list);
		model.addAttribute("taskName", "生产成本验证并核算");
		model.addAttribute("url", "/produce/mobile_computeProduceCostDetail.do");
		model.addAttribute("searchurl", "/produce/mobile_computeProduceCostListSearch.do");
		model.addAttribute("info", new SearchInfo(ordernumber, customername, stylename, employeename, startdate, enddate));//将查询条件传回页面  hcj
		jsonUtil.sendJson(response, model);
	}
	
	
	/**
	 * 显示成本核算详细信息
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/produce/mobile_computeProduceCostDetail.do", method= RequestMethod.GET)
	//@Transactional(rollbackFor = Exception.class)
	public void computeProduceCostDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Integer orderId=Integer.parseInt(request.getParameter("orderId"));
		Map<String,Object> orderInfo=produceService.getComputeProduceCostInfo(orderId);
		model.addAttribute("orderInfo", orderInfo);
		jsonUtil.sendJson(response, model);
	}
	
	/**
	 * 成本核算
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/produce/mobile_computeProduceCostSubmit.do", method= RequestMethod.POST)
	//@Transactional(rollbackFor = Exception.class)
	public void computeProduceCostSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Integer orderId = Integer.parseInt(request.getParameter("orderId"));
		String taskId = (request.getParameter("taskId"));
		
		boolean result = Boolean.parseBoolean(request.getParameter("result"));// 是否拒绝生产
		String comment = request.getParameter("suggestion");
		//如果拒绝，则不进行生产成本的核算，直接返回
		if (result == false) {
			boolean isSuccess=produceService.verifyProduceSubmit(taskId, result, comment);
			model.addAttribute(ISSUCCESS, isSuccess);
			jsonUtil.sendJson(response, model);
		}
		
		//裁剪费用
		String cut_cost = request.getParameter("cut_cost");
		//管理费用
		String manage_cost = request.getParameter("manage_cost");
		//缝制费用
		String nail_cost = request.getParameter("nail_cost");
		//整烫费用
	    String ironing_cost = request.getParameter("ironing_cost");
	  //锁订费用
		String swing_cost = request.getParameter("swing_cost");
		//包装费用
		String package_cost = request.getParameter("package_cost");
		//其他费用
		String other_cost = request.getParameter("other_cost");
	    //设计费用
		String design_cost = request.getParameter("design_cost");
		//大货物流费（整单）
		//String ask_logistics_cost = request.getParameter("ask_logistics_cost");
		
		//生产报价提交，默认验证通过
		boolean isSuccess=produceService.computeProduceCostSubmit(
				orderId,
				taskId,
				result, 
				comment,
				Float.parseFloat(cut_cost),
				Float.parseFloat(manage_cost),
				Float.parseFloat(nail_cost),
				Float.parseFloat(ironing_cost),
				Float.parseFloat(swing_cost),
				Float.parseFloat(package_cost),
				Float.parseFloat(other_cost),
				Float.parseFloat(design_cost)
						);
		
		model.addAttribute(ISSUCCESS, isSuccess);
		jsonUtil.sendJson(response, model);	
		}
	
	
	//=======================样衣生产=======================
	@RequestMapping(value = "/produce/mobile_produceSampleList.do")
	//@Transactional(rollbackFor = Exception.class)
	public void produceSampleList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<Map<String,Object>> list = produceService.getProduceSampleList();
		list = ListUtil.reserveList(list);
		model.addAttribute("list", list);
		model.addAttribute("taskName", "样衣生产");
		model.addAttribute("url", "/produce/mobile_produceSampleDetail.do");
		model.addAttribute("searchurl", "/produce/mobile_produceSampleListSearch.do");

		jsonUtil.sendJson(response, model);	
	}
	
	@RequestMapping(value = "/produce/mobile_produceSampleListSearch.do")
	//@Transactional(rollbackFor = Exception.class)
	public void produceSampleListSearch(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String ordernumber = request.getParameter("ordernumber");
		String customername = request.getParameter("customername");
		String stylename = request.getParameter("stylename");
		String employeename = request.getParameter("employeename");
		String startdate = request.getParameter("startdate");
		String enddate = request.getParameter("enddate");
		//将用户输入的employeeName转化为employeeId,因为order表中没有employeeName属性
	    List<Employee> employees = employeeService.getEmployeeByName(employeename);
		Integer[] employeeIds = new Integer[employees.size()];
		for(int i=0;i<employeeIds.length;i++){
			employeeIds[i] = employees.get(i).getEmployeeId();
		}
		List<Map<String,Object>> list = produceService.getSearchProduceSampleList(ordernumber,customername,stylename,startdate,enddate,employeeIds);
		list = ListUtil.reserveList(list);
		model.addAttribute("list", list);
		model.addAttribute("taskName", "样衣生产");
		model.addAttribute("url", "/produce/mobile_produceSampleDetail.do");
		model.addAttribute("searchurl", "/produce/mobile_produceSampleListSearch.do");

		model.addAttribute("info", new SearchInfo(ordernumber, customername, stylename, employeename, startdate, enddate));//将查询条件传回页面  hcj
		jsonUtil.sendJson(response, model);	
	}
	
	@RequestMapping(value = "/produce/mobile_produceSampleDetail.do")
	//@Transactional(rollbackFor = Exception.class)
	public void produceSampleDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = request.getParameter("orderId");
		Map<String,Object> orderInfo=produceService.getProduceSampleDetail(Integer.parseInt(orderId));
		model.addAttribute("orderInfo", orderInfo);
		jsonUtil.sendJson(response, model);	
	}
	
	
	@RequestMapping(value = "/produce/mobile_produceSampleSubmit.do")
	//@Transactional(rollbackFor = Exception.class)
	public void produceSampleSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = request.getParameter("orderId");
		boolean result =request.getParameter("result").equals("1");
		String taskId = (request.getParameter("taskId"));
		boolean isSuccess=produceService.produceSampleSubmit(taskId, result,orderId);
		model.addAttribute(ISSUCCESS, isSuccess);
		jsonUtil.sendJson(response, model);	
	}
	
	
	//=====================批量生产======================
	@RequestMapping(value = "/produce/mobile_produceList.do")
	//@Transactional(rollbackFor = Exception.class)
	public void produceList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<Map<String,Object>> list=produceService.getProduceList();
		list = ListUtil.reserveList(list);
		/*if(list.size()==0){
			jbpmTest.completeBeforeProduce(ProduceServiceImpl.ACTOR_PRODUCE_MANAGER);
			list=produceService.getProduceList();
		}*/
		model.addAttribute("list", list);
		model.addAttribute("taskName", "批量生产");
		model.addAttribute("url", "/produce/mobile_produceDetail.do");
		model.addAttribute("searchurl", "/produce/mobile_produceListSearch.do");

		jsonUtil.sendJson(response, model);	
	}

	@RequestMapping(value = "/produce/mobile_produceListSearch.do")
	//@Transactional(rollbackFor = Exception.class)
	public void produceListSearch(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String ordernumber = request.getParameter("ordernumber");
		String customername = request.getParameter("customername");
		String stylename = request.getParameter("stylename");
		String employeename = request.getParameter("employeename");
		String startdate = request.getParameter("startdate");
		String enddate = request.getParameter("enddate");
		//将用户输入的employeeName转化为employeeId,因为order表中没有employeeName属性
		List<Employee> employees = employeeService.getEmployeeByName(employeename);
		Integer[] employeeIds = new Integer[employees.size()];
		for(int i=0;i<employeeIds.length;i++){
			employeeIds[i] = employees.get(i).getEmployeeId();
		}
		List<Map<String,Object>> list=produceService.getSearchProduceList(ordernumber,customername,stylename,startdate,enddate,employeeIds);
		/*if(list.size()==0){
			jbpmTest.completeBeforeProduce(ProduceServiceImpl.ACTOR_PRODUCE_MANAGER);
			list=produceService.getProduceList();
		}*/
		list = ListUtil.reserveList(list);
		model.addAttribute("list", list);
		model.addAttribute("taskName", "批量生产");
		model.addAttribute("url", "/produce/mobile_produceDetail.do");
		model.addAttribute("searchurl", "/produce/mobile_produceListSearch.do");

		model.addAttribute("info", new SearchInfo(ordernumber, customername, stylename, employeename, startdate, enddate));//将查询条件传回页面  hcj
		jsonUtil.sendJson(response, model);	
	}
	
	@RequestMapping(value = "/produce/mobile_produceDetail.do")
	//@Transactional(rollbackFor = Exception.class)
	public void produceDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId=request.getParameter("orderId");
		Map<String,Object> orderInfo=produceService.getProduceDetail(Integer.parseInt(orderId));
		model.addAttribute("orderInfo", orderInfo);
		jsonUtil.sendJson(response, model);	
	}
	
	
	@RequestMapping(value = "/produce/mobile_produceSubmit.do")
	//@Transactional(rollbackFor = Exception.class)
	public void produceSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {	
		boolean result = Boolean.parseBoolean(request.getParameter("result"));
		String taskId = request.getParameter("taskId");
		int orderId = Integer.parseInt(request.getParameter("orderId"));
		List<Produce> produceList = null;
		if (result) {
			String produceColor = request.getParameter("produce_color");
			String produceXS = request.getParameter("produce_xs");
			String produceS = request.getParameter("produce_s");
			String produceM = request.getParameter("produce_m");
			String produceL = request.getParameter("produce_l");
			String produceXL = request.getParameter("produce_xl");
			String produceXXL = request.getParameter("produce_xxl");
			String produceJ = request.getParameter("produce_j");
			String processing_side = request.getParameter("processing_side");
			Order torder = orderService.findByOrderId(orderId+"");
			torder.setPayAccountInfo(processing_side);
			orderService.updateOrder(torder);

			produceList = produceService.getProduceList(orderId, produceColor, produceXS, 
					produceS, produceM, produceL, produceXL, produceXXL, produceJ, Produce.TYPE_PRODUCED);
		}
		boolean isSuccess=produceService.pruduceSubmit(taskId, result, produceList,orderId);
		model.addAttribute(ISSUCCESS, isSuccess);
		jsonUtil.sendJson(response, model);	
	}
	@Autowired
	private JSONUtil jsonUtil;
}
