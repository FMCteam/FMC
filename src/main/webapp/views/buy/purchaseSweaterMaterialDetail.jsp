<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>

<div class="maincontent">
	<div class="maincontentinner">
		<ul class="nav nav-tabs detail" id="tab">
			<li class="task-name">毛衣面料采购</li>

			<li class="active"><a href="#sweater" data-toggle="tab">毛衣面料采购</a>
			</li>
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
			<div class="tab-pane active" id="sweater">
				<form onsubmit="return confirm('确认操作？')" method="post"
					action="${ctx}/buy/purchaseSweaterMaterialSubmit.do?taskId=${orderInfo.task.id}&orderId=${orderInfo.order.orderId}">
					<table
						class="table table-striped table-bordered table-hover detail">
						<tr>
							<td><input type="radio" name="task_name" id="task_name"
								value="无库存" /> <span><font color="red">无库存，需采购面料</font></span>
							</td>
							<td><input type="radio" name="task_name" id="task_name"
								value="有库存" checked="checked" /> <span><font
									color="blue">有库存，无需采购</font></span></td>
						</tr>
						<tr>

							<td><span>供 应 商 ：</span><input type="text" name="supplier">
							</td>

							<td><span>采购负责人：</span><input type="text"
								name="Purchase_director" value="${employee_name}"></td>
						</tr>
						<tr>
							<td><span>采购时间：</span><input type="date"
								name="Purchase_time" ></td>
							<td><span>毛线类型：</span><input type="text" name="Wool_type">
							</td>
						</tr>
						<tr>
							<td><span>重 量(Kg)：</span><input type="text"
								name="Wool_weight"></td>
							<td><span>总 价(RMB)：</span><input type="text" name="total_price">
							</td>
						</tr>
					</table>
					<!--  <button
							style="margin-left:0px"
							class="btn btn-danger btn-rounded" onclick="return fail()">
							<i class="icon-remove icon-white"></i>采购到毛衣原料</button> -->

					<div class="action" style="float: right">
						<button class="btn btn-primary btn-rounded noreapt">
							<i class="icon-ok icon-white"></i> 确 认 完 成
						</button>
					</div>
					<br />

					<!-- 
						<div class="action" style="float:right">
							<button class="btn btn-primary btn-rounded noreapt">
								<i class="icon-ok icon-white"></i>采购完成
							</button>	
						</div>
						<br>
				 <input type="text" value=${ orderInfo.task.id}/>
				 <br>
				 <input type="text" value=${ orderInfo.order.orderId}/>
				 <input type="submit" name ="完成采购" value="完成采购" />
				 -->
				</form>
				<button class="btn btn-primary" onclick="history.back();">返回</button>
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
<link rel="stylesheet" href="${ctx}/css/order/add_order.css">
<link rel="stylesheet" href="${ctx}/css/fmc/detail.css">
<link rel="stylesheet" href="${ctx}/views/buy/buy.css">
<script type="text/javascript" src="${ctx}/js/order/add_order.js"></script>
<script type="text/javascript" src="${ctx}/js/fmc/table.js"></script>
<script type="text/javascript" src="${ctx}/js/order/add_produce.js"></script>
<script type="text/javascript" src="${ctx }/js/custom.js"></script>
<!--  <script type="text/javascript" >
	function fail(){
		$("#result").val(1);
		return confirm("确认采购到毛衣原料？");
	}
	function success(){
		$("#result").val(0);
		return confirm("确认仓库有毛衣原料？");
	}
</script> -->
<%@include file="/common/footer.jsp"%>
