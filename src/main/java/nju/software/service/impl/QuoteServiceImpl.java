package nju.software.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nju.software.dao.impl.QuoteDAO;
import nju.software.dataobject.Quote;
import nju.software.process.service.FMCProcessService;
import nju.software.service.QuoteService;

@Service("quoteServiceImpl")
public class QuoteServiceImpl implements QuoteService {

	@Autowired
	private QuoteDAO quoteDAO;
	@Autowired
	private FMCProcessService mainProcessService;
	
	@Override
	public Quote findByOrderId(String orderId) {
		List<Quote> quote = quoteDAO.findByProperty("orderId", Integer.parseInt(orderId));
		if(quote.size()!=0){
			return quote.get(0);
		}else
			return null;
	}

	@Override
	public boolean updateQuote(float innerPrice, float outPrice, int id,
			String taskId, String processId,String actor) {
		try {
			// 保存quote
			Quote q = quoteDAO.findById(id);
			q.setInnerPrice(innerPrice);
			q.setOuterPrice(outPrice);
			quoteDAO.merge(q);
			System.out.println("update quote successfully");
			// 推进流程的发展
			
			// 需要获取task中的数据
			
			int orderId_process = mainProcessService.getOrderIdInProcess(processId);
			System.out.println("orderId: " + id);
			if (id == orderId_process) {

				// 修改流程参数
				Map<String, Object> data = new HashMap<>();

				// 直接进入到下一个流程时

				mainProcessService.completeTask(taskId, actor, data);

			}
			return true;
		} catch (Exception e) {

		}
		return false;
	}

}
