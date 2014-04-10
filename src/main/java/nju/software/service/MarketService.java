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
import nju.software.dataobject.Product;
import nju.software.model.OrderInfo;
import nju.software.model.OrderModel;
import nju.software.model.ProductModel;
import nju.software.model.QuoteConfirmTaskSummary;


public interface MarketService {
	
	//==========================报价商定=======================
	public List<OrderInfo>getConfirmQuoteList(String actorId);
	
	public OrderInfo getConfirmQuoteDetail(String arctorId,Integer orderId);
	
	public boolean confirmQuoteSubmit(String actorId,long taskId,String result);
	
	
	//==========================签订合同=======================
	public List<OrderInfo>getSignContractList(String actorId);
	
	public OrderInfo getSignContractDetail(String arctorId,Integer orderId);
	
	public boolean signContractSubmit(String actorId,long taskId,String result,Integer orderId,double discount);
	
	
	
	public List<Customer>getAddOrderList();
	
	public Customer getAddOrderDetail(Integer cid);
	
	public boolean addOrderSubmit(Order order, List<Fabric> fabrics,
			List<Accessory> accessorys, Logistics logistics,HttpServletRequest request);
	


	
	public void completeQuoteConfirmTaskSummary(long taskId,String result);
	
	public List<Product> getProductList(int orderId, String productAskAmount, String productColor, String productStyle);

	public boolean confirmProduceOrderSubmit(Account account, int orderId, long taskId, 
			long processId, boolean comfirmworksheet, List<Product> productList);
	
	
	
	public List<OrderInfo> getOrderInfoList(Integer employeeId);
	
	public OrderInfo getOrderInfo(Integer orderId,long taskId);
	
	public void completeSignContract(Integer orderId,double discount,long taskId);

	public List<QuoteConfirmTaskSummary> getQuoteModifyTaskSummaryList(Integer employeeId);

	public List<OrderInfo> getModifyProductList(Integer userId);

	public boolean modifyProduct(Integer userId, int id, long taskId,
			long processId, boolean b, List<Product> pList);

	public List<OrderInfo> getModifyQuoteList(Integer userId);

	public OrderInfo getModifyQuoteDetail(int id);

	public OrderInfo getModifyProductDetail(int id, long taskId);

	public List<OrderInfo> getMergeQuoteList(Integer accountId);

	public void mergeQuoteSubmit(float inner, float outer, int id, long taskId,
			long processId);

	public List<OrderInfo> getVerifyQuoteList(Integer accountId);

	public void verifyQuoteSubmit(float inner, float outer, int id,
			long taskId, long processId);

	public List<OrderInfo> getModifyOrderList(Integer accountId);

	public OrderInfo getModifyOrderDetail(int id, long task_id);

	public void modifyOrderSubmit(Order order, List<Fabric> fabrics,
			List<Accessory> accessorys, Logistics logistics, long taskId);
	
}
