package nju.software.service;

import java.util.List;
import nju.software.model.OrderInfo;

public interface LogisticsService {

	// ===========================收取样衣=================================
	public List<OrderInfo> getReceiveSampleList();

	public OrderInfo getReceiveSampleDetail(Integer orderId);

	public boolean receiveSampleSubmit(long taskId, String result);

	// ===========================样衣发货=================================
	public List<OrderInfo> getSendSampleList();

	public OrderInfo getSendSampleDetail(Integer orderId);
	
	public boolean sendSampleSubmit(long taskId, long processId);
	
	// ===========================产品入库=================================
	public List<OrderInfo> getWarehouseList();

	public OrderInfo getWarehouseDetail(Integer orderId);

	public boolean warehouseSubmit(long taskId, String result);
	
	// ===========================产品发货=================================
	public List<OrderInfo> getSendClothesList();

	public OrderInfo getSendClothesDetail(Integer orderId);

	public void sendClothesSubmit(int orderId, long taskId, float logistics_cost);
	
	// ======================得到未扫描确认的列表=============================
	public List<OrderInfo> getSendClothesUncheckedList();
}
