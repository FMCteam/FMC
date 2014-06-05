package nju.software.controller;
import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nju.software.dataobject.Accessory;
import nju.software.dataobject.Account;
import nju.software.dataobject.DesignCad;
import nju.software.dataobject.Fabric;
import nju.software.dataobject.Logistics;
import nju.software.dataobject.Order;
import nju.software.dataobject.Produce;
import nju.software.dataobject.VersionData;
import nju.software.service.DesignCadService;
import nju.software.service.LogisticsService;
import nju.software.service.OrderService;
import nju.software.service.impl.BuyServiceImpl;
import nju.software.service.impl.DesignServiceImpl;
import nju.software.service.impl.MarketServiceImpl;
import nju.software.service.impl.ProduceServiceImpl;
import nju.software.util.DateUtil;
import nju.software.util.FileOperateUtil;

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
	private LogisticsService logisticsService;
	
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
		List<Map<String, Object>> orderModelList = orderService.getModifyOrderList();
		model.put("list", orderModelList);
		model.addAttribute("taskName", "修改订单");
		model.addAttribute("url", "/account/modifyOrderDetail.do");
		return "account/modifyOrderList";
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
		model.addAttribute("orderModel", orderModel);
		return "account/modifyOrderDetail";
	}

	// 询单的修改界面
		@RequestMapping(value = "account/modifyOrderSubmit.do", method = RequestMethod.POST)
		@Transactional(rollbackFor = Exception.class)
		public String modifyOrderSubmit(HttpServletRequest request,
				HttpServletResponse response, ModelMap model) {

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

			HttpSession session = request.getSession();
			Account account = (Account) session.getAttribute("cur_user");
//			boolean editok = request.getParameter("editok").equals("true") ? true
//					: false;
			orderService.modifyOrderSubmit(order, fabrics, accessorys, logistics,
					produces, sample_produces, versions, cad, account.getUserId());
			return "redirect:/account/modifyOrderList.do";
		}

}
