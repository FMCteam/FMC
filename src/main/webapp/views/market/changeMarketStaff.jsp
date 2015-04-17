<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<table class="table table-striped table-bordered table-hover detail">
	<tr>
		<td class="span4 title">申请理由</td>
		<td class="span8">
			<textarea class="span8 input-lg col-lg" rows="8" name="reason" id="reason"></textarea>
			<input type="hidden" name="orderId" value="${orderInfo.order.orderId}"></input>
			<input type="hidden" name="orderProcessId" value="${orderInfo.order.processId}"></input>
			<input type="hidden" name="employeeId" value="${orderInfo.order.employeeId}"></input>
		</td>
	</tr>
	<tr>
		<td class="span4 title">提交申请
			<span class="result"></span>
		</td>
		<td class="span12">
			<input type="submit" id="applySubmit" class="btn btn-primary" value="提交申请">
		</td>
	</tr>

</table>

<table class="table table-striped table-bordered" id="allApplication">
	
</table>