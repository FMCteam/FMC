package nju.software.service;

import java.util.List;
import java.util.Map;

import nju.software.dataobject.Produce;


public interface ProduceService {

	public List<Map<String, Object>> getVerifyProduceList();

	public Map<String, Object> getVerifyProduceDetail(int orderId);

	public boolean verifyProduceSubmit(long taskId, boolean productVal, String comment);

	public List<Map<String, Object>> getComputeProduceCostList();

	public Map<String, Object> getComputeProduceCostInfo(Integer orderId);

	public void computeProduceCostSubmit(int orderId, long taskId,
			float cut_cost, float manage_cost, float nali_cost,
			float ironing_cost, float swing_cost, float package_cost,
			float other_cost, float design_cost);

	public List<Map<String, Object>> getProduceSampleList();

	public Map<String, Object> getProduceSampleDetail(Integer orderId);

	public boolean produceSampleSubmit(long taskId, boolean result);

	public List<Map<String, Object>> getProduceList();

	public Map<String, Object> getProduceDetail(Integer orderId);

	public boolean pruduceSubmit(long taskId, boolean result,
			List<Produce> produceList);
	
	public List<Produce> getProduceList(int orderId, String produceColor,
			String produceXS, String produceS, String produceM,
			String produceL, String produceXL, String produceXXL, String type);

}
