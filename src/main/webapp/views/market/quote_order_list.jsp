<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%@include file="/common/header.jsp"%>


<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">

			<!--  如果是其它页面，这里是填充具体的内容。 -->
			<h4 class="widgettitle">市场专员合并报价</h4>
			<table id="dyntable" class="table table-bordered responsive">
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
						<!--  
						<th class="head1">设计费用</th>
						<th class="head0">裁剪费用</th>
						<th class="head1">管理费用</th>
						<th class="head0">swingCost</th>
						<th class="head1">ironingCost</th>
						<th class="head0">nailCost</th>
						<th class="head1">packageCost</th>
						<th class="head0">otherCost</th>
						<th class="head1">profitPerPiece</th>
						-->
						<th class="head0">innerPrice</th>
						<th class="head0">outerPrice</th>
						<th class="head0">操作</th>
						
					</tr>
				</thead>
				<tbody>
				
					<c:forEach var="quoteModel" items="${quote_list}">
						<form action="${ctx}/market/computerOrderSum.do" method="post">
						
						<tr class="gradeA">
							<td><input name="order_id" value=${quoteModel.quote.orderId } readonly ></td>
							<!-- 
							<td><input name="design_cost">${quoteModel.quote.designCost }></td>
							<td><input name="cut_cost">${quoteModel.quote.cutCost}</td>
							<td><input name="manage_cost">${quoteModel.quote.manageCost }</td>
							<td><input name="swing_cost">${quoteModel.quote.swingCost}</td>
							<td><input name="ironing_cost">${quoteModel.quote.ironingCost}</td>
							<td><input name="nail_cost">${quoteModel.quote.nailCost }</td>
							<td><input name="package_cost">${quoteModel.quote.packageCost }</td>
							<td><input name="other_cost">${quoteModel.quote.otherCost}</td>
							<td><input name="profit_per_piece">${quoteModel.quote.profitPerPiece}</td>
							 -->
							<td><input name="inner_price" value=${quoteModel.quote.innerPrice }></td>
							<td><input name="outer_price" value=${quoteModel.quote.outerPrice }></td>
							<td style="display:none">
							<input name="taskId" value=${quoteModel.taskId }>
							</td>
							<td style="display:none">
							<input name="processId" value=${quoteModel.processInstanceId }>
							</td>
						<td> <p class="stdformbutton">
                        <button class="btn btn-primary">更新</button>
                         </p></td>
						</tr>
					
						</form>
					</c:forEach>
				</tbody>
			</table>
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


<%@include file="/common/footer.jsp"%>

