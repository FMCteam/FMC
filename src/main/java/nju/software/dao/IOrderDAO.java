package nju.software.dao;

import java.util.List;
import java.util.Map;

import nju.software.dataobject.Order;

public interface IOrderDAO {

	public abstract void save(Order transientInstance);

	public abstract void delete(Order persistentInstance);

	public abstract Order findById(java.lang.Integer id);

	public abstract List<Order> findByExample(Order instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List<Order> findByCustomerId(Object customerId);

	public abstract List<Order> findByEmployeeId(Object employeeId);

	public abstract List<Order> findByOrderState(Object orderState);

	public abstract List<Order> findByCustomerName(Object customerName);

	public abstract List<Order> findByCustomerCompany(Object customerCompany);

	public abstract List<Order> findByCustomerCompanyFax(
			Object customerCompanyFax);

	public abstract List<Order> findByCustomerPhone1(Object customerPhone1);

	public abstract List<Order> findByCustomerPhone2(Object customerPhone2);

	public abstract List<Order> findByCustomerCompanyAddress(
			Object customerCompanyAddress);

	public abstract List<Order> findByStyleName(Object styleName);

	public abstract List<Order> findByFabricType(Object fabricType);

	public abstract List<Order> findByStyleSex(Object styleSex);

	public abstract List<Order> findByStyleSeason(Object styleSeason);

	public abstract List<Order> findBySpecialProcess(Object specialProcess);

	public abstract List<Order> findByOtherRequirements(Object otherRequirements);

	public abstract List<Order> findBySampleClothesPicture(
			Object sampleClothesPicture);

	public abstract List<Order> findByReferencePicture(Object referencePicture);

	public abstract List<Order> findByAskAmount(Object askAmount);

	public abstract List<Order> findByAskProducePeriod(Object askProducePeriod);

	public abstract List<Order> findByAskCodeNumber(Object askCodeNumber);

	public abstract List<Order> findByHasPostedSampleClothes(
			Object hasPostedSampleClothes);

	public abstract List<Order> findByIsNeedSampleClothes(
			Object isNeedSampleClothes);

	public abstract List<Order> findByOrderSource(Object orderSource);

	public abstract List findAll();

	public abstract Order merge(Order detachedInstance);

	public abstract void attachDirty(Order instance);

	public abstract void attachClean(Order instance);
	
	public List<Order> findSampleOrderAndPage(final int off_set,final int length);
	
	public int coutSampleOrder();
	
	public List getOrderList(Integer page);
	
	public List<Order> getSearchOrderList(String ordernumber,
			String customername, String stylename, String startdate,String enddate,
			Integer[] employeeIds,String userRole,Integer userId);

	
	public Integer getPageNumber();
		
	 public List<Order>findResultsByCustomerId(int customerId);
	

}