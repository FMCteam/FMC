<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>

<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
			<!--  如果是其它页面，这里是填充具体的内容。 -->
			<section class="list">
				<table id="dyntable" class="list">
					<caption>
						<span class="text-vertical">顾客:<span class="number">${fn:length(customer_list)}</span>个
						</span><input type="text" class="search-query float-right"
							placeholder="输入检索条件">
					</caption>
					<thead>
						<tr>
							<th>客户姓名</th>
							<th>客户电话</th>
							<th>客户地址</th>
							<th>公司名称</th>
							<th>公司电话</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="customer" items="${customer_list}">
							<tr>
								<td>${customer.customerName }</td>
								<td>${customer.customerPhone }</td>
								<td>${customer.companyAddress }</td>
								<td>${customer.companyName }</td>
								<td>${customer.companyPhone }</td>
								<td><a
									href="${ctx}/account/modifyCustomerDetail.do?customer_id=${customer.customerId}"
									class="btn btn-info" title="修改"><i
										class="iconsweets-create iconsweets-white"></i></a> <a
									href="${ctx}/account/deleteCustomerSubmit.do?customer_id=${customer.customerId}"
									class="btn btn-danger" title="删除"><i
										class="iconsweets-trashcan iconsweets-white"></i></a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</section>
			<div class="dataTables_paginate paging_full_numbers"
				id="dyntable_paginate" style="float:right;margin-top: 20px">
				<c:if test="${page==1 }">
					<a tabindex="0"
						class="first paginate_button paginate_button_disabled"
						id="dyntable_first">首页</a>
					<a tabindex="0"
						class="previous paginate_button paginate_button_disabled"
						id="dyntable_previous">上一页</a>
				</c:if>
				<c:if test="${page>1 }">
					<a tabindex="0" class="first paginate_button" id="dyntable_first"
						href="${ctx }/customer/search.do?page=1&number_per_page=1">首页</a>
					<a tabindex="0" class="previous paginate_button"
						id="dyntable_previous"
						href="${ctx }/customer/search.do?page=${page-1 }&number_per_page=1">上一页</a>
				</c:if>
				<c:if test="${page_number<6&&page_number>0}">
					<c:forEach var="i" begin="1" end="${page_number }">
						<c:if test="${page!=i }">
							<a tabindex="0" class="paginate_button"
								href="${ctx }/customer/search.do?page=${i }&number_per_page=1">${i }</a>
						</c:if>
						<c:if test="${page==i }">
							<a tabindex="0" class="paginate_active"
								href="${ctx }/customer/search.do?page=${i }&number_per_page=1">${i }</a>
						</c:if>
					</c:forEach>
				</c:if>
				<c:if test="${page_number>5}">
					<c:choose>
						<c:when test="${page<3 }">
							<c:forEach var="i" begin="1" end="5">
								<c:if test="${page!=i }">
									<a tabindex="0" class="paginate_button"
										href="${ctx }/customer/search.do?page=${i }&number_per_page=1">${i }</a>
								</c:if>
								<c:if test="${page==i }">
									<a tabindex="0" class="paginate_active"
										href="${ctx }/customer/search.do?page=${i }&number_per_page=1">${i }</a>
								</c:if>
							</c:forEach>
						</c:when>
						<c:when test="${page>page_number-3 }">
							<c:forEach var="i" begin="${page_number-4 }"
								end="${page_number }">
								<c:if test="${page!=i }">
									<a tabindex="0" class="paginate_button"
										href="${ctx }/customer/search.do?page=${i }&number_per_page=1">${i }</a>
								</c:if>
								<c:if test="${page==i }">
									<a tabindex="0" class="paginate_active"
										href="${ctx }/customer/search.do?page=${i }&number_per_page=1">${i }</a>
								</c:if>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<c:forEach var="i" begin="${page-2 }" end="${page+2 }">
								<c:if test="${page!=i }">
									<a tabindex="0" class="paginate_button"
										href="${ctx }/customer/search.do?page=${i }&number_per_page=1">${i }</a>
								</c:if>
								<c:if test="${page==i }">
									<a tabindex="0" class="paginate_active"
										href="${ctx }/customer/search.do?page=${i }&number_per_page=1">${i }</a>
								</c:if>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</c:if>
				<c:if test="${page<page_number }">
					<a tabindex="0" class="next paginate_button" id="dyntable_next"
						href="${ctx }/customer/search.do?page=${page+1 }&number_per_page=1">下一页</a>
					<a tabindex="0" class="last paginate_button" id="dyntable_last"
						href="${ctx }/customer/search.do?page=${page_number }&number_per_page=1">尾页</a>
				</c:if>
				<c:if test="${page==page_number&&page_number>0 }">
					<a tabindex="0"
						class="next paginate_button paginate_button_disabled"
						id="dyntable_next">下一页</a>
					<a tabindex="0"
						class="last paginate_button paginate_button_disabled"
						id="dyntable_last">尾页</a>
				</c:if>
			</div>

		</div>
		<!--row-fluid-->
	</div>
	<!--maincontentinner-->
</div>
<!--maincontent-->


<%@include file="/common/js_file.jsp"%>
<script type="text/javascript"
	src="${ctx}/js/jquery.noty.packaged.min.js"></script>
<script>
	if ('${notify}' != null && '${notify}' != "") {
		noty({
			text : '${notify}',
			layout : 'topCenter',
			timeout : 2000
		});
	}
</script>
<!-- 这里引入你需要的js文件 -->
<script type="text/javascript" src="${ctx }/js/custom.js"></script>
<link rel="stylesheet" href="${ctx}/css/fmc/table.css">
<script type="text/javascript" src="${ctx}/js/fmc/table.js"></script>
<%@include file="/common/footer.jsp"%>
