package nju.software.dao;

import java.util.List;

import nju.software.dataobject.Logistics;

public interface ILogisticsDAO {

	public abstract void save(Logistics transientInstance);

	public abstract void delete(Logistics persistentInstance);

	public abstract Logistics findById(java.lang.Integer id);

	public abstract List<Logistics> findByExample(Logistics instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List<Logistics> findByInPostSampleClothesType(
			Object inPostSampleClothesType);

	public abstract List<Logistics> findByInPostSampleClothesNumber(
			Object inPostSampleClothesNumber);

	public abstract List<Logistics> findBySampleClothesType(
			Object sampleClothesType);

	public abstract List<Logistics> findBySampleClothesAddress(
			Object sampleClothesAddress);

	public abstract List<Logistics> findBySampleClothesName(
			Object sampleClothesName);

	public abstract List<Logistics> findBySampleClothesPhone(
			Object sampleClothesPhone);

	public abstract List<Logistics> findBySampleClothesRemark(
			Object sampleClothesRemark);

	public abstract List<Logistics> findByProductClothesType(
			Object productClothesType);

	public abstract List<Logistics> findByProductClothesAddress(
			Object productClothesAddress);

	public abstract List<Logistics> findByProductClothesPrice(
			Object productClothesPrice);

	public abstract List<Logistics> findByProductClothesNumber(
			Object productClothesNumber);

	public abstract List<Logistics> findByProductClothesName(
			Object productClothesName);

	public abstract List<Logistics> findByProductClothesPhone(
			Object productClothesPhone);

	public abstract List<Logistics> findByProductClothesRemark(
			Object productClothesRemark);

	public abstract List findAll();

	public abstract Logistics merge(Logistics detachedInstance);

	public abstract void attachDirty(Logistics instance);

	public abstract void attachClean(Logistics instance);

}