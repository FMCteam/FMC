package nju.software.dao;

import java.util.List;

import nju.software.dataobject.Description;

public interface IDescriptionDAO {

	public abstract void save(Description transientInstance);

	public abstract void delete(Description persistentInstance);

	public abstract Description findById(java.lang.Integer id);

	public abstract List<Description> findByExample(Description instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List<Description> findBySampleClothesCustomerDescription(
			Object sampleClothesCustomerDescription);

	public abstract List<Description> findBySampleClothesDesignDescription(
			Object sampleClothesDesignDescription);

	public abstract List<Description> findByProductClothesCustomerDescription(
			Object productClothesCustomerDescription);

	public abstract List<Description> findByProductClothesDesignDescription(
			Object productClothesDesignDescription);

	public abstract List findAll();

	public abstract Description merge(Description detachedInstance);

	public abstract void attachDirty(Description instance);

	public abstract void attachClean(Description instance);

}