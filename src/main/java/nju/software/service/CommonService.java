package nju.software.service;

import org.springframework.transaction.annotation.Transactional;

public interface CommonService {
	
	@Transactional(rollbackFor = Exception.class)
	public Integer getTaskNumber(String userId);
	
	@Transactional(rollbackFor = Exception.class)
	public Integer getTaskNumber(String userId, String taskName);
	
	@Transactional(rollbackFor = Exception.class)
	public Integer getAllTaskNumber();
	
	@Transactional(rollbackFor = Exception.class)
	public Integer getMarketStaffTaskNumber();

	@Transactional(rollbackFor = Exception.class)
	public Integer getMarketStaffTaskNumber(String taskName);
}
