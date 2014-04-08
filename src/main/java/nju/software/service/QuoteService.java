package nju.software.service;


import nju.software.dataobject.Quote;

public interface QuoteService {

	public boolean updateQuote(float innerPrice,float outPrice,int id,long taskId,long processId,String actor);

	public Quote findByOrderId(String orderId);
	
}
