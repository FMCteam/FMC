<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>

<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
			<!--  如果是其它页面，这里是填充具体的内容。 -->
			<div class="widget">
				<h4 class="widgettitle">采购成本核算</h4>
				<div class="widgetcontent">
				<form id="costAccounting_form" method="post" action="${ctx }/produce/computeProduceCostSubmit.do">
						<table class="table table-striped table-bordered table-hover">
							<tr>
								<td rowspan="2">询单信息</td>
								<td>询单编号</td>
								<td>接单业务员</td>
								<td>客户名字</td>
								<td>报价日期</td>
								
							</tr>
							<tr>
								<td>${task.order.orderId }</td>
								<td>${task.order.employeeId }</td>
								<td>${task.order.customerName }</td>
								<td>${task.order.orderTime}</td>
								
							</tr>
							
							
						
							
						
                            <tr>
                             <td rowspan="4">其他成本</td>
                             <td>裁剪费用</td>
                             <td>管理费用</td>
                             <td>缝制费用</td>
                              <td>整烫费用</td>
                            
                            </tr>
							
							<tr>
				<td><input class="span12" name="cut_cost" id="cut_cost" placeholder="cut_cost"type="text"></td>
				<td><input class="span12" name="manage_cost" id="manage_cost" placeholder="manage_cost"type="text"></td>
				<td><input class="span12" name="nail_cost" id="nail_cost" placeholder="nail_cost"type="text"></td>
				<td><input class="span12" name="ironing_cost" id="ironing_cost" placeholder="ironing_cost"type="text"></td>		
					 <input type="hidden" name="orderId" value="${task.order.orderId }" />
	                  <input type="hidden" name="taskId" value="${task.taskId }" />
					<input type="hidden" name="pinId" value="${task.processInstanceId }" />
							</tr>
							
								
                            <tr>
                             
                             <td>锁订费用</td>
                             <td>包装费用</td>
                             <td>其他费用</td>
                            
                            </tr>
					
							<tr>
		<td><input class="span12" name="swing_cost" id="swing_cost" placeholder="swing_cost"type="text"></td>
		<td><input class="span12" name="package_cost" id="package_cost" placeholder="package_cost"type="text"></td>
		<td><input class="span12" name="other_cost" id="other_cost" placeholder="other_cost"type="text"></td>
					
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
