<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<script type="text/javascript" src="${ctx }/js/jquery-1.9.1.min.js"></script>
<title>登录</title>
</head>
<body>
	<h3>智造链-验货</h3>
	<table class="table table-striped table-bordered table-hover">
		<tr>
			<td rowspan="2">订单信息</td>
			<td>订单编号</td>
			<td>接单业务员</td>
			<td>客户名字</td>
			<td>报价日期</td>

		</tr>
		<tr>
			<td>${orderInfo.order.orderId }</td>
			<td>${orderInfo.order.employeeId }</td>
			<td>${orderInfo.order.customerName }</td>
			<td>${orderInfo.order.orderTime}</td>

		</tr>


		<tr>
			<td colspan="5">具体货箱数量及装箱条码表对应物流单号表</td>
		</tr>
		<tr>
			<td>总货箱数</td>
			<td colspan="4">1</td>
		</tr>
		<tr>
			<td>条码列表</td>
			<td colspan="2">物流单号</td>
			<td colspan="2">物流公司</td>
		</tr>
		<tr>
			<td>1</td>
			<td colspan="2">1</td>
			<td colspan="2">1</td>
		</tr>
		<tr>
			<td rowspan="2">发货信息</td>
			<td>物流部发货人</td>
			<td>发货时间</td>
			<td colspan="2">发货备注</td>
		</tr>
		<tr>
			<td>1</td>
			<td>1</td>
			<td colspan="2">1</td>
		</tr>



		<tr>

			<td>物流费用</td>
			<td><input class="span12" name="logistics_cost"
				id="logistics_cost" placeholder="logistics_cost" type="text" /></td>
			<input type="hidden" name="orderId"
				value="${orderInfo.order.orderId }" />
			<input type="hidden" name="taskId" value="${orderInfo.taskId }" />
			<input type="hidden" name="pinId"
				value="${orderInfo.processInstanceId }" />
		</tr>






		<tr>

			<td colspan="3"><input type="submit" style="float: right;" /></td>
		</tr>




	</table>
</body>
</html>