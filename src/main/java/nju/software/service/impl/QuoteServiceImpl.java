package nju.software.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drools.runtime.StatefulKnowledgeSession;
import org.jbpm.task.query.TaskSummary;
import org.jbpm.workflow.instance.WorkflowProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nju.software.dao.impl.AccessoryDAO;
import nju.software.dao.impl.FabricDAO;
import nju.software.dao.impl.LogisticsDAO;
import nju.software.dao.impl.OrderDAO;
import nju.software.dao.impl.QuoteDAO;
import nju.software.dataobject.Accessory;
import nju.software.dataobject.Account;
import nju.software.dataobject.Fabric;
import nju.software.dataobject.Logistics;
import nju.software.dataobject.Order;
import nju.software.dataobject.Quote;
import nju.software.model.OrderModel;
import nju.software.model.QuoteModel;
import nju.software.service.ProduceService;
import nju.software.service.QuoteService;
import nju.software.util.JbpmAPIUtil;

@Service("quoteServiceImpl")
public class QuoteServiceImpl implements QuoteService {

	@Autowired
	private QuoteDAO quoteDAO;
	@Autowired
	private JbpmAPIUtil jbpmAPIUtil;
	
	@Override
	public Quote findByOrderId(String orderId) {
		// TODO Auto-generated method stub
		List<Quote> quote = quoteDAO.findByProperty("orderId", Integer.parseInt(orderId));
		if(quote.size()!=0){
			return quote.get(0);
		}else
			return null;
	}

	@Override
	public boolean updateQuote(float innerPrice, float outPrice, int id,
			long taskId, long processId,String actor) {
		// TODO Auto-generated method stub
		try {
			// 保存quote
			Quote q = quoteDAO.findById(id);
			q.setInnerPrice(innerPrice);
			q.setOuterPrice(outPrice);
			quoteDAO.merge(q);
			System.out.println("update quote successfully");
			// 推进流程的发展
			
			// 需要获取task中的数据
			WorkflowProcessInstance process = (WorkflowProcessInstance) jbpmAPIUtil
					.getKsession().getProcessInstance(processId);
			int orderId_process = (int) process.getVariable("orderId");
			System.out.println("orderId: " + id);
			if (id == orderId_process) {

				// 修改流程参数
				Map<String, Object> data = new HashMap<>();

				// 直接进入到下一个流程时

				jbpmAPIUtil.completeTask(taskId, null, actor);

			}
			return true;
		} catch (Exception e) {

		}
		return false;
	}

}
