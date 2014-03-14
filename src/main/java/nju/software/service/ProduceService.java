package nju.software.service;

import nju.software.dataobject.Account;

public interface ProduceService {

	public boolean verify(Account account, int orderId,int taskId,long processId, boolean productVal,String comment);
}
