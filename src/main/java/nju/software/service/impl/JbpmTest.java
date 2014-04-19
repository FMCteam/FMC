package nju.software.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jbpm.task.query.TaskSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import nju.software.dao.impl.AccessoryDAO;
import nju.software.dao.impl.FabricDAO;
import nju.software.dao.impl.LogisticsDAO;
import nju.software.dao.impl.OrderDAO;
import nju.software.dao.impl.PackageDAO;
import nju.software.dao.impl.PackageDetailDAO;
import nju.software.dao.impl.ProduceDAO;
import nju.software.dao.impl.ProductDAO;
import nju.software.dao.impl.QuoteDAO;
import nju.software.dataobject.Accessory;
import nju.software.dataobject.Fabric;
import nju.software.dataobject.Logistics;
import nju.software.dataobject.Order;
import nju.software.dataobject.Package;
import nju.software.dataobject.PackageDetail;
import nju.software.dataobject.Produce;
import nju.software.util.JbpmAPIUtil;


@Service("test")
public class JbpmTest {

	
	public void completeVerify(String actorId,boolean result) {
		Order order=new Order();
		
		order.setEmployeeId(1);
		order.setCustomerId(1);
		order.setOrderState("Test");
		order.setOrderTime(new Timestamp(new Date().getTime()));
		order.setCustomerName("张三");
		order.setCustomerCompany("制造链");
		order.setCustomerCompanyFax("025-666-888");
		order.setCustomerPhone1("15991231235");
		order.setCustomerPhone2("15991231234");
		order.setCustomerCompanyAddress("Test");
		order.setStyleName("炫酷");
		order.setFabricType("针织 梭织");
		order.setStyleSex("男");
		order.setStyleSeason("春夏");
		order.setSpecialProcess("水印");
		order.setOtherRequirements("其他");
		order.setSampleAmount(20);
		order.setAskAmount(120);
		order.setAskProducePeriod("10");
		order.setAskDeliverDate(new Timestamp(new Date().getTime()));
		order.setAskCodeNumber("XXL");
		order.setHasPostedSampleClothes((short) 1);
		order.setIsNeedSampleClothes((short) 1);
		order.setOrderSource("客户推荐 老王");
		order.setLogisticsState(0);
		orderDAO.save(order);
		orderId=order.getOrderId();
		
	
		
		
		for(int i=0;i<3;i++){
			Produce p=new Produce();
		    p.setType(Produce.TYPE_PRODUCE);
			p.setColor(i+"");
			p.setXs(1);
			p.setS(2);
			p.setM(3);
			p.setL(4);
			p.setXl(5);
			p.setXxl(6);
			p.setOid(order.getOrderId());
			produceDAO.save(p);
		}
		
		for(int i=0;i<3;i++){
			Produce p=new Produce();
		    p.setType(Produce.TYPE_SAMPLE_PRODUCE);
			p.setColor(i+"");
			p.setXs(1);
			p.setS(2);
			p.setM(3);
			p.setL(4);
			p.setXl(5);
			p.setXxl(6);
			p.setOid(order.getOrderId());
			produceDAO.save(p);
		}
		
		
		// 面料数据
		String fabric_names = "fabric1,fabric2,fabric3";
		String fabric_amounts = "1,2,3";
		String fabric_name[] = fabric_names.split(",");
		String fabric_amount[] = fabric_amounts.split(",");
		for (int i = 0; i < fabric_name.length; i++) {
			fabricDAO.save(new Fabric(orderId, fabric_name[i], fabric_amount[i]));
		}

		// 辅料数据
		String accessory_names = "accessory1,accessory2,accessory3";
		String accessory_querys = "accessory1,accessory2,accessory3";
		String accessory_name[] = accessory_names.split(",");
		String accessory_query[] = accessory_querys.split(",");
		for (int i = 0; i < fabric_name.length; i++) {
			accessoryDAO.save(new Accessory(orderId, accessory_name[i],
					accessory_query[i]));
		}
		
		
		Logistics logistics=new Logistics(order.getOrderId());
		logisticsDAO.save(logistics);
		
		
		orderId=order.getOrderId();
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderId", orderId);
		params.put("marketStaff", actorId);
		params.put("needclothes", order.getIsNeedSampleClothes() == 1);
		params.put("sendclothes", order.getHasPostedSampleClothes() == 1);
		doTMWorkFlowStart(params);
		
		
		//收取样衣
		long taskId = getTaskId(LogisticsServiceImpl.ACTOR_LOGISTICS_MANAGER,
				LogisticsServiceImpl.TASK_RECEIVE_SAMPLE, orderId);
		Map <String,Object> data=new HashMap <String,Object> ();
		data.put("receivedsample", true);
		completeTask(taskId, data, LogisticsServiceImpl.ACTOR_LOGISTICS_MANAGER);
		
		
		//采购验证
		taskId = getTaskId(BuyServiceImpl.ACTOR_PURCHASE_MANAGER,
				BuyServiceImpl.TASK_VERIFY_PURCHASE, orderId);
		data=new HashMap <String,Object> ();
		data.put("buyVal", result);
		data.put("buyComment", "Test Buy Verify");
		completeTask(taskId, data, BuyServiceImpl.ACTOR_PURCHASE_MANAGER);
		
		
		//设计验证
		taskId = getTaskId(DesignServiceImpl.ACTOR_DESIGN_MANAGER,
				DesignServiceImpl.TASK_VERIFY_DESIGN, orderId);
		data=new HashMap <String,Object> ();
		data.put("designVal", result);
		data.put("designComment", "Test Design Verify");
		completeTask(taskId, data, DesignServiceImpl.ACTOR_DESIGN_MANAGER);
		
		
		//生产验证
		taskId = getTaskId(ProduceServiceImpl.ACTOR_PRODUCE_MANAGER,
				ProduceServiceImpl.TASK_VERIFY_PRODUCE, orderId);
		data=new HashMap <String,Object> ();
		data.put("productVal", result);
		data.put("productComment", "Test Produce Verify");
		completeTask(taskId, data, ProduceServiceImpl.ACTOR_PRODUCE_MANAGER);
	}
	
	
	
