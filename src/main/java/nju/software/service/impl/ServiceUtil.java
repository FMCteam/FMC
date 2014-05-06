package nju.software.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nju.software.dao.impl.AccessoryCostDAO;
import nju.software.dao.impl.AccessoryDAO;
import nju.software.dao.impl.CustomerDAO;
import nju.software.dao.impl.DesignCadDAO;
import nju.software.dao.impl.EmployeeDAO;
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
import nju.software.dataobject.AccessoryCost;
import nju.software.dataobject.DesignCad;
import nju.software.dataobject.FabricCost;
import nju.software.dataobject.Order;
import nju.software.dataobject.Package;
import nju.software.dataobject.PackageDetail;
import nju.software.dataobject.Produce;
import nju.software.dataobject.Quote;
import nju.software.util.JbpmAPIUtil;

import org.jbpm.task.query.TaskSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("service")
public class ServiceUtil {

	public List<Map<String, Object>> getOrderList(String actorId,
			String taskName) {
		List<TaskSummary> tasks = jbpmAPIUtil.getAssignedTasksByTaskname(
				actorId, taskName);
		List<Map<String, Object>> list = new ArrayList<>();

		for (TaskSummary task : tasks) {
			Integer orderId = (Integer) jbpmAPIUtil
					.getVariable(task, "orderId");
			Map<String, Object> model = new HashMap<String, Object>();
			Order order = orderDAO.findById(orderId);
			model.put("order", order);
			model.put("employee", employeeDAO.findById(order.getEmployeeId()));
			model.put("task", task);
			model.put("taskTime", getTaskTime(task.getCreatedOn()));
			model.put("orderId", getOrderId(order));
			list.add(model);
		}
		return list;
	}

	public Map<String, Object> getBasicOrderModel(String actorId,
			String taskName, Integer orderId) {
		Map<String, Object> model = new HashMap<String, Object>();
		TaskSummary task = jbpmAPIUtil.getTask(actorId, taskName, orderId);
		Order order = orderDAO.findById(orderId);
		model.put("task", task);
		model.put("taskId", task.getId());
		model.put("order", order);
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
		return model;
	}

	public Map<String, Object> getBasicOrderModelWithQuote(String actorId,
			String taskName, Integer orderId) {
		Map<String, Object> model = getBasicOrderModel(actorId, taskName,
				orderId);
		Quote quote = quoteDAO.findById(orderId);
		model.put("quote", quote);
		List<FabricCost> fabricCosts = fabricCostDAO.findByOrderId(orderId);
		model.put("fabricCosts", fabricCosts);
		List<AccessoryCost> accessoryCosts = accessoryCostDAO
				.findByOrderId(orderId);
		model.put("accessoryCosts", accessoryCosts);
		return model;
	}

	public Map<String, Object> getBasicOrderModelWithWareHouse(String actorId,
			String taskName, Integer orderId) {
		Map<String, Object> model = getBasicOrderModelWithQuote(actorId,
				taskName, orderId);
		List<Package> packageList = packageDAO.findByOrderId(orderId);
		model.put("packs", packageList);
		model.put("packDetails", findByPackageList(packageList));
		return model;
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

	private List<List<PackageDetail>> findByPackageList(
			List<Package> packageList) {
		// TODO Auto-generated method stub
		List<List<PackageDetail>> dList = new ArrayList<List<PackageDetail>>();
		for (int i = 0; i < packageList.size(); i++) {
			Package pk = packageList.get(i);
			List<PackageDetail> pList = packageDetailDAO.findByPackageId(pk
					.getPackageId());
			dList.add(pList);
		}
		return dList;
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
	private VersionDataDAO versionDataDAO;
	@Autowired
	private DesignCadDAO cadDAO;
	@Autowired
	private QuoteDAO quoteDAO;
	@Autowired
	private FabricCostDAO fabricCostDAO;
	@Autowired
	private AccessoryCostDAO accessoryCostDAO;

}
