package nju.software.service;

import java.util.List;
import java.util.Map;

import nju.software.model.OrderInfo;
import nju.software.model.OrderModel;

public interface QualityService {
	
	public List<Map<String,Object>> getCheckQualityList();

	public boolean checkQualitySubmit(int id, long taskId, boolean b);

	public OrderInfo getCheckQualityDetail(int orderId, long taskId);
}
