
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>


<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
			<!--  如果是其它页面，这里是填充具体的内容。 -->
			<section class="list">
				<table class="list">
					<caption>
						<span class="text-vertical">样衣待发货列表:<span class="number">${fn:length(list)}</span>件任务
						</span><input type="text" class="search-query float-right"
							placeholder="输入检索条件">
					</caption>
					<thead>
						<tr>
							<th>单号</th>
							<th>生成时间</th>
							<th>客户姓名</th>
							<th>客户电话</th>
							<th>公司名称</th>
							<th>公司电话</th>
							<th>操作</th>
						</tr>
					</thead>
					<c:forEach var="task" items="${list}">
						<tr>
							<td>${task.order.orderId }</td>
							<td>${fn:substring(task.order.orderTime,0,10) }</td>
							<td>${task.order.customerName }</td>
							<td>${task.order.customerPhone1 }</td>
							<td>${task.order.customerCompany }</td>
							<td>${task.order.customerCompanyFax }</td>
							<td><a
								href="${ctx}/logistics/sendSampleDetail.do?orderId=${task.order.orderId}">详情</a></td>
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
<link rel="stylesheet" href="${ctx}/css/fmc/table.css">
<script type="text/javascript" src="${ctx}/js/fmc/table.js"></script>
<script type="text/javascript" src="${ctx }/js/custom.js"></script>
<link rel="stylesheet" href="../views/market/quoteConfirmList.css">
<%@include file="/common/footer.jsp"%>