	public void completeComputeCost(String actorId){
		completeVerify(actorId, true);
		
		//采购成本核算
		long taskId = getTaskId(BuyServiceImpl.ACTOR_PURCHASE_MANAGER,
				BuyServiceImpl.TASK_COMPUTE_PURCHASE_COST, orderId);
		Map <String,Object> data=new HashMap <String,Object> ();
		completeTask(taskId, data, BuyServiceImpl.ACTOR_PURCHASE_MANAGER);
		
		
		//设计成本核算
		taskId = getTaskId(DesignServiceImpl.ACTOR_DESIGN_MANAGER,
				DesignServiceImpl.TASK_COMPUTE_DESIGN_COST, orderId);
		data=new HashMap <String,Object> ();
		completeTask(taskId, data, DesignServiceImpl.ACTOR_DESIGN_MANAGER);
		
		
		//生产成本核算
		taskId = getTaskId(ProduceServiceImpl.ACTOR_PRODUCE_MANAGER,
				ProduceServiceImpl.TASK_COMPUTE_PRODUCE_COST, orderId);
		data=new HashMap <String,Object> ();
		completeTask(taskId, data, ProduceServiceImpl.ACTOR_PRODUCE_MANAGER);
	}
	
	
	public void completeConfirmQuote(String actorId){
		completeComputeCost(actorId);
		
		//合并报价
		long taskId = getTaskId(actorId,
				MarketServiceImpl.TASK_MERGE_QUOTE, orderId);
		Map <String,Object> data=new HashMap <String,Object> ();
		completeTask(taskId, data, actorId);
		
		
		//审核报价
		taskId = getTaskId(MarketServiceImpl.ACTOR_MARKET_MANAGER,
				MarketServiceImpl.TASK_VERIFY_QUOTE, orderId);
		data=new HashMap <String,Object> ();
		completeTask(taskId, data, MarketServiceImpl.ACTOR_MARKET_MANAGER);
		
		
		//确认报价
		taskId = getTaskId(actorId,
				MarketServiceImpl.TASK_CONFIRM_QUOTE, orderId);
		data=new HashMap <String,Object> ();
		data.put("confirmquote", true);
		data.put("eidtquote", false);
		data.put("samplejin", true);
		completeTask(taskId, data, actorId);
	}
	
	
	public void completeProduceSample(String actorId){
		completeConfirmQuote(actorId);
		

		
		//样衣制作金确认
		long taskId = getTaskId(FinanceServiceImpl.ACTOR_FINANCE_MANAGER,
				FinanceServiceImpl.TASK_CONFIRM_SAMPLE_MONEY, orderId);
		Map data=new HashMap <String,Object> ();
		data.put(FinanceServiceImpl.RESULT_MONEY, true);
		completeTask(taskId, data, FinanceServiceImpl.ACTOR_FINANCE_MANAGER);
		
		
		//采购样衣
		 taskId = getTaskId(BuyServiceImpl.ACTOR_PURCHASE_MANAGER,
				BuyServiceImpl.TASK_PURCHASE_SAMPLE_MATERIAL, orderId);
		 data=new HashMap <String,Object> ();
		data.put("purchaseerror", false);
		completeTask(taskId, data, BuyServiceImpl.ACTOR_PURCHASE_MANAGER);
		
		
		
		//上传CAD
		taskId = getTaskId(DesignServiceImpl.ACTOR_DESIGN_MANAGER,
				DesignServiceImpl.TASK_UPLOAD_DESIGN, orderId);
		data=new HashMap <String,Object> ();
		//data.put("purchaseerror", false);
		completeTask(taskId, data, DesignServiceImpl.ACTOR_DESIGN_MANAGER);
		
		
		//生产样衣
		taskId = getTaskId(ProduceServiceImpl.ACTOR_PRODUCE_MANAGER,
				ProduceServiceImpl.TASK_PRODUCE_SAMPLE, orderId);
		data=new HashMap <String,Object> ();
		data.put("producterror", false);
		completeTask(taskId, data, ProduceServiceImpl.ACTOR_PRODUCE_MANAGER);
	}
	
	
	public void completeProduceConfirm(String actorId,boolean result){
		completeProduceSample(actorId);
		
		
		//样衣发货
		long taskId = getTaskId(LogisticsServiceImpl.ACTOR_LOGISTICS_MANAGER,
				LogisticsServiceImpl.TASK_SEND_SAMPLE, orderId);
		Map data=new HashMap <String,Object> ();
		//data.put("receivedsamplejin", true);
		completeTask(taskId, data, LogisticsServiceImpl.ACTOR_LOGISTICS_MANAGER);
		
		
		//确认加工单
		taskId = getTaskId(actorId,
				MarketServiceImpl.TASK_CONFIRM_PRODUCE_ORDER, orderId);
		data=new HashMap <String,Object> ();
		data.put("comfirmworksheet", true);
		completeTask(taskId, data, actorId);
		
		
		//采购部确认
		taskId = getTaskId(BuyServiceImpl.ACTOR_PURCHASE_MANAGER,
				BuyServiceImpl.TASK_CONFIRM_PURCHASE, orderId);
		data=new HashMap <String,Object> ();
		data.put("isworksheet", result);
		completeTask(taskId, data, BuyServiceImpl.ACTOR_PURCHASE_MANAGER);
		
		
		//设计部确认
		taskId = getTaskId(DesignServiceImpl.ACTOR_DESIGN_MANAGER,
				DesignServiceImpl.TASK_MODIFY_DESIGN, orderId);
		data=new HashMap <String,Object> ();
		//data.put("comfirmworksheet", true);
		completeTask(taskId, data, DesignServiceImpl.ACTOR_DESIGN_MANAGER);
	}
	
	
	public void completeBeforeProduce(String actorId){
		completeProduceConfirm(actorId, true);
		//签订合同加工单
		long taskId = getTaskId(actorId,
				MarketServiceImpl.TASK_SIGN_CONTRACT, orderId);
		Map<String,Object> data=new HashMap <String,Object> ();
		//data.put("comfirmworksheet", true);
		completeTask(taskId, data, actorId);
		
		
		//30%
		taskId = getTaskId(FinanceServiceImpl.ACTOR_FINANCE_MANAGER,
				FinanceServiceImpl.TASK_CONFIRM_DEPOSIT, orderId);
		data=new HashMap <String,Object> ();
		data.put(FinanceServiceImpl.RESULT_MONEY, true);
		completeTask(taskId, data, FinanceServiceImpl.ACTOR_FINANCE_MANAGER);
		
		
		//采购部确认
		taskId = getTaskId(BuyServiceImpl.ACTOR_PURCHASE_MANAGER,
				BuyServiceImpl.TASK_PURCHASE_MATERIAL, orderId);
		data=new HashMap <String,Object> ();
		data.put("procurementerror", false);
		completeTask(taskId, data, BuyServiceImpl.ACTOR_PURCHASE_MANAGER);
		System.out.println("采购部完成\n\n\n");
		
		//设计部确认
		taskId = getTaskId(DesignServiceImpl.ACTOR_DESIGN_MANAGER,
				DesignServiceImpl.TASK_CONFIRM_DESIGN, orderId);
		data=new HashMap <String,Object> ();
		//data.put("comfirmworksheet", true);
		completeTask(taskId, data, DesignServiceImpl.ACTOR_DESIGN_MANAGER);
	}
	
	
	
