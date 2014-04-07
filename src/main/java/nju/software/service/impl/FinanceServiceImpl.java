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
	public List<SampleMoneyConfirmTaskSummary> getSampleMoneyConfirmTaskSummaryList() {
		// TODO Auto-generated method stub
		
		List<TaskSummary> list =jbpmAPIUtil.getAssignedTasksByTaskname(actorId, "comfirm_sample");
		List<SampleMoneyConfirmTaskSummary>taskList=new ArrayList<>();
		
		for(TaskSummary task:list){
			Integer orderId=(Integer) getVariable(task, "orderId");
			Order order=orderDAO.findById(orderId);
			Quote quote=(Quote) quoteDAO.findByProperty("orderId", orderId).get(0);
			taskList.add(SampleMoneyConfirmTaskSummary.getInstance(order, quote));
		}
		return taskList;
	}

	@Override
	public SampleMoneyConfirmTaskSummary getSampleMoneyConfirmTask(long taskId,Integer orderId) {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public void sampleMoneyConfirm(long taskId, Money money) {
		// TODO Auto-generated method stub
		moneyDAO.save(money);
		Map<String,Object>data=new HashMap<String,Object>();
		data.put("receivedsamplejin", true);
		try {
			jbpmAPIUtil.completeTask(taskId, data, actorId);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void sampleMoneyNoConfirm(long taskId) {
		// TODO Auto-generated method stub
		Map<String,Object>data=new HashMap<String,Object>();
		data.put("receivedsamplejin", false);
		try {
			jbpmAPIUtil.completeTask(taskId, data, actorId);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private Object getVariable(TaskSummary task,String name){
		long processId = task.getProcessInstanceId();
		WorkflowProcessInstance process=(WorkflowProcessInstance) jbpmAPIUtil.getKsession().getProcessInstance(processId);
		return process.getVariable("orderId");
	}

	/**
	 * 确认样衣制作金
	 */
	@Override
	public boolean confirmSample(Account account, int orderId, long taskId,
			long processId, boolean receivedsamplejin, Money money) {
		// TODO Auto-generated method stub
//		String actorId = account.getUserRole();
		String actorId = "CAIWUZHUGUAN";
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
	public boolean confirmDeposit(Account account, int orderId, long taskId,
			long processId, boolean epositok, Money money) {
		// TODO Auto-generated method stub
//		String actorId = account.getUserRole();
		String actorId = "CAIWUZHUGUAN";
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
	public boolean confirmPayment(Account account, int orderId, long taskId,
			long processId, boolean paymentok, Money money) {
		// TODO Auto-generated method stub
//		String actorId = account.getUserRole();
		String actorId = "CAIWUZHUGUAN";
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
}
