package nju.software.dao;

import java.util.List;

import nju.software.dataobject.SampleClothes;

public interface ISampleClothesDAO {

	public abstract void save(SampleClothes transientInstance);

	public abstract void delete(SampleClothes persistentInstance);

	public abstract SampleClothes findById(java.lang.Integer id);

	public abstract List<SampleClothes> findByExample(SampleClothes instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List<SampleClothes> findByOrderId(Object orderId);

	public abstract List<SampleClothes> findByColor(Object color);

	public abstract List<SampleClothes> findByStyle(Object style);

	public abstract List<SampleClothes> findByAmount(Object amount);

	public abstract List<SampleClothes> findByPrice(Object price);

	public abstract List findAll();

	public abstract SampleClothes merge(SampleClothes detachedInstance);

	public abstract void attachDirty(SampleClothes instance);

	public abstract void attachClean(SampleClothes instance);

}