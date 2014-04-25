package nju.software.service;

import java.util.List;
import java.util.Map;
import nju.software.dataobject.Logistics;
import nju.software.dataobject.Package;
import nju.software.dataobject.PackageDetail;
import nju.software.model.OrderInfo;

public interface LogisticsService {

	// ===========================收取样衣=================================
	public List<Map<String, Object>> getReceiveSampleList();

	public Map<String, Object> getReceiveSampleDetail(Integer orderId);

	public boolean receiveSampleSubmit(long taskId, Integer orderId,
			Short result);

	// ===========================样衣发货=================================
	public List<Map<String, Object>> getSendSampleList();

	public Map<String, Object> getSendSampleDetail(Integer orderId);

	public boolean sendSampleSubmit(Map<String, Object> map);

	// ===========================产品入库=================================
	public List<Map<String, Object>> getPackageList();

	public List<Map<String, Object>> getWarehouseList();

	public Map<String, Object> getPackageDetail(Integer orderId);

	public Integer addPackage(Package pack, List<PackageDetail> detail);

	public boolean removePackage(Integer pid);

	public boolean packageSubmit(Integer orderId);

	public List<OrderInfo> getMobileWarehouseList();

	public Map<String, Object> getMobileWarehouseDetail(int orderId);

	public boolean updatePackage(int packageId, String warehouse, String shelf,
			String location);

	public boolean mobileWarehouseSubmit(long taskId, Integer orderId);

	// ===========================产品发货=================================
	public List<Map<String, Object>> getScanClothesList();

	public List<Map<String, Object>> getSendClothesList();

	public OrderInfo getSendClothesDetail(Integer orderId);

	public List<Map<String, Object>> getMobileSendClothesList();

	public Map<String, Object> getMobileSendClothesDetail(Integer orderId);

	public boolean mobileSendClothesSubmit(int orderId);

	public void sendClothesSubmit(Integer orderId, long taskId, float price,
			String name, String time, String number, String remark);

	// 新建一个package，返回新的package_id
	public Package createPackageForOrder(int orderId);

	public List<Package> getPackageListByOrderId(int orderId);

	public List<PackageDetail> getPackageDetailList(int packageId);

	public Package getPackageByPackageId(int packageId);

	public Logistics findByOrderId(String s_id);

}
