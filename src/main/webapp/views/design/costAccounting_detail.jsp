<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>

<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
			<!--  如果是其它页面，这里是填充具体的内容。 -->
			<div class="widget">
				<h4 class="widgettitle">设计成本核算</h4>
				<div class="widgetcontent">
					<form id="costAccounting_form" method="post" action="${ctx }/design/doCostAccounting.do">
						<table class="table table-striped table-bordered table-hover">
							<tr>
								<td rowspan="2">询单信息</td>
								<td>询单编号</td>
								<td>接单业务员</td>
								<td>客户名字</td>
								<td>报价日期</td>
								
							</tr>
							<tr>
								<td>${orderModel.order.orderId }</td>
								<td>${orderModel.order.employeeId }</td>
								<td>${orderModel.order.customerName }</td>
								<td>${orderModel.order.orderTime}</td>
								
							</tr>
							
							
							<tr>
                        <td>设计费用</td>
                     <td>
                     <input class="span12" name="design_cost" id="design_cost"  placeholder="design_cost" type="text" /></td>
                      <input type="hidden" name="orderId" value="${orderModel.order.orderId }" />
	                  <input type="hidden" name="taskId" value="${orderModel.taskId }" />
					<input type="hidden" name="pinId" value="${orderModel.processInstanceId }" />
                    </tr>
							
							  <tr>

                        <td colspan="3"><input type="submit" style="float: right;"/></td>
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
<script type="text/javascript" src="${ctx }/js/custom.js"></script>
<%@include file="/common/footer.jsp"%>
