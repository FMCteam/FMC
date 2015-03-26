package nju.software.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nju.software.dao.impl.AccessoryCostDAO;
import nju.software.dao.impl.AccessoryDAO;
import nju.software.dao.impl.CraftDAO;
import nju.software.dao.impl.CustomerDAO;
import nju.software.dao.impl.EmployeeDAO;
import nju.software.dao.impl.DesignCadDAO;
import nju.software.dao.impl.FabricCostDAO;
import nju.software.dao.impl.FabricDAO;
import nju.software.dao.impl.LogisticsDAO;
import nju.software.dao.impl.MoneyDAO;
import nju.software.dao.impl.OrderDAO;
import nju.software.dao.impl.PackageDAO;
import nju.software.dao.impl.PackageDetailDAO;
import nju.software.dao.impl.ProduceDAO;
import nju.software.dao.impl.ProductDAO;
import nju.software.dao.impl.QuoteDAO;
import nju.software.dao.impl.VersionDataDAO;
import nju.software.dataobject.Accessory;
import nju.software.dataobject.AccessoryCost;
import nju.software.dataobject.Craft;
import nju.software.dataobject.DesignCad;
import nju.software.dataobject.Fabric;
import nju.software.dataobject.FabricCost;
import nju.software.dataobject.Logistics;
import nju.software.dataobject.Order;
import nju.software.dataobject.Produce;
import nju.software.dataobject.Quote;
import nju.software.dataobject.VersionData;
import nju.software.model.OrderModel;
import nju.software.model.QuoteModel;
import nju.software.service.FinanceService;
import nju.software.service.OrderService;
import nju.software.util.ActivitiAPIUtil;

import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author 莫其凡
 * 
 */
@Service("orderServiceImpl")
public class OrderServiceImpl implements OrderService {
	
	// 新增订单
	public void addOrder(Order order) {
		orderDAO.save(order);
	}
  
	public List<Order> findAll() {
		List<Order> order = this.orderDAO.findAll();
		return order;
	}

