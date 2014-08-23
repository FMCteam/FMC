/**
 * 
 */
package nju.software.dao;

import java.util.List;

import nju.software.dataobject.CheckDetail;

/**
 * @author luxiangfan
 *
 */
public interface ICheckDetailDAO {
	public abstract void save(CheckDetail transientInstance);

	public abstract void delete(CheckDetail persistentInstance);

	public abstract CheckDetail findById(java.lang.Integer id);

	public abstract List<CheckDetail> findByExample(CheckDetail instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List<CheckDetail> findByRecordId(Object recordId);
	
	public abstract List findAll();

	public abstract CheckDetail merge(CheckDetail detachedInstance);

	public abstract void attachDirty(CheckDetail instance);

	public abstract void attachClean(CheckDetail instance);
}
