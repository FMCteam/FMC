package nju.software.service;

import java.util.List;
import java.util.Map;

import nju.software.dataobject.Accessory;
import nju.software.dataobject.Employee;
import nju.software.dataobject.Fabric;
import nju.software.dataobject.Logistics;
import nju.software.dataobject.Order;

public interface OrderService {
	

	
	
	public List<Object> getOrderWithSample(Map<String,Object> map);
	
	public Order findByOrderId(String orderId);
	
	public boolean merge(Order o);

	public void addOrder(Order order) ;
	
	public Order getOrderById(int orderId);
	
	public boolean updateOrder(Order order);
	public List<Order> findAll();
	public String addOrder(Order order,List<Fabric>fabrics,List<Accessory>accessorys,Logistics logistics);
}
