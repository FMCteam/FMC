<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>

<div class="maincontent">
	<div class="maincontentinner">
		<form method="post" name= "confirmProductForm"
			action="${ctx}/market/confirmProduceOrderSubmit.do"
			onsubmit="return confirmProductDetailSubmit(confirmProductForm.contractFile.value,confirmProductForm.confirmDepositFile.value);"
			enctype="multipart/form-data">
			<div class="row-fluid" style="min-height:300px;">
				<!--  如果是其它页面，这里是填充具体的内容。 -->

				<ul class="nav nav-tabs detail" id="tab">
					<li class="task-name">确认生产加工单并签订合同</li>
					<li><a href="#quote" data-toggle="tab">报价信息</a></li>
					<li><a href="#cad" data-toggle="tab">版型信息</a></li>
					<li class="active"><a href="#produce" data-toggle="tab">加工信息</a></li>
					<li><a href="#sample" data-toggle="tab">样衣信息</a></li>
					<li><a href="#material" data-toggle="tab">面辅信息</a></li>
					<li ><a href="#basic" data-toggle="tab">基本信息</a></li>
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
					<div class="tab-pane active" id="produce">
						<%@include file="/views/common/produce_w.jsp"%>
					</div>
					<div class="tab-pane" id="cad">
						<%@include file="/views/common/cad.jsp"%>
					</div>
					<div class="tab-pane" id="quote">
						<%@include file="/views/common/quote.jsp"%>
					</div>
				<input type="hidden" name="taskId" value="${orderInfo.task.id}" />
				<input type="hidden" name="orderId" value="${orderInfo.order.orderId }" />
				<input type="hidden" name="orderInfoQuoteOuterPrice" value="${orderInfo.quote.outerPrice}"/>
				<input type="hidden" name="orderInfoOrderAskAmount" value="${orderInfo.order.askAmount }"/>
				<table class="table table-striped table-bordered table-hover detail">
					<tr>
						<td>优惠金额</td>
						<td>总金额</td>
						<td style="background: #cc0000; "><a a style="color: #FFFFFF">上传合同（请上传jpg,png格式的图片）</a></td>
						<td style="background: #cc0000; "><a style="color: #FFFFFF">上传首定金收据（请上传jpg,png格式的图片）</a></td>
					</tr>
					<tr>
						<td><input name="discount" type="text" value="0" /><input name="sum" type="hidden" value="${orderInfo.quote.outerPrice*orderInfo.order.askAmount }"/></td>
						<td><input name="totalmoney" type="text" value="${orderInfo.quote.outerPrice*orderInfo.order.askAmount }" readonly="readonly" /></td>
						<td><a style="color: red;">*</a>
							<input name="contractFile" type="file" value="选择合同文件" required="required"/>
						</td>
						<td><a style="color: red;">*</a><input name="confirmDepositFile" type="file" value="选择定金文件" required="required"/></td>
					</tr>
					<tr>
						<td>备注信息</td>
						<td colspan="3">
							<%-- <input type="text"  name="moneyremark"  required="required" style="height:40px;width: 1000px"   placeholder="输入首定金备注" value="${orderInfo.order.moneyremark }" /> --%>
							<textarea class="span12"  style="resize:vertical" name="moneyremark"  id="money_remark" placeholder="输入首定金备注" ></textarea>
						</td>
					</tr>
				</table>
				</div>
			</div>
			<a class="btn btn-danger btn-rounded"  onclick="cancel()"><i class="icon-white icon-remove"></i>取消订单</a>
			<div class="action">
				<input type="hidden" name="taskId" value="${orderInfo.taskId}" /> <input
					type="hidden" name="orderId" value="${orderInfo.order.orderId }" />
				<input type="hidden" name="processId"
					value="${orderInfo.task.processInstanceId}" /> <input
					id="produce_color" type="hidden" name="produce_color" /> <input
					id="produce_xs" type="hidden" name="produce_xs" /> <input
					id="produce_s" type="hidden" name="produce_s" /> <input
					id="produce_m" type="hidden" name="produce_m" /> <input
					id="produce_l" type="hidden" name="produce_l" /> <input
					id="produce_xl" type="hidden" name="produce_xl" /> <input
					id="produce_xxl" type="hidden" name="produce_xxl" /> <input
					type="hidden" name="tof" id="tof">
					
					<a class="btn btn-primary btn-rounded" ><i class="icon-ok icon-white"></i>确定加工</a> 
			</div>
		</form>
		<br>
			<button class="btn btn-primary" onclick="history.back(-1);">返回</button> 
			<a href="${ctx}/market/printConfirmProcurementOrder.do?orderId=${orderInfo.order.orderId}"  onclick="return check()"  id="printConfirmProcurementOrder"
		 		 style="font-size: 13px; margin-left:20px; padding: 9px 30px 7px; background: #0866c6;border-color: #0a6bce; color: #fff; text-shadow: none;"   target="_blank">打印补货单</a><a style="color: red;font-size: 16px;">打印补货单</a><span style="color: red;font-size: 16px;">&nbsp;亲，请别忘了打印补货单哦！</span>

	</div>
	<!--maincontentinner-->
	<div class="footer">
		<div class="footer-left">
			<span>&copy; 2014. 江苏南通智造链有限公司.</span>
		</div>
	</div>
	<!--footer-->
