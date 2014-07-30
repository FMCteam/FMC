<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>

<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
			<!--  如果是其它页面，这里是填充具体的内容。 -->

			<ul class="nav nav-tabs detail" id="tab">
				<li class="task-name">${orderInfo.taskName}</li>
				<li class="active"><a href="#finance" data-toggle="tab">${orderInfo.tabName}</a></li>
				<li><a href="#quote" data-toggle="tab">报价信息</a></li>
				<li><a href="#cad" data-toggle="tab">版型信息</a></li>
				<li><a href="#produce" data-toggle="tab">加工信息</a></li>
				<li><a href="#sample" data-toggle="tab">样衣信息</a></li>
				<li><a href="#material" data-toggle="tab">面辅信息</a></li>
				<li><a href="#basic" data-toggle="tab">基本信息</a></li>
			</ul>

			<div class="tab-content">
				<div class="tab-pane" id="basic">
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
				<div class="tab-pane  active" id="finance">
				<!-- 
				 -->
					<form id="verify_form" action="${ctx}${orderInfo.url}"
						method="post" onsubmit="return verifyFinance();">
						<input type="hidden" name="money_state" value="已收到" /> <input
							id="verify_val" type="hidden" name="val" value="已收到" /> <input
							type="hidden" name="money_type" value="${orderInfo.type}" /> <input
							type="hidden" name="orderId" value="${orderInfo.order.orderId}" />
						<input type="hidden" name="taskId" value="${orderInfo.taskId}" /><input
							type="hidden" name="result" value="1" />

						<table class="table table-bordered detail finance">
							<tr>
								<td class="span2 title" rowspan="6">费用信息</td>
								<td class="title">金额类型</td>
								<td class="title">优惠金额</td>
								<td class="title">应收金额</td>
							</tr>
							<tr>
								<td>${orderInfo.moneyName}</td>
								<td>${orderInfo.order.discount}</td>
								<td>${(orderInfo.number)*orderInfo.price}-${orderInfo.order.discount}-${orderInfo.deposit}=<span id="shouldFinalPay">${(orderInfo.number)*orderInfo.price-orderInfo.order.discount-orderInfo.deposit}</span></td>
							</tr>
							<tr>
								<td class="title">实际大货件数</td>
								<td class="title">大货单价</td>
								<td class="title">大货总价</td>
							</tr>
							<tr>
								<td>${orderInfo.number}</td>
								<td>${orderInfo.price}</td>
								<td>${(orderInfo.number)*orderInfo.price}</td>
							</tr>
							<tr>
								<td class="title">样衣件数</td>
								<td class="title">样衣单价</td>
								<td class="title">样衣总价</td>
							</tr>
							<tr>
								<td>${orderInfo.order.sampleAmount}</td>
								<td>${orderInfo.samplePrice}</td>
								<td>${orderInfo.order.sampleAmount*orderInfo.samplePrice}</td>
							</tr>
							<tr>
								<td class="title" rowspan="5">汇款信息</td>
								<td class="title">汇款人<span style="color: red">*</span></td>
								<td class="title">汇款卡号</td>
								<td class="title">汇款银行<span style="color: red">*</span></td>

							</tr>
							<tr>
								<td><input type="text" name="money_name"
									required="required" /></td>
								<td><input type="text" name="money_number"
									 /></td>
								<td><input type="text" name="money_bank"
									required="required" /></td>

							</tr>
							<tr>
								<td class="title">汇款金额</td>
								<td class="title">收款账号<span style="color: red">*</span></td>
								<td class="title">收款时间<span style="color: red">*</span></td>

							</tr>
							<tr>
								<td><input type="text" name="money_amount"
									readonly="readonly" value="${((orderInfo.number+orderInfo.order.sampleAmount)*orderInfo.price-orderInfo.order.sampleAmount*orderInfo.samplePrice-orderInfo.order.discount)*0.7}" /></td>
								<td>
								<select name="account" required="required" >
										<option selected="selected">36933145@qq.com</option>
										<option>6228480424649506013</option>
										<option>872104037@qq.com</option>
								</select>
								</td>
								<td><input type="date" required="required" name="time" /></td>

							</tr>
							<tr>
								<td class="title" colspan="1">备注</td>
								<td colspan="2"><input type="text" name="money_remark"
									class="span12" /></td>
							</tr>
		                    <tr>
		                    <!-- 
		                        <td class="title">收款信息</td>
		                     -->
		                        <td class="title">收款图片</td>
		                        <td colspan="3"><c:if test="${orderInfo.order.confirmFinalPaymentFile!=null}">
				                <img src="${ctx}/common/getPic.do?type=confirmFinalPaymentFile&orderId=${orderInfo.order.orderId}"
					                 style="max-height: 300px;" alt="收款图片"></img>
			                    </c:if></td>
	                        </tr>
						</table>
						

						<div class="action">
							<input type="submit" id="financeSubmit" hidden="hidden" /> 
					
							<a  
								id="financeButton" class="btn btn-primary btn-rounded"><i
								class="icon-ok icon-white"></i>已确认收款</a> 
								
								<a
								class="btn btn-danger btn-rounded"
								href="${ctx}${orderInfo.url}?orderId=${orderInfo.order.orderId}&taskId=${orderInfo.task.id}&result=0"
								onclick="return confirmFinanceSubmit()"
								style="color: white; margin-left: 20px"><i
								class="icon-remove icon-white"></i>未收到汇款</a>
						</div>
					</form>
					
						<!-- 
				<form action="${ctx}/finance/confirmFinalPaymentFileSubmit.do?orderId=${orderInfo.order.orderId}" method="post" enctype="multipart/form-data">
				<table class="table table-striped table-bordered table-hover detail">
					<tr>
						<td>选择文件</td>
						<td colspan="3">
							<a style="color: red;">*</a>
			                <input type="hidden" name="processId" value="${orderInfo.task.processInstanceId}" />
							<input name="confirmFinalPaymentFile" id="confirmFinalPaymentFile" type="file" required="required"/> 
							<input  class="btn btn-primary btn-rounded" type="submit" value="上传尾金截图" onclick="return confirm('确认上传？')" />						
						</td>
					</tr>
				</table>
				</form>
						 -->

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
<script type="text/javascript">
$(document).ready(function() {
 var text=$("#shouldFinalPay").text();
	$("#shouldFinalPay").text(parseFloat(text).toFixed(2));

	$("a#financeButton").click(function() {
		$("input#financeSubmit").click();
	});
//	var text=$("#pay").text();
//	$("#pay").text(parseFloat(text).toFixed(2));
//	$("input[name='money_amount']").val(parseFloat(text).toFixed(2));

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
});  
</script>

<%@include file="/common/js_file.jsp"%>
<%@include file="/common/js_form_file.jsp"%>
<link rel="stylesheet" href="${ctx}/css/order/add_order.css">
<link rel="stylesheet" href="${ctx}/views/finance/finance.css">
<link rel="stylesheet" href="${ctx}/css/fmc/detail.css">
<script type="text/javascript" src="${ctx }/js/custom.js"></script>
<!-- 
<script type="text/javascript" src="${ctx}/views/finance/finance.js"></script>
 -->
<%@include file="/common/footer.jsp"%>
