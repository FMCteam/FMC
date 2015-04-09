package nju.software.service.impl;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import nju.software.dao.impl.AccessoryCostDAO;
import nju.software.dao.impl.AccessoryDAO;
import nju.software.dao.impl.CheckRecordDAO;
import nju.software.dao.impl.CraftDAO;
import nju.software.dao.impl.CustomerDAO;
import nju.software.dao.impl.DeliveryRecordDAO;
import nju.software.dao.impl.DesignCadDAO;
import nju.software.dao.impl.EmployeeDAO;
import nju.software.dao.impl.FabricCostDAO;
import nju.software.dao.impl.FabricDAO;
import nju.software.dao.impl.LogisticsDAO;
import nju.software.dao.impl.MarketstaffAlterDAO;
import nju.software.dao.impl.MoneyDAO;
import nju.software.dao.impl.OrderDAO;
import nju.software.dao.impl.ProduceDAO;
import nju.software.dao.impl.ProductDAO;
import nju.software.dao.impl.QuoteDAO;
import nju.software.dao.impl.VersionDataDAO;
import nju.software.dataobject.Accessory;
import nju.software.dataobject.AccessoryCost;
import nju.software.dataobject.CheckRecord;
import nju.software.dataobject.Craft;
import nju.software.dataobject.Customer;
import nju.software.dataobject.DeliveryRecord;
import nju.software.dataobject.DesignCad;
import nju.software.dataobject.Employee;
import nju.software.dataobject.Fabric;
import nju.software.dataobject.FabricCost;
import nju.software.dataobject.Logistics;
import nju.software.dataobject.MarketstaffAlter;
import nju.software.dataobject.Money;
import nju.software.dataobject.Order;
import nju.software.dataobject.Produce;
import nju.software.dataobject.Product;
import nju.software.dataobject.Quote;
import nju.software.dataobject.VersionData;
import nju.software.model.OrderInfo;
import nju.software.process.service.MainProcessService;
import nju.software.service.FinanceService;
import nju.software.service.MarketService;
import nju.software.util.ActivitiAPIUtil;
import nju.software.util.FileOperateUtil;
import nju.software.util.ImageUtil;
import nju.software.util.StringUtil;
import nju.software.util.mail.MailSenderInfo;
import nju.software.util.mail.SimpleMailSender;

import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Service("marketServiceImpl")
public class MarketServiceImpl implements MarketService {

	public final static String ACTOR_MARKET_MANAGER = "marketManager";
	public final static String ACTOR_MARKET_STAFF = "marketStaff";
	public final static String TASK_MODIFY_ORDER = "modifyOrder";
	public final static String TASK_MERGE_QUOTE = "mergeQuote";
	public final static String TASK_VERIFY_QUOTE = "verifyQuote";
	public final static String TASK_CONFIRM_QUOTE = "confirmQuote";
	public final static String TASK_MODIFY_QUOTE = "modifyQuote";
	public final static String TASK_CONFIRM_PRODUCE_ORDER = "confirmProduceOrder";
	public final static String TASK_MODIFY_PRODUCE_ORDER = "modifyProduceOrder";
	public final static String TASK_SIGN_CONTRACT = "signContract";
	public final static String TASK_PUSH_REST = "pushRest";

	
	public final static String RESULT_REORDER = "reorder";
	public final static String RESULT_MODIFY_ORDER = "modifyOrder";
	public final static String RESULT_QUOTE = "quote";
	public final static String RESULT_CONFIRM_PRODUCE_ORDER = "confirmProduceOrder";
	public final static String RESULT_IS_HAODUOYI = "isHaoDuoYi";
	public final static String RESULT_CONFIRM_PRODUCE_ORDER_CONTRACT = "confirmProduceOrderContract";
	public final static String RESULT_MODIFY_PRODUCE_ORDER = "modifyProduceOrder";
	public final static String RESULT_PUSH_RESTMONEY = "pushRestMoney";
	public final static String UPLOAD_DIR_SAMPLE = "/upload/sample/";
	public final static String UPLOAD_DIR_REFERENCE = "/upload/reference/";
	public final static String RESULT_VERIFY_QUOTE = "verifyQuoteSuccess";
	public final static String VERIFY_QUOTE_COMMENT = "verifyQuoteComment";
	public final static String RESULT_REASON="reason";
	public final static String RESULT_ALTERINFO="alterInfo";

	@Autowired
	private ProductDAO productDAO;
	@Autowired
	private CustomerDAO customerDAO;
	@Autowired
	private EmployeeDAO employeeDAO;
	@Autowired
	private OrderDAO orderDAO;
	@Autowired
	private QuoteDAO quoteDAO;
	@Autowired
	private MainProcessService mainProcessService;
	@Autowired
	private AccessoryDAO accessoryDAO;
	@Autowired
	private FabricDAO fabricDAO;
	@Autowired
	private DesignCadDAO cadDAO;
	@Autowired
	private ProduceDAO produceDAO;
	@Autowired
	private FinanceService financeService;
	@Autowired
	private VersionDataDAO versionDataDAO;
	@Autowired
	private LogisticsDAO logisticsDAO;
	@Autowired
	private FabricCostDAO fabricCostDAO;
	@Autowired
	private AccessoryCostDAO accessoryCostDAO;
	@Autowired
	private MoneyDAO moneyDAO;
	@Autowired
	private CheckRecordDAO checkRecordDAO;
	@Autowired
	private CraftDAO craftDAO;
	@Autowired
	private ServiceUtil service;
	@Autowired
	private DeliveryRecordDAO deliveryRecordDAO;
	@Autowired
	private MarketstaffAlterDAO marketstaffAlterDAO;
	
	
	@Override
	public List<Map<String, Object>> getAlterInfoByOrderId(Integer orderId) {

		MarketstaffAlter example = new MarketstaffAlter();
		List<MarketstaffAlter> list = new ArrayList<>();
		example.setOrderId(orderId);
		list = marketstaffAlterDAO.findByExample(example);
		List<Map<String, Object>> mapList= new ArrayList<>();
		for (MarketstaffAlter alter:list){
			String reasonString =null;
			//TODO reason
			Map<String,Object> alterInfo=new HashMap<String,Object>();
			alterInfo.put(MarketServiceImpl.RESULT_REASON, reasonString);
			alterInfo.put(MarketServiceImpl.RESULT_ALTERINFO, alter);
			mapList.add(alterInfo);		
			
		}
		return mapList;
	}
	
	@Override
	public List<Map<String, Object>> getAlltoDoAlterInfo() {
		MarketstaffAlter example = new MarketstaffAlter();
		List<MarketstaffAlter> list = new ArrayList<>();
		example.setVerifyState(MarketstaffAlter.STATE_TODO);
		list = marketstaffAlterDAO.findByExample(example);
		List<Map<String, Object>> mapList= new ArrayList<>();
		for (MarketstaffAlter alter:list){
			String reasonString =null;
			//TODO reason
			Map<String,Object> alterInfo=new HashMap<String,Object>();
			alterInfo.put(MarketServiceImpl.RESULT_REASON, reasonString);
			alterInfo.put(MarketServiceImpl.RESULT_ALTERINFO, alter);
			mapList.add(alterInfo);
			
			
		}
		return mapList;
	}

	
	@Override
	public void verifyAlterSubmit(MarketstaffAlter alter, String taskId,
			String processId, boolean result, String suggestion) {
		//TODO
	}
	
	@Override
	public List<MarketstaffAlter> getAlltoDoAlter() {
		MarketstaffAlter example = new MarketstaffAlter();
		List<MarketstaffAlter> results = new ArrayList<>();
		example.setVerifyState(MarketstaffAlter.STATE_TODO);
		results = marketstaffAlterDAO.findByExample(example);
		return results;
	}

	@Override
	public MarketstaffAlter getMarketStaffAlterById(int alterId) {
		MarketstaffAlter alter=marketstaffAlterDAO.findById(alterId);
		return alter;
	}

	@Override
	public boolean applyForAlterMarketStaffSubmit(MarketstaffAlter alterInfo, String reason) {
		marketstaffAlterDAO.save(alterInfo);
		Map<String, Object> params = new HashMap<>();
		params.put("reason", reason);
//		activitiApiUtil.startWorkflowProcessByKey(ActivitiAPIUtil.PROCESS_ALTER_MARKETSTAFF, params);
		//TODO 涉及流程启动
		
		
		return true;
	}

