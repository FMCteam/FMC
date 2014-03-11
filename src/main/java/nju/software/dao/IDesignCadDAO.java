package nju.software.dao;

import java.util.List;

import nju.software.dataobject.DesignCad;

public interface IDesignCadDAO {

	public abstract void save(DesignCad transientInstance);

	public abstract void delete(DesignCad persistentInstance);

	public abstract DesignCad findById(java.lang.Integer id);

	public abstract List<DesignCad> findByExample(DesignCad instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List<DesignCad> findByOrderId(Object orderId);

	public abstract List<DesignCad> findByCadUrl(Object cadUrl);

	public abstract List<DesignCad> findByCadVersion(Object cadVersion);

	public abstract List findAll();

	public abstract DesignCad merge(DesignCad detachedInstance);

	public abstract void attachDirty(DesignCad instance);

	public abstract void attachClean(DesignCad instance);

}