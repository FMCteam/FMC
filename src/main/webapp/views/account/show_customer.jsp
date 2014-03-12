
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
		
		<%@include file="/common/header.jsp" %>


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
                <th class="head1"></th>
            </tr>
            </thead>
            <tbody>
				<c:forEach var="customer" items="${customer_list }" >
                     <tr class="gradeA">
                     <td>${customer.customerName }</td>
					<td>${customer.customerPhone }</td>
					<td>${customer.companyAddress }</td>
					<td>${customer.companyName }</td>
					<td>${customer.companyPhone }</td>
					<td><a href="${ctx }/customer/modify.do?customer_id=${customer.customerId }" class="btn btn-info" title="修改"><i class="iconsweets-create iconsweets-white"></i></a>
						<a href="${ctx }/customer/delete.do?customer_id=${customer.customerId }" class="btn btn-danger" title="删除"><i class="iconsweets-trashcan iconsweets-white"></i></a></td>
                     </tr>
                </c:forEach>
            </tbody>
            </table>
			<div class="dataTables_paginate paging_full_numbers" id="dyntable_paginate" style="float:right">
                	<c:if test="${page==1 }">
	                	<a tabindex="0" class="first paginate_button paginate_button_disabled" id="dyntable_first">首页</a>
						<a tabindex="0" class="previous paginate_button paginate_button_disabled" id="dyntable_previous">上一页</a>
                	</c:if>
					<c:if test="${page>1 }">
	                	<a tabindex="0" class="first paginate_button" id="dyntable_first" href="${ctx }/customer/search.do?page=1&number_per_page=1">首页</a>
						<a tabindex="0" class="previous paginate_button" id="dyntable_previous" href="${ctx }/customer/search.do?page=${page-1 }&number_per_page=1">上一页</a>
                	</c:if>
					<c:if test="${page_number<6&&page_number>0}">
						<c:forEach var ="i" begin="1" end="${page_number }">
							<c:if test="${page!=i }"><a tabindex="0" class="paginate_button" href="${ctx }/customer/search.do?page=${i }&number_per_page=1">${i }</a></c:if>
							<c:if test="${page==i }"><a tabindex="0" class="paginate_active" href="${ctx }/customer/search.do?page=${i }&number_per_page=1">${i }</a></c:if>
						</c:forEach>
					</c:if>
					<c:if test="${page_number>5}">
						<c:choose>
							<c:when test="${page<3 }">
								<c:forEach var ="i" begin="1" end="5">
									<c:if test="${page!=i }"><a tabindex="0" class="paginate_button" href="${ctx }/customer/search.do?page=${i }&number_per_page=1">${i }</a></c:if>
									<c:if test="${page==i }"><a tabindex="0" class="paginate_active" href="${ctx }/customer/search.do?page=${i }&number_per_page=1">${i }</a></c:if>
								</c:forEach>
							</c:when>
							<c:when test="${page>page_number-3 }">
								<c:forEach var ="i" begin="${page_number-4 }" end="${page_number }">
									<c:if test="${page!=i }"><a tabindex="0" class="paginate_button" href="${ctx }/customer/search.do?page=${i }&number_per_page=1">${i }</a></c:if>
									<c:if test="${page==i }"><a tabindex="0" class="paginate_active" href="${ctx }/customer/search.do?page=${i }&number_per_page=1">${i }</a></c:if>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<c:forEach var ="i" begin="${page-2 }" end="${page+2 }">
									<c:if test="${page!=i }"><a tabindex="0" class="paginate_button" href="${ctx }/customer/search.do?page=${i }&number_per_page=1">${i }</a></c:if>
					 				<c:if test="${page==i }"><a tabindex="0" class="paginate_active" href="${ctx }/customer/search.do?page=${i }&number_per_page=1">${i }</a></c:if>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</c:if>
					<c:if test="${page<page_number }">
	                	<a tabindex="0" class="next paginate_button" id="dyntable_next" href="${ctx }/customer/search.do?page=${page+1 }&number_per_page=1">下一页</a>
						<a tabindex="0" class="last paginate_button" id="dyntable_last" href="${ctx }/customer/search.do?page=${page_number }&number_per_page=1">尾页</a>
                	</c:if>
					<c:if test="${page==page_number }">
	                	<a tabindex="0" class="next paginate_button paginate_button_disabled" id="dyntable_next">下一页</a>
						<a tabindex="0" class="last paginate_button paginate_button_disabled" id="dyntable_last">尾页</a>
                	</c:if>
				</div>

	</div><!--row-fluid-->
    </div><!--maincontentinner-->
</div><!--maincontent-->


<%@include file="/common/js_file.jsp" %>

        
        <!-- 这里引入你需要的js文件 -->
        <script type="text/javascript" src="${ctx }/js/custom.js"></script>
        
        
        <%@include file="/common/footer.jsp" %>
