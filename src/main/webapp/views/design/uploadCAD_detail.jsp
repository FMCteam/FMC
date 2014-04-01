
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
		
		<%@include file="/common/header.jsp" %>

        
        <div class="maincontent">
            <div class="maincontentinner">
                <div class="row-fluid" style="min-height:300px;">
                
                <form action="${ctx }/design/doUploadCAD.do" method="post" enctype="multipart/form-data">  
  
  <table class="table table-striped table-bordered table-hover">
							<tr>
								<td rowspan="2">询单信息</td>
								<td>询单编号</td>
								<td>接单业务员</td>
								<td>客户名字</td>
								<td>报价日期</td>
								
							</tr>
							<tr>
								<td>${orderModel.order.orderId }</td>
								<td>${orderModel.order.employeeId }</td>
								<td>${orderModel.order.customerName }</td>
								<td>${orderModel.order.orderTime}</td>
								
							</tr>
							
							
							<tr>
                        <td>设计费用</td>
                     <td>
                      <input name="CADFile" id="CADFile" type="file" />  
                      <input type="hidden" name="orderId" value="${orderModel.order.orderId }" />
	                  <input type="hidden" name="taskId" value="${orderModel.taskId }" />
					<input type="hidden" name="pinId" value="${orderModel.processInstanceId }" />
                    </tr>
							
							  <tr>

                        <td colspan="3"><input type="submit" style="float: right;"/></td>
                    </tr>

							
							
							
						</table>
  
  
  
  
  
  
               
               </form>  
				
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
    
