package nju.software.dao;

import java.util.List;

import nju.software.dataobject.Quote;

public interface IQuoteDAO {

	public abstract void save(Quote transientInstance);

	public abstract void delete(Quote persistentInstance);

	public abstract Quote findById(java.lang.Integer id);

	public abstract List<Quote> findByExample(Quote instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List<Quote> findByDesignCost(Object designCost);

	public abstract List<Quote> findByCutCost(Object cutCost);

	public abstract List<Quote> findByManageCost(Object manageCost);

	public abstract List<Quote> findBySwingCost(Object swingCost);

	public abstract List<Quote> findByIroningCost(Object ironingCost);

	public abstract List<Quote> findByNailCost(Object nailCost);

	public abstract List<Quote> findByPackageCost(Object packageCost);

	public abstract List<Quote> findByOtherCost(Object otherCost);

	public abstract List<Quote> findByProfitPerPiece(Object profitPerPiece);

	public abstract List<Quote> findByInnerPrice(Object innerPrice);

	public abstract List<Quote> findByOuterPrice(Object outerPrice);

	public abstract List findAll();

	public abstract Quote merge(Quote detachedInstance);

	public abstract void attachDirty(Quote instance);

	public abstract void attachClean(Quote instance);

}