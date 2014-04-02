package nju.software.service;

import java.util.List;

import nju.software.dataobject.Accessory;
import nju.software.dataobject.Account;
import nju.software.dataobject.Fabric;
import nju.software.dataobject.Logistics;
import nju.software.dataobject.PackageDetail;
import nju.software.dataobject.Product;
import nju.software.model.OrderInfo;
import nju.software.model.SampleProduceTask;
import nju.software.model.SampleProduceTaskSummary;

public interface ProduceService {

	public boolean verify(Account account, int orderId, long taskId, 
			long processId, boolean productVal, String comment);
	
	public boolean costAccounting(Account account, int orderId, long taskId, 
			long processId, float cut_cost,float manage_cost,float nali_cost,float ironing_cost,
			float swing_cost,float package_cost,float other_cost);
	
	public Logistics getLogisticsByOrderId(int orderId);
	
	public List<Fabric> getFabricByOrderId(int orderId);
	
	public List<Accessory> getAccessoryByOrderId(int orderId);
	
	public List<SampleProduceTaskSummary>getSampleProduceTaskSummaryList();
	
	public SampleProduceTask getSampleProduceTask(Integer orderId,long taskId) ;
	
	public void completeSampleProduceTask(long taskId,String result);
	
	
	public List<OrderInfo> getProduceList();
	
	public OrderInfo getProduceInfo(Integer orderId);

	public List<Product> getProductByOrderId(int parseInt);

	public List<nju.software.dataobject.Package> getPackageByOrderId(int parseInt);

	public List<List<PackageDetail>> getProductDetailByPackage(
			List<nju.software.dataobject.Package> packageList);

	public void pruduceSubmit(String[] pid,String[] askAmount,long taskId);

}
