<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<section class="list">
	<table class="list">
		<caption>
			<span class="text-vertical">${taskName}:<span class="number">${fn:length(list)}</span>件任务
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
					<td>${model.task.createdOn}</td>
					<td><a
						href="${ctx}${url}?orderId=${model.order.orderId}">详情</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</section>