
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%@include file="/common/header.jsp"%>


<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">

			<ul class="nav nav-tabs detail" id="tab">
				<li class="task-name">录入版型数据及生产样衣</li>
 				<li ><a href="#produce" data-toggle="tab">加工信息</a></li>
				<li class="active"><a href="#cad" data-toggle="tab">版型信息</a></li>
 
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

				<div class="tab-pane active" id="cad">
					<%@include file="/views/common/cad.jsp"%>
					<form action="${ctx }/design/uploadDesignSubmit.do" method="post"
				          onsubmit="return confirm('确认上传？')" enctype="multipart/form-data">
				<table class="table table-striped table-bordered table-hover detail">
					<tr>
						<td>选择版型文件</td>
						<td colspan="3">
							<a style="color: red;">*</a>
							<input name="CADFile" id="CADFile" type="file" required="required"/> 
							<input type="hidden" name="orderId" value="${orderInfo.order.orderId }" /> 
							<input type="hidden" name="taskId" value="${orderInfo.taskId }" />
						</td>
						<td colspan="3"><input type="submit"
							class="btn btn-primary btn-rounded"></td>
					</tr>
				</table>
			</form>
 
				</div>
				<div class="tab-pane active" id="produce">
					<%@include file="/views/common/produce.jsp"%>
					<!-- 
			    <button class="btn btn-primary" onclick="history.back();">返回</button>
					 -->
					<div class="action">
						<a href="${ctx}/design/produceSampleSubmit.do?taskId=${orderInfo.task.id}&result=1"
							class="btn btn-primary">加工完成</a> 
						<a href="${ctx}/design/produceSampleSubmit.do?taskId=${orderInfo.task.id}&result=0"
							class="btn btn-danger" style="color:white; margin-left:10px">加工失败</a>
					</div>
				</div>
			</div>

			
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

 

<%@include file="/common/js_file.jsp"%>
<%@include file="/common/js_form_file.jsp"%>
<link rel="stylesheet" href="${ctx}/css/fmc/table.css">
<script type="text/javascript" src="${ctx}/js/fmc/table.js"></script>
<link rel="stylesheet" href="${ctx}/css/fmc/detail.css">
<link rel="stylesheet" href="${ctx}/css/order/add_order.css">
<script type="text/javascript" src="${ctx}/js/order/add_produce.js"></script>
<script type="text/javascript" src="${ctx }/js/custom.js"></script>
<%@include file="/common/footer.jsp"%>
