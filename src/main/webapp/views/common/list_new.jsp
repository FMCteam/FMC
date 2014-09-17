<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<section class="list">
	<form id="orderSearch"  method="post" action="${ctx}${searchurl}">
		<table class="list tablesorter" id="grid">
			<caption>
				<span class="text-vertical">${taskName}:<span class="number">${fn:length(list)}</span>件任务
				</span>
				<br>
					<span >输入起始日期:</span>
						<input style="width: 210px" type="date" name="startdate" value="${info.startdate }" placeholder="输入订单起始日期">
						<span >输入订单编号:</span>
						<input type="text" style="width:110px;" name="ordernumber" value="${info.ordernumber }"  placeholder="输入订单编号">
						<span >款式名称:</span> 
						<input type="text"  style="width: 130px;" name="stylename" placeholder="输入款式名称" value="${info.stylename }">						
						<br>
						<input class="btn btn-primary" type="submit" value="查询" style="float:right;">
						<span > 输入截止日期:</span>
						<input style="width: 210px" type="date" name="enddate" value="${info.enddate }" placeholder="输入订单截止日期">
						<c:if test="${USER_user_role ne 'marketStaff'}">
							<span >市场专员名称:</span>
							<input type="text" style="width: 110px;" name="employeename" value="${info.employeename }" placeholder="输入市场专员名称">
						</c:if>
						<c:if test="${USER_user_role eq 'marketStaff'}">
							&nbsp; &nbsp; &nbsp; &nbsp; <input type="hidden"  name="employeename" >	
						</c:if>	
						<c:if test="${USER_user_role ne 'CUSTOMER'}">
							<span >客户名称:</span>
							<input type="text" style="width: 130px;" name="customername" placeholder="输入客户名称" value="${info.customername }">
						</c:if>
						<c:if test="${USER_user_role eq 'CUSTOMER'}">
							<input type="hidden" class="search-query " name="customername" >	
						</c:if>
			</caption>
		</table>
	</form>
</section>

<div class="OrderListWrap" style="margin-top:20px;margin-left:25px;"> 
	<div class="OrderList">
		<ul id="itemContainer" style="list-style:none;">
			<c:forEach var="model" items="${list}">
				<div class="orderWrap" style="width:25%; float:left; margin-bottom:20px;">
					<li>
						<div>
							<a href="${ctx}${url}?orderId=${model.order.orderId}&cid=${cid}" title="查看详情">
							<img src="${model.order.sampleClothesPicture}" title="查看详情" style="height:225px;width:225px" >
							</a>
						</div>
				
						<div>
							<table>
								<tbody>
									<tr>
										<td colspan="2" style="color: #CD0000;font-weight: bold; height: 42px; vertical-align:middle;">${model.order.styleName}</td>
									</tr>
									<tr>
										<td>订单号：</td>
										<td>${model.orderId}</td>
									</tr>
									<tr>
										<td>开始时间：</td>
										<td>${model.order.orderTime}</td>
									</tr>
									<tr>
										<td>市场专员：</td>
										<td>${model.employee.employeeName}</td>
									</tr>
									<tr>
										<td>客户姓名：</td>
										<td>${model.order.customerName}</td>
									</tr>
									<tr>
										<td>客户公司：</td>
										<td>${model.order.customerCompany}</td>
									</tr>
									<tr>
										<td>操作：</td>
										<td><a href="${ctx}${url}?orderId=${model.order.orderId}&cid=${cid}">查看详情</a></td>
									</tr>
								</tbody>
							</table>
						</div>
				
					</li>
				</div>
			</c:forEach>
		</ul>
		
	</div>
</div>

<link rel="stylesheet" href="${ctx}/css/jPages.css"/>
<link rel="stylesheet" href="${ctx}/css/animate.css"/>
<script type="text/javascript" src="${ctx}/js/jPages.min.js"></script>
<script>
$(function(){
	$("div.holder").jPages({
		containerID : "itemContainer",
      	previous : "上一页",
      	next : "下一页",
      	perPage : 8,
      	delay : 200
    });  
});
</script>

