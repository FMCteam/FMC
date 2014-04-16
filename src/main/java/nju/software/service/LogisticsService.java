package nju.software.service;

import java.util.List;
import java.util.Map;

import nju.software.dataobject.Logistics;
import nju.software.dataobject.Package;
import nju.software.dataobject.PackageDetail;
import nju.software.model.OrderInfo;

public interface LogisticsService {

	// ===========================收取样衣=================================
	public List<OrderInfo> getReceiveSampleList();

	public OrderInfo getReceiveSampleDetail(Integer orderId);

	public boolean receiveSampleSubmit(long taskId, String result);

	// ===========================样衣发货=================================
	public List<Map<String, Object>> getSendSampleList();

	public OrderInfo getSendSampleDetail(Integer orderId);

	public boolean sendSampleSubmit(Map<String, Object> map);

	// ===========================产品入库=================================
	public List<Map<String, Object>> getPackageList();

	public List<Map<String, Object>> getWarehouseList();

	public Integer addPackage(Package pack, List<PackageDetail> detail);

	public boolean removepackage(Integer pid);

	public boolean packageSubmit(Integer orderId);

	public OrderInfo getWarehouseDetail(Integer orderId);

	public boolean warehouseSubmit(long taskId, String result);

	// ===========================产品发货=================================
	public List<OrderInfo> getSendClothesList();

	public OrderInfo getSendClothesDetail(Integer orderId);

	public void sendClothesSubmit(Integer orderId, long taskId, float price,
			String name, String time,String number,String remark);

	// ======================得到未扫描确认的列表=============================
	public List<OrderInfo> getSendClothesUncheckedList();

	// 得到未入库的列表
	public List<OrderInfo> getSendClothesUnstoredList();

	// ===扫描确认===
	public boolean setOrderScanChecked(int orderId);

	// ===扫描确认===
	public boolean setOrderStored(int orderId);

	// 得到待扫描的pacakge的详细信息，包括packages和packagedetails
	public OrderInfo getScanCheckDetail(int orderId);

	// 新建一个package，返回新的package_id
	public Package createPackageForOrder(int orderId);

	//
	public boolean updateSendClothesStoreInfo(int packageId, String warehouse,
			String shelf, String location);

	public List<Package> getPackageListByOrderId(int orderId);

	public OrderInfo getStoreClothesDetail(int orderId);

	public List<PackageDetail> getPackageDetailList(int packageId);

	public Package getPackageByPackageId(int packageId);

	public Logistics findByOrderId(String s_id);

}
