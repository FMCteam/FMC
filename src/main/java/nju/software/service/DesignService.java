package nju.software.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

public interface DesignService {

	// ===========================设计验证=================================
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getVerifyDesignList();

	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> getVerifyDesignDetail(int orderId);

	@Transactional(rollbackFor = Exception.class)
	public boolean verifyDesignSubmit(String taskId, boolean result,
			String comment);

	// ===========================上传版型=================================
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getUploadDesignList();

	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> getUploadDesignDetail(Integer orderId);

	@Transactional(rollbackFor = Exception.class)
	public void uploadDesignSubmit(int orderId, String taskId, String url,
			Timestamp uploadTime);

	// ===========================修改版型=================================
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getModifyDesignList();

	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> getModifyDesignDetail(Integer orderId);

	@Transactional(rollbackFor = Exception.class)
	public boolean modifyDesignSubmit(int orderId, String taskId, String url,
			Timestamp uploadTime);

	// ===========================确认版型=================================
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getConfirmDesignList();

	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> getConfirmDesignDetail(Integer orderId);

	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getSearchVerifyDesignList(
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds);

	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getSearchUploadDesignList(
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds);

	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getSearchModifyDesignList(
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds);

	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getSearchConfirmDesignList(
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds);

	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getComputeDesignCostList();

	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getSearchComputeDesignCostList(
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds);

	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> getComputeDesignCostInfo(Integer orderId);

	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getNeedCraftList();

	@Transactional(rollbackFor = Exception.class)
	public boolean produceSampleSubmit(String taskId, boolean result);

	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getNeedCraftSampleList();

	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getTypeSettingSliceList();

	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> getNeedCraftSampleDetail(int orderId);

	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> getNeedCraftProductDetail(int orderId);

	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> getTypeSettingSliceDetail(int orderId);

	@Transactional(rollbackFor = Exception.class)
	public void uploadCraftFileSubmit(int orderId, String craftFileUrl);

	@Transactional(rollbackFor = Exception.class)
	public void needCraftSampleSubmit(int orderId, String taskId,
			String craftLeader, Timestamp completeTime);

	@Transactional(rollbackFor = Exception.class)
	public void needCraftProductSubmit(int orderId, String taskId,
			String crafsManName, Timestamp crafsProduceDate);

	@Transactional(rollbackFor = Exception.class)
	public void getTypeSettingSliceSubmit(int orderId, String taskId);

	@Transactional(rollbackFor = Exception.class)
	public void computeDesignCostSubmit(int orderId, String taskId,
			boolean result, String comment, short needCraft,
			float stampDutyMoney, float washHangDyeMoney, float laserMoney,
			float embroideryMoney, float crumpleMoney, float openVersionMoney);

	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getConfirmCadList();

	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> getConfirmCadDetail(int orderId);

	@Transactional(rollbackFor = Exception.class)
	public boolean confirmCadSubmit(int orderId, String taskId, String cadurl,
			Timestamp uploadTime);

	@Transactional(rollbackFor = Exception.class)
	public void getTypeSettingSliceSubmit(int orderId, String cadding_side,
			String taskId);

	@Transactional(rollbackFor = Exception.class)
	public boolean produceSampleSubmit(String taskId, boolean result,
			String orderId);

	@Transactional(rollbackFor = Exception.class)
	public void EntryCadData(int orderId, String taskId, String url,
			Timestamp uploadTime, String cadSide, Timestamp completeTime);

	// 获取订单中工艺状态
	@Transactional(rollbackFor = Exception.class)
	String getCraftInfo(Integer orderId);

	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getSearchNeedCraftSampleList(
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds);

	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getSearchNeedCraftList(String ordernumber,
			String customername, String stylename, String startdate,
			String enddate, Integer[] employeeIds);

	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getSearchTypeSettingSliceList(
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds);

	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getSearchConfirmCadList(
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds);

}
