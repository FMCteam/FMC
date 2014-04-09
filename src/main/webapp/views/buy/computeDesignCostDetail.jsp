<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>

<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
			<!--  如果是其它页面，这里是填充具体的内容。 -->
			<div class="widget">
				<h4 class="widgettitle">采购成本核算</h4>
				<div class="widgetcontent">
					<form id="costAccounting_form" method="post" action="${ctx }/buy/computeDesignCostSubmit.do">
						<table class="table table-striped table-bordered table-hover">
							<tr>
								<td rowspan="2">询单信息</td>
								<td>询单编号</td>
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
								 <td rowspan="6">面料报价</td>
                        <td>面料名</td>
                        <td>单件米耗</td>
                        <td>价格</td>
                        <td>单件成本</td>

								
							</tr>
							
							
							<tr>
								 <c:forEach var="fabric" items="${fabric_list}" >
	                        <tr >
	                    <td>${fabric.fabricName }</td>
	                      <input type="hidden" name="fabricName" value="${fabric.fabricName}" />
						<td><input class="span12" name="tear_per_meter"   placeholder="tear_per_meter"type="text" /></td>
                        <td><input class="span12" name="cost_per_meter"   placeholder="cost_per_meter"type="text" /></td>
                        <td><input class="span12" name="fabric_price"  placeholder="fabric_price"type="text" /></td>
						     </tr>
                        </c:forEach>
							</tr>
							
							 <tr>
                        <td>面料损耗</td>
                        <td><input class="span12" name="fabric_meters_cost" id="fabric_meters_cost"  placeholder="fabric_meters_cost" type="text" /></td>
                        <td>小计</td>
                        <td><input class="span12" name="all_fabric_prices" id="all_fabric_prices"  placeholder="all_fabric_prices" type="text" /></td>
							</tr>
							
							
							
							<tr>
								  <td rowspan="6">辅料报价</td>
                        <td>辅料名</td>
                        <td>单件耗数</td>
                        <td>价格</td>
                        <td>单件成本</td>

							</tr>
							
							<tr>
								 <c:forEach var="accessory" items="${accessory_list}" >
	                        <tr >
	                    <td>${accessory.accessoryName }</td>
	                      <input type="hidden" name="accessoryName" value="${accessory.accessoryName}" />
						 <td><input class="span12" name="tear_per_piece"   placeholder="tear_per_piece"type="text" /></td>
                        <td><input class="span12" name="cost_per_meter"   placeholder="cost_per_piece"type="text" /></td>
                        <td><input class="span12" name="accessory_price"   placeholder="accessory_price"type="text" /></td>

						     </tr>
                        </c:forEach>
							</tr>
							
						 <tr>
                        <td>辅料损耗 </td>
                        <td><input class="span12" name="accessory_meters_cost" id="accessory_meters_cost"  placeholder="accessory_meters_cost" type="text" /></td>
                        <td>小计</td>
                        <td><input class="span12" name="accessory_prices" id="accessory_prices"  placeholder="accessory_prices" type="text" /></td>
                    </tr>
                    
                    <tr>
                        <td>面辅总计</td>
                        <td colspan="2"><input class="span12" name="prices" id="prices"  placeholder="prices" type="text" /></td>
                        <td colspan="2"></td>
                        
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
