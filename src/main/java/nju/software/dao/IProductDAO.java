package nju.software.dao;

import java.util.List;

import nju.software.dataobject.Product;

public interface IProductDAO {

	public abstract void save(Product transientInstance);

	public abstract void delete(Product persistentInstance);

	public abstract Product findById(java.lang.Integer id);

	public abstract List<Product> findByExample(Product instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List<Product> findByOrderId(Object orderId);

	public abstract List<Product> findByStyle(Object style);

	public abstract List<Product> findByColor(Object color);

	public abstract List<Product> findByAskAmount(Object askAmount);

	public abstract List<Product> findByProduceAmount(Object produceAmount);

	public abstract List<Product> findByQualifiedAmount(Object qualifiedAmount);

	public abstract List findAll();

	public abstract Product merge(Product detachedInstance);

	public abstract void attachDirty(Product instance);

	public abstract void attachClean(Product instance);

}