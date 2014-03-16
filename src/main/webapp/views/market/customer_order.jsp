
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>


<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
			<!--  如果是其它页面，这里是填充具体的内容。 -->
			<h4 class="widgettitle">查看用户</h4>
			<table id="dyntable" class="table table-bordered responsive">

				<colgroup>
					<col class="con1" />
					<col class="con0" />
					<col class="con1" />
					<col class="con0" />
					<col class="con1" />
					<col class="con0" />
				</colgroup>
				<thead>
					<tr>
						<th class="head0">客户姓名</th>
						<th class="head1">客户电话</th>
						<th class="head0">客户地址</th>
						<th class="head1">公司名称</th>
						<th class="head0">公司电话</th>
						<th class="head1">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="customer" items="${customer_list }">
						<tr class="gradeA">
							<td>${customer.customerName }</td>
							<td>${customer.customerPhone }</td>
							<td>${customer.companyAddress }</td>
							<td>${customer.companyName }</td>
							<td>${customer.companyPhone }</td>
							<td><a
								href="${ctx}/market/add.do?cid=${customer.customerId}">下单</a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<!--row-fluid-->
	</div>
	<!--maincontentinner-->
</div>
<!--maincontent-->


<%@include file="/common/js_file.jsp"%>
<!-- 这里引入你需要的js文件 -->
<script type="text/javascript" src="${ctx }/js/custom.js"></script>
<style type="text/css">
</style>
<%@include file="/common/footer.jsp"%>
