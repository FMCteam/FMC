package nju.software.controller;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nju.software.dataobject.Accessory;
import nju.software.dataobject.Account;
import nju.software.dataobject.Fabric;
import nju.software.dataobject.Logistics;
import nju.software.dataobject.Order;
import nju.software.model.OrderModel;
import nju.software.service.DesignService;
import nju.software.service.OrderService;
import nju.software.util.FileOperateUtil;
import nju.software.util.JbpmAPIUtil;

import org.jbpm.task.query.TaskSummary;
import org.jbpm.workflow.instance.WorkflowProcessInstance;
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
	
	
	
	
	
	
	/**
	 * 设计验证跳转链接
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "design/verify.do", method= RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String verify(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		System.out.println("design verify ================ show task");
		List<OrderModel> orderList = new ArrayList<OrderModel>();
		Account account = (Account) request.getSession().getAttribute("cur_user");
//		String actorId = account.getUserRole();
		String actorId = "SHEJIZHUGUAN";
		System.out.println("actorId: " + actorId);
		String taskName = "design_verification ";
		orderList = orderService.getOrderByActorIdAndTaskname(actorId, taskName);
		model.addAttribute("order_list", orderList);
		
		return "design/verify";
	}
	/**
	 * 设计验证
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "design/doVerify.do", method= RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String doVerify(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		System.out.println("design verify ================");
		
		Account account = (Account) request.getSession().getAttribute("cur_user");
		boolean designVal = Boolean.parseBoolean(request.getParameter("designVal"));
		String s_orderId_request = (String) request.getParameter("orderId");
		int orderId_request = Integer.parseInt(s_orderId_request);
		String s_taskId = request.getParameter("taskId");
		long taskId = Long.parseLong(s_taskId);
		String s_processId = request.getParameter("pinId");
		long processId = Long.parseLong(s_processId);
		String comment = request.getParameter("suggestion");
		String taskName = "design_verification ";
		designService.verify(account, orderId_request, taskId, processId, designVal, comment);
		
		return "redirect:/design/verify.do";
	}
	
	/**
	 * 显示订单详细信息
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "design/verifyDetail.do", method= RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String verifyDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		System.out.println("design verify ================ show detail");
		OrderModel orderModel = null;
		Account account = (Account) request.getSession().getAttribute("cur_user");
//		String actorId = account.getUserRole();
		String s_orderId_request = (String) request.getParameter("id");
		int orderId_request = Integer.parseInt(s_orderId_request);
		String s_taskId = request.getParameter("task_id");
		long taskId = Long.parseLong(s_taskId);
		String s_processId = request.getParameter("process_id");
		long processId = Long.parseLong(s_processId);
		orderModel = orderService.getOrderDetail(orderId_request, taskId, processId);
		Logistics logistics = designService.getLogisticsByOrderId(orderId_request);
		List<Fabric> fabricList = designService.getFabricByOrderId(orderId_request);
		List<Accessory> accessoryList = designService.getAccessoryByOrderId(orderId_request);
		model.addAttribute("orderModel", orderModel);
		model.addAttribute("logistics", logistics);
		model.addAttribute("fabric_list", fabricList);
		model.addAttribute("accessory_list", accessoryList);
		
		return "design/verify_detail";
	}

	
	


	/**
	 * 成本核算跳转链接
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "design/costAccounting.do", method= RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String costAccounting(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		System.out.println(" design cost Accounting ================ show task");
		List<OrderModel> orderList = new ArrayList<OrderModel>();
		Account account = (Account) request.getSession().getAttribute("cur_user");
//		String actorId = account.getUserRole();
		String actorId = "SHEJIZHUGUAN";
		System.out.println("actorId: " + actorId);
		String taskName = "design_accounting";
		orderList = orderService.getOrderByActorIdAndTaskname(actorId, taskName);
		if (orderList.isEmpty()) {
			System.out.println("no orderList ");
		}
		model.addAttribute("order_list", orderList);
		
		
		return "design/cost_accounting";
	}
	
	
	
	
	
	
	
	
	/**
	 * 显示成本核算详细信息
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "design/costAccountingDetail.do", method= RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String costAccountingDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		System.out.println("design costAccounting Detail ================ costAccountingDetail");
		OrderModel orderModel = null;
		Account account = (Account) request.getSession().getAttribute("cur_user");
//		String actorId = account.getUserRole();
		String s_orderId_request = (String) request.getParameter("id");
		int orderId_request = Integer.parseInt(s_orderId_request);
		String s_taskId = request.getParameter("task_id");
		long taskId = Long.parseLong(s_taskId);
		String s_processId = request.getParameter("process_id");
		long processId = Long.parseLong(s_processId);
		orderModel = orderService.getOrderDetail(orderId_request, taskId, processId);
		Logistics logistics = designService.getLogisticsByOrderId(orderId_request);
		List<Fabric> fabricList = designService.getFabricByOrderId(orderId_request);
		List<Accessory> accessoryList = designService.getAccessoryByOrderId(orderId_request);
		model.addAttribute("orderModel", orderModel);
		model.addAttribute("logistics", logistics);
		model.addAttribute("fabric_list", fabricList);
		model.addAttribute("accessory_list", accessoryList);
		
		return "design/costAccounting_detail";
	}
	
	
	
	
	
	
	
	/**
	 * 成本核算
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "design/doCostAccounting.do", method= RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String doCostAccounting(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		System.out.println("do  design cost Accounting ================");
		
		Account account = (Account) request.getSession().getAttribute("cur_user");
	
		String s_orderId_request = (String) request.getParameter("orderId");
		int orderId_request = Integer.parseInt(s_orderId_request);
		String s_taskId = request.getParameter("taskId");
		long taskId = Long.parseLong(s_taskId);
		String s_processId = request.getParameter("pinId");
		long processId = Long.parseLong(s_processId);
		String design_cost_temp = request.getParameter("design_cost");
		float design_cost=Float.parseFloat(design_cost_temp);
		String taskName = "design_accounting ";
		designService.costAccounting(account, orderId_request, taskId, processId, design_cost);
		
		return "redirect:/design/costAccounting.do";
	}
	
	
	
	
	
	/**
	 * 录入版型数据跳转链接
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "design/upload_CAD.do", method= RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String upload_CAD(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {

		System.out.println(" design upload CAD ================ show task");
		List<OrderModel> orderList = new ArrayList<OrderModel>();
		Account account = (Account) request.getSession().getAttribute("cur_user");
//		String actorId = account.getUserRole();
		String actorId = "SHEJIZHUGUAN";
		System.out.println("actorId: " + actorId);
		String taskName = "entering_data";
		orderList = orderService.getOrderByActorIdAndTaskname(actorId, taskName);
		if (orderList.isEmpty()) {
			System.out.println("no orderList ");
		}
		model.addAttribute("order_list", orderList);
		
		
		return "design/upload_CAD";
	}
	
	
	/**
	 * 录入版型数据 详细页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "design/uploadCAD_detail.do", method= RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String uploadCAD_detail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {

		System.out.println("design uploadCAD detail ================ uploadCAD detail");
		OrderModel orderModel = null;
		Account account = (Account) request.getSession().getAttribute("cur_user");
//		String actorId = account.getUserRole();
		String s_orderId_request = (String) request.getParameter("id");
		int orderId_request = Integer.parseInt(s_orderId_request);
		String s_taskId = request.getParameter("task_id");
		long taskId = Long.parseLong(s_taskId);
		String s_processId = request.getParameter("process_id");
		long processId = Long.parseLong(s_processId);
		orderModel = orderService.getOrderDetail(orderId_request, taskId, processId);
//		Logistics logistics = designService.getLogisticsByOrderId(orderId_request);
//		List<Fabric> fabricList = designService.getFabricByOrderId(orderId_request);
//		List<Accessory> accessoryList = designService.getAccessoryByOrderId(orderId_request);
		model.addAttribute("orderModel", orderModel);
	
		
		
		return "design/uploadCAD_detail";
	}
	
	
	/**
	 * 
	 * 录入版型数据 上传文件处理
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "design/doUploadCAD.do", method= RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String doUploadCAD(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
	System.out.println("do  design doUploadCAD ================");
		
		Account account = (Account) request.getSession().getAttribute("cur_user");
	
		String s_orderId_request = (String) request.getParameter("orderId");
		int orderId_request = Integer.parseInt(s_orderId_request);
		String s_taskId = request.getParameter("taskId");
		long taskId = Long.parseLong(s_taskId);
		String s_processId = request.getParameter("pinId");
		long processId = Long.parseLong(s_processId);
//		String design_cost_temp = request.getParameter("design_cost");
//		float design_cost=Float.parseFloat(design_cost_temp);
		String taskName = "entering_data ";
		
		
		  MultipartHttpServletRequest multipartRequest =(MultipartHttpServletRequest) request;
		
		  MultipartFile file = multipartRequest.getFile("CADFile");
		
		  
		  
//		  if (file == null) {
//              try {
//				throw new Exception("上传失败：文件为空");
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}    
//          }
//		  
//		  
//		  
//          if(file.getSize()>10000000)        
//          {
//              try {
//				throw new Exception("上传失败：文件大小不能超过10M");
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}            
//          }
//          
//          
          
		  String filename=file.getOriginalFilename();     
		
//		
//		    if(file.getSize()>0){                
//                try {
//                    SaveFileFromInputStream(file.getInputStream(),"D:/",filename);
//                } catch (IOException e) {
//                    System.out.println(e.getMessage());
//                    return null;
//                }
//            }
//		
//		    
//		    else{
//                try {
//					throw new Exception("上传失败：上传文件不能为空");
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//            }
		
//		    Calendar calendar = Calendar.getInstance();
		 // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd mm:ss");
	
		    
		  FileOperateUtil.Upload(file);
		   
		    String url="D:/"+"/"+ filename;
		    Timestamp uploadTime = new Timestamp(new Date().getTime());
		
		    designService.uploadCAD(account, orderId_request, taskId, processId, url,uploadTime);
		
		
//		designService.costAccounting(account, orderId_request, taskId, processId, design_cost);
		
		return "redirect:/design/costAccounting.do";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

	/**
	 * 设计部设计CAD验证跳转链接
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "design/designCAD_confirm.do", method= RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String designCAD_confirm(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {

		System.out.println(" design DesignCAD confirm ================ show task");
		List<OrderModel> orderList = new ArrayList<OrderModel>();
		Account account = (Account) request.getSession().getAttribute("cur_user");
//		String actorId = account.getUserRole();
		String actorId = "SHEJIZHUGUAN";
		System.out.println("actorId: " + actorId);
		String taskName = "design_ok";
		orderList = orderService.getOrderByActorIdAndTaskname(actorId, taskName);
		if (orderList.isEmpty()) {
			System.out.println("no orderList ");
		}
		model.addAttribute("order_list", orderList);
		
		
		return "design/designCAD_confirm";
	}
	
	
	/**
	 * 设计CAD验证 上传 详细页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "design/designCadConfirm_detail.do", method= RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String CadConfirm_detail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {

		System.out.println("design DesignCadConfirm detail ================ DesignCadConfirm detail");
		OrderModel orderModel = null;
		Account account = (Account) request.getSession().getAttribute("cur_user");
//		String actorId = account.getUserRole();
		String s_orderId_request = (String) request.getParameter("id");
		int orderId_request = Integer.parseInt(s_orderId_request);
		String s_taskId = request.getParameter("task_id");
		long taskId = Long.parseLong(s_taskId);
		String s_processId = request.getParameter("process_id");
		long processId = Long.parseLong(s_processId);
		orderModel = orderService.getOrderDetail(orderId_request, taskId, processId);
//		Logistics logistics = designService.getLogisticsByOrderId(orderId_request);
//		List<Fabric> fabricList = designService.getFabricByOrderId(orderId_request);
//		List<Accessory> accessoryList = designService.getAccessoryByOrderId(orderId_request);
		model.addAttribute("orderModel", orderModel);
	
		
		
		return "design/DesignCadConfirm_detail";
	}
	
	
	/**
	 * 
	 * 设计CAD设计部验证
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "design/doDesignCadConfirm.do", method= RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String doCadConfirm(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
	System.out.println("do  design designCadConfirm ================");
		
		Account account = (Account) request.getSession().getAttribute("cur_user");
	
		String s_orderId_request = (String) request.getParameter("orderId");
		int orderId_request = Integer.parseInt(s_orderId_request);
		String s_taskId = request.getParameter("taskId");
		long taskId = Long.parseLong(s_taskId);
		String s_processId = request.getParameter("pinId");
		long processId = Long.parseLong(s_processId);
//		String design_cost_temp = request.getParameter("design_cost");
//		float design_cost=Float.parseFloat(design_cost_temp);
		String taskName = "design_ok ";
		
		
		  MultipartHttpServletRequest multipartRequest =(MultipartHttpServletRequest) request;
		
		  MultipartFile file = multipartRequest.getFile("CADFile");
		
          
		  String filename=file.getOriginalFilename();     
		
	
		    
		  FileOperateUtil.Upload(file);
		   
		    String url="D:/"+"/"+ filename;
		    Timestamp uploadTime = new Timestamp(new Date().getTime());
		
		    designService.uploadCAD(account, orderId_request, taskId, processId, url,uploadTime);
		
		
//		designService.costAccounting(account, orderId_request, taskId, processId, design_cost);
		
		return "redirect:/design/designCAD_confirm";
	}
	
	
	
	
	
	
	
	
	

	/**
	 * 设计部生产版型CAD验证跳转链接
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "design/productCAD_confirm.do", method= RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String productCAD_confirm(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {

		System.out.println(" design productCAD confirm ================ show task");
		List<OrderModel> orderList = new ArrayList<OrderModel>();
		Account account = (Account) request.getSession().getAttribute("cur_user");
//		String actorId = account.getUserRole();
		String actorId = "SHEJIZHUGUAN";
		System.out.println("actorId: " + actorId);
		String taskName = "product_comfirm";
		orderList = orderService.getOrderByActorIdAndTaskname(actorId, taskName);
		if (orderList.isEmpty()) {
			System.out.println("no orderList ");
		}
		model.addAttribute("order_list", orderList);
		
		
		return "design/productCAD_confirm";
	}
	
	
	/**
	 * 生产版型CAD验证 上传 详细页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "design/ProductCadConfirm_detail.do", method= RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String ProductCadConfirm_detail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {

		System.out.println("design ProductCadConfirm detail ================ productCadConfirm detail");
		OrderModel orderModel = null;
		Account account = (Account) request.getSession().getAttribute("cur_user");
//		String actorId = account.getUserRole();
		String s_orderId_request = (String) request.getParameter("id");
		int orderId_request = Integer.parseInt(s_orderId_request);
		String s_taskId = request.getParameter("task_id");
		long taskId = Long.parseLong(s_taskId);
		String s_processId = request.getParameter("process_id");
		long processId = Long.parseLong(s_processId);
		orderModel = orderService.getOrderDetail(orderId_request, taskId, processId);
//		Logistics logistics = designService.getLogisticsByOrderId(orderId_request);
//		List<Fabric> fabricList = designService.getFabricByOrderId(orderId_request);
//		List<Accessory> accessoryList = designService.getAccessoryByOrderId(orderId_request);
		model.addAttribute("orderModel", orderModel);
	
		
		
		return "design/productCadConfirm_detail";
	}
	
	
	/**
	 * 
	 *生产版型 CAD设计部验证
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "design/doProductCadConfirm.do", method= RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String doProductCadConfirm(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
	System.out.println("do  design ProductCadConfirm ================");
		
		Account account = (Account) request.getSession().getAttribute("cur_user");
	
		String s_orderId_request = (String) request.getParameter("orderId");
		int orderId_request = Integer.parseInt(s_orderId_request);
		String s_taskId = request.getParameter("taskId");
		long taskId = Long.parseLong(s_taskId);
		String s_processId = request.getParameter("pinId");
		long processId = Long.parseLong(s_processId);
//		String design_cost_temp = request.getParameter("design_cost");
//		float design_cost=Float.parseFloat(design_cost_temp);
		String taskName = "design_ok ";
		
		
		  MultipartHttpServletRequest multipartRequest =(MultipartHttpServletRequest) request;
		
		  MultipartFile file = multipartRequest.getFile("CADFile");
		
          
		  String filename=file.getOriginalFilename();     
		
	
		    
		  FileOperateUtil.Upload(file);
		   
		    String url="D:/"+"/"+ filename;
		    Timestamp uploadTime = new Timestamp(new Date().getTime());
		
		    designService.uploadCAD(account, orderId_request, taskId, processId, url,uploadTime);
		
		
//		designService.costAccounting(account, orderId_request, taskId, processId, design_cost);
		
		return "redirect:/design/productCAD_confirm";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
