
package nju.software.service.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nju.software.dao.impl.AccessoryDAO;
import nju.software.dao.impl.FabricDAO;
import nju.software.dao.impl.LogisticsDAO;
import nju.software.dao.impl.OrderDAO;
import nju.software.dataobject.Accessory;
import nju.software.dataobject.Fabric;
import nju.software.dataobject.Logistics;
import nju.software.dataobject.Order;
import nju.software.service.OrderService;
import nju.software.util.JbpmAPIUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 莫其凡
 *
 */
@Service("orderServiceImpl")
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderDAO orderDAO;
	@Autowired
	private JbpmAPIUtil jbpmAPIUtil;
	@Autowired
	private AccessoryDAO accessoryDAO;
	@Autowired
	private FabricDAO fabricDAO;
	@Autowired
	private LogisticsDAO logisticsDAO;
	
	//新增订单
	public void addOrder(Order order) {
		orderDAO.save(order);
	}

	public List<Order> findAll()
	{
		List<Order> order=this.orderDAO.findAll();
		return order;
	}
	


	
	public Order getOrderById(int orderId) {
		try {
			Order order = orderDAO.findById(orderId);
			return order;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean updateOrder(Order order) {
		// TODO Auto-generated method stub
		try {
			orderDAO.attachDirty(order);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public List<Object> getOrderWithSample(Map<String,Object> map) {
		try
		{
			
			//order that need sample and the hasPostedSampleCloth is false,isNeedSampleCloths is true
			
			int count=orderDAO.coutSampleOrder();
			int page=(int) map.get("page");
			int number=(int) map.get("number_per_page");
			int total_pages = 0;
			int start = 0;

			System.out.println("count"+count);
			System.out.println("page"+page);
			System.out.println("number"+number);
			if (count > 0)
				total_pages = (int) Math.ceil((double) count / number);
			if (page > total_pages)
				page = total_pages;
			start=number*(page-1);
			map.put("page_number",total_pages);
			List<Order> orderList=orderDAO.findSampleOrderAndPage(start, number);
			map.put("page_number",total_pages);
			List<Object> list=new ArrayList<Object>();
			list.add(orderList);
			list.add(map);
			return list;
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}
	@Override
	public Order findByOrderId(String orderId) {
		// TODO Auto-generated method stub
		
		return orderDAO.findById(Integer.parseInt(orderId));
	}
	@Override
	public boolean merge(Order o) {
		// TODO Auto-generated method stub
		try{
		Order order=orderDAO.merge(o);
		return true;
		}catch(Exception e)
		{
			
		}
		return false;
	}
	

	/**
	 * 提交询单，开始流程
	 */
	@Override
	public String addOrder(Order order,List<Fabric>fabrics,List<Accessory>accessorys,Logistics logistics){
		// TODO Auto-generated method stub
		
		//添加订单信息
		orderDAO.save(order);
		
		//添加面料信息
		for(Fabric fabric:fabrics){
			fabric.setOrderId(order.getOrderId());
			fabricDAO.save(fabric);
		}
		
		//添加辅料信息
		for(Accessory accessory:accessorys){
			accessory.setOrderId(order.getOrderId());
			accessoryDAO.save(accessory);
		}
		
		//添加物流信息
		logistics.setOrderId(order.getOrderId());
		logisticsDAO.save(logistics);
		
		//启动流程
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderId", order.getOrderId());
		params.put("employeeId", order.getEmployeeId());
		params.put("needclothes", order.getIsNeedSampleClothes());
		params.put("sendclothes", order.getHasPostedSampleClothes());
		doTMWorkFlowStart(params);
		
		return "下单成功";
	}
	
	
	/**
	 * 启动流程
	 */
	private void doTMWorkFlowStart(Map<String, Object> params) {
		try {
			jbpmAPIUtil.setParams(params);
			jbpmAPIUtil.startWorkflowProcess();
			System.out.println("流程启动成功！");
		} catch (Exception ex) {
			System.out.println("流程启动失败");
			ex.printStackTrace();
		}
	}
}
