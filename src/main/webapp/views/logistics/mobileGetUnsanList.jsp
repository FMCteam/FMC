<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<script type="text/javascript" src="${ctx }/js/jquery-1.9.1.min.js"></script>
<title>登录</title>
</head>
<body>
<h3>智造链-待验货列表</h3>
 
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
                            <th class="head1">业务员</th>
                            <th class="head0">客户姓名</th>
                            <th class="head1">客户公司</th>
                            <th class="head0">款式</th>
                            <th class="head1">件数</th>
                            <th class="head0">交货时间</th>
                            <th class="head1"></th>
                        </tr>
                    </thead>
                    <tbody>
	                    <c:forEach var="orderModel" items="${list}" >
	                        <tr class="gradeA">
	                            <td>${orderModel.order.orderId }</td>
								<td>${orderModel.order.employeeId }</td>
								<td>${orderModel.order.customerName }</td>
								<td>${orderModel.order.customerCompany }</td>
								<td>${orderModel.order.styleName }</td>
								<td>${orderModel.order.askAmount }</td>
								<td>${fn:substring(orderModel.order.askDeliverDate,0,10) }</td>
								<td><form action="${ctx }/logistics/checkSendClothes.do" method="get" >
									<input type="hidden" name="id" value="${orderModel.order.orderId }" />
									<%-- 	<input type="hidden" name="task_id" value="${orderModel.taskId }" />
										<input type="hidden" name="process_id" value="${orderModel.processInstanceId }" /> --%>
										<input type="submit" value="验货" /></form>
								</td>
	                        </tr>
                        </c:forEach>
                    </tbody>
                </table>
</body>
</html>

                
               