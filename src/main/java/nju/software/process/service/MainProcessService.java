package nju.software.process.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nju.software.service.impl.BuyServiceImpl;
import nju.software.service.impl.DesignServiceImpl;
import nju.software.service.impl.FinanceServiceImpl;
import nju.software.service.impl.LogisticsServiceImpl;
import nju.software.service.impl.MarketServiceImpl;
import nju.software.service.impl.ProduceServiceImpl;
import nju.software.service.impl.QualityServiceImpl;
import nju.software.service.impl.SweaterMakeServiceImpl;

import org.activiti.engine.task.Task;


public class MainProcessService extends BasicProcessService{
	public static final String PROCESS_NAME = "nju.software.fmc.bpmn";
	private static final String KEY_ORDER_ID = "orderId";

	public String startWorkflow(Map<String, Object> params) {
		return startWorkflow(PROCESS_NAME, params);
	}
	
	public int getOrderIdInProcess(String processId){
		return (int) getProcessVariable(processId, KEY_ORDER_ID);
	}
	
	public Task getTaskOfUserByTaskNameWithSpecificOrderId(String userId,String taskName, Object value){
		String newName = getTaskNameInBPMN(taskName);
		return getTask(userId, newName, KEY_ORDER_ID, value);
	}
	
	public List<Task> getAllTasksOfUserByTaskName(String userId, String taskName){
		String newName = getTaskNameInBPMN(taskName);
		if (newName != null) {
			return getTasksOfUserByTaskName(userId, newName);
		}
		return null;
	}
	
	//===================================市场部专员部分=============================================
	
	public List<Task> getConfirmProduceOrderTasks(String userId){
		String taskName = getTaskNameInBPMN(MarketServiceImpl.TASK_CONFIRM_PRODUCE_ORDER);
		return getTasksOfUserByTaskName(userId, taskName);
	}
	
	public List<Task> getComfirmQuoteTasks(String userId){
		String taskName = getTaskNameInBPMN(MarketServiceImpl.TASK_CONFIRM_QUOTE);
		return getTasksOfUserByTaskName(userId, taskName);
	}
	
	public List<Task> getMergeQuoteTasks(String userId){
		String taskName = getTaskNameInBPMN(MarketServiceImpl.TASK_MERGE_QUOTE);
		return getTasksOfUserByTaskName(userId, taskName);
	}
	
	public List<Task> getModifyOrderTasks(String userId){
		String taskName = getTaskNameInBPMN(MarketServiceImpl.TASK_MODIFY_ORDER);
		return getTasksOfUserByTaskName(userId, taskName);
	}
	
	public List<Task> getModifyProduceOrderTasks(String userId){
		String taskName = getTaskNameInBPMN(MarketServiceImpl.TASK_MODIFY_PRODUCE_ORDER);
		return getTasksOfUserByTaskName(userId, taskName);
	}
	
	public List<Task> getModifyQuoteTasks(String userId){
		String taskName = getTaskNameInBPMN(MarketServiceImpl.TASK_MODIFY_QUOTE);
		return getTasksOfUserByTaskName(userId, taskName);
	}
	
	public List<Task> getPushRestTasks(String userId) {
		String taskName = getTaskNameInBPMN(MarketServiceImpl.TASK_PUSH_REST);
		return getTasksOfUserByTaskName(userId, taskName);
	}
	
	public List<Task> getSignContractTasks(String userId){
		String taskName = getTaskNameInBPMN(MarketServiceImpl.TASK_SIGN_CONTRACT);
		return getTasksOfUserByTaskName(userId, taskName);
	}

	//=======================================市场主管部分========================================
	public List<Task> getVerifyQuoteTasks(String userId){
		String taskName = getTaskNameInBPMN(MarketServiceImpl.TASK_VERIFY_QUOTE);
		return getTasksOfUserByTaskName(MarketServiceImpl.ACTOR_MARKET_MANAGER, taskName);
	}
	
	//======================================设计部=============================================
	public List<Task> getComputDesignCostTasks(String userId){
		String taskName = getTaskNameInBPMN(DesignServiceImpl.TASK_COMPUTE_DESIGN_COST);
		return getTasksOfUserByTaskName(userId, taskName);
	}
	
	public List<Task> getConfirmCadTasks(String userId){
		String taskName = getTaskNameInBPMN(DesignServiceImpl.TASK_CONFIRM_CAD);
		return getTasksOfUserByTaskName(userId, taskName);
	}
	
