<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>

<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
			<!--  如果是其它页面，这里是填充具体的内容。 -->
			<div class="widget">
				<h4 class="widgettitle">质量检查</h4>
				<div class="widgetcontent">

					<form method="post"
						action="${ctx }/quality/checkQuality.do">
						<table class="table table-striped table-bordered table-hover">
							<tr>
								<td>订单信息</td>
								<td>单号</td>
								<td>${orderModel.order.orderId }</td>
								<td>生成时间</td>
								<td>${fn:substring(orderModel.order.orderTime,0,10) }</td>
							</tr>
							<tr>
								<td>加工方信息</td>
								<td>加工工厂</td>
								<td colspan="3">1</td>
							</tr>
							<tr>
								<td rowspan="3">客户信息</td>
								<td>公司名称</td>
								<td>${customer.companyName }</td>
								<td>联系电话</td>
								<td>${customer.companyPhone }</td>
							</tr>
							<tr>
								<td>采购人姓名</td>
								<td>${customer.buyContact }</td>
								<td>固定电话</td>
								<td>${customer.contactPhone1 }</td>
							</tr>
							<tr>
								<td>QQ</td>
								<td>${customer.qq }</td>
								<td>EMAIL</td>
								<td>${customer.email }</td>
							</tr>
							<tr>
								<td>专员信息</td>
								<td>姓名</td>
								<td>${employee.employeeName }</td>
								<td>电话</td>
								<td>${employee.phone1 }</td>
							</tr>
							<tr>
								<td rowspan="2">款式信息</td>
								<td colspan="2">名称</td>
								<td colspan="2">面料种类</td>
							</tr>
							<tr>
								<td colspan="2">${orderModel.order.styleName }</td>
								<td colspan="2">${orderModel.order.fabricType }</td>
							</tr>
							<tr>
								<td rowspan="4">加工要求</td>
								<td colspan="2">设计部要求</td>
								<td colspan="2">1</td>
							</tr>
							<tr>
								<td colspan="2">客户要求</td>
								<td colspan="2">1</td>
							</tr>
							<tr>
								<td colspan="2">客户新要求</td>
								<td colspan="2">1</td>
							</tr>
							<tr>
								<td colspan="2">生产主管意见</td>
								<td colspan="2">1</td>
							</tr>
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
						<input type="hidden" name="orderId"
							value="${orderModel.order.orderId}" />
							<input type="hidden" name="taskId"
							value="${orderModel.taskId}" />
							<input type="hidden" name="processId"
							value="${orderModel.processInstanceId}" />
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
<script type="text/javascript" src="${ctx}/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="${ctx}/js/order/add_order.js"></script>
<script type="text/javascript" src="${ctx }/js/custom.js"></script>
<%@include file="/common/footer.jsp"%>

