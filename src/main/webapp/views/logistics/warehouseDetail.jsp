<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>

<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
			<!--  如果是其它页面，这里是填充具体的内容。 -->
			<ul class="nav nav-tabs detail" id="tab">
				<li><a href="#warehouse" data-toggle="tab">入库信息</a></li>
				<li><a href="#quote" data-toggle="tab">报价信息</a></li>
				<li><a href="#cad" data-toggle="tab">版型信息</a></li>
				<li><a href="#produce" data-toggle="tab">加工信息</a></li>
				<li><a href="#sample" data-toggle="tab">样衣信息</a></li>
				<li><a href="#material" data-toggle="tab">面辅信息</a></li>
				<li class="active"><a href="#basic" data-toggle="tab">基本信息</a></li>
			</ul>

			<div class="tab-content">
				<div class="tab-pane active" id="basic">
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
				<div class="tab-pane" id="quote">
					<%@include file="/views/common/quote.jsp"%>
				</div>
				<div class="tab-pane" id="warehouse">
					<table class="table table-bordered detail package">
						<tr>
							<td>颜色</td>
							<td>大小</td>
							<td>件数</td>
							<td>操作</td>
						</tr>
						<tr class="addrow">
							<td><input type="text" class="color" /></td>
							<td><input type="text" class="size" /></td>
							<td><input type="text" class="number" /></td>
							<td><a>添加</a></td>
						</tr>
					</table>

					<div class="action">
						<form action="${ctx}/logistics/warehouseDetail.do" method="post"
							onsubmit="return dealString()">
							<input type="hidden" name="color" /> <input type="hidden"
								name="size" /> <input type="hidden" name="number" /> <input
								type="hidden" name="orderId" value="${orderInfo.order.orderId}" />
							<input class="btn btn-primary" type="submit" value="添加包裹" />
						</form>
					</div>

					<table class="table table-bordered detail">
						<tr>
							<td>箱号</td>
							<td>颜色</td>
							<td>大小</td>
							<td>件数</td>
							<td>操作</td>
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
									rowspan="${fn:length(orderInfo.packageDetails[status.index])}"><a>打印</a></td>
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
					<div class="action">
						<a class="btn btn-primary"
							href="${ctx}/logistics/warehouseSubmit.do?taskId=${orderInfo.task.id}">完成装箱</a>
					</div>
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
<script type="text/javascript" src="${ctx}/js/order/add_order.js"></script>
<script type="text/javascript" src="${ctx }/js/custom.js"></script>
<%@include file="/common/footer.jsp"%>
