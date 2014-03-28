package nju.software.service;

import java.util.List;

import nju.software.dataobject.Accessory;
import nju.software.dataobject.Account;
import nju.software.dataobject.Fabric;
import nju.software.dataobject.Logistics;
import nju.software.model.SampleProduceTask;
import nju.software.model.SampleProduceTaskSummary;

public interface ProduceService {

	public boolean verify(Account account, int orderId, long taskId, 
			long processId, boolean productVal, String comment);
	
	public Logistics getLogisticsByOrderId(int orderId);
	
	public List<Fabric> getFabricByOrderId(int orderId);
	
	public List<Accessory> getAccessoryByOrderId(int orderId);
	
	public List<SampleProduceTaskSummary>getSampleProduceTaskSummaryList();
	
	public SampleProduceTask getSampleProduceTask(Integer orderId,long taskId) ;
	
	public void completeSampleProduceTask(long taskId,String result);
}
