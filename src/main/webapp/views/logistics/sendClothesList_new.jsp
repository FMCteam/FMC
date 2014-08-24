
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
				</table>
			</section>
			
			<div class="OrderListWrap" style="margin-top:20px;margin-left:25px;"> 
				<div class="OrderList">
					<ul id="itemContainer" style="list-style:none;">
						<!-- 待发货列表 -->
						<c:forEach var="model" items="${scanList}">
							<div class="orderWrap" style="width:25%; float:left; margin-bottom:20px;">
								<li>
									<div>
										<a href="${ctx}/logistics/scanClothesDetail.do?orderId=${model.order.orderId}"  title="查看详情">
										<img src="${ctx}/common/getPic.do?type=sample&orderId=${model.order.orderId}" title="查看详情" style="height:225px;width:225px" >
										</a>
									</div>
							
									<div>
										<table>
											<tbody>
												<tr>
													<td>订单号：</td>
													<td>${model.orderId}</td>
												</tr>
												<tr>
												<td colspan="2" style="color: #CD0000;font-weight: bold;">${model.order.styleName}</td>
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
													<td>开始时间：</td>
													<td>${model.taskTime}</td>
												</tr>
												<tr>
													<td>操作：</td>
													<td><a href="${ctx}/logistics/scanClothesDetail.do?orderId=${model.order.orderId}">待发货</a></td>
												</tr>
											</tbody>
										</table>
									</div>
							
								</li>
							</div>
						</c:forEach>
						
						<!-- 已发货列表 -->
						<c:forEach var="model" items="${sendList}">
							<div class="orderWrap" style="width:25%; float:left; margin-bottom:20px;">
								<li>
									<div>
										<a href="${ctx}/logistics/sendClothesDetail.do?orderId=${model.order.orderId}"  title="查看详情">
										<img src="${ctx}/common/getPic.do?type=sample&orderId=${model.order.orderId}" title="查看详情" style="height:225px;width:225px" >
										</a>
									</div>
							
									<div>
										<table>
											<tbody>
												<tr>
													<td>订单号：</td>
													<td>${model.orderId}</td>
												</tr>
												<tr>
												<td colspan="2" style="color: #CD0000;font-weight: bold;">${model.order.styleName}</td>
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
													<td>开始时间：</td>
													<td>${model.taskTime}</td>
												</tr>
												<tr>
													<td>操作：</td>
													<td><a href="${ctx}/logistics/sendClothesDetail.do?orderId=${model.order.orderId}">已发货</a></td>
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
				
		</div>
		<!--row-fluid-->
		
		<div class="holder"></div>
		
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
<link rel="stylesheet" href="${ctx}/css/jPages.css"/>  
<link rel="stylesheet" href="${ctx}/css/animate.css"/>
<script type="text/javascript" src="${ctx}/js/jPages.min.js"></script>
<script type="text/javascript" src="${ctx}/js/fmc/table.js"></script>
<script type="text/javascript" src="${ctx }/js/custom.js"></script>
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


<%@include file="/common/footer.jsp"%>

