package nju.software.service;

import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.net.ntp.TimeStamp;

import nju.software.dataobject.Accessory;
import nju.software.dataobject.Account;
import nju.software.dataobject.Fabric;
import nju.software.dataobject.Logistics;
import nju.software.model.OrderInfo;

public interface DesignService {
	
	public boolean verifyDesignSubmit(Account account, int orderId, long taskId, 
			long processId, boolean designVal, String comment);
	
	public boolean costAccounting(Account account, int orderId, long taskId, 
			long processId, float design_cost);
	
	 public List<OrderInfo> getComputeDesignCostList();
		
		public OrderInfo getComputeDesignCostDetail(Integer orderId);

		public void computeDesignCostSubmit(int orderId,long taskId,float design_cost);
	
		 public List<OrderInfo> getUploadDesignList();
			
			public OrderInfo getUploadDesignDetail(Integer orderId);

			public void uploadDesignSubmit(int orderId,long taskId, String url,Timestamp uploadTime);
		
			
			 public List<OrderInfo> getModifyDesignList();
				
				public OrderInfo getModifyDesignDetail(Integer orderId);

//				public void ModifyDesignSubmit(int orderId,long taskId, String url,Timestamp uploadTime);
			
						
		 public List<OrderInfo> getConfirmDesignList();
					
		public OrderInfo getConfirmDesignDetail(Integer orderId);
			
		
	public Logistics getLogisticsByOrderId(int orderId);
	
	public List<Fabric> getFabricByOrderId(int orderId);
	
	public List<Accessory> getAccessoryByOrderId(int orderId);
	
	 public boolean uploadCAD(Account account, int orderId, long taskId, 
				long processId, String url,Timestamp uploadTime);

	public List<OrderInfo> getVerifyDesignList();

	public OrderInfo getVerifyDesignDetail(int orderId, long taskId);
	
}