	public List<Task> getConfirmDesignTasks(String userId){
		String taskName = getTaskNameInBPMN(DesignServiceImpl.TASK_CONFIRM_DESIGN);
		return getTasksOfUserByTaskName(userId, taskName);
	}
	
	public List<Task> getCraftProductTasks(String userId){
		String taskName = getTaskNameInBPMN(DesignServiceImpl.TASK_CRAFT_PRODUCT);
		return getTasksOfUserByTaskName(userId, taskName);
	}
	
	public List<Task> getCraftSampleTasks(String userId){
		String taskName = getTaskNameInBPMN(DesignServiceImpl.TASK_CRAFT_SAMPLE);
		return getTasksOfUserByTaskName(userId, taskName);
	}
	
	public List<Task> getModifyDesignTasks(String userId){
		String taskName  = getTaskNameInBPMN(DesignServiceImpl.TASK_MODIFY_DESIGN);
		return getTasksOfUserByTaskName(userId, taskName);
	}
	
	public List<Task> getTypeSettingSliceTasks(String userId){
		String taskName = getTaskNameInBPMN(DesignServiceImpl.TASK_TYPESETTING_SLICE);
		return getTasksOfUserByTaskName(userId, taskName);
	}
	
	public List<Task> getUploadDesignTasks(String userId){
		String taskName = getTaskNameInBPMN(DesignServiceImpl.TASK_UPLOAD_DESIGN);
		return getTasksOfUserByTaskName(userId, taskName);
	}
	
	public List<Task> getVerifyDesignTasks(String userId){
		String taskName = getTaskNameInBPMN(DesignServiceImpl.TASK_VERIFY_DESIGN);
		return getTasksOfUserByTaskName(userId, taskName);
	}
	
	//====================================采购部分============================================
	public List<Task> getBuySweaterMaterialTasks(String userId){
		String taskName = getTaskNameInBPMN(BuyServiceImpl.TASK_BUY_SWEATER_MATERIAL);
		return getTasksOfUserByTaskName(userId, taskName);
	}
	
	public List<Task> getComputePurchaseCostTasks(String userId){
		String taskName = getTaskNameInBPMN(BuyServiceImpl.TASK_COMPUTE_PURCHASE_COST);
		return getTasksOfUserByTaskName(userId, taskName);
	}
	
	public List<Task> getConfirmPurchaseTasks(String userId){
		String taskName = getTaskNameInBPMN(BuyServiceImpl.TASK_CONFIRM_PURCHASE);
		return getTasksOfUserByTaskName(userId, taskName);
	}
	
	public List<Task> getPurchaseMaterialTasks(String userId){
		String taskName = getTaskNameInBPMN(BuyServiceImpl.TASK_PURCHASE_MATERIAL);
		return getTasksOfUserByTaskName(userId, taskName);
	}
	
	public List<Task> getPurchaseSampleMaterialTasks(String userId){
		String taskName = getTaskNameInBPMN(BuyServiceImpl.TASK_PURCHASE_SAMPLE_MATERIAL);
		return getTasksOfUserByTaskName(userId, taskName);
	}

	public List<Task> getVerifyPurchaseTasks(String userId){
		String taskName = getTaskNameInBPMN(BuyServiceImpl.TASK_VERIFY_PURCHASE);
		return getTasksOfUserByTaskName(userId, taskName);
	}
	
	//=====================================生产部==============================================
	public List<Task> getComputeProduceCostTasks(String userId){
		String taskName = getTaskNameInBPMN(ProduceServiceImpl.TASK_COMPUTE_PRODUCE_COST);
		return getTasksOfUserByTaskName(userId, taskName);
	}
	
	public List<Task> getProduceTasks(String userId){
		String taskName = getTaskNameInBPMN(ProduceServiceImpl.TASK_PRODUCE);
		return getTasksOfUserByTaskName(userId, taskName);
	}
	
	public List<Task> getProduceSampleTasks(String userId){
		String taskName = getTaskNameInBPMN(ProduceServiceImpl.TASK_PRODUCE_SAMPLE);
		return getTasksOfUserByTaskName(userId, taskName);
	}
	
