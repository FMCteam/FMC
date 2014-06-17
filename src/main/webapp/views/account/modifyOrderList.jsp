
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%@include file="/common/header.jsp"%>


<div class="maincontent">
	<div class="maincontentinner">
	<div class="row-fluid" style="min-height:300px;">
    <section class="list">
	<form id="orderSearch"  method="post" action="${ctx}/account/modifyOrderSearch.do">
	<table class="list tablesorter">
		<caption>
			<span class="text-vertical">${taskName}:<span class="number">${fn:length(list)}</span>件任务
			</span>
			<br>
						<span >输入起始日期:</span>
						<input class="search-query" type="text" name="startdate" placeholder="输入订单起始日期">
						<span >输入截止日期:</span>
						<input class="search-query" type="text" name="enddate" placeholder="输入订单截止日期">
			<br>
						<input class="btn btn-primary" type="submit" value="查询" style="float:right;">
						<span >输入订单编号:</span>
						<input type="text" class="search-query " name="ordernumber" placeholder="输入订单编号">
						<span >市场专员名称:</span>
						<input type="text" class="search-query " name="employeename" placeholder="输入市场专员名称">
						<span >款式名称:</span>
						<input type="text" class="search-query " name="stylename" placeholder="输入款式名称">
						<span >客户名称:</span>
						<input type="text" class="search-query " name="customername" placeholder="输入客户名称">
			 
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
			<c:forEach var="model" items="${list}">
				<tr>
					<td>${model.orderId}${model.order.styleName}</td>
					<td style="padding:8px 0px 0px 0px;"><c:if
							test="${model.order.sampleClothesPicture!=null}">
							<img width="60px" height="100%"
								src="${ctx}/common/getPic.do?type=sample&orderId=${model.order.orderId}"></img>
						</c:if></td>
					<td>${model.employee.employeeName}</td>
					<td>${model.order.customerName}</td>
					<td>${model.order.customerCompany}</td>
					<td>${model.taskTime}</td>
					<td><a href="${ctx}${url}?orderId=${model.order.orderId}">详情</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</form>
</section>
			<div style="float: right;margin-top:20px;margin-right:0px">

				<select style=" width: 100px;margin: 0px 10px" id="orderPager">
					<c:forEach var="number" begin="1" end="${pages}">
						<option value="${number}" ${page eq number ?'selected':'' }>第${number}页
						</option>
					</c:forEach>
				</select> 
			</div>
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
<script>
	$("#orderPager").change(function() {
		var p = $("#orderPager").children("option:selected").val();
		window.location.href = "${ctx}/order/orderList.do?page=" + p;
	});
</script>
<%@include file="/common/footer.jsp"%>

