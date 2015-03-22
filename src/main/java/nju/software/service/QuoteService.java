package nju.software.service;

import org.springframework.transaction.annotation.Transactional;

import nju.software.dataobject.Quote;

public interface QuoteService {

	@Transactional(rollbackFor = Exception.class)
	public boolean updateQuote(float innerPrice, float outPrice, int id,
			String taskId, String processId, String actor);

	@Transactional(rollbackFor = Exception.class)
	public Quote findByOrderId(String orderId);

}
