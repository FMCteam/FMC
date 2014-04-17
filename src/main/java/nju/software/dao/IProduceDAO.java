package nju.software.dao;

import java.util.List;
import nju.software.dataobject.Produce;



public interface IProduceDAO {

	public abstract void save(Produce transientInstance);

	public abstract void delete(Produce persistentInstance);

	public abstract Produce findById(java.lang.Integer id);
	
	public abstract List<Produce> findByExample(Produce instance);

	public abstract List findByProperty(String propertyName, Object value);
	
	public abstract List<Produce> findByOrderId(Object orderId);
	
	public abstract List findAll();

	public abstract Produce merge(Produce detachedInstance);

	public abstract void attachDirty(Produce instance);

	public abstract void attachClean(Produce instance);

	public void deleteProduceByProperty(String propertyName,Object orderId);
	
	public void deleteSampleProduceByProperty(String propertyName,Object orderId);
	
}
