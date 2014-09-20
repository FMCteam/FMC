package nju.software.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nju.software.dao.impl.OrderDAO;
import nju.software.dataobject.Order;
import nju.software.service.SweaterMakeService; 
import nju.software.util.JbpmAPIUtil;

@Service("sweaterMakeServiceImpl")
public class SweaterMakeServiceImpl implements SweaterMakeService {

	@Override
	public List<Map<String, Object>> getSweaterSampleAndCraftList() {
		return service.getOrderList(ACTOR_SWEATER_MANAGER, TASK_CONFIRM_SWEATER_SAMPLE_AND_CRAFT);
	}

	@Override
	public Map<String, Object> getSweaterSampleAndCraftDetail(int orderId) {
		return service.getBasicOrderModelWithQuote(ACTOR_SWEATER_MANAGER,
				TASK_CONFIRM_SWEATER_SAMPLE_AND_CRAFT, orderId);
	}

	@Override
	public List<Map<String, Object>> getSendSweaterList() {
		return service.getOrderList(ACTOR_SWEATER_MANAGER, TASK_SEND_SWEATER);

	}

	@Override
	public Map<String, Object> getSendSweaterDetail(int orderId) {
		return service.getBasicOrderModelWithQuote(ACTOR_SWEATER_MANAGER,
				TASK_SEND_SWEATER, orderId);
	}

	@Override
	public boolean sweaterSampleAndCraftSubmit(long taskId, boolean result,
			String orderId) {
 		Order order = orderDAO.findById(Integer.parseInt(orderId));
		Map<String, Object> data = new HashMap<String, Object>();
 		try {
			jbpmAPIUtil.completeTask(taskId, data, ACTOR_SWEATER_MANAGER);
			return true;
		} catch (InterruptedException e) {
 			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean sendSweaterSubmit(long taskId, boolean result, String orderId) {
 		Order order = orderDAO.findById(Integer.parseInt(orderId));
		Map<String, Object> data = new HashMap<String, Object>();
 		try {
			jbpmAPIUtil.completeTask(taskId, data, ACTOR_SWEATER_MANAGER);
			return true;
		} catch (InterruptedException e) {
 			e.printStackTrace();
			return false;
		}
 
	}

	@Override
	public List<Map<String, Object>> getSearchSweaterSampleAndCraftList(
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds) {
		return service.getSearchOrderList(ACTOR_SWEATER_MANAGER, ordernumber,  customername,
				stylename,startdate,  enddate,  employeeIds,
				 TASK_CONFIRM_SWEATER_SAMPLE_AND_CRAFT);
	}

	@Override
	public List<Map<String, Object>> getSearchSendSweaterList(
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds) {
		return service.getSearchOrderList(ACTOR_SWEATER_MANAGER, ordernumber,  customername, 
				stylename, startdate,  enddate,  employeeIds,
				 TASK_SEND_SWEATER);
	}
	public final static String ACTOR_SWEATER_MANAGER = "SweaterMakeManager";
	public final static String TASK_CONFIRM_SWEATER_SAMPLE_AND_CRAFT = "confirmSweaterSampleAndCraft";
	public final static String TASK_SEND_SWEATER= "sendSweater";
	public final static String RESULT_PRODUCE = "produce";
	public final static String RESULT_PRODUCE_COMMENT = "produceComment";
	@Autowired
	private OrderDAO orderDAO;
	@Autowired
	private ServiceUtil service;
	@Autowired
	private JbpmAPIUtil jbpmAPIUtil;

	@Override
	public Map<String, Object> getProduceDetail(Integer orderId) {
		// TODO Auto-generated method stub
		return service.getBasicOrderModelWithQuote(ACTOR_SWEATER_MANAGER, RESULT_PRODUCE,
				orderId);
	}

}
