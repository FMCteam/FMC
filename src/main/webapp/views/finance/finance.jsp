<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<form id="verify_form" action="${ctx}${orderInfo.url}" method="post"
	onsubmit="return verifyFinance();">
	<input type="hidden" name="money_state" value="已收到" /> <input
		id="verify_val" type="hidden" name="val" value="已收到" /> <input
		type="hidden" name="money_type" value="${orderInfo.type}" /> <input
		type="hidden" name="orderId" value="${orderInfo.order.orderId}" /> <input
		type="hidden" name="taskId" value="${orderInfo.taskId}" /><input
		type="hidden" name="result" value="1" />

	<table class="table table-bordered detail finance">
		<tr>
			<td class="span2 title" rowspan="2">样衣信息</td>
			<td class="title">金额类型</td>
			<td class="title">件数</td>
			<td class="title">单价</td>
			<td class="title">应收金额</td>
		</tr>
		<tr>
			<td>${orderInfo.moneyName}</td>
			<td>${orderInfo.number}</td>
			<td>${orderInfo.price}</td>
			<td><span id="shouldReceivePay">${orderInfo.total}</span></td>
		</tr>
		<tr>
			<td class="title" rowspan="4">汇款信息</td>
			<td class="title">汇款人<span style="color: red">*</span></td>
			<td class="title">汇款卡号</td>
			<td class="title">汇款银行<span style="color: red">*</span></td>
			<td class="title">汇款金额</td>
		</tr>
		<tr>
			<td><input type="text" name="money_name" required="required" /></td>
			<td><input type="text" name="money_number" /></td>
			<td><input type="text" name="money_bank" required="required"/></td>
			<td><input type="text" name="money_amount"  readonly="readonly" value="${orderInfo.total}"/></td>
		</tr>
		<tr>
			<td class="title">收款时间<span style="color: red">*</span></td>
			<td class="title">收款账号<span style="color: red">*</span></td>
			<td class="title" colspan="2">备注</td>
		</tr>
		<tr>
			<td><input type="text" required="required" name="time"  id="input_day" readonly="readonly"/></td>
			<td>
			<select name="account" required="required" >
			<option selected="selected">36933145@qq.com</option>
			<option>6228480424649506013</option>
			<option>872104037@qq.com</option>
			</select>
			</td>
			<td colspan="2"><textarea  name="money_remark"  id="money_remark"
				class="span12"   readonly="readonly"></textarea></td>
		</tr>
		<tr>
		<td class="title">收款信息</td>
		<td class="title">收款图片</td>
		<td colspan="3"><c:if test="${orderInfo.order.confirmSampleMoneyFile!=null}">
				<img src="${orderInfo.order.confirmSampleMoneyFile}"
					style="max-height: 300px;" alt="收款图片"></img>
			</c:if></td>
	    </tr>
	    <%-- <td class="title">备注信息</td>
		<td colspan="4">
				<input type="text"  name="moneyremark"  readonly="readonly" style="height:40px;width: 1000px" value="${orderInfo.order.moneyremark}" />
		</td>
	    </tr> --%>
	</table>
	<div>
	 <a  class="btn btn-danger btn-rounded"  style="float: left;color: white"
			href="${ctx}${orderInfo.url}?orderId=${orderInfo.order.orderId}&taskId=${orderInfo.task.id}&result=0"
			onclick="return confirmFinanceSubmit()"><i
			class="icon-remove icon-white"></i>未收到汇款</a>
	</div>
		<input type="submit" id="financeSubmit" hidden="hidden" /> <a
			id="financeButton" class="btn btn-primary btn-rounded"  style="float: right;"><i
			class="icon-ok icon-white"></i>已确认收款</a>
		<br><br>
</form>
<script type="text/javascript">
$(document).ready(function(){
	$("#money_remark").val("${orderInfo.order.moneyremark}");
})


function confirmFinanceSubmit() {
	return confirm("确认操作？");
}

function verifyFinance() {
	var money_amount = $("input[name='money_amount']").val();
	if(isNaN(money_amount)){
		noty({
			text : '汇款金额必须是数字',
			layout : 'topCenter',
			timeout : 2000
		});
		return false;
	}
	return confirmFinanceSubmit();
}

	$("a#financeButton").click(function() {
		$("input#financeSubmit").click();
	});
    
	
	var text=$("#shouldReceivePay").text();
	$("#shouldReceivePay").text(parseFloat(text).toFixed(2));
	$("input[name='money_amount']").val(parseFloat(text).toFixed(2));
    

</script>