package nju.software.service;

import java.util.List;

import nju.software.dataobject.Accessory;
import nju.software.dataobject.Account;
import nju.software.dataobject.Fabric;
import nju.software.dataobject.Logistics;
import nju.software.model.ProductModel;

public interface BuyService {
	
	public boolean verify(Account account, int orderId, long taskId, 
			long processId, boolean buyVal, String comment);

	public Logistics getLogisticsByOrderId(int orderId);
	
	public List<Fabric> getFabricByOrderId(int orderId);
	
	public List<Accessory> getAccessoryByOrderId(int orderId);

	public ProductModel getProductDetail(int int_orderId, int int_taskId,
			int int_processId);
	
}
