package nju.software.service;

import nju.software.dataobject.DesignCad;

public interface DesignCadService {

	public DesignCad findByOrderId(String orderId);
}
