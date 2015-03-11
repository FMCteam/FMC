package nju.software.service;

import java.util.List;
import java.util.Map;

import nju.software.dataobject.OperateRecord;
import nju.software.dataobject.Produce;

public interface SweaterMakeService {
	public List<Map<String, Object>> getSweaterSampleAndCraftList();
	
	public Map<String, Object> getSweaterSampleAndCraftDetail(int orderId);
	
	public List<Map<String, Object>> getSendSweaterList();

	public Map<String, Object> getSendSweaterDetail(int orderId);


	public boolean sweaterSampleAndCraftSubmit(String taskId, boolean result,
			String orderId,OperateRecord oprecord);
	

	public boolean sendSweaterSubmit(String taskId, boolean result,List<Produce> produceList,
			Integer orderId);
	public List<Map<String, Object>> getSearchSweaterSampleAndCraftList(
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds);

	public List<Map<String, Object>> getSearchSendSweaterList(
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds);

	public List<Map<String, Object>> getOrders();

	public List<Map<String, Object>> getSerachOrders(String orderState);

}
