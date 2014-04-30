<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<table class="table table-striped table-bordered table-hover detail">
	<tr>
		<td>箱号</td>
		<td>仓库</td>
		<td>货架</td>
		<td>位置</td>
		<td>颜色</td>
		<td>大小</td>
		<td>件数</td>
	</tr>
	<c:forEach var="pack" items="${orderInfo.packs}" varStatus="status">
		<tr>
			<td rowspan="${fn:length(orderInfo.packDetails[status.index])}">${pack.packageId}</td>
			<td rowspan="${fn:length(orderInfo.packDetails[status.index])}">${pack.warehouseId}</td>
			<td rowspan="${fn:length(orderInfo.packDetails[status.index])}">${pack.shelfId}</td>
			<td rowspan="${fn:length(orderInfo.packDetails[status.index])}">${pack.location}</td>
			<td>${orderInfo.packDetails[status.index][0].clothesStyleColor}</td>
			<td>${orderInfo.packDetails[status.index][0].clothesStyleName}</td>
			<td>${orderInfo.packDetails[status.index][0].clothesAmount}</td>
		</tr>
		<c:forEach var="detail" begin="1"
			items="${orderInfo.packDetails[status.index]}">
			<tr>
				<td>${detail.clothesStyleColor}</td>
				<td>${detail.clothesStyleName}</td>
				<td>${detail.clothesAmount}</td>
			</tr>
		</c:forEach>
	</c:forEach>
</table>