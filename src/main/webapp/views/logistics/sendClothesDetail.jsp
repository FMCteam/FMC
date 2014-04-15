<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>

<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
			<!--  如果是其它页面，这里是填充具体的内容。 -->
			<ul class="nav nav-tabs detail" id="tab">
				<li><a href="#quote" data-toggle="tab">报价信息</a></li>
				<li><a href="#cad" data-toggle="tab">版型信息</a></li>
				<li><a href="#produce" data-toggle="tab">加工信息</a></li>
				<li><a href="#sample" data-toggle="tab">样衣信息</a></li>
				<li><a href="#material" data-toggle="tab">面辅信息</a></li>
				<li class="active"><a href="#basic" data-toggle="tab">基本信息</a></li>
			</ul>

			<div class="tab-content">
				<div class="tab-pane active" id="basic">
					<%@include file="/views/common/basic.jsp"%>
				</div>
				<div class="tab-pane" id="material">
					<%@include file="/views/common/material.jsp"%>
				</div>
				<div class="tab-pane" id="sample">
					<form action="${ctx}/logistics/sendSampleSubmit.do" method="post">
						<input type="hidden" name="orderId"
							value="${orderInfo.order.orderId}" /> <input type="hidden"
							name="taskId" value="${orderInfo.task.id}" />
						<table
							class="table table-striped table-bordered table-hover detail">
							<tr>
								<td rowspan="5">物流信息</td>
								<td>衣服箱数</td>
								<td>收货人</td>
								<td>手机</td>
								<td>收货地址</td>
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
								<td colspan="3">快递单号</td>
								<td>快递价格</td>
							</tr>
							<tr>
								<td><input type="date" name="time" /></td>
								<td><input type="text" name="name" /></td>
								<td colspan="3"><input type="text" name="number" /></td>
							</tr>
							<tr>
								<td>其他备注</td>
								<td colspan="4"><input class="span12" type="text" name="remark" /></td>
							</tr>
							<tr>
								<td>操作</td>
								<td><input type="submit" value="发货成功"/></td>
							</tr>
						</table>
					</form>
				</div>
				<div class="tab-pane" id="produce">
					<%@include file="/views/common/produce.jsp"%>
				</div>
				<div class="tab-pane" id="cad">
					<%@include file="/views/common/cad.jsp"%>
				</div>
				<div class="tab-pane" id="quote">
					<%@include file="/views/common/quote.jsp"%>
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
