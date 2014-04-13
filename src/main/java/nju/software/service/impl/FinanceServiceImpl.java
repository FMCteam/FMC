package nju.software.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.task.query.TaskSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nju.software.dao.impl.AccessoryDAO;
import nju.software.dao.impl.CustomerDAO;
import nju.software.dao.impl.EmployeeDAO;
import nju.software.dao.impl.FabricDAO;
import nju.software.dao.impl.LogisticsDAO;
import nju.software.dao.impl.MoneyDAO;
import nju.software.dao.impl.OrderDAO;
import nju.software.dao.impl.PackageDAO;
import nju.software.dao.impl.ProduceDAO;
import nju.software.dao.impl.ProductDAO;
import nju.software.dataobject.Money;
import nju.software.dataobject.Produce;
import nju.software.model.OrderInfo;
import nju.software.service.FinanceService;
import nju.software.util.JbpmAPIUtil;

@Service("financeServiceImpl")
public class FinanceServiceImpl implements FinanceService {

	
	//===========================样衣金确认=================================
	@Override
	public List<OrderInfo> getConfirmSampleMoneyList(String actorId) {
		// TODO Auto-generated method stub
		List<TaskSummary> tasks = jbpmAPIUtil.getAssignedTasksByTaskname(
				actorId, TASK_CONFIRM_SAMPLE_MONEY);
		List<OrderInfo> list = new ArrayList<>();
		for (TaskSummary task : tasks) {
			Integer orderId = (Integer) jbpmAPIUtil
					.getVariable(task, "orderId");
			OrderInfo orderInfo = new OrderInfo();
			orderInfo.setOrder(orderDAO.findById(orderId));
			orderInfo.setTask(task);
			list.add(orderInfo);
		}
		return list;
	}

	
	@Override
	public OrderInfo getConfirmSampleMoneyDetail(String actorId, Integer orderId) {
		// TODO Auto-generated method stub
		TaskSummary task = jbpmAPIUtil.getTask(actorId,
				TASK_CONFIRM_SAMPLE_MONEY, orderId);
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setOrder(orderDAO.findById(orderId));
		orderInfo.setEmployee(employeeDAO.findById(orderInfo.getOrder()
				.getEmployeeId()));
		orderInfo.setLogistics(logisticsDAO.findById(orderId));
		orderInfo.setAccessorys(accessoryDAO.findByOrderId(orderId));
		orderInfo.setFabrics(fabricDAO.findByOrderId(orderId));
		Produce produce=new Produce();
		produce.setType(Produce.TYPE_PRODUCE);
		produce.setOid(orderId);
		orderInfo.setProduce(produceDAO.findByExample(produce));
		produce.setType(Produce.TYPE_SAMPLE_PRODUCE);
		orderInfo.setSample(produceDAO.findByExample(produce));
		orderInfo.setTask(task);
		return orderInfo;
	}

	@Override
	public boolean confirmSampleMoneySubmit(String actorId,long taskId,boolean receivedsamplejin, Money money) {
		// TODO Auto-generated method stub
		if (receivedsamplejin) {
			moneyDAO.save(money);
		}
		Map<String, Object> data = new HashMap<>();
		data.put("receivedsamplejin", receivedsamplejin);
		try {
			jbpmAPIUtil.completeTask(taskId, data, actorId);
			return true;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	
	//===========================定金确认===================================
	@Override
	public List<OrderInfo> getConfirmDepositList(String actorId) {
		// TODO Auto-generated method stub
		List<TaskSummary> tasks = jbpmAPIUtil.getAssignedTasksByTaskname(
				actorId, TASK_CONFIRM_DEPOSIT);
		List<OrderInfo> list = new ArrayList<>();
		for (TaskSummary task : tasks) {
			Integer orderId = (Integer) jbpmAPIUtil
					.getVariable(task, "orderId");
			OrderInfo orderInfo = new OrderInfo();
			orderInfo.setOrder(orderDAO.findById(orderId));
			orderInfo.setTask(task);
			list.add(orderInfo);
		}
		return list;
	}

	@Override
	public OrderInfo getConfirmDepositDetail(String actorId, Integer orderId) {
		// TODO Auto-generated method stub
		TaskSummary task = jbpmAPIUtil.getTask(actorId, TASK_CONFIRM_DEPOSIT,
				orderId);
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setOrder(orderDAO.findById(orderId));
		orderInfo.setTask(task);
		return orderInfo;
	}

	@Override
	public boolean confirmDepositSubmit(String actorId,long taskId,boolean epositok, Money money){
		// TODO Auto-generated method stub
		if (epositok) {
			moneyDAO.save(money);
		}
		Map<String, Object> data = new HashMap<>();
		data.put("epositok", epositok);
		try {
			jbpmAPIUtil.completeTask(taskId, data, actorId);
			return true;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	
	//===========================尾款确认===================================
	@Override
	public List<OrderInfo> getConfirmFinalPaymentList(String actorId) {
		// TODO Auto-generated method stub
		List<TaskSummary> tasks = jbpmAPIUtil.getAssignedTasksByTaskname(
				actorId, TASK_CONFIRM_FINAL_PAYMENT);
		List<OrderInfo> list = new ArrayList<>();
		for (TaskSummary task : tasks) {
			Integer orderId = (Integer) jbpmAPIUtil
					.getVariable(task, "orderId");
			OrderInfo orderInfo = new OrderInfo();
			orderInfo.setOrder(orderDAO.findById(orderId));
			orderInfo.setTask(task);
			list.add(orderInfo);
		}
		return list;
	}

	
	@Override
	public OrderInfo getConfirmFinalPaymentDetail(String actorId,
			Integer orderId) {
		// TODO Auto-generated method stub
		TaskSummary task = jbpmAPIUtil.getTask(actorId,
				TASK_CONFIRM_FINAL_PAYMENT, orderId);
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setOrder(orderDAO.findById(orderId));
		orderInfo.setTask(task);
		return orderInfo;
	}

	
	@Override
	public boolean confirmFinalPaymentSubmit(String actorId,long taskId,boolean paymentok, Money money) {
		// TODO Auto-generated method stub
		if (paymentok) {
			moneyDAO.save(money);
		}
		Map<String, Object> data = new HashMap<>();
		data.put("paymentok", paymentok);
		try {
			jbpmAPIUtil.completeTask(taskId, data, actorId);
			return true;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	
	@Autowired
	private JbpmAPIUtil jbpmAPIUtil;
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
	
	public final static String ACTOR_FINANCE_MANAGER = "financeManager";
	public final static String TASK_CONFIRM_SAMPLE_MONEY = "confirmSampleMoney";
	public final static String TASK_CONFIRM_DEPOSIT = "confirmDeposit";
	public final static String TASK_CONFIRM_FINAL_PAYMENT = "confirmFinalPayment";
}
