<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>

<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
			<!--  如果是其它页面，这里是填充具体的内容。 -->
			<ul class="nav nav-tabs detail" id="tab">
				<li class="task-name">批量生产(外发)</li>
				<li class="active"><a href="#produceList" data-toggle="tab">批量生产</a></li>
				<li><a href="#quote" data-toggle="tab">报价信息</a></li>
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
				<div class="tab-pane" id="quote">
					<%@include file="/views/common/quote.jsp"%>
				</div>
				<div class="tab-pane active" id="produceList">
					<form method="post" id="produce_detail_form"
						action="${ctx}/produce/produceSubmit.do"  onsubmit="return check()">
						<table
							class="table table-striped table-bordered table-hover detail">
							<tr>
								<td class="title" rowspan="${fn:length(orderInfo.produce)+1}">计划生产数量</td>
								<td class="title">颜色</td>
								<td class="title">XS</td>
								<td class="title">S</td>
								<td class="title">M</td>
								<td class="title">L</td>
								<td class="title">XL</td>
								<td class="title">XXL</td>
								<td class="title">均码</td>
							</tr>
							<c:forEach var="produce" items="${orderInfo.produce}" >
								<tr>
									<td><span id="color">${produce.color}</span></td>
									<td><span id="xs">${produce.xs}</span></td>
									<td><span id="s">${produce.s}</span></td>
									<td><span id="m">${produce.m}</span></td>
									<td><span id="l">${produce.l}</span></td>
									<td><span id="xl">${produce.xl}</span></td>
									<td><span id="xxl">${produce.xxl}</span></td>
									<td><span id="j">${produce.j}</span></td>
								</tr>
							</c:forEach>
							<tr>
								<td class="title" rowspan="${fn:length(orderInfo.produce)+1}">实际生产数量</td>
								<td class="title">颜色</td>
								<td class="title">XS</td>
								<td class="title">S</td>
								<td class="title">M</td>
								<td class="title">L</td>
								<td class="title">XL</td>
								<td class="title">XXL</td>
								<td class="title">均码</td>
							</tr>
							<c:forEach var="produce" items="${orderInfo.produce}">
								<tr>
									<td><input class="span12 produce_color" type="text"
										value="${produce.color}" readonly="readonly" /></td>
									<td><input class="span12 produce_xs" type="number" min="0"
										value="${produce.xs}" required="required" onblur="this.value=(this.value==''?'0':this.value )"/></td>
									<td><input class="span12 produce_s" type="number" min="0"
										value="${produce.s}" required="required"  onblur="this.value=(this.value==''?'0':this.value )" /></td>
									<td><input class="span12 produce_m" type="number" min="0"
										value="${produce.m}" required="required" onblur="this.value=(this.value==''?'0':this.value )" /></td>
									<td><input class="span12 produce_l" type="number" min="0"
										value="${produce.l}" required="required" onblur="this.value=(this.value==''?'0':this.value )" /></td>
									<td><input class="span12 produce_xl" type="number" min="0"
										value="${produce.xl}" required="required" onblur="this.value=(this.value==''?'0':this.value )"/></td>
									<td><input class="span12 produce_xxl" type="number"
										min="0" value="${produce.xxl}" required="required" onblur="this.value=(this.value==''?'0':this.value )"/></td>
									<td><input class="span12 produce_j" type="number"
										min="0" value="${produce.j}" required="required" onblur="this.value=(this.value==''?'0':this.value )"/></td>
								</tr>
							</c:forEach>
							<tr>
								<td class="title"><span class="required">*</span>加工方</td>
								<td colspan="8">
									<input class="span14" id="processing_side" name="processing_side" type="text"  />
								</td>
							</tr>
						</table>
						
						<input type="hidden" id="produce_color" name="produce_color"/>
						<input type="hidden" id="produce_xs" name="produce_xs"/>
						<input type="hidden" id="produce_s" name="produce_s"/>
						<input type="hidden" id="produce_m" name="produce_m"/>
						<input type="hidden" id="produce_l" name="produce_l"/>
						<input type="hidden" id="produce_xl" name="produce_xl"/>
						<input type="hidden" id="produce_xxl" name="produce_xxl"/>
						<input type="hidden" id="produce_j" name="produce_j"/>
						
					    
					    <input id="disagree_detail" type="submit" value="终止外发加工" class="btn btn-danger btn-rounded">
						<div class="action" style="float:right">
							 <input type="hidden" name="orderId" value="${orderInfo.order.orderId }" />
							 <input type="hidden" name="taskId" value="${orderInfo.taskId }" /> 
							 <input id="verify_val" type="hidden" name="result" value="" /> 
							 
							 <input id="agree_detail" type="submit" value="开始外发加工" class="btn btn-primary btn-rounded">
						</div>
						<br>
					</form>
					
					<button class="btn btn-primary" onclick="history.back();">返回</button>
					
				</div>
			</div>
		</div>
		<!--row-fluid-->

		<input type="hidden" id="result" />

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
<link rel="stylesheet" href="${ctx}/css/fmc/table.css">
<script type="text/javascript" src="${ctx}/js/fmc/table.js"></script>
<link rel="stylesheet" href="${ctx}/css/fmc/detail.css">
<link rel="stylesheet" href="${ctx}/css/order/add_order.css">
<script type="text/javascript" src="${ctx}/js/order/add_produce.js"></script>
<script type="text/javascript" src="${ctx }/js/custom.js"></script>
<script>
jQuery(document).ready(function(){
	//确认完成大货生产
	jQuery("#agree_detail").click(function(){
		//加工方不能为空
		/* var processingSide = jQuery("input[name='processing_side']").val();
		if(processingSide == "" || processingSide == null){
			alert("请填写加工方信息");
			return;
		}
	
		if(confirm("确认完成大货外发生产？")){
			jQuery("#verify_val").val("true");
			getProduce();
			jQuery("#produce_detail_form").submit();
		} */
		$("#result").val(1);
	});
	//大货生产失败
	jQuery("#disagree_detail").click(function(){
		/* if(confirm("确认大货生产失败？")){
			jQuery("#verify_val").val("false");
			getProduce();
			jQuery("#produce_detail_form").submit();
		} */
		$("#result").val(0);
	});
});

function check(){
	alert(1)
	if($("#result").val()==1){
		var processingSide = jQuery("input[name='processing_side']").val();
		if(processingSide == "" || processingSide == null){
			alert("请填写加工方信息");
			return false;
		}
		if(confirm("确认完成大货外发生产？")){
			jQuery("#verify_val").val("true");
			getProduce();
		}else{
			return false;
		} 
	}else{
		if(confirm("确认大货生产失败？")){
			jQuery("#verify_val").val("false");
			getProduce();
		}else{
			return false;
		} 
	}
}

</script>

<script type="text/javascript">
var length=$("#size").attr("rowspan")-1;
var taskName=$("#"+"taskName"+length+"").text();
if("确认样衣" == taskName){
	$("#showSubmit").show();	
}else{
	$("#showSubmit").hide();
}
</script>
<%@include file="/common/footer.jsp"%>
