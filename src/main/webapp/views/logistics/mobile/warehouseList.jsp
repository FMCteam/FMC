<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<script type="text/javascript" src="${ctx}/js/jquery-1.10.2.min.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>制造链</title>
<script type="text/javascript">
$(function() {
	$("table.list tbody tr").click(function(){
		window.location.href=$(this).find("a").attr("href"); 
	});
});
</script>
</head>
<body>
	<%@include file="/views/logistics/mobile/nav.jsp"%>
	<section class="list">
		<table class="list">
			<caption style="padding:8px 20px;height:20px">入库列表</caption>
			<thead>
				<tr>
					<th>单号</th>
					<th>客户</th>
					<th>公司</th>
				</tr>
			</thead>
			<c:forEach var="orderInfo" items="${orderList}">
				<tr>
					<td><a
						href="${ctx}/logistics/mobile/warehouseDetail.do?orderId=${orderInfo.order.orderId}">${orderInfo.orderId }</a>
					</td>
					<td>${orderInfo.order.customerName}</td>
					<td>${orderInfo.order.customerCompany}</td>
				</tr>
			</c:forEach>
		</table>
	</section>
</body>
<button class="btn btn-primary" onclick="window.location.href='${ctx}/default.do';">返回到首页</button>
<script type="text/javascript" src="${ctx}/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="${ctx}/css/bootstrap.min.css">
<link rel="stylesheet" href="${ctx}/css/fmc/table.css">
<link href="${ctx}/css/bootstrap-responsive.min.css" rel="stylesheet">
</html>