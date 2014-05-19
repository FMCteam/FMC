<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>

<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
			<!--  如果是其它页面，这里是填充具体的内容。 -->
			<ul class="nav nav-tabs detail" id="tab">
				<li class="task-name">采购成本核算</li>
				<li class="active"><a href="#quote" data-toggle="tab">报价信息</a></li>
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
				
				<div class="tab-pane  active" id="quote">
				
					<form id="costAccounting_form" onSubmit="return verify()" method="post"
						action="${ctx}/buy/computePurchaseCostSubmit.do">

<table
							class="table table-striped table-bordered table-hover">
							<tr>

								<td class="span2">面料报价
								<input id="fabric_name" type="hidden"name="fabric_name" />
								<input id="fabric_amount" type="hidden"name="fabric_amount" />
								<input id="tear_per_meter" type="hidden"name="tear_per_meter" /></td>
								<input id="cost_per_meter" type="hidden"name="cost_per_meter" /></td>
								
								<td class="innertable span12">
								<table class="span12 table fabric_table">
										<tr>
											<td class="span5">面料名称</td>
											<td class="span5">面料克重</td>
											<td class="span5">单件米耗（单位：米）</td>
											<td class="span5">每米价格（单位：元）</td>
											<td class="span3">操作</td>
										</tr>
										<tr class="addrow">
											<td><input class="span12" type="text" required="required"/></td>
											<td><input class="span12" type="text" required="required" /></td>
											<td><input class="span12" type="text" required="required" /></td>
											<td><input class="span12" type="text" required="required" /></td>
											<td><a>添加</a><span class="required">（点击添加之后数据生效）</span></td>
										</tr>
									</table>
									</td>
							</tr>
							<tr>
								<td class="span2">辅料报价
								<input id="accessory_name" type="hidden" name="accessory_name" /> 
								<input id="accessory_query" type="hidden" name="accessory_query" /> 
								<input id="tear_per_piece" type="hidden" name="tear_per_piece" /></td>
								<input id="cost_per_piece" type="hidden" name="cost_per_piece" /></td>
								<td class="innertable span12"><table
										class="span12 table accessory_table">
										<tr>
											<td class="span5">辅料名称</td>
											<td class="span5">辅料要求</td>
											<td class="span5">单件耗数（单位：个）</td>
											<td class="span5">辅料单价（单位：元）</td>
											<td class="span3">操作</td>
										</tr>
										<tr class="addrow">
											<td><input class="span12" type="text" required="required" /></td>
											<td><input class="span12" type="text" required="required"/></td>
											<td><input class="span12" type="text" required="required"/></td>
												<td><input class="span12" type="text" required="required"/></td>
											<td><a>添加</a><span class="required">（点击添加之后数据生效）</span></td>
										</tr>
									</table></td>

							
						</table>
						
						
						
						
						
						
						
						
						
						
						
						
						
						<input class="btn btn-primary" type="submit" value="提交报价"
							style="float:right;"> <input type="hidden" name="orderId"
							value="${orderInfo.order.orderId }" /> <input type="hidden"
							name="taskId" value="${orderInfo.task.id}" />
					</form>
					</div>
				</div>
			</div>
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
<script type="text/javascript" src="${ctx}/views/buy/cost.js"></script>
<script type="text/javascript" src="${ctx}/js/order/add_fabric.js"></script>
<script type="text/javascript" src="${ctx }/js/custom.js"></script>
<%@include file="/common/footer.jsp"%>
