
package nju.software.service.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nju.software.dao.impl.OrderDAO;
import nju.software.dataobject.Employee;
import nju.software.dataobject.Order;
import nju.software.service.OrderService;
import nju.software.util.JbpmAPIUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("orderServiceImpl")
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderDAO orderDAO;
	@Autowired
	private JbpmAPIUtil jbpmAPIUtil;
	//新增订单
	public void addOrder(Order order) {
		orderDAO.save(order);
	}

	public List<Order> findAll()
	{
		List<Order> order=this.orderDAO.findAll();
		return order;
	}
	private void doTMWorkFlowStart(Map<String, Object> params) {
		try {
			jbpmAPIUtil.setParams(params);
			jbpmAPIUtil.startWorkflowProcess();
		} catch (Exception ex) {
			System.out.println("流程启动失败");
			ex.printStackTrace();
		}
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

}
