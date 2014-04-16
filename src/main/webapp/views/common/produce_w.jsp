<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<table class="table table-striped table-bordered table-hover detail produce_table">
	<tr>
		<td colspan="8">大货加工具体要求 <input id="produce_color"
			type="hidden" name="produce_color" /> <input id="produce_xs"
			type="hidden" name="produce_xs" /> <input id="produce_s"
			type="hidden" name="produce_s" /> <input id="produce_m"
			type="hidden" name="produce_m" /> <input id="produce_l"
			type="hidden" name="produce_l" /> <input id="produce_xl"
			type="hidden" name="produce_xl" /> <input id="produce_xxl"
			type="hidden" name="produce_xxl" /></td>
	</tr>
	<tr>
		<td>颜色</td>
		<td>XS</td>
		<td>S</td>
		<td>M</td>
		<td>L</td>
		<td>XL</td>
		<td>XXL</td>
		<td>操作</td>
	</tr>
	<tr class="addrow">
		<td><input type="text" class="span12" /></td>
		<td><input type="text" class="span12" /></td>
		<td><input type="text" class="span12" /></td>
		<td><input type="text" class="span12" /></td>
		<td><input type="text" class="span12" /></td>
		<td><input type="text" class="span12" /></td>
		<td><input type="text" class="span12" /></td>
		<td><a>添加</a></td>
	</tr>
	<c:forEach var="produceRow" items="${orderInfo.produces }" >
		<tr>
			<td class='span12 produce_color'>${produceRow.color }</td>
			<td class='span12 produce_xs'>${produceRow.xs }</td>
			<td class='span12 produce_s'>${produceRow.s }</td>
			<td class='span12 produce_m'>${produceRow.m }</td>
			<td class='span12 produce_l'>${produceRow.l }</td>
			<td class='span12 produce_xl'>${produceRow.xl }</td>
			<td class='span12 produce_xxl'>${produceRow.xxl }</td>
			<td class='span12'><a onclick="deleteRow(this,'produce_table')">删除</a></td>
		</tr>
	</c:forEach>
	<tr>
		<td class="span1">大货总件数</td>
		<td class="span1">${orderInfo.order.askAmount}</td>
		<td class="span1">最迟交货时间</td>
		<td class="span1" colspan="2">${fn:substring(orderInfo.order.askDeliverDate,0,10)}</td>
		<td class="span1">完工时间（天）</td>
		<td class="span1">${orderInfo.order.askProducePeriod}</td>
	</tr>
</table>