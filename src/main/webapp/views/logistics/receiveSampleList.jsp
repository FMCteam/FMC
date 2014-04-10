<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>






<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
			<!--  如果是其它页面，这里是填充具体的内容。 -->
			<table class="table table-stripe">
				<tr>
					<th>询单编号</th>
					<th>客户编号</th>
					<th>客户姓名</th>

					<th>快递名称</th>
					<th>快递单号</th>
					<th>邮寄时间</th>
					<th>操作</th>
				</tr>
				<c:forEach var="task" items="${list}">
					<tr>
						<td>${task.id}</td>
						<td></td>
						<td></td>
						<td></td>
						
						<td></td>
						<td></td>
					<td>
						<form action="${ctx }/logistics/receiveSampleSubmit.do" method="post" style="display: inline;">
									<input type="hidden" name="confirm" value="1" /><input type="hidden" name="orderId" value="${order.getO().getOrderId() }" />
									<input type="hidden" name="taskId" value="${order.getTaskId() }" />
									<input type="hidden" name="processInstanceId" value="${order.getProcessId() }" />
									<button class="btn btn-primary btn-rounded"><i class="icon-ok icon-white"></i> 收到</button></form>
									<form action="${ctx }/logistics/receiveSampleSubmit.do" method="post" style="display: inline;">
									<input type="hidden" name="confirm" value="0" /><input type="hidden" name="orderId" value="${order.getO().getOrderId() }" />
									<input type="hidden" name="taskId" value="${order.getTaskId() }" />
									<input type="hidden" name="processInstanceId" value="${order.getProcessId() }" />
									<button class="btn btn-danger btn-rounded"><i class="icon-remove icon-white"></i> 未收到</button></form>
									</td>
					</tr>
				</c:forEach>

				<tbody>

				</tbody>
			</table>
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
<link rel="stylesheet"
	href="${ctx}/css/logistics/to_receive_sample_list.css">
<script type="text/javascript" src="${ctx }/js/custom.js"></script>
<%@include file="/common/footer.jsp"%>