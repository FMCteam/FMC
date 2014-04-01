package nju.software.service;

import nju.software.dataobject.Logistics;

public interface LogisticsService {

	public Logistics findByOrderId(String orderId);

	public boolean addLogistics(Logistics log);

	public boolean sendSample(long taskId);
}
