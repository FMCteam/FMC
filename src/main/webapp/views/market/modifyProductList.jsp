
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>


<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
			<!--  如果是其它页面，这里是填充具体的内容。 -->
			<section>
				<table id="dyntable" class="table responsive">
					<caption>修改加工单</caption>
					<tr>
						<th class="head0">客户姓名</th>
						<th class="head1">客户电话</th>
						<th class="head1">公司名称</th>
						<th class="head0">公司电话</th>
						<th class="head0">内部报价</th>
						<th class="head0">外部报价</th>
						<th class="head1">操作</th>
					</tr>
					<c:forEach var="task" items="${tasks}">
						<tr class="gradeA">
							<td>${task.order.customerName}</td>
							<td>${task.order.customerPhone1}</td>
							<td>${task.order.customerCompany}</td>
							<td>${task.order.customerCompanyFax}</td>
							<td>${task.taskId}</td>
							<td>${task.processInstanceId}</td>
							<td>
								<a
								href="${ctx}/market/modifyProductDetail.do?id=${task.order.orderId}&tid=${task.taskId}&pid=${task.processInstanceId}">
									修改</a> </td>
						</tr>
					</c:forEach>
				</table>
			</section>

		</div>
		<!--row-fluid-->
	</div>
	<!--maincontentinner-->
</div>
<!--maincontent-->


<%@include file="/common/js_file.jsp"%>
<!-- 这里引入你需要的js文件 -->
<script type="text/javascript" src="${ctx }/js/custom.js"></script>
<link rel="stylesheet" href="../views/market/quoteConfirmList.css">
<%@include file="/common/footer.jsp"%>

