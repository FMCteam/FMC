<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>

<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
			<!--  如果是其它页面，这里是填充具体的内容。 -->
			<ul class="nav nav-tabs detail" id="tab">
				<li class="task-name">大货原料采购</li>
				<li class="active"><a href="#buy" data-toggle="tab">采购信息</a></li>
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
				<div class="tab-pane active" id="buy">
					<table
						class="table table-striped table-bordered table-hover detail">
						<tr>
							<td rowspan="${fn:length(orderInfo.fabricCosts)+1}">面料采购</td>
							<td colspan="2">面料名</td>
							<td colspan="1">单件米耗</td>
							<td colspan="1">件数</td>
							<td colspan="2">总采购米数</td>
						</tr>
						<c:forEach var="fabricCost" items="${orderInfo.fabricCosts}">
							<tr>
								<td colspan="2">${fabricCost.fabricName }</td>
								<td colspan="1">${fabricCost.tearPerMeter }</td>
								<td colspan="1">${orderInfo.order.askAmount}</td>
								<td colspan="2"><span id="metersPurchasedAll">${(fabricCost.tearPerMeter)*(orderInfo.order.askAmount)}</span></td>
							</tr>
						</c:forEach>
						<tr>
							<td rowspan="${fn:length(orderInfo.accessoryCosts)+1}">辅料采购</td>
							<td colspan="2">辅料名</td>
							<td colspan="1">单件耗数</td>
							<td colspan="1">件数</td>
							<td colspan="2">总采购个数</td>
						</tr>
						<c:forEach var="accessoryCost" items="${orderInfo.accessoryCosts}">
							<tr>
								<td colspan="2">${accessoryCost.accessoryName }</td>
								<td colspan="1">${accessoryCost.tearPerPiece }</td>
								<td colspan="1">${orderInfo.order.askAmount}</td> 
								<td colspan="2"><span id="piecesPurchasedAll">${(accessoryCost.tearPerPiece)*(orderInfo.order.askAmount)}</span></td>
	
							</tr>
						</c:forEach>
					</table>
					<a href="${ctx}/finance/printProcurementOrder.do?orderId=${orderInfo.order.orderId}"
							class="btn btn-primary btn-rounded" target="_blank">打印补货单</a>
					<a	href="${ctx}/buy/purchaseMaterialSubmit.do?taskId=${orderInfo.task.id}&result=0"
							onclick="return confirm('确认采购失败？')"
							class="btn btn-danger btn-rounded">
							<i class="icon-remove icon-white"></i>采购失败</a>		
					
					<div class="action" style="float:right">
						
						<a	href="${ctx}/buy/purchaseMaterialSubmit.do?taskId=${orderInfo.task.id}&result=1"
							onclick="return confirm('确认已经打印大货补货单？')"
							class="btn btn-primary btn-rounded">
							<i class="icon-ok icon-white"></i>采购完成</a>
					</div>
					<br>
					<button class="btn btn-primary" onclick="history.back();">返回</button>
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
<link rel="stylesheet" href="${ctx}/views/buy/buy.css">
<script type="text/javascript" src="${ctx}/views/buy/cost.js"></script>
<script type="text/javascript" src="${ctx}/js/order/add_order.js"></script>
<script type="text/javascript" src="${ctx }/js/custom.js"></script>
<script type="text/javascript">
	var text=$("#metersPurchasedAll").text();
	$("#metersPurchasedAll").text(parseFloat(text).toFixed(2));
	var text=$("#piecesPurchasedAll").text();
	$("#piecesPurchasedAll").text(parseFloat(text).toFixed(2));
<<<<<<< HEAD
	
	function fail(){
		$("#result").val(0);
		return confirm("确认采购失败？");
	}
	function success(){
		$("#result").val(1);
		return confirm("确认已经核对过补货单？");
	}
	 $(function(){
	        $("#fdd").click(function(){
	            var _this = $(this);//将当前的pimg元素作为_this传入函数
	            imgShow("#outerdiv", "#innerdiv", "#bigimg", _this);
	        });
	    });
=======
>>>>>>> 1a64da1fd8da8bc8e1eef63b2b6962ac7ab8d644
</script>
<style type="text/css">
* {margin:0;padding:0;}
#imglist {list-style:none; width:500px; margin:50px auto;}
#imglist li {float:left; margin-top:10px;}
</style>
<%@include file="/common/footer.jsp"%>
