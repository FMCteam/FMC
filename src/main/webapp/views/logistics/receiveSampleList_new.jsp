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
				</table>
			</section>
			
			<div class="OrderListWrap" style="margin-top:20px;margin-left:25px;"> 
				<div class="OrderList">
					<ul id="itemContainer" style="list-style:none;">
						<c:forEach var="task" items="${list}">
							<div class="orderWrap" style="width:25%; float:left; margin-bottom:20px;">
								<li>
									<div>
										<a href="${ctx}/logistics/receiveSampleDetail.do?orderId=${task.order.orderId}"  title="查看详情">
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
												<tr>
												<td colspan="2" style="color: #CD0000;font-weight: bold; height: 42px; vertical-align:middle;">${task.order.styleName}</td>
												</tr>
												<tr>
													<td>询单编号：</td>
													<td>${task.orderId}</td>
												</tr>
												<tr>
													<td>客户姓名：</td>
													<td>${task.order.customerName}</td>
												</tr>
												<tr>
													<td>快递名称：</td>
													<td>${task.logistics.inPostSampleClothesType}</td>
												</tr>
												<tr>
													<td>快递单号：</td>
													<td>${task.logistics.inPostSampleClothesNumber}</td>
												</tr>
												<tr>
													<td>邮寄时间：</td>
													<td>${fn:substring(task.logistics.inPostSampleClothesTime,0,10)}</td>
												</tr>
												<tr>
													<td>操作：</td>
													<td><a href="${ctx}/logistics/receiveSampleDetail.do?orderId=${task.order.orderId}">查看详情</a></td>
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
<%@include file="/common/js_form_file.jsp"%>
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