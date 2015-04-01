package nju.software.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nju.software.dataobject.Employee;
import nju.software.dataobject.SearchInfo;
import nju.software.service.DesignService;
import nju.software.service.EmployeeService;
import nju.software.service.MarketService;
import nju.software.util.DateUtil;
import nju.software.util.FileOperateUtil;
import nju.software.util.ListUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
public class DesignController {
	
//	private final static String CAD_URL = "C:/fmc/cad/";
//	private final static String CRAFT_FILE_URL = "C:/fmc/craft/";
	
	private final static String CAD_URL = "/upload/cad/";
	private final static String CRAFT_FILE_URL = "/upload/craft/";
	
	// ===========================设计验证=================================
	@RequestMapping(value = "/design/verifyDesignList.do")
	//@Transactional(rollbackFor = Exception.class)
	public String verifyDesignList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<Map<String, Object>> orderList = designService
				.getVerifyDesignList();
		orderList = ListUtil.reserveList(orderList);
		model.addAttribute("list", orderList);
		model.addAttribute("taskName", "设计验证");
		model.addAttribute("url", "/design/verifyDesignDetail.do");
		model.addAttribute("searchurl", "/design/verifyDesignListSearch.do");
		
		return "/design/verifyDesignList";
	}
	@Autowired
	private EmployeeService employeeService;
	// ===========================设计验证订单搜索=================================
	@RequestMapping(value = "/design/verifyDesignListSearch.do")
	//@Transactional(rollbackFor = Exception.class)
	public String verifyDesignListSearch(HttpServletRequest request,
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
		List<Map<String, Object>> orderList = designService
				.getSearchVerifyDesignList(ordernumber,customername,stylename,startdate,enddate,employeeIds);
		orderList = ListUtil.reserveList(orderList);
		model.addAttribute("list", orderList);
		model.addAttribute("taskName", "设计验证订单搜索");
		model.addAttribute("url", "/design/verifyDesignDetail.do");
		model.addAttribute("searchurl", "/design/verifyDesignListSearch.do");
		
		model.addAttribute("info", new SearchInfo(ordernumber, customername, stylename, employeename, startdate, enddate));//将查询条件传回页面  hcj
		return "/design/verifyDesignList";
	}
	
	
	@RequestMapping(value = "/design/verifyDesignDetail.do")
	//@Transactional(rollbackFor = Exception.class)
	public String verifyDesignDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Integer orderId = Integer.parseInt(request.getParameter("orderId"));
		Map<String, Object> orderInfo = designService
				.getVerifyDesignDetail(orderId);
		model.addAttribute("orderInfo", orderInfo);
		return "/design/verifyDesignDetail";
	}

	@RequestMapping(value = "/design/verifyDesignSubmit.do")
	//@Transactional(rollbackFor = Exception.class)
	public String verifyDesignSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String taskId = request.getParameter("taskId");
		boolean result = Boolean
				.parseBoolean(request.getParameter("designVal"));
		String comment = request.getParameter("suggestion");
		designService.verifyDesignSubmit(taskId, result, comment);
		return "forward:/design/verifyDesignList.do";
	}
	
	// =========================设计工艺验证===============================
	
	@RequestMapping(value = "design/computeDesignCostList.do", method= RequestMethod.GET)
	//@Transactional(rollbackFor = Exception.class)
	public String computeDesignCostList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		List<Map<String,Object>> list = designService.getComputeDesignCostList();
		list = ListUtil.reserveList(list);
		model.addAttribute("list", list);
		model.addAttribute("taskName", "设计工艺验证");
		model.addAttribute("url", "/design/computeDesignCostDetail.do");
		model.addAttribute("searchurl", "/design/computeDesignCostListSearch.do");

		return "/design/computeDesignCostList";
	}
	
	@RequestMapping(value = "design/computeDesignCostListSearch.do" )
	//@Transactional(rollbackFor = Exception.class)
	public String computeProduceCostListSearch(HttpServletRequest request,
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
		for(int i=0; i<employeeIds.length; i++){
			employeeIds[i] = employees.get(i).getEmployeeId();
		}
		List<Map<String,Object>> list = designService.getSearchComputeDesignCostList(ordernumber,customername,stylename,startdate,enddate,employeeIds);
		list = ListUtil.reserveList(list);
		model.addAttribute("list", list);
		model.addAttribute("taskName", "设计工艺验证");
		model.addAttribute("url", "/design/computeDesignCostDetail.do");
		model.addAttribute("searchurl", "/design/computeDesignCostListSearch.do");
		model.addAttribute("info", new SearchInfo(ordernumber, customername, stylename, employeename, startdate, enddate));//将查询条件传回页面  hcj
		return "/design/computeDesignCostList";
	}
	
	@RequestMapping(value = "design/computeDesignCostDetail.do", method= RequestMethod.GET)
	//@Transactional(rollbackFor = Exception.class)
	public String computeDesignCostDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Integer orderId=Integer.parseInt(request.getParameter("orderId"));
		Map<String,Object> orderInfo = designService.getComputeDesignCostInfo(orderId);
		model.addAttribute("orderInfo", orderInfo);
		return "/design/computeCraftCostDetail";
	}
	
	@RequestMapping(value = "design/computeDesignCostSubmit.do", method= RequestMethod.POST)
	//@Transactional(rollbackFor = Exception.class)
	public String computeDesignCostSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Integer orderId = Integer.parseInt(request.getParameter("orderId"));
		String taskId = request.getParameter("taskId");
		
		boolean result = Boolean.parseBoolean(request.getParameter("result"));// 是否拒绝设计
		String comment = request.getParameter("suggestion");
		//如果拒绝，则不进行设计工艺成本的核算，直接返回
		if (result == false) {
			designService.verifyDesignSubmit(taskId, result, comment);
			return "redirect:/design/computeDesignCostList.do";
		}
		
		//是否需要工艺
		String needCraft = request.getParameter("needcraft");
		//印花费
		String stampDutyMoney = request.getParameter("stampDutyMoney");
		//水洗吊染费
		String washHangDyeMoney = request.getParameter("washHangDyeMoney");
		//激光费
		String laserMoney = request.getParameter("laserMoney");
		//刺绣费
	    String embroideryMoney = request.getParameter("embroideryMoney");
	    //压皱费
		String crumpleMoney = request.getParameter("crumpleMoney");
		//开版费用
		String openVersionMoney = request.getParameter("openVersionMoney");

		if (needCraft.equals("0")) {
			stampDutyMoney = "0";
			washHangDyeMoney = "0";
			laserMoney = "0";
			embroideryMoney = "0";
			crumpleMoney = "0";
			openVersionMoney = "0";
		}
		//设计工艺报价提交
		designService.computeDesignCostSubmit(
				orderId,
				taskId,
				result,
				comment,
				Short.parseShort(needCraft),
				Float.parseFloat(stampDutyMoney),
				Float.parseFloat(washHangDyeMoney),
				Float.parseFloat(laserMoney),
				Float.parseFloat(embroideryMoney),
				Float.parseFloat(crumpleMoney),
				Float.parseFloat(openVersionMoney)
				);
		
		return "redirect:/design/computeDesignCostList.do";
	}
	

	// ===========================上传版型=================================
	@RequestMapping(value = "/design/getUploadDesignList.do")
	//@Transactional(rollbackFor = Exception.class)
	public String getUploadDesignList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<Map<String, Object>> list = designService.getUploadDesignList();
		list = ListUtil.reserveList(list);
		model.addAttribute("list", list);
