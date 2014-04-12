<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<table class="table table-striped table-bordered table-hover detail">
	<tr>
		<td class="span1" rowspan="2">样衣加工</td>
		<td class="span1">颜色</td>
		<td class="span1">XS</td>
		<td class="span1">S</td>
		<td class="span1">M</td>
		<td class="span1">L</td>
		<td class="span1">XL</td>
		<td class="span1">XXL</td>
	</tr>
	<tr>
		<td class="span1" rowspan="2">大货加工</td>
		<td class="span1">颜色</td>
		<td class="span1">XS</td>
		<td class="span1">S</td>
		<td class="span1">M</td>
		<td class="span1">L</td>
		<td class="span1">XL</td>
		<td class="span1">XXL</td>
	</tr>
	<tr>
		<td class="span1">大货总件数</td>
		<td class="span1">${orderInfo.order.askAmount}</td>
		<td class="span1">最迟交货时间</td>
		<td class="span1" colspan="2">${fn:substring(orderInfo.order.askDeliverDate,0,10)}</td>
		<td class="span1">完工时间（天）</td>
		<td class="span1">${orderInfo.order.askProducePeriod}</td>
	</tr>
</table>