<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>



<div class="maincontent">
	<div class="maincontentinner">
		<form id="verifyAlterDetailForm" class="verify" method="post" action="${ctx}/market/verifyAlterSubmit.do" >
			<div class="row-fluid" style="min-height:300px;">
				<!--  如果是其它页面，这里是填充具体的内容。 --><!--onSubmit="return quote_verify()"  -->
				<ul class="nav nav-tabs detail" id="tab">
					<li class="task-name">审核变更申请</li>
					<li  class="active"><a href="#alterApply" data-toggle="tab">变更申请信息</a></li>
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
					<div class="tab-pane  active" id="alterApply">
						<!-- @include file="/views/common/quote_w.jsp" --> 
                        <td class="span2">原因</td>
                        <textarea class="span12"
							disabled="disabled"	style="resize:vertical" rows="5" name="reason">${AlterInfo.reason}</textarea>
					</div>
				</div>
 				<table class="table table-striped table-bordered table-hover detail">  
 				    
					<tr>
						<td class="span2">意见</td>
						<td colspan="6"><textarea class="span12"
								style="resize:vertical" rows="5" name="suggestion"></textarea></td>
					</tr>
					<tr>
						<td>操作</td>
						<td colspan="6">
				            <input type="hidden" name="taskId" value="${AlterInfo.taskid}" />
				            <input type="hidden" name="order_id" value="${AlterInfo.orderId }" />
				            <input type="hidden" name="order_id" value="${AlterInfo.alterId }" />
				           <!-- <input type="hidden" name="processId" value="${orderInfo.task.processInstanceId}" />-->
				            <input type="hidden" name="verifyAlterSuccessVal" id="verifyAlterSuccess_val" value="" /> 
				             <input type="hidden" name="verifyState" id="verifyState_val" value="" /> 
				            <a id="disagree" class="btn btn-danger btn-rounded" style="float: left;">
								<i class="icon-remove icon-white"></i> 拒绝</a>
							<a id="agree" class="btn btn-primary btn-rounded" style="float: right;">
								<i class="icon-ok icon-white"></i> 同意</a> 
							
						</td>
                        
					</tr>
                   
                    <tr id="changeStaff">
						<td>变更专员</td>
                        <td> <select name="nextEmployeeId" id="nextEmployee">
                             <c:forEach var="employee" items="${employeeList}">       
                                    <option value="${employee.employeeId}">${employee.employeeName}</option>
                              </c:forEach>     
                              </select>  
                              <a id="agreeChange" class="btn btn-primary btn-rounded" style="float: right;">
							 <i class="icon-ok icon-white"></i> 确认</a>
                               </td>
                             
                    </tr>
                    
                   
				</table>
				
			</div>
			<!-- 
			
			 -->
			<!-- 
			<div class="action">
				<input type="hidden" name="taskId" value="${orderInfo.task.id}" />
				<input type="hidden" name="order_id" value="${orderInfo.quote.orderId }" />
				<input type="hidden" name="processId" value="${orderInfo.task.processInstanceId}" />
				<button class="btn btn-primary btn-rounded">
					<i class="icon-ok icon-white"></i>确定
				</button>
			</div>
			 -->	
		</form>
		
		<button class="btn btn-primary" onclick="history.back(-1);">返回</button>
		
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
<script type="text/javascript" src="${ctx}/js/order/add_order.js"></script>
<script type="text/javascript" src="${ctx}/js/order/add_quote.js"></script>
<script type="text/javascript" src="${ctx }/js/custom.js"></script>
<script type="text/javascript" src="${ctx}/views/market/verifyAlterDetail.js"></script>
<%@include file="/common/footer.jsp"%>
