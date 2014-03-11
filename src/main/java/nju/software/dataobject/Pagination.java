package nju.software.dataobject;

public class Pagination {
	private int page;
	private int pageSize=10;
	private int prePage;
	private int nextPage;
	private int totoalPage;
	private int count;
	public Pagination(){}
	public Pagination(int page, int pageSize) {
		super();
		this.page = page;
		this.pageSize = pageSize;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getTotoalPage() {
		return totoalPage;
	}
	public void setMaxPage(int totoalPage) {		
		this.totoalPage = totoalPage;
		setNextPage(this.page>=totoalPage?totoalPage:this.page+1);
	}
	public int getNextPage() {
		return nextPage;
	}
	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		if(page < 1) {
			this.page = 1;
		} else {
			this.page = page;
		}
		if(page>this.totoalPage){
			this.page=this.totoalPage;
		}
		setPrePage(this.page<=1?1:this.page-1);
		setNextPage(this.page>=totoalPage?totoalPage:this.page+1);
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPrePage() {
		return prePage;
	}
	public void setPrePage(int prePage) {
		this.prePage = prePage;
	}
	
}
