package nju.software.dataobject;

public class SearchInfo {

	private String ordernumber;
	private String customername; 
	private String stylename;
	private String employeename;
	private String startdate;
	private String enddate;
	
	
	
	public SearchInfo() {
		
	}
	
	public SearchInfo(String ordernumber, String customername,
			String stylename, String employeename, String startdate,
			String enddate) {
		super();
		this.ordernumber = ordernumber;
		this.customername = customername;
		this.stylename = stylename;
		this.employeename = employeename;
		this.startdate = startdate;
		this.enddate = enddate;
	}
	
	public String getOrdernumber() {
		return ordernumber;
	}          
	public void setOrdernumber(String ordernumber) {
		this.ordernumber = ordernumber;
	}
	public String getCustomername() {
		return customername;
	}
	public void setCustomername(String customername) {
		this.customername = customername;
	}
	public String getStylename() {
		return stylename;
	}
	public void setStylename(String stylename) {
		this.stylename = stylename;
	}
	public String getEmployeename() {
		return employeename;
	}
	public void setEmployeename(String employeename) {
		this.employeename = employeename;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	
	

}
