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
				<c:forEach var="order" items="${orderList}">
					<tr>
						<td>${order.getO().getOrderId() }</td>
						<td>${order.getO().getCustomerId() }</td>
						<td>${order.getO().getCustomerName()}</td>
						<td>${order.getLog().getInPostSampleClothesType() }</td>
						
						<td>${order.getLog().getInPostSampleClothesNumber() }</td>
						<td>${fn:substring(order.getLog().getInPostSampleClothesTime(),0,10) }</td>
					<td>
						<form action="${ctx }/logistics/sampleOrderRequest.do" method="post" style="display: inline;">
									<input type="hidden" name="confirm" value="1" /><input type="hidden" name="orderId" value="${order.getO().getOrderId() }" />
									<input type="hidden" name="taskId" value="${order.getTaskId() }" />
									<input type="hidden" name="processInstanceId" value="${order.getProcessId() }" />
									<button class="btn btn-primary btn-rounded"><i class="icon-ok icon-white"></i> 收到</button></form>
									<form action="${ctx }/logistics/sampleOrderRequest.do" method="post" style="display: inline;">
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
		  <div class="dataTables_paginate paging_full_numbers" id="dyntable_paginate" style="float:right">
                	<c:if test="${page==1 }">
	                	<a tabindex="0" class="first paginate_button paginate_button_disabled" id="dyntable_first">首页</a>
						<a tabindex="0" class="previous paginate_button paginate_button_disabled" id="dyntable_previous">上一页</a>
                	</c:if>
					<c:if test="${page>1 }">
	                	<a tabindex="0" class="first paginate_button" id="dyntable_first" href="${ctx }/logistics/sampleOrderList.do?page=1&number_per_page=10">首页</a>
						<a tabindex="0" class="previous paginate_button" id="dyntable_previous" href="${ctx }/logistics/sampleOrderList.do?page=${page-1 }&number_per_page=10">上一页</a>
                	</c:if>
					<c:if test="${page_number<6&&page_number>0}">
						<c:forEach var ="i" begin="1" end="${page_number }">
							<c:if test="${page!=i }"><a tabindex="0" class="paginate_button" href="${ctx }/logistics/sampleOrderList.do?page=${i }&number_per_page=10">${i }</a></c:if>
							<c:if test="${page==i }"><a tabindex="0" class="paginate_active" href="${ctx }/logistics/sampleOrderList.do?page=${i }&number_per_page=10">${i }</a></c:if>
						</c:forEach>
					</c:if>
					<c:if test="${page_number>5}">
						<c:choose>
							<c:when test="${page<3 }">
								<c:forEach var ="i" begin="1" end="5">
									<c:if test="${page!=i }"><a tabindex="0" class="paginate_button" href="${ctx }/logistics/sampleOrderList.do?page=${i }&number_per_page=10">${i }</a></c:if>
									<c:if test="${page==i }"><a tabindex="0" class="paginate_active" href="${ctx }/logistics/sampleOrderList.do?page=${i }&number_per_page=10">${i }</a></c:if>
								</c:forEach>
							</c:when>
							<c:when test="${page>page_number-3 }">
								<c:forEach var ="i" begin="${page_number-4 }" end="${page_number }">
									<c:if test="${page!=i }"><a tabindex="0" class="paginate_button" href="${ctx }/logistics/sampleOrderList.do?page=${i }&number_per_page=10">${i }</a></c:if>
									<c:if test="${page==i }"><a tabindex="0" class="paginate_active" href="${ctx }/logistics/sampleOrderList.do?page=${i }&number_per_page=10">${i }</a></c:if>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<c:forEach var ="i" begin="${page-2 }" end="${page+2 }">
									<c:if test="${page!=i }"><a tabindex="0" class="paginate_button" href="${ctx }/logistics/sampleOrderList.do?page=${i }&number_per_page=10">${i }</a></c:if>
					 				<c:if test="${page==i }"><a tabindex="0" class="paginate_active" href="${ctx }/logistics/sampleOrderList.do?page=${i }&number_per_page=10">${i }</a></c:if>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</c:if>
					<c:if test="${page<page_number }">
	                	<a tabindex="0" class="next paginate_button" id="dyntable_next" href="${ctx }/logistics/sampleOrderList.do?page=${page+1 }&number_per_page=10">下一页</a>
						<a tabindex="0" class="last paginate_button" id="dyntable_last" href="${ctx }/logistics/sampleOrderList.do?page=${page_number }&number_per_page=10">尾页</a>
                	</c:if>
					<c:if test="${page==page_number }">
	                	<a tabindex="0" class="next paginate_button paginate_button_disabled" id="dyntable_next">下一页</a>
						<a tabindex="0" class="last paginate_button paginate_button_disabled" id="dyntable_last">尾页</a>
                	</c:if>
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