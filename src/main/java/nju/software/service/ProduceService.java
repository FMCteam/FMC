package nju.software.service;

import java.util.List;
import java.util.Map;

import nju.software.dataobject.Produce;

public interface ProduceService {

	public List<Map<String, Object>> getVerifyProduceList();

	public Map<String, Object> getVerifyProduceDetail(int orderId);

	public boolean verifyProduceSubmit(String taskId, boolean productVal,
			String comment);

	public List<Map<String, Object>> getComputeProduceCostList();

	public Map<String, Object> getComputeProduceCostInfo(Integer orderId);

	public void computeProduceCostSubmit(int orderId, String taskId,
			boolean result, String comment, float cut_cost, float manage_cost,
			float nali_cost, float ironing_cost, float swing_cost,
			float package_cost, float other_cost, float design_cost);

	public List<Map<String, Object>> getProduceSampleList();

	public Map<String, Object> getProduceSampleDetail(Integer orderId);

	public boolean produceSampleSubmit(String taskId, boolean result);

	public boolean produceSampleSubmit(String taskId, boolean result,
			String orderId);

	public List<Map<String, Object>> getProduceList();

	public Map<String, Object> getProduceDetail(Integer orderId);

	public boolean pruduceSubmit(String taskId, boolean result,
			List<Produce> produceList);

	public boolean pruduceSubmit(String taskId, boolean result,
			List<Produce> produceList, Integer orderId);

	public List<Produce> getProduceList(int orderId, String produceColor,
			String produceXS, String produceS, String produceM,
			String produceL, String produceXL, String produceXXL, String produceJ, String type);

	public List<Map<String, Object>> getSearchVerifyProduceList(
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds);

	public List<Map<String, Object>> getSearchComputeProduceCostList(
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds);

	public List<Map<String, Object>> getSearchProduceSampleList(
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds);

	public List<Map<String, Object>> getSearchProduceList(String ordernumber,
			String customername, String stylename, String startdate,
			String enddate, Integer[] employeeIds);

}
