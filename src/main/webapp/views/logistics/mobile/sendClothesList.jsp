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
			<caption style="padding:8px 20px;height:20px">发货列表</caption>
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
						href="${ctx}/logistics/mobile/sendClothesDetail.do?orderId=${orderInfo.order.orderId}">${orderInfo.order.orderId}</a>
					</td>
					<td>${orderInfo.order.customerName}</td>
					<td>${orderInfo.order.customerCompany}</td>
				</tr>
			</c:forEach>
		</table>
	</section>
<button class="btn btn-primary" onclick="window.location.href='http://localhost:8080/fmc/default.do';">返回到首页</button>
</body>

<script type="text/javascript" src="${ctx}/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="${ctx}/css/bootstrap.min.css">
<link rel="stylesheet" href="${ctx}/css/fmc/table.css">
<link href="${ctx}/css/bootstrap-responsive.min.css" rel="stylesheet">
</html>