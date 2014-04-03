
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
		
		<%@include file="/common/header.jsp" %>

        
        <div class="maincontent">
            <div class="maincontentinner">
                <div class="row-fluid" style="min-height:300px;">
                
                    <!--  如果是其它页面，这里是填充具体的内容。 -->
                    <h4 class="widgettitle">入库订单详情</h4>
                <table id="dyntable" class="table table-bordered responsive">
                
                  	<tr>
								<td rowspan="6" }>生产情况</td>
								<td >生产编号</td>
								<td >订单要求</td>
								<td >实际生产</td>
								<td >自检合格</td>
								<td >颜色</td>
								<td >款式</td>
								
							</tr>
							<tr>
								<td class="innertable"><table
										class="span12 table fabric_table">
										 <c:forEach var="product" items="${product_list}" >
										<tr class="addrow">
											<td><input name="product_id" value=${product.id }></td>
											<td><input name="ask_amount" value=${product.askAmount }></td>
											<td><input name="produce_amount" value=${product.produceAmount }></td>
											
											<td><input name="qualified_amount" value=${product.qualifiedAmount }></td>
											<td><input name="color" value=${product.color }></td>
											<td><input name="style" value=${product.style }></td>
										</tr>
										</c:forEach>
									</table></td>
							</tr>
	                   	<tr>
								
								<td colspan="1">包编号</td>
								
								<td colspan="1">衣服件数</td>
								<td colspan="1">衣服样式</td>
								<td colspan="1">衣服颜色</td>
								<td colspan="1">操作</td>
								
							</tr>
							
							<tr>
								<td colspan="6" class="innertable"><table
										class="span12 table fabric_table">
										 <c:forEach var="packages" items="${package_detail_list}" >
										   <c:forEach var="package2" items="${packages}" >
										<tr class="addrow">
										
											<td><input name="package_id" value=${package2.packageId }></td>
												<td><input name="cloths_amount" value=${package2.clothesAmount }></td>
												<td><input name="clothes_style_name" value=${package2.clothesStyleName }></td>
													<td><input name="clothes_style_color" value=${package2.clothesStyleColor }></td>
											<td>操作<td>
										</tr>
										</c:forEach>
										</c:forEach>
									</table></td>
							</tr>
							 
                 
                </table>
                <div class="dataTables_paginate paging_full_numbers" id="dyntable_paginate" style="float:right">
                	<c:if test="${page==1 }">
	                	<a tabindex="0" class="first paginate_button paginate_button_disabled" id="dyntable_first">首页</a>
						<a tabindex="0" class="previous paginate_button paginate_button_disabled" id="dyntable_previous">上一页</a>
                	</c:if>
					<c:if test="${page>1 }">
	                	<a tabindex="0" class="first paginate_button" id="dyntable_first" href="${ctx }/employee/search.do?page=1&number_per_page=1">首页</a>
						<a tabindex="0" class="previous paginate_button" id="dyntable_previous" href="${ctx }/employee/search.do?page=${page-1 }&number_per_page=1">上一页</a>
                	</c:if>
					<c:if test="${page_number<6&&page_number>0}">
						<c:forEach var ="i" begin="1" end="${page_number }">
							<c:if test="${page!=i }"><a tabindex="0" class="paginate_button" href="${ctx }/employee/search.do?page=${i }&number_per_page=1">${i }</a></c:if>
							<c:if test="${page==i }"><a tabindex="0" class="paginate_active" href="${ctx }/employee/search.do?page=${i }&number_per_page=1">${i }</a></c:if>
						</c:forEach>
					</c:if>
					<c:if test="${page_number>5}">
						<c:choose>
							<c:when test="${page<3 }">
								<c:forEach var ="i" begin="1" end="5">
									<c:if test="${page!=i }"><a tabindex="0" class="paginate_button" href="${ctx }/employee/search.do?page=${i }&number_per_page=1">${i }</a></c:if>
									<c:if test="${page==i }"><a tabindex="0" class="paginate_active" href="${ctx }/employee/search.do?page=${i }&number_per_page=1">${i }</a></c:if>
								</c:forEach>
							</c:when>
							<c:when test="${page>page_number-3 }">
								<c:forEach var ="i" begin="${page_number-4 }" end="${page_number }">
									<c:if test="${page!=i }"><a tabindex="0" class="paginate_button" href="${ctx }/employee/search.do?page=${i }&number_per_page=1">${i }</a></c:if>
									<c:if test="${page==i }"><a tabindex="0" class="paginate_active" href="${ctx }/employee/search.do?page=${i }&number_per_page=1">${i }</a></c:if>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<c:forEach var ="i" begin="${page-2 }" end="${page+2 }">
									<c:if test="${page!=i }"><a tabindex="0" class="paginate_button" href="${ctx }/employee/search.do?page=${i }&number_per_page=1">${i }</a></c:if>
					 				<c:if test="${page==i }"><a tabindex="0" class="paginate_active" href="${ctx }/employee/search.do?page=${i }&number_per_page=1">${i }</a></c:if>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</c:if>
					<c:if test="${page<page_number }">
	                	<a tabindex="0" class="next paginate_button" id="dyntable_next" href="${ctx }/employee/search.do?page=${page+1 }&number_per_page=1">下一页</a>
						<a tabindex="0" class="last paginate_button" id="dyntable_last" href="${ctx }/employee/search.do?page=${page_number }&number_per_page=1">尾页</a>
                	</c:if>
					<c:if test="${page==page_number }">
	                	<a tabindex="0" class="next paginate_button paginate_button_disabled" id="dyntable_next">下一页</a>
						<a tabindex="0" class="last paginate_button paginate_button_disabled" id="dyntable_last">尾页</a>
                	</c:if>
				</div>
				
				
                      </div><!--row-fluid-->
                
                <div class="footer">
                    <div class="footer-left">
                        <span>&copy; 2014. 江苏南通智造链有限公司.</span>
                    </div>
                  
                </div><!--footer-->
                
            </div><!--maincontentinner-->
        </div><!--maincontent-->
        
        <%@include file="/common/js_file.jsp" %>

        
        <!-- 这里引入你需要的js文件 -->
        <script type="text/javascript" src="${ctx }/js/custom.js"></script>
        
        
        <%@include file="/common/footer.jsp" %>
    
