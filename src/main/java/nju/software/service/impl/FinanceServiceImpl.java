package nju.software.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nju.software.dao.impl.AccessoryCostDAO;
import nju.software.dao.impl.AccessoryDAO;
import nju.software.dao.impl.CheckRecordDAO;
import nju.software.dao.impl.CustomerDAO;
import nju.software.dao.impl.DeliveryRecordDAO;
import nju.software.dao.impl.EmployeeDAO;
import nju.software.dao.impl.FabricCostDAO;
import nju.software.dao.impl.FabricDAO;
import nju.software.dao.impl.LogisticsDAO;
import nju.software.dao.impl.MoneyDAO;
import nju.software.dao.impl.OrderDAO;
import nju.software.dao.impl.PackageDAO;
import nju.software.dao.impl.ProduceDAO;
import nju.software.dao.impl.ProductDAO;
import nju.software.dao.impl.QuoteDAO;
import nju.software.dataobject.CheckRecord;
import nju.software.dataobject.DeliveryRecord;
import nju.software.dataobject.Money;
import nju.software.dataobject.Order;
import nju.software.dataobject.Produce;
import nju.software.dataobject.Quote;
import nju.software.process.service.MainProcessService;
import nju.software.service.FinanceService;

@Service("financeServiceImpl")
public class FinanceServiceImpl implements FinanceService {

	// ===========================样衣金确认=================================
	@Override
	public List<Map<String, Object>> getConfirmSampleMoneyList(String actorId) {
		return service.getOrderList(actorId, TASK_CONFIRM_SAMPLE_MONEY);
	}

	@Override
	public List<Map<String, Object>> getSearchConfirmSampleMoneyList(
			String actorId, String ordernumber, String customername,
			String stylename, String startdate, String enddate,
			Integer[] employeeIds) {
		return service.getSearchOrderList(actorId,  ordernumber,  customername,
				 stylename,  startdate,  enddate,employeeIds, TASK_CONFIRM_SAMPLE_MONEY);

	}
	
	@Override
	public Map<String, Object> getConfirmSampleMoneyDetail(String actorId,
			Integer orderId) {
		Map<String, Object> model = service.getBasicOrderModelWithQuote(
				actorId, TASK_CONFIRM_SAMPLE_MONEY, orderId);
		Order order = (Order) model.get("order");
		Float price = (float) 0;
		if (order.getStyleSeason().equals("春夏")) {
			price = (float) 200;
			model.put("price", price);
		} else {
			price = (float) 400;
			model.put("price", price);
		}
		model.put("number", order.getSampleAmount());
		model.put("total", order.getSampleAmount() * price);
		model.put("taskName", "确认样衣制作金");
		model.put("tabName", "制版费用");
		model.put("type", "样衣制作金");
		model.put("url", "/finance/confirmSampleMoneySubmit.do");
		model.put("moneyName", "样衣制作金");
		return model;
	}

