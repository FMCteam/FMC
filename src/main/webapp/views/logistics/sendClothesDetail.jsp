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
								<td class="span2 title" rowspan="5">物流信息</td>
								<td class="span1 title">衣服箱数</td>
								<td class="span1 title">收货人</td>
								<td class="span3 title">手机</td>
								<td class="span3 title">收货地址</td>
							</tr>
							<tr>
								<td>${orderInfo.packageNumber}</td>
								<td>${orderInfo.logistics.sampleClothesName }</td>
								<td>${orderInfo.logistics.sampleClothesPhone }</td>
								<td>${orderInfo.logistics.sampleClothesAddress }</td>
							</tr>
							<tr>
								<td class="title">邮寄时间<span style="color: red">*</span></td>
								<td class="title">快递名称<span style="color: red">*</span></td>
								<td class="title">快递单号<span style="color: red">*</span></td>
								<td class="title">快递价格<span style="color: red">*</span></td>
							</tr>
							<tr>
								<td><input type="date" name="time" required="required" id="input_day"/></td>
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
								<td class="title">其他备注</td>
								<td colspan="4"><input class="span12" type="text"
									name="remark" /></td>
							</tr>
						</table>
						
						<table class="table table-striped table-bordered table-hover detail">
							<c:if test="${empty orderInfo.sendProductRecord}">
								<tr>
									<td class="title" style="width:22%;">大货发货记录</td>
									<td>无</td>
								</tr>
							</c:if>
							<c:if test="${!empty orderInfo.sendProductRecord}">
								<tr>
									<td class="title" rowspan="${fn:length(orderInfo.sendProductRecord) + 1}">大货发货记录</td>
									<td class="title">发货时间</td>
									<td class="title">快递名称</td>
									<td class="title">快递单号</td>
									<td class="title">快递价格</td>
									<td class="title">备注</td>
								</tr>
								<c:forEach var="sendProductRecord" items="${orderInfo.sendProductRecord}">
									<tr>
										<td>${sendProductRecord.sendTime}</td>
										<td>${sendProductRecord.expressName}</td>
										<td>${sendProductRecord.expressNumber}</td>
										<td>${sendProductRecord.expressPrice}</td>
										<td>${sendProductRecord.remark}</td>
									</tr>
								</c:forEach>
							</c:if>
						</table>
						<!-- 
						<div class="action">
							<input class="btn btn-primary" type="submit" value="完成发货" />
						</div>
						-->
						<button class="btn btn-primary" onclick="history.back();">返回</button>
						<div class="action" style="float:right">
							<input id="save_this_send" class="btn btn-primary" type="submit" value="保存此次发货" style="background-color:#1E90FF" />
							<input id="complete_final_send" class="btn btn-primary" type="submit" value="完成最终发货" />
							
							<!-- 隐藏标签，判断是否是最终的发货 -->
							<input id="is_final" type="hidden" name="isFinal" value="false" />
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
<script>
jQuery(document).ready(function(){
	//保存此次发货
	jQuery("#save_this_send").click(function(){
		jQuery("#is_final").val("false");
		//alert("保存此次发货");
		//jQuery("#send_sample_form").submit();
	});
	//完成最终发货
	jQuery("#complete_final_send").click(function(){
		jQuery("#is_final").val("true");
		//jQuery("#send_sample_form").submit();
		//alert("完成最终发货");
	});
});
</script>
<%@include file="/common/footer.jsp"%>
