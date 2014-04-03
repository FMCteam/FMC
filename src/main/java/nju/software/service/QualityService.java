package nju.software.service;

import java.util.List;

import nju.software.model.OrderModel;

public interface QualityService {
	
	public List<OrderModel> getCheckList();

	public boolean checkQuality(int id, long taskId, long processId, boolean b);
}
