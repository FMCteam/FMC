<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<html>
<head>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ctx}/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
	$.ready(function() {
		window.scrollTo(${list[0].x-500}, 0);
	});
</script>
<style>
a:hover{
	cursor: pointer;
}
</style>
<title>流水状态</title>
</head>

<body>
	<img src="${ctx}/images/fmc.png"
		style="position: absolute; left: 0px; top: 0px;"
		onload="window.scrollTo(${list[0].x-500},0);">
	<c:forEach var="node" items="${list}">
		<div
			style="position: absolute; border: 2px solid red; left: ${node.x}px; top:${node.y}px; width: ${node.width-3}px; height: ${node.height-3}px; border-radius:13px 13px 15px 13px"></div>
	</c:forEach>
	<a
		style="position: fixed; right:50px;bottom:100px;padding:8px 20px;display: block;background-color:#0866c6;border-color:#0a6bce;color: white"
		onclick="history.back();">返回</a>
</body>
</html>