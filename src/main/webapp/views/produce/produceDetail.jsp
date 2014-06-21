<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>

<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
			<!--  如果是其它页面，这里是填充具体的内容。 -->
			<ul class="nav nav-tabs detail" id="tab">
				<li class="task-name">批量生产</li>
				<li class="active"><a href="#produceList" data-toggle="tab">批量生产</a></li>
				<li><a href="#quote" data-toggle="tab">报价信息</a></li>
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
				<div class="tab-pane" id="quote">
					<%@include file="/views/common/quote.jsp"%>
				</div>
				<div class="tab-pane active" id="produceList">
					<form method="post" id="verify_form"
						action="${ctx}/produce/produceSubmit.do"
						onsubmit="return getProduce()">
						<table
							class="table table-striped table-bordered table-hover detail">
							<tr>
								<td class="span1" rowspan="${fn:length(orderInfo.produce)+1}">计划生产数量</td>
								<td class="span1">颜色</td>
								<td class="span1">XS</td>
								<td class="span1">S</td>
								<td class="span1">M</td>
								<td class="span1">L</td>
								<td class="span1">XL</td>
								<td class="span1">XXL</td>
							</tr>
							<c:forEach var="produce" items="${orderInfo.produce}">
								<tr>
									<td>${produce.color}</td>
									<td>${produce.xs}</td>
									<td>${produce.s}</td>
									<td>${produce.m}</td>
									<td>${produce.l}</td>
									<td>${produce.xl}</td>
									<td>${produce.xxl}</td>
								</tr>
							</c:forEach>
							<tr>
								<td class="span1" rowspan="${fn:length(orderInfo.produce)+1}">实际生产数量</td>
								<td class="span1">颜色</td>
								<td class="span1">XS</td>
								<td class="span1">S</td>
								<td class="span1">M</td>
								<td class="span1">L</td>
								<td class="span1">XL</td>
								<td class="span1">XXL</td>
							</tr>
							<c:forEach var="produce" items="${orderInfo.produce}">
								<tr>
									<td><input class="span12 produce_color" type="text"
										value="${produce.color}" readonly="readonly" /></td>
									<td><input class="span12 produce_xs" type="number" min="0"
										value="${produce.xs}" required="required" /></td>
									<td><input class="span12 produce_s" type="number" min="0"
										value="${produce.s}" required="required" /></td>
									<td><input class="span12 produce_m" type="number" min="0"
										value="${produce.m}" required="required" /></td>
									<td><input class="span12 produce_l" type="number" min="0"
										value="${produce.l}" required="required" /></td>
									<td><input class="span12 produce_xl" type="number" min="0"
										value="${produce.xl}" required="required" /></td>
									<td><input class="span12 produce_xxl" type="number"
										min="0" value="${produce.xxl}" required="required" /></td>
								</tr>
							</c:forEach>
						</table>
					    <button class="btn btn-primary" onclick="history.back();">返回</button>
						<div class="action">
							 <input type="hidden" name="orderId"
								value="${orderInfo.order.orderId }" /> <input type="hidden"
								name="taskId" value="${orderInfo.taskId }" /> <input
								id="verify_val" type="hidden" name="result" value="" /> <a
								id="agree_detail" class="btn btn-primary btn-rounded"><i
								class="icon-ok icon-white"></i>开始加工</a> <a id="disagree_detail"
								class="btn btn-danger btn-rounded"><i
								class="icon-remove icon-white"></i>加工失败</a>
						</div>
						<input id="produce_color" name="produce_color" type="hidden" /> <input
							id="produce_xs" name="produce_xs" type="hidden" /> <input
							id="produce_s" name="produce_s" type="hidden" /> <input
							id="produce_m" name="produce_m" type="hidden" /> <input
							id="produce_l" name="produce_l" type="hidden" /> <input
							id="produce_xl" name="produce_xl" type="hidden" /> <input
							id="produce_xxl" name="produce_xxl" type="hidden" />
					</form>
				</div>
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
<%@include file="/common/js_form_file.jsp"%>
<link rel="stylesheet" href="${ctx}/css/fmc/table.css">
<script type="text/javascript" src="${ctx}/js/fmc/table.js"></script>
<link rel="stylesheet" href="${ctx}/css/fmc/detail.css">
<link rel="stylesheet" href="${ctx}/css/order/add_order.css">
<script type="text/javascript" src="${ctx}/js/order/add_produce.js"></script>
<script type="text/javascript" src="${ctx }/js/custom.js"></script>
<%@include file="/common/footer.jsp"%>
