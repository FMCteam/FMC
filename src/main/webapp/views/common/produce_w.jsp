<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<table class="table table-striped table-bordered table-hover detail">
	<tr>
		<td class="span2" rowspan="2">加工信息</td>
		<td class="span1" colspan="2">样衣总数</td>
		<td class="span1" colspan="2">大货总数</td>
		<td class="span1" colspan="2">最迟交货时间</td>
		<td class="span1" colspan="2">完工时间（天）</td>
	</tr>
	<tr>
		<td class="span1" colspan="2">${orderInfo.order.sampleAmount}</td>
		<td class="span1 ask_amount" colspan="2">${orderInfo.order.askAmount}</td>
		<td class="span1" colspan="2">${fn:substring(orderInfo.order.askDeliverDate,0,10)}</td>
		<td class="span1" colspan="2">${orderInfo.order.askProducePeriod}</td>
	</tr>
	<c:if test="${orderInfo.order.reorder==0 }">	
		<tr>
			<td class="span2" rowspan="${fn:length(orderInfo.sample)+1}">样衣加工</td>
			<td class="span1">颜色</td>
			<td class="span1">XS</td>
			<td class="span1">S</td>
			<td class="span1">M</td>
			<td class="span1">L</td>
			<td class="span1">XL</td>
			<td class="span1">XXL</td>
			<td class="span1">总计</td>
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
</table>
<table class="table table-striped table-bordered table-hover detail">
	<tr>
		<td class="span2">大货加工 </td>
		<td colspan="8" class="innertable">
			<table class="span12 table table-bordered table-hover produce_table">
				<tr>
					<td class="span1">颜色</td>
					<td class="span1">XS</td>
					<td class="span1">S</td>
					<td class="span1">M</td>
					<td class="span1">L</td>
					<td class="span1">XL</td>
					<td class="span1">XXL</td>
					<td class="span1">操作</td>
				</tr>
				<tr class="addrow">
					<td><input type="text" class="span12" /></td>
					<td><input type="text" class="span12" /></td>
					<td><input type="text" class="span12" /></td>
					<td><input type="text" class="span12" /></td>
					<td><input type="text" class="span12" /></td>
					<td><input type="text" class="span12" /></td>
					<td><input type="text" class="span12" /></td>
					<td><a>添加</a><span class="required">（点击添加之后数据生效）</span></td>
				</tr>
				<c:forEach var="produceRow" items="${orderInfo.produce}">
					<tr>
						<td class='span12 produce_color'>${produceRow.color }</td>
						<td class='span12 produce_xs'>${produceRow.xs }</td>
						<td class='span12 produce_s'>${produceRow.s }</td>
						<td class='span12 produce_m'>${produceRow.m }</td>
						<td class='span12 produce_l'>${produceRow.l }</td>
						<td class='span12 produce_xl'>${produceRow.xl }</td>
						<td class='span12 produce_xxl'>${produceRow.xxl }</td>
						<td class='span12'><a
							onclick="deleteRow(this,'produce_table')">删除</a></td>
					</tr>
				</c:forEach>
				</table></td>
	</tr>
</table>
