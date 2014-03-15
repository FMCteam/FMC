<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
		
		<%@include file="/common/header.jsp" %>


<div class="maincontent">
    <div class="maincontentinner">
        <div class="row-fluid" style="min-height:300px;">
            <!--  如果是其它页面，这里是填充具体的内容。 -->
            <h4 class="widgettitle">修改订单</h4>
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
                <th class="head0">订单编号</th>
                <th class="head1">客户编号</th>
                <th class="head0">客户姓名</th>
                <th class="head1">下单时间</th>
                <th class="head0">操作</th>
                <th class="head1"></th>
            </tr>
            </thead>
            <tbody>
				<c:forEach var="order" items="${order_list }" >
                     <tr class="gradeA">
                     <td>${order.getOrder().getOrderId()}</td>
					<td>${order.getOrder().getCustomerId() }</td>
					<td>${order.getOrder().getCustomerName() }</td>
					<td>${order.getOrder().getOrderTime()}</td>
						<td><form action="${ctx }/market/modify.do" method="post" >
									<input type="hidden" name="order_id" value="${order.getOrder().getOrderId() }" />
										<input type="hidden" name="task_id" value="${order.getTaskId()}" />
										<input type="hidden" name="process_id" value="${order.getProcessInstanceId() }" />
                                     <input type="hidden" name="modify" value="1"/>
										<button class="btn btn-primary btn-rounded"><i class="icon-white"></i> 修改</button></form>

						
						<form action="${ctx }/market/modify.do" method="post" >
									<input type="hidden" name="order_id" value="${order.getOrder().getOrderId() }" />
										<input type="hidden" name="task_id" value="${order.getTaskId()}" />
										<input type="hidden" name="process_id" value="${order.getProcessInstanceId() }" />
                                     <intput type="hidden" name="modify" value="0"/>
										<button class="btn btn-primary btn-rounded"><i class="icon-white"></i>不通过</button></form></td>
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
	                	<a tabindex="0" class="first paginate_button" id="dyntable_first" href="${ctx }/market/modifyList.do?page=1&number_per_page=10">首页</a>
						<a tabindex="0" class="previous paginate_button" id="dyntable_previous" href="${ctx }/market/modifyList.do?page=${page-1 }&number_per_page=10">上一页</a>
                	</c:if>
					<c:if test="${page_number<6&&page_number>0}">
						<c:forEach var ="i" begin="1" end="${page_number }">
							<c:if test="${page!=i }"><a tabindex="0" class="paginate_button" href="${ctx }/market/modifyList.do?page=${i }&number_per_page=10">${i }</a></c:if>
							<c:if test="${page==i }"><a tabindex="0" class="paginate_active" href="${ctx }/market/modifyList.do?page=${i }&number_per_page=10">${i }</a></c:if>
						</c:forEach>
					</c:if>
					<c:if test="${page_number>5}">
						<c:choose>
							<c:when test="${page<3 }">
								<c:forEach var ="i" begin="1" end="5">
									<c:if test="${page!=i }"><a tabindex="0" class="paginate_button" href="${ctx }/market/modifyList.do?page=${i }&number_per_page=10">${i }</a></c:if>
									<c:if test="${page==i }"><a tabindex="0" class="paginate_active" href="${ctx }/market/modifyList.do?page=${i }&number_per_page=10">${i }</a></c:if>
								</c:forEach>
							</c:when>
							<c:when test="${page>page_number-3 }">
								<c:forEach var ="i" begin="${page_number-4 }" end="${page_number }">
									<c:if test="${page!=i }"><a tabindex="0" class="paginate_button" href="${ctx }/market/modifyList.do?page=${i }&number_per_page=10">${i }</a></c:if>
									<c:if test="${page==i }"><a tabindex="0" class="paginate_active" href="${ctx }/market/modifyList.do?page=${i }&number_per_page=10">${i }</a></c:if>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<c:forEach var ="i" begin="${page-2 }" end="${page+2 }">
									<c:if test="${page!=i }"><a tabindex="0" class="paginate_button" href="${ctx }/market/modifyList.do?page=${i }&number_per_page=10">${i }</a></c:if>
					 				<c:if test="${page==i }"><a tabindex="0" class="paginate_active" href="${ctx }/market/modifyList.do?page=${i }&number_per_page=10">${i }</a></c:if>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</c:if>
					<c:if test="${page<page_number }">
	                	<a tabindex="0" class="next paginate_button" id="dyntable_next" href="${ctx }/market/modifyList.do?page=${page+1 }&number_per_page=10">下一页</a>
						<a tabindex="0" class="last paginate_button" id="dyntable_last" href="${ctx }/market/modifyList.do?page=${page_number }&number_per_page=10">尾页</a>
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
