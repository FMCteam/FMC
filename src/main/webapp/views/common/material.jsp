<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<table class="table table-striped table-bordered table-hover detail">
	<tr>
		<td class="span2 title" rowspan="${fn:length(orderInfo.fabrics)+1}">面料</td>
		<td class="span5 title" colspan="4">面料名称</td>
		<td class="span5 title" colspan="4">面料克重</td>
	</tr>
	<c:forEach var="fabric" items="${orderInfo.fabrics}">
		<tr>
			<td colspan="4">${fabric.fabricName}</td>
			<td colspan="4">${fabric.fabricAmount}</td>
		</tr>
	</c:forEach>
	<tr>
		<td class="title" rowspan="${fn:length(orderInfo.accessorys)+1}">辅料</td>
		<td class="title" colspan="4">辅料名称</td>
		<td class="title" colspan="4">辅料要求</td>
	</tr>
	<c:forEach var="accessory" items="${orderInfo.accessorys}">
		<tr>
			<td colspan="4">${accessory.accessoryName}</td>
			<td colspan="4">${accessory.accessoryQuery}</td>
		</tr>
	</c:forEach>
</table>