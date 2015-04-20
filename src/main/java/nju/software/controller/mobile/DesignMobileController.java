package nju.software.controller.mobile;

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
import nju.software.service.CommonService;
import nju.software.service.DesignService;
import nju.software.service.EmployeeService;
import nju.software.service.MarketService;
import nju.software.util.DateUtil;
import nju.software.util.FileOperateUtil;
import nju.software.util.JSONUtil;
import nju.software.util.ListUtil;

import org.drools.lang.dsl.DSLMapParser.statement_return;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
public class DesignMobileController {
	@Autowired
         private JSONUtil jsonUtil ;
//	private final static String CAD_URL = "C:/fmc/cad/";
//	private final static String CRAFT_FILE_URL = "C:/fmc/craft/";
	private final static String UPLOAD_DIR = "upload_new";
	private final static String CAD_URL = "/upload_new/cad/";
	private final static String CRAFT_FILE_URL = "/upload_new/craft/";
	
	// ===========================设计验证=================================
	@RequestMapping(value = "/design/mobile_verifyDesignList.do")
	//@Transactional(rollbackFor = Exception.class)
	public String verifyDesignList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<Map<String, Object>> orderList = designService
				.getVerifyDesignList();
		orderList = ListUtil.reserveList(orderList);
		model.addAttribute("list", orderList);
		model.addAttribute("taskName", "设计验证");
		model.addAttribute("url", "/design/mobile_verifyDesignDetail.do");
		model.addAttribute("searchurl", "/design/mobile_verifyDesignListSearch.do");
		jsonUtil.sendJson(response, model);
		return "/design/mobile_verifyDesignList";
	}
	@Autowired
	private EmployeeService employeeService;
	// ===========================设计验证订单搜索=================================
	@RequestMapping(value = "/design/mobile_verifyDesignListSearch.do")
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
		model.addAttribute("url", "/design/mobile_verifyDesignDetail.do");
		model.addAttribute("searchurl", "/design/mobile_verifyDesignListSearch.do");
		
		model.addAttribute("info", new SearchInfo(ordernumber, customername, stylename, employeename, startdate, enddate));//将查询条件传回页面  hcj
		jsonUtil.sendJson(response, model);
		
		return "/design/mobile_verifyDesignList";
	}
	
	
	@RequestMapping(value = "/design/mobile_verifyDesignDetail.do")
	//@Transactional(rollbackFor = Exception.class)
	public String verifyDesignDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Integer orderId = Integer.parseInt(request.getParameter("orderId"));
		Map<String, Object> orderInfo = designService
				.getVerifyDesignDetail(orderId);
		model.addAttribute("orderInfo", orderInfo);
		jsonUtil.sendJson(response, model);
		return "/design/mobile_verifyDesignDetail";
	}

	@RequestMapping(value = "/design/mobile_verifyDesignSubmit.do")
	//@Transactional(rollbackFor = Exception.class)
	public String verifyDesignSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String taskId = request.getParameter("taskId");
		boolean result = Boolean
				.parseBoolean(request.getParameter("designVal"));
		String comment = request.getParameter("suggestion");
		boolean isSuccess =designService.verifyDesignSubmit(taskId, result, comment);
		model.addAttribute("isSuccess", isSuccess);
		jsonUtil.sendJson(response, model);
		return "forward:/design/mobile_verifyDesignList.do";
	}
	
	// =========================设计工艺验证===============================
	
	@RequestMapping(value = "/design/mobile_computeDesignCostList.do", method= RequestMethod.GET)
	//@Transactional(rollbackFor = Exception.class)
	public String computeDesignCostList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		List<Map<String,Object>> list = designService.getComputeDesignCostList();
		list = ListUtil.reserveList(list);
		model.addAttribute("list", list);
		model.addAttribute("taskName", "设计工艺验证");
		model.addAttribute("url", "/design/mobile_computeDesignCostDetail.do");
		model.addAttribute("searchurl", "/design/mobile_computeDesignCostListSearch.do");
		jsonUtil.sendJson(response, model);
		return "/design/mobile_computeDesignCostList";
	}
	
	@RequestMapping(value = "/design/mobile_computeDesignCostListSearch.do" )
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
		model.addAttribute("url", "/design/mobile_computeDesignCostDetail.do");
		model.addAttribute("searchurl", "/design/mobile_computeDesignCostListSearch.do");
		model.addAttribute("info", new SearchInfo(ordernumber, customername, stylename, employeename, startdate, enddate));//将查询条件传回页面  hcj
		jsonUtil.sendJson(response, model);
		return "/design/mobile_computeDesignCostList";
	}
	
	@RequestMapping(value = "/design/mobile_computeDesignCostDetail.do", method= RequestMethod.GET)
	//@Transactional(rollbackFor = Exception.class)
	public String computeDesignCostDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Integer orderId=Integer.parseInt(request.getParameter("orderId"));
		Map<String,Object> orderInfo = designService.getComputeDesignCostInfo(orderId);
		model.addAttribute("orderInfo", orderInfo);
		jsonUtil.sendJson(response, model);
		return "/design/mobile_computeCraftCostDetail";
	}
	
	@RequestMapping(value = "/design/mobile_computeDesignCostSubmit.do", method= RequestMethod.POST)
	//@Transactional(rollbackFor = Exception.class)
	public String computeDesignCostSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Integer orderId = Integer.parseInt(request.getParameter("orderId"));
		String taskId = request.getParameter("taskId");
		
		boolean result = Boolean.parseBoolean(request.getParameter("result"));// 是否拒绝设计
		String comment = request.getParameter("suggestion");
		//如果拒绝，则不进行设计工艺成本的核算，直接返回
		if (result == false) {
			boolean isSuccess=designService.verifyDesignSubmit(taskId, result, comment);
			model.addAttribute("isSuccess", isSuccess);
			jsonUtil.sendJson(response, model);
			return "redirect:/design/mobile_computeDesignCostList.do";
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
		
		jsonUtil.sendJson(response, model);
		return "redirect:/design/mobile_computeDesignCostList.do";
	}
	

	// ===========================上传版型=================================
	@RequestMapping(value = "/design/mobile_getUploadDesignList.do")
	//@Transactional(rollbackFor = Exception.class)
	public String getUploadDesignList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<Map<String, Object>> list = designService.getUploadDesignList();
		list = ListUtil.reserveList(list);
		model.addAttribute("list", list);
