package nju.software.dao;

import java.util.List;

import nju.software.dataobject.Package;

public interface IPackageDAO {

	public abstract void save(Package transientInstance);

	public abstract void delete(Package persistentInstance);

	public abstract Package findById(java.lang.Integer id);

	public abstract List<Package> findByExample(Package instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List<Package> findByOrderId(Object orderId);

	public abstract List findAll();

	public abstract Package merge(Package detachedInstance);

	public abstract void attachDirty(Package instance);

	public abstract void attachClean(Package instance);

}