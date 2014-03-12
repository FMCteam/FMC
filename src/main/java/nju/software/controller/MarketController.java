package nju.software.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListResourceBundle;
import java.util.Map;

import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nju.software.dataobject.Account;
import nju.software.dataobject.Customer;
import nju.software.dataobject.Order;
import nju.software.service.CustomerService;
import nju.software.service.OrderService;
import nju.software.util.FileOperateUtil;
import nju.software.util.JbpmAPIUtil;
import nju.software.util.StringUtil;

import org.apache.commons.lang.StringUtils;
import org.drools.runtime.process.WorkflowProcessInstance;
import org.jbpm.task.query.TaskSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MarketController {
	@Autowired
	private OrderService orderService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private JbpmAPIUtil jbpmAPIUtil;

	
	//顾客下单的列表页面
	@RequestMapping(value = "market/customerOrder.do", method = RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String customerOrder(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Map params = new HashMap();
		params.put("page", 1);
		params.put("number_per_page", 100);
		List list = customerService.listCustomer(params);
		model.addAttribute("customer_list", list.get(0));
		return "market/customer_order";
	}

	
	//顾客下单的表单页面
	@RequestMapping(value = "market/add.do", method = RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String addEmployee(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String id=request.getParameter("cid");
		Customer customer=customerService.findByCustomerId(Integer.parseInt(id));
		model.addAttribute("customer",customer);
		return "market/add_order";
	}

	
	//提交表单的页面
	@RequestMapping(value = "market/addMarketOrder.do", method = RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String addMarketOrder(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		
		//表单数据
		Integer customerId=Integer.parseInt(request.getParameter("customerId"));
		Customer customer=customerService.findByCustomerId(customerId);
		Integer employeeId=6;
		String orderState="A";
		Timestamp orderTime=new Timestamp(new Date().getTime());
		String customerName=customer.getCustomerName();
		String customerCompany=customer.getCompanyName();
		String customerCompanyFax=customer.getCompanyFax();
		String customerPhone1=customer.getContactPhone1();
		String customerPhone2=customer.getContactPhone2();
		String customerCompanyAddress=customer.getCompanyAddress();
		String styleName=request.getParameter("style_name");
		String fabricType=request.getParameter("fabric_type");
		String styleSex=request.getParameter("style_sex");
		String styleSeason=request.getParameter("style_season");
		String specialProcess=StringUtils.join(request.getParameterValues("special_process"),"|");
		String otherRequirements=StringUtils.join(request.getParameterValues("other_requirements"),"|");
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd mm:ss");
		String sampleClothesPicture=sdf.format(calendar.getTime());
		String referencePicture=sdf.format(calendar.getTime());
		FileOperateUtil.Upload(request, "sample_clothes_picture", sdf.format(calendar.getTime()), "sample_clothes_picture");
		FileOperateUtil.Upload(request, "reference_picture", sdf.format(calendar.getTime()), "reference_picture");
		Integer askAmount=Integer.parseInt(request.getParameter("ask_amount"));
		String askProducePeriod=request.getParameter("ask_produce_period");
		Timestamp askDeliverDate=Timestamp.valueOf(request.getParameter("ask_deliver_date"));
		String askCodeNumber=request.getParameter("ask_code_number");
		Short hasPostedSampleClothes=Short.parseShort(request.getParameter("has_posted_sample_clothes"));
		Short isNeedSampleClothes=Short.parseShort(request.getParameter("is_need_sample_clothes"));
		String orderSource=request.getParameter("order_source");
		
		
		//Order 
		Order order = new Order();
		order.setEmployeeId(employeeId);
		order.setCustomerId(customerId);
		order.setOrderState(orderState);
		order.setOrderTime(orderTime);
		order.setCustomerName(customerName);
		order.setCustomerCompany(customerCompany);
		order.setCustomerCompanyFax(customerCompanyFax);
		order.setCustomerPhone1(customerPhone1);
		order.setCustomerPhone2(customerPhone2);
		order.setCustomerCompanyAddress(customerCompanyAddress);
		order.setStyleName(styleName);
		order.setFabricType(fabricType);
		order.setStyleSex(styleSex);
		order.setStyleSeason(styleSeason);
		order.setSpecialProcess(specialProcess);
		order.setOtherRequirements(otherRequirements);
		order.setSampleClothesPicture(sampleClothesPicture);
		order.setReferencePicture(referencePicture);
		order.setAskAmount(askAmount);
		order.setAskProducePeriod(askProducePeriod);
		order.setAskDeliverDate(askDeliverDate);
		order.setAskCodeNumber(askCodeNumber);
		order.setHasPostedSampleClothes(hasPostedSampleClothes);
		order.setIsNeedSampleClothes(isNeedSampleClothes);
		order.setOrderSource(orderSource);
		
		
		
		orderService.addOrder(order);
		return "redirect:/market/customerOrder.do";
	}

}
