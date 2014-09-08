package nju.software.controller;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nju.software.dataobject.Order;
import nju.software.service.OrderService;

import javax.servlet.http.HttpSession;

import nju.software.dataobject.Accessory;
import nju.software.dataobject.AccessoryCost;
import nju.software.dataobject.Account;
import nju.software.dataobject.Craft;
import nju.software.dataobject.DesignCad;
import nju.software.dataobject.Employee;
import nju.software.dataobject.Fabric;
import nju.software.dataobject.FabricCost;
import nju.software.dataobject.Logistics;
import nju.software.dataobject.Order;
import nju.software.dataobject.Produce;
import nju.software.dataobject.Quote;
import nju.software.dataobject.SearchInfo;
import nju.software.dataobject.VersionData;
import nju.software.service.CraftService;
import nju.software.service.CustomerService;
import nju.software.service.DesignCadService;
import nju.software.service.EmployeeService;
import nju.software.service.LogisticsService;
import nju.software.service.MarketService;
import nju.software.service.OrderService;
import nju.software.service.QuoteService;
import nju.software.service.impl.BuyServiceImpl;
import nju.software.service.impl.DesignServiceImpl;
import nju.software.service.impl.MarketServiceImpl;
import nju.software.service.impl.ProduceServiceImpl;
import nju.software.service.impl.ServiceUtil;
import nju.software.util.DateUtil;
import nju.software.util.FileOperateUtil;
import nju.software.util.mail.MailSenderInfo;
import nju.software.util.mail.SimpleMailSender;

import org.apache.commons.lang.StringUtils;
import org.jbpm.task.query.TaskSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


	

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
public class OrderController {

	@Autowired
	private OrderService orderService;
	@Autowired
	private DesignCadService cadService;
	@Autowired
	private CraftService craftService;
	@Autowired
	private LogisticsService logisticsService;
	@Autowired
	private QuoteService quoteService;	
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private MarketService marketService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private ServiceUtil service;
	
	public static Timestamp getTime(String time) {
		if(time.equals("")) return null;
		Date outDate = DateUtil.parse(time, DateUtil.newFormat);
		return new Timestamp(outDate.getTime());
	}
	
	@RequestMapping(value = "account/modifyOrderList.do", method = RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String getOrderList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		HttpSession session = request.getSession();
		Account account = (Account) session.getAttribute("cur_user");
		String result = request.getParameter("result");
		if(result != null){
			request.setAttribute("notify", "该订单已签订过大货合同，无法进行修改！");
		}
		List<Map<String, Object>> orderModelList = orderService.getModifyOrderList();
		model.put("list", orderModelList);
		model.addAttribute("taskName", "修改订单");
		model.addAttribute("url", "/account/modifyOrderDetail.do");
		model.addAttribute("searchurl", "/account/modifyOrderSearch.do");

		return "account/modifyOrderList_new";
	}
	
	@RequestMapping(value = "account/modifyOrderSearch.do")
	@Transactional(rollbackFor = Exception.class)
	public String modifyOrderSearch(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String ordernumber = request.getParameter("ordernumber");
		String customername = request.getParameter("customername");
		String stylename = request.getParameter("stylename");
		String employeename = request.getParameter("employeename");
		String startdate = request.getParameter("startdate");
		String enddate = request.getParameter("enddate");
		//System.out.println("ordernumber:"+ordernumber+"---------\n"+"customername:"+customername+"---------\n"+"stylename:"+stylename+"---------\n"+"employeename:"+employeename+"---------\n"+"startdate:"+startdate+"---------\n"+"enddate:"+enddate+"---------\n");
		List<Employee> employees = employeeService.getEmployeeByName(employeename);
		Integer[] employeeIds = new Integer[employees.size()];
		for(int i=0;i<employeeIds.length;i++){
			employeeIds[i] = employees.get(i).getEmployeeId();
		}
		List<Map<String, Object>> list = orderService.getSearchOrderList(ordernumber,customername,stylename,startdate,enddate,employeeIds,"",0);
         
		String string_page=request.getParameter("page")==null?"1":request.getParameter("page");
		Integer page=Integer.parseInt(string_page);

//		HttpSession session = request.getSession();
//		Account account = (Account) session.getAttribute("cur_user");
 		model.put("list", list);
		model.addAttribute("taskName", "修改订单查找");
		model.addAttribute("url", "/account/modifyOrderDetail.do");
		model.addAttribute("searchurl", "/account/modifyOrderSearch.do");

		model.addAttribute("info", new SearchInfo(ordernumber, customername, stylename, employeename, startdate, enddate));//将查询条件传回页面  hcj
		model.addAttribute("page", page);
		
		if(list!=null&&list.size()!=0){
			model.addAttribute("pages", list.get(0).get("pages"));
		}
		//System.out.println("===========ok:"+list.size());
		return "account/modifyOrderList_new";
	}	
	
