<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>


<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
			<section class="list">
				<table class="list tablesorter">
					<caption>
						<span class="text-vertical">待收取样衣:<span class="number">${fn:length(list)}</span>件任务
						</span><input type="text" class="search-query float-right"
							placeholder="输入检索条件">
					</caption>
					<thead>
						<tr>
							<th>询单编号</th>
							<th>样衣图片</th>
							<th>客户姓名</th>
							<th>快递名称</th>
							<th>快递单号</th>
							<th>邮寄时间</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="task" items="${list}">
							<tr>
								<td>${task.orderId}</td>
								<td style="padding:8px 0px 0px 0px;"><c:if
										test="${task.order.sampleClothesPicture!=null}">
										<img width="60px" height="100%"
											src="${ctx}/common/getPic.do?type=sample&orderId=${task.order.orderId}"></img>
									</c:if></td>
								<td>${task.order.customerName}</td>
								<td>${task.logistics.inPostSampleClothesType}</td>
								<td>${task.logistics.inPostSampleClothesNumber}</td>
								<td>${fn:substring(task.logistics.inPostSampleClothesTime,0,10)}</td>
								<td><a
									href="${ctx}/logistics/receiveSampleDetail.do?orderId=${task.order.orderId}">详情
								</a></td>
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
<%@include file="/common/js_form_file.jsp"%>
<link rel="stylesheet" href="${ctx}/css/fmc/table.css">
<script type="text/javascript" src="${ctx}/js/fmc/table.js"></script>
<script type="text/javascript" src="${ctx }/js/custom.js"></script>
<%@include file="/common/footer.jsp"%>