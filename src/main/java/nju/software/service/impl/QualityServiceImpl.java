package nju.software.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nju.software.dao.impl.CheckDetailDAO;
import nju.software.dao.impl.CheckRecordDAO;
import nju.software.dao.impl.CustomerDAO;
import nju.software.dao.impl.EmployeeDAO;
import nju.software.dao.impl.OrderDAO;
import nju.software.dao.impl.ProduceDAO;
import nju.software.dataobject.CheckDetail;
import nju.software.dataobject.CheckRecord;
import nju.software.dataobject.Produce;
import nju.software.service.QualityService;
import nju.software.util.JbpmAPIUtil;

import org.drools.runtime.StatefulKnowledgeSession;
import org.jbpm.task.query.TaskSummary;
import org.jbpm.workflow.instance.WorkflowProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("qualityServiceImpl")
public class QualityServiceImpl implements QualityService {

	public final static String ACTOR_QUALITY_MANAGER = "qualityManager";
	public final static String TASK_CHECK_QUALITY = "checkQuality";
	public final static String CHECK_RESULT_QUALITY = "QUALIFIED";
	public final static String CHECK_RESULT_UNQUALITY = "UNQUALIFIED";

	@Autowired
	private OrderDAO orderDAO;
	@Autowired
	private EmployeeDAO employeeDAO;
	@Autowired
	private CustomerDAO customerDAO;
	@Autowired
	private JbpmAPIUtil jbpmAPIUtil;
	@Autowired
	private ServiceUtil service;
	@Autowired
	private ProduceDAO produceDAO;
	@Autowired
	private CheckRecordDAO checkRecordDAO;
	@Autowired
	private CheckDetailDAO checkDetailDAO;

	@Override
	public List<Map<String, Object>> getCheckQualityList() {
		// TODO Auto-generated method stub
		return service.getOrderList(ACTOR_QUALITY_MANAGER, TASK_CHECK_QUALITY);
	}

	@Override
	public List<Map<String, Object>> getSearchCheckQualityList(
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds) {
		return service.getSearchOrderList(ACTOR_QUALITY_MANAGER,  ordernumber,  customername,  stylename,
				 startdate,  enddate, employeeIds,TASK_CHECK_QUALITY);

	}
	
	public Object getVariable(String name, TaskSummary task) {
		StatefulKnowledgeSession session = jbpmAPIUtil.getKsession();
		long processId = task.getProcessInstanceId();
		WorkflowProcessInstance process = (WorkflowProcessInstance) session
				.getProcessInstance(processId);
		return process.getVariable(name);
	}