//		model.addAttribute("taskName", "录入版型数据");
		model.addAttribute("taskName", "样衣版型录入及生产");
		model.addAttribute("url", "/design/mobile_getUploadDesignDetail.do");
		model.addAttribute("searchurl", "/design/mobile_getUploadDesignListSearch.do");
		jsonUtil.sendJson(response, model);
		return "/design/mobile_getUploadDesignList";
	}

	// ===========================上传版型搜索=================================
	@RequestMapping(value = "/design/mobile_getUploadDesignListSearch.do")
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
		model.addAttribute("url", "/design/mobile_getUploadDesignDetail.do");
		model.addAttribute("searchurl", "/design/mobile_getUploadDesignListSearch.do");

		model.addAttribute("info", new SearchInfo(ordernumber, customername, stylename, employeename, startdate, enddate));//将查询条件传回页面  hcj
		jsonUtil.sendJson(response, model);
		return "/design/mobile_getUploadDesignList";
	}
	
	@RequestMapping(value = "/design/mobile_getUploadDesignDetail.do")
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
		jsonUtil.sendJson(response, model);
		return "/design/mobile_getUploadDesignDetail";
	}

	//录入版型数据
	@RequestMapping(value = "/design/mobile_uploadDesignSubmit.do", method = RequestMethod.POST)
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
		String relativePath = File.separator + UPLOAD_DIR + File.separator
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
		jsonUtil.sendJson(response, model);
		return "/design/mobile_getUploadDesignDetail";
//		return "forward:/design/mobile_getUploadDesignList.do";
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
	@RequestMapping(value = "/design/mobile_uploadDesignSubmit_all.do", method = RequestMethod.POST)
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
		String relativePath = File.separator + UPLOAD_DIR + File.separator
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
		jsonUtil.sendJson(response, model);
		return "/market/orderDetail";
	}
 
    //生产样衣
	@RequestMapping(value = "/design/mobile_produceSampleSubmit.do")
	//@Transactional(rollbackFor = Exception.class)
	public String produceSampleSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = request.getParameter("orderId");
		boolean result =request.getParameter("result").equals("1");
		String taskId =request.getParameter("taskId");
