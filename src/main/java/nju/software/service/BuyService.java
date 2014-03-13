package nju.software.service;

import nju.software.dataobject.Account;

public interface BuyService {
	
	public boolean verify(Account account, int orderId, long taskId, long processId, boolean buyVal, String comment);

}