//		model.addAttribute("taskName", "录入版型数据");
		model.addAttribute("taskName", "样衣版型录入及生产");
		model.addAttribute("url", "/design/getUploadDesignDetail.do");
		model.addAttribute("searchurl", "/design/getUploadDesignListSearch.do");

		return "/design/getUploadDesignList";
	}

	// ===========================上传版型搜索=================================
	@RequestMapping(value = "/design/getUploadDesignListSearch.do")
	//@Transactional(rollbackFor = Exception.class)
	public String getUploadDesignListSearch(HttpServletRequest request,
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
		List<Map<String, Object>> list = designService.getSearchUploadDesignList(ordernumber,customername,stylename,startdate,enddate,employeeIds);
		list = ListUtil.reserveList(list);
		model.addAttribute("list", list);
		model.addAttribute("taskName", "录入版型数据");
		model.addAttribute("url", "/design/getUploadDesignDetail.do");
		model.addAttribute("searchurl", "/design/getUploadDesignListSearch.do");

		model.addAttribute("info", new SearchInfo(ordernumber, customername, stylename, employeename, startdate, enddate));//将查询条件传回页面  hcj
		return "/design/getUploadDesignList";
	}
	
	@RequestMapping(value = "/design/getUploadDesignDetail.do")
	//@Transactional(rollbackFor = Exception.class)
	public String getUploadDesignDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = request.getParameter("orderId");
		Map<String, Object> orderInfo = designService
				.getUploadDesignDetail(Integer.parseInt(orderId));
		String  orderSampleStatus =designService.getCraftInfo(Integer.parseInt(orderId));
		model.addAttribute("orderInfo", orderInfo);
		//判断订单样衣采购的状态
		//1.正在进行样衣原料料采购  2.正在进行样衣面料工艺加工 3.样衣原料已经准备好（无需工艺），请根据样衣单到采购部领取面料
		//4.样衣原料工艺已完成，请根据样衣到印花设计部领取面料
		model.addAttribute("orderSampleStatus",orderSampleStatus);
		return "/design/getUploadDesignDetail";
	}

	//录入版型数据
	@RequestMapping(value = "/design/uploadDesignSubmit.do", method = RequestMethod.POST)
	//@Transactional(rollbackFor = Exception.class)
	public String uploadDesignSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = request.getParameter("orderId");
		String taskId = request.getParameter("taskId");
		String cadSide = request.getParameter("cadSide");//制版人
		Timestamp completeTime = this.getTime(request.getParameter("completeTime"));//制版完成时间
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("CADFile");
		String filename = file.getOriginalFilename();
		
		// 将图片保存在和项目根目录同级的文件夹upload下
		String curPath = request.getSession().getServletContext()
				.getRealPath("/");// 获取当前路径
		String fatherPath = new File(curPath).getParent();// 当前路径的上级目录
		String relativePath = File.separator + "upload" + File.separator
				+ "cad" + File.separator + orderId;
		String filedir = fatherPath + relativePath;// 最终要保存的路径
		
		String fileid = "CADFile";
		FileOperateUtil.Upload(request, filedir, null, fileid);
		
		String url = CAD_URL + orderId + "/" + filename;//最终保存在数据库的相对路径
 		
		Timestamp uploadTime = new Timestamp(new Date().getTime());
		designService.EntryCadData(Integer.parseInt(orderId),
				taskId, url, uploadTime, cadSide, completeTime);
 		Map<String, Object> orderInfo = designService
				.getUploadDesignDetail(Integer.parseInt(orderId));
		String  orderSampleStatus =designService.getCraftInfo(Integer.parseInt(orderId));
		model.addAttribute("orderInfo", orderInfo);
		model.addAttribute("orderSampleStatus",orderSampleStatus);
		return "/design/getUploadDesignDetail";
