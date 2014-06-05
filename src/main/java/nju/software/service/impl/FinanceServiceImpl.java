package nju.software.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drools.command.Context;
import org.drools.command.impl.GenericCommand;
import org.drools.command.impl.KnowledgeCommandContext;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.NodeInstance;
import org.drools.runtime.process.ProcessInstance;
import org.drools.runtime.process.WorkflowProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nju.software.dao.impl.AccessoryCostDAO;
import nju.software.dao.impl.AccessoryDAO;
import nju.software.dao.impl.CustomerDAO;
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
import nju.software.dataobject.Money;
import nju.software.dataobject.Order;
import nju.software.dataobject.Produce;
import nju.software.dataobject.Quote;
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
	public boolean confirmSampleMoneySubmit(String actorId, long taskId,
			boolean result, Money money) {
		// TODO Auto-generated method stub
		if (result) {
			moneyDAO.save(money);
		}
		Map<String, Object> data = new HashMap<>();
		data.put(RESULT_MONEY, result);
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
	public boolean confirmDepositSubmit(String actorId, long taskId,
			boolean result, Money money) {
		// TODO Auto-generated method stub
		if (result) {
			moneyDAO.save(money);
		}
		Map<String, Object> data = new HashMap<>();
		data.put(RESULT_MONEY, result);
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
		Map<String, Object> model = service.getBasicOrderModelWithQuote(
				actorId, TASK_CONFIRM_FINAL_PAYMENT, orderId);
		Order order = (Order) model.get("order");
		Quote quote = (Quote) model.get("quote");
		Float price = quote.getOuterPrice();
		model.put("price", price);
		Produce p=new Produce();
		p.setOid(orderId);
		p.setType(Produce.TYPE_QUALIFIED);
		List<Produce>list=produceDAO.findByExample(p);
		Integer amount=0;
		for(Produce produce:list){
			amount+=produce.getProduceAmount();
		}
		model.put("number", amount);
		model.put("total",  order.getTotalMoney() * 0.7);
		model.put("taskName", "确认大货尾款");
		model.put("tabName", "大货尾款");
		model.put("type", "大货尾款");
		model.put("url", "/finance/confirmFinalPaymentSubmit.do");
		model.put("moneyName", "70%尾款");
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
	public boolean confirmFinalPaymentSubmit(String actorId, long taskId,
			boolean result, Money money) {
		// TODO Auto-generated method stub
		if (result) {
			moneyDAO.save(money);
		}
		Map<String, Object> data = new HashMap<>();
		data.put(RESULT_MONEY, result);
		try {
			jbpmAPIUtil.completeTask(taskId, data, actorId);
			return true;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Map<String, Object>> getProcessState(final Integer orderId) {
		// //System.out.println("size:"+jbpmAPIUtil.getKsession().getProcessInstances().size());
		final List<Map<String, Object>> list = new ArrayList<>();
		jbpmAPIUtil.getKsession().execute(new GenericCommand<String>() {
			public String execute(Context context) {
				StatefulKnowledgeSession ksession = ((KnowledgeCommandContext) context)
						.getStatefulKnowledgesession();
				long processId=orderDAO.findById(orderId).getProcessId();
				org.jbpm.process.instance.ProcessInstance processInstance = (org.jbpm.process.instance.ProcessInstance) ksession
						.getProcessInstance(processId);
				if(processInstance==null){
					return null;
				}
				for (NodeInstance nodeInstance : ((org.jbpm.workflow.instance.WorkflowProcessInstance) processInstance)
						.getNodeInstances()) {
					Map<String, Object> data = nodeInstance.getNode()
							.getMetaData();
					Map<String, Object> data2 = new HashMap<String, Object>();
					data2.put("x", data.get("x"));
					data2.put("y", data.get("y"));
					data2.put("width", data.get("width"));
					data2.put("height", data.get("height"));
					list.add(data2);
				}
				return null;
			}
		});
		return list;
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
	private QuoteDAO quoteDAO;
	@Autowired
	private FabricCostDAO fabricCostDAO;
	@Autowired
	private AccessoryCostDAO accessoryCostDAO;
	@Autowired
	private ServiceUtil service;

	public final static String ACTOR_FINANCE_MANAGER = "financeManager";
	public final static String TASK_CONFIRM_SAMPLE_MONEY = "confirmSampleMoney";
	public final static String TASK_CONFIRM_DEPOSIT = "confirmDeposit";
	public final static String TASK_CONFIRM_FINAL_PAYMENT = "confirmFinalPayment";
	public final static String RESULT_MONEY = "receiveMoney";
}
