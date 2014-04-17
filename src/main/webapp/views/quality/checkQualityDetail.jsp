<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>

<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
			<!--  如果是其它页面，这里是填充具体的内容。 -->
			<ul class="nav nav-tabs detail" id="tab">
				<li class="task-name">质量检查</li>
				<li class="active"><a href="#quality" data-toggle="tab">质量检查</a></li>
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
				<div class="tab-pane active" id="quality">
					<form method="post" action="${ctx }/quality/checkQualitySubmit.do">
						<table class="table table-striped table-bordered table-hover detail">
							<tr>
								<td>质检信息</td>
								<td>开始时间</td>
								<td><input id="datepicker" type="text" name="start_date" class="input-small" /></td>
								<td>完成时间</td>
								<td><input id="datepicker1" type="text" name="end_date" class="input-small" /></td>
							</tr>
							<tr>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td colspan="5">质检合格具体数量表</td>
							</tr>
							<tr>
								<td colspan="5">
								<table class=" table table-striped table-bordered">
								<tbody>
								<tr>
									<td>颜色</td>
									<td>XS</td>
									<td>S</td>
									<td>M</td>
									<td>L</td>
									<td>XL</td>
									<td>XXL</td>
								</tr>
								<tr>
									<td><input class="span12" type="text" name="good_color" /></td>
									<td><input class="span12" type="text" name="good_xs" /></td>
									<td><input class="span12" type="text" name="good_s" /></td>
									<td><input class="span12" type="text" name="good_m" /></td>
									<td><input class="span12" type="text" name="good_l" /></td>
									<td><input class="span12" type="text" name="good_xl" /></td>
									<td><input class="span12" type="text" name="good_xxl" /></td>
								</tr>
								</tbody>
								</table>
								</td>
							</tr>
							<tr>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
							</tr>
							<tr>
								<td colspan="5">质检不合格具体数量表</td>
							</tr>
							<tr>
								<td colspan="5">
								<table class=" table table-striped table-bordered">
								<tbody>
								<tr>
									<td>颜色</td>
									<td>XS</td>
									<td>S</td>
									<td>M</td>
									<td>L</td>
									<td>XL</td>
									<td>XXL</td>
								</tr>
								<tr>
									<td><input class="span12" type="text" name="bad_color" /></td>
									<td><input class="span12" type="text" name="bad_xs" /></td>
									<td><input class="span12" type="text" name="bad_s" /></td>
									<td><input class="span12" type="text" name="bad_m" /></td>
									<td><input class="span12" type="text" name="bad_l" /></td>
									<td><input class="span12" type="text" name="bad_xl" /></td>
									<td><input class="span12" type="text" name="bad_xxl" /></td>
								</tr>
								</tbody>
								</table>
								</td>
							</tr>
							<tr>
								<td>质检主管意见</td>
								<td colspan="4"><input class="span12" type="text" name="suggestion" /></td>
							</tr>
							<tr>
								<td>操作</td>
								<td colspan="4"><button class="btn btn-primary btn-rounded">确认</button></td>
							</tr>
						</table>
						<input type="hidden" name="orderId" value="${orderInfo.order.orderId}" />
						<input type="hidden" name="taskId" value="${orderInfo.taskId}" />
					</form>
				</div>
			</div>
			
			<div class="widget">
				<h4 class="widgettitle">质量检查</h4>
				<div class="widgetcontent">

					
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
<script type="text/javascript" src="${ctx}/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="${ctx}/js/order/add_order.js"></script>
<script type="text/javascript" src="${ctx }/js/custom.js"></script>
<%@include file="/common/footer.jsp"%>

