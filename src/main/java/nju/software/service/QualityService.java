package nju.software.service;

import java.util.List;
import java.util.Map;

import nju.software.dataobject.CheckRecord;
import nju.software.dataobject.Produce;

public interface QualityService {
	
	public List<Map<String,Object>> getCheckQualityList();

	public String checkQualitySubmit(int orderId, long taskId, String isFinal, CheckRecord checkRecord, List<Produce> goodList);

	public Map<String,Object> getCheckQualityDetail(int orderId);

	public List<Map<String, Object>> getSearchCheckQualityList(
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds);

}
