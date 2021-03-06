<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>

<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
			<!--  如果是其它页面，这里是填充具体的内容。 -->

			<ul class="nav nav-tabs detail" id="tab">
				<li class="task-name">订单详情</li>
				<li class="active"><a href="#changeMarket" data-toggle="tab">更换专员</a></li>
				<li><a href="#cad" data-toggle="tab">版型信息</a></li>
				<li><a href="#produce" data-toggle="tab">加工信息</a></li>
				<li><a href="#sample" data-toggle="tab">样衣信息</a></li>
				<li><a href="#basic" data-toggle="tab">基本信息</a></li>
			</ul>

			<div class="tab-content">
				<div class="tab-pane" id="basic">
					<%@include file="/views/common/basic.jsp"%>
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
				<div class="tab-pane active" id="changeMarket">
					<%@include file="/views/market/changeMarketStaff.jsp"%>
				</div>
			</div>
			<c:if test="${orderInfo.order.orderState!='1'}">
			<div class="push-order" style="float:left">
				
				<c:if test="${role=='ADMIN' || role=='marketManager' || role=='marketStaff'}">
				<a class="btn btn-primary" 
					href="${ctx}/order/pushOrderInfo.do?orderId=${orderInfo.order.orderId}">推送订单信息</a>
				</c:if>
				<a class="btn btn-primary"
					href="${ctx}/image.do?orderId=${orderInfo.order.orderId}">订单进度</a>
			</div>
			</c:if>
			<div class="action" style="float:right">
				
				<c:if test="${role=='ADMIN'}">
				<c:if test="${orderInfo.order.orderState=='A'}">
 					<a class="btn btn-danger" style="color: white" onclick="return confirm('确定终止订单？');"
						href="${ctx}/order/end.do?orderId=${orderInfo.order.orderId}">终止订单</a>
				</c:if>
				</c:if>
			</div>
			<br><br>
			<button class="btn btn-primary" onclick="history.back();">返回</button>
			
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
<link rel="stylesheet" href="${ctx}/views/finance/finance.css">
<link rel="stylesheet" href="${ctx}/css/fmc/detail.css">
<script type="text/javascript" src="${ctx }/js/custom.js"></script>
<script type="text/javascript" src="${ctx}/views/finance/finance.js"></script>
<script type="text/javascript">
$(function(){
	var date = new Date();
	var month = date.getMonth()>8?date.getMonth()+1:"0"+(date.getMonth()+1);
	var day = date.getDate()>9?date.getDate():"0"+date.getDate();
	var hour = date.getHours()>9?date.getHours():"0"+date.getHours();
	var minute = date.getMinutes()>9?date.getMinutes():"0"+date.getMinutes();
	var second = date.getSeconds()>9?date.getSeconds():"0"+date.getSeconds();
	$("#input_day").val(date.getFullYear()+"/"+month+"/"+day+" "+hour+":"+minute+":"+second);
	
	updateAllApplicationTable(${orderInfo.order.orderId});
	$("#applySubmit").click(function(){
		$.post("${ctx}/market/applyForAlterMarketStaffSubmit.do",{"reason":$("#reason").val(),"orderId":${orderInfo.order.orderId},"orderProcessId":${orderInfo.order.processId},"employeeId":${orderInfo.order.employeeId}},function(data){
			if(data.result == true){
				$(".result").css("color","red").html("已提交");
				updateAllApplicationTable(${orderInfo.order.orderId});
				noty({
					text : '申请提交成功',
					layout : 'topCenter',
					timeout : 2000
				});
			}
			else if(data.result == "existRepetition"){
				noty({
					text : '您提交的申请还未审批,请勿重复提交',
					layout : 'topCenter',
					timeout : 2000
				});
			}
			else{
				noty({
					text : '申请提交失败',
					layout : 'topCenter',
					timeout : 2000
				});
			}
		});
	});
	
	function updateAllApplicationTable(orderId){
		$.get("${ctx}/market/getAlterInfoByOrderId.do",{"orderId":orderId},function(data){
			var str = "<tr><td>申请时间</td><td>申请专员ID</td><td>申请理由</td><td>审批状态</td></tr>";
			for(var i=0;i<data.alterInfoList.length;i++){
				var info = data.alterInfoList[i];
				
				var date = new Date(info.alterInfo.applyTime.time);
				
				var month = date.getMonth()>8?date.getMonth()+1:"0"+(date.getMonth()+1);
				var day = date.getDate()>9?date.getDate():"0"+date.getDate();
				var hour = date.getHours()>9?date.getHours():"0"+date.getHours();
				var minute = date.getMinutes()>9?date.getMinutes():"0"+date.getMinutes();
				var second = date.getSeconds()>9?date.getSeconds():"0"+date.getSeconds();
				
				var datetime = date.getFullYear()+"/"+month+"/"+day+" "+hour+":"+minute+":"+second;
				
				str+="<tr><td>"+datetime+"</td><td>"+info.alterInfo.employeeId+"</td><td>"+(info.reason==null?"":info.reason)+"</td><td>"+info.alterInfo.verifyState+"</td></tr>";
			}
			$("#allApplication").html(str);
		});
	}
	
});
</script>
<%@include file="/common/footer.jsp"%>
