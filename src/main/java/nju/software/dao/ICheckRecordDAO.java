package nju.software.dao;

import java.util.List;
import nju.software.dataobject.CheckRecord;

/**
 * @author luxiangfan
 */
public interface ICheckRecordDAO {
	public abstract void save(CheckRecord transientInstance);

	public abstract void delete(CheckRecord persistentInstance);

	public abstract CheckRecord findById(java.lang.Integer id);

	public abstract List<CheckRecord> findByExample(CheckRecord instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List<CheckRecord> findByOrderId(Object orderId);
	
	public abstract List findAll();

	public abstract CheckRecord merge(CheckRecord detachedInstance);

	public abstract void attachDirty(CheckRecord instance);

	public abstract void attachClean(CheckRecord instance);
}