	@RequestMapping(value = "account/modifyOrderDetail.do")
	@Transactional(rollbackFor = Exception.class)
	public String modifyOrderDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {

		String s_id = request.getParameter("orderId");
		int id = Integer.parseInt(s_id);
		// 修改
		HttpSession session = request.getSession();
		Account account = (Account) session.getAttribute("cur_user");
		Map<String, Object> orderModel = orderService.getModifyOrderDetail(account.getUserId(), id);
		if(orderModel == null){
			//若该订单已签订过大货合同，则返回可修改订单列表
			return "redirect:/account/modifyOrderList.do?result=0";
		}
		model.addAttribute("orderModel", orderModel);
		return "account/modifyOrderDetail";
	}

	// 询单的修改界面
		@RequestMapping(value = "account/modifyOrderSubmit.do", method = RequestMethod.POST)
		@Transactional(rollbackFor = Exception.class)
		public String modifyOrderSubmit(HttpServletRequest request,
				HttpServletResponse response, ModelMap model) {

			HttpSession session = request.getSession();
			Account account = (Account) session.getAttribute("cur_user");
			if(!account.getUserName().equals("admin")){
				return "redirect:/account/modifyOrderList.do";
			}
			String s_id = request.getParameter("id");
			//String s_task_id = request.getParameter("task_id");
			int id = Integer.parseInt(s_id);
			//long task_id = Long.parseLong(s_task_id);
			// 保存修改该的order数据，accessory，fabric，logistics
			// 订单数据

			String orderState = "A";
			String styleName = request.getParameter("style_name");
			String fabricType = request.getParameter("fabric_type");
			String clothesType = request.getParameter("clothes_type");
			String styleSex = request.getParameter("style_sex");
			String styleSeason = request.getParameter("style_season");
			String specialProcess = StringUtils.join(
					request.getParameterValues("special_process"), "|");
			String otherRequirements = StringUtils.join(
					request.getParameterValues("other_requirements"), "|");
			String referenceUrl = request.getParameter("reference_url");
			Calendar calendar = Calendar.getInstance();

			Integer askAmount = Integer
					.parseInt(request.getParameter("ask_amount"));
			String askProducePeriod = request.getParameter("ask_produce_period");
			Timestamp askDeliverDate = getTime(request
					.getParameter("ask_deliver_date"));
			//String askCodeNumber = request.getParameter("ask_code_number");
			Short hasPostedSampleClothes = Short.parseShort(request
					.getParameter("has_posted_sample_clothes"));
			Short isNeedSampleClothes = Short.parseShort(request
					.getParameter("is_need_sample_clothes"));
			String orderSource = request.getParameter("order_source");

			// 面料数据
			String fabric_names = request.getParameter("fabric_name");
			String fabric_amounts = request.getParameter("fabric_amount");
			String fabric_name[] = fabric_names.split(",");
			String fabric_amount[] = fabric_amounts.split(",");
			List<Fabric> fabrics = new ArrayList<Fabric>();
			for (int i = 0; i < fabric_name.length; i++) {
				fabrics.add(new Fabric(0, fabric_name[i], fabric_amount[i]));
			}

			//面料价格数据
			
 			String fabric_cost_name[] = request.getParameterValues("fabric_cost_name");
 			System.out.println("length: "+fabric_cost_name.length);
 			
 			for(int k=0;k<fabric_cost_name.length;k++){
 				System.out.println("lll"+fabric_cost_name[k]);
 			}
 			String cost_per_meter[] = request.getParameterValues("cost_per_meter");
 			System.out.println("length: "+cost_per_meter.length);

 			for(int k=0;k<cost_per_meter.length;k++){
 				System.out.println("lll"+cost_per_meter[k]);
 			}
 			String tear_per_meter[] = request.getParameterValues("tearpermeter");
 			System.out.println("length: "+tear_per_meter.length);
 			for(int k=0;k<tear_per_meter.length;k++){
 				System.out.println("lll"+tear_per_meter[k]);
 			}
 			System.out.println("hhhh");
 			System.out.println(fabric_cost_name.toString() );
 			System.out.println(cost_per_meter.toString());
 			List<FabricCost> fabricCosts = new ArrayList<FabricCost>();
			for(int i=1;i<fabric_cost_name.length;i++){
				System.out.println(fabric_cost_name[i]+cost_per_meter[i]+"kkkkkkkk");
				FabricCost fc = new FabricCost();
				fc.setFabricName(fabric_cost_name[i]);
				fc.setCostPerMeter(Float.parseFloat(cost_per_meter[i]));
				fc.setTearPerMeter(Float.parseFloat(tear_per_meter[i]));				
				fabricCosts.add(fc);
			}
			// 辅料数据
			String accessory_names = request.getParameter("accessory_name");
			String accessory_querys = request.getParameter("accessory_query");
			String accessory_name[] = accessory_names.split(",");
			String accessory_query[] = accessory_querys.split(",");
			List<Accessory> accessorys = new ArrayList<Accessory>();
			for (int i = 0; i < accessory_name.length; i++) {
				accessorys.add(new Accessory(0, accessory_name[i],
						accessory_query[i]));
			}
            //辅料价格数据
			String accessory_cost_name[] = request.getParameterValues("accessory_cost_name");
			String cost_per_piece[] = request.getParameterValues("costperpiece");
			String tear_per_piece[] = request.getParameterValues("tearperpiece");	
//			System.out.println("whhlength1"+tear_per_piece.length);
//			System.out.println("whhlength2"+cost_per_piece.length);
//			System.out.println("whhlength3"+accessory_cost_name.length);

			List<AccessoryCost> accessoryCosts = new ArrayList<AccessoryCost>();
			for(int i = 1;i<accessory_cost_name.length;i++){
				AccessoryCost ac = new AccessoryCost();
				ac.setAccessoryName(accessory_cost_name[i]);
				ac.setCostPerPiece(Float.parseFloat(cost_per_piece[i]));
				ac.setTearPerPiece(Float.parseFloat(tear_per_piece[i-1]));
				accessoryCosts.add(ac);
 			}
			// 大货加工要求
			String produce_colors = request.getParameter("produce_color");
			String produce_xss = request.getParameter("produce_xs");
			String produce_ss = request.getParameter("produce_s");
			String produce_ms = request.getParameter("produce_m");
			String produce_ls = request.getParameter("produce_l");
			String produce_xls = request.getParameter("produce_xl");
			String produce_xxls = request.getParameter("produce_xxl");
			String produce_color[] = produce_colors.split(",");
			String produce_xs[] = produce_xss.split(",");
			String produce_s[] = produce_ss.split(",");
			String produce_m[] = produce_ms.split(",");
			String produce_l[] = produce_ls.split(",");
			String produce_xl[] = produce_xls.split(",");
			String produce_xxl[] = produce_xxls.split(",");
			List<Produce> produces = new ArrayList<Produce>();
			for (int i = 0; i < produce_color.length; i++) {
				if (produce_color[i].equals(""))
					continue;
				Produce p = new Produce();
				p.setColor(produce_color[i]);
				p.setOid(0);
				int l = Integer.parseInt(produce_l[i]);
				int m = Integer.parseInt(produce_m[i]);
				int s = Integer.parseInt(produce_s[i]);
				int xs = Integer.parseInt(produce_xs[i]);
				int xl = Integer.parseInt(produce_xl[i]);
				int xxl = Integer.parseInt(produce_xxl[i]);
				p.setL(l);
				p.setM(m);
				p.setS(s);
				p.setXl(xl);
				p.setXs(xs);
				p.setXxl(xxl);
				p.setProduceAmount(l + m + s + xs + xl + xxl);
				p.setType(Produce.TYPE_PRODUCE);
				produces.add(p);
			}

			// 样衣加工要求
			String sample_produce_colors = request
					.getParameter("sample_produce_color");
			String sample_produce_xss = request.getParameter("sample_produce_xs");
			String sample_produce_ss = request.getParameter("sample_produce_s");
			String sample_produce_ms = request.getParameter("sample_produce_m");
			String sample_produce_ls = request.getParameter("sample_produce_l");
			String sample_produce_xls = request.getParameter("sample_produce_xl");
			String sample_produce_xxls = request.getParameter("sample_produce_xxl");
			String sample_produce_color[] = sample_produce_colors.split(",");
			String sample_produce_xs[] = sample_produce_xss.split(",");
			String sample_produce_s[] = sample_produce_ss.split(",");
			String sample_produce_m[] = sample_produce_ms.split(",");
			String sample_produce_l[] = sample_produce_ls.split(",");
			String sample_produce_xl[] = sample_produce_xls.split(",");
			String sample_produce_xxl[] = sample_produce_xxls.split(",");
			List<Produce> sample_produces = new ArrayList<Produce>();
			int sample_amount = 0;
			for (int i = 0; i < sample_produce_color.length; i++) {
				if (sample_produce_color[i].equals(""))
					continue;
				Produce p = new Produce();
				p.setColor(sample_produce_color[i]);
				p.setOid(0);
				int l = Integer.parseInt(sample_produce_l[i]);
				int m = Integer.parseInt(sample_produce_m[i]);
				int s = Integer.parseInt(sample_produce_s[i]);
				int xs = Integer.parseInt(sample_produce_xs[i]);
				int xl = Integer.parseInt(sample_produce_xl[i]);
				int xxl = Integer.parseInt(sample_produce_xxl[i]);
				p.setL(l);
				p.setM(m);
				p.setS(s);
				p.setXl(xl);
				p.setXs(xs);
				p.setXxl(xxl);
				p.setType(Produce.TYPE_SAMPLE_PRODUCE);
				int temp = l + m + s + xs + xl + xxl;
				p.setProduceAmount(temp);
				sample_amount+=temp;
				sample_produces.add(p);
			}

			// 版型数据
			String version_sizes = request.getParameter("version_size");
			String version_centerBackLengths = request
					.getParameter("version_centerBackLength");
			String version_busts = request.getParameter("version_bust");
			String version_waistLines = request.getParameter("version_waistLine");
			String version_shoulders = request.getParameter("version_shoulder");
			String version_buttocks = request.getParameter("version_buttock");
			String version_hems = request.getParameter("version_hem");
			String version_trouserss = request.getParameter("version_trousers");
			String version_skirts = request.getParameter("version_skirt");
			String version_sleevess = request.getParameter("version_sleeves");
			String version_size[] = version_sizes.split(",");
			String version_centerBackLength[] = version_centerBackLengths
					.split(",");
			String version_bust[] = version_busts.split(",");
			String version_waistLine[] = version_waistLines.split(",");
			String version_shoulder[] = version_shoulders.split(",");
			String version_buttock[] = version_buttocks.split(",");
			String version_hem[] = version_hems.split(",");
			String version_trousers[] = version_trouserss.split(",");
			String version_skirt[] = version_skirts.split(",");
			String version_sleeves[] = version_sleevess.split(",");
			List<VersionData> versions = new ArrayList<VersionData>();
			for (int i = 0; i < version_size.length; i++) {
				if (version_size[i].equals(""))
					continue;
				versions.add(new VersionData(0, version_size[i],
						version_centerBackLength[i], version_bust[i],
						version_waistLine[i], version_shoulder[i],
						version_buttock[i], version_hem[i], version_trousers[i],
						version_skirt[i], version_sleeves[i]));
			}

			// 物流数据
			Logistics logistics = logisticsService.findByOrderId(s_id);
			if (hasPostedSampleClothes == 1) {
				String in_post_sample_clothes_time = request
						.getParameter("in_post_sample_clothes_time");
				String in_post_sample_clothes_type = request
						.getParameter("in_post_sample_clothes_type");
				String in_post_sample_clothes_number = request
						.getParameter("in_post_sample_clothes_number");

				logistics
						.setInPostSampleClothesTime(getTime(in_post_sample_clothes_time));
				logistics.setInPostSampleClothesType(in_post_sample_clothes_type);
				logistics
						.setInPostSampleClothesNumber(in_post_sample_clothes_number);
			}else{
				logistics.setInPostSampleClothesTime(null);
				logistics.setInPostSampleClothesType(null);
				logistics.setInPostSampleClothesNumber(null);
			}
			//if (isNeedSampleClothes == 1) {
//				String sample_clothes_time = request
//						.getParameter("sample_clothes_time");
//				String sample_clothes_type = request
//						.getParameter("sample_clothes_type");
//				String sample_clothes_number = request
//						.getParameter("sample_clothes_number");
				String sample_clothes_name = request
						.getParameter("sample_clothes_name");
				String sample_clothes_phone = request
						.getParameter("sample_clothes_phone");
				String sample_clothes_address = request
						.getParameter("sample_clothes_address");
				String sample_clothes_remark = request
						.getParameter("sample_clothes_remark");

//				logistics.setSampleClothesTime(getTime(sample_clothes_time));
//				logistics.setSampleClothesType(sample_clothes_type);
//				logistics.setSampleClothesNumber(sample_clothes_number);
				logistics.setSampleClothesName(sample_clothes_name);
				logistics.setSampleClothesPhone(sample_clothes_phone);
				logistics.setSampleClothesAddress(sample_clothes_address);
				logistics.setSampleClothesRemark(sample_clothes_remark);
			//}
			
			// CAD
			DesignCad cad = cadService.findByOrderId(s_id);
			//cad.setCadVersion((short) 1);
			String cad_fabric = request.getParameter("cadFabric");
			String cad_box = request.getParameter("cadBox");
			String cad_package = request.getParameter("cadPackage");
			String cad_version_data = request.getParameter("cadVersionData");
			String cad_tech = request.getParameter("cadTech");
			String cad_other = request.getParameter("cadOther");
			cad.setCadBox(cad_box);
			cad.setCadFabric(cad_fabric);
			cad.setCadOther(cad_other);
			cad.setCadPackage(cad_package);
			cad.setCadTech(cad_tech);
			cad.setCadVersionData(cad_version_data);

			//Craft
			Craft craft = craftService.findCraftByOrderId(s_id);
			String stampDutyMoney = request.getParameter("stampDutyMoney");
			String washHangDyeMoney = request.getParameter("washHangDyeMoney");
			String laserMoney = request.getParameter("laserMoney");
			String embroideryMoney = request.getParameter("embroideryMoney");
			String crumpleMoney = request.getParameter("crumpleMoney");
			String openVersionMoney = request.getParameter("openVersionMoney");
			craft.setStampDutyMoney(Float.parseFloat(stampDutyMoney));
			craft.setWashHangDyeMoney(Float.parseFloat(washHangDyeMoney));
			craft.setLaserMoney(Float.parseFloat(laserMoney));
			craft.setEmbroideryMoney(Float.parseFloat(embroideryMoney));
            craft.setCrumpleMoney(Float.parseFloat(crumpleMoney));
            craft.setOpenVersionMoney(Float.parseFloat(openVersionMoney));
            
			// Order
			Order order = orderService.findByOrderId(s_id);
			// order.setEmployeeId(employeeId);
			// order.setCustomerId(customerId);
			order.setOrderState(orderState);
			// order.setCustomerName(customerName);
			// order.setCustomerCompany(customerCompany);
			// order.setCustomerCompanyFax(customerCompanyFax);
			// order.setCustomerPhone1(customerPhone1);
			// order.setCustomerPhone2(customerPhone2);
			// order.setCustomerCompanyAddress(customerCompanyAddress);
			order.setStyleName(styleName);
			order.setFabricType(fabricType);
			order.setClothesType(clothesType);
			order.setStyleSex(styleSex);
			order.setStyleSeason(styleSeason);
			order.setSpecialProcess(specialProcess);
			order.setOtherRequirements(otherRequirements);
			order.setReferenceUrl(referenceUrl);
			order.setAskAmount(askAmount);
			order.setSampleAmount(sample_amount);
			order.setAskProducePeriod(askProducePeriod);
			order.setAskDeliverDate(askDeliverDate);
			//order.setAskCodeNumber(askCodeNumber);
			order.setHasPostedSampleClothes(hasPostedSampleClothes);
			order.setIsNeedSampleClothes(isNeedSampleClothes);
			order.setOrderSource(orderSource);
			
			//保存Quote
			Quote quote = quoteService.findByOrderId(s_id);

			String cut_cost = request.getParameter("cut_cost");
			String manage_cost = request.getParameter("manage_cost");
			String swing_cost = request.getParameter("swing_cost");
			String ironing_cost = request.getParameter("ironing_cost");
			String nail_cost = request.getParameter("nail_cost");
			String package_cost = request.getParameter("package_cost");
			String other_cost = request.getParameter("other_cost");
			String design_cost = request.getParameter("design_cost");
			
			quote.setCutCost(Float.parseFloat(cut_cost));
			quote.setManageCost(Float.parseFloat(manage_cost));
			quote.setSwingCost(Float.parseFloat(swing_cost));
			quote.setIroningCost(Float.parseFloat(ironing_cost));
			quote.setNailCost(Float.parseFloat(nail_cost));
			quote.setPackageCost(Float.parseFloat(package_cost));
			quote.setOtherCost(Float.parseFloat(other_cost));
			quote.setDesignCost(Float.parseFloat(design_cost));
			
			
			
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			if (!multipartRequest.getFile("sample_clothes_picture").isEmpty()) {
				File file = new File(order.getSampleClothesPicture());
				if(file.exists()){
					file.delete();
				}
				MultipartFile mfile = multipartRequest.getFile("sample_clothes_picture");
				String filename = mfile.getOriginalFilename();
				String url = MarketServiceImpl.UPLOAD_DIR_SAMPLE + order.getOrderId();
				String fileid = "sample_clothes_picture";
				FileOperateUtil.Upload(request, url, null, fileid);
				url = url + "/" + filename;
				order.setSampleClothesPicture(url);
			}
			if (!multipartRequest.getFile("reference_picture").isEmpty()) {
				File file = new File(order.getReferencePicture());
				if(file.exists()){
					file.delete();
				}
				MultipartFile mfile = multipartRequest.getFile("reference_picture");
				String filename = mfile.getOriginalFilename();
				String url = MarketServiceImpl.UPLOAD_DIR_REFERENCE + order.getOrderId();
				String fileid = "reference_picture";
				FileOperateUtil.Upload(request, url, null, fileid);
				url = url + "/" + filename;
				order.setReferencePicture(url);
			}

//			boolean editok = request.getParameter("editok").equals("true") ? true
//					: false;
			orderService.modifyOrderSubmit(order, fabrics, accessorys, logistics,
					produces, sample_produces, versions, cad, account.getUserId(),fabricCosts,accessoryCosts,quote,craft);
			return "redirect:/account/modifyOrderList.do";
		}

