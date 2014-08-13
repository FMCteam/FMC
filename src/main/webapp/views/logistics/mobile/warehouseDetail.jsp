<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<script type="text/javascript" src="${ctx}/js/jquery-1.10.2.min.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>制造链</title>
<script type="text/javascript">
	$(function() {
		var $tw = $("#txtWare");
		var $ts = $("#txtShelf");
		var $tl = $("#txtLocation");

		function checkPackageId(pid) {
			var found = false;
			var table_rows = $("table#pack tbody tr td:first-child");
			for (var i = 0; i < table_rows.length; i++) {
				var _pid = $(table_rows[i]).text();
				//alert(_pid);
				if (_pid == pid) {
					found = true;
					$tw.val($("table#pack tbody tr:eq(" + i + ") td:eq(1)")
							.text());
					$ts.val($("table#pack tbody tr:eq(" + i + ") td:eq(2)")
							.text());
					$tl.val($("table#pack tbody tr:eq(" + i + ") td:eq(3)")
							.text());
					break;
				}
			}
			if (found) {
				$tw.attr("disabled", false);
				$ts.attr("disabled", false);
				$tl.attr("disabled", false);
			} else {
				alert("错误，扫描包号不属于当前订单！");
			}
		}
		$("#txtScan").keydown(function(e) {
			if (e.keyCode === 13) {
				checkPackageId($(this).val().trim());
			}
		}).focus();
		$("#submit_btn").click(function() {
			$("#updateForm").submit();
		});
	});
	function check() {
		if ($("table#pack td:empty").length === 0) {
			return true;
		} else {
			alert("还未扫描确认所有包！");
			return false;
		}
	}
</script>
</head>
<body>
	<%@include file="/views/logistics/mobile/nav.jsp"%>
	<h4>入库-订单号:${orderInfo.orderId}</h4>
	<div>
		<form method="post" id="updateForm"
			action="${ctx}/logistics/mobile/updatePackage.do">
			<input type="hidden" value="${orderInfo.order.orderId }"
				name="orderId" />
			<table id="scan">
				<tr>
					<td>包号：</td>
					<td><input autocomplete="off" class="span12" type="text"
						id="txtScan" name="packageId" /></td>
				</tr>
				<tr>
					<td>仓库：</td>
					<td><input autocomplete="off" disabled="true" class="span12"
						type="text" id="txtWare" name="warehouseId" /></td>
				</tr>
				<tr>
					<td>货架：</td>
					<td><input autocomplete="off" disabled="true" type="text"
						class="span12" id="txtShelf" name="shelfId" /></td>
				</tr>
				<tr>
					<td>位置：</td>
					<td><input autocomplete="off" disabled="true" type="text"
						class="span12" id="txtLocation" name="location" /></td>
				</tr>
			</table>

			<input class="span12 btn btn-primary" type="button" id="submit_btn"
				value="提交" />
		</form>
	</div>
	<table id="pack" class="table table-striped">
		<thead>
			<tr>
				<td>包号</td>
				<td>仓库</td>
				<td>货架</td>
				<td>位置</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="packageInfo" items="${orderInfo.packs}">
				<tr>
					<td>${packageInfo.packageId}</td>
					<td>${packageInfo.warehouseId }</td>
					<td>${packageInfo.shelfId}</td>
					<td>${packageInfo.location}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div>
		<form action="${ctx}/logistics/mobile/warehouseSubmit.do" onsubmit="return check();"
			method="post">
			<input type="hidden" value="${orderInfo.task.id}" name="taskId" />
			<input type="hidden" value="${orderInfo.order.orderId }" name="orderId" />
			
			<input class="span12 btn btn-primary" type="submit" value="完成入库"
				name="submit" />
		</form>
	</div>
</body>

<script type="text/javascript" src="${ctx}/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="${ctx}/css/bootstrap.min.css">
<link rel="stylesheet" href="${ctx}/views/logistics/mobile/detail.css">
<link href="${ctx}/css/bootstrap-responsive.min.css" rel="stylesheet">
</html>