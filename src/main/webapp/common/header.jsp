<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" import="nju.software.filter.AccessFilter"%>
<%@ page language="java" import="nju.software.dataobject.Account"%>
<%@ include file="/common/taglibs.jsp"%>
<!doctype html>
<head>
<title>智造链</title>
<meta http-equiv="content-type" content="text/html;charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@include file="/common/css_file.jsp"%>
<link rel="stylesheet" href="${ctx}/css/fmc/table.css">
<script type="text/javascript" src="${ctx}/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.tablesorter.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.noty.packaged.min.js"></script>

<!--  <script type="text/javascript" src="${ctx }/js/custom.js"></script>-->
<script>
$(function() {
	if ('${notify}' != null && '${notify}' != "") {
		noty({
			text : '${notify}',
			layout : 'topCenter',
			timeout : 2000
		});
	}
});
</script>
<script>
$(function() {
	if ('${orderStateMessage}' != null && '${orderStateMessage}' != "") {
		noty({
			text : '${orderStateMessage}',
			layout : 'topCenter'
		});
	}
});
</script>
<script type="text/javascript">
	$(function() {
		//	if($("table.tablesorter tbody tr").length!=0){
		$("table.tablesorter").tablesorter();
		//	}

		getTaskNumber();
		//$("li span.task").css("color","white");
		
	});
	
	function getTaskNumber() {
		$.ajax({
			url : "${ctx}/common/getTaskNumber.do",
			success : function(msg) {
				var json = eval("(" + msg + ")");
				for ( var key in json) {
					$("span." + key).text("  (" + json[key] + ")");
				}
			}
		});
	}

</script>
<style type="text/css">
table.tablesorter thead th {
	height: 20px;
}

table.tablesorter thead tr .header {
	background-image: url(${ctx}/images/blueimages/bg.gif);
	background-repeat: no-repeat;
	background-position: center right;
	cursor: pointer;
}

table.tablesorter thead tr .headerSortUp {
	background-image: url(${ctx}/images/blueimages/asc.gif);
}

table.tablesorter thead tr .headerSortDown {
	background-image: url(${ctx}/images/blueimages/desc.gif);
}
</style>

</head>
<body>
	<div id="mainwrapper" class="mainwrapper">