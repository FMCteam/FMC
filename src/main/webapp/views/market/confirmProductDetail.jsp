<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>

<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
			<!--  如果是其它页面，这里是填充具体的内容。 -->
			<div class="widget">
				<h4 class="widgettitle">确认合同加工单</h4>
				<div class="widgetcontent">
					<form onSubmit="return verify()" method="post" 
						action="${ctx }/market/confirmProduceOrderSubmit.do">
						<table class="table table-striped table-bordered table-hover">
							<tr>
								<td>订单号</td>
								<td colspan="4">${orderInfo.order.orderId}</td>
							</tr>
							<tr>
								<td rowspan="2">加工单</td>
								<td>生产数量</td>
								<td>颜色</td>
								<td>款式</td>
								<td>操作</td>
								<input id="product_amount" name="product_amount" type="hidden" />
								<input id="product_color" name="product_color" type="hidden" />
								<input id="product_style" name="product_style" type="hidden" />
							</tr>
							<tr>
								<td colspan="4" class="innertable"><table
										class="span12 table product_table">
										<tr class="addrow">
											<td><input class="span12" type="text" /></td>
											<td><input class="span12" type="text" /></td>
											<td><input class="span12" type="text" /></td>
											<td><a>添加</a></td>
										</tr>
									</table></td>
							</tr>
							<tr>
								<td>操作</td>
								<td colspan="2"><input type="submit" /></td>
								<td colspan="2"><input type="reset" /></td>
							</tr>
						</table>
						<input type="hidden" name="order_id" value="${orderInfo.order.orderId}" />
						<input type="hidden" name="task_id" value="${orderInfo.task.id}" />
						<input type="hidden" name="process_id" value="${orderInfo..task.processInstanceId }" />
						<input type="hidden" name="comfirmworksheet" value="true" />
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
<script type="text/javascript" src="${ctx}/js/order/add_product.js"></script>
<script type="text/javascript" src="${ctx }/js/custom.js"></script>
<%@include file="/common/footer.jsp"%>
