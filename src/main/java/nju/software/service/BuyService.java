package nju.software.service;

import java.util.List;
import java.util.Map;

import nju.software.dataobject.Accessory;
import nju.software.dataobject.Fabric;
import nju.software.dataobject.Logistics;
import nju.software.model.OrderInfo;
import nju.software.model.ProductModel;

public interface BuyService {
	
	//===========================采购验证=================================
	public List<Map<String,Object>> getVerifyPurchaseList();

	public OrderInfo getVerifyPurchaseDetail(Integer orderId);
	
	public boolean verifyPurchaseSubmit(long taskId, boolean buyVal,
			String comment);
	
	//===========================采购成本核算=================================
	public List<Map<String,Object>> getComputePurchaseCostList();

	public OrderInfo getComputePurchaseCostDetail(Integer orderId);
	
	public void computePurchaseCostSubmit(int orderId, long taskId,
			String[] fabric_names, String[] tear_per_meters,
			String[] cost_per_meters, String[] fabric_prices,
			String[] accessory_names, String[] tear_per_piece,
			String[] cost_per_piece, String[] accessory_prices);
	
	//===========================采购样衣原料=================================
	public List<Map<String,Object>> getPurchaseSampleMaterialList();

	public OrderInfo getPurchaseSampleMaterialDetail(Integer orderId);

	public boolean purchaseSampleMaterialSubmit(long taskId, String result);
	
	//===========================采购确认=================================
	public List<Map<String,Object>> getConfirmPurchaseList();

	public OrderInfo getConfirmPurchaseDetail(Integer orderId);

	public boolean confirmPurchaseSubmit(long taskId, String result);
	
	//===========================大货原料采购=================================
	public List<Map<String,Object>> getPurchaseMaterialList();

	public OrderInfo getPurchaseMaterialDetail(Integer orderId);

	public boolean purchaseMaterialSubmit(long taskId, String result);
}
