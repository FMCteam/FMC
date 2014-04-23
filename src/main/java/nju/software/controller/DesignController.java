package nju.software.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

	// ===========================设计验证=================================
	@RequestMapping(value = "/design/verifyDesignList.do")
	@Transactional(rollbackFor = Exception.class)
	public String verifyDesignList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<Map<String, Object>> orderList = designService
				.getVerifyDesignList();
		model.addAttribute("list", orderList);
		model.addAttribute("taskName", "设计验证");
		model.addAttribute("url", "/design/verifyDesignDetail.do");
		return "/design/verifyDesignList";
	}

	@RequestMapping(value = "/design/verifyDesignDetail.do")
	@Transactional(rollbackFor = Exception.class)
	public String verifyDesignDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Integer orderId = Integer.parseInt(request.getParameter("orderId"));
		Map<String, Object> orderInfo = designService
				.getVerifyDesignDetail(orderId);
		model.addAttribute("orderInfo", orderInfo);
		return "/design/verifyDesignDetail";
	}

	@RequestMapping(value = "/design/verifyDesignSubmit.do")
	@Transactional(rollbackFor = Exception.class)
	public String verifyDesignSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		long taskId = Long.parseLong(request.getParameter("taskId"));
		boolean result = Boolean
				.parseBoolean(request.getParameter("designVal"));
		String comment = request.getParameter("suggestion");
		designService.verifyDesignSubmit(taskId, result, comment);
		return "forward:/design/verifyDesignList.do";
	}

	// ===========================上传版型=================================
	@RequestMapping(value = "/design/getUploadDesignList.do")
	@Transactional(rollbackFor = Exception.class)
	public String getUploadDesignList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<Map<String, Object>> list = designService.getUploadDesignList();
		model.addAttribute("list", list);
		model.addAttribute("taskName", "录入版型数据");
		model.addAttribute("url", "/design/getUploadDesignDetail.do");
		return "/design/getUploadDesignList";
	}

	@RequestMapping(value = "/design/getUploadDesignDetail.do")
	@Transactional(rollbackFor = Exception.class)
	public String getUploadDesignDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = request.getParameter("orderId");
		Map<String, Object> orderInfo = designService
				.getUploadDesignDetail(Integer.parseInt(orderId));
		model.addAttribute("orderInfo", orderInfo);
		return "/design/getUploadDesignDetail";
	}

	@RequestMapping(value = "/design/uploadDesignSubmit.do", method = RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String uploadDesignSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = request.getParameter("orderId");
		String taskId = request.getParameter("taskId");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("CADFile");
		String filename = file.getOriginalFilename();
		FileOperateUtil.Upload(file);
		String url = "D:/" + "/" + filename;
		Timestamp uploadTime = new Timestamp(new Date().getTime());
		designService.uploadDesignSubmit(Integer.parseInt(orderId),
				Long.parseLong(taskId), url, uploadTime);
		return "forward:/design/getUploadDesignList.do";
	}

	// ===========================修改版型=================================
	@RequestMapping(value = "/design/getModifyDesignList.do")
	@Transactional(rollbackFor = Exception.class)
	public String getModifyDesignList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<Map<String, Object>> list = designService.getModifyDesignList();
		model.addAttribute("list", list);
		model.addAttribute("taskName", "设计部确认");
		model.addAttribute("url", "/design/getModifyDesignDetail.do");
		return "/design/getModifyDesignList";
	}

	@RequestMapping(value = "/design/getModifyDesignDetail.do")
	@Transactional(rollbackFor = Exception.class)
	public String getModifyDesignDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = request.getParameter("orderId");
		Map<String, Object> orderInfo = designService
				.getModifyDesignDetail(Integer.parseInt(orderId));
		model.addAttribute("orderInfo", orderInfo);
		return "/design/getModifyDesignDetail";
	}

	@RequestMapping(value = "/design/modifyDesignSubmit.do")
	@Transactional(rollbackFor = Exception.class)
	public String modifyDesignSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = (String) request.getParameter("orderId");
		String taskId = request.getParameter("taskId");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("CADFile");
		String filename = file.getOriginalFilename();
		FileOperateUtil.Upload(file);
		String url = "D:/" + "/" + filename;
		Timestamp uploadTime = new Timestamp(new Date().getTime());
		designService.modifyDesignSubmit(Integer.parseInt(orderId),
				Long.parseLong(taskId), url, uploadTime);
		return "forward:/design/getModifyDesignList.do";
	}

	
	// ===========================确认版型=================================
	@RequestMapping(value = "/design/getConfirmDesignList.do")
	@Transactional(rollbackFor = Exception.class)
	public String getConfirmDesignList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<Map<String, Object>> list = designService.getConfirmDesignList();
		model.addAttribute("list", list);
		model.addAttribute("taskName", "设计生产确认");
		model.addAttribute("url", "/design/getConfirmDesignDetail.do");
		return "/design/getConfirmDesignList";
	}
	
	@RequestMapping(value = "/design/getConfirmDesignDetail.do")
	@Transactional(rollbackFor = Exception.class)
	public String getConfirmDesignDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = request.getParameter("orderId");
		Map<String, Object> orderInfo = designService
				.getConfirmDesignDetail(Integer.parseInt(orderId));
		model.addAttribute("orderInfo", orderInfo);
		return "/design/getConfirmDesignDetail";
	}

	@RequestMapping(value = "/design/confirmDesignSubmit.do")
	@Transactional(rollbackFor = Exception.class)
	public String confirmDesignSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = request.getParameter("orderId");
		String taskId = request.getParameter("taskId");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("CADFile");
		String filename = file.getOriginalFilename();
		FileOperateUtil.Upload(file);
		String url = "D:/" + "/" + filename;
		Timestamp uploadTime = new Timestamp(new Date().getTime());
		designService.uploadDesignSubmit(Integer.parseInt(orderId),
				Long.parseLong(taskId), url, uploadTime);
		return "forward:/design/getConfirmDesignList.do";
	}

	
	@RequestMapping(value = "design/downloadCadSubmit.do", method = RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String downloadCadSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String url = request.getParameter("cadUrl");
		FileOperateUtil.Download(response, url);
		return null;
	}

	@Autowired
	private JbpmAPIUtil jbpmAPIUtil;
	@Autowired
	private OrderService orderService;
	@Autowired
	private DesignService designService;
	@Autowired
	private JbpmTest jbpmTest;
}
