package nju.software.service;

import java.sql.Timestamp;
import java.util.List;

import nju.software.dataobject.Accessory;
import nju.software.dataobject.Account;
import nju.software.dataobject.Fabric;
import nju.software.dataobject.Logistics;
import nju.software.dataobject.PackageDetail;
import nju.software.dataobject.Produce;
import nju.software.dataobject.Product;
import nju.software.model.OrderInfo;
import nju.software.model.SampleProduceTask;
import nju.software.model.SampleProduceTaskSummary;

public interface ProduceService {

	public boolean verifyProduceSubmit(Account account, int orderId, long taskId, 
			boolean productVal, String comment);
	
	public boolean costAccounting(Account account, int orderId, long taskId, 
			long processId, float cut_cost,float manage_cost,float nali_cost,float ironing_cost,
			float swing_cost,float package_cost,float other_cost);
	
	public Logistics getLogisticsByOrderId(int orderId);
	
	public List<Fabric> getFabricByOrderId(int orderId);
	
	public List<Accessory> getAccessoryByOrderId(int orderId);
	
	public List<OrderInfo>getProduceSampleList();
	
	public OrderInfo getProduceSampleDetail(Integer orderId) ;
	
	public boolean produceSampleSubmit(long taskId, boolean result, List<Produce> produceList);
	
	public List<Produce> getProduceList(String produceColor, String produceXS, String produceS, 
			String produceM, String produceL, String produceXL, String produceXXL);
	
	public List<OrderInfo> getProduceList();
	
	public OrderInfo getProduceDetail(Integer orderId);

	
  public List<OrderInfo> getComputeProduceCostList();
	
	public OrderInfo getComputeProduceCostInfo(Integer orderId);

	public void ComputeProduceCostSubmit(int orderId,long taskId,float cut_cost, float manage_cost, float nali_cost,
			float ironing_cost, float swing_cost, float package_cost,
			float other_cost, float design_cost);
	
	public List<Product> getProductByOrderId(int parseInt);

	public List<nju.software.dataobject.Package> getPackageByOrderId(int parseInt);

	public List<List<PackageDetail>> getProductDetailByPackage(
			List<nju.software.dataobject.Package> packageList);

	public boolean pruduceSubmit(String[] pid,String[] askAmount,long taskId);

	public void savePackageDetail(int parseInt, String[] array_amount,
			String[] array_color, String[] array_name, Timestamp entryTime);

	public List<OrderInfo> getVerifyProduceList();

	public OrderInfo getVerifyProduceDetail(int orderId, long taskId);

}
