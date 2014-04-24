package nju.software.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nju.software.dao.impl.DesignCadDAO;
import nju.software.dataobject.DesignCad;
import nju.software.service.DesignCadService;

@Service("designCadServiceImpl")
public class DesignCadServiceImpl implements DesignCadService{

	@Autowired
	private DesignCadDAO cadDAO;
	@Override
	public DesignCad findByOrderId(String orderId) {
		// TODO Auto-generated method stub
		List<DesignCad> list = cadDAO.findByOrderId(orderId);
		if(list.size()>0)
			return list.get(0);
		else
			return null;
	}

}
