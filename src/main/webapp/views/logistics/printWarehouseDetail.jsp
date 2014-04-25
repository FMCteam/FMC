<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>打印</title>

<script type="text/javascript" src="${ctx}/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-barcode.min.js"></script>

<script type="text/javascript">
	$(function() {
		var pid = $("#pid").val().trim();
		$("#bar").barcode(pid, "code128");
	});
</script>
<style type="text/css">
body {
	text-align: center;
	padding-top: 10px;
}

table {
	width: 100%;
	*--
}

h2 {
	font-weight: bold;
	text-align: center;
}

td {
	padding: 10px;
	text-align: center;
}

#bar {
	display: inline-block;
}
</style>
</head>
<body>
	<input type="hidden" value="${orderInfo.pack.packageId}" id="pid" />
	<h2>智造链-装包单</h2>
	<table border="1">
		<tr>
			<td>订单号：</td>
			<td>${orderInfo.orderId}</td>
		</tr>
		<tr>
			<td>装包号：</td>
			<td>${orderInfo.pack.packageId}</td>
		</tr>
		<tr>
			<td>条形码：</td>
			<td><span id="bar"></span></td>
		</tr>
		<tr>
			<td>详情：</td>
			<td>
				<table>
					<tr>
						<th>颜色</th>
						<th>型号</th>
						<th>数量</th>
					</tr>
					<c:forEach var="detail" items="${orderInfo.packDetails}">
						<tr>
							<td>${detail.clothesStyleColor }</td>
							<td>${detail.clothesStyleName }</td>
							<td>${detail.clothesAmount }</td>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>