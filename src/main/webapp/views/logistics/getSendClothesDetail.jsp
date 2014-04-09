<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>

<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
			<!--  如果是其它页面，这里是填充具体的内容。 -->
			<div class="widget">
				<h4 class="widgettitle">发货详细信息</h4>
				<div class="widgetcontent">
					<form id="costAccounting_form" method="post" action="${ctx }/logistics/sendClothesSubmit.do">
						<table class="table table-striped table-bordered table-hover">
							<tr>
								<td rowspan="2">订单信息</td>
								<td>订单编号</td>
								<td>接单业务员</td>
								<td>客户名字</td>
								<td>报价日期</td>
								
							</tr>
							<tr>
								<td>${orderInfo.order.orderId }</td>
								<td>${orderInfo.order.employeeId }</td>
								<td>${orderInfo.order.customerName }</td>
								<td>${orderInfo.order.orderTime}</td>
								
							</tr>
							
							
							<tr>
								<td colspan="5">具体货箱数量及装箱条码表对应物流单号表</td>
							</tr>
							<tr>
								<td>总货箱数</td>
								<td colspan="4">1</td>
							</tr>
							<tr>
								<td>条码列表</td>
								<td colspan="2">物流单号</td>
								<td colspan="2">物流公司</td>
							</tr>
							<tr>
								<td>1</td>
								<td colspan="2">1</td>
								<td colspan="2">1</td>
							</tr>
							<tr>
								<td rowspan="2">发货信息</td>
								<td>物流部发货人</td>
								<td>发货时间</td>
								<td colspan="2">发货备注</td>
							</tr>
							<tr>
								<td>1</td>
								<td>1</td>
								<td colspan="2">1</td>
							</tr>
							
							
							
							<tr>
							
                        <td>物流费用</td>
                     <td>
                     <input class="span12" name="logistics_cost" id="logistics_cost"  placeholder="logistics_cost" type="text" /></td>
                      <input type="hidden" name="orderId" value="${orderInfo.order.orderId }" />
	                  <input type="hidden" name="taskId" value="${orderInfo.taskId }" />
					<input type="hidden" name="pinId" value="${orderInfo.processInstanceId }" />
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
