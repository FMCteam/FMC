package nju.software.dao;

import java.util.List;

import nju.software.dataobject.MarketstaffAlter;

public interface IMarketstaffAlterDAO {

	public abstract void save(MarketstaffAlter transientInstance);
	
	public abstract void delete(MarketstaffAlter persistentInstance);
	
	public abstract void attachDirty(MarketstaffAlter instance);
	
	public abstract void attachClean(MarketstaffAlter instance);
	
	public abstract MarketstaffAlter findById(Integer alterId);
	
	public abstract List<MarketstaffAlter> findByExample(MarketstaffAlter example);
	
	public abstract List findByProperty(String propertyName, Object value);
	
	public abstract List<MarketstaffAlter> findVerifyState(String verfiyState);
	
	public abstract List<MarketstaffAlter> findByApplyerId(Integer applyId);
}
