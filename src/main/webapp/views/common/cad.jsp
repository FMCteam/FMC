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
	<c:forEach var="versionRow" items="${orderInfo.versions }" >
		<tr>
			<td class='span12 version_size'>${versionRow.size }</td>
			<td class='span12 version_centerBackLength'>${versionRow.centerBackLength }</td>
			<td class='span12 version_bust'>${versionRow.bust }</td>
			<td class='span12 version_waistLine'>${versionRow.waistline }</td>
			<td class='span12 version_shoulder'>${versionRow.shoulder }</td>
			<td class='span12 version_buttock'>${versionRow.buttock }</td>
			<td class='span12 version_hem'>${versionRow.hem }</td>
			<td class='span12 version_trousers'>${versionRow.trousers }</td>
			<td class='span12 version_skirt'>${versionRow.skirt }</td>
			<td class='span12 version_sleeves'>${versionRow.sleeves }</td>
		</tr>
	</c:forEach>
</table>
<table class="table table-striped table-bordered table-hover detail">
	<tr>
		<td class="span2" rowspan="5">版型数据信息</td>
		<td class="span3">面料</td>
		<td class="span6" colspan="3"></td>
	</tr>
	<tr>
		<td class="span3">包装</td>
		<td class="span6" colspan="2"></td>
	</tr>
	<tr>
		<td class="span3">版型</td>
		<td colspan="3"></td>
	</tr>
	<tr>
		<td class="span1">装箱</td>
		<td colspan="2"></td>
	</tr>
	<tr>
		<td class="span1">工艺</td>
		<td colspan="6"></td>
	</tr>
</table>