
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
		
		<%@include file="/common/header.jsp" %>


<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
		
				<ul class="nav nav-tabs detail" id="tab">
				<li class="task-name">确认版型数据</li>
					<li><a href="#quote" data-toggle="tab">报价信息</a></li>
					<li><a href="#cad" data-toggle="tab">版型信息</a></li>
					<li><a href="#produce" data-toggle="tab">加工信息</a></li>
					<li><a href="#sample" data-toggle="tab">样衣信息</a></li>
					<li><a href="#material" data-toggle="tab">面辅信息</a></li>
					<li class="active"><a href="#basic" data-toggle="tab">基本信息</a></li>
				</ul>
	
				<div class="tab-content">
					<div class="tab-pane active" id="basic">
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
				</div>
				
			<form action="${ctx }/design/downloadCadSubmit.do" method="post"
			onsubmit="return confirm('确认提交？')" enctype="multipart/form-data">
				<table class="table table-striped table-bordered table-hover">
					<tr>
						<td>下载文件</td>
						<td>版本：${orderInfo.cad.cadVersion}</td>
						<td>上传时间：${orderInfo.cad.uploadTime}</td>
						<td>
							<input type="submit" class="btn btn-primary btn-rounded" value="下载"/>
						</td>
					</tr>
				</table>
				<input type="hidden" name="cadUrl" value="${orderInfo.cad.cadUrl}" />
				<input type="hidden" name="orderId" value="${orderInfo.order.orderId}"/>
				<input type="hidden" name="taskId" value="${orderInfo.taskId}" />
			</form>

			<form action="${ctx }/design/confirmDesignSubmit.do" method="post"
				enctype="multipart/form-data">
				<table class="table table-striped table-bordered table-hover">
					<tr>
						<td>选择文件</td>
						<td colspan="3">
							<input name="CADFile" id="CADFile" type="file" /> 
							<input type="hidden" name="orderId" value="${orderInfo.order.orderId }" />
							<input type="hidden" name="taskId" value="${orderInfo.taskId }" />
						</td>
						<td colspan="3">
							<input type="submit" class="btn btn-primary btn-rounded" />
						</td>
					</tr>
				</table>
			</form>

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

<%@include file="/common/js_file.jsp" %>

        
        <!-- 这里引入你需要的js文件 -->
        <script type="text/javascript" src="${ctx }/js/custom.js"></script>
        
        
        <%@include file="/common/footer.jsp" %>
    
