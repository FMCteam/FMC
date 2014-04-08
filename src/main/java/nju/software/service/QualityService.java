package nju.software.service;

import java.util.List;

import nju.software.model.OrderInfo;
import nju.software.model.OrderModel;

public interface QualityService {
	
	public List<OrderInfo> getCheckQualityList();

	public boolean checkQualitySubmit(int id, long taskId, long processId, boolean b);

	public OrderInfo getCheckQualityDetail(int orderId, long taskId);
}
