<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>

<div class="maincontent">
	<div class="maincontentinner">
		<form method="post" action="${ctx}/market/mergeQuoteSubmit.do" onSubmit="return quote_verify()">
			<div class="row-fluid" style="min-height:300px;">
				<!--  如果是其它页面，这里是填充具体的内容。 -->
				<ul class="nav nav-tabs detail" id="tab">
					<li class="task-name">合并报价</li>
					<li class="active"><a href="#quote" data-toggle="tab">报价信息</a></li>
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
					<div class="tab-pane  active" id="quote">
						<%@include file="/views/common/quote_w.jsp"%>
					</div>
				</div>
			</div>
			<div class="tab-pane" id="comment">
						<table
							class="table table-striped table-bordered table-hover detail">
							<tr>
							<!-- 
								<td class="title" >主管审核意见</td>
							 -->							
								<td><span>主管审核意见：</span>${verifyQuoteComment}</td>
							</tr>
 
						</table>
					</div>
			<div class="action">
				<input type="hidden" name="taskId" value="${orderInfo.task.id}" />
				<input type="hidden" name="order_id" value="${orderInfo.quote.orderId }" /> 
				<input type="hidden" name="processId" value="${orderInfo.task.processInstanceId}" />
				<button class="btn btn-primary btn-rounded">
					<i class="icon-ok icon-white"></i>确定
				</button>
			</div>
			 <button class="btn btn-primary" onclick="history.back(-1);">返回</button>
			</form>
			<c:if test="${orderInfo.order.orderSource=='好多衣'}">
	 		<a	href="${ctx}/market/printProcurementSampleOrder.do?orderId=${orderInfo.order.orderId}"
								class="btn btn-primary btn-rounded" style="width: 80px;" target="_blank">打印样衣单</a><a style="color: red;font-size: 16px;"> &nbsp亲，别忘了打印样衣单哦！</a>
			</c:if>
		</div>
		<c:if test="${orderInfo.order.orderSource=='好多衣'}">
			<a href="${ctx}/market/printConfirmProcurementOrderHDY.do?orderId=${orderInfo.order.orderId}"  onclick="return check()"  id="printConfirmProcurementOrder"
		 		            style="font-size: 13px; margin-left:20px; padding: 9px 30px 7px; background: #0866c6;border-color: #0a6bce; color: #fff; text-shadow: none;"   target="_blank">打印补货单</a><a style="color: red;font-size: 16px;">亲，请别忘了打印补货单哦！</a>
		</c:if>

	<div class="footer">
		<div class="footer-left">
			<span>&copy; 2014. 江苏南通智造链有限公司.</span>
		</div>
	</div>


</div>





<%@include file="/common/js_file.jsp"%>
<%@include file="/common/js_form_file.jsp"%>
<link rel="stylesheet" href="${ctx}/css/fmc/table.css">
<script type="text/javascript" src="${ctx}/js/fmc/table.js"></script>
<link rel="stylesheet" href="${ctx}/css/fmc/detail.css">
<script type="text/javascript" src="${ctx}/js/order/add_order.js"></script>
<script type="text/javascript" src="${ctx}/js/order/add_quote.js"></script>
<script type="text/javascript" src="${ctx }/js/custom.js"></script>
<%@include file="/common/footer.jsp"%>
