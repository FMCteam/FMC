<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>

<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
			<!--  如果是其它页面，这里是填充具体的内容。 -->
			<ul class="nav nav-tabs detail" id="tab">
				<li class="task-name">设计工艺验证</li>
				<li class="active"><a href="#quote" data-toggle="tab">工艺报价</a></li>
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
						method="post" action="${ctx }/design/computeDesignCostSubmit.do">
						<table
							class="table table-striped table-bordered table-hover detail">
							<tr>
								<td class="title">客户工艺要求</td>
								<td colspan="12">${orderInfo.designCadTech}</td>
							</tr>

							<tr>
								<td class="title" colspan="1" rowspan="2">
									&nbsp;<span>需要工艺</span><input type="radio" name="needcraft" value="1"  checked="checked"  required="required">
									<br/>
									<span>不要工艺</span><input type="radio" name="needcraft" value="0" >
								</td>
								<td class="title" colspan="1">印花费（元/件）</td>
								<td class="title" colspan="1">水洗吊染费（元/件）</td>
								<td class="title" colspan="1">激光费（元/件）</td>
								<td class="title" colspan="1">刺绣费（元/件）</td>
								<td class="title" colspan="1">压皱费（元/件）</td>
								<td class="title" colspan="1">开版费用<a style="color: red;">[整单]</a></td>
							</tr>
							<tr>
								<td><input class="span12" type="text" name="stampDutyMoney" value="0" /></td>
								<td><input class="span12" type="text" name="washHangDyeMoney" value="0"/></td>
								<td><input class="span12" type="text" name="laserMoney" value="0" /></td>
								<td><input class="span12" type="text" name="embroideryMoney" value="0" /></td>
								<td><input class="span12" type="text" name="crumpleMoney" value="0"/></td>
								<td><input class="span12"  type="text" name="openVersionMoney" value="0"/></td>
							</tr>
							<tr>
								<td class="title span2">意见</td>
								<td colspan="6">
									<textarea class="span12"
										style="resize:vertical" rows="3" name="suggestion"></textarea>
								</td>
							</tr>
						</table>
	                  	
	                    <input id="disagree" class="btn btn-danger" type="button" value="拒绝" />
	                  	<div class="action" style="float:right"> 
							<input id="agree" class="btn btn-primary" type="button" value="同意" /> 
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
<script type="text/javascript" src="${ctx}/views/design/designCraft.js"></script>
<script type="text/javascript" src="${ctx }/js/custom.js"></script>
<script>
jQuery(document).ready(function(){
	//提交报价
	jQuery("#agree").click(function(){
		if(confirm("确认操作？")){
			jQuery("#verify_val").val("true");
			jQuery("#costAccounting_form").submit();
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
