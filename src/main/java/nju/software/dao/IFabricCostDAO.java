package nju.software.dao;

import java.util.List;

import nju.software.dataobject.FabricCost;

public interface IFabricCostDAO {

	public abstract void save(FabricCost transientInstance);

	public abstract void delete(FabricCost persistentInstance);

	public abstract FabricCost findById(java.lang.Integer id);

	public abstract List<FabricCost> findByExample(FabricCost instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List<FabricCost> findByOrderId(Object orderId);

	public abstract List<FabricCost> findByFabricName(Object fabricName);

	public abstract List<FabricCost> findByTearPerMeter(Object tearPerMeter);

	public abstract List<FabricCost> findByPrice(Object price);

	public abstract List<FabricCost> findByCostPerMeter(Object costPerMeter);

	public abstract List findAll();

	public abstract FabricCost merge(FabricCost detachedInstance);

	public abstract void attachDirty(FabricCost instance);

	public abstract void attachClean(FabricCost instance);
	public void deleteByProperty(String propertyName,Object orderId);

}