		// =======================被终止订单列表=================================
		@RequestMapping(value = "/order/endList.do")
		@Transactional(rollbackFor = Exception.class)
		public String endList(HttpServletRequest request,
				HttpServletResponse response, ModelMap model) {
			Account account = (Account) request.getSession().getAttribute("cur_user");		
			List<Map<String, Object>> list = null;
			
			//客户和市场专员只能看到与自己相关的被终止的订单
			if("CUSTOMER".equals(account.getUserRole()) || "marketStaff".equals(account.getUserRole())){
				list = orderService.getOrdersEnd(account.getUserRole(), account.getUserId());
			} else {
				list = orderService.findByProperty("orderState", "1");
			}
			
			model.put("list", list);
			model.addAttribute("taskName", "被终止订单列表");
			model.addAttribute("url", "/order/orderDetail.do");
			return "/order/endList_new";
		}
		// =======================被终止订单列表搜索=================================
		@RequestMapping(value = "/order/endListSearch.do")
		@Transactional(rollbackFor = Exception.class)
		public String endListSearch(HttpServletRequest request,
				HttpServletResponse response, ModelMap model) {
			Account account = (Account) request.getSession().getAttribute("cur_user");	
			
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
			List<Map<String, Object>> list = marketService.getSearchOrderList(ordernumber,customername,stylename,startdate,enddate,employeeIds,account.getUserRole(),account.getUserId());
			List<Map<String,Object>> resultlist =  new ArrayList<>();
			for(int i =0;i<list.size();i++){
				Map<String, Object> model1  = list.get(i);
				Order order = (Order) model1.get("order");
				if(order.getOrderState().equals("1")){
					resultlist.add(model1);
				}
			}
 
//			List<Map<String,Object>>list=orderService.findByProperty("orderState","1");
			model.put("list", resultlist);
			model.addAttribute("taskName", "被终止订单列表");
			model.addAttribute("url", "/order/orderDetail.do");
			model.addAttribute("info", new SearchInfo(ordernumber, customername, stylename, employeename, startdate, enddate));//将查询条件传回页面  hcj
			return "/order/endList_new";
		}		
		
		
		// ===========================结束订单=================================
		@RequestMapping(value = "/order/end.do")
		@Transactional(rollbackFor = Exception.class)
		public String end(HttpServletRequest request,
				HttpServletResponse response, ModelMap model) {
			Integer orderId=Integer.parseInt(request.getParameter("orderId"));
			orderService.endOrder(orderId);
			return "forward:/order/endList.do";
		}
		
