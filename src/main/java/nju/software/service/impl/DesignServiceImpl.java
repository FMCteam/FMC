package nju.software.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.net.ntp.TimeStamp;
import org.drools.runtime.StatefulKnowledgeSession;
import org.jbpm.task.query.TaskSummary;
import org.jbpm.workflow.instance.WorkflowProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nju.software.dao.impl.AccessoryDAO;
import nju.software.dao.impl.CraftDAO;
import nju.software.dao.impl.FabricDAO;
import nju.software.dao.impl.LogisticsDAO;
import nju.software.dao.impl.OrderDAO;
import nju.software.dao.impl.QuoteDAO;
import nju.software.dao.impl.DesignCadDAO;
import nju.software.dataobject.Accessory;
import nju.software.dataobject.Account;
import nju.software.dataobject.Craft;
import nju.software.dataobject.Fabric;
import nju.software.dataobject.Logistics;
import nju.software.dataobject.Order;
import nju.software.dataobject.Quote;
import nju.software.dataobject.DesignCad;
import nju.software.model.OrderInfo;
import nju.software.service.DesignService;
import nju.software.util.JbpmAPIUtil;

@Service("designServiceImpl")
public class DesignServiceImpl implements DesignService {

	// ===========================设计验证=================================
	@Override
	public List<Map<String, Object>> getVerifyDesignList() {
		// TODO Auto-generated method stub
		return service.getOrderList(ACTOR_DESIGN_MANAGER, TASK_VERIFY_DESIGN);
	}

	@Override
	public List<Map<String, Object>> getSearchVerifyDesignList(
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds) {
		// TODO Auto-generated method stub
		return service.getSearchOrderList(ACTOR_DESIGN_MANAGER, ordernumber,customername,stylename,
				 startdate,enddate,employeeIds,TASK_VERIFY_DESIGN);
	}
	
	@Override
	public Map<String, Object> getVerifyDesignDetail(int orderId) {
		// TODO Auto-generated method stub
		return service.getBasicOrderModelWithQuote(ACTOR_DESIGN_MANAGER,
				TASK_VERIFY_DESIGN, orderId);
	}

