package nju.software.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import nju.software.dataobject.Accessory;
import nju.software.dataobject.Account;
import nju.software.dataobject.Fabric;
import nju.software.dataobject.Logistics;
import nju.software.dataobject.PackageDetail;
import nju.software.dataobject.Produce;
import nju.software.dataobject.Product;
import nju.software.model.OrderInfo;

public interface ProduceService {

	public boolean verifyProduceSubmit(Account account, int orderId, long taskId, 
			boolean productVal, String comment);
	
	public boolean costAccounting(Account account, int orderId, long taskId, 
			long processId, float cut_cost,float manage_cost,float nali_cost,float ironing_cost,
			float swing_cost,float package_cost,float other_cost);
	
	public Logistics getLogisticsByOrderId(int orderId);
	
	public List<Fabric> getFabricByOrderId(int orderId);
	
	public List<Accessory> getAccessoryByOrderId(int orderId);
	
	public List<Map<String, Object>> getProduceSampleList();
	
	public Map<String,Object> getProduceSampleDetail(Integer orderId) ;
	
	public boolean produceSampleSubmit(long taskId, boolean result, List<Produce> produceList);
	
	public List<Produce> getProduceList(int orderId, String produceColor, String produceXS, String produceS, 
			String produceM, String produceL, String produceXL, String produceXXL, String type);
	
	public List<Map<String, Object>> getProduceList();
	
	public Map<String,Object> getProduceDetail(Integer orderId);

	
  public List<Map<String, Object>> getComputeProduceCostList();
	
	public Map<String,Object> getComputeProduceCostInfo(Integer orderId);

	public void ComputeProduceCostSubmit(int orderId,long taskId,float cut_cost, float manage_cost, float nali_cost,
			float ironing_cost, float swing_cost, float package_cost,
			float other_cost, float design_cost);
	
	public List<Product> getProductByOrderId(int parseInt);

	public List<nju.software.dataobject.Package> getPackageByOrderId(int parseInt);

	public List<List<PackageDetail>> getProductDetailByPackage(
			List<nju.software.dataobject.Package> packageList);

	public boolean pruduceSubmit(long taskId, boolean result, List<Produce> produceList);

	public void savePackageDetail(int parseInt, String[] array_amount,
			String[] array_color, String[] array_name, Timestamp entryTime);

	public List<Map<String, Object>> getVerifyProduceList();

	public Map<String,Object> getVerifyProduceDetail(int orderId);

}
