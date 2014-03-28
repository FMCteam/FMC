package nju.software.service;

import java.util.List;
import nju.software.model.QuoteConfirmTaskSummary;


public interface MarketService {
	
	public List<QuoteConfirmTaskSummary> getQuoteConfirmTaskSummaryList(Integer employeeId);
	
	public void completeQuoteConfirmTaskSummary(long taskId,String result);

	
}
