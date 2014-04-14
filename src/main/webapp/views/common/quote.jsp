<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<table class="table table-striped table-bordered detail">
	<tr>
		<td rowspan="${fn:length(orderInfo.fabrics)+1}">面料报价</td>
		<td>面料名</td>
		<td>单件米耗</td>
		<td>价格</td>
		<td>单件成本</td>
	</tr>

	<c:forEach var="fabric" items="${orderInfo.fabrics}">
		<tr>
			<td>${fabric.fabricName }</td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
	</c:forEach>
	<tr>
		<td rowspan="${fn:length(orderInfo.accessorys)+1}">辅料报价</td>
		<td>辅料名</td>
		<td>单件耗数</td>
		<td>价格</td>
		<td>单件成本</td>
	</tr>
	<c:forEach var="accessory" items="${orderInfo.accessorys}">
		<tr>
			<td>${accessory.accessoryName }</td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
	</c:forEach>
	<tr>
		<td>面辅总计</td>
		<td>面料总计</td>
		<td></td>
		<td>辅料总计</td>
		<td></td>
	</tr>
	<tr>
		<td rowspan="4">其他成本</td>
		<td>裁剪费用</td>
		<td>管理费用</td>
		<td>缝制费用</td>
		<td>整烫费用</td>
	</tr>

	<tr>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
	</tr>
	<tr>
		<td>锁订费用</td>
		<td>包装费用</td>
		<td>其他费用</td>
		<td>设计费用</td>
	</tr>
	<tr>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
	</tr>
	<tr>
		<td rowspan="2">费用核算</td>
		<td>成本总计</td>
		<td>单件利润</td>
		<td>生产报价</td>
		<td>客户报价</td>
	</tr>
	<tr>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
	</tr>

</table>