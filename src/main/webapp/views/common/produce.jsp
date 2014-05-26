<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<table class="table table-striped table-bordered table-hover detail">
	<tr>
		<td class="span1 title" rowspan="2">加工信息</td>
		<td class="span1 title" colspan="2">样衣总数</td>
		<td class="span1 title" colspan="2">大货总数</td>
		<td class="span1 title" colspan="2">最迟交货时间</td>
		<td class="span1 title" colspan="2">完工时间（天）</td>
	</tr>
	<tr>
		<td class="span1" colspan="2">${orderInfo.order.sampleAmount}</td>
		<td class="span1" colspan="2">${orderInfo.order.askAmount}</td>
		<td class="span1" colspan="2">${fn:substring(orderInfo.order.askDeliverDate,0,10)}</td>
		<td class="span1" colspan="2">${orderInfo.order.askProducePeriod}</td>
	</tr>
	<c:if test="${orderInfo.order.reorder==0 }">
		<tr>
			<td class="span1 title" rowspan="${fn:length(orderInfo.sample)+1}">样衣加工</td>
			<td class="span1 title">颜色</td>
			<td class="span1 title">XS</td>
			<td class="span1 title">S</td>
			<td class="span1 title">M</td>
			<td class="span1 title">L</td>
			<td class="span1 title">XL</td>
			<td class="span1 title">XXL</td>
			<td class="span1 title">总计</td>
		</tr>
		<c:forEach var="sample" items="${orderInfo.sample}">
			<tr>
				<td>${sample.color}</td>
				<td>${sample.xs}</td>
				<td>${sample.s}</td>
				<td>${sample.m}</td>
				<td>${sample.l}</td>
				<td>${sample.xl}</td>
				<td>${sample.xxl}</td>
				<td>${sample.produceAmount}</td>
			</tr>
		</c:forEach>
	</c:if>
	
	<tr>
		<td class="span1 title" rowspan="${fn:length(orderInfo.produce)+1}">大货加工</td>
		<td class="span1 title">颜色</td>
		<td class="span1 title">XS</td>
		<td class="span1 title">S</td>
		<td class="span1 title">M</td>
		<td class="span1 title">L</td>
		<td class="span1 title">XL</td>
		<td class="span1 title">XXL</td>
		<td class="span1 title">总计</td>
	</tr>
	<c:forEach var="produce" items="${orderInfo.produce}">
		<tr>
			<td>${produce.color}</td>
			<td>${produce.xs}</td>
			<td>${produce.s}</td>
			<td>${produce.m}</td>
			<td>${produce.l}</td>
			<td>${produce.xl}</td>
			<td>${produce.xxl}</td>
			<td>${produce.produceAmount}</td>
		</tr>
	</c:forEach>
</table>