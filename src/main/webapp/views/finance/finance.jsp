<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<form action="${ctx}${orderInfo.url}" method="post"
	onsubmit="return verifyFinance()">
	<input type="hidden" name="money_state" value="已收到" /> <input
		type="hidden" name="money_type" value="${orderInfo.type}" /> <input type="hidden"
		name="orderId" value="${orderInfo.order.orderId}" /> <input
		type="hidden" name="taskId" value="${orderInfo.task.id}" /><input
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
			<td>${orderInfo.moneyName}</td>
			<td>${orderInfo.number}</td>
			<td>${orderInfo.price}</td>
			<td>${orderInfo.total}</td>
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
			<td colspan="4"><input class="btn btn-primary" type="submit"
				value="确认收款" /> <a class="btn btn-primary"
				href="${ctx}${orderInfo.url}?orderId=${orderInfo.order.orderId}&taskId=${orderInfo.task.id}&result=0"
				onclick="return confirmFinanceSubmit()">未收到汇款</a></td>
		</tr>
	</table>
</form>