//		designService.produceSampleSubmit(taskId, result);
		boolean isSuccess=designService.produceSampleSubmit(taskId, result,orderId);
		model.addAttribute("isSuccess", isSuccess);
		jsonUtil.sendJson(response, model);
		return "forward:/design/mobile_getUploadDesignList.do";
	}
	
	
	// ===========================修改版型=================================
	@RequestMapping(value = "/design/mobile_getModifyDesignList.do")
	//@Transactional(rollbackFor = Exception.class)
	public String getModifyDesignList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<Map<String, Object>> list = designService.getModifyDesignList();
		list = ListUtil.reserveList(list);
		model.addAttribute("list", list);
		model.addAttribute("taskName", "设计部确认");
		model.addAttribute("url", "/design/mobile_getModifyDesignDetail.do");
		model.addAttribute("searchurl", "/design/mobile_getModifyDesignListSearch.do");
		jsonUtil.sendJson(response, model);
		return "/design/mobile_getModifyDesignList";
	}

	// ===========================修改版型=================================
	@RequestMapping(value = "/design/mobile_getModifyDesignListSearch.do")
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
		model.addAttribute("url", "/design/mobile_getModifyDesignDetail.do");
		model.addAttribute("searchurl", "/design/mobile_getModifyDesignListSearch.do");
		model.addAttribute("info", new SearchInfo(ordernumber, customername, stylename, employeename, startdate, enddate));//将查询条件传回页面  hcj
		jsonUtil.sendJson(response, model);
		return "/design/mobile_getModifyDesignList";
	}
	
	@RequestMapping(value = "/design/mobile_getModifyDesignDetail.do")
	//@Transactional(rollbackFor = Exception.class)
	public String getModifyDesignDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = request.getParameter("orderId");
		Map<String, Object> orderInfo = designService
				.getModifyDesignDetail(Integer.parseInt(orderId));
		model.addAttribute("orderInfo", orderInfo);
		jsonUtil.sendJson(response, model);
		return "/design/mobile_getModifyDesignDetail";
	}

	@RequestMapping(value = "/design/mobile_modifyDesignSubmit.do")
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
		String relativePath = File.separator + UPLOAD_DIR + File.separator
				+ "cad" + File.separator + orderId;
		String filedir = fatherPath + relativePath;// 最终要保存的路径
		
		String fileid = "CADFile";
		FileOperateUtil.Upload(request, filedir, null, fileid);
		
		String url = CAD_URL + orderId + "/" + filename;//最终保存在数据库的相对路径
		
		Timestamp uploadTime = new Timestamp(new Date().getTime());
		boolean isSuccess=designService.modifyDesignSubmit(Integer.parseInt(orderId),
				taskId, url, uploadTime);
		model.addAttribute("isSuccess", isSuccess);
		jsonUtil.sendJson(response, model);
		return "forward:/design/mobile_getModifyDesignList.do";
	}
	
	
	@RequestMapping(value = "/design/mobile_needCraftSampleSubmit.do")
	//@Transactional(rollbackFor = Exception.class)
	public String needCraftSampleSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		int orderId = Integer.parseInt(request.getParameter("orderId"));
		String s_taskId = request.getParameter("taskId");
		String craftLeader = request.getParameter("craftLeader");//工艺负责人
		Timestamp completeTime = this.getTime(request.getParameter("completeTime"));//工艺完成时间
		
        designService.needCraftSampleSubmit(orderId, s_taskId, craftLeader, completeTime);
        jsonUtil.sendJson(response, model);
        return "redirect:/design/mobile_getNeedCraftSampleList.do";
	}	
	
    //上传工艺制作图
	@RequestMapping(value = "/design/mobile_uploadCraftFileSubmit.do")
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
		String relativePath = File.separator + UPLOAD_DIR + File.separator
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
		jsonUtil.sendJson(response, model);
		return "/design/mobile_needCraftSampleDetail";
	}

	// ===========================获取需要工艺制作的样衣=================================
	@RequestMapping(value = "/design/mobile_needCraftSampleDetail.do")
	//@Transactional(rollbackFor = Exception.class)
	public String needCraftSampleDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = request.getParameter("orderId");
		Map<String, Object> orderInfo = designService
				.getNeedCraftSampleDetail(Integer.parseInt(orderId));
		model.addAttribute("orderInfo", orderInfo);
		jsonUtil.sendJson(response, model);
		return "/design/mobile_needCraftSampleDetail";
	}
	
	// ===========================样衣工艺制作列表=================================
	@RequestMapping(value = "/design/mobile_getNeedCraftSampleList.do")
	//@Transactional(rollbackFor = Exception.class)
	public String getNeedCraftSampleList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
