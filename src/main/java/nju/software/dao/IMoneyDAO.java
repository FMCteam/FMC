package nju.software.dao;

import java.util.List;

import nju.software.dataobject.Money;

public interface IMoneyDAO {

	public abstract void save(Money transientInstance);

	public abstract void delete(Money persistentInstance);

	public abstract Money findById(java.lang.Integer id);

	public abstract List<Money> findByExample(Money instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List<Money> findByOrderId(Object orderId);

	public abstract List<Money> findByMoneyType(Object moneyType);

	public abstract List<Money> findByMoneyAmount(Object moneyAmount);

	public abstract List<Money> findByMoneyState(Object moneyState);

	public abstract List<Money> findByMoneyName(Object moneyName);

	public abstract List<Money> findByMoneyBank(Object moneyBank);

	public abstract List<Money> findByMoneyNumber(Object moneyNumber);

	public abstract List<Money> findByMoneyRemark(Object moneyRemark);

	public abstract List findAll();

	public abstract Money merge(Money detachedInstance);

	public abstract void attachDirty(Money instance);

	public abstract void attachClean(Money instance);

}