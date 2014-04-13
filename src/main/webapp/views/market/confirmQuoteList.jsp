
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>


<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
			<!--  如果是其它页面，这里是填充具体的内容。 -->
			<section class="list">
				<table id="dyntable" class="list">
					<caption>
						<span class="text-vertical">报价商定:<span class="number">${fn:length(list)}</span>件任务
						</span><input type="text" class="search-query float-right"
							placeholder="输入检索条件">
					</caption>
					<tr>
						<th class="head0">客户姓名</th>
						<th class="head1">客户电话</th>
						<th class="head1">公司名称</th>
						<th class="head0">公司电话</th>
						<th class="head0">内部报价</th>
						<th class="head0">外部报价</th>
						<th class="head1">操作</th>
					</tr>
					<c:forEach var="quoteModel" items="${list }">
						<tr class="gradeA">
							<td>${quoteModel.order.customerName}</td>
							<td>${quoteModel.order.customerPhone1}</td>
							<td>${quoteModel.order.customerCompany}</td>
							<td>${quoteModel.order.customerCompanyFax}</td>
							<td>${quoteModel.quote.innerPrice}</td>
							<td>${quoteModel.quote.outerPrice}</td>
							<td><a href="${ctx}/market/confirmQuoteDetail.do?orderId=${quoteModel.quote.orderId }&taskId=${quoteModel.task.id }">详细</a></td>
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
