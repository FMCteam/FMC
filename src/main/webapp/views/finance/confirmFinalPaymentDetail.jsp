<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>

<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
			<!--  如果是其它页面，这里是填充具体的内容。 -->

			<ul class="nav nav-tabs detail" id="tab">
				<li class="task-name">大货生产定金确认</li>
				<li><a href="#sampleMoney" data-toggle="tab">大货定金</a></li>
				<li><a href="#quote" data-toggle="tab">报价信息</a></li>
				<li><a href="#cad" data-toggle="tab">版型信息</a></li>
				<li><a href="#produce" data-toggle="tab">加工信息</a></li>
				<li><a href="#sample" data-toggle="tab">样衣信息</a></li>
				<li><a href="#material" data-toggle="tab">面辅信息</a></li>
				<li class="active"><a href="#basic" data-toggle="tab">基本信息</a></li>
			</ul>

			<div class="tab-content">
				<div class="tab-pane active" id="basic">
					<%@include file="/views/common/basic.jsp"%>
				</div>
				<div class="tab-pane" id="material">
					<%@include file="/views/common/material.jsp"%>
				</div>
				<div class="tab-pane" id="sample">
					<%@include file="/views/common/sample.jsp"%>
				</div>
				<div class="tab-pane" id="produce">
					<%@include file="/views/common/produce.jsp"%>
				</div>
				<div class="tab-pane" id="cad">
					<%@include file="/views/common/cad.jsp"%>
				</div>
				<div class="tab-pane" id="quote">
					<%@include file="/views/common/quote.jsp"%>
				</div>
				<div class="tab-pane" id="sampleMoney">
					<form action="${ctx}/finance/confirmFinalPaymentSubmit.do"
						method="post" onSubmit="return verify()">
						<input type="hidden" name="money_state" value="已收到" /> <input
							type="hidden" name="money_type" value="大货尾款" /> <input
							type="hidden" name="orderId" value="${orderInfo.order.orderId}" />
						<input type="hidden" name="taskId" value="${orderInfo.task.id}" /><input
							type="hidden" name="result" value="1" />

						<table class="table table-bordered detail">
							<tr>
								<td rowspan="2">样衣信息</td>
								<td>金额类型</td>
								<td>样衣件数</td>
								<td>制版单价</td>
								<td>应收金额</td>
							</tr>
							<tr>
								<td>70%尾款</td>
								<td>10</td>
								<td>100</td>
								<td>700</td>
							</tr>
							<tr>
								<td rowspan="3">汇款信息</td>
								<td>汇款人</td>
								<td>汇款卡号</td>
								<td>汇款银行</td>
								<td>汇款金额</td>
							</tr>
							<tr>
								<td><input type="text" name="money_name" /></td>
								<td><input type="text" name="money_number" /></td>
								<td><input type="text" name="money_bank" /></td>
								<td><input type="text" name="money_amount" /></td>
							</tr>
							<tr>
								<td>备注</td>
								<td colspan="3"><input type="text" name="money_remark"
									class="span12" /></td>
							</tr>
							<tr>
								<td>操作</td>
								<td colspan="4"><input class="btn btn-primary" type="submit" value="确认收款" /> <a class="btn btn-primary"
									href="${ctx}/finance/confirmFinalPaymentSubmit.do?orderId=${orderInfo.order.orderId}&taskId=${orderInfo.task.id}&result=0"
									onclick="return actionConfirm()">未收到汇款</a></td>
							</tr>
						</table>
					</form>
				</div>
			</div>
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
<script>
	function actionConfirm() {
		return confirm("确认操作？");
	}

	function verify() {
		var money_name = $("input[name='money_name']").val();
		var money_number = $("input[name='money_number']").val();
		var money_bank = $("input[name='money_bank']").val();
		var money_amount = $("input[name='money_amount']").val();
		var money_remark = $("input[name='money_remark']").val();
		if (money_name == "" || money_number == "" || money_bank == ""
				|| money_amount == "" || money_remark == "") {
			alert("数据不能为空");
			return false;
		}
		return actionConfirm();
	}
</script>
<%@include file="/common/js_file.jsp"%>
<%@include file="/common/js_form_file.jsp"%>
<link rel="stylesheet" href="${ctx}/css/order/add_order.css">
<link rel="stylesheet" href="${ctx}/css/fmc/detail.css">
<script type="text/javascript" src="${ctx }/js/custom.js"></script>
<%@include file="/common/footer.jsp"%>
