package nju.software.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import nju.software.dataobject.Produce;

public interface ProduceService {

	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getVerifyProduceList();

	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> getVerifyProduceDetail(int orderId);

	@Transactional(rollbackFor = Exception.class)
	public boolean verifyProduceSubmit(String taskId, boolean productVal,
			String comment);

	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getComputeProduceCostList();

	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> getComputeProduceCostInfo(Integer orderId);

	@Transactional(rollbackFor = Exception.class)
	public boolean computeProduceCostSubmit(int orderId, String taskId,
			boolean result, String comment, float cut_cost, float manage_cost,
			float nali_cost, float ironing_cost, float swing_cost,
			float package_cost, float other_cost, float design_cost);

	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getProduceSampleList();

	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> getProduceSampleDetail(Integer orderId);

	@Transactional(rollbackFor = Exception.class)
	public boolean produceSampleSubmit(String taskId, boolean result);

	@Transactional(rollbackFor = Exception.class)
	public boolean produceSampleSubmit(String taskId, boolean result,
			String orderId);

	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getProduceList();

	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> getProduceDetail(Integer orderId);

	@Transactional(rollbackFor = Exception.class)
	public boolean pruduceSubmit(String taskId, boolean result,
			List<Produce> produceList);

	@Transactional(rollbackFor = Exception.class)
	public boolean pruduceSubmit(String taskId, boolean result,
			List<Produce> produceList, Integer orderId);

	@Transactional(rollbackFor = Exception.class)
	public List<Produce> getProduceList(int orderId, String produceColor,
			String produceXS, String produceS, String produceM,
			String produceL, String produceXL, String produceXXL, String produceJ, String type);

	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getSearchVerifyProduceList(
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds);

	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getSearchComputeProduceCostList(
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds);

	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getSearchProduceSampleList(
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds);

	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getSearchProduceList(String ordernumber,
			String customername, String stylename, String startdate,
			String enddate, Integer[] employeeIds);

}
