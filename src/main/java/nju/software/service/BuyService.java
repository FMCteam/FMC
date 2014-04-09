package nju.software.service;

import java.util.List;

import nju.software.dataobject.Accessory;
import nju.software.dataobject.Account;
import nju.software.dataobject.Fabric;
import nju.software.dataobject.Logistics;
import nju.software.model.OrderInfo;
import nju.software.model.ProductModel;

public interface BuyService {
	

	public List<OrderInfo> getPurchaseSampleMaterialList();
	
	public OrderInfo getPurchaseSampleMaterialDetail(Integer orderId);
	
	public boolean purchaseSampleMaterialSubmit(long taskId,String result);
	
	public List<OrderInfo> getConfirmPurchaseList();
	
	public OrderInfo getConfirmPurchaseDetail(Integer orderId);
	
	public boolean confirmPurchaseSubmit(long taskId,String result);
	
	public List<OrderInfo> getPurchaseMaterialList();
	
	public OrderInfo getPurchaseMaterialDetail(Integer orderId);
	
	public boolean purchaseMaterialSubmit(long taskId,String result);
	
	
	
	
	

	public boolean verifyPurchaseSubmit(Account account, int orderId, long taskId, 

			long processId, boolean buyVal, String comment);

	public boolean costAccounting(Account account, int orderId, long taskId, 
			long processId, String[] fabric_names,String[] tear_per_meters,String[] cost_per_meters
			,String[] fabric_prices);
	

	public Logistics getLogisticsByOrderId(int orderId);
	
	public List<Fabric> getFabricByOrderId(int orderId);
	
	public List<Accessory> getAccessoryByOrderId(int orderId);

	public ProductModel getProductDetail(int int_orderId, int int_taskId,
			int int_processId);

	public List<OrderInfo> getVerifyPurchaseList();

	public OrderInfo getVerifyPurchaseDetail(int orderId, long taskId);
	
}
