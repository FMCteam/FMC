package nju.software.dao;

import java.util.List;

import nju.software.dataobject.OperateRecord;

/*
 * author:weiheng
 */
public interface IOperateRecordDAO {
	public abstract void save(OperateRecord transientInstance);

	public abstract void delete(OperateRecord persistentInstance);

	public abstract OperateRecord findById(java.lang.Integer id);

	public abstract List<OperateRecord> findByExample(OperateRecord instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List<OperateRecord> findByOrderId(Object orderId);

	public abstract List<OperateRecord> findByTaskName(Object taskName);

	public abstract List<OperateRecord> findByOperatePerson(Object operatePerson);

	public abstract List<OperateRecord> findByOperateRemark(Object operateRemark);
	
	public abstract List findAll();

	public abstract OperateRecord merge(OperateRecord detachedInstance);

	public abstract void attachDirty(OperateRecord instance);

	public abstract void attachClean(OperateRecord instance);
	
	

}