//		List<Map<String, Object>> list = designService.getNeedCraftList();
		List<Map<String, Object>> list = designService.getNeedCraftSampleList();
		list = ListUtil.reserveList(list);
		model.addAttribute("list", list);
		model.addAttribute("taskName", "样衣工艺制作");
		model.addAttribute("url", "/design/mobile_needCraftSampleDetail.do");
		model.addAttribute("searchurl", "/design/mobile_getNeedCraftSampleListSearch.do");
		jsonUtil.sendJson(response, model);
		return "/design/mobile_getNeedCraftSampleList";
	}
    
	@RequestMapping(value="/design/mobile_getNeedCraftSampleListSearch.do")
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
		model.addAttribute("url", "/design/mobile_needCraftSampleDetail.do");
		model.addAttribute("searchurl", "/design/mobile_getNeedCraftSampleListSearch.do");
		model.addAttribute("info", new SearchInfo(ordernumber, customername, stylename, employeename, startdate, enddate));//将查询条件传回页面  hcj
		jsonUtil.sendJson(response, model);
		return "/design/mobile_getNeedCraftSampleList";
	
	    
	}
	
	@RequestMapping(value = "/design/mobile_needCraftProductSubmit.do")
	//@Transactional(rollbackFor = Exception.class)
	public String needCraftProductSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws UnsupportedEncodingException {
		String crafsManName= new String( request.getParameter("crafsManName").getBytes("iso-8859-1"), "UTF-8");
		String crafsProduceDate =request.getParameter("crafsProduceDate");
		Timestamp timeProduceDate=this.getTime(crafsProduceDate);
		int orderId = Integer.parseInt(request.getParameter("orderId"));
		String s_taskId = request.getParameter("taskId");
        designService.needCraftProductSubmit(orderId,s_taskId,crafsManName,timeProduceDate);
        jsonUtil.sendJson(response, model);
        return "redirect:/design/mobile_getNeedCraftProductList.do";
	}	
	
	// ===========================获取需要工艺制作的大货订单=================================
	@RequestMapping(value = "/design/mobile_needCraftProductDetail.do")
	//@Transactional(rollbackFor = Exception.class)
	public String needCraftProductDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = request.getParameter("orderId");
		Map<String, Object> orderInfo = designService
				.getNeedCraftProductDetail(Integer.parseInt(orderId));
		model.addAttribute("orderInfo", orderInfo);
		jsonUtil.sendJson(response, model);
		return "/design/mobile_needCraftProductDetail";
	}
	
	// ===========================大货生产工艺制作列表=================================
	@RequestMapping(value = "/design/mobile_getNeedCraftProductList.do")
	//@Transactional(rollbackFor = Exception.class)
	public String getNeedCraftList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<Map<String, Object>> list = designService.getNeedCraftList();
		list = ListUtil.reserveList(list);
 		model.addAttribute("list", list);
		model.addAttribute("taskName", "大货工艺制作");
		model.addAttribute("url", "/design/mobile_needCraftProductDetail.do");
		model.addAttribute("searchurl", "/design/mobile_getNeedCraftProductListSearch.do");
		jsonUtil.sendJson(response, model);
		return "/design/mobile_getNeedCraftProductList";
	}

	// ===========================大货生产工艺制作列表查询=================================
	@RequestMapping(value = "/design/mobile_getNeedCraftProductListSearch.do")
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
		model.addAttribute("url", "/design/mobile_needCraftProductDetail.do");
		model.addAttribute("searchurl", "/design/mobile_getNeedCraftProductListSearch.do");

		model.addAttribute("info", new SearchInfo(ordernumber, customername, stylename, employeename, startdate, enddate));//将查询条件传回页面  hcj
		jsonUtil.sendJson(response, model);
		return "/design/mobile_getNeedCraftProductList";
	}
	
	@RequestMapping(value = "/design/mobile_getTypeSettingSliceSubmit.do")
	//@Transactional(rollbackFor = Exception.class)
	public String getTypeSettingSliceSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		int orderId = Integer.parseInt(request.getParameter("orderId"));
		String cadding_side = request.getParameter("cadding_side");
		String s_taskId = request.getParameter("taskId");
        designService.getTypeSettingSliceSubmit(orderId,cadding_side,s_taskId);
        jsonUtil.sendJson(response, model);
        return "redirect:/design/mobile_getTypeSettingSliceList.do";
	}	
	
	// ===========================获取需要排版切片的大货订单=================================
	@RequestMapping(value = "/design/mobile_getTypeSettingSliceDetail.do")
	//@Transactional(rollbackFor = Exception.class)
	public String getTypeSettingSliceDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = request.getParameter("orderId");
		Map<String, Object> orderInfo = designService
				.getTypeSettingSliceDetail(Integer.parseInt(orderId));
		model.addAttribute("orderInfo", orderInfo);
		String  orderSampleStatus=designService.getCraftInfo(Integer.parseInt(orderId));
		model.addAttribute("orderSampleStatus",orderSampleStatus);
		jsonUtil.sendJson(response, model);
		return "/design/mobile_getTypeSettingSliceDetail";
	}
	
	//=============================获取需要大货生产排版切片的任务   =============================
	@RequestMapping(value = "/design/mobile_getTypeSettingSliceList.do")
	//@Transactional(rollbackFor = Exception.class)
	public String getTypeSettingSliceList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<Map<String, Object>> list = designService.getTypeSettingSliceList();
		list = ListUtil.reserveList(list);
		model.addAttribute("list", list);
		model.addAttribute("taskName", "排版切片");
		model.addAttribute("url", "/design/mobile_getTypeSettingSliceDetail.do");
		model.addAttribute("searchurl", "/design/mobile_getTypeSettingSliceListSearch.do");
		jsonUtil.sendJson(response, model);
		return "/design/mobile_getTypeSettingSliceList";
	}
	
	//=============================获取需要大货生产排版切片的任务搜索   =============================
	@RequestMapping(value = "/design/mobile_getTypeSettingSliceListSearch.do")
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
		model.addAttribute("url", "/design/mobile_getTypeSettingSliceDetail.do");
		model.addAttribute("searchurl", "/design/mobile_getTypeSettingSliceListSearch.do");

		model.addAttribute("info", new SearchInfo(ordernumber, customername, stylename, employeename, startdate, enddate));//将查询条件传回页面  hcj
		jsonUtil.sendJson(response, model);
		return "/design/mobile_getTypeSettingSliceList";
	}
	
	// ===========================确认版型=================================
	@RequestMapping(value = "/design/mobile_getConfirmDesignList.do")
	//@Transactional(rollbackFor = Exception.class)
	public String getConfirmDesignList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<Map<String, Object>> list = designService.getConfirmDesignList();
		list = ListUtil.reserveList(list);
		model.addAttribute("list", list);
		model.addAttribute("taskName", "设计生产确认");
		model.addAttribute("url", "/design/mobile_getConfirmDesignDetail.do");
		model.addAttribute("searchurl", "/design/mobile_getConfirmDesignListSearch.do");
		jsonUtil.sendJson(response, model);
		return "/design/mobile_getConfirmDesignList";
	}
	
	@RequestMapping(value = "/design/mobile_getConfirmDesignListSearch.do")
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
		model.addAttribute("url", "/design/mobile_getConfirmDesignDetail.do");
		model.addAttribute("searchurl", "/design/mobile_getConfirmDesignListSearch.do");
		model.addAttribute("info", new SearchInfo(ordernumber, customername, stylename, employeename, startdate, enddate));//将查询条件传回页面  hcj
		jsonUtil.sendJson(response, model);
		return "/design/mobile_getConfirmDesignList";
	}
	
	@RequestMapping(value = "/design/mobile_getConfirmDesignDetail.do")
	//@Transactional(rollbackFor = Exception.class)
	public String getConfirmDesignDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = request.getParameter("orderId");
		Map<String, Object> orderInfo = designService
				.getConfirmDesignDetail(Integer.parseInt(orderId));
		model.addAttribute("orderInfo", orderInfo);
		jsonUtil.sendJson(response, model);
		return "/design/mobile_getConfirmDesignDetail";
	}

	@RequestMapping(value = "/design/mobile_confirmDesignSubmit.do")
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
		String relativePath = File.separator + UPLOAD_DIR + File.separator
				+ "cad" + File.separator + orderId;
		String filedir = fatherPath + relativePath;// 最终要保存的路径
		
		String fileid = "CADFile";
		FileOperateUtil.Upload(request, filedir, null, fileid);
		
		String url = CAD_URL + orderId + "/" + filename;//最终保存在数据库的相对路径
		
		Timestamp uploadTime = new Timestamp(new Date().getTime());
		designService.uploadDesignSubmit(Integer.parseInt(orderId),
				taskId, url, uploadTime);
		jsonUtil.sendJson(response, model);
		return "forward:/design/mobile_getConfirmDesignList.do";
	}

	
	@RequestMapping(value = "/mobile_downloadCadSubmit.do", method = RequestMethod.POST)
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
	@RequestMapping(value = "/design/mobile_getConfirmCadList.do")
	//@Transactional(rollbackFor = Exception.class)
	public String getConfirmCadList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<Map<String, Object>> list = designService.getConfirmCadList();
		list = ListUtil.reserveList(list);
		model.addAttribute("list", list);
		model.addAttribute("taskName", "确认最终版型");
		model.addAttribute("url", "/design/mobile_getConfirmCadDetail.do");
		model.addAttribute("searchurl", "/design/mobile_getConfirmCadListSearch.do");
		jsonUtil.sendJson(response, model);
		return "/design/mobile_getConfirmCadList";
	}
	
	// ===========================在排版切片之前确认最终版型搜索=================================
	@RequestMapping(value = "/design/mobile_getConfirmCadListSearch.do")
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
		model.addAttribute("url", "/design/mobile_getConfirmCadDetail.do");
		model.addAttribute("searchurl", "/design/mobile_getConfirmCadListSearch.do");

		model.addAttribute("info", new SearchInfo(ordernumber, customername, stylename, employeename, startdate, enddate));//将查询条件传回页面  hcj
		jsonUtil.sendJson(response, model);
		return "/design/mobile_getConfirmCadList";
	}
	
	@RequestMapping(value = "/design/mobile_getConfirmCadDetail.do")
	//@Transactional(rollbackFor = Exception.class)
	public String getConfirmCadDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = request.getParameter("orderId");
		Map<String, Object> orderInfo = designService
				.getConfirmCadDetail(Integer.parseInt(orderId));
		model.addAttribute("orderInfo", orderInfo);
		jsonUtil.sendJson(response, model);
		return "/design/mobile_getConfirmCadDetail";
	}
	@RequestMapping(value = "/design/mobile_confirmCadSubmit.do")
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
		String relativePath = File.separator + UPLOAD_DIR + File.separator
				+ "cad" + File.separator + orderId;
		String filedir = fatherPath + relativePath;// 最终要保存的路径
		
		String fileid = "CADFile";
		FileOperateUtil.Upload(request, filedir, null, fileid);
		
		String url = CAD_URL + orderId + "/" + filename;//最终保存在数据库的相对路径
		
		Timestamp uploadTime = new Timestamp(new Date().getTime());
		boolean isSuccess =designService.confirmCadSubmit(Integer.parseInt(orderId),
				taskId, url, uploadTime);
		model.addAttribute("isSuccess", isSuccess);
		jsonUtil.sendJson(response, model);
		return "forward:/design/mobile_getConfirmCadList.do";
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