	@Override
	public String checkQualitySubmit(int orderId, long taskId, String isFinal,
			CheckRecord checkRecord, List<Produce> goodList) {

		String msg = "";
		
		// 先根据orderId找出本次订单大货生产总数（外发总数）
		List<Produce> totalProduceList = produceDAO
				.findTotalProduceByOrderId(orderId);

		int totalProduceAmount = 0;// 大货生产总数
		for (Produce p : totalProduceList) {
			totalProduceAmount += p.getProduceAmount();
		}

		// 再根据orderId找出本次订单的所有质检记录
		List<CheckRecord> checkRecordList = checkRecordDAO
				.findByOrderId(orderId);
		int historyQualifiedAmount = 0;// 历史质检合格总数
		int historyInvalidAmount = 0;// 历史报废总数
//		int historyUnqualifiedAmount = 0;// 历史质检不合格总数
		if (checkRecordList != null && checkRecordList.size() > 0) {
			for (CheckRecord record : checkRecordList) {
				historyQualifiedAmount += record.getQualifiedAmount();
				historyInvalidAmount += record.getInvalidAmount();
//				historyUnqualifiedAmount += record.getRepairAmount();
			}
		}
		
		//如果没有质检记录却选择完成本次质检，返回false
		if (isFinal.equals("true") &&(checkRecordList == null || checkRecordList.size() == 0)){
			msg = "没有质检记录，不能完成最终质检";
			return msg;
		}
		// 历史已质检合格总数 + 报废总数
		int historyCheckTotalAmount = historyQualifiedAmount + historyInvalidAmount;

		// 再计算本次质检总数
		int thisCheckQualifiedAmount = 0;//本次合格数量
		int thisCheckRepairAmount = checkRecord.getRepairAmount();//本次回修数量
		int thisCheckInvalidAmount = checkRecord.getInvalidAmount();//本次报废数量
//		int thisCheckUnqualifiedAmount = 0;
		
		for (int i = 0; i < goodList.size(); i++) {
			thisCheckQualifiedAmount += goodList.get(i).getProduceAmount();
		}
		
//		for (int i = 0; i < badList.size(); i++) {
//			thisCheckUnqualifiedAmount += badList.get(i).getProduceAmount();
//		}
		
		// 本次质检总数（没有包括本次回修数）
		int thisCheckTotalAmount = thisCheckQualifiedAmount + thisCheckInvalidAmount;

		// 若：1、本次质检合格总数 > 生产总数（外发总数）;
		// 2、本次质检回修总数 > 生产总数;
		// 3、本次质检合格数 + 报废数 > 生产总数;
		// 4、本次质检合格数和报废数 + 回修数 > 生产总数;
		// 5、本次质检总数+历史质检合格数和报废数 > 生产总数
		if (thisCheckQualifiedAmount > totalProduceAmount
				|| thisCheckRepairAmount > totalProduceAmount
				|| thisCheckTotalAmount > totalProduceAmount
				|| (thisCheckTotalAmount + thisCheckRepairAmount) > totalProduceAmount
				|| (thisCheckTotalAmount + thisCheckRepairAmount + historyCheckTotalAmount) > totalProduceAmount){
			
			msg = "您填写的质检数量有误，请重新填写";
			return msg;
		}
		
		//如果选择"保存本次质检"但是合格总数+报废总数等于应收总数（应该选择"完成最终质检"），返回false;
		if (isFinal.equals("false")
				&& ((thisCheckTotalAmount + historyCheckTotalAmount) == totalProduceAmount)) {
			msg = "所有产品已经完成质量检查，请选择完成最终质检";
			return msg;
		}
		
		//如果选择"完成最终质检"但是合格总数+报废总数不等于应收总数，返回false;
		if (isFinal.equals("true")
				&& ((thisCheckTotalAmount + historyCheckTotalAmount) != totalProduceAmount)) {
			msg = "您填写的质检数量有误或者有产品还未质检，不能完成最终的质检";
			return msg;
		}
		
		checkRecord.setQualifiedAmount(thisCheckQualifiedAmount);
		checkRecordDAO.save(checkRecord);
		int recordId = checkRecord.getRecordId();

		CheckDetail checkDetail = new CheckDetail();
		checkDetail.setRecordId(recordId);
		for (int i = 0; i < goodList.size(); i++) {
			Produce produce = goodList.get(i);
			checkDetail.setColor(produce.getColor());
			checkDetail.setXs(produce.getXs());
			checkDetail.setS(produce.getS());
			checkDetail.setM(produce.getM());
			checkDetail.setL(produce.getL());
			checkDetail.setXl(produce.getXl());
			checkDetail.setXxl(produce.getXxl());
			checkDetail.setCheckAmount(thisCheckQualifiedAmount);
			checkDetail.setCheckResult(CHECK_RESULT_QUALITY);

			checkDetailDAO.save(checkDetail);
		}

//		for (int i = 0; i < badList.size(); i++) {
//			Produce produce = badList.get(i);
//			checkDetail.setColor(produce.getColor());
//			checkDetail.setXs(produce.getXs());
//			checkDetail.setS(produce.getS());
//			checkDetail.setM(produce.getM());
//			checkDetail.setL(produce.getL());
//			checkDetail.setXl(produce.getXl());
//			checkDetail.setXxl(produce.getXxl());
//			checkDetail.setCheckAmount(thisCheckUnqualifiedAmount);
//			checkDetail.setCheckResult(CHECK_RESULT_UNQUALITY);
//
//			checkDetailDAO.save(checkDetail);
//		}

		// 如果不是最后一次质检，直接返回
		if (isFinal.equals("false")) {
			return msg;
		}
		
		//如果是最后一次质检，需要执行completeTask方法
		Map<String, Object> data = new HashMap<>();
		try {
			jbpmAPIUtil.completeTask(taskId, data, ACTOR_QUALITY_MANAGER);
			return msg;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;
	}

	@Override
	public Map<String,Object> getCheckQualityDetail(int orderId) {
		// TODO Auto-generated method stub
		Map<String,Object> oi=service.getBasicOrderModelWithQuote(
				ACTOR_QUALITY_MANAGER, TASK_CHECK_QUALITY, orderId);
		Produce produce = new Produce();
		produce.setOid(orderId);
		produce.setType(Produce.TYPE_PRODUCED);
		oi.put("produced", produceDAO.findByExample(produce));
		oi.put("repairRecord", checkRecordDAO.findByOrderId(orderId));//回修记录
		return oi;
	}


}