//		return "forward:/design/getUploadDesignList.do";
	}
	/** 
	* @Title: uploadDesignSubmit_all 
	* @Description: TODO:设计部门可以随时修改上传的版型数据
	* @param @param request
	* @param @param response
	* @param @param model
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	@RequestMapping(value = "/design/uploadDesignSubmit_all.do", method = RequestMethod.POST)
	//@Transactional(rollbackFor = Exception.class)
	public String uploadDesignSubmit_all(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = request.getParameter("orderId");
		String cadSide = request.getParameter("cadSide");//制版人
		Timestamp completeTime = this.getTime(request.getParameter("completeTime"));//制版完成时间
		String taskId ="1";
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("CADFile");
		String filename = file.getOriginalFilename();
		
		// 将图片保存在和项目根目录同级的文件夹upload下
		String curPath = request.getSession().getServletContext()
				.getRealPath("/");// 获取当前路径
		String fatherPath = new File(curPath).getParent();// 当前路径的上级目录
		String relativePath = File.separator + "upload" + File.separator
				+ "cad" + File.separator + orderId;
		String filedir = fatherPath + relativePath;// 最终要保存的路径
		
		String fileid = "CADFile";
		FileOperateUtil.Upload(request, filedir, null, fileid);
		
		String url = CAD_URL + orderId + "/" + filename;//最终保存在数据库的相对路径
		
		Timestamp uploadTime = new Timestamp(new Date().getTime());
		designService.EntryCadData(Integer.parseInt(orderId),
				taskId, url, uploadTime, cadSide, completeTime);
 		Map<String, Object> orderInfo = marketService.getOrderDetail(Integer.valueOf(orderId));
		model.addAttribute("orderInfo", orderInfo);
		model.addAttribute("orderInfo", orderInfo);
		return "/market/orderDetail";
	}
 
    //生产样衣
	@RequestMapping(value = "/design/produceSampleSubmit.do")
	//@Transactional(rollbackFor = Exception.class)
	public String produceSampleSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = request.getParameter("orderId");
		boolean result =request.getParameter("result").equals("1");
		String taskId =request.getParameter("taskId");
//		designService.produceSampleSubmit(taskId, result);
		designService.produceSampleSubmit(taskId, result,orderId);
		return "forward:/design/getUploadDesignList.do";
	}
	
	
	// ===========================修改版型=================================
	@RequestMapping(value = "/design/getModifyDesignList.do")
	//@Transactional(rollbackFor = Exception.class)
	public String getModifyDesignList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<Map<String, Object>> list = designService.getModifyDesignList();
		list = ListUtil.reserveList(list);
		model.addAttribute("list", list);
		model.addAttribute("taskName", "设计部确认");
		model.addAttribute("url", "/design/getModifyDesignDetail.do");
		model.addAttribute("searchurl", "/design/getModifyDesignListSearch.do");

		return "/design/getModifyDesignList";
	}

	// ===========================修改版型=================================
	@RequestMapping(value = "/design/getModifyDesignListSearch.do")
	//@Transactional(rollbackFor = Exception.class)
	public String getModifyDesignListSearch(HttpServletRequest request,
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
		List<Map<String, Object>> list = designService.getSearchModifyDesignList(ordernumber,customername,stylename,startdate,enddate,employeeIds);
		list = ListUtil.reserveList(list);
		model.addAttribute("list", list);
		model.addAttribute("taskName", "设计部确认");
		model.addAttribute("url", "/design/getModifyDesignDetail.do");
		model.addAttribute("searchurl", "/design/getModifyDesignListSearch.do");
		model.addAttribute("info", new SearchInfo(ordernumber, customername, stylename, employeename, startdate, enddate));//将查询条件传回页面  hcj
		return "/design/getModifyDesignList";
	}
	
	@RequestMapping(value = "/design/getModifyDesignDetail.do")
	//@Transactional(rollbackFor = Exception.class)
	public String getModifyDesignDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = request.getParameter("orderId");
		Map<String, Object> orderInfo = designService
				.getModifyDesignDetail(Integer.parseInt(orderId));
		model.addAttribute("orderInfo", orderInfo);
		return "/design/getModifyDesignDetail";
	}

	@RequestMapping(value = "/design/modifyDesignSubmit.do")
	//@Transactional(rollbackFor = Exception.class)
	public String modifyDesignSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = request.getParameter("orderId");
		String taskId = request.getParameter("taskId");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("CADFile");
		String filename = file.getOriginalFilename();
		
		// 将图片保存在和项目根目录同级的文件夹upload下
		String curPath = request.getSession().getServletContext()
				.getRealPath("/");// 获取当前路径
		String fatherPath = new File(curPath).getParent();// 当前路径的上级目录
		String relativePath = File.separator + "upload" + File.separator
				+ "cad" + File.separator + orderId;
		String filedir = fatherPath + relativePath;// 最终要保存的路径
		
		String fileid = "CADFile";
		FileOperateUtil.Upload(request, filedir, null, fileid);
		
		String url = CAD_URL + orderId + "/" + filename;//最终保存在数据库的相对路径
		
		Timestamp uploadTime = new Timestamp(new Date().getTime());
		designService.modifyDesignSubmit(Integer.parseInt(orderId),
				taskId, url, uploadTime);
		return "forward:/design/getModifyDesignList.do";
	}
	
	
	@RequestMapping(value = "design/needCraftSampleSubmit.do")
	//@Transactional(rollbackFor = Exception.class)
	public String needCraftSampleSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		int orderId = Integer.parseInt(request.getParameter("orderId"));
		String s_taskId = request.getParameter("taskId");
		String craftLeader = request.getParameter("craftLeader");//工艺负责人
		Timestamp completeTime = this.getTime(request.getParameter("completeTime"));//工艺完成时间
		
        designService.needCraftSampleSubmit(orderId, s_taskId, craftLeader, completeTime);
		return "redirect:/design/getNeedCraftSampleList.do";
	}	
	
    //上传工艺制作图
	@RequestMapping(value = "/design/uploadCraftFileSubmit.do")
	//@Transactional(rollbackFor = Exception.class)
	public String uploadCraftFileSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = request.getParameter("orderId");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile craftFile = multipartRequest.getFile("craftFile");
 

		// 将图片保存在和项目根目录同级的文件夹upload下
		String curPath = request.getSession().getServletContext()
				.getRealPath("/");// 获取当前路径
		String fatherPath = new File(curPath).getParent();// 当前路径的上级目录
		String relativePath = File.separator + "upload" + File.separator
				+ "craft" + File.separator + orderId;
		String filedir = fatherPath + relativePath;// 最终要保存的路径
		String craftFileName = craftFile.getOriginalFilename();
		String craftFileId = "craftFile";
		FileOperateUtil.Upload(request, filedir, null, craftFileId);

		String craftFileUrl = CRAFT_FILE_URL + orderId + "/" + craftFileName;// 保存在数据库里的相对路径
		
		designService.uploadCraftFileSubmit(Integer.parseInt(orderId),
				craftFileUrl);

		Map<String, Object> orderInfo = designService
				.getNeedCraftSampleDetail(Integer.parseInt(orderId));
		model.addAttribute("orderInfo", orderInfo);
		return "/design/needCraftSampleDetail";
	}

	// ===========================获取需要工艺制作的样衣=================================
	@RequestMapping(value = "/design/needCraftSampleDetail.do")
	//@Transactional(rollbackFor = Exception.class)
	public String needCraftSampleDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = request.getParameter("orderId");
		Map<String, Object> orderInfo = designService
				.getNeedCraftSampleDetail(Integer.parseInt(orderId));
		model.addAttribute("orderInfo", orderInfo);
		return "/design/needCraftSampleDetail";
	}
	
	// ===========================样衣工艺制作列表=================================
	@RequestMapping(value = "/design/getNeedCraftSampleList.do")
	//@Transactional(rollbackFor = Exception.class)
	public String getNeedCraftSampleList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
//		List<Map<String, Object>> list = designService.getNeedCraftList();
		List<Map<String, Object>> list = designService.getNeedCraftSampleList();
		list = ListUtil.reserveList(list);
		model.addAttribute("list", list);
		model.addAttribute("taskName", "样衣工艺制作");
		model.addAttribute("url", "/design/needCraftSampleDetail.do");
		model.addAttribute("searchurl", "/design/getNeedCraftSampleListSearch.do");

		return "/design/getNeedCraftSampleList";
	}
    
	@RequestMapping(value="/design/getNeedCraftSampleListSearch.do")
	//@Transactional(rollbackFor = Exception.class)
	public String getNeedCraftSampleListSearch(HttpServletRequest request,
			HttpServletResponse response,ModelMap model){
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
		List<Map<String,Object>> list = designService.getSearchNeedCraftSampleList(ordernumber,customername,stylename,startdate,enddate,employeeIds);
		list = ListUtil.reserveList(list);
		model.addAttribute("list", list);
		model.addAttribute("taskName", "样衣工艺制作");
		model.addAttribute("url", "/design/needCraftSampleDetail.do");
		model.addAttribute("searchurl", "/design/getNeedCraftSampleListSearch.do");
		model.addAttribute("info", new SearchInfo(ordernumber, customername, stylename, employeename, startdate, enddate));//将查询条件传回页面  hcj
		return "/design/getNeedCraftSampleList";
	
	    
	}
	
	@RequestMapping(value = "design/needCraftProductSubmit.do")
	//@Transactional(rollbackFor = Exception.class)
	public String needCraftProductSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws UnsupportedEncodingException {
		String crafsManName= new String( request.getParameter("crafsManName").getBytes("iso-8859-1"), "UTF-8");
		String crafsProduceDate =request.getParameter("crafsProduceDate");
		Timestamp timeProduceDate=this.getTime(crafsProduceDate);
		int orderId = Integer.parseInt(request.getParameter("orderId"));
		String s_taskId = request.getParameter("taskId");
        designService.needCraftProductSubmit(orderId,s_taskId,crafsManName,timeProduceDate);
		return "redirect:/design/getNeedCraftProductList.do";
	}	
	
	// ===========================获取需要工艺制作的大货订单=================================
	@RequestMapping(value = "/design/needCraftProductDetail.do")
	//@Transactional(rollbackFor = Exception.class)
	public String needCraftProductDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = request.getParameter("orderId");
		Map<String, Object> orderInfo = designService
				.getNeedCraftProductDetail(Integer.parseInt(orderId));
		model.addAttribute("orderInfo", orderInfo);
		return "/design/needCraftProductDetail";
	}
	
	// ===========================大货生产工艺制作列表=================================
	@RequestMapping(value = "/design/getNeedCraftProductList.do")
	//@Transactional(rollbackFor = Exception.class)
	public String getNeedCraftList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<Map<String, Object>> list = designService.getNeedCraftList();
		list = ListUtil.reserveList(list);
 		model.addAttribute("list", list);
		model.addAttribute("taskName", "大货工艺制作");
		model.addAttribute("url", "/design/needCraftProductDetail.do");
		model.addAttribute("searchurl", "/design/getNeedCraftProductListSearch.do");

		return "/design/getNeedCraftProductList";
	}

	// ===========================大货生产工艺制作列表查询=================================
	@RequestMapping(value = "/design/getNeedCraftProductListSearch.do")
	//@Transactional(rollbackFor = Exception.class)
	public String getNeedCraftListSearch(HttpServletRequest request,
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
		List<Map<String, Object>> list = designService.getSearchNeedCraftList(ordernumber,customername,stylename,startdate,enddate,employeeIds);
		list = ListUtil.reserveList(list);
		model.addAttribute("list", list);
		model.addAttribute("taskName", "大货工艺制作");
		model.addAttribute("url", "/design/needCraftProductDetail.do");
		model.addAttribute("searchurl", "/design/getNeedCraftProductListSearch.do");

		model.addAttribute("info", new SearchInfo(ordernumber, customername, stylename, employeename, startdate, enddate));//将查询条件传回页面  hcj
		return "/design/getNeedCraftProductList";
	}
	
	@RequestMapping(value = "design/getTypeSettingSliceSubmit.do")
	//@Transactional(rollbackFor = Exception.class)
	public String getTypeSettingSliceSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		int orderId = Integer.parseInt(request.getParameter("orderId"));
		String cadding_side = request.getParameter("cadding_side");
		String s_taskId = request.getParameter("taskId");
        designService.getTypeSettingSliceSubmit(orderId,cadding_side,s_taskId);
		return "redirect:/design/getTypeSettingSliceList.do";
	}	
	
	// ===========================获取需要排版切片的大货订单=================================
	@RequestMapping(value = "/design/getTypeSettingSliceDetail.do")
	//@Transactional(rollbackFor = Exception.class)
	public String getTypeSettingSliceDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = request.getParameter("orderId");
		Map<String, Object> orderInfo = designService
				.getTypeSettingSliceDetail(Integer.parseInt(orderId));
		model.addAttribute("orderInfo", orderInfo);
		String  orderSampleStatus=designService.getCraftInfo(Integer.parseInt(orderId));
		model.addAttribute("orderSampleStatus",orderSampleStatus);
		return "/design/getTypeSettingSliceDetail";
	}
	
	//=============================获取需要大货生产排版切片的任务   =============================
	@RequestMapping(value = "/design/getTypeSettingSliceList.do")
	//@Transactional(rollbackFor = Exception.class)
	public String getTypeSettingSliceList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<Map<String, Object>> list = designService.getTypeSettingSliceList();
		list = ListUtil.reserveList(list);
		model.addAttribute("list", list);
		model.addAttribute("taskName", "排版切片");
		model.addAttribute("url", "/design/getTypeSettingSliceDetail.do");
		model.addAttribute("searchurl", "/design/getTypeSettingSliceListSearch.do");

		return "/design/getTypeSettingSliceList";
	}
	
	//=============================获取需要大货生产排版切片的任务搜索   =============================
	@RequestMapping(value = "/design/getTypeSettingSliceListSearch.do")
	//@Transactional(rollbackFor = Exception.class)
	public String getTypeSettingSliceListSearch(HttpServletRequest request,
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
		List<Map<String, Object>> list = designService.getSearchTypeSettingSliceList(ordernumber,customername,stylename,startdate,enddate,employeeIds);
		list = ListUtil.reserveList(list);
		model.addAttribute("list", list);
		model.addAttribute("taskName", "排版切片任务搜索");
		model.addAttribute("url", "/design/getTypeSettingSliceDetail.do");
		model.addAttribute("searchurl", "/design/getTypeSettingSliceListSearch.do");

		model.addAttribute("info", new SearchInfo(ordernumber, customername, stylename, employeename, startdate, enddate));//将查询条件传回页面  hcj
		return "/design/getTypeSettingSliceList";
	}
	
	// ===========================确认版型=================================
	@RequestMapping(value = "/design/getConfirmDesignList.do")
	//@Transactional(rollbackFor = Exception.class)
	public String getConfirmDesignList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<Map<String, Object>> list = designService.getConfirmDesignList();
		list = ListUtil.reserveList(list);
		model.addAttribute("list", list);
		model.addAttribute("taskName", "设计生产确认");
		model.addAttribute("url", "/design/getConfirmDesignDetail.do");
		model.addAttribute("searchurl", "/design/getConfirmDesignListSearch.do");

		return "/design/getConfirmDesignList";
	}
	
	@RequestMapping(value = "/design/getConfirmDesignListSearch.do")
	//@Transactional(rollbackFor = Exception.class)
	public String getConfirmDesignListSearch(HttpServletRequest request,
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
		List<Map<String, Object>> list = designService.getSearchConfirmDesignList(ordernumber,customername,stylename,startdate,enddate,employeeIds);
		list = ListUtil.reserveList(list);
		model.addAttribute("list", list);
		model.addAttribute("taskName", "设计生产确认");
		model.addAttribute("url", "/design/getConfirmDesignDetail.do");
		model.addAttribute("searchurl", "/design/getConfirmDesignListSearch.do");
		model.addAttribute("info", new SearchInfo(ordernumber, customername, stylename, employeename, startdate, enddate));//将查询条件传回页面  hcj
		return "/design/getConfirmDesignList";
	}
	
	@RequestMapping(value = "/design/getConfirmDesignDetail.do")
	//@Transactional(rollbackFor = Exception.class)
	public String getConfirmDesignDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = request.getParameter("orderId");
		Map<String, Object> orderInfo = designService
				.getConfirmDesignDetail(Integer.parseInt(orderId));
		model.addAttribute("orderInfo", orderInfo);
		return "/design/getConfirmDesignDetail";
	}

	@RequestMapping(value = "/design/confirmDesignSubmit.do")
	//@Transactional(rollbackFor = Exception.class)
	public String confirmDesignSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = request.getParameter("orderId");
		String taskId = request.getParameter("taskId");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("CADFile");
		String filename = file.getOriginalFilename();
		
		// 将图片保存在和项目根目录同级的文件夹upload下
		String curPath = request.getSession().getServletContext()
				.getRealPath("/");// 获取当前路径
		String fatherPath = new File(curPath).getParent();// 当前路径的上级目录
		String relativePath = File.separator + "upload" + File.separator
				+ "cad" + File.separator + orderId;
		String filedir = fatherPath + relativePath;// 最终要保存的路径
		
		String fileid = "CADFile";
		FileOperateUtil.Upload(request, filedir, null, fileid);
		
		String url = CAD_URL + orderId + "/" + filename;//最终保存在数据库的相对路径
		
		Timestamp uploadTime = new Timestamp(new Date().getTime());
		designService.uploadDesignSubmit(Integer.parseInt(orderId),
				taskId, url, uploadTime);
		return "forward:/design/getConfirmDesignList.do";
	}

	
	@RequestMapping(value = "/downloadCadSubmit.do", method = RequestMethod.POST)
	@ResponseBody
	//@Transactional(rollbackFor = Exception.class)
	public void downloadCadSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String url = request.getParameter("cadUrl");
		String curPath = request.getSession().getServletContext()
				.getRealPath("/");// 获取当前路径
		String fatherPath = new File(curPath).getParent();// 当前路径的上级目录
		
		url = fatherPath + url;
		url = url.replace("/", File.separator);
		url = url.replace("\\", File.separator);
		
		System.out.println(url+"------------------------");
		FileOperateUtil.Download(response, url);
	}
	
	// ===========================在排版切片之前确认最终版型=================================
	@RequestMapping(value = "/design/getConfirmCadList.do")
	//@Transactional(rollbackFor = Exception.class)
	public String getConfirmCadList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<Map<String, Object>> list = designService.getConfirmCadList();
		list = ListUtil.reserveList(list);
		model.addAttribute("list", list);
		model.addAttribute("taskName", "确认最终版型");
		model.addAttribute("url", "/design/getConfirmCadDetail.do");
		model.addAttribute("searchurl", "/design/getConfirmCadListSearch.do");

		return "/design/getConfirmCadList";
	}
	
	// ===========================在排版切片之前确认最终版型搜索=================================
	@RequestMapping(value = "/design/getConfirmCadListSearch.do")
	//@Transactional(rollbackFor = Exception.class)
	public String getConfirmCadListSearch(HttpServletRequest request,
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

		List<Map<String, Object>> list = designService.getSearchConfirmCadList(ordernumber,customername,stylename,startdate,enddate,employeeIds);
		list = ListUtil.reserveList(list);
		model.addAttribute("list", list);
		model.addAttribute("taskName", "确认最终版型");
		model.addAttribute("url", "/design/getConfirmCadDetail.do");
		model.addAttribute("searchurl", "/design/getConfirmCadListSearch.do");

		model.addAttribute("info", new SearchInfo(ordernumber, customername, stylename, employeename, startdate, enddate));//将查询条件传回页面  hcj
		return "/design/getConfirmCadList";
	}
	
	@RequestMapping(value = "/design/getConfirmCadDetail.do")
	//@Transactional(rollbackFor = Exception.class)
	public String getConfirmCadDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = request.getParameter("orderId");
		Map<String, Object> orderInfo = designService
				.getConfirmCadDetail(Integer.parseInt(orderId));
		model.addAttribute("orderInfo", orderInfo);
		return "/design/getConfirmCadDetail";
	}
	@RequestMapping(value = "/design/confirmCadSubmit.do")
	//@Transactional(rollbackFor = Exception.class)
	public String confirmCadSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = request.getParameter("orderId");
		String taskId = request.getParameter("taskId");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("CADFile");
		String filename = file.getOriginalFilename();
		
		// 将图片保存在和项目根目录同级的文件夹upload下
		String curPath = request.getSession().getServletContext()
				.getRealPath("/");// 获取当前路径
		String fatherPath = new File(curPath).getParent();// 当前路径的上级目录
		String relativePath = File.separator + "upload" + File.separator
				+ "cad" + File.separator + orderId;
		String filedir = fatherPath + relativePath;// 最终要保存的路径
		
		String fileid = "CADFile";
		FileOperateUtil.Upload(request, filedir, null, fileid);
		
		String url = CAD_URL + orderId + "/" + filename;//最终保存在数据库的相对路径
		
		Timestamp uploadTime = new Timestamp(new Date().getTime());
		designService.confirmCadSubmit(Integer.parseInt(orderId),
				taskId, url, uploadTime);
		return "forward:/design/getConfirmCadList.do";
	}
	
	private Timestamp getTime(String time) {
		Date outDate = DateUtil.parse(time, DateUtil.haveSecondFormat);
		return new Timestamp(outDate.getTime());
	}
	
	@Autowired
	private DesignService designService;
	@Autowired
	private MarketService marketService;
}
