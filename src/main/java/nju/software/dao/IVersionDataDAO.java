package nju.software.dao;

import java.util.List;

import nju.software.dataobject.VersionData;

public interface IVersionDataDAO {

	public abstract void save(VersionData transientInstance);

	public abstract void delete(VersionData persistentInstance);

	public abstract VersionData findById(java.lang.Integer id);

	public abstract List<VersionData> findByExample(VersionData instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List<VersionData> findByOrderId(Object orderId);

	public abstract List<VersionData> findBySize(Object size);

	public abstract List<VersionData> findByCenterBackLength(
			Object centerBackLength);

	public abstract List<VersionData> findByBust(Object bust);

	public abstract List<VersionData> findByWaistline(Object waistline);

	public abstract List<VersionData> findByShoulder(Object shoulder);

	public abstract List<VersionData> findByButtock(Object buttock);

	public abstract List<VersionData> findByHem(Object hem);

	public abstract List<VersionData> findByTrousers(Object trousers);

	public abstract List<VersionData> findBySkirt(Object skirt);

	public abstract List<VersionData> findBySleeves(Object sleeves);

	public abstract List findAll();

	public abstract VersionData merge(VersionData detachedInstance);

	public abstract void attachDirty(VersionData instance);

	public abstract void attachClean(VersionData instance);

	void deleteByProperty(String propertyName, Object orderId);

}