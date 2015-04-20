package nju.software.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nju.software.dataobject.Account;

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
	
	public Integer getMarketSecrtaryTaskNumber();
	
	/**
	 * 获取当前登录用户。若获取失败，则调用sendJson方法，向response写回结果
	 * @param request
	 * @param response
	 * @return
	 */
	public Account getCurAccount(HttpServletRequest request, HttpServletResponse response);
}
