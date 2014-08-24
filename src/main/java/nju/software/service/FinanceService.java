package nju.software.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nju.software.dataobject.Money;
import nju.software.model.OrderInfo;

public interface FinanceService {
	
	//===========================样衣金确认=================================
	public List<Map<String,Object>>getConfirmSampleMoneyList(String actorId);
	
	public Map<String,Object> getConfirmSampleMoneyDetail(String actorId,Integer orderId);
	
	public boolean confirmSampleMoneySubmit(String actorId,long taskId,boolean result, Money money);
	
	//===========================定金确认===================================
	public List<Map<String,Object>>getConfirmDepositList(String actorId);
	
	public Map<String,Object> getConfirmDepositDetail(String actorId,Integer orderId);
	
	public boolean confirmDepositSubmit(String actorId,long taskId,boolean result, Money money);
	
	//===========================尾款确认===================================
	public List<Map<String,Object>>getConfirmFinalPaymentList(String actorId);
	
	public Map<String,Object> getConfirmFinalPaymentDetail(String actorId,Integer orderId);
	
	public boolean confirmFinalPaymentSubmit(String actorId,long taskId,boolean result, Money money);
	public boolean confirmFinalPaymentSubmit(String actorId, long taskId,boolean result, Money money,Integer orderId);
	
	public  List<Map<String, Object>> getProcessState(Integer orderId);
	public  ArrayList<String> getProcessStateName(Integer orderId);

	public List<Map<String, Object>> getSearchConfirmSampleMoneyList(
			String actorId, String ordernumber, String customername,
			String stylename, String startdate, String enddate,
			Integer[] employeeIds);

	public List<Map<String, Object>> getSearchConfirmDepositList(
			String actorId, String ordernumber, String customername,
			String stylename, String startdate, String enddate,
			Integer[] employeeIds);

	public List<Map<String, Object>> getSearchConfirmFinalPaymentList(
			String actorId, String ordernumber, String customername,
			String stylename, String startdate, String enddate,
			Integer[] employeeIds);

	public List<Map<String, Object>> getReturnDepositList(String actorId);

	public Map<String, Object> getReturnDepositDetail(String actorId,
			int orderId);

	public void returnDepositSubmit(String actorId, long taskId);
	public void returnDepositSubmit(String actorId, long taskId,Integer orderId);

	public boolean confirmSampleMoneySubmit(String actorId, long taskId,
			boolean result, Money money, int orderId);

	public boolean confirmDepositSubmit(String actorId, long taskId,
			boolean result, Money money, int orderId);

	List<Map<String, Object>> getReturnDepositList(String actorId,
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds);
}
