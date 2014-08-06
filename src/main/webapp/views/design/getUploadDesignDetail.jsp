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
					<form action="${ctx}/design/uploadDesignSubmit.do" method="post"
				          onsubmit="return check()" enctype="multipart/form-data">
				    	<table class="table table-striped table-bordered table-hover detail">
							<tr>
								<td class="title">版型文件<span style="color:red">*</span></td>
								<td>
									<a style="color: red;">*</a>
									<input name="CADFile" id="CADFile" type="file" required="required"/> 
									<input type="hidden" name="orderId" value="${orderInfo.order.orderId }" /> 
									<input type="hidden" name="taskId" value="${orderInfo.taskId }" />
								</td>
								<td rowspan="3"><input type="submit" value="录入版型数据"
									class="btn btn-primary btn-rounded">
								</td>
							</tr>
							<tr>
								<td class="title">制版人<span style="color:red">*</span></td>
								<td>
									<input name="cadSide" id="cad_side" type="text" required="required"/> 
								</td>
							</tr>
							<tr>
								<td class="title">制版完成时间<span style="color:red">*</span></td>
								<td>
									<input name="completeTime" id="input_day"  type="date"  required="required" /> 
								</td>
							</tr>
				    	</table>
			       </form>
			       <div class="action">
						<a href="${ctx}/design/produceSampleSubmit.do?orderId=${orderInfo.order.orderId}&taskId=${orderInfo.task.id}&result=1"
							onclick="return checkcad()"  
							class="btn btn-primary">加工完成</a> 
					</div>
							<a href="${ctx}/design/produceSampleSubmit.do?orderId=${orderInfo.order.orderId}&taskId=${orderInfo.task.id}&result=0" 
							onclick="return confirm('确认加工失败？')"
							class="btn btn-danger" style="color:white; ">加工失败</a>
				   </div>

				<div class="tab-pane" id="produce">
					<%@include file="/views/common/produce.jsp"%>
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

 <script type="text/javascript" >

 	function check(){
 		var CADFile = document.getElementById("CADFile").value;
		var CADFilestr = CADFile.substr(CADFile.indexOf(".")).toLowerCase();		
		if(CADFile.length != 0){
			if(CADFilestr == ".jpg" || CADFilestr == ".png"){	
				if(confirm('确认录入版型数据？')){
					return true;
				}else{
					return false;
				}
			}else{
				alert("版型格式不对，请上传jpg或png格式的图片！");
				return false;
			}
		}else{
			alert("请上传版型文件！");
			return false;
		}
 	}
 	

 	function checkcad(){
 		var cadUrl = $("#cadUrl").val();
 		 if(cadUrl != null){
 			 if(confirm('确认完成？')){
 					return true;
 				}else{
 					return false;
 				}
 		}else{ 
 				alert("请上传版型文件");
 				return false;
 		 } 
 	}
 </script>

<%@include file="/common/js_file.jsp"%>
<%@include file="/common/js_form_file.jsp"%>
<link rel="stylesheet" href="${ctx}/css/fmc/table.css">
<script type="text/javascript" src="${ctx}/js/fmc/table.js"></script>
<link rel="stylesheet" href="${ctx}/css/fmc/detail.css">
<link rel="stylesheet" href="${ctx}/css/order/add_order.css">
<script type="text/javascript" src="${ctx}/js/order/add_produce.js"></script>
<script type="text/javascript" src="${ctx }/js/custom.js"></script>
<script type="text/javascript" src="${ctx}/js/order/add_order.js"></script>
<%@include file="/common/footer.jsp"%>
