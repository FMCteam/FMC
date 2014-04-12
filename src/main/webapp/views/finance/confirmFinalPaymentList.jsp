<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>

<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
			<!--  如果是其它页面，这里是填充具体的内容。 -->
			<section class="list">
				<table class="list">
					<caption>
						<span class="text-vertical">尾款确认列表:<span class="number">${fn:length(list)}</span>件任务
						</span><input type="text" class="search-query float-right"
							placeholder="输入检索条件">
					</caption>
					<thead>
						<tr>
							<th class="head0">订单号</th>
							<th class="head1">业务员</th>
							<th class="head0">客户姓名</th>
							<th class="head1">客户公司</th>
							<th class="head0">款式</th>
							<th class="head1">件数</th>
							<th class="head0">交货时间</th>
							<th class="head1">操作</th>
							<th class="head0">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="task" items="${list}">
							<tr class="gradeA">
								<td>${task.order.orderId}</td>
								<td>${task.order.employeeId}</td>
								<td>${task.order.customerName}</td>
								<td>${task.order.customerCompany}</td>
								<td>${task.order.styleName}</td>
								<td>${task.order.askAmount}</td>
								<td>${fn:substring(task.order.askDeliverDate,0,10)}</td>
								<td><a
									href="${ctx}/finance/confirmFinalPaymentDetail.do?orderId=${task.order.orderId}">详情</a>
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

