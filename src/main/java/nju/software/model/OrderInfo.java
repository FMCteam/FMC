package nju.software.model;

import java.util.List;

import org.jbpm.task.query.TaskSummary;

import nju.software.dataobject.Accessory;
import nju.software.dataobject.AccessoryCost;
import nju.software.dataobject.Customer;
import nju.software.dataobject.DesignCad;
import nju.software.dataobject.Employee;
import nju.software.dataobject.Fabric;
import nju.software.dataobject.FabricCost;
import nju.software.dataobject.Logistics;
import nju.software.dataobject.Order;
import nju.software.dataobject.PackageDetail;
import nju.software.dataobject.Produce;
import nju.software.dataobject.Product;
import nju.software.dataobject.Quote;
import nju.software.dataobject.Package;
import nju.software.dataobject.VersionData;

public class OrderInfo {
	
	private Order order=null;
	private Customer customer=null;
	private Employee employee=null;
	private List<Fabric> fabrics=null;
	private List<Accessory> accessorys=null;
	private List<VersionData> versions=null;
	private Logistics logistics=null;
	private List<FabricCost>fabricCosts=null;
	private List<AccessoryCost>sccessoryCosts=null;
	private Quote quote=null;
	private DesignCad cad=null;
	private List<Product>products=null;
	private List<Package>packages=null;
	private List<List<PackageDetail>>packageDetails=null;
	private TaskSummary task;
	private long taskId;
	private List<Produce> produces;
	
	

	public List<Produce> getProduces() {
		return produces;
	}

	public void setProduces(List<Produce> produces) {
		this.produces = produces;
	}

	public List<Fabric> getFabrics() {
		return fabrics;
	}

	public void setFabrics(List<Fabric> fabrics) {
		this.fabrics = fabrics;
	}

	public List<Accessory> getAccessorys() {
		return accessorys;
	}

	public void setAccessorys(List<Accessory> accessorys) {
		this.accessorys = accessorys;
	}

	public Logistics getLogistics() {
		return logistics;
	}

	public void setLogistics(Logistics logistics) {
		this.logistics = logistics;
	}

	public List<FabricCost> getFabricCosts() {
		return fabricCosts;
	}

	public void setFabricCosts(List<FabricCost> fabricCosts) {
		this.fabricCosts = fabricCosts;
	}

	public List<AccessoryCost> getSccessoryCosts() {
		return sccessoryCosts;
	}

	public void setSccessoryCosts(List<AccessoryCost> sccessoryCosts) {
		this.sccessoryCosts = sccessoryCosts;
	}

	public Quote getQuote() {
		return quote;
	}

	public void setQuote(Quote quote) {
		this.quote = quote;
	}

	public DesignCad getCad() {
		return cad;
	}

	public void setCad(DesignCad cad) {
		this.cad = cad;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public List<Package> getPackages() {
		return packages;
	}

	public void setPackages(List<Package> packages) {
		this.packages = packages;
	}

	public List<List<PackageDetail>> getPackageDetails() {
		return packageDetails;
	}

	public void setPackageDetails(List<List<PackageDetail>> packageDetails) {
		this.packageDetails = packageDetails;
	}

	public TaskSummary getTask() {
		return task;
	}

	public void setTask(TaskSummary task) {
		this.task = task;
	}
	
	
	public OrderInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderInfo(Order order, long taskId) {
		super();
		this.order = order;
		this.taskId = taskId;
	}
	
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public long getTaskId() {
		return taskId;
	}
	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public List<VersionData> getVersions() {
		return versions;
	}

	public void setVersions(List<VersionData> versions) {
		this.versions = versions;
	}
}
