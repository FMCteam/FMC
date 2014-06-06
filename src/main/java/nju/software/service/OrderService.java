package nju.software.service;

import java.util.List;
import java.util.Map;

import nju.software.dataobject.Accessory;
import nju.software.dataobject.Account;
import nju.software.dataobject.DesignCad;
import nju.software.dataobject.Employee;
import nju.software.dataobject.Fabric;
import nju.software.dataobject.Logistics;
import nju.software.dataobject.Order;
import nju.software.dataobject.Produce;
import nju.software.dataobject.Quote;
import nju.software.dataobject.VersionData;
import nju.software.model.OrderModel;
import nju.software.model.QuoteModel;

public interface OrderService {
	

	
	
	public List<Object> getOrderWithSample(Map<String,Object> map);
	
	public Order findByOrderId(String orderId);
	
	public boolean merge(Order o);

	public void addOrder(Order order) ;
	
	public Order getOrderById(int orderId);
	
	public List<Map<String,Object>>findByProperty(String propertyName, Object value);
	
	public boolean updateOrder(Order order);
	public List<Order> findAll();
	public String addOrder(Order order,List<Fabric>fabrics,List<Accessory>accessorys,Logistics logistics);
	
	public List<OrderModel> getOrderByActorIdAndTaskname(String actorId, String taskName);
	
	public OrderModel getOrderDetail(int orderId, long taskId, long processId);
	//市场专员完成修改询单的功能,就只是推进流程的进行
	public boolean verify(int orderId, long taskId, 
			long processId,boolean editOk,String buyComment,String designComment,String productComment);

	public void modifyOrder(Order order, List<Fabric> fabrics,
			List<Accessory> accessorys, Logistics logistics);
	public List<QuoteModel> getQuoteByActorAndTask(String actor,String taskName);
	
	public QuoteModel getQuoteByOrderAndPro(String actor,String taskName,int orderId);

	
	public void endOrder(Integer orderId);


	public List<Map<String, Object>> getModifyOrderList();

	public Map<String, Object> getModifyOrderDetail(Integer userId, int id);

	public void modifyOrderSubmit(Order order, List<Fabric> fabrics,
			List<Accessory> accessorys, Logistics logistics,
			List<Produce> produces, List<Produce> sample_produces,
			List<VersionData> versions, DesignCad cad, int user_id);

}
