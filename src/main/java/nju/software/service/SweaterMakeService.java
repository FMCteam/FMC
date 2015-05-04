package nju.software.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import nju.software.dataobject.OperateRecord;
import nju.software.dataobject.Produce;

public interface SweaterMakeService {
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getSweaterSampleAndCraftList();
	
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> getSweaterSampleAndCraftDetail(int orderId);
	
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getSendSweaterList();

	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> getSendSweaterDetail(int orderId);

	@Transactional(rollbackFor = Exception.class)
	public boolean sweaterSampleAndCraftSubmit(String taskId, boolean result,
			String orderId,OperateRecord oprecord);
	
	@Transactional(rollbackFor = Exception.class)
	public boolean sendSweaterSubmit(String taskId, boolean result, String processing_side, String sendTime, String Purchase_director,
			Integer orderId);
	
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getSearchSweaterSampleAndCraftList(
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds);

	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getSearchSendSweaterList(
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds);

	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getOrders();

	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getSerachOrders(String orderState);

}
