<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>

<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
			<!--  如果是其它页面，这里是填充具体的内容。 -->
			<ul class="nav nav-tabs detail" id="tab">
				<li class="task-name">签订合同</li>
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
			</div>
		</div>
		<form action="${ctx}/market/signContractSubmit.do" method="post" enctype="multipart/form-data">
			
				<input type="hidden" name="taskId" value="${orderInfo.task.id}" />
				<input type="hidden" name="orderId" value="${orderInfo.order.orderId }" />
				<table class="table table-striped table-bordered table-hover detail">
					<tr>
						<td>优惠金额</td>
						<td>总金额</td>
						<td>上传合同</td>
					</tr>
					<tr>
						<td><input name="discount" type="text" /><input name="sum" type="hidden" value="${orderInfo.quote.outerPrice*orderInfo.order.askAmount }"/></td>
						<td><input name="totalmoney" type="text" value="${orderInfo.quote.outerPrice*orderInfo.order.askAmount }" readonly="readonly" /></td>
						<td><input name="contractFile" type="file" value="选择文件" required="required"/></td>
					</tr>
				</table>
		    <button class="btn btn-primary" onclick="history.back();">返回</button>
			<div class="action">
				<button class="btn btn-primary btn-rounded">
					<i class="icon-ok icon-white"></i>签订
				</button>
			</div>
		</form>
		<!--row-fluid-->

	</div>
	<!--maincontentinner-->
	<div class="footer">
		<div class="footer-left">
			<span>&copy; 2014. 江苏南通智造链有限公司.</span>
		</div>
	</div>
	<!--footer-->
</div>
<!--maincontent-->




<link rel="stylesheet" href="${ctx}/css/order/add_order.css">
<script type="text/javascript" src="${ctx}/js/order/add_product.js"></script>
<script type="text/javascript" src="${ctx}/js/order/add_produce.js"></script>
<%@include file="/common/js_file.jsp"%>
<%@include file="/common/js_form_file.jsp"%>
<link rel="stylesheet" href="${ctx}/css/fmc/table.css">
<script type="text/javascript" src="${ctx}/js/fmc/table.js"></script>
<link rel="stylesheet" href="${ctx}/css/fmc/detail.css">
<script type="text/javascript" src="${ctx}/js/order/add_order.js"></script>
<script type="text/javascript" src="${ctx }/js/custom.js"></script>
<%@include file="/common/footer.jsp"%>
