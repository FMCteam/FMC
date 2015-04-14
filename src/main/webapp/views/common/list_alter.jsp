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
						<input type="text" style="width:130px;" name="ordernumber" value="${info.ordernumber }"  placeholder="输入订单编号">
						<span >输入款式名称:</span> 
						<input type="text"  style="width: 130px;" name="stylename" placeholder="输入款式名称" value="${info.stylename }">
					
						<br>
						<input class="btn btn-primary" type="submit" value="查询" style="float:right;">
						<span > 输入截止日期:</span>
						<input style="width: 210px" type="date" name="enddate" value="${info.enddate }" placeholder="输入订单截止日期">
						<c:if test="${USER_user_role ne 'marketStaff'}">
							<span >原市场专员名称:</span>
							<input type="text" style="width: 130px;" name="employeename" value="${info.employeename }" placeholder="输入原市场专员名称">
						</c:if>
						<c:if test="${USER_user_role eq 'marketStaff'}">
							<input type="hidden"  name="employeename" >	
						</c:if>	
						<c:if test="${USER_user_role ne 'marketStaff'}">
							<span >新市场专员名称:</span>
							<input type="text" style="width: 130px;" name="employeename" value="${info.employeename }" placeholder="输入新市场专员名称">
						</c:if>
						<c:if test="${USER_user_role eq 'marketStaff'}">
							<input type="hidden"  name="employeename" >	
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
							<c:if test="${empty model.order.sampleClothesThumbnailPicture}">
								<img src="${model.order.sampleClothesPicture}" title="查看详情" style="height:225px;width:225px" >	
							</c:if>
							<c:if test="${not empty model.order.sampleClothesThumbnailPicture}">
								<img src="${model.order.sampleClothesThumbnailPicture}" title="查看详情" style="height:225px;width:225px" >
							</c:if>
							
							</a>
						</div>
						<div>
							<table>
								<tbody>
									<!--<tr>
										<td colspan="2" style="color: #CD0000;font-weight: bold; height: 42px; vertical-align:middle;">${model.order.styleName}</td>
									</tr>-->
									
                                    <tr>
										<td>订单号：</td>
										<td>${model.Alter.orderId}</td>
									</tr>
									<tr>
										<td>申请时间：</td>
										<td>${model.Alter.applyTime}</td>
									</tr>
									<tr>
										<td>原市场专员：</td>
										<td>${model.employee.employeeName}</td>
									</tr>
                                    <tr>
										<td>新市场专员：</td>
                                        <c:if test="${empty model.employeeNext}">
										<td>待确定</td>
                                        </c:if>
                                        <c:if test="${not empty model.employeeNext}">
                                        <td>${model.employeeNext.employeeName}</td>
                                        </c:if>
									</tr>
									<tr>
										<td>审批状态：</td>
										<td>${model.Alter.verifyState}</td>
									</tr>
									
									<tr>
										<td>操作：</td>
										<td><a href="${ctx}${url}?orderId=${model.Alter.orderId}&alterId=${model.Alter.alterId}">查看详情</a></td>
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