	public void completeCheckQuality(String actorId){
		
		completeBeforeProduce(actorId);
		
		
		for(int i=0;i<3;i++){
			Produce p=new Produce();
		    p.setType(Produce.TYPE_PRODUCED);
			p.setColor(i+"");
			p.setXs(1);
			p.setS(2);
			p.setM(3);
			p.setL(4);
			p.setXl(5);
			p.setXxl(6);
			p.setOid(orderId);
			produceDAO.save(p);
		}
		
		
		
		//生产
		long taskId = getTaskId(ProduceServiceImpl.ACTOR_PRODUCE_MANAGER,
				ProduceServiceImpl.TASK_PRODUCE, orderId);
		Map<String,Object>data=new HashMap <String,Object> ();
		data.put("volumeproduction", true);
		completeTask(taskId, data, ProduceServiceImpl.ACTOR_PRODUCE_MANAGER);
		
		
		
		
		
		
		//质检
		taskId = getTaskId(QualityServiceImpl.ACTOR_QUALITY_MANAGER,
				QualityServiceImpl.TASK_CHECK_QUALITY, orderId);
		data=new HashMap <String,Object> ();
		//data.put("comfirmworksheet", true);
		completeTask(taskId, data, QualityServiceImpl.ACTOR_QUALITY_MANAGER);
	}
	
	
	public void completeTaskLogistics(String actionId){
		completeCheckQuality(actionId);
		Package packages = new Package();
		packages.setOrderId(orderId);
		Date inDate = new Date();
		Timestamp entryTime = new Timestamp(inDate.getTime());
		packages.setPackageTime(entryTime);
		packages.setWarehouseId("1号");
		packages.setShelfId("3层");
		packageDAO.save(packages);
		PackageDetail packageDetail =new PackageDetail();
		packageDetail.setClothesAmount(100);
		packageDetail.setClothesStyleColor("红色");
		packageDetail.setClothesStyleName("雪纺连衣裙");
		packageDetail.setPackageId(packages.getPackageId());
		packageDetailDAO.save(packageDetail);
		 long taskId = getTaskId("logisticsManager",
				"warehouse", orderId);
		Map data=new HashMap <String,Object> ();
		completeTask(taskId, data, "logisticsManager");
		
		 taskId = getTaskId("financeManager",
					"confirmFinalPayment", orderId);
			data=new HashMap <String,Object> ();
			data.put("paymentok", true);
			completeTask(taskId, data, "financeManager");
	}
		
	
	
