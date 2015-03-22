package nju.software.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import nju.software.dataobject.Accessory;
import nju.software.dataobject.Fabric;
import nju.software.dataobject.Logistics;
import nju.software.dataobject.Produce;
import nju.software.model.OrderInfo;
import nju.software.model.ProductModel;

public interface BuyService {

	// ===========================采购验证=================================
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getVerifyPurchaseList();

	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> getVerifyPurchaseDetail(Integer orderId);

	@Transactional(rollbackFor = Exception.class)
	public boolean verifyPurchaseSubmit(String taskId, boolean result,
			String comment);

	// ===========================采购成本核算=================================
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getComputePurchaseCostList();

	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> getComputePurchaseCostDetail(Integer orderId);

	@Transactional(rollbackFor = Exception.class)
	public void computePurchaseCostSubmit(int orderId, String taskId,
			boolean result, String comment, String[] fabric_names,
			String[] fabric_amouts, String[] tear_per_meters,
			String[] cost_per_meters, String[] accessory_names,
			String[] accessory_querys, String[] tear_per_piece,
			String[] cost_per_piece);

	// ===========================采购样衣原料=================================
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getPurchaseSampleMaterialList();

	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> getPurchaseSampleMaterialDetail(Integer orderId);

	@Transactional(rollbackFor = Exception.class)
	public boolean purchaseSampleMaterialSubmit(String taskId, boolean result);

	@Transactional(rollbackFor = Exception.class)
	public boolean purchaseSampleMaterialSubmit(String taskId, boolean result,
			boolean needcraft, String orderId, String samplepurName,
			Timestamp samplepurDate, String samplesupplierName);

	// ===========================采购确认=================================
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getConfirmPurchaseList();

	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> getConfirmPurchaseDetail(Integer orderId);

	@Transactional(rollbackFor = Exception.class)
	public boolean confirmPurchaseSubmit(String taskId, boolean result);

	@Transactional(rollbackFor = Exception.class)
	public boolean confirmPurchaseSubmit(String taskId, boolean result,
			String orderId);

	// ===========================大货原料采购=================================
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getPurchaseMaterialList();

	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> getPurchaseMaterialDetail(Integer orderId);

	@Transactional(rollbackFor = Exception.class)
	public boolean purchaseMaterialSubmit(String taskId, boolean result);

	@Transactional(rollbackFor = Exception.class)
	public boolean purchaseMaterialSubmit(String taskId, boolean result,
			String orderId, String masspurName, Timestamp masspurDate,
			String masssupplierName);

	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getSearchVerifyPurchaseList(
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds);

	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getSearchComputePurchaseCostList(
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds);

	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getSearchPurchaseSampleMaterialList(
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds);

	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getSearchConfirmPurchaseList(
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds);

	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getSearchPurchaseMaterialList(
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds);

	@Transactional(rollbackFor = Exception.class)
	Map<String, Object> getPrintProcurementOrderDetail(Integer orderId,
			List<Produce> productList);

	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> purchaseSweaterMaterialList();

	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getSearchPurchaseSweaterMaterialList(
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds);

	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> getPurchaseSweaterMaterialDetail(int orderId);

	@Transactional(rollbackFor = Exception.class)
	public boolean purchaseSweaterMaterialSubmit(String taskId, String orderId,
			String total_price, String weight, String type,
			String purchase_time, String supplier, String head,
			boolean buySweaterMaterialResult);

	@Transactional(rollbackFor = Exception.class)
	public boolean isNeedCraft(String processId, String variableName);

}
