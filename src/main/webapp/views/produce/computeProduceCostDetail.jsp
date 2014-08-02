<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>

<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
			<!--  如果是其它页面，这里是填充具体的内容。 -->
			<ul class="nav nav-tabs detail" id="tab">
				<li class="task-name">生产成本验证并核算</li>
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
					<form id="costAccounting_form" class="verify"
						method="post" action="${ctx }/produce/computeProduceCostSubmit.do">
						<table
							class="table table-striped table-bordered table-hover detail">
							<tr>
								<td class="title" rowspan="4">生产成本</td>
								<td>裁剪费用（单位：元）</td>
								<td>管理费用（单位：元）</td>
								<td>缝制费用（单位：元）</td>
								<td>整烫费用（单位：元）</td>
							</tr>

							<tr>
								<td><input class="span12" name="cut_cost" id="cut_cost"
									type="text" required="required" /></td>
								<td><input class="span12" name="manage_cost"
									id="manage_cost" type="text" required="required" /></td>
								<td><input class="span12" name="nail_cost" id="nail_cost"
									type="text" required="required" /></td>
								<td><input class="span12" name="ironing_cost"
									id="ironing_cost" type="text" required="required" /></td>
							</tr>

							<tr>
								<td>锁订费用（单位：元）</td>
								<td>包装费用（单位：元）</td>
								<td>其他费用（单位：元）</td>
								<td>设计费用（单位：元）</td>
							</tr>

							<tr>
								<td><input class="span12" name="swing_cost" id="swing_cost"
									type="text" required="required" /></td>
								<td><input class="span12" name="package_cost"
									id="package_cost" type="text" required="required" /></td>
								<td><input class="span12" name="other_cost" id="other_cost"
									type="text" required="required" /></td>
								<td><input class="span12" name="design_cost"
									id="design_cost" type="text" required="required" /></td>
							</tr>
							<tr>
								<td class="title span2">意见</td>
								<td colspan="4">
									<textarea class="span12"
										style="resize:vertical" rows="3" name="suggestion"></textarea>
								</td>
							</tr>
						</table>
	                  	
	                  <input id="disagree" class="btn btn-danger" type="button" value="拒绝生产" /> 
	                  	<div class="action" style="float:right">
							<input id="agree" class="btn btn-primary" type="button" value="提交报价" /> 
							<input type="hidden" name="orderId" value="${orderInfo.order.orderId }" /> 
							<input type="hidden" name="taskId" value="${orderInfo.task.id}" />
							<input id="verify_val" type="hidden" name="result" value="false" />
					  	</div>
					  	<br>
					  	<button class="btn btn-primary" onclick="history.back();">返回</button>
					</form>
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
<link rel="stylesheet" href="${ctx}/views/produce/cost.css">
<script type="text/javascript" src="${ctx}/views/produce/cost.js"></script>
<script type="text/javascript" src="${ctx}/js/order/add_order.js"></script>
<script type="text/javascript" src="${ctx }/js/custom.js"></script>
<script>
jQuery(document).ready(function(){
	//提交报价
	jQuery("#agree").click(function(){
		if(confirm("确认操作？")){
			//报价信息填写正确
			if(checkForm()){
				jQuery("#verify_val").val("true");
				jQuery("#costAccounting_form").submit();
			}
		}
	});
	//拒绝采购
	jQuery("#disagree").click(function(){
		var suggestion = $("form.verify textarea").val();
		if (suggestion == "") {
			alert("拒绝意见不能为空");
			return;
		}
		if(confirm("确认操作？")){
			jQuery("#verify_val").val("false");
			jQuery("#costAccounting_form").submit();
		}
	});
});
</script>

<%@include file="/common/footer.jsp"%>