	public void completeTask(long taskId, Map<?, ?> data, String actorId) {
		try {
			jbpmAPIUtil.completeTask(taskId, data, actorId);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void doTMWorkFlowStart(Map<String, Object> params) {
		try {
			jbpmAPIUtil.setParams(params);
			jbpmAPIUtil.startWorkflowProcess();
			System.out.println("流程启动成功！");
		} catch (Exception ex) {
			System.out.println("流程启动失败");
			ex.printStackTrace();
		}
	}

	public long getTaskId(String actorId, String taskName, Integer orderId) {
		List<TaskSummary> list = jbpmAPIUtil.getAssignedTasksByTaskname(
				actorId, taskName);
		for (TaskSummary task : list) {
			if (jbpmAPIUtil.getVariable(task, "orderId").equals(orderId)) {
				return task.getId();
			}
		}
		return 0;
	}
	
	
	

	@Autowired
	private OrderDAO orderDAO;
	@Autowired
	private QuoteDAO quoteDAO;
	@Autowired
	private JbpmAPIUtil jbpmAPIUtil;
	@Autowired
	private AccessoryDAO accessoryDAO;
	@Autowired
	private FabricDAO fabricDAO;
	@Autowired
	private LogisticsDAO logisticsDAO;
	@Autowired
	private ProductDAO productDAO;
	@Autowired
	private ProduceDAO produceDAO;
	@Autowired
	private PackageDAO packageDAO;
	@Autowired
	private PackageDetailDAO packageDetailDAO;
	private Integer orderId=0;
}
