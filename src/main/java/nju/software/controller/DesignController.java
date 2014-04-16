package nju.software.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nju.software.dataobject.Account;
import nju.software.model.OrderInfo;
import nju.software.service.DesignService;
import nju.software.service.OrderService;
import nju.software.service.impl.JbpmTest;
import nju.software.util.FileOperateUtil;
import nju.software.util.JbpmAPIUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
public class DesignController {
	
	@Autowired
	private JbpmAPIUtil jbpmAPIUtil;
	@Autowired
	private OrderService orderService;
	@Autowired
	private DesignService designService;
	@Autowired
	private JbpmTest jbpmTest;
	
	
	
	
	
	/**
	 * 设计验证跳转链接
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "design/verifyDesignList.do", method= RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String verifyDesignList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		System.out.println("design verify ================ show task");
		Account account = (Account) request.getSession().getAttribute("cur_user");
//		String actorId = account.getUserRole();
		List<Map<String,Object>> orderList = designService.getVerifyDesignList();
		model.addAttribute("list", orderList);
		model.addAttribute("taskName", "设计验证");
		model.addAttribute("url", "/design/verifyDesignDetail.do");
		return "design/verifyDesignList";
	}
	/**
	 * 设计验证
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "design/verifyDesignSubmit.do")
	@Transactional(rollbackFor = Exception.class)
	public String verifyDesignSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		System.out.println("design verify ================");
		
		Account account = (Account) request.getSession().getAttribute("cur_user");
		boolean designVal = Boolean.parseBoolean(request.getParameter("designVal"));
		String s_orderId_request = (String) request.getParameter("orderId");
		int orderId_request = Integer.parseInt(s_orderId_request);
		String s_taskId = request.getParameter("taskId");
		long taskId = Long.parseLong(s_taskId);
		String comment = request.getParameter("suggestion");

		designService.verifyDesignSubmit(account, orderId_request, taskId, designVal, comment);
		
		return "redirect:/design/verifyDesignList.do";
	}
	
	/**
	 * 显示订单详细信息
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "design/verifyDesignDetail.do")
	@Transactional(rollbackFor = Exception.class)
	public String verifyDesignDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
//		String actorId = account.getUserRole();
		String s_orderId_request = (String) request.getParameter("orderId");
		int orderId_request = Integer.parseInt(s_orderId_request);
		long taskId = 0;
		OrderInfo orderInfo = designService.getVerifyDesignDetail(orderId_request, taskId);
		model.addAttribute("orderInfo", orderInfo);	
		return "design/verifyDesignDetail";
	}

	
	

	

	/**
	 * 成本核算跳转链接
	 * @author WangJian
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "design/getComputeDesignCostList.do", method= RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String getComputeDesignCostList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		List<Map<String,Object>> list = designService.getComputeDesignCostList();
		
		if(list.size()==0){
			jbpmTest.completeVerify("1", true);
			list=designService.getComputeDesignCostList();
		}
		
		model.addAttribute("list", list);	
		model.addAttribute("taskName", "设计成本核算");
		model.addAttribute("url", "/design/getComputeDesignCostDetail.do");
		return "design/computeDesignCostList";
	}
	
	/**
	 * 显示成本核算详细信息
	 * @author WangJian
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "design/getComputeDesignCostDetail.do")
	@Transactional(rollbackFor = Exception.class)
	public String getComputeDesignCostDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		String orderId=request.getParameter("orderId");
		OrderInfo orderInfo=designService.getComputeDesignCostDetail(Integer.parseInt(orderId));
		model.addAttribute("orderInfo", orderInfo);
	
		
		return "design/computeDesignCostDetail";
	}
	
	/**
	 * 成本核算
	 * @author WangJian
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "design/computeDesignCostSubmit.do", method= RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String computeDesignCostSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		String orderId = (String) request.getParameter("orderId");

		String taskId = request.getParameter("taskId");
		
		String design_cost = request.getParameter("design_cost");
	
	   designService.computeDesignCostSubmit( 
			   Integer.parseInt(orderId),
			   Long.parseLong(taskId), 
			   Float.parseFloat(design_cost)
			   );
		
		return "redirect:/design/getComputeDesignCostList.do";
	}
	
	
	
	
	
	/**uploadDesign
	 * 录入版型数据跳转链接
	 * @author WangJian
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "design/getUploadDesignList.do", method= RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String getUploadDesignList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {

		List<Map<String,Object>> list = designService.getUploadDesignList();
		model.addAttribute("list", list);	
		model.addAttribute("taskName", "录入版型数据");
		model.addAttribute("url", "/design/getUploadDesignDetail.do");
	    return "design/getUploadDesignList";
	}
	
	
	/**uploadDesign
	 * 录入版型数据 详细页面
	 * @author WangJian
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "design/getUploadDesignDetail.do", method= RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String getUploadDesignDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
       String orderId=request.getParameter("orderId");
	   OrderInfo orderInfo=designService.getUploadDesignDetail(Integer.parseInt(orderId));
	   model.addAttribute("orderInfo", orderInfo);
	   return "design/getUploadDesignDetail";
	}
	
	
	/**
	 * uploadDesign
	 * 录入版型数据 上传文件处理
	 * @author WangJian
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "design/uploadDesignSubmit.do", method= RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	
	public String uploadDesignSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {

		String orderId = (String) request.getParameter("orderId");
	
		String taskId = request.getParameter("taskId");
	
	 MultipartHttpServletRequest multipartRequest =(MultipartHttpServletRequest) request;
		
		  MultipartFile file = multipartRequest.getFile("CADFile");
		     
          	  String filename=file.getOriginalFilename();     
		      FileOperateUtil.Upload(file);
		   
		    String url="D:/"+"/"+ filename;
		    
		    Timestamp uploadTime = new Timestamp(new Date().getTime());
		    
		 designService.uploadDesignSubmit(
				Integer.parseInt(orderId), 
				 Long.parseLong(taskId),
				 url,
				 uploadTime);
	
		return "redirect:/design/getUploadDesignList.do";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

	/**MODIFY DESIGN
	 * 设计部设计CAD验证跳转链接
	 * @author WangJian
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "design/getModifyDesignList.do", method= RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String getModifyDesignList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {

		List<Map<String,Object>> list = designService.getModifyDesignList();
		model.addAttribute("list", list);		
		model.addAttribute("taskName", "设计部确认");
		model.addAttribute("url", "/design/getModifyDesignDetail.do");
		return "design/getModifyDesignList";
	}
	
	
	/**MODIFY DESIGN
	 * 设计CAD验证 上传 详细页面
	 * @author WangJian
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "design/getModifyDesignDetail.do", method= RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String getModifyDesignDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		 
		 String orderId=request.getParameter("orderId");
		   OrderInfo orderInfo=designService.getModifyDesignDetail(Integer.parseInt(orderId));
		   model.addAttribute("orderInfo", orderInfo);
		  
		
		
		return "design/getModifyDesignDetail";
	}
	
	
	/**
	 * MODIFY DESIGN
	 * 设计CAD设计部验证
	 * @author WangJian
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "design/modifyDesignSubmit.do", method= RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String modifyDesignSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		String orderId = (String) request.getParameter("orderId");
		
		String taskId = request.getParameter("taskId");
	
	 MultipartHttpServletRequest multipartRequest =(MultipartHttpServletRequest) request;
		
		  MultipartFile file = multipartRequest.getFile("CADFile");
		     
          	  String filename=file.getOriginalFilename();     
		      FileOperateUtil.Upload(file);
		   
		    String url="D:/"+"/"+ filename;
		    
		    Timestamp uploadTime = new Timestamp(new Date().getTime());
		 
  //上传文件实现一致，公用uploadDesign方法代码		    
		    
		 designService.uploadDesignSubmit(
				Integer.parseInt(orderId), 
				 Long.parseLong(taskId),
				 url,
				 uploadTime);
	
		return "redirect:/design/getModifyDesignList.do";
	}
	
	
	
	
	
	
	
	
	

	/**CONFIRM DESIGN
	 * 设计部生产版型CAD验证跳转链接
	 * @author WangJian
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "design/getConfirmDesignList.do", method= RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String getConfirmDesignList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {

		List<Map<String,Object>> list = designService.getConfirmDesignList();
		model.addAttribute("list", list);		
		model.addAttribute("taskName", "设计生产确认");
		model.addAttribute("url", "/design/getConfirmDesignDetail.do");
		return "design/getConfirmDesignList";
	}
	
	
	/**
	 * 生产版型CAD验证 上传 详细页面
	 * @author WangJian
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "design/getConfirmDesignDetail.do", method= RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String getConfirmDesignDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {

		 
		 String orderId=request.getParameter("orderId");
		   OrderInfo orderInfo=designService.getModifyDesignDetail(Integer.parseInt(orderId));
		   model.addAttribute("orderInfo", orderInfo);
		  
		
	
		
		
		return "design/getConfirmDesignDetail";
	}
	
	
	/**
	 * 
	 *生产版型 CAD设计部验证
	 *@author WangJian
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "design/confirmDesignSubmit.do", method= RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String confirmDesignSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		
		String orderId = (String) request.getParameter("orderId");
		
		String taskId = request.getParameter("taskId");
	
	 MultipartHttpServletRequest multipartRequest =(MultipartHttpServletRequest) request;
		
		  MultipartFile file = multipartRequest.getFile("CADFile");
		     
          	  String filename=file.getOriginalFilename();     
		      FileOperateUtil.Upload(file);
		   
		    String url="D:/"+"/"+ filename;
		    
		    Timestamp uploadTime = new Timestamp(new Date().getTime());
		 
  //上传文件实现一致，公用uploadDesign方法代码		    
		    
		 designService.uploadDesignSubmit(
				Integer.parseInt(orderId), 
				 Long.parseLong(taskId),
				 url,
				 uploadTime);
		return "redirect:/design/getConfirmDesignList.do";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
