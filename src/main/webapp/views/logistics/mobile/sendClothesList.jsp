<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>制造链</title>
</head>
<body>
	<%@include file="/views/logistics/mobile/nav.jsp"%>
		<h3>智造链-发货列表</h3>
	<ul>
		<c:forEach var="orderInfo" items="${orderList}">
			<li><a
				href="${ctx}/logistics/mobile/sendClothesDetail.do?orderId=${orderInfo.order.orderId}">${orderInfo.order.orderId}</a>
			</li>
		</c:forEach>
	</ul>
</body>
<script type="text/javascript" src="${ctx}/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${ctx}/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="${ctx}/css/bootstrap.min.css">
<link href="${ctx}/css/bootstrap-responsive.min.css" rel="stylesheet">
</html>