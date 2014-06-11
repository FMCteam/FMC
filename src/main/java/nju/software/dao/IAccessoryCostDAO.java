package nju.software.dao;

import java.util.List;

import nju.software.dataobject.AccessoryCost;

public interface IAccessoryCostDAO {

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IAccessoryCostDAO#save(nju.software.dataobject.AccessoryCost)
	 */
	public abstract void save(AccessoryCost transientInstance);

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IAccessoryCostDAO#delete(nju.software.dataobject.AccessoryCost)
	 */
	public abstract void delete(AccessoryCost persistentInstance);

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IAccessoryCostDAO#findById(java.lang.Integer)
	 */
	public abstract AccessoryCost findById(java.lang.Integer id);

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IAccessoryCostDAO#findByExample(nju.software.dataobject.AccessoryCost)
	 */
	public abstract List<AccessoryCost> findByExample(AccessoryCost instance);

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IAccessoryCostDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
	public abstract List findByProperty(String propertyName, Object value);

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IAccessoryCostDAO#findByOrderId(java.lang.Object)
	 */
	public abstract List<AccessoryCost> findByOrderId(Object orderId);

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IAccessoryCostDAO#findByAccessoryName(java.lang.Object)
	 */
	public abstract List<AccessoryCost> findByAccessoryName(Object accessoryName);

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IAccessoryCostDAO#findByTearPerPiece(java.lang.Object)
	 */
	public abstract List<AccessoryCost> findByTearPerPiece(Object tearPerPiece);

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IAccessoryCostDAO#findByPrice(java.lang.Object)
	 */
	public abstract List<AccessoryCost> findByPrice(Object price);

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IAccessoryCostDAO#findByCostPerPiece(java.lang.Object)
	 */
	public abstract List<AccessoryCost> findByCostPerPiece(Object costPerPiece);

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IAccessoryCostDAO#findAll()
	 */
	public abstract List findAll();

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IAccessoryCostDAO#merge(nju.software.dataobject.AccessoryCost)
	 */
	public abstract AccessoryCost merge(AccessoryCost detachedInstance);

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IAccessoryCostDAO#attachDirty(nju.software.dataobject.AccessoryCost)
	 */
	public abstract void attachDirty(AccessoryCost instance);

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IAccessoryCostDAO#attachClean(nju.software.dataobject.AccessoryCost)
	 */
	public abstract void attachClean(AccessoryCost instance);
	public void deleteByProperty(String propertyName,Object orderId);

}