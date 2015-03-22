package nju.software.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import nju.software.dataobject.CheckRecord;
import nju.software.dataobject.Produce;

public interface QualityService {

	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getCheckQualityList();

	@Transactional(rollbackFor = Exception.class)
	public String checkQualitySubmit(int orderId, String taskId, String isFinal,
			CheckRecord checkRecord, List<Produce> goodList)throws Exception;

	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> getCheckQualityDetail(int orderId);

	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getSearchCheckQualityList(
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds);

	boolean test(int orderId, String clothesType) throws Exception;

}
