<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>物流</title>

<script type="text/javascript" src="${ctx }/js/jquery-1.9.1.min.js"></script>
<style type="text/css">
	a {
		display: block;
		margin-bottom: 20px;
	}
</style>
</head>
<body>
<h3>智造链-物流操作</h3>
<a href="${ctx }/logistics/mobileStore.do">入库登记</a>
<a href="${ctx }/logistics/mobileScan.do">发货扫描</a>
</body>
</html>