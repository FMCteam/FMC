package nju.software.service;

import java.util.List;

import nju.software.dataobject.Accessory;
import nju.software.dataobject.Account;
import nju.software.dataobject.Fabric;
import nju.software.dataobject.Logistics;

public interface ProduceService {

	public boolean verify(Account account, int orderId, String taskName, boolean productVal);
	
	public Logistics getLogisticsByOrderId(int orderId);
	
	public List<Fabric> getFabricByOrderId(int orderId);
	
	public List<Accessory> getAccessoryByOrderId(int orderId);
}
