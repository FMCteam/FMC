package nju.software.service;

import java.util.List;

import nju.software.dataobject.Logistics;
import nju.software.model.OrderInfo;

public interface LogisticsService {
	
	public List<OrderInfo>getReceiveSampleList();
	
	public OrderInfo getReceiveSampleDetail(Integer orderId);
	
	public boolean receiveSampleSubmit(long taskId,String result);
	
	
	
	public Logistics findByOrderId(String orderId);

	public boolean addLogistics(Logistics log);

	public boolean sendSampleSubmit(long taskId, long processId);

	public List<OrderInfo> getSendSampleList(int s_page, int s_number_per_page);

	public OrderInfo getSendSampleDetail(int orderId, long tid);
}