		// ==========================推送订单信息===============================
		@RequestMapping(value = "/order/pushOrderInfo.do")
		@Transactional(rollbackFor = Exception.class)
		public String pushOrderInfo(HttpServletRequest request,
				HttpServletResponse response, ModelMap model){
			Integer orderId = Integer.parseInt(request.getParameter("orderId"));
			ArrayList<String> stateNames = marketService.getProcessStateName(orderId);
			String orderStateName = null; //订单当前状态
			if(stateNames != null && stateNames.size() > 0){
				orderStateName = stateNames.get(0);
			}
			
			Order order = orderService.getOrderById(orderId);
			
			Account account = (Account) request.getSession().getAttribute("cur_user");
			Map<String, Object> orderInfo = marketService.getOrderDetail(orderId);
			model.addAttribute("role", account.getUserRole());
			model.addAttribute("orderInfo", orderInfo);
			
			String emailAddr = customerService.findByCustomerId(order.getCustomerId()).getEmail();
			if(StringUtils.isEmpty(emailAddr)){
				model.addAttribute("notify", "客户未填写邮箱，推动订单信息失败！");
				return "/market/orderDetail";
			}
					
			//编辑推送邮件的标题和内容
			String emailTitle = "智造链 - ";
	        String emailContent = "尊敬的客户：<br/>"
	        					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您好！您编号为" + service.getOrderId(order) + "的订单当前流水状态为：" + orderStateName + "，";		
			if(orderStateName.equals("收取样衣")){
				emailTitle += "请尽快提供样衣";
				emailContent += "请尽快提供样衣。";
			}else if(orderStateName.equals("确认样衣制作金")){
				emailTitle += "请尽快支付样衣制作金";
				emailContent += "请尽快支付样衣制作金。";
			}else if(orderStateName.equals("签订合同")){
				emailTitle += "请尽快签订合同";
				emailContent += "请尽快签订合同。";
			}else if(orderStateName.equals("30%定金确认")){
				emailTitle += "请尽快交清首付款";
				emailContent += "请尽快交清首付款。";
			}else if(orderStateName.equals("70%金额确认")){
				emailTitle += "请尽快支付尾款";
				emailContent += "请尽快支付尾款。";
			}else{
				emailTitle += "订单流水状态";
				emailContent += "请耐心等待。";
			}
			
			emailContent += "<br/><br/>南通智造链有限公司";
			MailSenderInfo mailSenderInfo = new MailSenderInfo();
	        mailSenderInfo.setSubject(emailTitle);
	        mailSenderInfo.setContent(emailContent);
	        mailSenderInfo.setToAddress(emailAddr);
	        SimpleMailSender.sendHtmlMail(mailSenderInfo);	
			
	        model.addAttribute("notify", "已向客户邮箱推送订单当前状态信息！");
	        
	        return "/market/orderDetail";
		}

}
