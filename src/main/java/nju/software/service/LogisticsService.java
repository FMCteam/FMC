package nju.software.service;

import java.util.List;
import java.util.Map;

import nju.software.dataobject.Logistics;
import nju.software.dataobject.Package;
import nju.software.dataobject.PackageDetail;

public interface LogisticsService {

	// ===========================收取样衣=================================
	public List<Map<String, Object>> getReceiveSampleList();

	public Map<String, Object> getReceiveSampleDetail(Integer orderId);

	public boolean receiveSampleSubmit(String taskId, Integer orderId,
			Short result);

	// ===========================样衣发货=================================
	public List<Map<String, Object>> getSendSampleList();

	public Map<String, Object> getSendSampleDetail(Integer orderId);

	public boolean sendSampleSubmit(Map<String, Object> map);

	// ===========================产品入库=================================
	public List<Map<String, Object>> getPackageList();

	public List<Map<String, Object>> getPackageHaoDuoYiList();

	public List<Map<String, Object>> getWarehouseList();

	public List<Map<String, Object>> getWarehouseHaoDuoYiList();

	public Map<String, Object> getPackageDetail(Integer orderId);

	public Map<String, Object> getPrintWarehouseDetail(Integer orderId,
			Integer packageId);

	public Integer addPackage(Package pack, List<PackageDetail> detail);

	public boolean removePackage(Integer pid);

	public boolean packageSubmit(Integer orderId);

	public List<Map<String, Object>> getMobileWarehouseList();

	public Map<String, Object> getMobileWarehouseDetail(int orderId);

	public boolean updatePackage(int packageId, String warehouse, String shelf,
			String location);

	public boolean mobileWarehouseSubmit(String taskId, Integer orderId);

	// ===========================产品发货=================================
	public List<Map<String, Object>> getScanClothesList();

	public List<Map<String, Object>> getSendClothesList();

	public Map<String, Object> getSendClothesDetail(Integer orderId);

	public List<Map<String, Object>> getMobileSendClothesList();

	public Map<String, Object> getMobileSendClothesDetail(Integer orderId);

	public boolean mobileSendClothesSubmit(int orderId);

	public void sendClothesSubmit(Integer orderId, String taskId, float price,
			String name, String time, String number, String remark,
			String isFinal);

	public List<Package> getPackageListByOrderId(int orderId);

	public List<PackageDetail> getPackageDetailList(int packageId);

	public Package getPackageByPackageId(int packageId);

	public Logistics findByOrderId(String s_id);

	public List<Map<String, Object>> getSearchReceiveSampleList(
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds,
			String userRole, Integer userId);

	public List<Map<String, Object>> getSearchSendSampleList(
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds);

	List<Map<String, Object>> getMobileWarehouseHaoDuoYiList();

}
