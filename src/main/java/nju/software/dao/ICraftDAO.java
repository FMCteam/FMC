package nju.software.dao;

import java.util.List;

import nju.software.dataobject.Craft;
 
public interface ICraftDAO {
	public abstract void save(Craft transientInstance);

	public abstract void delete(Craft persistentInstance);

	public abstract Craft findById(java.lang.Integer id);

	public abstract List<Craft> findByExample(Craft instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List<Craft> findByOrderId(Object orderId);
	public abstract List<Craft> findByNeedCraft(Object needCraft) ;

	public abstract List findAll();

	public abstract Craft merge(Craft detachedInstance);

	public abstract void attachDirty(Craft instance);

	public abstract void attachClean(Craft instance);
}
