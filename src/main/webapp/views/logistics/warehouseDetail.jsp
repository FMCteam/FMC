<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>

<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
			<!--  如果是其它页面，这里是填充具体的内容。 -->
			<ul class="nav nav-tabs detail" id="tab">
				<li class="task-name">大货入库</li>
				<li class="active"><a href="#warehouse" data-toggle="tab">入库信息</a></li>
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
				<div class="tab-pane active" id="warehouse">
					<table class="table table-bordered detail" id="pack">
						<tr>
							<td class="title">箱号</td>
							<td class="title">颜色</td>
							<td class="title">大小</td>
							<td class="title">件数</td>
							<td class="title">操作</td>
						</tr>
						<c:forEach var="pack" items="${orderInfo.packages}"
							varStatus="status">
							<tr>
								<td
									rowspan="${fn:length(orderInfo.packageDetails[status.index])}">${pack.packageId}</td>
								<td>${orderInfo.packageDetails[status.index][0].clothesStyleColor}</td>
								<td>${orderInfo.packageDetails[status.index][0].clothesStyleName}</td>
								<td>${orderInfo.packageDetails[status.index][0].clothesAmount}</td>
								<td
									rowspan="${fn:length(orderInfo.packageDetails[status.index])}">
									<a target="_blank"
									href="${ctx}/logistics/printWarehouseDetail.do?orderId=${orderInfo.order.orderId}&packageId=${pack.packageId}">打印</a>
								</td>
							</tr>
							<c:forEach var="detail" begin="1"
								items="${orderInfo.packageDetails[status.index]}">
								<tr>
									<td>${detail.clothesStyleColor}</td>
									<td>${detail.clothesStyleName}</td>
									<td>${detail.clothesAmount}</td>
								</tr>
							</c:forEach>
						</c:forEach>
					</table>
				</div>
				<button class="btn btn-primary" onclick="history.back();">返回</button>
				<div class="action">
					<a class="btn btn-primary"
						href="${ctx}/logistics/mobile/warehouseDetail.do?orderId=${orderInfo.order.orderId}">入库</a>
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
<script type="text/javascript" src="${ctx}/js/fmc/common.js"></script>
<link rel="stylesheet" href="${ctx}/css/fmc/detail.css">
<link rel="stylesheet" href="${ctx}/views/logistics/logistics.css">
<script type="text/javascript" src="${ctx}/views/logistics/logistics.js"></script>
<script type="text/javascript" src="${ctx}/js/order/add_order.js"></script>
<script type="text/javascript" src="${ctx }/js/custom.js"></script>
<%@include file="/common/footer.jsp"%>
