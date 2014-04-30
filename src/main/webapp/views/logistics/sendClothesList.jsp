
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%@include file="/common/header.jsp"%>


<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
			<section class="list">
				<table class="list tablesorter">
					<caption>
						<span class="text-vertical">待发货列表:<span class="number">${fn:length(scanList)+fn:length(sendList)}</span>件任务
						</span><input type="text" class="search-query float-right"
							placeholder="输入检索条件">
					</caption>
					<thead>
						<tr>
							<th>订单号</th>
							<th>样衣图片</th>
							<th>市场专员</th>
							<th>客户姓名</th>
							<th>客户公司</th>
							<th>任务开始时间</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="model" items="${scanList}">
							<tr>
								<td>${model.orderId}</td>
								<td style="padding:8px 0px 0px 0px;"><c:if
										test="${model.order.sampleClothesPicture!=null}">
										<img width="60px" height="100%"
											src="${ctx}/common/getPic.do?type=sample&orderId=${model.order.orderId}"></img>
									</c:if></td>
								<td>${model.employee.employeeName}</td>
								<td>${model.order.customerName}</td>
								<td>${model.order.customerCompany}</td>
								<td>${model.taskTime}</td>
								<td><a
									href="${ctx}/logistics/scanClothesDetail.do?orderId=${model.order.orderId}">待发货</a>
								</td>
							</tr>
						</c:forEach>
						<c:forEach var="model" items="${sendList}">
							<tr>
								<td>${model.orderId}</td>
								<td style="padding:8px 0px 0px 0px;"><c:if
										test="${model.order.sampleClothesPicture!=null}">
										<img width="60px" height="100%"
											src="${ctx}/common/getPic.do?type=sample&orderId=${model.order.orderId}"></img>
									</c:if></td>
								<td>${model.employee.employeeName}</td>
								<td>${model.order.customerName}</td>
								<td>${model.order.customerCompany}</td>
								<td>${model.taskTime}</td>
								<td><a
									href="${ctx}/logistics/sendClothesDetail.do?orderId=${model.order.orderId}">已发货</a>
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

