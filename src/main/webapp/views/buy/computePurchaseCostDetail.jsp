<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>

<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
			<!--  如果是其它页面，这里是填充具体的内容。 -->
			<ul class="nav nav-tabs detail" id="tab">
				<li class="task-name">采购成本核算</li>
				<li class="active"><a href="#quote" data-toggle="tab">报价信息</a></li>
				<li><a href="#cad" data-toggle="tab">版型信息</a></li>
				<li><a href="#produce" data-toggle="tab">加工信息</a></li>
				<li><a href="#sample" data-toggle="tab">样衣信息</a></li>
				<li><a href="#material" data-toggle="tab">面辅信息</a></li>
				<li><a href="#basic" data-toggle="tab">基本信息</a></li>
			</ul>

			<div class="tab-content">
				<div class="tab-pane" id="basic">
					<%@include file="/views/common/basic.jsp"%>
				</div>
				<div class="tab-pane" id="material">
					<%@include file="/views/common/material.jsp"%>
				</div>
				<div class="tab-pane" id="sample">
					<%@include file="/views/common/sample.jsp"%>
				</div>
				<div class="tab-pane" id="produce">
					<%@include file="/views/common/produce.jsp"%>
				</div>
				<div class="tab-pane" id="cad">
					<%@include file="/views/common/cad.jsp"%>
				</div>
				<div class="tab-pane active" id="quote">

					<form id="costAccounting_form" method="post"
						action="${ctx}/buy/computePurchaseCostSubmit.do">
						<table
							class="table table-striped table-bordered table-hover detail">
							<tr>
								<td class="span2" rowspan="${fn:length(orderInfo.fabrics)+1}">面料报价</td>
								<td class="span3">面料名</td>
								<td class="span3">单件米耗</td>
								<td class="span3">每米价格</td>
							
							</tr>

							<c:forEach var="fabric" items="${orderInfo.fabrics}">
								<tr>
									<td>${fabric.fabricName}<input type="hidden"
										name="fabricName" value="${fabric.fabricName}" /></td>
									<td><input class="span12" name="tear_per_meter"
										type="text" /></td>
									<td><input class="span12" name="cost_per_meter"
										type="text" /></td>
									
								</tr>
							</c:forEach>

							<tr>
								<td rowspan="${fn:length(orderInfo.accessorys)+1}">辅料报价</td>
								<td>辅料名</td>
								<td>单件耗数</td>
								<td>辅料单价</td>
								
							</tr>

							<c:forEach var="accessory" items="${orderInfo.accessorys}">
								<tr>
									<td>${accessory.accessoryName}<input type="hidden"
										name="accessoryName" value="${accessory.accessoryName}" /></td>
									<td><input class="span12" name="tear_per_piece"
										type="text" /></td>
									<td><input class="span12" name="cost_per_piece"
										type="text" /></td>
								
								</tr>
							</c:forEach>
						<!-- 	<tr>
								<td>面辅总计</td>
								<td>面料总计</td>
								<td><input class="span12" name="all_fabric_prices"
									id="all_fabric_prices" type="text" /></td>
								<td>辅料总计</td>
								<td><input class="span12" name="accessory_prices"
									id="accessory_prices" type="text" /></td>
							</tr>
							 -->
						</table>
						<input class="btn btn-primary" type="submit" value="提交报价"
							style="float:right;"> <input type="hidden" name="orderId"
							value="${orderInfo.order.orderId }" /> <input type="hidden"
							name="taskId" value="${orderInfo.task.id}" />
					</form>
				</div>
			</div>
		</div>
	</div>


	<div class="footer">
		<div class="footer-left">
			<span>&copy; 2014. 江苏南通智造链有限公司.</span>
		</div>
	</div>


</div>
</div>




<%@include file="/common/js_file.jsp"%>
<%@include file="/common/js_form_file.jsp"%>
<link rel="stylesheet" href="${ctx}/css/fmc/table.css">
<script type="text/javascript" src="${ctx}/js/fmc/table.js"></script>
<link rel="stylesheet" href="${ctx}/css/fmc/detail.css">
<script type="text/javascript" src="${ctx}/views/buy/cost.js"></script>
<script type="text/javascript" src="${ctx}/js/order/add_order.js"></script>
<script type="text/javascript" src="${ctx }/js/custom.js"></script>
<%@include file="/common/footer.jsp"%>
