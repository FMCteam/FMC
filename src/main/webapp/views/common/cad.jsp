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
		<td class="span2" rowspan="7">版型数据信息</td>
		<td class="span3">面料</td>
		<td class="span6" colspan="3">${orderInfo.designCad.cadFabric}</td>
	</tr>
	<tr>
		<td class="span3">包装</td>
		<td class="span6" colspan="2">${orderInfo.designCad.cadBox }</td>
	</tr>
	<tr>
		<td class="span3">版型</td>
		<td colspan="3">${orderInfo.designCad.cadVersionData }</td>
	</tr>
	<tr>
		<td class="span1">装箱</td>
		<td colspan="2">${orderInfo.designCad.cadPackage }</td>
	</tr>
	<tr>
		<td class="span1">工艺</td>
		<td colspan="6">${orderInfo.designCad.cadTech }</td>
	</tr>
	<tr>
		<td class="span1">其他</td>
		<td colspan="6">${orderInfo.designCad.cadOther}</td>
	</tr>
	</tr>
	<tr>
		<td class="span1">CAD</td>
		<td colspan="6">
			<c:if test="${orderInfo.cad!=null}">
			<form action="${ctx }/design/downloadCadSubmit.do" method="post" 
				nctype="multipart/form-data">
				<span style="margin-right: 80px">版本：${orderInfo.cad.cadVersion}</span>
				<span>上传时间：${orderInfo.cad.uploadTime}</span>
				<input style="margin-left: 80px" type="submit" class="btn btn-primary btn-rounded" value="下载"/>
				<input type="hidden" name="cadUrl" value="${orderInfo.cad.cadUrl}" />
				<input type="hidden" name="orderId" value="${orderInfo.order.orderId}"/>
				<input type="hidden" name="taskId" value="${orderInfo.taskId}" />
			</form>
			</c:if>
		</td>
	</tr>
</table>