	@Override
	public boolean verifyDesignSubmit(long taskId, boolean result,
			String comment) {
		// TODO Auto-generated method stub
		Map<String, Object> data = new HashMap<>();
		data.put(RESULT_DESIGN, result);
		data.put(RESULT_DESIGN_COMMENT, comment);
		try {
			jbpmAPIUtil.completeTask(taskId, data, ACTOR_DESIGN_MANAGER);
			return true;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	
	@Override
	public List<Map<String, Object>> getComputeDesignCostList() {
		return service.getOrderList(ACTOR_DESIGN_MANAGER,
				TASK_COMPUTE_DESIGN_COST);
	}
	
	@Override
	public List<Map<String, Object>> getSearchComputeDesignCostList(
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds) {
		return service.getSearchOrderList(ACTOR_DESIGN_MANAGER, ordernumber,  customername,  stylename,
				 startdate,  enddate, employeeIds,
				TASK_COMPUTE_DESIGN_COST);
	}
	
	@Override
	public Map<String, Object> getComputeDesignCostInfo(Integer orderId) {
		Map<String, Object> model = service.getBasicOrderModelWithQuote(ACTOR_DESIGN_MANAGER,
				TASK_COMPUTE_DESIGN_COST, orderId);
		DesignCad designcad = designCadDAO.findByOrderId(orderId).get(0);
		model.put("designCadTech", designcad.getCadTech());
		return model;
	}
	
	//计算设计工艺费用
	@Override
	public void computeDesignCostSubmit(int orderId, long taskId,
			boolean result, String comment, short needCraft,
			float stampDutyMoney, float washHangDyeMoney, float laserMoney,
			float embroideryMoney, float crumpleMoney, float openVersionMoney) {
		
		List<Craft> craftList = craftDAO.findByOrderId(orderId);
		for (Craft craft : craftList) {
			craftDAO.delete(craft);
		}
		Craft craft = new Craft();
		craft.setNeedCraft(needCraft);
		craft.setOrderId(orderId);
		craft.setStampDutyMoney(stampDutyMoney);
		craft.setWashHangDyeMoney(washHangDyeMoney);
		craft.setLaserMoney(laserMoney);
		craft.setEmbroideryMoney(embroideryMoney);
		craft.setCrumpleMoney(crumpleMoney);
		craft.setOpenVersionMoney(openVersionMoney);
		craftDAO.save(craft);
		Quote quote = quoteDAO.findById(orderId);
		//单件工艺制作费
		float craftCost = stampDutyMoney + washHangDyeMoney + laserMoney + embroideryMoney
						  + crumpleMoney + openVersionMoney;
		if (null == quote) {
			quote = new Quote();
			quote.setCraftCost(craftCost);
			quote.setOrderId(orderId);
			quoteDAO.save(quote);
		}
		
		quote.setCraftCost(craftCost);
		quoteDAO.attachDirty(quote);

		Map<String, Object> data = new HashMap<String, Object>();
		boolean isNeedCraft = false;
		if (needCraft == 1){
			isNeedCraft = true;
		}
		data.put(RESULT_NEED_CRAFT, isNeedCraft);
		data.put(RESULT_DESIGN, result);
		data.put(RESULT_DESIGN_COMMENT, comment);
		
		try {
			jbpmAPIUtil.completeTask(taskId, data, ACTOR_DESIGN_MANAGER);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	// ===========================上传版型=================================
	@Override
	public List<Map<String, Object>> getUploadDesignList() {
		return service.getOrderList(ACTOR_DESIGN_MANAGER, TASK_UPLOAD_DESIGN);
	}

	@Override
	public List<Map<String, Object>> getSearchUploadDesignList(
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds) {
		return service.getSearchOrderList(ACTOR_DESIGN_MANAGER, ordernumber,customername,stylename,
				 startdate,enddate,employeeIds, TASK_UPLOAD_DESIGN);
	}
	
	@Override
	public Map<String, Object> getUploadDesignDetail(Integer orderId) {
		// TODO Auto-generated method stub
		return service.getBasicOrderModelWithQuote(ACTOR_DESIGN_MANAGER,
				TASK_UPLOAD_DESIGN, orderId);
	}

	@Override
	public void uploadDesignSubmit(int orderId, long taskId, String url,
			Timestamp uploadTime) {
		// TODO Auto-generated method stub
		DesignCad designCad = null;
		List<DesignCad> designCadList = designCadDAO.findByOrderId(orderId);
		if (designCadList.isEmpty()) {
			designCad = new DesignCad();
			designCad.setOrderId(orderId);
			designCad.setCadVersion((short) 1);
		} else {
			designCad = designCadList.get(0);
			short newVersion = (short) (designCad.getCadVersion() + 1);
			designCad.setCadVersion(newVersion);
		}
		designCad.setCadUrl(url);
		designCad.setUploadTime(uploadTime);
		designCadDAO.attachDirty(designCad);
		
//		Map<String, Object> data = new HashMap<String, Object>();
//		try {
//			jbpmAPIUtil.completeTask(taskId, data, ACTOR_DESIGN_MANAGER);
//			return true;
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return false;
//		}
	}
//===========================样衣生产提交========================================
	@Override
	public boolean produceSampleSubmit(long taskId, boolean result) {
 		Map<String, Object> data = new HashMap<String, Object>();
		data.put(RESULT_PRODUCE, result);
		try {
			jbpmAPIUtil.completeTask(taskId, data, ACTOR_DESIGN_MANAGER);
			return true;
		} catch (InterruptedException e) {
 			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public boolean produceSampleSubmit(long taskId, boolean result,
			String orderId) {
		Order order = orderDAO.findById(Integer.parseInt(orderId));
 		Map<String, Object> data = new HashMap<String, Object>();
		data.put(RESULT_PRODUCE, result);
		try {
			jbpmAPIUtil.completeTask(taskId, data, ACTOR_DESIGN_MANAGER);
			if(result==false){//如果result的的值为FALSE，即为样衣生产失败，流程会异常终止，将orderState设置为1
				order.setOrderState("1");
				orderDAO.merge(order);
			}
			return true;
		} catch (InterruptedException e) {
 			e.printStackTrace();
			return false;
		}
	}

	
	// ===========================修改版型=================================
	@Override
	public List<Map<String, Object>> getModifyDesignList() {
		return service.getOrderList(ACTOR_DESIGN_MANAGER, TASK_MODIFY_DESIGN);
	}

	@Override
	public List<Map<String, Object>> getSearchModifyDesignList(
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds) {
		// TODO Auto-generated method stub
		return service.getSearchOrderList(ACTOR_DESIGN_MANAGER,ordernumber,customername,stylename,
				 startdate,enddate,employeeIds,TASK_MODIFY_DESIGN);
	}
	
	@Override
	public Map<String, Object> getModifyDesignDetail(Integer orderId) {
		// TODO Auto-generated method stub
		Map<String, Object> model = service.getBasicOrderModelWithQuote(
				ACTOR_DESIGN_MANAGER, TASK_MODIFY_DESIGN, orderId);

		
		if(designCadDAO.findByOrderId(orderId)!=null&&designCadDAO.findByOrderId(orderId).size()!=0){
			if (((Order)model.get("order")).getIsNeedSampleClothes() == 1) {
				model.put("cad", designCadDAO.findByOrderId(orderId).get(0));
			}
		}
		return model;
	}
	
	@Override
	public boolean modifyDesignSubmit(int orderId, long taskId, String url,
			Timestamp uploadTime) {
		// TODO Auto-generated method stub
		DesignCad designCad = null;
		List<DesignCad> designCadList = designCadDAO.findByOrderId(orderId);
		if (designCadList.isEmpty()) {
			designCad = new DesignCad();
			designCad.setOrderId(orderId);
			designCad.setCadVersion((short) 1);
		} else {
			designCad = designCadList.get(0);
			short newVersion = (short) (designCad.getCadVersion() + 1);
			designCad.setCadVersion(newVersion);
		}
		designCad.setCadUrl(url);
		designCad.setUploadTime(uploadTime);
		designCadDAO.attachDirty(designCad);
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put(RESULT_DESIGN, true);
		try {
			jbpmAPIUtil.completeTask(taskId, data, ACTOR_DESIGN_MANAGER);
			return true;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}


	// ===========================确认版型=================================
	@Override
	public List<Map<String, Object>> getConfirmDesignList() {
		// TODO Auto-generated method stub
		return service.getOrderList(ACTOR_DESIGN_MANAGER, TASK_CONFIRM_DESIGN);
	}
	// ===========================排版切片前最终确认版型=================================	
	@Override
	public List<Map<String, Object>> getConfirmCadList() {
		// TODO Auto-generated method stub
		return service.getOrderList(ACTOR_DESIGN_MANAGER, TASK_CONFIRM_CAD);
	}
	
    //获得需要工艺制作的大货订单列表
	@Override
	public List<Map<String, Object>> getNeedCraftList() {
		// TODO Auto-generated method stub
		return service.getOrderList(ACTOR_DESIGN_MANAGER, TASK_CRAFT_PRODUCT);
	}
	 //获得需要工艺制作的大货订单
	@Override
	public Map<String, Object> getNeedCraftProductDetail(int orderId) {
		Map<String, Object> model = service.getBasicOrderModelWithQuote(
				ACTOR_DESIGN_MANAGER, TASK_CRAFT_PRODUCT, orderId);
		DesignCad designcad = designCadDAO.findByOrderId(orderId).get(0);
		model.put("designCadTech", designcad.getCadTech());
		Craft craft = craftDAO.findByOrderId(orderId).get(0);
		model.put("craft", craft);
		return model;
	}
	
	@Override
	public void needCraftProductSubmit(int orderId, long taskId) {
 		Map<String, Object> data = new HashMap<String, Object>();
		try {
			jbpmAPIUtil.completeTask(taskId, data, ACTOR_DESIGN_MANAGER);
			
		} catch (InterruptedException e) {
 			e.printStackTrace();
		}
	}
	
	//获得需要工艺制作的样衣订单列表
	@Override
	public List<Map<String, Object>> getNeedCraftSampleList() {
		// TODO Auto-generated method stub
		return service.getOrderList(ACTOR_DESIGN_MANAGER, TASK_CRAFT_SAMPLE);
	}
	//获得需要工艺制作的样衣订单
	@Override
	public Map<String, Object> getNeedCraftSampleDetail(int orderId) {
		Map<String, Object> model = service.getBasicOrderModelWithQuote(
				ACTOR_DESIGN_MANAGER, TASK_CRAFT_SAMPLE, orderId);
		DesignCad designcad = designCadDAO.findByOrderId(orderId).get(0);
		model.put("designCadTech", designcad.getCadTech());
		Craft craft = craftDAO.findByOrderId(orderId).get(0);
		model.put("sampleCraft", craft);
		return model;
	}
	
	@Override
	public void uploadCraftFileSubmit(int orderId, String craftFileUrl) {
		Craft craft = craftDAO.findByOrderId(orderId).get(0);
		craft.setCraftFileUrl(craftFileUrl);
		craftDAO.merge(craft);
 
	}
	@Override
	public void needCraftSampleSubmit(int orderId, long taskId) {
		// TODO Auto-generated method stub
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			jbpmAPIUtil.completeTask(taskId, data, ACTOR_DESIGN_MANAGER);
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//获得需要排版切片订单列表
	@Override
	public List<Map<String, Object>> getTypeSettingSliceList() {
		// TODO Auto-generated method stub
		return service.getOrderList(ACTOR_DESIGN_MANAGER, TASK_TYPESETTING_SLICE);
	}
	
	//获得需要排版切片订单	
	@Override
	public Map<String, Object> getTypeSettingSliceDetail(int orderId) {
		Map<String, Object> model = service.getBasicOrderModelWithQuote(
				ACTOR_DESIGN_MANAGER, TASK_TYPESETTING_SLICE, orderId);
		return model;
	}
	
	@Override
	public void getTypeSettingSliceSubmit(int orderId, long taskId) {
 		Map<String, Object> data = new HashMap<String, Object>();
		try {
			jbpmAPIUtil.completeTask(taskId, data, ACTOR_DESIGN_MANAGER);
			
		} catch (InterruptedException e) {
 			e.printStackTrace();
		}
	}
	
	@Override
	public void getTypeSettingSliceSubmit(int orderId, String cadding_side,
			long taskId) {
		DesignCad designCad = null;
		List<DesignCad> designCadList = designCadDAO.findByOrderId(orderId);
		if (designCadList.isEmpty()) {
			designCad = new DesignCad();
			designCad.setOrderId(orderId);
			designCad.setCadVersion((short) 1);
			designCad.setCaddingSide(cadding_side);
		} else {
			designCad = designCadList.get(0);
			designCad.setCaddingSide(cadding_side);
 		}
 
		designCadDAO.attachDirty(designCad);
 		Map<String, Object> data = new HashMap<String, Object>();
		try {
			jbpmAPIUtil.completeTask(taskId, data, ACTOR_DESIGN_MANAGER);
			
		} catch (InterruptedException e) {
 			e.printStackTrace();
		}
	}
	
	@Override
	public List<Map<String, Object>> getSearchConfirmDesignList(
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds) {
		return service.getSearchOrderList(ACTOR_DESIGN_MANAGER,
				 ordernumber,  customername,  stylename,startdate,  enddate,  employeeIds,
				TASK_CONFIRM_DESIGN);

	}
	
	@Override
	public Map<String, Object> getConfirmDesignDetail(Integer orderId) {
		// TODO Auto-generated method stub
		Map<String, Object> model = service.getBasicOrderModelWithQuote(
				ACTOR_DESIGN_MANAGER, TASK_CONFIRM_DESIGN, orderId);
		model.put("cad", designCadDAO.findByOrderId(orderId).get(0));
		return model;
	}
	//取得确认版型数据的详细信息
	@Override
	public Map<String, Object> getConfirmCadDetail(int orderId) {
		Map<String, Object> model = service.getBasicOrderModelWithQuote(
				ACTOR_DESIGN_MANAGER, TASK_CONFIRM_CAD, orderId);
		model.put("cad", designCadDAO.findByOrderId(orderId).get(0));
		return model;
	}
	//重新提交版型数据
	@Override
	public boolean confirmCadSubmit(int orderId, long taskId, String cadurl,
			Timestamp uploadTime) {
		DesignCad designCad = null;
		List<DesignCad> designCadList = designCadDAO.findByOrderId(orderId);
		if (designCadList.isEmpty()) {
			designCad = new DesignCad();
			designCad.setOrderId(orderId);
			designCad.setCadVersion((short) 1);
		} else {
			designCad = designCadList.get(0);
			short newVersion = (short) (designCad.getCadVersion() + 1);
			designCad.setCadVersion(newVersion);
		}
		designCad.setCadUrl(cadurl);
		designCad.setUploadTime(uploadTime);
		designCadDAO.attachDirty(designCad);
		
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			jbpmAPIUtil.completeTask(taskId, data, ACTOR_DESIGN_MANAGER);
			return true;
		} catch (InterruptedException e) {
 			e.printStackTrace();
			return false;
		}
	}
	public final static String ACTOR_DESIGN_MANAGER = "designManager";
	public final static String TASK_VERIFY_DESIGN = "verifyDesign";
	public final static String TASK_COMPUTE_DESIGN_COST = "computeDesignCost";
	public final static String TASK_UPLOAD_DESIGN = "uploadDegisn";
	public final static String TASK_MODIFY_DESIGN = "modifyDesign";
	public final static String TASK_CONFIRM_DESIGN = "confirmDesign";
	public final static String TASK_CRAFT_SAMPLE = "craftSample";
	public final static String TASK_CRAFT_PRODUCT = "craft";
	public final static String TASK_TYPESETTING_SLICE = "typeSettingSlice";
	public final static String TASK_CONFIRM_CAD = "confirmCad";
	public final static String RESULT_DESIGN = "design";
	public final static String RESULT_DESIGN_COMMENT = "designComment";
    public final static String RESULT_NEED_CRAFT ="needCraft";
	public final static String RESULT_PRODUCE = "produce";
    
	@Autowired
	private JbpmAPIUtil jbpmAPIUtil;
	@Autowired
	private ServiceUtil service;
	@Autowired
	private DesignCadDAO designCadDAO;
	@Autowired
	private QuoteDAO quoteDAO;
	@Autowired
	private CraftDAO craftDAO;
	@Autowired
	private OrderDAO orderDAO;
	




}
