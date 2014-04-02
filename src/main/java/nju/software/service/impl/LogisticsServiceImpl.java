package nju.software.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nju.software.dao.impl.AccountDAO;
import nju.software.dao.impl.LogisticsDAO;
import nju.software.dataobject.Logistics;
import nju.software.service.LogisticsService;
import nju.software.util.JbpmAPIUtil;

@Service("logisticsServiceImpl")
public class LogisticsServiceImpl implements LogisticsService{

	@Autowired
	private JbpmAPIUtil jbpmAPIUtil;
	@Autowired
	private LogisticsDAO logisticsDAO;
	@Override
	public Logistics findByOrderId(String orderId) {
		// TODO Auto-generated method stub
		try{
		List<Logistics> list= logisticsDAO.findByProperty("orderId",Integer.parseInt(orderId));
		if(list!=null&&list.size()>=1)
		{
			return list.get(0);
		}
		}catch(Exception e)
		{
			
		}
		return null;
	}
	@Override
	public boolean addLogistics(Logistics log) {
		// TODO Auto-generated method stub
		try{
		this.logisticsDAO.save(log);
		return true;
		}catch(Exception e)
		{
			
		}
		return false;
	}
	
	@Override
	public boolean sendSample(long taskId, String actor, long processId){
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			jbpmAPIUtil.completeTask(taskId, data, "WULIUZHUGUAN");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

}
