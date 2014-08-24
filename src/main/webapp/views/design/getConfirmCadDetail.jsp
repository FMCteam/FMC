
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%@include file="/common/header.jsp"%>
<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
			<ul class="nav nav-tabs detail" id="tab">
				<li class="task-name">确认版型数据</li>
				<li><a href="#quote" data-toggle="tab">报价信息</a></li>
				<li class="active"><a href="#cad" data-toggle="tab">版型信息</a></li>
				<li><a href="#produce" data-toggle="tab">加工信息</a></li>
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
				<div class="tab-pane" id="produce">
					<%@include file="/views/common/produce.jsp"%>
				</div>
				<div class="tab-pane active" id="cad">
					<%@include file="/views/common/cad.jsp"%>
				</div>
				<div class="tab-pane" id="quote">
					<%@include file="/views/common/quote.jsp"%>
				</div>
			</div>

			<form action="${ctx }/design/confirmCadSubmit.do" method="post"
				onsubmit="return check()" enctype="multipart/form-data">
				<table class="table table-striped table-bordered table-hover detail">
					<tr>
						<td>选择版型文件</td>
						<td colspan="3"><a style="color:red;">*</a><input name="CADFile" id="CADFile"
							type="file" required="required" /><input type="hidden" name="orderId"
							value="${orderInfo.order.orderId }" /> <input type="hidden"
							name="taskId" value="${orderInfo.taskId }" /></td>
						<td colspan="3"><input type="submit"
							class="btn btn-primary btn-rounded" /></td>
					</tr>
				</table>
			</form>
	<button class="btn btn-primary" onclick="history.back();">返回</button>
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

<script type="text/javascript" >
	function check(){
		var CADFile = document.getElementById("CADFile").value;
		var CADFilestr = CADFile.substr(CADFile.indexOf(".")).toLowerCase();		

			if(CADFilestr == ".jpg" || CADFilestr == ".png"){	
				if(confirm('确认提交？')){
					return true;
				}else{
					return false;
				}
			}else{
				alert("版型格式不对，请上传jpg或png格式的图片！");
				return false;
			}
	}
</script>

<%@include file="/common/js_file.jsp"%>
<%@include file="/common/js_form_file.jsp"%>
<link rel="stylesheet" href="${ctx}/css/fmc/table.css">
<script type="text/javascript" src="${ctx}/js/fmc/table.js"></script>
<link rel="stylesheet" href="${ctx}/css/fmc/detail.css">
<script type="text/javascript" src="${ctx}/js/order/add_order.js"></script>
<script type="text/javascript" src="${ctx }/js/custom.js"></script>
<%@include file="/common/footer.jsp"%>

