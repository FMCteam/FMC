package nju.software.service;

import java.util.List;
import java.util.Map;

public interface SweaterMakeService {
	public List<Map<String, Object>> getSweaterSampleAndCraftList();
	
	public Map<String, Object> getSweaterSampleAndCraftDetail(int orderId);
	
	public List<Map<String, Object>> getSendSweaterList();

	public Map<String, Object> getSendSweaterDetail(int orderId);


	public boolean sweaterSampleAndCraftSubmit(long taskId, boolean result,
			String orderId);
	

	public boolean sendSweaterSubmit(long taskId, boolean result,
			String orderId);
	public List<Map<String, Object>> getSearchSweaterSampleAndCraftList(
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds);

	public List<Map<String, Object>> getSearchSendSweaterList(
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds);
}
