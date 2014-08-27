<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>样衣单打印</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<script type="text/javascript" src="${ctx}/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.elastic.source.js"
	charset="utf-8"></script>
<script type="text/javascript">
	$('textarea').elastic();
</script>
<link rel="stylesheet" href="${ctx}/css/bootstrap.min.css">
<style type="text/css">
body {
	text-align: center;
	padding-left: 50px;
	padding-right: 50px;
	margin-top: 2px;
}

* {
	
}

h3.title {
	font-weight: bold;
	text-align: center;
	margin: 0px 0px 2px 0px;
}

td {
	padding: 2px;
	text-align: left;
	border: 1px solid black;
}

textarea.input {
	border: 1px solid black;
	padding: 4px;
	width: 100%;
}

table.table td.center {
	text-align: center;
	vertical-align: middle;
}

table.table.table-bordered{
	margin: 13px 0px 10px 0px;
	border: 1px solid black;
}
</style>
</head>

<body>
	<div style="position: relative;">
		<span style="position:absolute;bottom: 5px;right: 0px;">订单编号:${orderInfo.orderId}
		</span>
		<h3 class="title">智造链样衣单</h3>
	</div>
	<table class="table table-bordered">
		<tr>
			<td class="span3">下单业务员:${orderInfo.employee.employeeName}</td>
			<td class="span3">订单来源：${orderInfo.order.orderSource }</td>
			<td class="span3">客户：${orderInfo.order.customerName}-${orderInfo.order.customerCompany}</td>
		</tr>
		<tr>
			<td>款号：</td>
			<td>采购单号：</td>
			<td>下单日期：${fn:substring(orderInfo.order.orderTime,0,10)}</td>
		</tr>
	</table>
	<p style="font-size: 20px;text-align: left">${orderInfo.order.styleName}</p>
	<table class="table table-bordered">
		<tr>
			<td
				style="width:40%;height:170px;text-align: center;vertical-align:middle;">贴样布处</td>
			<td rowspan="2"><img style="height:200px;"
				src="${orderInfo.order.sampleClothesPicture}">
		</tr>
		<tr>
			<td style="height:30px;vertical-align:middle;">面料成分： <c:forEach
					var="fabric" items="${orderInfo.fabrics}">
				${fabric.fabricName}&nbsp
			</c:forEach></td>
		</tr>
	</table>
	<table class="table table-bordered">
		<tr>
			<td class="center span3">样衣下单数</td>
			<td class="center span2">XS</td>
			<td class="center span2">S</td>
			<td class="center span2">M</td>
			<td class="center span2">L</td>
			<td class="center span2">XL</td>
			<td class="center span2">XXL</td>
			<td class="center span2"></td>
		</tr>
		<c:forEach var="sample" items="${orderInfo.sample}">
			<tr>
				<td class="center">${sample.color}</td>
				<td class="center">${sample.xs}</td>
				<td class="center">${sample.s}</td>
				<td class="center">${sample.m}</td>
				<td class="center">${sample.l}</td>
				<td class="center">${sample.xl}</td>
				<td class="center">${sample.xxl}</td>
				<td></td>
			</tr>
		</c:forEach>

	</table>
	<table class="table table-bordered">
		<tr>
			<td rowspan="3" class="center span3" style="height: 150px;">样衣面辅料信息</td>
			<td rowspan="3" class="center span2">面料用量</td>
			<td rowspan="3" class="center span3"></td>
			<td rowspan="3" class="center span2">辅料用量</td>
			<td rowspan="3" class="center span3"></td>
		</tr>
	</table>
	<table class="table table-bordered">
		<tr>
			<td rowspan="3" class="center span2">样衣加工信息</td>
			<td class="center span2">制版人姓名</td>
			<td class="center span2"></td>
		</tr>
		<tr >
			<td class="center span2">样衣完成日期</td>
			<td class="center span2"></td>
		</tr>
		
	</table>
	<p id="info">
		<textarea class="input" rows="8">备注:</textarea>
	</p>

</body>
</html>