</div>
<!--maincontent-->


<link rel="stylesheet" href="${ctx}/css/order/add_order.css">
<link rel="stylesheet" href="${ctx}/css/fmc/table.css">
<link rel="stylesheet" href="${ctx}/css/fmc/detail.css">
<%@include file="/common/js_file.jsp"%>
<%@include file="/common/js_form_file.jsp"%>
<script type="text/javascript" src="${ctx}/js/order/add_product.js"></script>
<script type="text/javascript" src="${ctx}/js/order/add_produce.js"></script>
<script type="text/javascript" src="${ctx}/js/fmc/table.js"></script>
<script type="text/javascript" src="${ctx}/js/order/add_order.js"></script>
<script type="text/javascript" src="${ctx }/js/custom.js"></script>
<!-- 
<script type="text/javascript">
$(document).ready(function(){
});  
</script>
 -->
<script type="text/javascript">
$(function(){
	$("#tof").val("false");
})
function confirmProductDetailSubmit(fileValue1,fileValue2) {
	if($("#tof").val() == "true"){
		var fileValue1str = fileValue1.toLowerCase().split(".");
		var fileValue2str = fileValue2.toLowerCase().split(".");
		if(fileValue1.length != 0){
			if(!(fileValue1str[fileValue1str.length-1] =="png"||fileValue1str[fileValue1str.length-1] =="jpg")){
				alert("合同文件格式错误，请上传png,jpg格式的文件!");
				return false;
			}else{
				if(fileValue2.length != 0){
					if(!(fileValue2str[fileValue2str.length-1] =="png"||fileValue2str[fileValue2str.length-1] =="jpg")){
						alert("定金收据格式错误，请上传jpg,png格式的文件!");
						return false;
					}else{
						return produce_verify();
					}
				}else{
					alert("请上传定金文件!");
					return false;
				}
			}
		}else{
			alert("请上传合同文件!");
			return false;
		}
	}else{
		return produce_verify();
	}
	
}

function cancel(){
	$("#tof").val("false");
}

var orderInfoQuoteOuterPrice = $("input[name='orderInfoQuoteOuterPrice']").val();
var orderInfoOrderAskAmount = $("input[name='orderInfoOrderAskAmount']").val();
var totalMoney = orderInfoQuoteOuterPrice*orderInfoOrderAskAmount;
$("input[name='totalmoney']").val(totalMoney.toFixed(2));

function check(){
	if(confirm("确认打印补货单？")){
		var produce_color = getTdString("produce_color");
		var produce_xs = getTdString("produce_xs");
		var produce_s = getTdString("produce_s");
		var produce_m = getTdString("produce_m");
		var produce_l = getTdString("produce_l");
		var produce_xl = getTdString("produce_xl");
		var produce_xxl = getTdString("produce_xxl");
		$("#printConfirmProcurementOrder").attr("href","${ctx}/market/printConfirmProcurementOrder.do?orderId=${orderInfo.order.orderId}&produce_color="+produce_color+"&produce_xs="+produce_xs+"&produce_s="+produce_s+"&produce_m="+produce_m+"&produce_l="+produce_l+"&produce_xl="+produce_xl+"&produce_xxl="+produce_xxl); 
		
		return true;
	}else{
		return false;
	}
}

function getTdString(col){
	var tdString="";
	var i=0;
	for(;i<$("td."+col).length-1;i++){
		tdString+=$("td."+col).eq(i).text()+",";
	}
	tdString+=$("td."+col).eq(i).text();
	return tdString;
}

$("#money_remark").val("${orderInfo.order.moneyremark}");
</script>

<%@include file="/common/footer.jsp"%>
