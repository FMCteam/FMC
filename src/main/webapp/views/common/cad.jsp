<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<table class="table table-striped table-bordered table-hover detail">
	<tr>
		<td class="span1 title">尺寸表/部位</td>
		<td class="span1 title">后中长</td>
		<td class="span1 title">胸围</td>
		<td class="span1 title">腰围</td>
		<td class="span1 title">肩宽</td>
		<td class="span1 title">臀围</td>
		<td class="span1 title">下摆</td>
		<td class="span1 title">裤长</td>
		<td class="span1 title">裙长</td>
		<td class="span1 title">袖长</td>
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
		<td class="span2 title" rowspan="7">版型数据信息</td>
		<td class="span3 title">面料</td>
		<td class="span6" colspan="3">${orderInfo.designCad.cadFabric}</td>
	</tr>
	<tr>
		<td class="span3 title">包装</td>
		<td class="span6" colspan="2">${orderInfo.designCad.cadBox }</td>
	</tr>
	<tr>
		<td class="span3 title">版型</td>
		<td colspan="3">${orderInfo.designCad.cadVersionData }</td>
	</tr>
	<tr>
		<td class="span1 title">装箱</td>
		<td colspan="2">${orderInfo.designCad.cadPackage }</td>
	</tr>
	<tr>
		<td class="span1 title">工艺</td>
		<td colspan="6">${orderInfo.designCad.cadTech }</td>
	</tr>
	<tr>
		<td class="span1 title">其他</td>
		<td colspan="6">${orderInfo.designCad.cadOther}</td>
	</tr>
	</tr>
	<tr>
		<td class="span1 title">CAD</td>
		<td colspan="6">
			<c:if test="${orderInfo.designCad.cadUrl!=null}">
			<form action="${ctx }/downloadCadSubmit.do" method="post" 
				nctype="multipart/form-data">
				<span style="margin-right: 80px">版本：${orderInfo.designCad.cadVersion}</span>
				<span>上传时间：${orderInfo.designCad.uploadTime}</span>
				<input style="margin-left: 80px" type="submit" class="btn btn-primary btn-rounded" value="下载"/>
				<input type="hidden" name="cadUrl" value="${orderInfo.designCad.cadUrl}" />
				<input type="hidden" name="orderId" value="${orderInfo.order.orderId}"/>
				<input type="hidden" name="taskId" value="${orderInfo.taskId}" />
			</form>
			</c:if>
		</td>
	</tr>
</table>