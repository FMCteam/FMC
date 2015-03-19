package nju.software.service;

public interface CommonService {
	
	public Integer getTaskNumber(String userId);
	
	public Integer getTaskNumber(String userId, String taskName);
	
	public Integer getAllTaskNumber();
	
	public Integer getMarketStaffTaskNumber();

	public Integer getMarketStaffTaskNumber(String taskName);
}