	@Override
	public List<Map<String, Object>> getOrderAlterDoingList(String actorId) {
		MarketstaffAlter example = new MarketstaffAlter();
		List<MarketstaffAlter> results = new ArrayList<>();
		example.setVerifyState(MarketstaffAlter.STATE_TODO);
		example.setEmployeeId(Integer.valueOf(actorId));
		results = marketstaffAlterDAO.findByExample(example);
		
		List<Map<String, Object>> list = new ArrayList<>();
		for (MarketstaffAlter marketstaffAlter : results) {
			Integer orderId = marketstaffAlter.getOrderId();
			Order order = orderDAO.findById(orderId);
			Map<String, Object> model = new HashMap<>();
			model.put("alterId", marketstaffAlter.getAlterId());
			model.put("order", order);
			model.put("verify_state", marketstaffAlter.getVerifyState());
			list.add(model);
		}
		return list;
	}




	
	@Override
	public Map<String, Object> getApplyAlterInfo(Integer alterId) {
		MarketstaffAlter alter=marketstaffAlterDAO.findById(alterId);
		Map<String,Object> alterMap=new HashMap<String ,Object>();
		Order order=orderDAO.findById(alter.getAlterId());
		Employee employee=employeeDAO.findById(alter.getEmployeeId());
		Employee nextEmployee=employeeDAO.findById(alter.getNextEmployeeId());
		
		alterMap.put("alterId", alter.getAlterId());
		alterMap.put("order", order);
		alterMap.put("employee", employee);
		alterMap.put("nextEmployee", nextEmployee);
		alterMap.put("currentOrderTaskName",alter.getCurrentTaskName());
		alterMap.put("applyTime", alter.getApplyTime());
		alterMap.put("endTime",alter.getEndTime());
		alterMap.put("verify_state", alter.getVerifyState());
		
		return alterMap;
	}



	@Override
	public void verifyAlterMarketstaffSubmit(MarketstaffAlter alterInfo,  boolean result, String comment) {
//		MarketstaffAlter alter=marketstaffAlterDAO.findById(alterId);
		if (result==true)alterInfo.setVerifyState(MarketstaffAlter.STATE_AGREE);
		else alterInfo.setVerifyState(MarketstaffAlter.STATE_DISAGREE);
		
		
		//TODO 
		//涉及流程 comment  
		marketstaffAlterDAO.save(alterInfo);
		
	}



	@Override
	public void changeMarketstaffSubmit(Integer alterId,
			String applyUserId, String newUserId) {

		MarketstaffAlter alter=marketstaffAlterDAO.findById(alterId);
		alter.setNextEmployeeId(Integer.parseInt(newUserId));
		marketstaffAlterDAO.save(alter);
		Order order=orderDAO.findById(alter.getOrderId());
		order.setEmployeeId(Integer.parseInt(newUserId));
		orderDAO.save(order);
		//TODO 结束流程
				
		
	}

	@Override
	public List<Map<String, Object>> getApplyAlterOrderList(String actorId) {
		MarketstaffAlter example = new MarketstaffAlter();
		List<MarketstaffAlter> results = new ArrayList<>();
		example.setVerifyState(MarketstaffAlter.STATE_TODO);
		results = marketstaffAlterDAO.findByExample(example);
		
		List<Map<String, Object>> list = new ArrayList<>();
		for (MarketstaffAlter marketstaffAlter : results) {
			Integer orderId = marketstaffAlter.getOrderId();
			Order order = orderDAO.findById(orderId);
			Map<String, Object> model = new HashMap<>();
			model.put("order", order);
			model.put("verify_state", marketstaffAlter.getVerifyState());
			list.add(model);
		}
		return list;
	}
	
	@Override
	public List<Customer> getAddOrderList() {
		// TODO Auto-generated method stub
		return customerDAO.findAll();
	}

	@Override
	public Customer getAddOrderDetail(Integer cid) {
		// TODO Auto-generated method stub
		return customerDAO.findById(cid);
	}

	@Override
	public boolean addOrderCustomerSubmit(Order order, List<Fabric> fabrics,
			List<Accessory> accessorys, Logistics logistics,
			List<Produce> produces, List<Produce> sample_produces,
			List<VersionData> versions, DesignCad cad,
			HttpServletRequest request){

		OrderDAO orderDAO=new OrderDAO();
		// 添加订单信息
		orderDAO.save(order);

		Integer orderId = order.getOrderId();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

		if (!multipartRequest.getFile("sample_clothes_picture").isEmpty()) {
			// String filedir = request.getSession().getServletContext()
			// .getRealPath("/upload/sample/" + orderId);

			String curPath = request.getSession().getServletContext()
					.getRealPath("/");
			String fatherPath = new File(curPath).getParent();
			String relativePath = File.separator + "upload" + File.separator
					+ "sample" + File.separator + orderId;
			String filedir = fatherPath + relativePath;
			File file = FileOperateUtil.Upload(request, filedir, "1",
					"sample_clothes_picture");
			System.out.println("-------" + filedir + File.separator);
			ImageUtil.createThumbnail(filedir.replaceAll("\\\\", "\\\\\\\\"),
					filedir + File.separator);
			order.setSampleClothesPicture(UPLOAD_DIR_SAMPLE + orderId + "/"
					+ file.getName());
			order.setSampleClothesThumbnailPicture(UPLOAD_DIR_SAMPLE + orderId
					+ "/" + "thumbnail.png");
		}

		if (!multipartRequest.getFile("reference_picture").isEmpty()) {
			// String filedir = request.getSession().getServletContext()
			// .getRealPath("/upload/reference/" + orderId);

			String curPath = request.getSession().getServletContext()
					.getRealPath("/");
			String fatherPath = new File(curPath).getParent();
			String relativePath = File.separator + "upload" + File.separator
					+ "reference" + File.separator + orderId;
			String filedir = fatherPath + relativePath;

			File file = FileOperateUtil.Upload(request, filedir, "1",
					"reference_picture");
			order.setReferencePicture(UPLOAD_DIR_REFERENCE + orderId + "/"
					+ file.getName());
		}

		orderDAO.attachDirty(order);

		// 添加面料信息
		for (Fabric fabric : fabrics) {
			fabric.setOrderId(orderId);
			fabricDAO.save(fabric);
		}

		// 添加辅料信息
		for (Accessory accessory : accessorys) {
			accessory.setOrderId(orderId);
			accessoryDAO.save(accessory);
		}

		// 添加大货加工单信息
		for (Produce produce : produces) {
			produce.setOid(orderId);
			produceDAO.save(produce);
		}

		// 添加样衣加工单信息
		for (Produce produce : sample_produces) {
			produce.setOid(orderId);
			produceDAO.save(produce);
		}

		// 添加版型信息
		for (VersionData versionData : versions) {
			versionData.setOrderId(orderId);
			versionDataDAO.save(versionData);
		}

		// 添加物流信息
		logistics.setOrderId(orderId);
		logisticsDAO.save(logistics);

		// cad
		cad.setOrderId(orderId);
		cadDAO.save(cad);
//		// 启动流程
//		Map<String, Object> params = getParams(order);
//		String processId = activitiApiUtil.startWorkflowProcess(params);
//		order.setProcessId(processId);
//		orderDAO.attachDirty(order);
		
//		//测试事务回滚是否成功
//				if (true) {
//					throw new RuntimeException();
//				}
		return true;
	}
	
