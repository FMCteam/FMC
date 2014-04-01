package nju.software.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nju.software.dao.impl.AccountDAO;
import nju.software.dao.impl.LogisticsDAO;
import nju.software.dataobject.Logistics;
import nju.software.service.LogisticsService;

@Service("logisticsServiceImpl")
public class LogisticsServiceImpl implements LogisticsService{

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
	public boolean sendSample(long taskId){
		
		return false;
	}

}
