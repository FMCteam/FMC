<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>物流</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<script type="text/javascript" src="${ctx }/js/jquery-1.9.1.min.js"></script>
<style type="text/css">
	* {
		padding: 0;
		margin: 0;
	}
	ul li {
		list-style: none;
	}
	li {
		margin-bottom: 15px;
	}
</style>
</head>
<body>
<h3>智造链-入库列表</h3>
<ul>
<c:forEach var="order" items="${orderList}">
<li>
<a href="${ctx }/logistics/updateStore.do?orderId=${orderList.orderId}">${orderList.orderId }</a>
</li>
</c:forEach>
</ul>
</body>
</html>