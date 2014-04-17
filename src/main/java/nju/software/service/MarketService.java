package nju.software.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nju.software.dataobject.Accessory;
import nju.software.dataobject.Account;
import nju.software.dataobject.Customer;
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
	public List<OrderInfo> getConfirmQuoteList(String actorId);
	
	public OrderInfo getConfirmQuoteDetail(String arctorId,Integer orderId);
	
	public boolean confirmQuoteSubmit(String actorId,long taskId,String result);
	
	
	//==========================签订合同=======================
	public List<OrderInfo> getSignContractList(String actorId);
	
	public OrderInfo getSignContractDetail(String arctorId,Integer orderId);
	
	public boolean signContractSubmit(String actorId,long taskId,int i,double d,double discount);
	
	
	
	public List<Customer> getAddOrderList();
	
	public Customer getAddOrderDetail(Integer cid);
	
	public boolean addOrderSubmit(Order order, List<Fabric> fabrics,
			List<Accessory> accessorys, Logistics logistics,List<Produce> produces, List<Produce> sample_produces, List<VersionData> versions, HttpServletRequest request);
	


	
	//public void completeQuoteConfirmTaskSummary(long taskId,String result);
	
	public List<Product> getProductList(int orderId, String productAskAmount, String productColor, String productStyle);

	public boolean confirmProduceOrderSubmit(String string, int orderId, long taskId, 
			long processId, boolean comfirmworksheet, List<Produce> produces);
	
	
	
	public List<OrderInfo> getOrderInfoList(Integer employeeId);
	
	public OrderInfo getOrderInfo(Integer orderId,long taskId);
	
	//public void completeSignContract(Integer orderId,double discount,long taskId);

	//public List<QuoteConfirmTaskSummary> getQuoteModifyTaskSummaryList(Integer employeeId);

	public List<OrderInfo> getModifyProductList(Integer userId);

	public boolean modifyProductSubmit(String string, int id, long taskId,
			long processId, boolean b, List<Produce> produces);

	public List<OrderInfo> getModifyQuoteList(Integer userId);

	public OrderInfo getModifyQuoteDetail(int id, int accountId);

	public OrderInfo getModifyProductDetail(int id, long taskId, Integer integer);

	public List<OrderInfo> getMergeQuoteList(Integer accountId);

	public void mergeQuoteSubmit(int accountId, Quote quote, int id, long taskId,
			long processId);

	public List<OrderInfo> getVerifyQuoteList(Integer accountId);

	public void verifyQuoteSubmit(Quote quote, int id,
			long taskId, long processId);

	public List<OrderInfo> getModifyOrderList(Integer accountId);

	public OrderInfo getModifyOrderDetail(int accountId, int id, long task_id);

	public void modifyOrderSubmit(Order order, List<Fabric> fabrics,
			List<Accessory> accessorys, Logistics logistics, List<Produce> produces, List<Produce> sample_produces, List<VersionData> versions, boolean editok, long taskId, Integer accountId);

	public OrderInfo getMergeQuoteDetail(Integer userId, int id, long task_id);

	public OrderInfo getVerifyQuoteDetail(Integer userId, int id, long task_id);

	public OrderInfo getConfirmQuoteDetail(Integer userId, int id, long task_id);

	public void modifyQuoteSubmit(Quote quote, int id, long taskId,
			long processId, Integer userId);

	public List<OrderInfo> getConfirmProductList(String actorId);

	public OrderInfo getConfirmProductDetail(Integer userId, int id, long taskId);
	
}
