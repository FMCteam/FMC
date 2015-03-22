package nju.software.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import nju.software.dataobject.Logistics;
import nju.software.dataobject.Package;
import nju.software.dataobject.PackageDetail;

public interface LogisticsService {

	// ===========================收取样衣=================================
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getReceiveSampleList();

	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> getReceiveSampleDetail(Integer orderId);

	@Transactional(rollbackFor = Exception.class)
	public boolean receiveSampleSubmit(String taskId, Integer orderId,
			Short result);

	// ===========================样衣发货=================================
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getSendSampleList();

	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> getSendSampleDetail(Integer orderId);

	@Transactional(rollbackFor = Exception.class)
	public boolean sendSampleSubmit(Map<String, Object> map);

	// ===========================产品入库=================================
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getPackageList();

	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getPackageHaoDuoYiList();

	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getWarehouseList();

	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getWarehouseHaoDuoYiList();

	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> getPackageDetail(Integer orderId);

	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> getPrintWarehouseDetail(Integer orderId,
			Integer packageId);

	@Transactional(rollbackFor = Exception.class)
	public Integer addPackage(Package pack, List<PackageDetail> detail);

	@Transactional(rollbackFor = Exception.class)
	public boolean removePackage(Integer pid);

	@Transactional(rollbackFor = Exception.class)
	public boolean packageSubmit(Integer orderId);

	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getMobileWarehouseList();

	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> getMobileWarehouseDetail(int orderId);

	@Transactional(rollbackFor = Exception.class)
	public boolean updatePackage(int packageId, String warehouse, String shelf,
			String location);

	@Transactional(rollbackFor = Exception.class)
	public boolean mobileWarehouseSubmit(String taskId, Integer orderId);

	// ===========================产品发货=================================
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getScanClothesList();

	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getSendClothesList();

	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> getSendClothesDetail(Integer orderId);

	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getMobileSendClothesList();

	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> getMobileSendClothesDetail(Integer orderId);

	@Transactional(rollbackFor = Exception.class)
	public boolean mobileSendClothesSubmit(int orderId);

	@Transactional(rollbackFor = Exception.class)
	public void sendClothesSubmit(Integer orderId, String taskId, float price,
			String name, String time, String number, String remark,
			String isFinal);

	@Transactional(rollbackFor = Exception.class)
	public List<Package> getPackageListByOrderId(int orderId);

	@Transactional(rollbackFor = Exception.class)
	public List<PackageDetail> getPackageDetailList(int packageId);

	@Transactional(rollbackFor = Exception.class)
	public Package getPackageByPackageId(int packageId);

	@Transactional(rollbackFor = Exception.class)
	public Logistics findByOrderId(String s_id);

	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getSearchReceiveSampleList(
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds,
			String userRole, Integer userId);

	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getSearchSendSampleList(
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds);

	@Transactional(rollbackFor = Exception.class)
	List<Map<String, Object>> getMobileWarehouseHaoDuoYiList();

}