	public Order getOrderById(int orderId) {
		try {
			Order order = orderDAO.findById(orderId);
			return order;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean updateOrder(Order order) {
		try {
			orderDAO.attachDirty(order);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Object> getOrderWithSample(Map<String, Object> map) {
		try {

			// order that need sample and the hasPostedSampleCloth is
			// false,isNeedSampleCloths is true

			int count = orderDAO.coutSampleOrder();
			int page = (int) map.get("page");
			int number = (int) map.get("number_per_page");
			int total_pages = 0;
			int start = 0;

			System.out.println("count" + count);
			System.out.println("page" + page);
			System.out.println("number" + number);
			if (count > 0)
				total_pages = (int) Math.ceil((double) count / number);
			if (page > total_pages)
				page = total_pages;
			start = number * (page - 1);
			map.put("page_number", total_pages);
			List<Order> orderList = orderDAO.findSampleOrderAndPage(start,
					number);
			map.put("page_number", total_pages);
			List<Object> list = new ArrayList<Object>();
			list.add(orderList);
			list.add(map);
			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public Order findByOrderId(String orderId) {
		return orderDAO.findById(Integer.parseInt(orderId));
	}

	@Override
	public boolean merge(Order o) {
		try {
			orderDAO.merge(o);
			return true;
		} catch (Exception e) {

		}
		return false;
	}

	/**
	 * 提交询单，开始流程
	 */
	@Override
	public String addOrder(Order order, List<Fabric> fabrics,
			List<Accessory> accessorys, Logistics logistics) {

		// 添加订单信息
//		orderDAO.save(order);
//
//		File temp;
//		File save;
//		String sampleClothesPicture = order.getSampleClothesPicture();
//		if (sampleClothesPicture != null) {
//			temp = new File(sampleClothesPicture);
//			save = new File(temp.getParentFile().getParentFile()
//					.getParentFile().getAbsolutePath()
//					+ "/sampleClothesPicture/"
//					+ order.getOrderId()
//					+ "/"
//					+ temp.getName());
//			System.out.println(save.getAbsolutePath());
//			save.getParentFile().mkdirs();
//			temp.renameTo(save);
//			order.setSampleClothesPicture("/upload/sampleClothesPicture/"
//					+ order.getOrderId() + "/" + save.getName());
//		} else {
//			order.setSampleClothesPicture("");
//		}
//
//		String referencePicture = order.getReferencePicture();
//		if (referencePicture != null) {
//			temp = new File(referencePicture);
//			save = new File(temp.getParentFile().getParentFile()
//					.getParentFile().getAbsolutePath()
//					+ "/referencePicture/"
//					+ order.getOrderId()
//					+ "/"
//					+ temp.getName());
//			save.getParentFile().mkdirs();
//			temp.renameTo(save);
//			order.setSampleClothesPicture("/upload/referencePicture/"
//					+ order.getOrderId() + "/" + save.getName());
//		} else {
//			order.setReferencePicture("");
//		}
//		orderDAO.attachDirty(order);
//		
//
//		// 添加面料信息
//		for (Fabric fabric : fabrics) {
//			fabric.setOrderId(order.getOrderId());
//			fabricDAO.save(fabric);
//		}
//
//		// 添加辅料信息
//		for (Accessory accessory : accessorys) {
//			accessory.setOrderId(order.getOrderId());
//			accessoryDAO.save(accessory);
//		}
//
//		// 添加物流信息
//		logistics.setOrderId(order.getOrderId());
//		logisticsDAO.save(logistics);
//
//		// 启动流程
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("orderId", order.getOrderId());
//		params.put("marketStaff", order.getEmployeeId());
//		params.put("needclothes", order.getIsNeedSampleClothes() == 1);
//		params.put("sendclothes", order.getHasPostedSampleClothes() == 1);
//		
//		//TODO 以下转为代码初始化参数，原为xml初始化
//		params.put("reorder", order.getReorder() != 0);
//		boolean isSweater = false;
//		if (order.getClothesType().contains("毛衣")) {// 假如订单的衣服类型里含有“毛衣”这两个字，
//			isSweater = true;
//		}
//		params.put("isSweater", isSweater);
//		params.put("receiveSample", order.getHasPostedSampleClothes());
//		params.put("sendSample", order.getIsNeedSampleClothes());
//		params.put("purchase", false);
//		params.put("design", false);
//		params.put("produce", false);
//		params.put("purchaseComment", "");
//		params.put("designComment", "");
//		params.put("produceComment", "");
//		params.put("modifyOrder", false);
//		params.put("quote", 0);
//		params.put("receiveMoney", false);
//		params.put("confirmProduceOrderContract", false);
//		params.put("isHaoDuoYi", false);
//		params.put("verifyQuoteSuccess", false);
//		params.put("needCraft", false);
//		params.put("pushRestMoney", false);
//		params.put("verifyQuoteComment", "");
//		params.put("sweaterMaterial", false);
//		activitiAPIUtil.startWorkflowProcess(params);
//
//		return "下单成功";
		return "";
	}

	/**
	 * 通过actorId和taskName来查找order列表，返回List<OrderModel>
	 */
	@Override
	public List<OrderModel> getOrderByActorIdAndTaskname(String actorId,
			String taskName) {
		List<OrderModel> orderList = new ArrayList<OrderModel>();
		List<Task> list = activitiAPIUtil.getAssignedTasksOfUserByTaskName(
				actorId, taskName);
		if (list.isEmpty()) {
			System.out.println("no task list");
		}
		for (Task task : list) {
			// 需要获取task中的数据
			String processId = task.getProcessInstanceId();
			int orderId = (int) activitiAPIUtil.getProcessVariable(processId, "orderId");
			Order order = getOrderById(orderId);
			if (order != null) {
				System.out.println("orderId: " + order.getOrderId());
				OrderModel orderModel = new OrderModel(order, task.getId(),
						processId);
				orderList.add(orderModel);
			}
		}
		return orderList;
	}

	/**
	 * 通过orderId来获取order详细信息，并将taskId和processId封装，返回orderModel
	 */
	@Override
	public OrderModel getOrderDetail(int orderId, String taskId, String processId) {
		OrderModel orderModel = null;
		
		int orderId_process = (int)activitiAPIUtil.getProcessVariable(processId, "orderId");
		if (orderId == orderId_process) {
			Order order = orderDAO.findById(orderId);
			orderModel = new OrderModel(order, taskId, processId);
		}
		return orderModel;
	}

	@Override
	public boolean verify(int orderId, String taskId, String processId,
			boolean editOk, String buyComment, String designComment,
			String productComment) {
		String actorId = "SHICHANGZHUANYUAN";
		// 需要获取task中的数据
		int orderId_process = (int) activitiAPIUtil.getProcessVariable(processId, "orderId");
		System.out.println("orderId: " + orderId);
		if (orderId == orderId_process) {

			// 修改流程参数
			Map<String, Object> data = new HashMap<>();
			data.put("buyComment", buyComment);
			data.put("designComment", designComment);
			data.put("productCommment", productComment);
			data.put("editok", editOk);
			// 直接进入到下一个流程时
			try {
				activitiAPIUtil.completeTask(taskId, data, actorId);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return true;
		}

		return false;

	}

	@Override
	public void modifyOrder(Order order, List<Fabric> fabrics,
			List<Accessory> accessorys, Logistics logistics) {
		// 添加订单信息
		orderDAO.merge(order);
		/*
		 * //删除order相关的面料和辅料和物流的信息
		 * fabricDAO.deleteByProperty(order.getOrderId()); //添加面料信息 for(Fabric
		 * fabric:fabrics){ fabric.setOrderId(order.getOrderId());
		 * fabricDAO.save(fabric); }
		 * 
		 * //添加辅料信息 for(Accessory accessory:accessorys){
		 * accessory.setOrderId(order.getOrderId());
		 * accessoryDAO.save(accessory); }
		 * 
		 * //添加物流信息 logistics.setOrderId(order.getOrderId());
		 * logisticsDAO.save(logistics);
		 */
	}

	@Override
	public List<QuoteModel> getQuoteByActorAndTask(String actor, String taskName) {
		List<QuoteModel> orderList = new ArrayList<QuoteModel>();
		List<Task> list = activitiAPIUtil.getAssignedTasksOfUserByTaskName(actor,
				taskName);
		if (list.isEmpty()) {
			System.out.println("no task list");
		}
		for (Task task : list) {
			// 需要获取task中的数据
			String processId = task.getProcessInstanceId();
			int orderId = (int) activitiAPIUtil.getProcessVariable(processId, "orderId");
			List<Quote> quote = quoteDAO.findByProperty("orderId", orderId);
			if (quote != null && quote.size() != 0) {

				System.out.println("quote: " + quote.get(0).getOrderId());
				QuoteModel model = new QuoteModel(quote.get(0), task.getId(),
						processId);

				orderList.add(model);
			}
		}
		return orderList;

	}

	@Override
	public QuoteModel getQuoteByOrderAndPro(String actor, String taskName,
			int orderId) {
		List<Task> list = activitiAPIUtil.getAssignedTasksOfUserByTaskName(actor,
				taskName);
		if (list.isEmpty()) {
			System.out.println("no task list");
		}
		for (Task task : list) {
			// 需要获取task中的数据
			String pid = task.getProcessInstanceId();
			int oid = (int) activitiAPIUtil.getProcessVariable(pid, "orderId");
			if (orderId == oid) {
				List<Quote> quote = quoteDAO.findByProperty("orderId", orderId);
				return new QuoteModel(quote.get(0), task.getId(),
						pid);
			}
		}
		return null;
	}

	@Override

	public void endOrder(Integer orderId) {
		Order order=orderDAO.findById(orderId);
		order.setOrderState("1");
		orderDAO.attachDirty(order);
		activitiAPIUtil.abortProcess(order.getProcessId());
	}

	@Override
	public List<Map<String,Object>> findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		List<Order> orders = orderDAO.findByProperty(propertyName, value);
		List<Map<String, Object>> list = new ArrayList<>();
		for (Order order : orders) {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("order", order);
			model.put("employee", employeeDAO.findById(order.getEmployeeId()));
			model.put("orderId", service.getOrderId(order));
			list.add(model);
		}
		return list;
	}
			

			
	@Autowired
	private FinanceService financeService;		

	public List<Map<String, Object>> getOrdersEnd(String userRole, Integer userId) {
		/*Order orderExample = new Order();
		orderExample.setOrderState("1"); //被终止的订单
		if("CUSTOMER".equals(userRole)){
			orderExample.setCustomerId(userId);
		}else if ("marketStaff".equals(userRole)){
			orderExample.setEmployeeId(userId);
		}
		
		List<Order> orders = orderDAO.findByExample(orderExample);*/
		List<Order> orders = orderDAO.getSearchOrderList( null,
				 null,null,null,null,null,userRole,userId);
		List<Map<String, Object>> list = new ArrayList<>();
		for (Order order : orders) {
			if(order.getOrderState().equals("1")){
				Map<String, Object> model = new HashMap<String, Object>();
				model.put("order", order);
				model.put("employee", employeeDAO.findById(order.getEmployeeId()));
				model.put("orderId", service.getOrderId(order));
				list.add(model);
			}
			
		}
		return list;
	}		
			
	public List<Map<String, Object>> getModifyOrderList() {
		// TODO Auto-generated method stub
		Order o = new Order();
		//下面第一种方法new了一个空的Order对象，不知道为什么会查出数据
//		List<Order> orderList = orderDAO.findByExample(o);
		List<Order> orderList = orderDAO.findByOrderState("A");//只能够修改正在进行的订单
		List<Map<String, Object>> list = new ArrayList<>();
		int orderslength = orderList.size();
		Integer pages=(int) Math.ceil((double)orderslength/10);

		for (Order order : orderList) {
			ArrayList<String>  orderProcessStateNames = financeService.getProcessStateName(order.getOrderId());
			if(orderProcessStateNames.size()>0){
				order.setOrderProcessStateName(orderProcessStateNames.get(0));
			}else{
				order.setOrderProcessStateName("");
			}
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("order", order);
			model.put("employee", employeeDAO.findById(order.getEmployeeId()));
			model.put("taskTime", getTaskTime(order.getOrderTime()));
			model.put("orderId", getOrderId(order));
            model.put("pages", pages);
			list.add(model);
		}
		return list;
	}
	public boolean ownToOrderList(Order o1,List<Order> orderList){
		
		for(int i =0;i<orderList.size();i++){
			if(o1.getOrderId().equals(orderList.get(i).getOrderId())){
				return true;				
			}
		}
		return false;
	}
	public List<Map<String, Object>> getSearchOrderList(String ordernumber,
			String customername, String stylename, String startdate,String enddate,
			Integer[] employeeIds,String userRole,Integer userId) {
		
//        Order o = new Order();
//		o.setOrderState("A");//只能修改正在进行的订单
//		List<Order> orderList = orderDAO.findByExample(o);
//		List<Order> orders = orderDAO.getSearchOrderList(ordernumber,
//		    customername,stylename,startdate,enddate,employeeIds);
//		 
//		for(int j =0;j<orders.size();j++){
//			Order ord =  orders.get(j);			
//			if(ownToOrderList(ord, orderList)){
//				searchResults.add(ord);
//				System.out.println("hehehehehhehehe"+ord.getOrderId());
//			}else{
//				System.out.println("hahahahahaha");
//			}
//		}
		List<Order> searchResults = orderDAO.getSearchOrderDoingList(ordernumber,"", customername, stylename, startdate, enddate, employeeIds,userRole,userId);

		List<Map<String, Object>> list = new ArrayList<>();
		for (Order order : searchResults) {
			ArrayList<String>  orderProcessStateNames = financeService.getProcessStateName(order.getOrderId());
			if(orderProcessStateNames.size()>0){
				order.setOrderProcessStateName(orderProcessStateNames.get(0));
			}else{
				order.setOrderProcessStateName("");
			}
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("order", order);
			model.put("employee", employeeDAO.findById(order.getEmployeeId()));
			model.put("orderId", service.getOrderId(order));
//			model.put("pages", pages);
			model.put("taskTime", getTaskTime(order.getOrderTime()));
			list.add(model);
 		}
		return list;
 	}

	

	
	public String getOrderId(Order order) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		return dateFormat.format(order.getOrderTime())
				+ String.format("%06d", order.getOrderId());
	}

	public String getTaskTime(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(date);
	}
	
	@Autowired
	private ActivitiAPIUtil activitiAPIUtil;
	@Autowired
	private LogisticsDAO logisticsDAO;
	@Autowired
	private OrderDAO orderDAO;
	@Autowired
	private EmployeeDAO employeeDAO;
	@Autowired
	private CustomerDAO customerDAO;
	@Autowired
	private ProductDAO productDAO;
	@Autowired
	private PackageDAO packageDAO;
	@Autowired
	private PackageDetailDAO packageDetailDAO;
	@Autowired
	private AccessoryDAO accessoryDAO;
	@Autowired
	private FabricDAO fabricDAO;
	@Autowired
	private ProduceDAO produceDAO;
	@Autowired
	private MoneyDAO moneyDAO;
	@Autowired
	private ServiceUtil service;
	@Autowired
	private VersionDataDAO versionDataDAO;
	@Autowired
	private DesignCadDAO cadDAO;
	@Autowired
	private QuoteDAO quoteDAO;
	@Autowired
	private FabricCostDAO fabricCostDAO;
	@Autowired
	private AccessoryCostDAO accessoryCostDAO;
	@Autowired
	private CraftDAO craftDAO;
	@Override
	public Map<String, Object> getModifyOrderDetail(Integer userId, int orderId) {
		
		
		Map<String, Object> model = new HashMap<String, Object>();
		//TaskSummary task = jbpmAPIUtil.getTask(actorId, taskName, orderId);
		Order order = orderDAO.findById(orderId);
		if(order.getConfirmDepositFile() != null){
			return null;
		}
		//model.put("task", task);
		//model.put("taskId", task.getId());
		List<Craft> crafts = craftDAO.findByOrderId(orderId);
		if (crafts != null && crafts.size() != 0) {
			model.put("craft", crafts.get(0));
			Craft craft = crafts.get(0);
			short need_craft = craft.getNeedCraft();
			if(need_craft ==1){
				model.put("needcraft", "needcraft");
			}else{
				model.put("needcraft", "dontneedcraft");
			}
		}
 
		model.put("order", order);
		model.put("customer",customerDAO.findById(order.getCustomerId()));
		model.put("employee", employeeDAO.findById(order.getEmployeeId()));
		model.put("logistics", logisticsDAO.findById(orderId));
		model.put("fabrics", fabricDAO.findByOrderId(orderId));
		model.put("accessorys", accessoryDAO.findByOrderId(orderId));
		List<DesignCad> cads = cadDAO.findByOrderId(orderId);
		if (cads != null && cads.size() != 0) {
			model.put("designCad", cads.get(0));
		}
		model.put("orderId", getOrderId(order));

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
		return model;
	}

	@Override
	public void modifyOrderSubmit(Order order, List<Fabric> fabrics,
			List<Accessory> accessorys, Logistics logistics,
			List<Produce> produces, List<Produce> sample_produces,
			List<VersionData> versions, DesignCad cad, int userId,
			List<FabricCost> fabricCosts,List<AccessoryCost> accessoryCosts,Quote quote,Craft craft) {
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
		//添加面料价格信息
		fabricCostDAO.deleteByProperty("orderId", orderId);
		float all_fabric_prices=0.0f;
		for(FabricCost fabricCost : fabricCosts){
			fabricCost.setOrderId(orderId);
			BigDecimal  fabric_price_temp =   
					 new BigDecimal((fabricCost.getTearPerMeter())*(fabricCost.getCostPerMeter()));  
			float  fabric_price = fabric_price_temp.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue(); 
			fabricCost.setPrice(fabric_price);
			all_fabric_prices+=fabric_price;
			fabricCostDAO.save(fabricCost);
			
		}

		//添加辅料价格信息
		accessoryCostDAO.deleteByProperty("orderId", orderId);
		float all_accessory_prices=0.0f;
		for(AccessoryCost accessoryCost : accessoryCosts){
			accessoryCost.setOrderId(orderId);
			float accessory_price= accessoryCost.getTearPerPiece()*(accessoryCost.getCostPerPiece());
			accessoryCost.setPrice(accessory_price);      	
        	all_accessory_prices+=accessory_price;
        	accessoryCostDAO.save(accessoryCost);
			
		}
		//添加Quote
        
        Quote originalQuote =quoteDAO.findById(orderId);	
        originalQuote.setCutCost(quote.getCutCost());
        originalQuote.setManageCost(quote.getManageCost());
        originalQuote.setSwingCost(quote.getSwingCost());
        originalQuote.setIroningCost(quote.getIroningCost());
        originalQuote.setNailCost(quote.getNailCost());
        originalQuote.setPackageCost(quote.getPackageCost());
        originalQuote.setOtherCost(quote.getOtherCost());
        originalQuote.setDesignCost(quote.getDesignCost());

        originalQuote.setAccessoryCost(all_accessory_prices);
        originalQuote.setFabricCost(all_fabric_prices);
			
			float singleCost=originalQuote.getCutCost()+originalQuote.getManageCost()+originalQuote.getDesignCost()+
					originalQuote.getIroningCost()+originalQuote.getNailCost()+originalQuote.getPackageCost()+
					originalQuote.getSwingCost()+originalQuote.getOtherCost()+all_accessory_prices+all_fabric_prices;
			float craft_price = craft.getCrumpleMoney()+craft.getEmbroideryMoney()+craft.getLaserMoney()+
					            craft.getOpenVersionMoney()+craft.getStampDutyMoney()+craft.getWashHangDyeMoney();
			singleCost+=craft_price;
			originalQuote.setCraftCost(craft_price);
			originalQuote.setSingleCost(singleCost);
			quoteDAO.merge(originalQuote);
//			quoteDAO.attachDirty(originalQuote);
		
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
		
        craftDAO.merge(craft);
	}



}
