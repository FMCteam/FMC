
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%@include file="/common/header.jsp"%>


<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">

			<!--  如果是其它页面，这里是填充具体的内容。 -->
			<section class="list">
				<table class="list">
					<caption>
						<span class="text-vertical">待入库列表:<span class="number">${fn:length(list)}</span>件任务
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
						<c:forEach var="model" items="${packageList}">
							<tr>
								<td>${model.order.orderId}</td>
								<td>${model.employee.employeeName}</td>
								<td>${model.order.customerName}</td>
								<td>${model.order.customerCompany}</td>
								<td>${model.task.createdOn}</td>
								<td><a
									href="${ctx}/logistics/warehouseDetail.do?orderId=${model.order.orderId}&warehouse=0">装箱</a>
								</td>
							</tr>
						</c:forEach>
						<c:forEach var="model" items="${warehouseList}">
							<tr>
								<td>${model.order.orderId}</td>
								<td>${model.employee.employeeName}</td>
								<td>${model.order.customerName}</td>
								<td>${model.order.customerCompany}</td>
								<td>${model.task.createdOn}</td>
								<td><a
									href="${ctx}/logistics/warehouseDetail.do?orderId=${model.order.orderId}&warehouse=1">入库</a>
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

