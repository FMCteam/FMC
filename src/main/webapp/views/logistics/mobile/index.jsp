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
	<a style="margin-bottom:20px;height:35px;margin-top: 50px;"
		class="span12 btn btn-primary"
		href="${ctx}/logistics/mobile/warehouseList.do">入库登记</a>
	<a style="margin-bottom:20px;height:35px;"
		class="span12 btn btn-primary"
		href="${ctx}/logistics/mobile/sendClothesList.do">发货扫描</a>
</body>
<script type="text/javascript" src="${ctx}/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${ctx}/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="${ctx}/css/bootstrap.min.css">
<link href="${ctx}/css/bootstrap-responsive.min.css" rel="stylesheet">
</html>