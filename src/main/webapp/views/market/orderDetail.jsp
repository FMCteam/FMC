<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>

<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
			<!--  如果是其它页面，这里是填充具体的内容。 -->

			<ul class="nav nav-tabs detail" id="tab">
				<li class="task-name">订单详情</li>
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
			<c:if test="${orderInfo.order.orderState!='1'}">
			<div class="push-order" style="float:left">
				
				<c:if test="${role=='ADMIN' || role=='marketManager' || role=='marketStaff'}">
				<a class="btn btn-primary" 
					href="${ctx}/order/pushOrderInfo.do?orderId=${orderInfo.order.orderId}">推送订单信息</a>
				</c:if>
				<a class="btn btn-primary"
					href="${ctx}/image.do?orderId=${orderInfo.order.orderId}">订单进度</a>
			</div>
			<div class="action" style="float:right">
				
				<c:if test="${role=='ADMIN'}">
					<a class="btn btn-danger" style="color: white" onclick="return confirm('确定终止订单？');"
						href="${ctx}/order/end.do?orderId=${orderInfo.order.orderId}">终止订单</a>
				</c:if>
			</div>
			<br><br>
			<button class="btn btn-primary" onclick="history.back();">返回</button>
			</c:if>
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
<link rel="stylesheet" href="${ctx}/views/finance/finance.css">
<link rel="stylesheet" href="${ctx}/css/fmc/detail.css">
<script type="text/javascript" src="${ctx }/js/custom.js"></script>
<script type="text/javascript" src="${ctx}/views/finance/finance.js"></script>
<%@include file="/common/footer.jsp"%>
