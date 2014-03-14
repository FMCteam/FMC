package nju.software.service;

import nju.software.dataobject.Account;

public interface DesignService {
	
	public boolean verify(Account account, int orderId,int taskId,long processId,
			boolean designVal,String comment);

}