	@Override
	public boolean confirmSampleMoneySubmit(String actorId, String taskId,
			boolean result, Money money) {
		if (result) {
			moneyDAO.save(money);
		}
		Map<String, Object> data = new HashMap<>();
		data.put(RESULT_MONEY, result);
		try {
			mainProcessService.completeTask(taskId, actorId, data);
			return true;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public boolean confirmSampleMoneySubmit(String actorId, String taskId,
			boolean result, Money money, int orderId) {
		Order order = orderDAO.findById(orderId);
		if (result) {
			moneyDAO.save(money);
		}
		Map<String, Object> data = new HashMap<>();
		data.put(RESULT_MONEY, result);
		try {
			mainProcessService.completeTask(taskId, actorId, data);
			if(result==false){//如果result的的值为false，即为未收取到样衣金，流程会异常终止，将orderState设置为1
				order.setOrderState("1");
				orderDAO.attachDirty(order);
			}
			return true;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}
	}

	// ===========================退还定金===================================
	@Override
	public List<Map<String, Object>> getReturnDepositList(String actorId) {
		return service.getOrderList(actorId,TASK_RETURN_DEPOSIT );
	}
	
	@Override
	public List<Map<String, Object>> getReturnDepositList(String actorId,
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds) {
		return service.getSearchOrderList(actorId, ordernumber,  customername,
				 stylename,  startdate,  enddate,
				 employeeIds,TASK_RETURN_DEPOSIT);
	}
	// ===========================定金确认===================================
	@Override
	public List<Map<String, Object>> getConfirmDepositList(String actorId) {
		return service.getOrderList(actorId, TASK_CONFIRM_DEPOSIT);
	}

	@Override
	public List<Map<String, Object>> getSearchConfirmDepositList(
			String actorId, String ordernumber, String customername,
			String stylename, String startdate, String enddate,
			Integer[] employeeIds) {
		return service.getSearchOrderList(actorId, ordernumber,  customername,
				 stylename,  startdate,  enddate,
				 employeeIds,TASK_CONFIRM_DEPOSIT);
	}
//退还定金订单详情
	@Override
	public Map<String, Object> getReturnDepositDetail(String actorId,
			int orderId) {

		Map<String, Object> model = service.getBasicOrderModelWithQuote(
				actorId, TASK_RETURN_DEPOSIT, orderId);
		Order order = (Order) model.get("order");
		Quote quote = (Quote) model.get("quote");
		Float price = quote.getOuterPrice();
		model.put("price", price);
		model.put("number", order.getAskAmount());
		model.put("total", order.getTotalMoney() * 0.3);
		model.put("taskName", "退还大货定金");
		model.put("tabName", "大货定金");
		model.put("type", "大货定金");
		model.put("url", "/finance/returnDepositSubmit.do");
		model.put("moneyName", "退还定金");
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
	public Map<String, Object> getConfirmDepositDetail(String actorId,
			Integer orderId) {

		Map<String, Object> model = service.getBasicOrderModelWithQuote(
				actorId, TASK_CONFIRM_DEPOSIT, orderId);
		Order order = (Order) model.get("order");
		Quote quote = (Quote) model.get("quote");
		Float price = quote.getOuterPrice();
		model.put("price", price);
		model.put("number", order.getAskAmount());
		model.put("total", order.getTotalMoney() * 0.3);
		model.put("taskName", "确认大货定金");
		model.put("tabName", "大货定金");
		model.put("type", "大货定金");
		model.put("url", "/finance/confirmDepositSubmit.do");
		model.put("moneyName", "30%定金");
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
	public boolean confirmDepositSubmit(String actorId, String taskId,
			boolean result, Money money) {
		if (result) {
			moneyDAO.save(money);
		}
		Map<String, Object> data = new HashMap<>();
		data.put(RESULT_MONEY, result);
		try {
			mainProcessService.completeTask(taskId, actorId, data);
			return true;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}
	}
	@Override
	public boolean confirmDepositSubmit(String actorId, String taskId,
			boolean result, Money money, int orderId) {
		if (result) {
			moneyDAO.save(money);
		}
		Map<String, Object> data = new HashMap<>();
		data.put(RESULT_MONEY, result);
		try {
			mainProcessService.completeTask(taskId, actorId, data);
			if(result==false){//如果result的的值为false，即为确认定金失败，流程会异常终止，将orderState设置为1
				Order order = orderDAO.findById(orderId);
				order.setOrderState("1");
				orderDAO.merge(order);
				}
			return true;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}
	}

	// ===========================尾款确认===================================
	@Override
	public List<Map<String, Object>> getConfirmFinalPaymentList(String actorId) {
		return service.getOrderList(actorId, TASK_CONFIRM_FINAL_PAYMENT);
	}

	@Override
	public List<Map<String, Object>> getSearchConfirmFinalPaymentList(
			String actorId, String ordernumber, String customername,
			String stylename, String startdate, String enddate,
			Integer[] employeeIds) {
		return service.getSearchOrderList(actorId, ordernumber, customername,
				 stylename, startdate, enddate,employeeIds, TASK_CONFIRM_FINAL_PAYMENT);
 	}

	
	@Override
	public Map<String, Object> getConfirmFinalPaymentDetail(String actorId,
			Integer orderId) {
		Map<String, Object> model = service.getBasicOrderModelWithQuote(
				actorId, TASK_CONFIRM_FINAL_PAYMENT, orderId);
		Order order = (Order) model.get("order");
		Quote quote = (Quote) model.get("quote");
		Float price = quote.getOuterPrice();
		model.put("price", price);
		/*Produce p=new Produce();
		p.setOid(orderId);
		p.setType(Produce.TYPE_QUALIFIED);
		List<Produce>list=produceDAO.findByExample(p);
		Integer amount=0;
		for(Produce produce:list){
			amount+=produce.getProduceAmount();
		}*/
		
		int amount = 0;
		List<CheckRecord> list = checkRecordDAO.findByOrderId(orderId);
		for(CheckRecord cr: list){
			amount += cr.getQualifiedAmount();
		}
		model.put("number", amount);
		List<DeliveryRecord> deliveryRecord = deliveryRecordDAO.findSampleRecordByOrderId(orderId);
		model.put("deliveryRecord", deliveryRecord);
		
		//model.put("total",  order.getTotalMoney() * 0.7);
		model.put("taskName", "确认大货尾款");
		model.put("tabName", "大货尾款");
		model.put("type", "大货尾款");
		model.put("url", "/finance/confirmFinalPaymentSubmit.do");
//		model.put("moneyName", "70%尾款");
		model.put("moneyName", "大货尾款");
		Money money=new Money();
		money.setOrderId(orderId);
		money.setMoneyType("大货定金");
		model.put("deposit", moneyDAO.findByExample(money).get(0).getMoneyAmount());
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
	public boolean confirmFinalPaymentSubmit(String actorId, String taskId,
			boolean result, Money money) {
		if (result) {
			moneyDAO.save(money);
		}
		Map<String, Object> data = new HashMap<>();
		data.put(RESULT_MONEY, result);
		try {
			mainProcessService.completeTask(taskId, actorId, data);
			return true;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void returnDepositSubmit(String actorId, String taskId) {
		Map<String, Object> data = new HashMap<>();
 		try {
			mainProcessService.completeTask(taskId, actorId, data);
 		} catch (InterruptedException e) {
 			e.printStackTrace();
 		}
		
	}
	

	@Override
	public void returnDepositSubmit(String actorId, String taskId, Integer orderId) {
		Order order = orderDAO.findById(orderId);
		Map<String, Object> data = new HashMap<>();
 		try {
			mainProcessService.completeTask(taskId, actorId, data);
			order.setOrderState("1");
			orderDAO.attachDirty(order);
 		} catch (InterruptedException e) {
 			e.printStackTrace();
 		}
	}
	
	@Override
	public List<Map<String, Object>> getProcessState(final Integer orderId) {
		String processId=orderDAO.findById(orderId).getProcessId();
		return mainProcessService.getProcessStates(processId);
	}

	@Override
	public ArrayList<String> getProcessStateName(final Integer orderId) {

				Order order = orderDAO.findById(orderId);
				String processId= order.getProcessId();
				return (ArrayList<String>) mainProcessService.getProcessStateNames(processId);
	}
	
	@Autowired
	private MainProcessService mainProcessService;
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
	private AccessoryDAO accessoryDAO;
	@Autowired
	private FabricDAO fabricDAO;
	@Autowired
	private ProduceDAO produceDAO;
	@Autowired
	private MoneyDAO moneyDAO;
	@Autowired
	private QuoteDAO quoteDAO;
	@Autowired
	private FabricCostDAO fabricCostDAO;
	@Autowired
	private AccessoryCostDAO accessoryCostDAO;
	@Autowired
	private ServiceUtil service;
	@Autowired
	private CheckRecordDAO checkRecordDAO;
	@Autowired
	private DeliveryRecordDAO deliveryRecordDAO;
	
	public final static String ACTOR_FINANCE_MANAGER = "financeManager";
	public final static String TASK_CONFIRM_SAMPLE_MONEY = "confirmSampleMoney";
	public final static String TASK_CONFIRM_DEPOSIT = "confirmDeposit";
	public final static String TASK_RETURN_DEPOSIT = "returnDeposit";	
	public final static String TASK_CONFIRM_FINAL_PAYMENT = "confirmFinalPayment";
	
	public final static String RESULT_MONEY = "receiveMoney";
	
	@Override
	public Map<String, Object> getPrintProcurementOrderDetail(Integer orderId,List<Produce> produces) {
		Map<String,Object>model=new HashMap<String,Object>();
		Order order = orderDAO.findById(orderId);
		model.put("order", order);
		model.put("customer",customerDAO.findById(order.getCustomerId()));
		model.put("employee", employeeDAO.findById(order.getEmployeeId()));
		model.put("logistics", logisticsDAO.findById(orderId));
		model.put("fabrics", fabricCostDAO.findByOrderId(orderId));
		model.put("accessorys", accessoryDAO.findByOrderId(orderId));
		model.put("orderId", service.getOrderId(order));
		Produce produce = new Produce();
		produce.setOid(orderId);
		produce.setType(Produce.TYPE_SAMPLE_PRODUCE);
		model.put("sample", produceDAO.findByExample(produce));
		produce.setType(Produce.TYPE_PRODUCE);
		model.put("produce", produceDAO.findByExample(produce));
		return model;

	}

	@Override
	public boolean confirmFinalPaymentSubmit(String actorId, String taskId,
			boolean result, Money money, Integer orderId) {
		Order order = orderDAO.findById(orderId);
		if (result) {
			moneyDAO.save(money);
/*			if(order.getIsHaoDuoYi()==1&&(order.getLogisticsState()==2)){//如果是好多衣客户且产品已入库，则该订单完成
				order.setOrderState("Done");
			}*/
		}
		Map<String, Object> data = new HashMap<>();
		data.put(RESULT_MONEY, result);
		try {
			mainProcessService.completeTask(taskId, actorId, data);
			if(result==false){//如果result的的值为false，即为尾款收取失败，流程会异常终止，将orderState设置为1
				order.setOrderState("1");	
			}
			orderDAO.attachDirty(order);
			return true;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}
	}

















}
