
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>


<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
			<!--  如果是其它页面，这里是填充具体的内容。 -->
			<section>
				<table id="dyntable" class="table responsive">
					<caption>待生产样衣列表</caption>
					<tr>
						<th class="head0">客户姓名</th>
						<th class="head1">客户电话</th>
						<th class="head1">公司名称</th>
						<th class="head0">公司电话</th>
						<th class="head0">内部报价</th>
						<th class="head0">外部报价</th>
						<th class="head1">操作</th>
					</tr>
					<tr>
						<td>(例子)李二宇</td>
						<td>15996385325</td>
						<td>南京大学软件学院</td>
						<td>025-67678989</td>
						<td>￥200.00</td>
						<td>￥250.00</td>
						<td><a href="#">样衣详情</a></td>
					</tr>
					<c:forEach var="task" items="${tasks}">
						<tr class="gradeA">
							<td>${task.customerName}</td>
							<td>${task.customerPhone}</td>
							<td>${task.companyName}</td>
							<td>${task.companyPhone}</td>
							<td>${task.innerPrice}</td>
							<td>${task.outerPrice}</td>
							<td><a
								href="${ctx}/produce/sampleProduce.do?taskId=${task.taskId}&orderId=${task.orderId}">样衣详情</a>
							</td>
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
