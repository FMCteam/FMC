<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<table class="table table-striped table-bordered table-hover">
	<tr>
		<td rowspan="2">加工信息</td>
		<td>加工件数<span class="required">*</span></td>
		<td colspan="2">最迟交货时间</td>
		<td colspan="2">完工时间（天）</td>
		<td>码数</td>
	</tr>
	<tr>
		<td>${orderInfo.order.askAmount}</td>
		<td colspan="2">${fn:substring(orderInfo.order.askDeliverDate,0,10)}</td>
		<td colspan="2">${orderInfo.order.askProducePeriod }</td>
		<td>${orderInfo.order.askCodeNumber }</td>
	</tr>
</table>