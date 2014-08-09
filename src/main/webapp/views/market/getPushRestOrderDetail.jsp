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
 
					<form id="verify_form" name="verify_form" action="${ctx}/market/getPushRestOrderSubmit.do" enctype="multipart/form-data"
						method="post"  onsubmit="return getPushRestOrderDetailSubmit(verify_form.confirmFinalPaymentFile.value);">
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
								<td class="title" style="color: red;">应收金额</td>
							</tr>
							<tr>
								<td>${orderInfo.moneyName}</td>
								<td>${orderInfo.order.discount}</td>
								<td><span id="allMoneyOfProducts">${(orderInfo.number)*orderInfo.price}</span>（大货总价）-${orderInfo.order.discount}（优惠金额）-${orderInfo.deposit}（定金）=<span id="FinalMoneyShouldPay">${(orderInfo.number)*orderInfo.price-orderInfo.order.discount-orderInfo.deposit}</span></td>
							</tr>
							<tr>
								<td class="title">实际大货件数</td>
								<td class="title">大货单价</td>
								<td class="title">大货总价</td>
							</tr>
							<tr>
								<td>${orderInfo.number}</td>
								<td>${orderInfo.price}</td>
								<td><span id="allMoneyOfAllProducts">${(orderInfo.number)*orderInfo.price}</span></td>
							</tr>
							<tr>
								<td class="title">样衣件数</td>
								<td class="title">样衣单价</td>
								<td class="title">样衣总价</td>
							</tr>
							<tr>
								<td>${orderInfo.order.sampleAmount}</td>
								<td>${orderInfo.samplePrice}</td>
								<td><span id="allMoneyOfSamples">${orderInfo.order.sampleAmount*orderInfo.samplePrice}</span></td>
							</tr>
						    <tr>
						        <td class="title" style="color:red; ">选择尾金截图文件</td>
						        <td colspan="3">
							       <a style="color: red;">*</a>
			                       <input type="hidden" name="processId" value="${orderInfo.task.processInstanceId}" />
							       <input name="confirmFinalPaymentFile" id="confirmFinalPaymentFile" type="file" required="required"/> 
							       <!-- 
							       <input  class="btn btn-primary btn-rounded" type="submit" value="上传尾金截图" onclick="return confirm('确认上传？')" />						
							        -->
						       </td>
					       </tr>
					<!-- 
							<tr>
		                        <td class="title">收款信息</td>
		                        <td class="title">收款图片</td>
		                        <td colspan="3"><c:if test="${orderInfo.order.confirmFinalPaymentFile!=null}">
				                <img src="${ctx}/common/getPic.do?type=confirmFinalPaymentFile&orderId=${orderInfo.order.orderId}"
					                 style="max-height: 300px;" alt="收款图片"></img>
			                    </c:if></td>
	                        </tr>
					 -->
						</table>
						<a href="${ctx}${orderInfo.url}?orderId=${orderInfo.order.orderId}&taskId=${orderInfo.task.id}&result=0" 
						   class="btn btn-danger btn-rounded"
						   onclick="return confirmFinanceSubmit()"
						   style="color: white">
						   <i class="icon-remove icon-white"></i>催尾款失败</a>
						<div class="action" style="float:right">
							<input type="submit" id="financeSubmit" hidden="hidden" /> 
							<a  id="financeButton" class="btn btn-primary btn-rounded">
								<i class="icon-ok icon-white"></i>已确认收款</a> 
						</div>
						<!-- 
						 -->
					</form>
					<!-- 
				<form action="${ctx}/market/confirmFinalPaymentFileSubmit.do?orderId=${orderInfo.order.orderId}" method="post" enctype="multipart/form-data">
				<table class="table table-striped table-bordered table-hover detail">
					<tr>
						<td>选择尾金截图文件</td>
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
 var text=$("#FinalMoneyShouldPay").text();
	$("#FinalMoneyShouldPay").text(parseFloat(text).toFixed(2));
 var text=$("#allMoneyOfProducts").text();
	$("#allMoneyOfProducts").text(parseFloat(text).toFixed(2));
 var text=$("#allMoneyOfAllProducts").text();
	$("#allMoneyOfAllProducts").text(parseFloat(text).toFixed(2));
	
 var text=$("#allMoneyOfSamples").text();
	$("#allMoneyOfSamples").text(parseFloat(text).toFixed(2));	 
});
function getPushRestOrderDetailSubmit(fileValue) {
     if(fileValue!=""){
    	 
     return	 confirmFinanceSubmit();   
     }else{
     alert("请选择文件");     
	 return false;
     }
     
}

</script>

<%@include file="/common/js_file.jsp"%>
<%@include file="/common/js_form_file.jsp"%>
<link rel="stylesheet" href="${ctx}/css/order/add_order.css">
<link rel="stylesheet" href="${ctx}/views/finance/finance.css">
<link rel="stylesheet" href="${ctx}/css/fmc/detail.css">
<script type="text/javascript" src="${ctx }/js/custom.js"></script>
<script type="text/javascript" src="${ctx}/views/finance/finance.js"></script>
<%@include file="/common/footer.jsp"%>
