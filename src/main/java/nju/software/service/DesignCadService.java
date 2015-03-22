package nju.software.service;

import org.springframework.transaction.annotation.Transactional;

import nju.software.dataobject.DesignCad;

public interface DesignCadService {

	@Transactional(rollbackFor = Exception.class)
	public DesignCad findByOrderId(String orderId);
}