	public List<Task> getVerifyProduceTasks(String userId){
		String taskName = getTaskNameInBPMN(ProduceServiceImpl.TASK_VERIFY_PRODUCE);
		return getTasksOfUserByTaskName(userId, taskName);
	}
	
	//====================================财务部==============================================
	public List<Task> getConfirmDepositTasks(String userId){
		String taskName = getTaskNameInBPMN(FinanceServiceImpl.TASK_CONFIRM_DEPOSIT);
		return getTasksOfUserByTaskName(userId, taskName);
	}
	
	public List<Task> getConfirmFinalPaymentTasks(String userId){
		String taskName = getTaskNameInBPMN(FinanceServiceImpl.TASK_CONFIRM_FINAL_PAYMENT);
		return getTasksOfUserByTaskName(userId, taskName);
	}
	
	public List<Task> getConfirmSampleMoneyTasks(String userId){
		String taskName = getTaskNameInBPMN(FinanceServiceImpl.TASK_CONFIRM_SAMPLE_MONEY);
		return getTasksOfUserByTaskName(userId, taskName);
	}
	
	public List<Task> getReturnDepositTasks(String userId){
		String taskName = getTaskNameInBPMN(FinanceServiceImpl.TASK_RETURN_DEPOSIT);
		return getTasksOfUserByTaskName(userId, taskName);
	}
	
	//====================================物流部================================================
	public List<Task> getReceiveSampleTasks(String userId){
		String taskName = getTaskNameInBPMN(LogisticsServiceImpl.TASK_RECEIVE_SAMPLE);
		return getTasksOfUserByTaskName(userId, taskName);
	}
	
	public List<Task> getSendClothesTasks(String userId){
		String taskName = getTaskNameInBPMN(LogisticsServiceImpl.TASK_SEND_CLOTHES);
		return getTasksOfUserByTaskName(userId, taskName);
	}

	public List<Task> getSendSampleTasks(String userId){
		String taskName = getTaskNameInBPMN(LogisticsServiceImpl.TASK_SEND_SAMPLE);
		return getTasksOfUserByTaskName(userId, taskName);
	}
	
	public List<Task> getWarehouseTasks(String userId){
		String taskName = getTaskNameInBPMN(LogisticsServiceImpl.TASK_WAREHOUSE);
		return getTasksOfUserByTaskName(userId, taskName);
	}
	
	public List<Task> getWarehouseHaoduoyiTasks(String userId){
		String taskName = getTaskNameInBPMN(LogisticsServiceImpl.TASK_WAREHOUSE_HAODUOYI);
		return getTasksOfUserByTaskName(userId, taskName);
	}
	
	//=======================================质检部==============================================
	public List<Task> getCheckQualityTasks(String userId){
		String taskName = getTaskNameInBPMN(QualityServiceImpl.TASK_CHECK_QUALITY);
		return getTasksOfUserByTaskName(userId, taskName);
	}
	
	//=======================================毛衣制作部===========================================
	public List<Task> getConfrimSweaterSampleAndCraftTasks(String userId){
		String taskName = getTaskNameInBPMN(SweaterMakeServiceImpl.TASK_CONFIRM_SWEATER_SAMPLE_AND_CRAFT);
		return getTasksOfUserByTaskName(userId, taskName);
	}
	
	public List<Task> getSendSweaterTasks(String userId){
		String taskName = getTaskNameInBPMN(SweaterMakeServiceImpl.TASK_SEND_SWEATER);
		return getTasksOfUserByTaskName(userId, taskName);
	}
	
