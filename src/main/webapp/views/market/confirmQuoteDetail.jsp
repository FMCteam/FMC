<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>

<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
			<!--  如果是其它页面，这里是填充具体的内容。 -->
			<ul class="nav nav-tabs detail" id="tab">
			<li class="task-name">商定报价</li>
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
				<div class="tab-pane active" id="quote">
					<%@include file="/views/common/quote.jsp"%>
					<form id="confirm_quote_form" action="${ctx}/market/confirmQuoteSubmit.do" method="post"
						enctype="multipart/form-data">
						<table class="table table-striped table-bordered table-hover detail">
							<tr>
								<td>上传收取样衣金截图文件</td>
								<td colspan="3">
									<a style="color: red;">*</a>
									<!-- 
									<input type="hidden" name="orderId" value="${orderInfo.order.orderId }" /> 
									<input type="hidden" name="taskId" value="${orderInfo.taskId }" />
							 		-->
			                		<input type="hidden" name="processId" value="${orderInfo.task.processInstanceId}" />
									<input name="confirmSampleMoneyFile" id="confirmSampleMoneyFile" type="file" required="required"/> 
								</td>
							</tr>
						</table>
						
 					    <div class="action" >
 					    	<input id="cancel_order" type="button" value="取消订单" class="btn btn-danger btn-rounded" />
							<input id="modify_price" type="button" value="修改报价" class="btn btn-primary btn-rounded" style="background-color:#1E90FF" />	
							<input id="confirm_price" type="button" value="确认报价" class="btn btn-primary btn-rounded" />
						</div>
						<input type="hidden" name="taskId" value="${orderInfo.task.id}" />
						<input type="hidden" name="orderId" value="${orderInfo.quote.orderId }" />
						<input type="hidden" name="processId" value="${orderInfo.task.processInstanceId}" />
						<!-- 隐藏标签，判断确认或修改报价，还是取消订单 -->
						<input id="operation_result" type="hidden" name="result" value="0" />
 					</form>
				</div>
			</div>
		</div>
		<button class="btn btn-primary" style="float:left" onclick="history.back();">返回</button>
		<!-- 
		<div class="action" style="float:right">
			<input type="hidden" name="taskId" value="${orderInfo.task.id}" />
			<input type="hidden" name="order_id" value="${orderInfo.quote.orderId }" />
			<input type="hidden" name="processId" value="${orderInfo.task.processInstanceId}" />
			<a href="${ctx}/market/confirmQuoteSubmit.do?result=0&taskId=${orderInfo.task.id}&orderId=${orderInfo.quote.orderId}" 
			class="btn btn-primary btn-rounded"><i class="icon-white"></i>确认</a> 
		</div>
		-->
	</div>


	<div class="footer">
		<div class="footer-left">
			<span>&copy; 2014. 江苏南通智造链有限公司.</span>
		</div>
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
<script>
jQuery(document).ready(function(){
	//确认报价
	jQuery("#confirm_price").click(function(){
		if(confirm('确认报价？')){
			jQuery("#operation_result").val("0");
			jQuery("#confirm_quote_form").submit();
		}
	});
	//修改报价
	jQuery("#modify_price").click(function(){
		if(confirm('确定修改报价？')){
			jQuery("#operation_result").val("1");
			jQuery("#confirm_quote_form").submit();
		}
	});
	//取消订单
	jQuery("#cancel_order").click(function(){
		if(confirm('确定取消订单？')){
			jQuery("#operation_result").val("2");
			jQuery("#confirm_quote_form").submit();
		}
	});
});
</script>

<%@include file="/common/footer.jsp"%>
