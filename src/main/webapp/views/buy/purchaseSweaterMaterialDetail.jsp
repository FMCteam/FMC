<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>

<div class="maincontent">
	<div class="maincontentinner">
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
					<br />	
				
				<!-- 
						<div class="action" style="float:right">
							<button class="btn btn-primary btn-rounded noreapt">
								<i class="icon-ok icon-white"></i>采购完成
							</button>	
						</div>
						<br>
				 <input type="text" value=${ orderInfo.task.id}/>
				 <br>
				 <input type="text" value=${ orderInfo.order.orderId}/>
				 <input type="submit" name ="完成采购" value="完成采购" />
				 -->
		</form>
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
