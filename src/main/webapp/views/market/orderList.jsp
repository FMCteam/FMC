<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>


<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
			<section class="list">
				<table class="list">
					<caption>
						<span class="text-vertical">${taskName}:<span
							class="number">${fn:length(list)}</span>条订单
						</span><input type="text" class="search-query float-right"
							placeholder="输入检索条件">
					</caption>
					<thead>
						<tr>
							<th>订单号</th>
							<th>市场专员</th>
							<th>客户姓名</th>
							<th>客户公司</th>
							<th>任务开始时间</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="model" items="${list}">
							<tr>
								<td>${model.order.orderId}</td>
								<td>${model.employee.employeeName}</td>
								<td>${model.order.customerName}</td>
								<td>${model.order.customerCompany}</td>
								<td>${model.order.orderTime}</td>
								<td><a href="${ctx}${url}?orderId=${model.order.orderId}">详情</a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</section>
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
<!-- 这里引入你需要的js文件 -->
<link rel="stylesheet" href="${ctx}/css/fmc/table.css">
<script type="text/javascript" src="${ctx}/js/fmc/table.js"></script>
<script type="text/javascript" src="${ctx }/js/custom.js"></script>
<%@include file="/common/footer.jsp"%>