	@Override
	public boolean addOrderSubmit(Order order, List<Fabric> fabrics,
			List<Accessory> accessorys, Logistics logistics,
			List<Produce> produces, List<Produce> sample_produces,
			List<VersionData> versions, DesignCad cad,
			HttpServletRequest request){

		// 添加订单信息
		orderDAO.save(order);

		Integer orderId = order.getOrderId();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

		if (!multipartRequest.getFile("sample_clothes_picture").isEmpty()) {
			// String filedir = request.getSession().getServletContext()
			// .getRealPath("/upload/sample/" + orderId);

			String curPath = request.getSession().getServletContext()
					.getRealPath("/");
			String fatherPath = new File(curPath).getParent();
			String relativePath = File.separator + "upload" + File.separator
					+ "sample" + File.separator + orderId;
			String filedir = fatherPath + relativePath;
			File file = FileOperateUtil.Upload(request, filedir, "1",
					"sample_clothes_picture");
			System.out.println("-------" + filedir + File.separator);
			ImageUtil.createThumbnail(filedir.replaceAll("\\\\", "\\\\\\\\"),
					filedir + File.separator);
			order.setSampleClothesPicture(UPLOAD_DIR_SAMPLE + orderId + "/"
					+ file.getName());
			order.setSampleClothesThumbnailPicture(UPLOAD_DIR_SAMPLE + orderId
					+ "/" + "thumbnail.png");
		}

		if (!multipartRequest.getFile("reference_picture").isEmpty()) {
			// String filedir = request.getSession().getServletContext()
			// .getRealPath("/upload/reference/" + orderId);

			String curPath = request.getSession().getServletContext()
					.getRealPath("/");
			String fatherPath = new File(curPath).getParent();
			String relativePath = File.separator + "upload" + File.separator
					+ "reference" + File.separator + orderId;
			String filedir = fatherPath + relativePath;

			File file = FileOperateUtil.Upload(request, filedir, "1",
					"reference_picture");
			order.setReferencePicture(UPLOAD_DIR_REFERENCE + orderId + "/"
					+ file.getName());
		}

		orderDAO.attachDirty(order);

		// 添加面料信息
		for (Fabric fabric : fabrics) {
			fabric.setOrderId(orderId);
			fabricDAO.save(fabric);
		}

		// 添加辅料信息
		for (Accessory accessory : accessorys) {
			accessory.setOrderId(orderId);
			accessoryDAO.save(accessory);
		}

		// 添加大货加工单信息
		for (Produce produce : produces) {
			produce.setOid(orderId);
			produceDAO.save(produce);
		}

		// 添加样衣加工单信息
		for (Produce produce : sample_produces) {
			produce.setOid(orderId);
			produceDAO.save(produce);
		}

		// 添加版型信息
		for (VersionData versionData : versions) {
			versionData.setOrderId(orderId);
			versionDataDAO.save(versionData);
		}

		// 添加物流信息
		logistics.setOrderId(orderId);
		logisticsDAO.save(logistics);

		// cad
		cad.setOrderId(orderId);
		cadDAO.save(cad);
		// 启动流程
		Map<String, Object> params = getParams(order);
		String processId = mainProcessService.startWorkflow(params);
		order.setProcessId(processId);
		orderDAO.attachDirty(order);
		
//		//测试事务回滚是否成功
//				if (true) {
//					throw new RuntimeException();
//				}
		return true;
	}

