package nju.software.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import nju.software.dataobject.Accessory;
import nju.software.dataobject.Account;
import nju.software.dataobject.Customer;
import nju.software.dataobject.Fabric;
import nju.software.dataobject.Logistics;
import nju.software.dataobject.Order;
import nju.software.service.CustomerService;
import nju.software.service.OrderService;
import nju.software.util.DateUtil;
import nju.software.util.FileOperateUtil;
import nju.software.util.JbpmAPIUtil;
import nju.software.util.StringUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ntp.TimeStamp;
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
		
		
		//订单数据
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
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd mm:ss");
		//String sampleClothesPicture=sdf.format(calendar.getTime());
		//String referencePicture=sdf.format(calendar.getTime());
		//FileOperateUtil.Upload(request, "sample_clothes_picture", sdf.format(calendar.getTime()), "sample_clothes_picture");
		//FileOperateUtil.Upload(request, "reference_picture", sdf.format(calendar.getTime()), "reference_picture");
		Integer askAmount=Integer.parseInt(request.getParameter("ask_amount"));
		String askProducePeriod=request.getParameter("ask_produce_period");
		Timestamp askDeliverDate=getTime(request.getParameter("ask_deliver_date"));
		String askCodeNumber=request.getParameter("ask_code_number");
		Short hasPostedSampleClothes=Short.parseShort(request.getParameter("has_posted_sample_clothes"));
		Short isNeedSampleClothes=Short.parseShort(request.getParameter("is_need_sample_clothes"));
		String orderSource=request.getParameter("order_source");
		
		//面料数据
		String fabric_names=request.getParameter("fabric_name");
		String fabric_amounts=request.getParameter("fabric_amount");
		String fabric_name[]=fabric_names.split(",");
		String fabric_amount[]=fabric_amounts.split(",");
		List<Fabric>fabrics=new ArrayList<Fabric>();
		for(int i=0;i<fabric_name.length;i++){
			fabrics.add(new Fabric(0, fabric_name[i], fabric_amount[i]));
		}
		
		
	
		//辅料数据
		String accessory_names=request.getParameter("accessory_name");
		String accessory_querys=request.getParameter("accessory_query");
		String accessory_name[]=accessory_names.split(",");
		String accessory_query[]=accessory_querys.split(",");
		List<Accessory>accessorys=new ArrayList<Accessory>();
		for(int i=0;i<fabric_name.length;i++){
			accessorys.add(new Accessory(0, accessory_name[i], accessory_query[i]));
		}
		
		
		//物流数据
		Logistics logistics=new Logistics();
		String in_post_sample_clothes_time=request.getParameter("in_post_sample_clothes_time");
		String in_post_sample_clothes_type=request.getParameter("in_post_sample_clothes_type");
		String in_post_sample_clothes_number=request.getParameter("in_post_sample_clothes_number");
		
		logistics.setInPostSampleClothesTime(getTime(in_post_sample_clothes_time));
		logistics.setInPostSampleClothesType(in_post_sample_clothes_type);
		logistics.setInPostSampleClothesNumber(in_post_sample_clothes_number);
		
		
		String sample_clothes_time=request.getParameter("sample_clothes_time");
		String sample_clothes_type=request.getParameter("sample_clothes_type");
		String sample_clothes_number=request.getParameter("sample_clothes_number");
		String sample_clothes_name=request.getParameter("sample_clothes_name");
		String sample_clothes_phone=request.getParameter("sample_clothes_phone");
		String sample_clothes_address=request.getParameter("sample_clothes_address");
		String sample_clothes_remark=request.getParameter("sample_clothes_remark");
		
		logistics.setSampleClothesTime(getTime(sample_clothes_time));
		logistics.setSampleClothesType(sample_clothes_type);
		logistics.setSampleClothesNumber(sample_clothes_number);
		logistics.setSampleClothesName(sample_clothes_name);
		logistics.setSampleClothesPhone(sample_clothes_phone);
		logistics.setSampleClothesAddress(sample_clothes_address);
		logistics.setSampleClothesRemark(sample_clothes_remark);
		
		
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
		//order.setSampleClothesPicture(sampleClothesPicture);
		//order.setReferencePicture(referencePicture);
		order.setAskAmount(askAmount);
		order.setAskProducePeriod(askProducePeriod);
		order.setAskDeliverDate(askDeliverDate);
		order.setAskCodeNumber(askCodeNumber);
		order.setHasPostedSampleClothes(hasPostedSampleClothes);
		order.setIsNeedSampleClothes(isNeedSampleClothes);
		order.setOrderSource(orderSource);
		
		
		orderService.addOrder(order,fabrics,accessorys,logistics);

		
		return "redirect:/market/customerOrder.do";
	}

	
	public static Timestamp getTime(String time){
		Date outDate = DateUtil.parse(time, DateUtil.newFormat);
		return new Timestamp(outDate.getTime());
	}
}
