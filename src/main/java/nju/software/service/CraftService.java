package nju.software.service;

import org.springframework.transaction.annotation.Transactional;

import nju.software.dataobject.Craft;

public interface CraftService {
	
	@Transactional(rollbackFor = Exception.class)
	public Craft findCraftByOrderId(String  orderId );

}
