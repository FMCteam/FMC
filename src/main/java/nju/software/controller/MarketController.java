package nju.software.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import nju.software.dataobject.Accessory;
import nju.software.dataobject.Account;
import nju.software.dataobject.Customer;
import nju.software.dataobject.DesignCad;
import nju.software.dataobject.Fabric;
import nju.software.dataobject.Logistics;
import nju.software.dataobject.Money;
import nju.software.dataobject.Order;
import nju.software.dataobject.Produce;
import nju.software.dataobject.Product;
import nju.software.dataobject.Quote;
import nju.software.dataobject.VersionData;
import nju.software.model.OrderInfo;
import nju.software.model.OrderModel;
import nju.software.model.QuoteModel;
import nju.software.service.BuyService;
import nju.software.service.CustomerService;
import nju.software.service.DesignCadService;
import nju.software.service.LogisticsService;
import nju.software.service.MarketService;
import nju.software.service.OrderService;
import nju.software.service.QuoteService;
import nju.software.service.impl.BuyServiceImpl;
import nju.software.service.impl.DesignServiceImpl;
import nju.software.service.impl.JbpmTest;
import nju.software.service.impl.MarketServiceImpl;
import nju.software.service.impl.ProduceServiceImpl;
import nju.software.util.DateUtil;
import nju.software.util.FileOperateUtil;
import nju.software.util.JavaMailUtil;
import nju.software.util.JbpmAPIUtil;
import nju.software.util.StringUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ntp.TimeStamp;
import org.drools.runtime.process.WorkflowProcessInstance;
import org.jbpm.task.Task;
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
public class MarketController {
	@Autowired
	private OrderService orderService;
	@Autowired
	private BuyService buyService;
	@Autowired
	private DesignCadService cadService;
	@Autowired
	private LogisticsService logisticsService;
	@Autowired
	private QuoteService quoteService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private JbpmAPIUtil jbpmAPIUtil;

	@Autowired
	private JavaMailUtil javaMailUtil;
	@Autowired
	private JbpmTest jbpmTest;

