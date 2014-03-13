package nju.software.service;

import nju.software.dataobject.Account;

public interface BuyService {
	
	public boolean verify(Account account, int orderId, String taskName, boolean buyVal);

}
