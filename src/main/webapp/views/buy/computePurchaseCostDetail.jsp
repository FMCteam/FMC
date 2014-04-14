<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>

<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
			<!--  如果是其它页面，这里是填充具体的内容。 -->
			<ul class="nav nav-tabs detail" id="tab">
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
				
				<form id="costAccounting_form" method="post" action="${ctx }/buy/computePurchaseCostSubmit.do">
					
					
					
					<table class="table table-striped table-bordered table-hover">
				
				
					<tr>
								 <td rowspan="${orderInfo.data.fabricSize }">面料报价</td>
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
								  <td rowspan= "${orderInfo.data.accessorySize }">辅料报价</td>
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
                        <td><input class="span12" name="cost_per_piece"   placeholder="cost_per_piece"type="text" /></td>
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
	                  <input type="hidden" name="taskId" value="${orderInfo.task.id }" />   
					
                    </tr>
							
							
							  <tr>

                        <td colspan="3"><input type="submit" style="float: right;"/></td>
                    </tr>

							
							
							
						</table>
					</form>
					
				</div>
				
				
				
				
			</div>
		</div>
		<div class="action">
			
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
<link rel="stylesheet" href="${ctx}/css/fmc/detail.css">
<script type="text/javascript" src="${ctx}/js/order/add_order.js"></script>
<script type="text/javascript" src="${ctx }/js/custom.js"></script>
<%@include file="/common/footer.jsp"%>