	// ================================客户下单====================================
	@RequestMapping(value = "/market/addOrderList.do")
	@Transactional(rollbackFor = Exception.class)
	public String addOrderList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<Customer> customers = marketService.getAddOrderList();
		model.addAttribute("customers", customers);
		return "/market/addOrderList";
	}

	@RequestMapping(value = "/market/addOrderDetail.do")
	@Transactional(rollbackFor = Exception.class)
	public String addOrderDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String cid = request.getParameter("cid");
		Customer customer = marketService.getAddOrderDetail(Integer
				.parseInt(cid));
		model.addAttribute("customer", customer);
		HttpSession session = request.getSession();
		Account account = (Account) session.getAttribute("cur_user");
		model.addAttribute("employee_name", account.getNickName());
		return "/market/addOrderDetail";
	}

	@RequestMapping(value = "/market/addOrderSubmit.do")
	@Transactional(rollbackFor = Exception.class)
	public String addOrderSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {

		// 订单数据
		Integer customerId = Integer.parseInt(request
				.getParameter("customerId"));
		Customer customer = customerService.findByCustomerId(customerId);
		HttpSession session = request.getSession();
		Account account = (Account) session.getAttribute("cur_user");
		Integer employeeId = account.getUserId();
		String orderState = "A";
		Timestamp orderTime = new Timestamp(new Date().getTime());
		String customerName = customer.getCustomerName();
		String customerCompany = customer.getCompanyName();
		String customerCompanyFax = customer.getCompanyFax();
		String customerPhone1 = customer.getContactPhone1();
		String customerPhone2 = customer.getContactPhone2();
		String customerCompanyAddress = customer.getCompanyAddress();
		String styleName = request.getParameter("style_name");
		String fabricType = request.getParameter("fabric_type");
		String styleSex = request.getParameter("style_sex");
		String styleSeason = request.getParameter("style_season");
		String specialProcess = StringUtils.join(
				request.getParameterValues("special_process"), "|");
		String otherRequirements = StringUtils.join(
				request.getParameterValues("other_requirements"), "|");
		Integer askAmount = Integer
				.parseInt(request.getParameter("ask_amount"));
		String askProducePeriod = request.getParameter("ask_produce_period");
		Timestamp askDeliverDate = getTime(request
				.getParameter("ask_deliver_date"));
		String askCodeNumber = request.getParameter("ask_code_number");
		Short hasPostedSampleClothes = Short.parseShort(request
				.getParameter("has_posted_sample_clothes"));
		Short isNeedSampleClothes = Short.parseShort(request
				.getParameter("is_need_sample_clothes"));
		String orderSource = request.getParameter("order_source");
		String sampleClothesPicture = request
				.getParameter("sample_clothes_picture");
		String refPicture = request.getParameter("reference_picture");
		// 面料数据
		String fabric_names = request.getParameter("fabric_name");
		String fabric_amounts = request.getParameter("fabric_amount");
		String fabric_name[] = fabric_names.split(",");
		String fabric_amount[] = fabric_amounts.split(",");
		List<Fabric> fabrics = new ArrayList<Fabric>();
		for (int i = 0; i < fabric_name.length; i++) {
			if (fabric_name[i].equals(""))
				continue;
			fabrics.add(new Fabric(0, fabric_name[i], fabric_amount[i]));
		}

		// 辅料数据
		String accessory_names = request.getParameter("accessory_name");
		String accessory_querys = request.getParameter("accessory_query");
		String accessory_name[] = accessory_names.split(",");
		String accessory_query[] = accessory_querys.split(",");
		List<Accessory> accessorys = new ArrayList<Accessory>();
		for (int i = 0; i < accessory_name.length; i++) {
			if (accessory_name[i].equals(""))
				continue;
			accessorys.add(new Accessory(0, accessory_name[i],
					accessory_query[i]));
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
			p.setProduceAmount(l + m + s + xs + xl + xxl);
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
		Logistics logistics = new Logistics();
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
		}
		if (isNeedSampleClothes == 1) {
			// String sample_clothes_time = request
			// .getParameter("sample_clothes_time");
			// String sample_clothes_type = request
			// .getParameter("sample_clothes_type");
			// String sample_clothes_number = request
			// .getParameter("sample_clothes_number");
			String sample_clothes_name = request
					.getParameter("sample_clothes_name");
			String sample_clothes_phone = request
					.getParameter("sample_clothes_phone");
			String sample_clothes_address = request
					.getParameter("sample_clothes_address");
			String sample_clothes_remark = request
					.getParameter("sample_clothes_remark");

			// logistics.setSampleClothesTime(getTime(sample_clothes_time));
			// logistics.setSampleClothesType(sample_clothes_type);
			// logistics.setSampleClothesNumber(sample_clothes_number);
			logistics.setSampleClothesName(sample_clothes_name);
			logistics.setSampleClothesPhone(sample_clothes_phone);
			logistics.setSampleClothesAddress(sample_clothes_address);
			logistics.setSampleClothesRemark(sample_clothes_remark);
		}

		// CAD
		DesignCad cad = new DesignCad();
		cad.setOrderId(0);
		cad.setCadVersion((short) 1);
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
		// Order
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
		order.setAskAmount(askAmount);
		order.setSampleAmount(0);
		order.setAskProducePeriod(askProducePeriod);
		order.setAskDeliverDate(askDeliverDate);
		order.setAskCodeNumber(askCodeNumber);
		order.setHasPostedSampleClothes(hasPostedSampleClothes);
		order.setIsNeedSampleClothes(isNeedSampleClothes);
		order.setOrderSource(orderSource);

		marketService.addOrderSubmit(order, fabrics, accessorys, logistics,
				produces, sample_produces, versions, cad, request);

		JavaMailUtil.send();

		return "forward:/market/addOrderList.do";
	}

	// test precondition
	@RequestMapping(value = "market/precondition.do", method = RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String precondition(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<TaskSummary> task1 = jbpmAPIUtil.getAssignedTasksByTaskname(
				"CAIGOUZHUGUAN", "Purchasing_accounting");

		List<TaskSummary> task2 = jbpmAPIUtil.getAssignedTasksByTaskname(
				"SHEJIZHUGUAN", "design_accounting");
		List<TaskSummary> task3 = jbpmAPIUtil.getAssignedTasksByTaskname(
				"SHENGCHANZHUGUAN", "business_accounting");
		try {
			for (TaskSummary s1 : task1) {

				jbpmAPIUtil.completeTask(s1.getId(), null, "CAIGOUZHUGUAN");
			}
			for (TaskSummary s1 : task2) {

				jbpmAPIUtil.completeTask(s1.getId(), null, "SHEJIZHUGUAN");
			}
			for (TaskSummary s1 : task3) {

				jbpmAPIUtil.completeTask(s1.getId(), null, "SHENGCHANZHUGUAN");
			}
			System.out.println("precodition satisfied");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		return null;
	}

	@Autowired
	private MarketService marketService;

	// 专员修改报价
	@RequestMapping(value = "market/modifyQuoteSubmit.do", method = RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String modifyQuoteSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String s_profit = request.getParameter("profitPerPiece");
		String innerPrice = request.getParameter("inner_price");
		String outerPrice = request.getParameter("outer_price");
		String s_single = request.getParameter("single_cost");
		String orderId = request.getParameter("order_id");
		String s_taskId = request.getParameter("taskId");
		String s_processId = request.getParameter("processId");
		float profit = Float.parseFloat(s_profit);
		float inner = Float.parseFloat(innerPrice);
		float outer = Float.parseFloat(outerPrice);
		float single = Float.parseFloat(s_single);
		int id = Integer.parseInt(orderId);
		long taskId = Long.parseLong(s_taskId);
		long processId = Long.parseLong(s_processId);
		Quote quote = quoteService.findByOrderId(orderId);
		quote.setSingleCost(single);
		quote.setProfitPerPiece(profit);
		quote.setInnerPrice(inner);
		quote.setOuterPrice(outer);
		HttpSession session = request.getSession();
		Account account = (Account) session.getAttribute("cur_user");
		marketService.modifyQuoteSubmit(quote, id, taskId, processId,
				account.getUserId());

		return "redirect:/market/confirmQuoteList.do";
	}

	// 专员修改报价
	@RequestMapping(value = "market/modifyQuoteDetail.do", method = RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String modifyQuoteDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {

		String orderId = request.getParameter("orderId");
		// String s_processId=request.getParameter("pid");
		int id = Integer.parseInt(orderId);
		// long processId=Long.parseLong(s_processId);
		HttpSession session = request.getSession();
		Account account = (Account) session.getAttribute("cur_user");
		Map<String, Object> orderInfo = marketService.getModifyQuoteDetail(id,
				account.getUserId());
		model.addAttribute("orderInfo", orderInfo);
		return "market/modifyQuoteDetail";
	}

	// 专员修改报价列表
	@RequestMapping(value = "market/modifyQuoteList.do", method = RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String modifyQuoteList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {

		HttpSession session = request.getSession();
		Account account = (Account) session.getAttribute("cur_user");
		List<Map<String, Object>> tasks = marketService
				.getModifyQuoteList(account.getUserId());

		model.put("list", tasks);
		model.addAttribute("taskName", "修改报价");
		model.addAttribute("url", "/market/modifyQuoteDetail.do");
		return "market/modifyQuoteList";
	}

	// 专员修改加工单列表
	@RequestMapping(value = "market/modifyProductList.do", method = RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String modifyProductList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {

		HttpSession session = request.getSession();
		Account account = (Account) session.getAttribute("cur_user");
		List<Map<String, Object>> tasks = marketService
				.getModifyProductList(account.getUserId());

		model.put("list", tasks);
		model.addAttribute("taskName", "修改合同加工单");
		model.addAttribute("url", "/market/modifyProductDetail.do");
		return "market/modifyProductList";
	}

	// 专员修改加工单详情
	@RequestMapping(value = "market/modifyProductDetail.do", method = RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String modifyProductDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = request.getParameter("orderId");
		int id = Integer.parseInt(orderId);
		HttpSession session = request.getSession();
		Account account = (Account) session.getAttribute("cur_user");
		Map<String, Object> oi = marketService.getModifyProductDetail(id,
				account.getUserId());
		model.addAttribute("orderInfo", oi);
		return "market/modifyProductDetail";
	}

	// 专员修改加工单
	@RequestMapping(value = "market/modifyProductSubmit.do", method = RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String modifyProductSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Account account = (Account) request.getSession().getAttribute(
				"cur_user");
		String s_orderId_request = (String) request.getParameter("orderId");
		int orderId_request = Integer.parseInt(s_orderId_request);
		String s_taskId = request.getParameter("taskId");
		long taskId = Long.parseLong(s_taskId);
		String s_processId = request.getParameter("processId");
		long processId = Long.parseLong(s_processId);
		boolean editworksheetok = Boolean.parseBoolean(request
				.getParameter("tof"));
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

		marketService.modifyProductSubmit(account.getUserId() + "",
				orderId_request, taskId, processId, editworksheetok, produces);
		return "redirect:/market/modifyProductList.do";
	}

	// 专员合并报价
	@RequestMapping(value = "market/mergeQuoteSubmit.do", method = RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String mergeQuoteSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {

		String s_profit = request.getParameter("profitPerPiece");
		String innerPrice = request.getParameter("inner_price");
		String outerPrice = request.getParameter("outer_price");
		String s_single = request.getParameter("single_cost");
		String orderId = request.getParameter("order_id");
		String s_taskId = request.getParameter("taskId");
		String s_processId = request.getParameter("processId");
		float profit = Float.parseFloat(s_profit);
		float inner = Float.parseFloat(innerPrice);
		float outer = Float.parseFloat(outerPrice);
		float single = Float.parseFloat(s_single);
		int id = Integer.parseInt(orderId);
		long taskId = Long.parseLong(s_taskId);
		long processId = Long.parseLong(s_processId);

		Quote quote = quoteService.findByOrderId(orderId);
		quote.setProfitPerPiece(profit);
		quote.setInnerPrice(inner);
		quote.setOuterPrice(outer);
		quote.setSingleCost(single);
		HttpSession session = request.getSession();
		Account account = (Account) session.getAttribute("cur_user");
		marketService.mergeQuoteSubmit(account.getUserId(), quote, id, taskId,
				processId);
		return "redirect:/market/mergeQuoteList.do";
	}

	// 专员合并报价信息
	@RequestMapping(value = "market/mergeQuoteDetail.do")
	@Transactional(rollbackFor = Exception.class)
	public String mergeQuoteDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String s_id = request.getParameter("orderId");
		int id = Integer.parseInt(s_id);
		HttpSession session = request.getSession();
		Account account = (Account) session.getAttribute("cur_user");
		Map<String, Object> orderModel = marketService.getMergeQuoteDetail(
				account.getUserId(), id);
		model.addAttribute("orderInfo", orderModel);
		model.addAttribute("merge_w", true);
		return "market/mergeQuoteDetail";
	}

	// 专员合并报价List
	@RequestMapping(value = "market/mergeQuoteList.do", method = RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String mergeQuoteList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		HttpSession session = request.getSession();
		Account account = (Account) session.getAttribute("cur_user");

		List<Map<String, Object>> list = marketService
				.getMergeQuoteList(account.getAccountId());

		if (list.size() == 0) {
			jbpmTest.completeComputeCost(account.getAccountId() + "");
			list = marketService.getMergeQuoteList(account.getAccountId());
		}

		model.put("list", list);
		model.addAttribute("taskName", "合并报价");
		model.addAttribute("url", "/market/mergeQuoteDetail.do");
		return "market/mergeQuoteList";
	}

	// 主管审核报价
	@RequestMapping(value = "market/verifyQuoteSubmit.do", method = RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String verifyQuoteSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		/*
		 * Map params = new HashMap(); params.put("page", 1);
		 * params.put("number_per_page", 100); List list =
		 * customerService.listCustomer(params);
		 * model.addAttribute("customer_list", list.get(0));
		 */
		String s_single = request.getParameter("single_cost");
		String s_profit = request.getParameter("profitPerPiece");
		String innerPrice = request.getParameter("inner_price");
		String outerPrice = request.getParameter("outer_price");
		String orderId = request.getParameter("order_id");
		String s_taskId = request.getParameter("taskId");
		String s_processId = request.getParameter("processId");
		float profit = Float.parseFloat(s_profit);
		float inner = Float.parseFloat(innerPrice);
		float outer = Float.parseFloat(outerPrice);
		float single = Float.parseFloat(s_single);
		int id = Integer.parseInt(orderId);
		long taskId = Long.parseLong(s_taskId);
		long processId = Long.parseLong(s_processId);
		Quote quote = quoteService.findByOrderId(orderId);
		quote.setSingleCost(single);
		quote.setProfitPerPiece(profit);
		quote.setInnerPrice(inner);
		quote.setOuterPrice(outer);
		marketService.verifyQuoteSubmit(quote, id, taskId, processId);
		return "redirect:/market/verifyQuoteList.do";
	}

	// 主管审核报价detail
	@RequestMapping(value = "market/verifyQuoteDetail.do", method = RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String verifyQuoteDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String s_id = request.getParameter("orderId");
		int id = Integer.parseInt(s_id);
		HttpSession session = request.getSession();
		Account account = (Account) session.getAttribute("cur_user");
		Map<String, Object> orderModel = marketService.getVerifyQuoteDetail(
				account.getUserId(), id);
		model.addAttribute("orderInfo", orderModel);
		return "market/verifyQuoteDetail";

	}

	// 主管审核报价List
	@RequestMapping(value = "market/verifyQuoteList.do", method = RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String verifyQuoteList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		HttpSession session = request.getSession();
		Account account = (Account) session.getAttribute("cur_user");
		List<Map<String, Object>> list = marketService
				.getVerifyQuoteList(account.getUserId());
		model.put("list", list);
		model.addAttribute("taskName", "审核报价");
		model.addAttribute("url", "/market/verifyQuoteDetail.do");
		return "market/verifyQuoteList";

	}

	// 修改询单的列表
	@RequestMapping(value = "market/modifyOrderList.do", method = RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String modifyOrderList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		HttpSession session = request.getSession();
		Account account = (Account) session.getAttribute("cur_user");
		List<Map<String, Object>> orderModelList = marketService
				.getModifyOrderList(account.getUserId());
		if (orderModelList.size() == 0) {
			jbpmTest.completeVerify(account.getUserId() + "", false);
			orderModelList = marketService.getModifyOrderList(account
					.getUserId());
		}
		model.put("list", orderModelList);
		model.addAttribute("taskName", "修改询单");
		model.addAttribute("url", "/market/modifyOrderDetail.do");
		return "market/modifyOrderList";
	}

	// 询单的修改界面
	@RequestMapping(value = "market/modifyOrderDetail.do")
	@Transactional(rollbackFor = Exception.class)
	public String modifyOrderDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {

		String s_id = request.getParameter("orderId");
		int id = Integer.parseInt(s_id);
		// 修改
		HttpSession session = request.getSession();
		Account account = (Account) session.getAttribute("cur_user");
		Map<String, Object> orderModel = marketService.getModifyOrderDetail(
				account.getUserId(), id);
		model.addAttribute("orderModel", orderModel);
		String purchaseComment = (String)jbpmAPIUtil.getVariable(
				(TaskSummary) orderModel.get("task"),
				BuyServiceImpl.RESULT_PURCHASE_COMMENT);
		String designComment = (String)jbpmAPIUtil.getVariable(
				(TaskSummary) orderModel.get("task"),
				DesignServiceImpl.RESULT_DESIGN_COMMENT);
		String produceComment = (String)jbpmAPIUtil.getVariable(
				(TaskSummary) orderModel.get("task"), 
				ProduceServiceImpl.RESULT_PRODUCE_COMMENT);
		model.addAttribute("purchaseComment", purchaseComment);
		model.addAttribute("designComment", designComment);
		model.addAttribute("produceComment", produceComment);
		return "market/modifyOrderDetail";
	}

	// 询单的修改界面
	@RequestMapping(value = "market/modifyOrderSubmit.do", method = RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String modifyOrderSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {

		String s_id = request.getParameter("id");
		String s_task_id = request.getParameter("task_id");
		int id = Integer.parseInt(s_id);
		long task_id = Long.parseLong(s_task_id);
		// 保存修改该的order数据，accessory，fabric，logistics
		// 订单数据

		String orderState = "A";
		String styleName = request.getParameter("style_name");
		String fabricType = request.getParameter("fabric_type");
		String styleSex = request.getParameter("style_sex");
		String styleSeason = request.getParameter("style_season");
		String specialProcess = StringUtils.join(
				request.getParameterValues("special_process"), "|");
		String otherRequirements = StringUtils.join(
				request.getParameterValues("other_requirements"), "|");
		Calendar calendar = Calendar.getInstance();

		Integer askAmount = Integer
				.parseInt(request.getParameter("ask_amount"));
		String askProducePeriod = request.getParameter("ask_produce_period");
		Timestamp askDeliverDate = getTime(request
				.getParameter("ask_deliver_date"));
		String askCodeNumber = request.getParameter("ask_code_number");
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
			p.setProduceAmount(l + m + s + xs + xl + xxl);
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
		}
		if (isNeedSampleClothes == 1) {
			String sample_clothes_time = request
					.getParameter("sample_clothes_time");
			String sample_clothes_type = request
					.getParameter("sample_clothes_type");
			String sample_clothes_number = request
					.getParameter("sample_clothes_number");
			String sample_clothes_name = request
					.getParameter("sample_clothes_name");
			String sample_clothes_phone = request
					.getParameter("sample_clothes_phone");
			String sample_clothes_address = request
					.getParameter("sample_clothes_address");
			String sample_clothes_remark = request
					.getParameter("sample_clothes_remark");

			logistics.setSampleClothesTime(getTime(sample_clothes_time));
			logistics.setSampleClothesType(sample_clothes_type);
			logistics.setSampleClothesNumber(sample_clothes_number);
			logistics.setSampleClothesName(sample_clothes_name);
			logistics.setSampleClothesPhone(sample_clothes_phone);
			logistics.setSampleClothesAddress(sample_clothes_address);
			logistics.setSampleClothesRemark(sample_clothes_remark);
		}
		
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
		order.setStyleSex(styleSex);
		order.setStyleSeason(styleSeason);
		order.setSpecialProcess(specialProcess);
		order.setOtherRequirements(otherRequirements);
		// order.setSampleClothesPicture(sampleClothesPicture);
		// order.setReferencePicture(referencePicture);
		order.setAskAmount(askAmount);
		order.setAskProducePeriod(askProducePeriod);
		order.setAskDeliverDate(askDeliverDate);
		order.setAskCodeNumber(askCodeNumber);
		order.setHasPostedSampleClothes(hasPostedSampleClothes);
		order.setIsNeedSampleClothes(isNeedSampleClothes);
		order.setOrderSource(orderSource);

		HttpSession session = request.getSession();
		Account account = (Account) session.getAttribute("cur_user");
		boolean editok = request.getParameter("editok").equals("true") ? true
				: false;
		marketService.modifyOrderSubmit(order, fabrics, accessorys, logistics,
				produces, sample_produces, versions, cad, editok, task_id,
				account.getUserId());
		// WorkflowProcessInstance process = (WorkflowProcessInstance)
		// jbpmAPIUtil
		// .getKsession().getProcessInstance(Long.parseLong(s_process_id));
		// String buyComment = process.getVariable("buyComment").toString();
		// String designComment =
		// process.getVariable("designComment").toString();
		// // String
		// // productComment=process.getVariable("productComment").toString();
		// orderService.verify(id, task_id, process_id, true, buyComment,
		// designComment, null);
		return "redirect:/market/modifyOrderList.do";
	}

	public static Timestamp getTime(String time) {
		Date outDate = DateUtil.parse(time, DateUtil.newFormat);
		return new Timestamp(outDate.getTime());
	}

	@RequestMapping(value = "/market/confirmQuoteList.do")
	@Transactional(rollbackFor = Exception.class)
	public String confirmQuoteList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		HttpSession session = request.getSession();
		Account account = (Account) session.getAttribute("cur_user");
		List<Map<String, Object>> list = marketService
				.getConfirmQuoteList(account.getUserId() + "");
		model.put("list", list);
		model.addAttribute("taskName", "确认报价");
		model.addAttribute("url", "/market/confirmQuoteDetail.do");
		return "market/confirmQuoteList";
	}

	@RequestMapping(value = "/market/confirmQuoteDetail.do")
	@Transactional(rollbackFor = Exception.class)
	public String confirmQuoteDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String s_id = request.getParameter("orderId");
		int id = Integer.parseInt(s_id);
		HttpSession session = request.getSession();
		Account account = (Account) session.getAttribute("cur_user");
		Map<String, Object> orderModel = marketService.getConfirmQuoteDetail(
				account.getUserId(), id);
		model.addAttribute("orderInfo", orderModel);
		return "market/confirmQuoteDetail";
	}

	@RequestMapping(value = "/market/confirmQuoteSubmit.do", method = RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String confirmQuoteSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {

		String result = request.getParameter("result");
		String taskId = request.getParameter("taskId");
		String orderId = request.getParameter("orderId");
		Account account = (Account) request.getSession().getAttribute(
				"cur_user");
		String actorId = account.getUserId() + "";
		marketService.confirmQuoteSubmit(actorId, Long.parseLong(taskId),
				result);

		// 1=修改报价
		if (result.equals("1")) {
			return "redirect:/market/modifyQuoteList.do?id=" + orderId;
		} else {
			return "redirect:/market/confirmQuoteList.do";
		}
	}

	// ============================确认合同加工单===========================

	/**
	 * 确认合同加工单跳转链接
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "market/confirmProduceOrderList.do", method = RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String confirmProduceOrderList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {

		Account account = (Account) request.getSession().getAttribute(
				"cur_user");
		String actorId = account.getUserId() + "";
		List<Map<String, Object>> orderList = marketService
				.getConfirmProductList(actorId);
		if (orderList.size() == 0) {
			jbpmTest.completeProduceConfirm("1", true);
			orderList = marketService.getConfirmProductList(actorId);
		}
		model.put("list", orderList);
		model.addAttribute("taskName", "确认合同加工单");
		model.addAttribute("url", "/market/confirmProduceOrderDetail.do");
		return "market/confirmProductList";
	}

	/**
	 * 确认合同加工单
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "market/confirmProduceOrderSubmit.do", method = RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String confirmProduceOrderSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {

		Account account = (Account) request.getSession().getAttribute(
				"cur_user");
		String s_orderId_request = (String) request.getParameter("orderId");
		int orderId_request = Integer.parseInt(s_orderId_request);
		String s_taskId = request.getParameter("taskId");
		long taskId = Long.parseLong(s_taskId);
		String s_processId = request.getParameter("processId");
		long processId = Long.parseLong(s_processId);
		boolean comfirmworksheet = Boolean.parseBoolean(request
				.getParameter("tof"));
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

		marketService.confirmProduceOrderSubmit(account.getUserId() + "",
				orderId_request, taskId, processId, comfirmworksheet, produces);
		return "redirect:/market/confirmProduceOrderList.do";
	}

	/**
	 * 确认合同加工单详情
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "market/confirmProduceOrderDetail.do")
	@Transactional(rollbackFor = Exception.class)
	public String confirmProduceOrderDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {

		Account account = (Account) request.getSession().getAttribute(
				"cur_user");
		String s_orderId_request = (String) request.getParameter("orderId");
		int id = Integer.parseInt(s_orderId_request);
		// String s_taskId = request.getParameter("taskId");
		// long taskId = Long.parseLong(s_taskId);
		Map<String, Object> orderInfo = marketService.getConfirmProductDetail(
				account.getUserId(), id);
		model.addAttribute("orderInfo", orderInfo);

		return "market/confirmProductDetail";
	}

	/**
	 * 取消订单
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "market/cancelProduct.do", method = RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String cancelSample(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {

		System.out.println("cancel product ===============");
		Account account = (Account) request.getSession().getAttribute(
				"cur_user");
		String s_orderId_request = (String) request.getParameter("id");
		int orderId_request = Integer.parseInt(s_orderId_request);
		String s_taskId = request.getParameter("task_id");
		long taskId = Long.parseLong(s_taskId);
		String s_processId = request.getParameter("process_id");
		long processId = Long.parseLong(s_processId);
		boolean comfirmworksheet = false;
		marketService.confirmProduceOrderSubmit(account.getUserId() + "",
				orderId_request, taskId, processId, comfirmworksheet, null);

		return "redirect:/market/confirmProduceOrderList.do";
	}

	// =======================================================

	// ========================签订合同============================
	@RequestMapping(value = "/market/signContractList.do")
	@Transactional(rollbackFor = Exception.class)
	public String signContractList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Account account = (Account) request.getSession().getAttribute(
				"cur_user");
		List<Map<String, Object>> list = marketService
				.getSignContractList(account.getUserId() + "");
		if (list.size() == 0) {
			jbpmTest.completeProduceConfirm(account.getUserId() + "", true);
			marketService.getSignContractList(account.getUserId() + "");
		}
		model.put("list", list);
		model.addAttribute("taskName", "签订合同");
		model.addAttribute("url", "/market/signContractDetail.do");
		return "/market/signContractList";
	}

	@RequestMapping(value = "/market/signContractDetail.do")
	@Transactional(rollbackFor = Exception.class)
	public String signContractDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Account account = (Account) request.getSession().getAttribute(
				"cur_user");
		String orderId = request.getParameter("orderId");
		Map<String, Object> orderInfo = marketService.getSignContractDetail(
				account.getUserId() + "", Integer.parseInt(orderId));
		model.addAttribute("orderInfo", orderInfo);
		return "/market/signContractDetail";
	}

	@RequestMapping(value = "market/signContractSubmit.do", method = RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String signContractSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String discount = request.getParameter("discount");
		String total = request.getParameter("totalmoney");
		String orderId = request.getParameter("orderId");
		String taskId = request.getParameter("taskId");
		Account account = (Account) request.getSession().getAttribute(
				"cur_user");
		String actorId = account.getUserId() + "";

		marketService.signContractSubmit(actorId, Long.parseLong(taskId),
				Integer.parseInt(orderId), Double.parseDouble(discount),
				Double.parseDouble(total));
		return "redirect:/market/signContractList.do";
	}

	@RequestMapping(value = "/market/orderList.do")
	@Transactional(rollbackFor = Exception.class)
	public String orderList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Account account = (Account) request.getSession().getAttribute(
				"cur_user");
		List<Map<String, Object>> list = marketService.getOrderList(account
				.getUserId());
		model.addAttribute("list", list);
		model.addAttribute("taskName", "订单列表");
		model.addAttribute("url", "/market/orderDetail.do");
		return "/market/orderList";
	}

	@RequestMapping(value = "/market/orderDetail.do")
	@Transactional(rollbackFor = Exception.class)
	public String orderDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Integer orderId = Integer.parseInt(request.getParameter("orderId"));
		Map<String, Object> orderInfo = marketService.getOrderDetail(orderId);
		model.addAttribute("orderInfo", orderInfo);
		return "/market/orderDetail";
	}
}
