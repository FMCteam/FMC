<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>

<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
			<!--  如果是其它页面，这里是填充具体的内容。 -->

			<ul class="nav nav-tabs detail" id="tab">
				<li class="task-name">认领订单</li>
				<li><a href="#cad" data-toggle="tab">版型信息</a></li>
				<li><a href="#produce" data-toggle="tab">加工信息</a></li>
				<li><a href="#sample" data-toggle="tab">样衣信息</a></li>
				<li class="active"><a href="#basic" data-toggle="tab">基本信息</a></li>
			</ul>

			<div class="tab-content">
				<div class="tab-pane active" id="basic">
					<%@include file="/views/common/basic.jsp"%>
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
			</div>
			<br>
			<br>
			<button class="btn btn-primary" onclick="history.back();">返回</button>
			<a id="agree" class="btn btn-primary btn-rounded" href="/market/claimCustomerOrderSubmit.do"
				style="float: right;"> <i class="icon-ok icon-white"></i> 认领
			</a>

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
<link rel="stylesheet" href="${ctx}/css/fmc/detail.css">
<script type="text/javascript" src="${ctx }/js/custom.js"></script>
<script type="text/javascript">
	$(function() {
		var date = new Date();
		var month = date.getMonth() > 8 ? date.getMonth() + 1 : "0"
				+ (date.getMonth() + 1);
		var day = date.getDate() > 9 ? date.getDate() : "0" + date.getDate();
		var hour = date.getHours() > 9 ? date.getHours() : "0"
				+ date.getHours();
		var minute = date.getMinutes() > 9 ? date.getMinutes() : "0"
				+ date.getMinutes();
		var second = date.getSeconds() > 9 ? date.getSeconds() : "0"
				+ date.getSeconds();
		$("#input_day").val(
				date.getFullYear() + "/" + month + "/" + day + " " + hour + ":"
						+ minute + ":" + second);

	});
</script>
<%@include file="/common/footer.jsp"%>
