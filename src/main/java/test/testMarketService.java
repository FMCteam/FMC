package test;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nju.software.dataobject.Accessory;
import nju.software.dataobject.DesignCad;
import nju.software.dataobject.Fabric;
import nju.software.dataobject.Logistics;
import nju.software.dataobject.Order;
import nju.software.dataobject.Produce;
import nju.software.dataobject.VersionData;
import nju.software.service.MarketService;
import nju.software.service.impl.MarketServiceImpl;

public class testMarketService {
	public void test(){
		
		Order order=new Order();
		List<Fabric> fabrics=new ArrayList<Fabric>();
		MarketService ms=new MarketServiceImpl();
		List<Accessory> accessorys=new ArrayList<Accessory>();
		Logistics logistics=new Logistics();
		List<Produce> produces=new ArrayList<Produce>();
		List<Produce> sample_produces=new ArrayList<Produce>();
		
		List<VersionData> versions=new ArrayList<VersionData>();
		DesignCad cad=new DesignCad();
		
		HttpServletRequest request=null;
		ms.addOrderCustomerSubmit(order, fabrics, accessorys, logistics, produces, sample_produces, versions, cad, request);
	}
	public static void main(String args[]){
		new testMarketService().test();
	}
}
