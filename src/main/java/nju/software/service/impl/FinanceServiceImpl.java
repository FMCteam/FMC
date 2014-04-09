package nju.software.service.impl;

import java.io.ObjectOutputStream.PutField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.task.query.TaskSummary;
import org.jbpm.workflow.instance.WorkflowProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nju.software.dao.impl.MoneyDAO;
import nju.software.dao.impl.OrderDAO;
import nju.software.dao.impl.QuoteDAO;
import nju.software.dataobject.Account;
import nju.software.dataobject.Money;
import nju.software.dataobject.Order;
import nju.software.dataobject.Quote;
import nju.software.model.OrderInfo;
import nju.software.model.SampleMoneyConfirmTaskSummary;
import nju.software.service.FinanceService;
import nju.software.util.JbpmAPIUtil;


@Service("financeServiceImpl")
public class FinanceServiceImpl implements FinanceService {
	
	public final static String ACTOR_FINANCE_MANAGER = "financeManager";
	public final static String TASK_CONFIRM_SAMPLE_MONEY = "confirmSampleMoney";
	public final static String TASK_CONFIRM_DEPOSIT = "confirmDeposit";
	public final static String TASK_CONFIRM_FINAL_PAYMENT = "confirmFinalPayment";
	
	
	@Autowired
	private JbpmAPIUtil jbpmAPIUtil;
	@Autowired
	private OrderDAO orderDAO;
	@Autowired
	private QuoteDAO quoteDAO;
	@Autowired
	private MoneyDAO moneyDAO;
	
	private String actorId="CAIWUZHUGUAN";
	
	
	

	


	

	
	
	
	@Override
	public boolean confirmSampleMoneySubmit(Account account, int orderId, long taskId,
			long processId, boolean receivedsamplejin, Money money) {
		// TODO Auto-generated method stub
//		String actorId = account.getUserRole();
		String actorId = ACTOR_FINANCE_MANAGER;
		//需要获取task中的数据	
		WorkflowProcessInstance process=(WorkflowProcessInstance) jbpmAPIUtil.getKsession().getProcessInstance(processId);
		int orderId_process  = (int) process.getVariable("orderId");
		System.out.println("orderId: " + orderId);
		if (orderId == orderId_process) {
			//如果收到样衣制作金，创建money记录
			if (receivedsamplejin) {
				moneyDAO.save(money);
			}
			//修改流程参数
			Map<String, Object> data = new HashMap<>();
			data.put("receivedsamplejin", receivedsamplejin);
			System.out.println("taskId" + taskId);
			//直接进入到下一个流程时
			try {
				jbpmAPIUtil.completeTask(taskId, data, actorId);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}

		return false;
		
	}

	@Override
	public boolean confirmDepositSubmit(Account account, int orderId, long taskId,
			long processId, boolean epositok, Money money) {
		// TODO Auto-generated method stub
//		String actorId = account.getUserRole();
		String actorId = ACTOR_FINANCE_MANAGER;
		//需要获取task中的数据	
		WorkflowProcessInstance process=(WorkflowProcessInstance) jbpmAPIUtil.getKsession().getProcessInstance(processId);
		int orderId_process  = (int) process.getVariable("orderId");
		System.out.println("orderId: " + orderId);
		if (orderId == orderId_process) {
			//如果收到样衣制作金，创建money记录
			if (epositok) {
				moneyDAO.save(money);
			}
			//修改流程参数
			Map<String, Object> data = new HashMap<>();
			data.put("epositok", epositok);
			//直接进入到下一个流程时
			try {
				jbpmAPIUtil.completeTask(taskId, data, actorId);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}

		return false;
	}

	@Override
	public boolean confirmFinalPaymentSubmit(Account account, int orderId, long taskId,
			long processId, boolean paymentok, Money money) {
		// TODO Auto-generated method stub
//		String actorId = account.getUserRole();
		String actorId = ACTOR_FINANCE_MANAGER;
		//需要获取task中的数据	
		WorkflowProcessInstance process=(WorkflowProcessInstance) jbpmAPIUtil.getKsession().getProcessInstance(processId);
		int orderId_process  = (int) process.getVariable("orderId");
		System.out.println("orderId: " + orderId);
		if (orderId == orderId_process) {
			//如果收到样衣制作金，创建money记录
			if (paymentok) {
				moneyDAO.save(money);
			}
			//修改流程参数
			Map<String, Object> data = new HashMap<>();
			data.put("paymentok", paymentok);
			//直接进入到下一个流程时
			try {
				jbpmAPIUtil.completeTask(taskId, data, actorId);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}

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
		orderInfo.setTask(task);
		return orderInfo;
	}

	@Override
	public boolean confirmSampleMoneySubmit() {
		// TODO Auto-generated method stub
		return false;
	}

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
		TaskSummary task = jbpmAPIUtil.getTask(actorId,
				TASK_CONFIRM_DEPOSIT, orderId);
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setOrder(orderDAO.findById(orderId));
		orderInfo.setTask(task);
		return orderInfo;
	}

	@Override
	public boolean confirmDepositSubmit() {
		// TODO Auto-generated method stub
		return false;
	}

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
	public boolean confirmFinalPaymentSubmit() {
		// TODO Auto-generated method stub
		return false;
	}
}
