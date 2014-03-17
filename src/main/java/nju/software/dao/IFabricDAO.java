package nju.software.dao;

import java.util.List;

import nju.software.dataobject.Fabric;

public interface IFabricDAO {

	public abstract void save(Fabric transientInstance);

	public abstract void delete(Fabric persistentInstance);

	public abstract Fabric findById(java.lang.Integer id);

	public abstract List<Fabric> findByExample(Fabric instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List<Fabric> findByOrderId(Object orderId);

	public abstract List<Fabric> findByFabricName(Object fabricName);

	public abstract List<Fabric> findByFabricAmount(Object fabricAmount);

	public abstract List findAll();

	public abstract Fabric merge(Fabric detachedInstance);

	public abstract void attachDirty(Fabric instance);

	public abstract void attachClean(Fabric instance);

	public void deleteByProperty(String propertyName,Object orderId);
}