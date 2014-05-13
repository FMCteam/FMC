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
import nju.software.dao.impl.FabricDAO;
import nju.software.dao.impl.LogisticsDAO;
import nju.software.dao.impl.OrderDAO;
import nju.software.dao.impl.QuoteDAO;
import nju.software.dao.impl.DesignCadDAO;
import nju.software.dataobject.Accessory;
import nju.software.dataobject.Account;
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

	// ===========================上传版型=================================
	@Override
	public List<Map<String, Object>> getUploadDesignList() {
		return service.getOrderList(ACTOR_DESIGN_MANAGER, TASK_UPLOAD_DESIGN);
	}

	@Override
	public Map<String, Object> getUploadDesignDetail(Integer orderId) {
		// TODO Auto-generated method stub
		return service.getBasicOrderModelWithQuote(ACTOR_DESIGN_MANAGER,
				TASK_UPLOAD_DESIGN, orderId);
	}

	@Override
	public boolean uploadDesignSubmit(int orderId, long taskId, String url,
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
		try {
			jbpmAPIUtil.completeTask(taskId, data, ACTOR_DESIGN_MANAGER);
			return true;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
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

	@Override
	public Map<String, Object> getConfirmDesignDetail(Integer orderId) {
		// TODO Auto-generated method stub
		Map<String, Object> model = service.getBasicOrderModelWithQuote(
				ACTOR_DESIGN_MANAGER, TASK_CONFIRM_DESIGN, orderId);
		model.put("cad", designCadDAO.findByOrderId(orderId).get(0));
		return model;
	}

	public final static String ACTOR_DESIGN_MANAGER = "designManager";
	public final static String TASK_VERIFY_DESIGN = "verifyDesign";
	public final static String TASK_COMPUTE_DESIGN_COST = "computeDesignCost";
	public final static String TASK_UPLOAD_DESIGN = "uploadDegisn";
	public final static String TASK_MODIFY_DESIGN = "modifyDesign";
	public final static String TASK_CONFIRM_DESIGN = "confirmDesign";
	public final static String RESULT_DESIGN = "design";
	public final static String RESULT_DESIGN_COMMENT = "designComment";

	@Autowired
	private JbpmAPIUtil jbpmAPIUtil;
	@Autowired
	private ServiceUtil service;
	@Autowired
	private DesignCadDAO designCadDAO;
}
