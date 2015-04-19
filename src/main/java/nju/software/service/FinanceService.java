package nju.software.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import nju.software.dataobject.Money;
import nju.software.dataobject.Produce;

public interface FinanceService {

	// ===========================样衣金确认=================================
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getConfirmSampleMoneyList(String actorId);

	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> getConfirmSampleMoneyDetail(String actorId,
			Integer orderId);

	@Transactional(rollbackFor = Exception.class)
	public boolean confirmSampleMoneySubmit(String actorId, String taskId,
			boolean result, Money money);

	// ===========================定金确认===================================
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getConfirmDepositList(String actorId);

	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> getConfirmDepositDetail(String actorId,
			Integer orderId);

	@Transactional(rollbackFor = Exception.class)
	public boolean confirmDepositSubmit(String actorId, String taskId,
			boolean result, Money money);

	// ===========================尾款确认===================================
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getConfirmFinalPaymentList(String actorId);

	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> getConfirmFinalPaymentDetail(String actorId,
			Integer orderId);

	@Transactional(rollbackFor = Exception.class)
	public boolean confirmFinalPaymentSubmit(String actorId, String taskId,
			boolean result, Money money);

	@Transactional(rollbackFor = Exception.class)
	public boolean confirmFinalPaymentSubmit(String actorId, String taskId,
			boolean result, Money money, Integer orderId);

	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getProcessState(Integer orderId);

	@Transactional(rollbackFor = Exception.class)
	public ArrayList<String> getProcessStateName(Integer orderId);

	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getSearchConfirmSampleMoneyList(
			String actorId, String ordernumber, String customername,
			String stylename, String startdate, String enddate,
			Integer[] employeeIds);

	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getSearchConfirmDepositList(
			String actorId, String ordernumber, String customername,
			String stylename, String startdate, String enddate,
			Integer[] employeeIds);

	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getSearchConfirmFinalPaymentList(
			String actorId, String ordernumber, String customername,
			String stylename, String startdate, String enddate,
			Integer[] employeeIds);

	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getReturnDepositList(String actorId);

	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> getReturnDepositDetail(String actorId,
			int orderId);

	@Transactional(rollbackFor = Exception.class)
	public boolean returnDepositSubmit(String actorId, String taskId);

	@Transactional(rollbackFor = Exception.class)
	public boolean returnDepositSubmit(String actorId, String taskId, Integer orderId);

	@Transactional(rollbackFor = Exception.class)
	public boolean confirmSampleMoneySubmit(String actorId, String taskId,
			boolean result, Money money, int orderId);

	@Transactional(rollbackFor = Exception.class)
	public boolean confirmDepositSubmit(String actorId, String taskId,
			boolean result, Money money, int orderId);

	@Transactional(rollbackFor = Exception.class)
	List<Map<String, Object>> getReturnDepositList(String actorId,
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds);

	@Transactional(rollbackFor = Exception.class)
	Map<String, Object> getPrintProcurementOrderDetail(Integer orderId,
			List<Produce> produces);
}
