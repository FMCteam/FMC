<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>

<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
			<!--  如果是其它页面，这里是填充具体的内容。 -->
			<div class="widget">
				<h4 class="widgettitle">确认定金</h4>
				<div class="widgetcontent">
					<form class="stdform" id="verify_form" method="post"
						action="${ctx }/finance/doConfirmDepositDetail.do">
						<p>
							<label>订单号</label> <span class="field"><input type="text" name="order_id" 
							class="input-large" value="${orderModel.order.orderId }" readonly />
						</p>
						<p>
							<label>汇款金额</label> <span class="field"><input type="text"
								name="money_amount" class="input-large" placeholder="汇款金额" />&nbsp;<span
								style="color:#ff0000;">*</span>(必填)</span>
						</p>
						<p>
							<label>汇款状态</label> <span class="field"> <select name="money_state"
								class="uniformselect">
									<option value="已收到">已收到</option>
									<option value="未收到">未收到</option>
							</select> &nbsp;<span style="color:#ff0000;">*</span>(必填)
							</span>
						</p>
						<p>
							<label>汇款类型</label> <span class="field"><input type="text"
								name="money_type" class="input-large" placeholder="汇款类型" />&nbsp;<span
								style="color:#ff0000;">*</span>(必填)</span>
						</p>
						<p>
							<label>汇款银行</label> <span class="field"><input type="text"
								name="money_bank" class="input-large" placeholder="汇款银行" /></span>
						</p>
						<p>
							<label>汇款人姓名</label> <span class="field"><input type="text"
								name="money_name" class="input-large" placeholder="汇款人姓名" /></span>
						</p>
						<p>
							<label>汇款人卡号</label> <span class="field"><input type="text"
								name="money_number" class="input-large" placeholder="汇款人卡号" /></span>
						</p>
						<p>
							<label>备注</label> <span class="field"><input
								type="text" name="money_remark" class="input-large"
								placeholder="备注" /></span>
						</p>
						<p>
							<input type="hidden" name="orderId" value="${orderModel.order.orderId }" />
							<input type="hidden" name="taskId" value="${orderModel.taskId }" />
							<input type="hidden" name="pinId" value="${orderModel.processInstanceId }" />
						</p>
						<p class="stdformbutton">
							<button class="btn btn-primary">确认</button>
						</p>
					</form>
				</div>
				<!--widgetcontent-->
			</div>

		</div>
		<!--row-fluid-->



		<div class="footer">
			<div class="footer-left">
				<span>&copy; 2014. 江苏南通智造链有限公司.</span>
			</div>
		</div>
		<!--footer-->

	</div>
	<!--maincontentinner-->
</div>
<!--maincontent-->



<%@include file="/common/js_file.jsp"%>
<%@include file="/common/js_form_file.jsp"%>
<link rel="stylesheet" href="${ctx}/css/order/add_order.css">
<script type="text/javascript" src="${ctx}/js/order/add_order.js"></script>
<script type="text/javascript" src="${ctx }/js/custom.js"></script>
<%@include file="/common/footer.jsp"%>
