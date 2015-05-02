package nju.software.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import nju.software.dataobject.Order;
import nju.software.dataobject.Produce;
import nju.software.dataobject.Quote;
import nju.software.process.service.FMCProcessService;
import nju.software.service.ProduceService;

@Service("produceServiceImpl")
public class ProduceServiceImpl implements ProduceService {

	@Override
	public List<Map<String, Object>> getVerifyProduceList() {
		return service.getOrderList(ACTOR_PRODUCE_MANAGER, TASK_VERIFY_PRODUCE);
	}
	

	@Override
	public List<Map<String, Object>> getSearchVerifyProduceList(
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds) {
		return service.getSearchOrderList(ACTOR_PRODUCE_MANAGER,  ordernumber,  customername,  stylename,
				 startdate,  enddate,  employeeIds,
				TASK_VERIFY_PRODUCE);
	}
	
	@Override
	public Map<String, Object> getVerifyProduceDetail(int orderId) {
		return service.getBasicOrderModelWithQuote(ACTOR_PRODUCE_MANAGER,
				TASK_VERIFY_PRODUCE, orderId);
	}

	@Override
	public boolean verifyProduceSubmit(String taskId, boolean result,
			String comment) {
		Map<String, Object> data = new HashMap<>();
		data.put(RESULT_PRODUCE, result);
		data.put(RESULT_PRODUCE_COMMENT, comment);
		try {
			mainProcessService.completeTask(taskId, ACTOR_PRODUCE_MANAGER, data);
			return true;
		} catch (InterruptedException e) {
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
	public List<Map<String, Object>> getSearchComputeProduceCostList(
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds) {
		return service.getSearchOrderList(ACTOR_PRODUCE_MANAGER, ordernumber,  customername,  stylename,
				 startdate,  enddate, employeeIds,
				TASK_COMPUTE_PRODUCE_COST);
	}
	
	@Override
	public Map<String, Object> getComputeProduceCostInfo(Integer orderId) {
		return service.getBasicOrderModelWithQuote(ACTOR_PRODUCE_MANAGER,
				TASK_COMPUTE_PRODUCE_COST, orderId);
	}

	@Override
	public boolean computeProduceCostSubmit(int orderId, String taskId, boolean result, String comment,
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
				+ quote.getFabricCost() + quote.getAccessoryCost() + quote.getCraftCost();
		quote.setSingleCost(producecost);
		QuoteDAO.attachDirty(quote);

		Map<String, Object> data = new HashMap<String, Object>();
		data.put(RESULT_PRODUCE, result);
		data.put(RESULT_PRODUCE_COMMENT, comment);
		try {
			mainProcessService.completeTask(taskId, ACTOR_PRODUCE_MANAGER, data);
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// =========================样衣生产========================
	@Override
	public List<Map<String, Object>> getProduceSampleList() {
		return service.getOrderList(ACTOR_PRODUCE_MANAGER, TASK_PRODUCE_SAMPLE);
	}

	@Override
	public List<Map<String, Object>> getSearchProduceSampleList(
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds) {
		return service.getSearchOrderList(ACTOR_PRODUCE_MANAGER, ordernumber,  customername,  stylename,
				 startdate,  enddate,  employeeIds,
				TASK_PRODUCE_SAMPLE);

	}

	
	@Override
	public Map<String, Object> getProduceSampleDetail(Integer orderId) {
		return service.getBasicOrderModelWithQuote(ACTOR_PRODUCE_MANAGER,
				TASK_PRODUCE_SAMPLE, orderId);
	}

	@Override
	public boolean produceSampleSubmit(String taskId, boolean result) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put(RESULT_PRODUCE, result);
		try {
			mainProcessService.completeTask(taskId, ACTOR_PRODUCE_MANAGER, data);
			return true;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	@Override
	public boolean produceSampleSubmit(String taskId, boolean result,String orderId) {
		Order order = orderDAO.findById(Integer.parseInt(orderId));
		Map<String, Object> data = new HashMap<String, Object>();
		data.put(RESULT_PRODUCE, result);
		try {
			mainProcessService.completeTask(taskId, ACTOR_PRODUCE_MANAGER, data);
			if(result==false){//如果result的的值为false，即为样衣生产失败，流程会异常终止，将orderState设置为1
				order.setOrderState("1");
				order.setOrderProcessStateName("被终止");
				orderDAO.attachDirty(order);
			}
			return true;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}
	}

	// =======================批量生产========================
	@Override
	public List<Map<String, Object>> getProduceList() {
		return service.getOrderList(ACTOR_PRODUCE_MANAGER, TASK_PRODUCE);
	}

	@Override
	public List<Map<String, Object>> getSearchProduceList(String ordernumber,
			String customername, String stylename, String startdate,
			String enddate, Integer[] employeeIds) {
		return service.getSearchOrderList(ACTOR_PRODUCE_MANAGER, ordernumber, customername,  stylename,  startdate,
				 enddate,employeeIds,TASK_PRODUCE);

	}
	
	@Override
	public Map<String, Object> getProduceDetail(Integer orderId) {
		// TODO Auto-generated method stub
		return service.getBasicOrderModelWithQuote(ACTOR_PRODUCE_MANAGER, TASK_PRODUCE,
				orderId);
	}

	@Override
	public boolean pruduceSubmit(String taskId, boolean result,
			List<Produce> produceList) {
		if (result) {
			 for (int i = 0; i < produceList.size(); i++) {
			 produceDAO.save(produceList.get(i));
			 }
		}
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			data.put(RESULT_PRODUCE, result);
			mainProcessService.completeTask(taskId, ACTOR_PRODUCE_MANAGER, data);
			return true;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public boolean pruduceSubmit(String taskId, boolean result,
			List<Produce> produceList,Integer orderId) {
		Order order = orderDAO.findById(orderId);
		if (result) {
			for (int i = 0; i < produceList.size(); i++) {
				produceDAO.save(produceList.get(i));
			}
		}
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			data.put(RESULT_PRODUCE, result);
			mainProcessService.completeTask(taskId, ACTOR_PRODUCE_MANAGER, data);
			if(result==false){//如果result的的值为false，即为大货生产失败，流程会异常终止，将orderState设置为1
				order.setOrderState("1");
				order.setOrderProcessStateName("被终止");
				orderDAO.attachDirty(order);
			}
			return true;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	
	}


	public List<Produce> getProduceList(int orderId, String produceColor,
			String produceXS, String produceS, String produceM,
			String produceL, String produceXL, String produceXXL, String produceJ, String type) {
		String[] color = produceColor.split(",");
		String[] xs = produceXS.split(",");
		String[] s = produceS.split(",");
		String[] m = produceM.split(",");
		String[] l = produceL.split(",");
		String[] xl = produceXL.split(",");
		String[] xxl = produceXXL.split(",");
		String[] j = produceJ.split(",");
		List<Produce> produceList = new ArrayList<Produce>();
		for (int i = 0; i < color.length; i++) {
			//System.out.println(color[i] + xs[i] + xxl[i]);
			Produce produce = new Produce();
			produce.setOid(orderId);
			produce.setColor(color[i]);
			produce.setXs(Integer.parseInt(xs[i]));
			produce.setS(Integer.parseInt(s[i]));
			produce.setM(Integer.parseInt(m[i]));
			produce.setL(Integer.parseInt(l[i]));
			produce.setXl(Integer.parseInt(xl[i]));
			produce.setXxl(Integer.parseInt(xxl[i]));
			produce.setJ(Integer.parseInt(j[i]));
			produce.setType(type);
			int total = produce.getXs() + produce.getS() + produce.getM() + produce.getL() +
					produce.getXl() + produce.getXxl() + produce.getJ();
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
	private FMCProcessService mainProcessService;
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
