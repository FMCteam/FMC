<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<script type="text/javascript" src="${ctx}/js/jquery-1.10.2.min.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>制造链</title>
<script type="text/javascript">
	function check() {

		if ($("#package_list").find("table[sc='unchecked']").length === 0) {
			return true;
		} else {
			alert("还未扫描确认所有包！");
			return false;
		}
	}
	$(function() {
		var $tw = $("#txtWare");
		var $ts = $("#txtShelf");
		var $tl = $("#txtLocation");
		function checkPackageId(pid) {
			var found = false;
			var pl = $("#package_list").find("table");
			for (var i = 0; i < pl.length; i++) {
				var $p = $(pl[i]), _pid = $p.attr("id").split("_")[1];
				if (_pid === pid) {
					found = true;
					$tw.val($p.find("tr:eq(1) td:eq(1)").text());
					$ts.val($p.find("tr:eq(2) td:eq(1)").text());
					$tl.val($p.find("tr:eq(3) td:eq(1)").text());
					$("#status_" + pid).css("color", "green").text("已确认");
					$p.attr("sc", "checked");
					break;
				}
			}
			if (!found) {
				alert("错误，扫描包号不属于当前订单！");
			}
		}
		$("#txtScan").keydown(function(e) {
			if (e.keyCode === 13) {
				checkPackageId($(this).val().trim());
				$(this).select();
			}
		}).focus();

	});
</script>
</head>
<body>
	<%@include file="/views/logistics/mobile/nav.jsp"%>
	<h3>发货</h3>
	<div>
		<h4>订单号：${orderInfo.order.orderId}</h4>
	</div>
	<div style="margin-bottom: 10px;">

		<table>
			<tr>
				<td>包号：</td>
				<td><input autocomplete="off" type="text" id="txtScan"
					name="packageId" /></td>
			</tr>
			<tr>
				<td>仓库：</td>
				<td><input autocomplete="off" disabled="true" type="text"
					id="txtWare" name="warehouseId" /></td>
			</tr>
			<tr>
				<td>货架：</td>
				<td><input autocomplete="off" disabled="true" type="text"
					id="txtShelf" name="shelfId" /></td>
			</tr>
			<tr>
				<td>位置：</td>
				<td><input autocomplete="off" disabled="true" type="text"
					id="txtLocation" name="location" /></td>
			</tr>
		</table>

	</div>

	<ul id="package_list">
		<c:forEach var="packageInfo" items="${orderInfo.packs}">
			<li>
				<table id="package_${packageInfo.packageId }" sc="unchecked">
					<tr>
						<td>包号：</td>
						<td>${packageInfo.packageId }</td>
					</tr>
					<tr>
						<td>仓库：</td>
						<td>${packageInfo.warehouseId }</td>
					</tr>
					<tr>
						<td>货架：</td>
						<td>${packageInfo.shelfId }</td>
					</tr>
					<tr>
						<td>位置：</td>
						<td>${packageInfo.location }</td>
					</tr>
					<tr>
						<td>状态：</td>
						<td><span id="status_${packageInfo.packageId }"
							style="color: red;">未确认</span></td>
					</tr>
				</table>
			</li>
		</c:forEach>
	</ul>
	<div>
		<form action="${ctx}/logistics/mobile/sendClothesSubmit.do"
			method="post" onsubmit="return check();">
			<input type="hidden" value="${orderInfo.order.orderId }"
				name="orderId" /> <input type="submit" value="完成扫描" name="submit" />
		</form>
	</div>
</body>

<script type="text/javascript" src="${ctx}/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="${ctx}/css/bootstrap.min.css">
<link href="${ctx}/css/bootstrap-responsive.min.css" rel="stylesheet">
</html>