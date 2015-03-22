package nju.software.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import nju.software.dataobject.Accessory;
import nju.software.dataobject.AccessoryCost;
import nju.software.dataobject.Craft;
import nju.software.dataobject.DesignCad;
import nju.software.dataobject.Fabric;
import nju.software.dataobject.FabricCost;
import nju.software.dataobject.Logistics;
import nju.software.dataobject.Order;
import nju.software.dataobject.Produce;
import nju.software.dataobject.Quote;
import nju.software.dataobject.VersionData;
import nju.software.model.OrderModel;
import nju.software.model.QuoteModel;

public interface OrderService {

	@Transactional(rollbackFor = Exception.class)
	public List<Object> getOrderWithSample(Map<String, Object> map);

	@Transactional(rollbackFor = Exception.class)
	public Order findByOrderId(String orderId);

	@Transactional(rollbackFor = Exception.class)
	public boolean merge(Order o);

	@Transactional(rollbackFor = Exception.class)
	public void addOrder(Order order);

	@Transactional(rollbackFor = Exception.class)
	public Order getOrderById(int orderId);

	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getOrdersEnd(String userRole,
			Integer userId);

	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> findByProperty(String propertyName,
			Object value);

	@Transactional(rollbackFor = Exception.class)
	public boolean updateOrder(Order order);

	@Transactional(rollbackFor = Exception.class)
	public List<Order> findAll();

	@Transactional(rollbackFor = Exception.class)
	public String addOrder(Order order, List<Fabric> fabrics,
			List<Accessory> accessorys, Logistics logistics) throws Exception;

	@Transactional(rollbackFor = Exception.class)
	public List<OrderModel> getOrderByActorIdAndTaskname(String actorId,
			String taskName);

	@Transactional(rollbackFor = Exception.class)
	public OrderModel getOrderDetail(int orderId, String taskId, String processId);

	// 市场专员完成修改询单的功能,就只是推进流程的进行
	@Transactional(rollbackFor = Exception.class)
	public boolean verify(int orderId, String taskId, String processId,
			boolean editOk, String buyComment, String designComment,
			String productComment);

	@Transactional(rollbackFor = Exception.class)
	public void modifyOrder(Order order, List<Fabric> fabrics,
			List<Accessory> accessorys, Logistics logistics);

	@Transactional(rollbackFor = Exception.class)
	public List<QuoteModel> getQuoteByActorAndTask(String actor, String taskName);

	@Transactional(rollbackFor = Exception.class)
	public QuoteModel getQuoteByOrderAndPro(String actor, String taskName,
			int orderId);

	@Transactional(rollbackFor = Exception.class)
	public void endOrder(Integer orderId);

	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getModifyOrderList();

	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> getModifyOrderDetail(Integer userId, int id);

	@Transactional(rollbackFor = Exception.class)
	public void modifyOrderSubmit(Order order, List<Fabric> fabrics,
			List<Accessory> accessorys, Logistics logistics,
			List<Produce> produces, List<Produce> sample_produces,
			List<VersionData> versions, DesignCad cad, int user_id,
			List<FabricCost> fabricCosts, List<AccessoryCost> accessoryCosts,
			Quote quote,Craft craft);

	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getSearchOrderList(String ordernumber,
			String customername, String stylename, String startdate,
			String enddate, Integer[] employeeIds, String userRole,
			Integer userId);

}
