<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>



<div class="maincontent">
	<div class="maincontentinner">
		<form id="AllocateOrderForm" class="verify" method="post" action="${ctx}/market/AllocateOrderSubmit.do" ">
			<div class="row-fluid" style="min-height:300px;">
				<!--  如果是其它页面，这里是填充具体的内容。 -->
				<ul class="nav nav-tabs detail" id="tab">
					<li class="task-name">分配订单</li>
					<li class="active"><a href="#cad" data-toggle="tab">版型信息</a></li>
					<li><a href="#produce" data-toggle="tab">加工信息</a></li>
					<li><a href="#sample" data-toggle="tab">样衣信息</a></li>
					<li><a href="#material" data-toggle="tab">面辅信息</a></li>
					<li><a href="#basic" data-toggle="tab">基本信息</a></li> 
				</ul>

				
				<div class="tab-content">
					<div class="tab-pane  active" id="basic">
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
					
				</div>
				
 				<table class="table table-striped table-bordered table-hover detail">
					
					
                   <input type="hidden" name="order_id" value="${orderInfo.order.orderId }" />
                    <tr id="changeStaff">
						<td>分配专员</td>
                        <td> <select name="nextEmployeeId" id="nextEmployee"
									  >
                             <c:forEach var="employee" items="${employeeList}">       
                                    <option value="${employee.employeeId}">${employee.employeeName}</option>
                                    
                               </c:forEach>     
                             </select> 
                              <a id="agreeChange" class="btn btn-primary btn-rounded" style="float: right;">
							 <i class="icon-ok icon-white"></i> 确认</a>  </td>
				</table>
				
			</div>
			<!-- 
			
			 -->
			<!-- 
			<div class="action">
				<input type="hidden" name="taskId" value="${orderInfo.task.id}" />
				 <input type="hidden" name="order_id" value="${alterModel.alterInfo.orderId }" />
				 
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

<script type="text/javascript" src="${ctx}/views/market/allocateOrder.js"></script>
<%@include file="/common/footer.jsp"%>
