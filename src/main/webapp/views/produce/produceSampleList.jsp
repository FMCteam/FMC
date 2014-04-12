
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>


<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
			<!--  如果是其它页面，这里是填充具体的内容。 -->
			<section class="list">
            <table class="list">
                <caption>
					<span class="text-vertical">生产样衣列表:<span class="number">${fn:length(list)}</span>件任务
					</span><input type="text" class="search-query float-right"
						placeholder="输入检索条件">
				</caption>
					<thead>
                        <tr>
                            <th>订单号</th>
                            <th>业务员</th>
                            <th>客户姓名</th>
                            <th>客户公司</th>
                            <th>款式</th>
                            <th>件数</th>
                            <th>交货时间</th>
                            <th>操作</th>
                        </tr>
                    </thead>
	                    <c:forEach var="orderModel" items="${list}" >
	                        <tr class="gradeA">
	                            <td>${orderModel.order.orderId }</td>
								<td>${orderModel.order.employeeId }</td>
								<td>${orderModel.order.customerName }</td>
								<td>${orderModel.order.customerCompany }</td>
								<td>${orderModel.order.styleName }</td>
								<td>${orderModel.order.askAmount }</td>
								<td>${fn:substring(orderModel.order.askDeliverDate,0,10) }</td>
								<td><a href="${ctx }/produce/produceSampleDetail.do?orderId=${orderModel.order.orderId}&task_id=${orderModel.task.id}">详情
									</a></td>								
	                        </tr>
                        </c:forEach>
                </table>
                </section>

		</div>
		<!--row-fluid-->
	</div>
	<!--maincontentinner-->
</div>
<!--maincontent-->


<%@include file="/common/js_file.jsp"%>
<!-- 这里引入你需要的js文件 -->
<script type="text/javascript" src="${ctx }/js/custom.js"></script>
<link rel="stylesheet" href="${ctx}/css/fmc/table.css">
<script type="text/javascript" src="${ctx}/js/fmc/table.js"></script>
<link rel="stylesheet" href="../views/market/quoteConfirmList.css">
<%@include file="/common/footer.jsp"%>
