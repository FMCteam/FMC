package nju.software.service;

import java.util.List;

import nju.software.dataobject.Account;
import nju.software.dataobject.Money;
import nju.software.dataobject.Product;
import nju.software.model.QuoteConfirmTaskSummary;


public interface MarketService {
	
	public List<QuoteConfirmTaskSummary> getQuoteConfirmTaskSummaryList(Integer employeeId);
	
	public void completeQuoteConfirmTaskSummary(long taskId,String result);
	
	public List<Product> getProduct(int orderId, String productAskAmount, String productColor, String productStyle);

	public boolean confirmProduct(Account account, int orderId, long taskId, 
			long processId, boolean comfirmworksheet, List<Product> productList);
	
}
