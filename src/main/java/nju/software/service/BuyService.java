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

	public Map<String,Object> getVerifyPurchaseDetail(Integer orderId);
	
	public boolean verifyPurchaseSubmit(long taskId, boolean result,
			String comment);
	
	//===========================采购成本核算=================================
	public List<Map<String,Object>> getComputePurchaseCostList();

	public Map<String,Object> getComputePurchaseCostDetail(Integer orderId);
	
	public void computePurchaseCostSubmit(int orderId, long taskId,
			boolean result, String comment, String[] fabric_names,
			String[] fabric_amouts, String[] tear_per_meters,
			String[] cost_per_meters, String[] accessory_names,
			String[] accessory_querys, String[] tear_per_piece,
			String[] cost_per_piece);
	
	//===========================采购样衣原料=================================
	public List<Map<String,Object>> getPurchaseSampleMaterialList();

	public Map<String,Object> getPurchaseSampleMaterialDetail(Integer orderId);

	public boolean purchaseSampleMaterialSubmit(long taskId, boolean result, boolean needcraft,String orderId);
	public boolean purchaseSampleMaterialSubmit(long taskId, boolean result );

	//===========================采购确认=================================
	public List<Map<String,Object>> getConfirmPurchaseList();

	public Map<String, Object> getConfirmPurchaseDetail(Integer orderId);

	public boolean confirmPurchaseSubmit(long taskId, boolean result);
	
	//===========================大货原料采购=================================
	public List<Map<String,Object>> getPurchaseMaterialList();

	public Map<String, Object> getPurchaseMaterialDetail(Integer orderId);

	public boolean purchaseMaterialSubmit(long taskId, boolean result);

	public List<Map<String, Object>> getSearchVerifyPurchaseList(
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds);

	public List<Map<String, Object>> getSearchComputePurchaseCostList(
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds);

	public List<Map<String, Object>> getSearchPurchaseSampleMaterialList(
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds);

	public List<Map<String, Object>> getSearchConfirmPurchaseList(
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds);

	public List<Map<String, Object>> getSearchPurchaseMaterialList(
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds);

}
