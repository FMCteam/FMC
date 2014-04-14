<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%@include file="/common/header.jsp"%>


<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">

			<!--  如果是其它页面，这里是填充具体的内容。 -->
			<section class="list">
			
			<table id="dyntable" class="list">
			<caption>
						<span class="text-vertical">合并报价:<span class="number">${fn:length(quote_list)}</span>件任务
						</span><input type="text" class="search-query float-right"
							placeholder="输入检索条件">
					</caption>
				<colgroup>
					<col class="con1" />
					<col class="con0" />
					<col class="con1" />
					<col class="con0" />
					<col class="con1" />
					<col class="con0" />
					<col class="con1" />
					<col class="con0" />
				</colgroup>
				<thead>
					<tr>
						<th class="head0">订单号</th>
						<th class="head0">内部报价</th>
						<th class="head0">外部报价</th>
						<th class="head0">操作</th>
						
					</tr>
				</thead>
				<tbody>
				
					<c:forEach var="quoteModel" items="${quote_list}">
						<tr class="gradeA">
							<td>${quoteModel.quote.orderId }</td>
							<td>${quoteModel.quote.innerPrice }</td>
							<td>${quoteModel.quote.outerPrice }</td>
						<td> <a href="${ctx}/market/mergeQuoteDetail.do?orderId=${quoteModel.quote.orderId }&taskId=${quoteModel.task.id }">合并</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table></section>
			<div class="dataTables_paginate paging_full_numbers"
				id="dyntable_paginate" style="float:right">
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
						href="${ctx }/employee/search.do?page=1&number_per_page=1">首页</a>
					<a tabindex="0" class="previous paginate_button"
						id="dyntable_previous"
						href="${ctx }/employee/search.do?page=${page-1 }&number_per_page=1">上一页</a>
				</c:if>
				<c:if test="${page_number<6&&page_number>0}">
					<c:forEach var="i" begin="1" end="${page_number }">
						<c:if test="${page!=i }">
							<a tabindex="0" class="paginate_button"
								href="${ctx }/employee/search.do?page=${i }&number_per_page=1">${i }</a>
						</c:if>
						<c:if test="${page==i }">
							<a tabindex="0" class="paginate_active"
								href="${ctx }/employee/search.do?page=${i }&number_per_page=1">${i }</a>
						</c:if>
					</c:forEach>
				</c:if>
				<c:if test="${page_number>5}">
					<c:choose>
						<c:when test="${page<3 }">
							<c:forEach var="i" begin="1" end="5">
								<c:if test="${page!=i }">
									<a tabindex="0" class="paginate_button"
										href="${ctx }/employee/search.do?page=${i }&number_per_page=1">${i }</a>
								</c:if>
								<c:if test="${page==i }">
									<a tabindex="0" class="paginate_active"
										href="${ctx }/employee/search.do?page=${i }&number_per_page=1">${i }</a>
								</c:if>
							</c:forEach>
						</c:when>
						<c:when test="${page>page_number-3 }">
							<c:forEach var="i" begin="${page_number-4 }"
								end="${page_number }">
								<c:if test="${page!=i }">
									<a tabindex="0" class="paginate_button"
										href="${ctx }/employee/search.do?page=${i }&number_per_page=1">${i }</a>
								</c:if>
								<c:if test="${page==i }">
									<a tabindex="0" class="paginate_active"
										href="${ctx }/employee/search.do?page=${i }&number_per_page=1">${i }</a>
								</c:if>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<c:forEach var="i" begin="${page-2 }" end="${page+2 }">
								<c:if test="${page!=i }">
									<a tabindex="0" class="paginate_button"
										href="${ctx }/employee/search.do?page=${i }&number_per_page=1">${i }</a>
								</c:if>
								<c:if test="${page==i }">
									<a tabindex="0" class="paginate_active"
										href="${ctx }/employee/search.do?page=${i }&number_per_page=1">${i }</a>
								</c:if>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</c:if>
				<c:if test="${page<page_number }">
					<a tabindex="0" class="next paginate_button" id="dyntable_next"
						href="${ctx }/employee/search.do?page=${page+1 }&number_per_page=1">下一页</a>
					<a tabindex="0" class="last paginate_button" id="dyntable_last"
						href="${ctx }/employee/search.do?page=${page_number }&number_per_page=1">尾页</a>
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


<!-- 这里引入你需要的js文件 -->
<script type="text/javascript" src="${ctx }/js/custom.js"></script>
<link rel="stylesheet" href="../views/market/quoteConfirmList.css">
<link rel="stylesheet" href="${ctx}/css/fmc/table.css">
<script type="text/javascript" src="${ctx}/js/fmc/table.js"></script>
<%@include file="/common/footer.jsp"%>

