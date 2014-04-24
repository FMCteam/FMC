<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<table class="table table-striped table-bordered table-hover detail">
	<tr>
		<td class="span1">尺寸表/部位</td>
		<td class="span1">后中长</td>
		<td class="span1">胸围</td>
		<td class="span1">腰围</td>
		<td class="span1">肩宽</td>
		<td class="span1">臀围</td>
		<td class="span1">下摆</td>
		<td class="span1">裤长</td>
		<td class="span1">裙长</td>
		<td class="span1">袖长</td>
	</tr>
	<c:forEach var="version" items="${orderInfo.versions}">
		<tr>
			<td class="span1">${version.size}</td>
			<td class="span1">${version.centerBackLength}</td>
			<td class="span1">${version.bust}</td>
			<td class="span1">${version.waistline}</td>
			<td class="span1">${version.shoulder}</td>
			<td class="span1">${version.buttock}</td>
			<td class="span1">${version.hem}</td>
			<td class="span1">${version.trousers}</td>
			<td class="span1">${version.skirt}</td>
			<td class="span1">${version.sleeves}</td>
		</tr>
	</c:forEach>
</table>
<table class="table table-striped table-bordered table-hover detail">
	<tr>
		<td class="span2" rowspan="5">版型数据信息</td>
		<td class="span3">面料</td>
		<td class="span6" colspan="3">${orderInfo.designCad[0].cadFabric }</td>
	</tr>
	<tr>
		<td class="span3">包装</td>
		<td class="span6" colspan="2">${orderInfo.designCad[0].cadBox }</td>
	</tr>
	<tr>
		<td class="span3">版型</td>
		<td colspan="3">${orderInfo.designCad[0].cadVersionData }</td>
	</tr>
	<tr>
		<td class="span1">装箱</td>
		<td colspan="2">${orderInfo.designCad[0].cadPackage }</td>
	</tr>
	<tr>
		<td class="span1">工艺</td>
		<td colspan="6">${orderInfo.designCad[0].cadTech }</td>
	</tr>
</table>