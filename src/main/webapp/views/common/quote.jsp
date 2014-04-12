<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<table class="table table-striped table-bordered detail">
	<tr>
		<td rowspan="${fn}">面料报价</td>
		<td>面料名</td>
		<td>单件米耗</td>
		<td>价格</td>
		<td>单件成本</td>


	</tr>
	<tr>
		<c:forEach var="fabric" items="${fabric_list}">
			<tr>
				<td>${fabric.fabricName }</td>
				<input type="hidden" name="fabricName" value="${fabric.fabricName}" />
				<td><input class="span12" name="tear_per_meter"
					placeholder="tear_per_meter" type="text" /></td>
				<td><input class="span12" name="cost_per_meter"
					placeholder="cost_per_meter" type="text" /></td>
				<td><input class="span12" name="fabric_price"
					placeholder="fabric_price" type="text" /></td>
			</tr>
		</c:forEach>
	</tr>

	<tr>
		<td>面料损耗</td>
		<td><input class="span12" name="fabric_meters_cost"
			id="fabric_meters_cost" placeholder="fabric_meters_cost" type="text" /></td>
		<td>小计</td>
		<td><input class="span12" name="all_fabric_prices"
			id="all_fabric_prices" placeholder="all_fabric_prices" type="text" /></td>
	</tr>



	<tr>
		<td rowspan="6">辅料报价</td>
		<td>辅料名</td>
		<td>单件耗数</td>
		<td>价格</td>
		<td>单件成本</td>

	</tr>

	<tr>
		<c:forEach var="accessory" items="${accessory_list}">
			<tr>
				<td>${accessory.accessoryName }</td>
				<input type="hidden" name="accessoryName"
					value="${accessory.accessoryName}" />
				<td><input class="span12" name="tear_per_piece"
					placeholder="tear_per_piece" type="text" /></td>
				<td><input class="span12" name="cost_per_meter"
					placeholder="cost_per_piece" type="text" /></td>
				<td><input class="span12" name="accessory_price"
					placeholder="accessory_price" type="text" /></td>

			</tr>
		</c:forEach>
	</tr>

	<tr>
		<td>辅料损耗</td>
		<td><input class="span12" name="accessory_meters_cost"
			id="accessory_meters_cost" placeholder="accessory_meters_cost"
			type="text" /></td>
		<td>小计</td>
		<td><input class="span12" name="accessory_prices"
			id="accessory_prices" placeholder="accessory_prices" type="text" /></td>
	</tr>

	<tr>
		<td>面辅总计</td>
		<td colspan="2"><input class="span12" name="prices" id="prices"
			placeholder="prices" type="text" /></td>
		<td colspan="2"></td>

		<input type="hidden" name="orderId"
			value="${orderInfo.order.orderId }" />
		<input type="hidden" name="taskId" value="${orderInfo.task.id }" />

	</tr>
</table>