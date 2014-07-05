package nju.software.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface DesignService {

	// ===========================设计验证=================================
	public List<Map<String, Object>> getVerifyDesignList();

	public Map<String, Object> getVerifyDesignDetail(int orderId);

	public boolean verifyDesignSubmit(long taskId, boolean result,
			String comment);

	// ===========================上传版型=================================
	public List<Map<String, Object>> getUploadDesignList();

	public Map<String, Object> getUploadDesignDetail(Integer orderId);

	public boolean uploadDesignSubmit(int orderId, long taskId, String url,
			Timestamp uploadTime);

	// ===========================修改版型=================================
	public List<Map<String, Object>> getModifyDesignList();

	public Map<String, Object> getModifyDesignDetail(Integer orderId);

	public boolean modifyDesignSubmit(int orderId, long taskId, String url,
			Timestamp uploadTime);

	// ===========================确认版型=================================
	public List<Map<String, Object>> getConfirmDesignList();

	public Map<String, Object> getConfirmDesignDetail(Integer orderId);

	public List<Map<String, Object>> getSearchVerifyDesignList(
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds);

	public List<Map<String, Object>> getSearchUploadDesignList(
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds);

	public List<Map<String, Object>> getSearchModifyDesignList(
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds);

	public List<Map<String, Object>> getSearchConfirmDesignList(
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds);
}
