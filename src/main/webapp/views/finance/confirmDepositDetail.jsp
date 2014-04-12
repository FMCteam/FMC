<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>

<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
			<!--  如果是其它页面，这里是填充具体的内容。 -->
			<div class="widget">
				<h4 class="widgettitle">定金确认</h4>
				<div class="widgetcontent">
					<form action="${ctx}/finance/confirmDepositSubmit.do"
						method="post" onSubmit="return verify()">
						<input type="hidden" name="money_state" value="已收到" /> <input
							type="hidden" name="money_type" value="样衣制作金" /> <input
							type="hidden" name="orderId" value="${orderInfo.order.orderId}" />
						<input type="hidden" name="taskId" value="${orderInfo.task.id}" /><input
							type="hidden" name="result" value="1" />

						<table class="table table-bordered">
							<tr>
								<td rowspan="2">样衣信息</td>
								<td>金额类型</td>
								<td>生产件数</td>
								<td>单件报价</td>
								<td>应收金额</td>
							</tr>
							<tr>
								<td>30%定金</td>
								<td>100</td>
								<td>10</td>
							    <td>300</td>
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
								<td colspan="4"><input type="submit" value="确认收款" /> <a
									href="${ctx}/finance/confirmDepositSubmit.do?orderId=${orderInfo.order.orderId}&taskId=${orderInfo.task.id}&result=0"
									onclick="return actionConfirm()">未收到汇款</a></td>
							</tr>
						</table>
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
<script type="text/javascript" src="${ctx }/js/custom.js"></script>
<%@include file="/common/footer.jsp"%>
