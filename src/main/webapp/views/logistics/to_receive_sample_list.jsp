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
					<th>寄件人</th>
					<th>快递名称</th>
					<th>快递单号</th>
					<th>邮寄时间</th>
					<th>操作</th>
				</tr>
				<tr>
					<td>0000000001</td>
					<td>0000000001</td>
					<td>二哥</td>
					<td>二哥</td>
					<td>申通</td>
					<td>XA565466666666</td>
					<td>2013-12-12</td>
					<td><a href="">已收货</a>&nbsp&nbsp&nbsp<a href="">未收货</a>&nbsp&nbsp&nbsp<a href="">详情</a></td>
				</tr>
				  <tbody>
				<c:forEach var="order" items="${orderList}" >
                     <tr class="gradeA">
                     <td>${order.getOrderId() }</td>
                   
                     <td>${order.getCustomerName()}</td>
					<td>${order.getEmployeeId()}</td>
					<td>
<a href="${ctx }/logistics/sampleOrderRequest.do?confirm=1&&orderId=${order.orderId }" class="btn btn-info" title="收到"><i class="iconsweets-create iconsweets-white"></i></a>
<a href="${ctx }/logistics/sampleOrderRequest.do?confirm=0&&orderId=${order.orderId }" class="btn btn-danger" title="未收到"><i class="iconsweets-trashcan iconsweets-white"></i></a></td>
                     </tr>
                </c:forEach>
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
<link rel="stylesheet" href="${ctx}/css/logistics/to_receive_sample_list.css">
<script type="text/javascript" src="${ctx }/js/custom.js"></script>
<%@include file="/common/footer.jsp"%>