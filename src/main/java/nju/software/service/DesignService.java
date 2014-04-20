package nju.software.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.commons.net.ntp.TimeStamp;

import nju.software.dataobject.Accessory;
import nju.software.dataobject.Account;
import nju.software.dataobject.Fabric;
import nju.software.dataobject.Logistics;
import nju.software.model.OrderInfo;

public interface DesignService {
	
	public boolean verifyDesignSubmit(Account account, int orderId, long taskId, 
			boolean designVal, String comment);
	
	public boolean costAccounting(Account account, int orderId, long taskId, 
			long processId, float design_cost);
	
	 public List<Map<String,Object>> getComputeDesignCostList();
		
		public Map<String,Object> getComputeDesignCostDetail(Integer orderId);

		public void computeDesignCostSubmit(int orderId,long taskId,float design_cost);
	
		 public List<Map<String,Object>> getUploadDesignList();
			
			public Map<String,Object> getUploadDesignDetail(Integer orderId);

			public void uploadDesignSubmit(int orderId,long taskId, String url,Timestamp uploadTime);
		
			
			 public List<Map<String,Object>> getModifyDesignList();
				
				public Map<String,Object> getModifyDesignDetail(Integer orderId);

//				public void ModifyDesignSubmit(int orderId,long taskId, String url,Timestamp uploadTime);
			
						
		 public List<Map<String,Object>> getConfirmDesignList();
					
		public Map<String,Object> getConfirmDesignDetail(Integer orderId);
			
		
	public Logistics getLogisticsByOrderId(int orderId);
	
	public List<Fabric> getFabricByOrderId(int orderId);
	
	public List<Accessory> getAccessoryByOrderId(int orderId);
	
	 public boolean uploadCAD(Account account, int orderId, long taskId, 
				long processId, String url,Timestamp uploadTime);

	public List<Map<String, Object>> getVerifyDesignList();

	public Map<String,Object> getVerifyDesignDetail(int orderId);
	
}