	@Override
	public void addMoreOrderSubmit(Order order, List<Fabric> fabrics,
			List<Accessory> accessorys, Logistics logistics,
			List<Produce> produces, List<VersionData> versions, DesignCad cad,
			HttpServletRequest request) {
		// 添加订单信息
		orderDAO.save(order);

		Integer orderId = order.getOrderId();

		// 添加面料信息
		for (Fabric fabric : fabrics) {
			fabric.setOrderId(orderId);
			fabricDAO.save(fabric);
		}

		// 添加辅料信息
		for (Accessory accessory : accessorys) {
			accessory.setOrderId(orderId);
			accessoryDAO.save(accessory);
		}

		// 添加大货加工单信息
		for (Produce produce : produces) {
			produce.setOid(orderId);
			produceDAO.save(produce);
		}

		// 添加样衣加工单信息
		// for (Produce produce : sample_produces) {
		// produce.setOid(orderId);
		// produceDAO.save(produce);
		// }

		// 添加版型信息
		for (VersionData versionData : versions) {
			versionData.setOrderId(orderId);
			versionDataDAO.save(versionData);
		}

		// 添加物流信息
		logistics.setOrderId(orderId);
		logisticsDAO.save(logistics);

		// cad
		cad.setOrderId(orderId);
		cadDAO.save(cad);

		// 报价
		String sourceId = request.getParameter("sourceId");
		System.out.println("sourceId:-------->" + sourceId);
		System.out.println("orderId:-------->" + orderId);
		Integer source = Integer.parseInt(sourceId);

		Quote quote = quoteDAO.findById(source);
		try {
			Quote newQuote = (Quote) copy(quote);
			newQuote.setOrderId(orderId);
			quoteDAO.save(newQuote);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 工艺报价
		List<Craft> craftList = craftDAO.findByOrderId(source);
		Craft craft = null;
		boolean isNeedCraft = false;
		if (craftList != null && craftList.size() > 0) {
			craft = craftList.get(0);
		}
		if (craft.getNeedCraft() == 1) {// 如果需要工艺
			isNeedCraft = true;
		}
		try {
			Craft newCraft = (Craft) copy(craft);
			newCraft.setOrderId(orderId);
			craftDAO.save(newCraft);
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<FabricCost> fabricCosts = fabricCostDAO.findByOrderId(source);
		for (FabricCost fc : fabricCosts) {
			FabricCost newFC = new FabricCost();
			newFC.setCostPerMeter(fc.getCostPerMeter());
			newFC.setFabricName(fc.getFabricName());
			newFC.setOrderId(orderId);
			newFC.setPrice(fc.getPrice());
			newFC.setTearPerMeter(fc.getTearPerMeter());
			fabricCostDAO.save(newFC);
		}
		List<AccessoryCost> accessoryCosts = accessoryCostDAO
				.findByOrderId(source);
		for (AccessoryCost ac : accessoryCosts) {
			AccessoryCost newAC = new AccessoryCost();
			newAC.setAccessoryName(ac.getAccessoryName());
			newAC.setCostPerPiece(ac.getCostPerPiece());
			newAC.setOrderId(orderId);
			newAC.setPrice(ac.getPrice());
			newAC.setTearPerPiece(ac.getTearPerPiece());
			accessoryCostDAO.save(newAC);
		}

		// 图片
		Order sourceOrder = orderDAO.findById(source);
		/*
		 * System.out.println("--------------"); HttpSession httpSession =
		 * request.getSession(); ServletContext servletContext =
		 * httpSession.getServletContext(); String curPath =
		 * servletContext.getRealPath("/");
		 */
		// String curPath =
		// request.getSession().getServletContext().getRealPath("/");
		// String fatherPath = new File(curPath).getParent();
		String rePath = new File(request.getSession().getServletContext()
				.getRealPath("/")).getParent();
		String sourcePicture = rePath + sourceOrder.getSampleClothesPicture();
		System.out.println(sourcePicture);
		String targetPicture = rePath + UPLOAD_DIR_SAMPLE + orderId;

		File newSamplePic = FileOperateUtil.CopyAndPaste(sourcePicture,
				targetPicture);
		File newRefPic = FileOperateUtil.CopyAndPaste(
				rePath + sourceOrder.getReferencePicture(), rePath
						+ UPLOAD_DIR_REFERENCE + orderId);
		order.setSampleClothesPicture(newSamplePic
				.getAbsolutePath()
				.substring(rePath.length(),
						newSamplePic.getAbsolutePath().length())
				.replace('\\', '/'));
		order.setReferencePicture(newRefPic
				.getAbsolutePath()
				.substring(rePath.length(),
						newRefPic.getAbsolutePath().length())
				.replace('\\', '/'));

		// 启动流程
		Map<String, Object> params = getParams(order);
		params.put(RESULT_REORDER, true);
		params.put(DesignServiceImpl.RESULT_NEED_CRAFT, isNeedCraft);// 翻单是否需要工艺

		String processId = mainProcessService.startWorkflow(params);
		order.setProcessId(processId);
		orderDAO.attachDirty(order);
	}

	@Override
	public List<Map<String, Object>> getModifyOrderList(Integer accountId) {
		List<Map<String, Object>> temp = service.getOrderList(accountId + "",
				TASK_MODIFY_ORDER);
		return temp;
	}

	@Override
	public List<Map<String, Object>> getSearchModifyOrderList(Integer userId,
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds) {
		List<Map<String, Object>> temp = service.getSearchOrderList(
				userId + "", ordernumber, customername, stylename, startdate,
				enddate, employeeIds, TASK_MODIFY_ORDER);
		return temp;
	}

	@Override
	public Map<String, Object> getModifyOrderDetail(int accountId, int id) {
		return service
				.getBasicOrderModel(accountId + "", TASK_MODIFY_ORDER, id);

	}

	@Override
	public void modifyOrderSubmit(Order order, List<Fabric> fabrics,
			List<Accessory> accessorys, Logistics logistics,
			List<Produce> produces, List<Produce> sample_produces,
			List<VersionData> versions, DesignCad cad, boolean editok,
			String taskId, Integer accountId) {
		// 添加订单信息
		orderDAO.merge(order);
		Integer orderId = order.getOrderId();
		// 添加面料信息
		fabricDAO.deleteByProperty("orderId", orderId);
		for (Fabric fabric : fabrics) {
			fabric.setOrderId(orderId);
			fabricDAO.save(fabric);
		}
		// 添加辅料信息
		accessoryDAO.deleteByProperty("orderId", orderId);
		for (Accessory accessory : accessorys) {
			accessory.setOrderId(orderId);
			accessoryDAO.save(accessory);
		}
		// 添加大货加工单信息
		produceDAO.deleteProduceByProperty("oid", orderId);
		for (Produce produce : produces) {
			produce.setOid(orderId);
			produceDAO.save(produce);
		}
		// 添加样衣加工单信息
		produceDAO.deleteSampleProduceByProperty("oid", orderId);
		for (Produce produce : sample_produces) {
			produce.setOid(orderId);
			produceDAO.save(produce);
		}
		// 添加版型数据
		versionDataDAO.deleteByProperty("orderId", orderId);
		for (VersionData version : versions) {
			version.setOrderId(orderId);
			versionDataDAO.save(version);
		}
		// 添加物流信息
		logistics.setOrderId(orderId);
		logisticsDAO.merge(logistics);

		// cad
		cadDAO.merge(cad);

		// 启动流程
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(RESULT_MODIFY_ORDER, editok);
		try {
			mainProcessService.completeTask(taskId, accountId + "",  params);
			if (editok == false) {// 如果editok的的值为false，即为未收取到样衣，流程会异常终止，将orderState设置为1
				order.setOrderState("1");
				orderDAO.merge(order);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Product> getProductList(int orderId, String productAskAmount,
			String productColor, String productStyle) {

		String[] amountList = productAskAmount.split(",");
		String[] colorList = productColor.split(",");
		String[] styleList = productStyle.split(",");
		List<Product> productList = new ArrayList<Product>();
		for (int i = 0; i < amountList.length; i++) {
			Product product = new Product();
			product.setOrderId(orderId);
			product.setAskAmount(Integer.parseInt(amountList[i]));
			product.setColor(colorList[i]);
			product.setStyle(styleList[i]);
			product.setProduceAmount(0);
			product.setQualifiedAmount(0);
			productList.add(product);
		}

		return productList;
	}

	@Override
	public boolean confirmProduceOrderSubmit(String actorId, int orderId,
			String taskId, String processId, boolean comfirmworksheet,
			List<Produce> productList) {
		// 需要获取task中的数据
		int orderId_process = mainProcessService.getOrderIdInProcess(processId);

		if (orderId == orderId_process) {
			// 如果通过，创建合同加工单
			int ask_amount = 0;
			produceDAO.deleteProduceByProperty("oid", orderId);
			if (comfirmworksheet) {
				for (Produce produce : productList) {
					produce.setOid(orderId);
					produceDAO.save(produce);
					ask_amount += produce.getProduceAmount();
				}
				Order order = orderDAO.findById(orderId);
				order.setAskAmount(ask_amount);
				orderDAO.merge(order);
			}
			// 修改流程参数
			Map<String, Object> data = new HashMap<>();
			data.put(RESULT_CONFIRM_PRODUCE_ORDER_CONTRACT, comfirmworksheet);
			// 直接进入到下一个流程时
			try {
				mainProcessService.completeTask(taskId, actorId, data);
				if (comfirmworksheet == false) {// 如果result的的值为false，即为确认加工单并签订合同失败，流程会异常终止，将orderState设置为1
					Order order = orderDAO.findById(orderId);
					order.setOrderState("1");
					orderDAO.merge(order);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return true;
		}

		return false;

	}


	@Override
	public OrderInfo getOrderInfo(Integer orderId, String taskId) {
		Order order = orderDAO.findById(orderId);
		OrderInfo orderInfo = new OrderInfo(order, taskId);
		return orderInfo;
	}

	@Override
	public List<Map<String, Object>> getModifyProductList(Integer userId) {
		List<Map<String, Object>> temp = service.getOrderList(userId + "",
				TASK_MODIFY_PRODUCE_ORDER);
		return temp;
	}

	@Override
	public List<Map<String, Object>> getSearchModifyProductList(Integer userId,
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds) {
		List<Map<String, Object>> temp = service.getSearchOrderList(
				userId + "", ordernumber, customername, stylename, startdate,
				enddate, employeeIds, TASK_MODIFY_PRODUCE_ORDER);
		return temp;
	}

	@Override
	public boolean modifyProductSubmit(String userId, int id, String taskId,
			String processId, boolean editworksheetok, List<Produce> productList) {
		List<Task> tasks = mainProcessService.getModifyProduceOrderTasks(userId);
		for (Task task : tasks) {
			if (task.getId() == taskId
					&& (mainProcessService.getOrderIdInProcess(processId) == id)) {
				produceDAO.deleteProduceByProperty("oid", id);
				if (editworksheetok) {
					for (Produce produce : productList) {
						produce.setOid(id);
						produceDAO.save(produce);
					}
				}
				// 修改流程参数
				Map<String, Object> data = new HashMap<>();
				data.put(RESULT_MODIFY_PRODUCE_ORDER, editworksheetok);
				// 直接进入到下一个流程时
				try {
					mainProcessService.completeTask(taskId, userId, data);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	// ==========================报价商定=======================
	@Override
	public List<Map<String, Object>> getConfirmQuoteList(String actorId) {
		List<Map<String, Object>> temp = service.getOrderList(actorId,
				TASK_CONFIRM_QUOTE);
		return temp;
	}

	@Override
	public List<Map<String, Object>> getSearchConfirmQuoteList(String string,
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds) {
		List<Map<String, Object>> temp = service.getSearchOrderList(string,
				ordernumber, customername, stylename, startdate, enddate,
				employeeIds, TASK_CONFIRM_QUOTE);
		return temp;
	}

	@Override
	public OrderInfo getConfirmQuoteDetail(String arctorId, Integer orderId) {
		Task task = mainProcessService.getTaskOfUserByTaskNameWithSpecificOrderId(arctorId, TASK_CONFIRM_QUOTE, orderId);
		OrderInfo model = new OrderInfo();
		model.setOrder(orderDAO.findById(orderId));
		model.setTask(task);
		return model;
	}

	@Override
	public boolean confirmQuoteSubmit(String actorId, String taskId,
			String result) {
		Map<String, Object> data = new HashMap<String, Object>();

		data.put(RESULT_QUOTE, Integer.parseInt(result));
		try {
			mainProcessService.completeTask(taskId, actorId, data);
			return true;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean confirmQuoteSubmit(String actorId, String taskId,
			int orderId, String result, String url, String moneyremark) {
		// result为0，表示有上传样衣制作金的截图
		// result为1，表示修改报价
		// result为2，表示取消订单
		Order order = orderDAO.findById(orderId);
		if (Integer.parseInt(result) == 0) {
			order.setConfirmSampleMoneyFile(url);
			order.setMoneyremark(moneyremark);
			orderDAO.attachDirty(order);
		}
		Map<String, Object> data = new HashMap<String, Object>();
		data.put(RESULT_QUOTE, Integer.parseInt(result));
		try {
			mainProcessService.completeTask(taskId, actorId, data);
			if (result.equals("2")) {// 如果result的的值为2，即为取消订单，流程会异常终止，将orderState设置为1
				order.setOrderState("1");
				orderDAO.attachDirty(order);
			}
			return true;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public List<Map<String, Object>> getModifyQuoteList(Integer userId) {
		List<Map<String, Object>> temp = service.getOrderList(userId + "",
				TASK_MODIFY_QUOTE);
		return temp;
	}

	@Override
	public List<Map<String, Object>> getSearchModifyQuoteList(Integer userId,
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds) {
		List<Map<String, Object>> temp = service.getSearchOrderList(
				userId + "", ordernumber, customername, stylename, startdate,
				enddate, employeeIds, TASK_MODIFY_QUOTE);
		return temp;
	}

	public Map<String, Object> getModifyQuoteDetail(int orderId, int accountId) {
		return service.getBasicOrderModelWithQuote(accountId + "",
				TASK_MODIFY_QUOTE, orderId);
	}

	@Override
	public Map<String, Object> getModifyProductDetail(int orderId,
			Integer accountId) {
		return service.getBasicOrderModelWithQuote(accountId + "",
				TASK_MODIFY_PRODUCE_ORDER, orderId);
	}

	// ==========================签订合同=======================
	@Override
	public List<Map<String, Object>> getSignContractList(String actorId) {
		List<Map<String, Object>> temp = service.getOrderList(actorId,
				TASK_SIGN_CONTRACT);
		return temp;
	}

	@Override
	public List<Map<String, Object>> getSearchSignContractList(String actorId,
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds) {
		List<Map<String, Object>> temp = service.getSearchOrderList(actorId,
				ordernumber, customername, stylename, startdate, enddate,
				employeeIds, TASK_SIGN_CONTRACT);
		return temp;
	}

	@Override
	public Map<String, Object> getSignContractDetail(String actorId,
			Integer orderId) {
		return service.getBasicOrderModelWithQuote(actorId, TASK_SIGN_CONTRACT,
				orderId);

	}

	// ==========================取得催尾款列表=======================
	@Override
	public List<Map<String, Object>> getPushRestOrderList(String userId) {
		List<Map<String, Object>> model = service.getOrderList(userId,
				TASK_PUSH_REST);
		return model;
	}

	@Override
	public List<Map<String, Object>> getSearchPushRestOrderList(String userId,
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds) {
		List<Map<String, Object>> temp = service.getSearchOrderList(userId,
				ordernumber, customername, stylename, startdate, enddate,
				employeeIds, TASK_PUSH_REST);
		return temp;
	}

	// ==========================取得催尾款订单=======================
	@Override
	public Map<String, Object> getPushRestOrderDetail(String userId, int orderId) {
		Map<String, Object> model = service.getBasicOrderModelWithQuote(userId,
				TASK_PUSH_REST, orderId);
		Order order = (Order) model.get("order");
		Quote quote = (Quote) model.get("quote");
		Float price = quote.getOuterPrice();
		model.put("price", price);
		// Produce p=new Produce();
		// p.setOid(orderId);
		// p.setType(Produce.TYPE_QUALIFIED);
		// List<Produce> list = produceDAO.findByExample(p);
		// Integer amount = 0;
		// for (Produce produce : list) {
		// amount += produce.getProduceAmount();
		// }

		// 计算质检合格总数，即实际的大货总数
		int amount = 0;
		List<CheckRecord> list = checkRecordDAO.findByOrderId(orderId);
		for (CheckRecord cr : list) {
			amount += cr.getQualifiedAmount();
		}
		List<DeliveryRecord> deliveryRecord = deliveryRecordDAO
				.findSampleRecordByOrderId(orderId);
		model.put("deliveryRecord", deliveryRecord);

		model.put("number", amount);
		// model.put("total", order.getTotalMoney() * 0.7);
		model.put("taskName", "催尾款");
		model.put("tabName", "大货尾款");
		model.put("type", "大货尾款");
		model.put("url", "/market/getPushRestOrderSubmit.do");

		// model.put("moneyName", "70%尾款");
		model.put("moneyName", "大货尾款");
		Money money = new Money();
		money.setOrderId(orderId);
		money.setMoneyType("大货定金");
		model.put("deposit", moneyDAO.findByExample(money).get(0)
				.getMoneyAmount());
		Float samplePrice = (float) 0;
		if (order.getStyleSeason().equals("春夏")) {
			samplePrice = (float) 200;
			model.put("samplePrice", samplePrice);
		} else {
			samplePrice = (float) 400;
			model.put("samplePrice", samplePrice);
		}
		return model;
	}

	@Override
	public boolean getPushRestOrderSubmit(String actorId, String taskId,
			boolean result) {
		Map<String, Object> data = new HashMap<>();
		data.put(RESULT_PUSH_RESTMONEY, result);
		try {
			mainProcessService.completeTask(taskId, actorId, data);
			return true;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public boolean getPushRestOrderSubmit(String actorId, String taskId,
			boolean result, String orderId_string) {
		Order order = orderDAO.findById(Integer.parseInt(orderId_string));
		Map<String, Object> data = new HashMap<>();
		data.put(RESULT_PUSH_RESTMONEY, result);
		try {
			mainProcessService.completeTask(taskId, actorId, data);
			if (result == false) {// 如果result的的值为false，即为催尾款失败，流程会异常终止，将orderState设置为1
				order.setOrderState("1");
				orderDAO.attachDirty(order);
			}
			return true;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public List<Map<String, Object>> getMergeQuoteList(Integer accountId) {
		List<Map<String, Object>> temp = service.getOrderList(accountId + "",
				TASK_MERGE_QUOTE);
		return temp;
	}

	@Override
	public List<Map<String, Object>> getSearchMergeQuoteList(Integer userId,
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds) {
		List<Map<String, Object>> temp = service.getSearchOrderList(
				userId + "", ordernumber, customername, stylename, startdate,
				enddate, employeeIds, TASK_MERGE_QUOTE);
		return temp;

	}

	@Override
	public void mergeQuoteSubmit(int accountId, Quote q, int id, String taskId,
			String processId) {
		// 需要获取task中的数据
		int orderId_process = mainProcessService.getOrderIdInProcess(processId);
		Order order = orderDAO.findById(id);
		// String orderSource = order.getOrderSource();
		//
		Short isHaoduoyi = order.getIsHaoDuoYi();
		short ishaoduoyi = isHaoduoyi.shortValue();
		boolean isHaoDuoYi2 = false;
		if (ishaoduoyi == 1)
			isHaoDuoYi2 = true;

		// boolean isHaoDuoYi = (orderSource.equals("好多衣"))?true:false;
		if (id == orderId_process) {
			Map<String, Object> data = new HashMap<>();
			data.put(RESULT_IS_HAODUOYI, isHaoDuoYi2);
			quoteDAO.merge(q);
			try {
				mainProcessService.completeTask(taskId, accountId+"", data);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public List<Map<String, Object>> getVerifyQuoteList(Integer accountId) {
		List<Map<String, Object>> temp = service.getOrderList(
				ACTOR_MARKET_MANAGER, TASK_VERIFY_QUOTE);
		return temp;
	}

	@Override
	public List<Map<String, Object>> getSearchVerifyQuoteList(Integer userId,
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds) {
		List<Map<String, Object>> temp = service.getSearchOrderList(
				ACTOR_MARKET_MANAGER, ordernumber, customername, stylename,
				startdate, enddate, employeeIds, TASK_VERIFY_QUOTE);
		return temp;
	}

	@Override
	public void verifyQuoteSubmit(Quote q, int id, String taskId,
			String processId) {
		int orderId_process = mainProcessService.getOrderIdInProcess(processId);
		if (id == orderId_process) {
			quoteDAO.merge(q);
			try {
				mainProcessService.completeTask(taskId, ACTOR_MARKET_MANAGER, null);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void verifyQuoteSubmit(Quote quote, int id, String taskId,
			String processId, boolean result, String comment) {
		int orderId_process = mainProcessService.getOrderIdInProcess(processId);
		if (id == orderId_process) {
			quoteDAO.merge(quote);
			Map<String, Object> data = new HashMap<>();
			data.put(RESULT_VERIFY_QUOTE, result);
			data.put(VERIFY_QUOTE_COMMENT, comment);
			try {
				mainProcessService.completeTask(taskId, ACTOR_MARKET_MANAGER, data);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void signContractSubmit(String actorId, String taskId, int orderId,
			double discount, double total, String url) {
		Order order = orderDAO.findById(orderId);
		order.setDiscount(discount);
		order.setTotalMoney(total);
		order.setContractFile(url);
		orderDAO.merge(order);
		// Map<String, Object> data = new HashMap<>();
		// try {
		// jbpmAPIUtil.completeTask(taskId, data, actorId);
		// return true;
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// return false;
		// }
	}

	@Override
	public void signContractSubmit(String actorId, String taskId, int orderId,
			double discount, double total, String url,
			String confirmDepositFileUrl, String moneyremark) {
		Order order = orderDAO.findById(orderId);
		order.setDiscount(discount);
		order.setTotalMoney(total);
		order.setContractFile(url);
		order.setConfirmDepositFile(confirmDepositFileUrl);
		order.setMoneyremark(moneyremark);
		orderDAO.merge(order);
		// Map<String, Object> data = new HashMap<>();
		// try {
		// jbpmAPIUtil.completeTask(taskId, data, actorId);
		// return true;
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// return false;
		// }
	}

	@Override
	public void signConfirmFinalPaymentFileSubmit(int orderId,
			String confirmFinalPaymentFileUrl, String moneyremark) {
		Order order = orderDAO.findById(orderId);
		order.setMoneyremark(moneyremark);
		order.setConfirmFinalPaymentFile(confirmFinalPaymentFileUrl);
		orderDAO.merge(order);

	}

	@Override
	public Map<String, Object> getMergeQuoteDetail(Integer userId, int orderId) {
		Map<String, Object> model = service.getBasicOrderModelWithQuote(userId
				+ "", TASK_MERGE_QUOTE, orderId);
		// 工艺报价信息
		Craft craft = craftDAO.findByOrderId(orderId).get(0);
		model.put("craft", craft);

		return model;
	}

	@Override
	public Map<String, Object> getVerifyQuoteDetail(Integer userId, int orderId) {
		return service.getBasicOrderModelWithQuote(ACTOR_MARKET_MANAGER,
				TASK_VERIFY_QUOTE, orderId);
	}

	@Override
	public Map<String, Object> getConfirmQuoteDetail(Integer userId, int orderId) {
		return service.getBasicOrderModelWithQuote(userId + "",
				TASK_CONFIRM_QUOTE, orderId);
	}

	@Override
	public void modifyQuoteSubmit(Quote q, int id, String taskId,
			String processId, Integer userId) {
		int orderId_process = mainProcessService.getOrderIdInProcess(processId);
		if (id == orderId_process) {
			quoteDAO.merge(q);
			try {
				mainProcessService.completeTask(taskId, userId+"", null);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<Map<String, Object>> getConfirmProductList(String actorId) {
		List<Map<String, Object>> temp = service.getOrderList(actorId,
				TASK_CONFIRM_PRODUCE_ORDER);
		return temp;
	}

	@Override
	public List<Map<String, Object>> getSearchConfirmProductList(
			String actorId, String ordernumber, String customername,
			String stylename, String startdate, String enddate,
			Integer[] employeeIds) {
		List<Map<String, Object>> temp = service.getSearchOrderList(actorId,
				ordernumber, customername, stylename, startdate, enddate,
				employeeIds, TASK_CONFIRM_PRODUCE_ORDER);
		return temp;
	}

	@Override
	public Map<String, Object> getConfirmProductDetail(Integer userId,
			int orderId) {
		return service.getBasicOrderModelWithQuote(userId + "",
				TASK_CONFIRM_PRODUCE_ORDER, orderId);
	}

	@Override
	public List<Map<String, Object>> getOrderList(Integer page) {
		// List<Order> orders = orderDAO.findByEmployeeId(employeeId);
		List<Order> orders = orderDAO.getOrderList(page);
		Integer pages = orderDAO.getPageNumber();
		List<Map<String, Object>> list = new ArrayList<>();
		for (Order order : orders) {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("order", order);
			model.put("employee", employeeDAO.findById(order.getEmployeeId()));
			model.put("orderId", service.getOrderId(order));
			model.put("pages", pages);
			list.add(model);
		}
		return list;
	}

	public ArrayList<String> getProcessStateName(final Integer orderId) {
		String processId = orderDAO.findById(orderId).getProcessId();
		return (ArrayList<String>) mainProcessService.getProcessStateNames(processId);

	}

	@Override
	public List<Map<String, Object>> getOrders() {
		List<Order> orders = orderDAO.getOrders();
		Integer pages = orderDAO.getPageNumber();
		List<Map<String, Object>> list = new ArrayList<>();
		for (Order order : orders) {
			// ArrayList<String> orderProcessStateNames =
			// getProcessStateName(order.getOrderId());
			ArrayList<String> orderProcessStateNames = financeService
					.getProcessStateName(order.getOrderId());
			// System.out.println("取到的订单当前状态为："+orderProcessStateNames.get(0)+"数组大小："+orderProcessStateNames.size());
			if (orderProcessStateNames.size() > 0) {
				order.setOrderProcessStateName(orderProcessStateNames.get(0));
			} else {
				order.setOrderProcessStateName("");
			}
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("order", order);
			model.put("employee", employeeDAO.findById(order.getEmployeeId()));
			model.put("orderId", service.getOrderId(order));
			model.put("pages", pages);
			list.add(model);
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> getOrders(String userRole, Integer userId) {
		List<Order> orders = null;

		if ("CUSTOMER".equals(userRole)) {
			orders = orderDAO.findByCustomerId(userId);
		} else if ("marketStaff".equals(userRole)) {
			orders = orderDAO.findByEmployeeId(userId);
		}

		Integer pages = orderDAO.getPageNumber();
		List<Map<String, Object>> list = new ArrayList<>();
		for (Order order : orders) {
			ArrayList<String> orderProcessStateNames = getProcessStateName(order
					.getOrderId());
			if (orderProcessStateNames.size() > 0) {
				order.setOrderProcessStateName(orderProcessStateNames.get(0));
			} else {
				order.setOrderProcessStateName("");
			}

			Map<String, Object> model = new HashMap<String, Object>();
			model.put("order", order);
			model.put("employee", employeeDAO.findById(order.getEmployeeId()));
			model.put("orderId", service.getOrderId(order));
			model.put("pages", pages);
			list.add(model);
		}
		return list;
	}

	// @Transactional(rollbackFor = Exception.class)
	@Override
	public List<Map<String, Object>> getOrdersDoing() {
		List<Order> orders = orderDAO.getOrdersDoing();
		List<Map<String, Object>> list = new ArrayList<>();
		for (Order order : orders) {
			ArrayList<String> orderProcessStateNames = getProcessStateName(order
					.getOrderId());
			if (orderProcessStateNames.size() > 0) {
				order.setOrderProcessStateName(orderProcessStateNames.get(0));
			} else {
				order.setOrderProcessStateName("");
			}
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("order", order);
			model.put("employee", employeeDAO.findById(order.getEmployeeId()));
			model.put("orderId", service.getOrderId(order));
			list.add(model);
		}
		return list;
	}

	// @Transactional(rollbackFor = Exception.class)
	@Override
	public List<Map<String, Object>> getOrdersDoing(String userRole,
			Integer userId) {
		Order orderExample = new Order();
		List<Order> orders = new ArrayList<Order>();
		orderExample.setOrderState("A"); // 正在进行中的订单

		if ("CUSTOMER".equals(userRole)) {
			orderExample.setCustomerId(userId);
			orders = orderDAO.findOrdersDoingByCustomer(orderExample);
		} else if ("marketStaff".equals(userRole)) {
			orderExample.setEmployeeId(userId);
			orders = orderDAO.findOrdersDoingByEmployee(orderExample);
		}

		// List<Order> orders = orderDAO.findByExample(orderExample);
		List<Map<String, Object>> list = new ArrayList<>();
		for (Order order : orders) {
			ArrayList<String> orderProcessStateNames = getProcessStateName(order
					.getOrderId());
			if (orderProcessStateNames.size() > 0) {
				order.setOrderProcessStateName(orderProcessStateNames.get(0));
			} else {
				order.setOrderProcessStateName("");
			}
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("order", order);
			model.put("employee", employeeDAO.findById(order.getEmployeeId()));
			model.put("orderId", service.getOrderId(order));
			list.add(model);
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> getOrdersDone() {
		List<Order> orders = orderDAO.getOrdersDone();
		List<Map<String, Object>> list = new ArrayList<>();
		for (Order order : orders) {
			ArrayList<String> orderProcessStateNames = getProcessStateName(order
					.getOrderId());
			if (orderProcessStateNames.size() > 0) {
				order.setOrderProcessStateName(orderProcessStateNames.get(0));
			} else {
				order.setOrderProcessStateName("");
			}
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("order", order);
			model.put("employee", employeeDAO.findById(order.getEmployeeId()));
			model.put("orderId", service.getOrderId(order));
			list.add(model);
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> getOrdersDone(String userRole,
			Integer userId) {
		Order orderExample = new Order();
		orderExample.setOrderState("Done"); // 已经完成的订单
		List<Order> orders = new ArrayList<Order>();

		if ("CUSTOMER".equals(userRole)) {
			orderExample.setCustomerId(userId);
			orders = orderDAO.findOrdersDoneByCustomer(orderExample);

		} else if ("marketStaff".equals(userRole)) {
			orderExample.setEmployeeId(userId);
			orders = orderDAO.findOrdersDoneByEmployee(orderExample);

		}
		// List<Order> orders = orderDAO.findByExample(orderExample);
		List<Map<String, Object>> list = new ArrayList<>();
		for (Order order : orders) {
			ArrayList<String> orderProcessStateNames = getProcessStateName(order
					.getOrderId());
			if (orderProcessStateNames.size() > 0) {
				order.setOrderProcessStateName(orderProcessStateNames.get(0));
			} else {
				order.setOrderProcessStateName("");
			}
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("order", order);
			model.put("employee", employeeDAO.findById(order.getEmployeeId()));
			model.put("orderId", service.getOrderId(order));
			list.add(model);
		}
		return list;
	}

	@Override
	public Map<String, Object> getOrderDetail(Integer orderId) {
		Map<String, Object> model = new HashMap<String, Object>();
		Order order = orderDAO.findById(orderId);
		model.put("order", order);
		model.put("orderId", service.getOrderId(order));
		model.put("employee", employeeDAO.findById(order.getEmployeeId()));
		model.put("logistics", logisticsDAO.findById(orderId));
		model.put("fabrics", fabricDAO.findByOrderId(orderId));
		model.put("accessorys", accessoryDAO.findByOrderId(orderId));

		Produce produce = new Produce();
		produce.setOid(orderId);
		produce.setType(Produce.TYPE_SAMPLE_PRODUCE);
		model.put("sample", produceDAO.findByExample(produce));

		produce.setType(Produce.TYPE_PRODUCE);
		model.put("produce", produceDAO.findByExample(produce));

		model.put("versions", versionDataDAO.findByOrderId(orderId));

		Quote quote = quoteDAO.findById(orderId);

		model.put("quote", quote);
		List<FabricCost> fabricCosts = fabricCostDAO.findByOrderId(orderId);
		model.put("fabricCosts", fabricCosts);
		List<AccessoryCost> accessoryCosts = accessoryCostDAO
				.findByOrderId(orderId);
		model.put("accessoryCosts", accessoryCosts);
		model.put("repairRecord", checkRecordDAO.findByOrderId(orderId));
		List<DesignCad> cads = cadDAO.findByOrderId(orderId);
		if (cads != null && cads.size() != 0) {
			model.put("designCad", cads.get(0));
		}
		return model;
	}

	@Override
	public List<Map<String, Object>> getAddMoreOrderList(int customerId) {

		System.out.println("客户的ID是：" + customerId);
		// Order o = new Order();
		// o.setCustomerId(customerId);
		// List<Order> orderList = orderDAO.findByExample(o);
		List<Order> orderList = orderDAO.findResultsByCustomerId(customerId);
		List<Map<String, Object>> list = new ArrayList<>();
		System.out.println("翻单数量：" + orderList.size());
		for (Order order : orderList) {
			System.out.println("翻单数量客户姓名：" + order.getCustomerName());
			ArrayList<String> orderProcessStateNames = getProcessStateName(order
					.getOrderId());
			if (orderProcessStateNames.size() > 0) {
				order.setOrderProcessStateName(orderProcessStateNames.get(0));
			} else {
				order.setOrderProcessStateName("");
			}
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("order", order);
			model.put("employee", employeeDAO.findById(order.getEmployeeId()));
			model.put("taskTime", order.getOrderTime());
			model.put("orderId", service.getOrderId(order));
			list.add(model);
		}
		return list;
	}

	/**
	 * 根据条件查询翻单列表 hcj
	 */
	@Override
	public List<Map<String, Object>> getSearchAddMoreOrderList(
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds) {
		// Order o = new Order();
		// o.setCustomerId(customerId);
		// List<Order> orderList = orderDAO.findByExample(o);
		List<Order> orderList = orderDAO
				.getSearchOrderDoneList(ordernumber, customername, stylename,
						startdate, enddate, employeeIds, "", 0);
		List<Map<String, Object>> list = new ArrayList<>();
		System.out.println("翻单数量：" + orderList.size());
		for (Order order : orderList) {
			System.out.println("翻单数量客户姓名：" + order.getCustomerName());
			ArrayList<String> orderProcessStateNames = getProcessStateName(order
					.getOrderId());
			if (orderProcessStateNames.size() > 0) {
				order.setOrderProcessStateName(orderProcessStateNames.get(0));
			} else {
				order.setOrderProcessStateName("");
			}
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("order", order);
			model.put("employee", employeeDAO.findById(order.getEmployeeId()));
			model.put("taskTime", order.getOrderTime());
			model.put("orderId", service.getOrderId(order));
			list.add(model);
		}
		return list;
	}

	@Override
	public Map<String, Object> getAddMoreOrderDetail(int orderId) {
		Map<String, Object> model = new HashMap<String, Object>();
		Order order = orderDAO.findById(orderId);
		model.put("order", order);
		if (order.getIsHaoDuoYi() == 1) {
			return null;
		}
		model.put("employee", employeeDAO.findById(order.getEmployeeId()));
		model.put("logistics", logisticsDAO.findById(orderId));// 物流信息
		model.put("fabrics", fabricDAO.findByOrderId(orderId));// 面料信息
		model.put("accessorys", accessoryDAO.findByOrderId(orderId));// 辅料信息
		model.put("designCad", cadDAO.findByOrderId(orderId));// 制版信息
		model.put("orderId", service.getOrderId(order));
		model.put("craft", craftDAO.findByOrderId(orderId).get(0));// 工艺信息

		Produce produce = new Produce();
		produce.setOid(orderId);
		produce.setType(Produce.TYPE_SAMPLE_PRODUCE);
		model.put("sample", produceDAO.findByExample(produce));// 样衣生产信息

		produce.setType(Produce.TYPE_PRODUCE);
		model.put("produce", produceDAO.findByExample(produce));// 大货生产信息

		model.put("versions", versionDataDAO.findByOrderId(orderId));// 版型信息

		Quote quote = quoteDAO.findById(orderId);
		model.put("quote", quote);// 报价信息
		List<FabricCost> fabricCosts = fabricCostDAO.findByOrderId(orderId);
		model.put("fabricCosts", fabricCosts);// 面料报价
		List<AccessoryCost> accessoryCosts = accessoryCostDAO
				.findByOrderId(orderId);
		model.put("accessoryCosts", accessoryCosts);// 辅料报价

		return model;
	}

	public static Object copy(Object object) throws Exception {
		Class<?> classType = object.getClass();
		Object objectCopy = classType.getConstructor(new Class[] {})
				.newInstance(new Object[] {});
		Field fields[] = classType.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			String fieldName = field.getName();
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String getMethodName = "get" + firstLetter + fieldName.substring(1);
			String setMethodName = "set" + firstLetter + fieldName.substring(1);
			Method getMethod = classType.getMethod(getMethodName,
					new Class[] {});
			Method setMethod = classType.getMethod(setMethodName,
					new Class[] { field.getType() });
			Object value = getMethod.invoke(object, new Object[] {});
			setMethod.invoke(objectCopy, new Object[] { value });
		}
		return objectCopy;
	}

	/*
	 * @Override public List<QuoteConfirmTaskSummary>
	 * getQuoteModifyTaskSummaryList( Integer employeeId) { Auto-generated
	 * method stub List<TaskSummary> tasks =
	 * jbpmAPIUtil.getAssignedTasksByTaskname( "SHICHANGZHUANYUAN",
	 * "edit_quoteorder"); List<QuoteConfirmTaskSummary> taskSummarys = new
	 * ArrayList<>(); for (TaskSummary task : tasks) { if
	 * (getVariable("employeeId", task).equals(employeeId)) { Integer orderId =
	 * (Integer) getVariable("orderId", task); QuoteConfirmTaskSummary summary =
	 * QuoteConfirmTaskSummary .getInstance(orderDAO.findById(orderId), (Quote)
	 * quoteDAO.findById(orderId), task.getId()); taskSummarys.add(summary); } }
	 * return taskSummarys; }
	 */

	@Override
	public List<Map<String, Object>> getSearchOrderList(String ordernumber,
			String customername, String stylename, String startdate,
			String enddate, Integer[] employeeIds, String userRole,
			Integer userId) {
		List<Order> orders = orderDAO.getSearchOrderList(ordernumber,
				customername, stylename, startdate, enddate, employeeIds,
				userRole, userId);
		int orderslength = orders.size();
		Integer pages = (int) Math.ceil((double) orderslength / 10);
		List<Map<String, Object>> list = new ArrayList<>();
		for (Order order : orders) {
			ArrayList<String> orderProcessStateNames = getProcessStateName(order
					.getOrderId());
			if (orderProcessStateNames.size() > 0) {
				order.setOrderProcessStateName(orderProcessStateNames.get(0));
			} else {
				order.setOrderProcessStateName("");
			}
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("order", order);
			model.put("employee", employeeDAO.findById(order.getEmployeeId()));
			model.put("orderId", service.getOrderId(order));
			model.put("pages", pages);
			model.put("taskTime", getTaskTime(order.getOrderTime()));
			list.add(model);
		}
		return list;
	}

	public String getTaskTime(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(date);
	}

	@Override
	public List<Map<String, Object>> getSearchOrdersDoing(String ordernumber,
			String orderProcessStateName, String customername,
			String stylename, String startdate, String enddate,
			Integer[] employeeIds, String userRole, Integer userId) {
		List<Order> orders = orderDAO.getSearchOrderDoingList(ordernumber,
				orderProcessStateName, customername, stylename, startdate,
				enddate, employeeIds, userRole, userId);
		List<Map<String, Object>> list = new ArrayList<>();
		for (Order order : orders) {
			ArrayList<String> orderProcessStateNames = getProcessStateName(order
					.getOrderId());
			if (orderProcessStateNames.size() > 0) {
				order.setOrderProcessStateName(orderProcessStateNames.get(0));
			} else {
				order.setOrderProcessStateName("");
			}
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("order", order);
			model.put("employee", employeeDAO.findById(order.getEmployeeId()));
			model.put("orderId", service.getOrderId(order));
			model.put("taskTime", getTaskTime(order.getOrderTime()));
			list.add(model);
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> getSearchOrdersDone(String ordernumber,
			String customername, String stylename, String startdate,
			String enddate, Integer[] employeeIds, String userRole,
			Integer userId) {
		List<Order> orders = orderDAO.getSearchOrderDoneList(ordernumber,
				customername, stylename, startdate, enddate, employeeIds,
				userRole, userId);
		List<Map<String, Object>> list = new ArrayList<>();
		for (Order order : orders) {
			ArrayList<String> orderProcessStateNames = getProcessStateName(order
					.getOrderId());
			if (orderProcessStateNames.size() > 0) {
				order.setOrderProcessStateName(orderProcessStateNames.get(0));
			} else {
				order.setOrderProcessStateName("");
			}
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("order", order);
			model.put("employee", employeeDAO.findById(order.getEmployeeId()));
			model.put("orderId", service.getOrderId(order));
			model.put("taskTime", getTaskTime(order.getOrderTime()));
			list.add(model);
		}
		return list;
	}

	@Override
	public void sendOrderInfoViaEmail(Order order, Customer customer) {
		if (StringUtil.isEmpty(customer.getEmail()))
			return;

		String emailTitle = "智造链 - 下单信息";
		String emailContent = "尊敬的客户，您已成功下单，以下是您具体的订单信息：<br/>"
				+ "<table border='1px' bordercolor='#000000' cellspacing='0px' style='border-collapse:collapse'>"
				+ "<thead>" + "<tr>" + "<th>订单号</th>" + "<th>款型</th>"
				+ "<th>件数</th>" + "<th>预期交付日期</th>" + "</tr>" + "</thead>"
				+ "<tbody>" + "<tr>" + "<td>" + service.getOrderId(order)
				+ "</td>" + "<td>" + order.getStyleName() + "</td>" + "<td>"
				+ order.getAskAmount() + "</td>" + "<td>"
				+ order.getAskDeliverDate() + "</td>" + "</tr>" + "</tbody>"
				+ "</table>";

		MailSenderInfo mailSenderInfo = new MailSenderInfo();
		mailSenderInfo.setSubject(emailTitle);
		mailSenderInfo.setContent(emailContent);
		mailSenderInfo.setToAddress(customer.getEmail());
		SimpleMailSender.sendHtmlMail(mailSenderInfo);
	}

	@Override
	public void sendOrderInfoViaPhone(Order order, Customer customer) {

	}

	// TODO 这也不知道是什么鬼，保留原有的接口吧
	@Override
	public void testPrecondition(String userId, String taskName) {
		List<Task> tasks = mainProcessService.getTasksOfUserByTaskName(userId, taskName);
		try {
			for (Task task : tasks) {
				mainProcessService.completeTask(task.getId(), userId, null);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getComment(Object task, String variableName) {
		return (String) mainProcessService.getVariable(((Task)task).getProcessInstanceId(), variableName);
	}

	/*
	 * @Override public void completeQuoteConfirmTaskSummary(String taskId,
	 * String result) { Map<String, Object> data = new HashMap<String,
	 * Object>(); if (result.equals("1")) { data.put("confirmquote", true);
	 * data.put("eidtquote", false); data.put("samplejin", true); } if
	 * (result.equals("2")) { data.put("confirmquote", false);
	 * data.put("eidtquote", true); data.put("samplejin", true); } if
	 * (result.equals("3")) { data.put("confirmquote", false);
	 * data.put("eidtquote", false); data.put("samplejin", true); } try {
	 * jbpmAPIUtil.completeTask(taskId, data, "SHICHANGZHUANYUAN"); } catch
	 * (InterruptedException e) { catch block e.printStackTrace(); } }
	 */

	/*
	 * @Override public void completeSignContract(Integer orderId, double
	 * discount, String taskId) { Order order = orderDAO.findById(orderId);
	 * order.setDiscount(discount); orderDAO.attachDirty(order);
	 * 
	 * Map<String, Object> data = new HashMap<>(); try {
	 * jbpmAPIUtil.completeTask(taskId, data, "SHICHANGZHUANYUAN"); } catch
	 * (InterruptedException e) { catch block e.printStackTrace(); }
	 * 
	 * }
	 */
	
	private Map<String, Object> getParams(Order order){
		Map<String, Object> params = new HashMap<>();
		params.put("orderId", order.getOrderId());
		//Activiti在指定专员时，好像不可以使用Integer类型的数据，因为无法解析成为String型
		params.put(ACTOR_MARKET_STAFF, String.valueOf(order.getEmployeeId()));

		params.put(LogisticsServiceImpl.RESULT_RECEIVE_SAMPLE,
				(int) order.getHasPostedSampleClothes());
		params.put(LogisticsServiceImpl.RESULT_SEND_SAMPLE,
				(int) order.getIsNeedSampleClothes());
		params.put(RESULT_REORDER, false);
		
		params.put("purchase", false);
		params.put("produce", false);
		params.put("purchaseComment", new String());
		params.put("designComment", new String());
		params.put("produceComment", new String());
		params.put("modifyOrder", false);
		params.put("quote", 0);
		params.put("receiveMoney", false);
		params.put("confirmProduceOrderContract", false);
		params.put(RESULT_IS_HAODUOYI, order.getIsHaoDuoYi() == 1);
		params.put("needCraft", false);
		params.put(RESULT_PUSH_RESTMONEY, false);
		params.put("verifyQuoteComment", new String());
		params.put("sweaterMaterial", false);
		boolean isSweater = false;
		if (order.getClothesType().contains("毛衣")) {// 假如订单的衣服类型里含有“毛衣”这两个字，
			isSweater = true;
		}
		params.put("isSweater", isSweater);
		return params;
	}


	

	

	

	
}
