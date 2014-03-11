package nju.software.web.vo;

public class DBSignal {
	
	private static DBSignal dbSignal;
	public boolean isDBChanged=false;
	
	private DBSignal(){}
	
	public  static DBSignal getInstance(){
		if(dbSignal==null)
			dbSignal=new DBSignal();
		return dbSignal;
	}

}
