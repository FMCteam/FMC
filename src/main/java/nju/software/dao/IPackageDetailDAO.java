package nju.software.dao;

import java.util.List;

import nju.software.dataobject.PackageDetail;
import nju.software.dataobject.Package;

public interface IPackageDetailDAO {

	public abstract void save(PackageDetail transientInstance);

	public abstract void delete(PackageDetail persistentInstance);

	public abstract PackageDetail findById(java.lang.Integer id);

	public abstract List<PackageDetail> findByExample(PackageDetail instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List<PackageDetail> findByPackageId(Integer packageId);

	public abstract List<PackageDetail> findByClothesStyleName(
			Object clothesStyleName);

	public abstract List<PackageDetail> findByClothesStyleColor(
			Object clothesStyleColor);

	public abstract List<PackageDetail> findByClothesAmount(Object clothesAmount);

	public abstract List findAll();

	public abstract PackageDetail merge(PackageDetail detachedInstance);

	public abstract void attachDirty(PackageDetail instance);

	public abstract void attachClean(PackageDetail instance);
	
	public abstract List<List<PackageDetail>> findByPackageList(List<Package> packageList);
	
}