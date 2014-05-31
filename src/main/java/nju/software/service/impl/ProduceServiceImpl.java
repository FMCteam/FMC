package nju.software.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.drools.runtime.StatefulKnowledgeSession;
import org.jbpm.task.query.TaskSummary;
import org.jbpm.workflow.instance.WorkflowProcessInstance;
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
import nju.software.dataobject.Account;
import nju.software.dataobject.Order;
import nju.software.dataobject.Produce;
import nju.software.dataobject.Quote;
import nju.software.service.ProduceService;
import nju.software.util.JbpmAPIUtil;

@Service("produceServiceImpl")
public class ProduceServiceImpl implements ProduceService {

	@Override
	public List<Map<String, Object>> getVerifyProduceList() {
		// TODO Auto-generated method stub
		return service.getOrderList(ACTOR_PRODUCE_MANAGER, TASK_VERIFY_PRODUCE);
	}

	@Override
	public Map<String, Object> getVerifyProduceDetail(int orderId) {
		// TODO Auto-generated method stub
		return service.getBasicOrderModelWithQuote(ACTOR_PRODUCE_MANAGER,
				TASK_VERIFY_PRODUCE, orderId);
	}

	@Override
	public boolean verifyProduceSubmit(long taskId, boolean result,
			String comment) {
		// TODO Auto-generated method stub
		Map<String, Object> data = new HashMap<>();
		data.put(RESULT_PRODUCE, result);
		data.put(RESULT_PRODUCE_COMMENT, comment);
		try {
			jbpmAPIUtil.completeTask(taskId, data, ACTOR_PRODUCE_MANAGER);
			return true;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Map<String, Object>> getComputeProduceCostList() {
		return service.getOrderList(ACTOR_PRODUCE_MANAGER,
				TASK_COMPUTE_PRODUCE_COST);
	}

	@Override
	public Map<String, Object> getComputeProduceCostInfo(Integer orderId) {
		return service.getBasicOrderModelWithQuote(ACTOR_PRODUCE_MANAGER,
				TASK_COMPUTE_PRODUCE_COST, orderId);
	}

	@Override
	public void computeProduceCostSubmit(int orderId, long taskId,
			float cut_cost, float manage_cost, float nali_cost,
			float ironing_cost, float swing_cost, float package_cost,
			float other_cost, float design_cost) {

		Quote quote = QuoteDAO.findById(orderId);

		if (quote == null) {
			quote = new Quote();
			quote.setOrderId(orderId);
			quote.setCutCost(cut_cost);
			quote.setManageCost(manage_cost);
			quote.setSwingCost(swing_cost);
			quote.setIroningCost(ironing_cost);
			quote.setNailCost(nali_cost);
			quote.setPackageCost(package_cost);
			quote.setOtherCost(other_cost);
			quote.setDesignCost(design_cost);
			QuoteDAO.save(quote);
		} else {
			quote.setCutCost(cut_cost);
			quote.setManageCost(manage_cost);
			quote.setSwingCost(swing_cost);
			quote.setIroningCost(ironing_cost);
			quote.setNailCost(nali_cost);
			quote.setPackageCost(package_cost);
			quote.setOtherCost(other_cost);
			quote.setDesignCost(design_cost);
		}

		float producecost = cut_cost + manage_cost + swing_cost + ironing_cost
				+ nali_cost + package_cost + other_cost
				+ quote.getFabricCost() + quote.getAccessoryCost();
		quote.setSingleCost(producecost);
		QuoteDAO.attachDirty(quote);

		Map<String, Object> data = new HashMap<String, Object>();
		try {
			jbpmAPIUtil.completeTask(taskId, data, ACTOR_PRODUCE_MANAGER);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// =========================样衣生产========================
	@Override
	public List<Map<String, Object>> getProduceSampleList() {
		// TODO Auto-generated method stub
		return service.getOrderList(ACTOR_PRODUCE_MANAGER, TASK_PRODUCE_SAMPLE);
	}

	@Override
	public Map<String, Object> getProduceSampleDetail(Integer orderId) {
		// TODO Auto-generated method stub
		return service.getBasicOrderModelWithQuote(ACTOR_PRODUCE_MANAGER,
				TASK_PRODUCE_SAMPLE, orderId);
	}

	@Override
	public boolean produceSampleSubmit(long taskId, boolean result) {
		// TODO Auto-generated method stub
		Map<String, Object> data = new HashMap<String, Object>();
		data.put(RESULT_PRODUCE, result);
		try {
			jbpmAPIUtil.completeTask(taskId, data, ACTOR_PRODUCE_MANAGER);
			return true;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	// =======================批量生产========================
	@Override
	public List<Map<String, Object>> getProduceList() {
		// TODO Auto-generated method stub
		return service.getOrderList(ACTOR_PRODUCE_MANAGER, TASK_PRODUCE);
	}

	@Override
	public Map<String, Object> getProduceDetail(Integer orderId) {
		// TODO Auto-generated method stub
		return service.getBasicOrderModelWithQuote(ACTOR_PRODUCE_MANAGER, TASK_PRODUCE,
				orderId);
	}

	@Override
	public boolean pruduceSubmit(long taskId, boolean result,
			List<Produce> produceList) {
		if (result) {
			 for (int i = 0; i < produceList.size(); i++) {
			 produceDAO.save(produceList.get(i));
			 }
		}
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			data.put(RESULT_PRODUCE, result);
			jbpmAPIUtil.completeTask(taskId, data, ACTOR_PRODUCE_MANAGER);
			return true;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}


	public List<Produce> getProduceList(int orderId, String produceColor,
			String produceXS, String produceS, String produceM,
			String produceL, String produceXL, String produceXXL, String type) {
		String[] color = produceColor.split(",");
		String[] xs = produceXS.split(",");
		String[] s = produceS.split(",");
		String[] m = produceM.split(",");
		String[] l = produceL.split(",");
		String[] xl = produceXL.split(",");
		String[] xxl = produceXXL.split(",");
		List<Produce> produceList = new ArrayList<Produce>();
		for (int i = 0; i < color.length; i++) {
			System.out.println(color[i] + xs[i] + xxl[i]);
			Produce produce = new Produce();
			produce.setOid(orderId);
			produce.setColor(color[i]);
			produce.setXs(Integer.parseInt(xs[i]));
			produce.setS(Integer.parseInt(s[i]));
			produce.setM(Integer.parseInt(m[i]));
			produce.setL(Integer.parseInt(l[i]));
			produce.setXl(Integer.parseInt(xl[i]));
			produce.setXxl(Integer.parseInt(xxl[i]));
			produce.setType(type);
			int total = produce.getXs() + produce.getS() + produce.getM() + produce.getL() +
					produce.getXl() + produce.getXxl();
			produce.setProduceAmount(total);
			produceList.add(produce);
		}
		return produceList;
	}

	public final static String ACTOR_PRODUCE_MANAGER = "produceManager";
	public final static String TASK_VERIFY_PRODUCE = "verifyProduce";
	public final static String TASK_COMPUTE_PRODUCE_COST = "computeProduceCost";
	public final static String TASK_PRODUCE_SAMPLE = "produceSample";
	public final static String TASK_PRODUCE = "produce";
	public final static String RESULT_PRODUCE = "produce";
	public final static String RESULT_PRODUCE_COMMENT = "produceComment";
	@Autowired
	private OrderDAO orderDAO;
	@Autowired
	private JbpmAPIUtil jbpmAPIUtil;
	@Autowired
	private LogisticsDAO logisticsDAO;
	@Autowired
	private QuoteDAO QuoteDAO;
	@Autowired
	private FabricDAO fabricDAO;
	@Autowired
	private AccessoryDAO accessoryDAO;
	@Autowired
	private QuoteDAO quoteDAO;
	@Autowired
	private ProductDAO productDAO;
	@Autowired
	private ProduceDAO produceDAO;
	@Autowired
	private PackageDAO packageDAO;
	@Autowired
	private PackageDetailDAO packageDetailDAO;
	@Autowired
	private ServiceUtil service;
}
