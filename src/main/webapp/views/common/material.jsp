<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<table class="table table-striped table-bordered table-hover">
	<tr>
		<td rowspan="${fn:length(orderInfo.fabrics)+1}">面料</td>
		<td colspan="3">面料名称</td>
		<td colspan="3">面料克重</td>
	</tr>
	<c:forEach var="fabric" items="${orderInfo.fabrics}">
		<tr>
			<td colspan="3">${fabric.fabricName}</td>
			<td colspan="3">${fabric.fabricAmount}</td>
		</tr>
	</c:forEach>
	<tr>
		<td rowspan="${fn:length(orderInfo.accessorys)+1}">辅料</td>
		<td colspan="3">辅料名称</td>
		<td colspan="3">辅料要求</td>
	</tr>
	<c:forEach var="accessory" items="${orderInfo.accessorys}">
		<tr>
			<td colspan="3">${accessory.accessoryName}</td>
			<td colspan="3">${accessory.accessoryQuery}</td>
		</tr>
	</c:forEach>
</table>