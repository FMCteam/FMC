/**
 * 
 */
package nju.software.dao;

import java.util.List;

import nju.software.dataobject.DeliveryRecord;

/**
 * @author luxiangfan
 *
 */
public interface IDeliveryRecordDAO {
	public abstract void save(DeliveryRecord transientInstance);

	public abstract void delete(DeliveryRecord persistentInstance);

	public abstract DeliveryRecord findById(java.lang.Integer id);

	public abstract List<DeliveryRecord> findByExample(DeliveryRecord instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List<DeliveryRecord> findByOrderId(Object orderId);
	
	public abstract List findAll();

	public abstract DeliveryRecord merge(DeliveryRecord detachedInstance);

	public abstract void attachDirty(DeliveryRecord instance);

	public abstract void attachClean(DeliveryRecord instance);
}
