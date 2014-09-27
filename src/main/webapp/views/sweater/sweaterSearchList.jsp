<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>

<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
			<section class="list">
				<form  method="post" action="${ctx}${searchurl}">
				<table class="list tablesorter" id="grid">
					<caption>
						<span class="text-vertical">${taskName}:<span
							class="number">${fn:length(list)}</span>条订单
						</span>
						<br>
						<tr>
							<td>
								<select type="text" class="span12" name="orderState" required="required" >
								<option selected="selected" value="0">请选择</option>
								<option value="采购毛线">采购毛线</option>
								<option value="打小样">打小样</option>
								<option value="制作工艺">制作工艺</option>
								<option value="确认样衣">确认样衣</option>
								<option value="外发">外发</option>
								<option value="回收质检">回收质检</option>
							    <option value="已完成">已完成</option>
								</select>
							</td>
							<td>&nbsp;</td>
							<td>
								<input class="btn btn-primary" type="submit" value="查询" style="float:right;">
							</td>
						</tr>
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
													<td>订单状态：</td>
													<td><span style="color:red;">${model.order.orderProcessStateName}</span></td>
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
		
		</div>
		
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
<script type="text/javascript" src="${ctx}/js/custom.js"></script>
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

