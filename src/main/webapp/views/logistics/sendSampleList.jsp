
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>


<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
			<!--  如果是其它页面，这里是填充具体的内容。 -->
			<section>
				<table id="dyntable" class="table responsive">
					<caption>样衣发货</caption>
					<tr>
						<th class="head0">单号</th>
						<th class="head1">生成时间</th>
						<th class="head0">客户姓名</th>
						<th class="head1">客户电话</th>
						<th class="head0">公司名称</th>
						<th class="head1">公司电话</th>
						<th class="head0">操作</th>
					</tr>
					<c:forEach var="task" items="${orderList}">
						<tr class="gradeA">
							<td>${task.order.orderId }</td>
							<td>${fn:substring(task.order.orderTime,0,10) }</td>
							<td>${task.order.customerName }</td>
							<td>${task.order.customerPhone1 }</td>
							<td>${task.order.customerCompany }</td>
							<td>${task.order.customerCompanyFax }</td>
							<td><a href="${ctx}/logistics/sendSampleDetail.do?taskId=${task.task.id}&pid=${task.task.processInstanceId}&orderId=${task.order.orderId}">发货</a></td>
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
