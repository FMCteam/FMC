package nju.software.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nju.software.dao.impl.CraftDAO;
import nju.software.dataobject.Craft;
import nju.software.service.CraftService;

@Service("craftServiceImpl")
public class CraftServiceImpl implements CraftService {
	
	@Autowired
	private CraftDAO craftDAO;

	@Override
	public Craft findCraftByOrderId(String orderId) {
 		List<Craft> list = craftDAO.findByOrderId(Integer.parseInt(orderId));
		if(list.size()>0)
			return list.get(0);
		else
			return null;
	}

}
