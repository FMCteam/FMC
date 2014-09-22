<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>

<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
			<!--  如果是其它页面，这里是填充具体的内容。 -->
			<ul class="nav nav-tabs detail" id="tab">
				<li class="task-name">购买毛衣原料</li>
				<li class="active"><a href="#buySweaterMaterial" data-toggle="tab">购买毛衣原料</a> </li>
 				<li><a href="#quote" data-toggle="tab">报价信息</a></li>
				<li><a href="#cad" data-toggle="tab">版型信息</a></li>
				<!-- 
				<li><a href="#produce" data-toggle="tab">加工信息</a></li>
				 -->
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
				<!-- 
				<div class="tab-pane" id="produce">
					<%@include file="/views/common/produce.jsp"%>
				</div>
				 -->
				<div class="tab-pane" id="cad">
					<%@include file="/views/common/cad.jsp"%>
				</div>
				<div class="tab-pane" id="quote">
					<%@include file="/views/common/quote.jsp"%>
				</div>
				<div class="tab-pane active" id="buySweaterMaterial">			 
		             <form onsubmit="return confirm('确认操作？')" method="post"
				           action="${ctx}/buy/purchaseSweaterMaterialSubmit.do?taskId=${orderInfo.task.id}&orderId=${orderInfo.order.orderId}">
				
				     <input type="hidden" id="result" name="result"/>
				    
				     <button
							style="margin-left:0px"
							class="btn btn-danger btn-rounded" onclick="return fail()">
							<i class="icon-remove icon-white"></i>采购到毛衣原料</button>
					
					 <div class="action" style="float:right">
						<button class="btn btn-primary btn-rounded noreapt" onclick="return success()">
							<i class="icon-ok icon-white"></i>仓库有毛衣原料
						</button>
					 </div>
		             </form>
	
				</div>
			</div>
		</div>
	
	
	
		
		
		
		<button class="btn btn-primary" onclick="history.back();">返回</button>
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
<link rel="stylesheet" href="${ctx}/css/order/add_order.css">
<link rel="stylesheet" href="${ctx}/css/fmc/detail.css">
<link rel="stylesheet" href="${ctx}/views/buy/buy.css">
<script type="text/javascript" >
	function fail(){
		$("#result").val(1);
		return confirm("确认采购到毛衣原料？");
	}
	function success(){
		$("#result").val(0);
		return confirm("确认仓库有毛衣原料？");
	}
</script>
<%@include file="/common/footer.jsp"%>
