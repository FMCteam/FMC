package nju.software.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import nju.software.dataobject.Accessory;
import nju.software.dataobject.Account;
import nju.software.dataobject.Customer;
import nju.software.dataobject.DesignCad;
import nju.software.dataobject.Fabric;
import nju.software.dataobject.Logistics;
import nju.software.dataobject.Money;
import nju.software.dataobject.Order;
import nju.software.dataobject.Produce;
import nju.software.dataobject.Product;
import nju.software.dataobject.Quote;
import nju.software.dataobject.VersionData;
import nju.software.model.OrderInfo;
import nju.software.model.OrderModel;
import nju.software.model.ProductModel;



public interface MarketService {
	
	//==========================报价商定=======================
	public List<Map<String, Object>> getConfirmQuoteList(String actorId);
	
	public OrderInfo getConfirmQuoteDetail(String arctorId,Integer orderId);
	
	public boolean confirmQuoteSubmit(String actorId,long taskId,String result);
	
	
	//==========================签订合同=======================
	public List<Map<String, Object>> getSignContractList(String actorId);
	
	public Map<String, Object> getSignContractDetail(String arctorId,Integer orderId);
	
	public void signContractSubmit(String actorId,long taskId,int i,double d,double discount, String url);
	
	
	
	public List<Customer> getAddOrderList();
	
	public Customer getAddOrderDetail(Integer cid);
	
	public boolean addOrderSubmit(Order order, List<Fabric> fabrics,
			List<Accessory> accessorys, Logistics logistics,List<Produce> produces, List<Produce> sample_produces, List<VersionData> versions, DesignCad cad, HttpServletRequest request);
	


	
	//public void completeQuoteConfirmTaskSummary(long taskId,String result);
	
	public List<Product> getProductList(int orderId, String productAskAmount, String productColor, String productStyle);

	public boolean confirmProduceOrderSubmit(String string, int orderId, long taskId, 
			long processId, boolean comfirmworksheet, List<Produce> produces);
	
	
	
	public List<OrderInfo> getOrderInfoList(Integer employeeId);
	
	public OrderInfo getOrderInfo(Integer orderId,long taskId);
	
	//public void completeSignContract(Integer orderId,double discount,long taskId);

	//public List<QuoteConfirmTaskSummary> getQuoteModifyTaskSummaryList(Integer employeeId);

	public List<Map<String, Object>> getModifyProductList(Integer userId);

	public boolean modifyProductSubmit(String string, int id, long taskId,
			long processId, boolean b, List<Produce> produces);

	public List<Map<String, Object>> getModifyQuoteList(Integer userId);

	public Map<String, Object> getModifyQuoteDetail(int id, int accountId);

	public Map<String, Object> getModifyProductDetail(int id, Integer integer);

	public List<Map<String, Object>> getMergeQuoteList(Integer accountId);

	public void mergeQuoteSubmit(int accountId, Quote quote, int id, long taskId,
			long processId);

	public List<Map<String, Object>> getVerifyQuoteList(Integer accountId);

	public void verifyQuoteSubmit(Quote quote, int id,
			long taskId, long processId);

	public List<Map<String, Object>> getModifyOrderList(Integer accountId);

	public Map<String, Object> getModifyOrderDetail(int accountId, int id);

	public void modifyOrderSubmit(Order order, List<Fabric> fabrics,
			List<Accessory> accessorys, Logistics logistics, List<Produce> produces, List<Produce> sample_produces, List<VersionData> versions, DesignCad cad, boolean editok, long taskId, Integer accountId);

	public Map<String, Object> getMergeQuoteDetail(Integer userId, int id);

	public Map<String, Object> getVerifyQuoteDetail(Integer userId, int id);

	public Map<String, Object> getConfirmQuoteDetail(Integer userId, int id);

	public void modifyQuoteSubmit(Quote quote, int id, long taskId,
			long processId, Integer userId);

	public List<Map<String, Object>> getConfirmProductList(String actorId);

	public Map<String, Object> getConfirmProductDetail(Integer userId, int id);
	
	public List<Map<String, Object>>getOrderList(Integer page);
	
	public Map<String, Object>getOrderDetail(Integer orderId);

	public List<Map<String, Object>> getAddMoreOrderList(int customerId);
	
	public List<Map<String, Object>> getSearchAddMoreOrderList(String ordernumber,String customername,String stylename,String startdate,String enddate, Integer[] employeeIds);

	public Map<String, Object> getAddMoreOrderDetail(int id);

	public void addMoreOrderSubmit(Order order, List<Fabric> fabrics,
			List<Accessory> accessorys, Logistics logistics,
			List<Produce> produces, List<VersionData> versions, DesignCad cad,
			HttpServletRequest request);

	public List<Map<String, Object>> getSearchOrderList(String ordernumber,
			String customername, String stylename, String startdate,String enddate,
			Integer[] employeeIds,String userRole,Integer userId);

	public List<Map<String, Object>> getSearchModifyOrderList(Integer userId,
			String ordernumber, String customername, String stylename,
			String startdate,String enddate, Integer[] employeeIds);

	public List<Map<String, Object>> getSearchMergeQuoteList(Integer userId,
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds);

	public List<Map<String, Object>> getSearchVerifyQuoteList(Integer userId,
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds);

	public List<Map<String, Object>> getSearchConfirmQuoteList(String string,
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds);

	public List<Map<String, Object>> getSearchModifyQuoteList(Integer userId,
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds);

	public List<Map<String, Object>> getSearchConfirmProductList(
			String actorId, String ordernumber, String customername,
			String stylename, String startdate, String enddate,
			Integer[] employeeIds);

	public List<Map<String, Object>> getSearchModifyProductList(Integer userId,
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds);

	public List<Map<String, Object>> getSearchSignContractList(String actorId,
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds);

	public List<Map<String, Object>> getOrders();
	
	public List<Map<String, Object>> getOrders(String userRole, Integer userId);

	public List<Map<String, Object>> getOrdersDoing();
	
	public List<Map<String, Object>> getOrdersDoing(String userRole, Integer userId);

	public List<Map<String, Object>> getOrdersDone();


	public List<Map<String, Object>> getSearchOrdersDoing(String ordernumber,
			String customername, String stylename, String startdate,
			String enddate, Integer[] employeeIds,String userRole,Integer userId);


	public List<Map<String, Object>> getSearchOrdersDone(String ordernumber,
			String customername, String stylename, String startdate,
			String enddate, Integer[] employeeIds,String userRole,Integer userId);
	

	public List<Map<String, Object>> getOrdersDone(String userRole, Integer userId);

	public void sendOrderInfoViaEmail(Order order, Customer customer);

	public void sendOrderInfoViaPhone(Order order, Customer customer);

	public ArrayList<String> getProcessStateName(Integer orderId);
	public void signContractSubmit(String actorId, long parseLong,
			int parseInt, double parseDouble, double parseDouble2, String url,
			String confirmDepositFileUrl);

	public boolean confirmQuoteSubmit(String actorId, long parseLong,
			int parseInt, String result, String url);

	public void signConfirmFinalPaymentFileSubmit( int orderId, String confirmFinalPaymentFileUrl);

	public void verifyQuoteSubmit(Quote quote, int id, long taskId,
			long processId, boolean result, String comment);
     
	public List<Map<String, Object>> getPushRestOrderList(String userId);

	public Map<String, Object> getPushRestOrderDetail(String userId,
			int orderId);

	public boolean getPushRestOrderSubmit(String actorId, long taskId,
			boolean result);
	
	public boolean getPushRestOrderSubmit(String actorId, long taskId,
			boolean result,String orderId_string);

}
