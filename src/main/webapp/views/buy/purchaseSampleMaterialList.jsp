
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
		
		<%@include file="/common/header.jsp" %>

        
        <div class="maincontent">
            <div class="maincontentinner">
                <div class="row-fluid" style="min-height:300px;">
                
                    <!--  如果是其它页面，这里是填充具体的内容。 -->
                     <section class="list">
                <table class="list">
                    
                  <caption><span class="text-vertical">待样衣采购确认:${fn:length(list)}件任务</span>
                <input type="text" class="search-query float-right" placeholder="输入检索条件"></caption>
				<thead>
                        <tr>
                            <th >订单号</th>
                            <th >业务员</th>
                            <th >客户姓名</th>
                            <th >客户公司</th>
                            <th >款式</th>
                            <th >件数</th>
                            <th >交货时间</th>
                            <th ></th>
                        </tr>
                    </thead>
                    
	                    <c:forEach var="orderModel" items="${list}" >
	                        <tr >
	                            <td>${orderModel.order.orderId }</td>
								<td>${orderModel.order.employeeId }</td>
								<td>${orderModel.order.customerName }</td>
								<td>${orderModel.order.customerCompany }</td>
								<td>${orderModel.order.styleName }</td>
								<td>${orderModel.order.askAmount }</td>
								<td>${fn:substring(orderModel.order.askDeliverDate,0,10) }</td>
									
										
									
								<td><a
							href="${ctx}/buy/purchaseSampleMaterialDetail.do?orderId=${orderModel.order.orderId}">详情
						</a> 
						</td>
	                        </tr>
                        </c:forEach>
                   
                </table>
             </section>
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
        
            <link rel="stylesheet" href="${ctx}/css/fmc/table.css">
        <script type="text/javascript" src="${ctx}/js/fmc/table.js"></script>
        <%@include file="/common/footer.jsp" %>
    
