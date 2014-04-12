<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>

<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
			<!--  如果是其它页面，这里是填充具体的内容。 -->
			<div class="widget">
				<h4 class="widgettitle">生产样衣</h4>
				<div class="widgetcontent">

					<form method="post" action="${ctx }/produce/produceSampleSubmit.do">
						<table class="table table-striped table-bordered table-hover">
							<tr>
								<td rowspan="${fn:length(orderInfo.produces)+1}">计划生产样衣数量</td>
								<td>颜色</td>
								<td>XS</td>
								<td>S</td>
								<td>M</td>
								<td>L</td>
								<td>XL</td>
								<td>XXL</td>
							</tr>
							<c:forEach var="produce" items="${orderInfo.produces}">
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
								<td rowspan="${fn:length(orderInfo.produces)+1}">实际生产样衣数量</td>
								<td>颜色</td>
								<td>XS</td>
								<td>S</td>
								<td>M</td>
								<td>L</td>
								<td>XL</td>
								<td>XXL</td>
							</tr>
							<c:forEach var="produce" items="${orderInfo.produces}">
								<tr>
									<td><input calss="produce_color" type="text" value="${produce.color}"/></td>
									<td><input calss="produce_xs" type="text" value="${produce.xs}" /></td>
									<td><input calss="produce_s" type="text" value="${produce.s}" /></td>
									<td><input calss="produce_m" type="text" value="${produce.m}" /></td>
									<td><input calss="produce_l" type="text" value="${produce.l}" /></td>
									<td><input calss="produce_xl" type="text" value="${produce.xl}" /></td>
									<td><input calss="produce_xxl" type="text" value="${produce.xxl}" /></td>
								</tr>
							</c:forEach>
							
							<tr>
								<td>操作</td>	
								<td colspan="7">
									<input type="hidden" name="orderId" value="${orderInfo.order.orderId }" />
									<input type="hidden" name="taskId" value="${orderInfo.taskId }" />
									<input id="result" type="hidden" name="result" value="" />
									<a id="agree_detail" class="btn btn-primary btn-rounded"><i class="icon-ok icon-white"></i>加工完成</a>
									<a id="disagree_detail" class="btn btn-danger btn-rounded"><i class="icon-remove icon-white"></i>加工失败</a>
								</td>
							</tr>
						</table>
					</form>
				</div>
				<!--widgetcontent-->
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
<link rel="stylesheet" href="${ctx}/css/order/add_order.css">
<script type="text/javascript" src="${ctx}/js/order/add_order.js"></script>
<script type="text/javascript" src="${ctx}/js/order/add_produce.js"></script>
<script type="text/javascript" src="${ctx }/js/custom.js"></script>
<%@include file="/common/footer.jsp"%>
