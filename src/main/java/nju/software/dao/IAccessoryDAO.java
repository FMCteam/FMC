package nju.software.dao;

import java.util.List;

import nju.software.dataobject.Accessory;

public interface IAccessoryDAO {

	public abstract void save(Accessory transientInstance);

	public abstract void delete(Accessory persistentInstance);

	public abstract Accessory findById(java.lang.Integer id);

	public abstract List<Accessory> findByExample(Accessory instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List<Accessory> findByOrderId(Object orderId);

	public abstract List<Accessory> findByAccessoryName(Object accessoryName);

	public abstract List<Accessory> findByAccessoryQuery(Object accessoryQuery);

	public abstract List findAll();

	public abstract Accessory merge(Accessory detachedInstance);

	public abstract void attachDirty(Accessory instance);

	public abstract void attachClean(Accessory instance);

}