	private String getTaskNameInBPMN(String taskName){
		return getTaskNameMap().get(taskName);
	}
	/**
	 * 从流程图中获取到的taskname为中文，而前端需要英文名<span class..>，因此做了个映射
	 * @return
	 */
	private Map<String, String> getTaskNameMap(){
		Map<String, String> nameMap = new HashMap<>();
		//BuyService
		nameMap.put(BuyServiceImpl.TASK_VERIFY_PURCHASE,"采购验证");
		nameMap.put(BuyServiceImpl.TASK_COMPUTE_PURCHASE_COST,"采购成本验证并核算");
		nameMap.put(BuyServiceImpl.TASK_PURCHASE_SAMPLE_MATERIAL,"样衣面料采购");
		nameMap.put(BuyServiceImpl.TASK_CONFIRM_PURCHASE,"采购确认");
		nameMap.put(BuyServiceImpl.TASK_PURCHASE_MATERIAL,"大货面料采购确认");
		nameMap.put(BuyServiceImpl.TASK_BUY_SWEATER_MATERIAL,"购买组织原料");
		//DesignService
		nameMap.put(DesignServiceImpl.TASK_VERIFY_DESIGN,"设计验证");
		nameMap.put(DesignServiceImpl.TASK_COMPUTE_DESIGN_COST,"设计工艺验证");
		nameMap.put(DesignServiceImpl.TASK_UPLOAD_DESIGN, "录入版型数据及生产样衣");
		nameMap.put(DesignServiceImpl.TASK_MODIFY_DESIGN, "修改设计");
		nameMap.put(DesignServiceImpl.TASK_CONFIRM_DESIGN, "确认设计");
		nameMap.put(DesignServiceImpl.TASK_CRAFT_SAMPLE, "样衣工艺制作");
		nameMap.put(DesignServiceImpl.TASK_CRAFT_PRODUCT, "大货工艺制作");
		nameMap.put(DesignServiceImpl.TASK_TYPESETTING_SLICE, "排版切片");
		nameMap.put(DesignServiceImpl.TASK_CONFIRM_CAD, "确认版型");
		//FinanceService
		nameMap.put(FinanceServiceImpl.TASK_CONFIRM_SAMPLE_MONEY, "确认样衣制作金");
		nameMap.put(FinanceServiceImpl.TASK_CONFIRM_DEPOSIT, "30%定金确认");
		nameMap.put(FinanceServiceImpl.TASK_RETURN_DEPOSIT, "退还定金");
		nameMap.put(FinanceServiceImpl.TASK_CONFIRM_FINAL_PAYMENT, "尾款金额确认");
		//LogisticsService
		nameMap.put(LogisticsServiceImpl.TASK_RECEIVE_SAMPLE, "收取样衣");
		nameMap.put(LogisticsServiceImpl.TASK_SEND_SAMPLE, "样衣发货");
		nameMap.put(LogisticsServiceImpl.TASK_WAREHOUSE, "入库");
		nameMap.put(LogisticsServiceImpl.TASK_WAREHOUSE_HAODUOYI, "好多衣入库");
		nameMap.put(LogisticsServiceImpl.TASK_SEND_CLOTHES, "发货");
		//MarketService
		nameMap.put(MarketServiceImpl.TASK_MODIFY_ORDER, "修改询单");
		nameMap.put(MarketServiceImpl.TASK_MERGE_QUOTE, "专员合并报价");
		nameMap.put(MarketServiceImpl.TASK_VERIFY_QUOTE, "主管审核报价");
		nameMap.put(MarketServiceImpl.TASK_VERIFY_ALTER, "verifyAlter");
		nameMap.put(MarketServiceImpl.TASK_CONFIRM_QUOTE, "商定报价");
		nameMap.put(MarketServiceImpl.TASK_MODIFY_QUOTE, "修改报价");
		nameMap.put(MarketServiceImpl.TASK_CONFIRM_PRODUCE_ORDER, "确认加工单并签订合同");
		nameMap.put(MarketServiceImpl.TASK_MODIFY_PRODUCE_ORDER, "修改加工订单");
		nameMap.put(MarketServiceImpl.TASK_SIGN_CONTRACT, "签订合同");
		nameMap.put(MarketServiceImpl.TASK_PUSH_REST, "催尾款");
		//ProduceService
		nameMap.put(ProduceServiceImpl.TASK_VERIFY_PRODUCE, "生产验证");
		nameMap.put(ProduceServiceImpl.TASK_COMPUTE_PRODUCE_COST, "生产成本验证并核算");
		nameMap.put(ProduceServiceImpl.TASK_PRODUCE_SAMPLE, "生产样衣");
		nameMap.put(ProduceServiceImpl.TASK_PRODUCE, "批量生产");
		//QualityService
		nameMap.put(QualityServiceImpl.TASK_CHECK_QUALITY, "质量检测");
		//SweaterMakeService
		nameMap.put(SweaterMakeServiceImpl.TASK_CONFIRM_SWEATER_SAMPLE_AND_CRAFT, "打小样&制作工艺&制版打样&样衣确认");
		nameMap.put(SweaterMakeServiceImpl.TASK_SEND_SWEATER, "外发");
		return nameMap;
	}
}
