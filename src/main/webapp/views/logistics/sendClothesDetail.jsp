<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>

<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
			<!--  如果是其它页面，这里是填充具体的内容。 -->
			<ul class="nav nav-tabs detail" id="tab">
				<li class="task-name">大货发货</li>
				<li><a href="#warehouse" data-toggle="tab">入库信息</a></li>
				<li><a href="#cad" data-toggle="tab">版型信息</a></li>
				<li><a href="#produce" data-toggle="tab">加工信息</a></li>
				<li class="active"><a href="#logistics" data-toggle="tab">发货信息</a></li>
				<li><a href="#material" data-toggle="tab">面辅信息</a></li>
				<li><a href="#basic" data-toggle="tab">基本信息</a></li>
			</ul>

			<div class="tab-content">
				<div class="tab-pane" id="basic">
					<%@include file="/views/common/basic.jsp"%>
				</div>
				<div class="tab-pane" id="material">
					<%@include file="/views/common/material.jsp"%>
				</div>
				<div class="tab-pane active" id="logistics">
					<form action="${ctx}/logistics/sendClothesSubmit.do" method="post"
						onsubmit="return confirm('确认操作？');">
						<input type="hidden" name="orderId"
							value="${orderInfo.order.orderId}" /> <input type="hidden"
							name="taskId" value="${orderInfo.task.id}" />
						<table
							class="table table-striped table-bordered table-hover detail">
							<tr>
								<td class="span2" rowspan="5">物流信息</td>
								<td class="span1">衣服箱数</td>
								<td class="span1">收货人</td>
								<td class="span3">手机</td>
								<td class="span3">收货地址</td>
							</tr>
							<tr>
								<td>10</td>
								<td>${orderInfo.logistics.sampleClothesName }</td>
								<td>${orderInfo.logistics.sampleClothesPhone }</td>
								<td>${orderInfo.logistics.sampleClothesAddress }</td>
							</tr>
							<tr>
								<td>邮寄时间</td>
								<td>快递名称</td>
								<td>快递单号</td>
								<td>快递价格</td>
							</tr>
							<tr>
								<td><input type="date" name="time" required="required" /></td>
								<td><select name="name" style="margin: 0px">
										<option value="顺丰">顺丰</option>
										<option value="韵达">韵达</option>
										<option value="圆通">圆通</option>
										<option value="中通">中通</option>
										<option value="申通">申通</option>
										<option value="汇通">汇通</option>
										<option value="EMS">EMS</option>
								</select></td>
								<td><input type="text" name="number" required="required" /></td>
								<td><input type="text" name="price" required="required" /></td>
							</tr>
							<tr>
								<td>其他备注</td>
								<td colspan="4"><input class="span12" type="text"
									name="remark" /></td>
							</tr>
						</table>
						<div class="action">
							<input class="btn btn-primary" type="submit" value="完成发货" />
						</div>
					</form>
				</div>
				<div class="tab-pane" id="produce">
					<%@include file="/views/common/produce.jsp"%>
				</div>
				<div class="tab-pane" id="cad">
					<%@include file="/views/common/cad.jsp"%>
				</div>
				<div class="tab-pane" id="warehouse">
					<%@include file="/views/common/warehouse.jsp"%>
				</div>
			</div>
		</div>
	</div>


	<div class="footer">
		<div class="footer-left">
			<span>&copy; 2014. 江苏南通智造链有限公司.</span>
		</div>
	</div>


</div>
</div>




<%@include file="/common/js_file.jsp"%>
<%@include file="/common/js_form_file.jsp"%>
<link rel="stylesheet" href="${ctx}/css/fmc/table.css">
<script type="text/javascript" src="${ctx}/js/fmc/table.js"></script>
<link rel="stylesheet" href="${ctx}/css/fmc/detail.css">
<script type="text/javascript" src="${ctx}/js/order/add_order.js"></script>
<script type="text/javascript" src="${ctx }/js/custom.js"></script>
<%@include file="/common/footer.jsp"%>
