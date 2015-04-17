package nju.software.dao;

import java.util.List;

import nju.software.dataobject.OrderSource;

public interface IOrderSourceDAO {
	
	public abstract void save(OrderSource instance);
	
	public abstract void delete(OrderSource instance);
	
	public abstract OrderSource findById(Integer id);
	
	public List findByProperty(String propertyName, Object value);
	
	public List<OrderSource> findByExample(OrderSource example);
	
	public List<OrderSource> findByOrderId(Integer orderId);
	
	public List<OrderSource> findBySource(String source);
	
	public List<OrderSource> findAll();
	
	public abstract OrderSource merge(OrderSource orderSource);
	
	public abstract void attachDirty(OrderSource orderSource);
	
	public abstract void attachClean(OrderSource orderSource);
		
}
