<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>

<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
			<!--  如果是其它页面，这里是填充具体的内容。 -->
			<ul class="nav nav-tabs detail" id="tab">
				<li class="task-name">批量生产</li>
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
					<form method="post" action="${ctx}/produce/produceSubmit.do" onsubmit="return deal()">
						<table class="table table-striped table-bordered table-hover detail">
							<tr>
								<th>要求件数</th>
								<th>要求大小</th>
								<th>实际件数</th>
							</tr>
							<c:forEach var="product" items="${task.products}">
								<tr>
								<td>${product.askAmount}</td>
								<td>${product.style}</td>
								<td><input type="text" class="produceAmount" value="${product.askAmount}"/>
								<input class="pid" type="hidden" value="${product.id}"></td>
								</tr>
							</c:forEach>
							<tr>
								<td colspan="3"><input type="submit" class="btn btn-danger btn-rounded"></td>
							</tr>
						</table>
						<input id="pid" type="hidden" name="pid"/>
						<input id="produceAmount" type="hidden" name="produceAmount"/>
						<input id="taskId" type="hidden" name="taskId" value="${task.taskId}"/>
					</form>
				</div>
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
<script type="text/javascript" src="${ctx}/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="${ctx}/js/order/add_order.js"></script>
<script type="text/javascript" src="${ctx }/js/custom.js"></script>
<script type="text/javascript">
function deal(){
	
	$("#pid").val(getString("pid"));
	$("#produceAmount").val(getString("produceAmount"));
	return true;
}

function getString(col){
	var tdString="";
	var i=0;
	for(;i<$("."+col).length-1;i++){
		tdString+=$("."+col).eq(i).val()+",";
	}
	tdString+=$("."+col).eq(i).val();
	return tdString;
}

</script>
<%@include file="/common/footer.jsp"%>
