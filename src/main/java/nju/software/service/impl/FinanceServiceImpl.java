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
import nju.software.dataobject.Order;
import nju.software.dataobject.Produce;
import nju.software.model.OrderInfo;
import nju.software.service.FinanceService;
import nju.software.util.JbpmAPIUtil;

@Service("financeServiceImpl")
public class FinanceServiceImpl implements FinanceService {

	// ===========================样衣金确认=================================
	@Override
	public List<Map<String, Object>> getConfirmSampleMoneyList(String actorId) {
		// TODO Auto-generated method stub
		return service.getOrderList(actorId, TASK_CONFIRM_SAMPLE_MONEY);
	}

	
	@Override
	public Map<String, Object> getConfirmSampleMoneyDetail(String actorId,
			Integer orderId) {
		// TODO Auto-generated method stub
		TaskSummary task = jbpmAPIUtil.getTask(actorId,
				TASK_CONFIRM_SAMPLE_MONEY, orderId);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("order", orderDAO.findById(orderId));
		model.put("task", task);
		model.put("employee", employeeDAO.findById(orderDAO.findById(orderId)
				.getEmployeeId()));
		model.put("logistics", logisticsDAO.findById(orderId));
		model.put("fabrics", fabricDAO.findByOrderId(orderId));
		model.put("accessorys", accessoryDAO.findByOrderId(orderId));

		Produce produce = new Produce();
		produce.setType(Produce.TYPE_PRODUCE);
		produce.setOid(orderId);
		model.put("produce", produceDAO.findByExample(produce));
		produce.setType(Produce.TYPE_SAMPLE_PRODUCE);
		model.put("sample", produceDAO.findByExample(produce));
		
		
		model.put("taskName", "确认样衣制作金");
		model.put("tabName", "制版费用");
		model.put("type", "样衣制作金");
		model.put("url", "/finance/confirmSampleMoneySubmit.do");
		model.put("moneyName", "样衣制作金");
		model.put("number", 10);
		model.put("price", 200);
		model.put("total", 2000);
		return model;
	}

	@Override
	public boolean confirmSampleMoneySubmit(String actorId, long taskId,
			boolean receivedsamplejin, Money money) {
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

	// ===========================定金确认===================================
	@Override
	public List<Map<String, Object>> getConfirmDepositList(String actorId) {
		// TODO Auto-generated method stub
		return service.getOrderList(actorId, TASK_CONFIRM_DEPOSIT);
	}

	@Override
	public Map<String, Object> getConfirmDepositDetail(String actorId,
			Integer orderId) {
		// TODO Auto-generated method stub
		TaskSummary task = jbpmAPIUtil.getTask(actorId,
				TASK_CONFIRM_SAMPLE_MONEY, orderId);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("order", orderDAO.findById(orderId));
		model.put("task", task);
		model.put("employee", employeeDAO.findById(orderDAO.findById(orderId)
				.getEmployeeId()));
		model.put("logistics", logisticsDAO.findById(orderId));
		model.put("fabrics", fabricDAO.findByOrderId(orderId));
		model.put("accessorys", accessoryDAO.findByOrderId(orderId));

		Produce produce = new Produce();
		produce.setType(Produce.TYPE_PRODUCE);
		produce.setOid(orderId);
		model.put("produce", produceDAO.findByExample(produce));
		produce.setType(Produce.TYPE_SAMPLE_PRODUCE);
		model.put("sample", produceDAO.findByExample(produce));
		
		model.put("taskName", "确认大货定金");
		model.put("tabName", "大货定金");
		model.put("type", "大货定金");
		model.put("url", "/finance/confirmDepositSubmit.do");
		model.put("moneyName", "30%定金");
		model.put("number", 10);
		model.put("price", 200);
		model.put("total", 600);

		return model;
	}

	@Override
	public boolean confirmDepositSubmit(String actorId, long taskId,
			boolean epositok, Money money) {
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

	// ===========================尾款确认===================================
	@Override
	public List<Map<String, Object>> getConfirmFinalPaymentList(String actorId) {
		// TODO Auto-generated method stub
		return service.getOrderList(actorId, TASK_CONFIRM_FINAL_PAYMENT);
	}

	@Override
	public Map<String, Object> getConfirmFinalPaymentDetail(String actorId,
			Integer orderId) {
		// TODO Auto-generated method stub
		TaskSummary task = jbpmAPIUtil.getTask(actorId,
				TASK_CONFIRM_SAMPLE_MONEY, orderId);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("order", orderDAO.findById(orderId));
		model.put("task", task);
		model.put("employee", employeeDAO.findById(orderDAO.findById(orderId)
				.getEmployeeId()));
		model.put("logistics", logisticsDAO.findById(orderId));
		model.put("fabrics", fabricDAO.findByOrderId(orderId));
		model.put("accessorys", accessoryDAO.findByOrderId(orderId));

		Produce produce = new Produce();
		produce.setType(Produce.TYPE_PRODUCE);
		produce.setOid(orderId);
		model.put("produce", produceDAO.findByExample(produce));
		produce.setType(Produce.TYPE_SAMPLE_PRODUCE);
		model.put("sample", produceDAO.findByExample(produce));
		
		
		model.put("taskName", "确认大货尾款");
		model.put("tabName", "大货尾款");
		model.put("type", "大货尾款");
		model.put("url", "/finance/confirmDepositSubmit.do");
		model.put("moneyName", "70%尾款");
		model.put("number", 10);
		model.put("price", 200);
		model.put("total", 1400);
		return model;
	}

	@Override
	public boolean confirmFinalPaymentSubmit(String actorId, long taskId,
			boolean paymentok, Money money) {
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
	@Autowired
	private ServiceUtil service;

	public final static String ACTOR_FINANCE_MANAGER = "financeManager";
	public final static String TASK_CONFIRM_SAMPLE_MONEY = "confirmSampleMoney";
	public final static String TASK_CONFIRM_DEPOSIT = "confirmDeposit";
	public final static String TASK_CONFIRM_FINAL_PAYMENT = "confirmFinalPayment";
}
