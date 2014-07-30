<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>

<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
			<!--  如果是其它页面，这里是填充具体的内容。 -->
			<ul class="nav nav-tabs detail" id="tab">
				<li class="task-name">工艺加工</li>
				<li class="active"><a href="#craftSample" data-toggle="tab">工艺报价</a></li>
				<li><a href="#quote" data-toggle="tab">报价信息</a></li>
				<li><a href="#cad" data-toggle="tab">版型信息</a></li>
				<li><a href="#produce" data-toggle="tab">加工信息</a></li>
				<li><a href="#sample" data-toggle="tab">样衣信息</a></li>
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
				<div class="tab-pane" id="sample">
					<%@include file="/views/common/sample.jsp"%>
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
			<div class="tab-pane active" id="craft">
						<table class="table table-striped table-bordered table-hover detail">
						    <tr>
								<td>客户工艺要求：</td>
								<td colspan="12">
								 ${orderInfo.designCadTech}
								</td>
							</tr>	
							<tr>
								<td >印花费（元/件）：
								</td>
								<td >水洗吊染费（元/件）：
								</td>
								<td  >激光费（元/件）：
								</td>
								<td >刺绣费（元/件）：
								</td>
								<td  >压皱费（元/件）：
								</td>
								<td >开版费用（元/件）：
								</td>
							</tr>
							<tr>
							<td  >${ orderInfo.craft.stampDutyMoney}</td>
							<td  >${ orderInfo.craft.washHangDyeMoney}</td>
							<td  >${ orderInfo.craft.laserMoney}</td>
							<td  >${ orderInfo.craft.embroideryMoney}</td>
							<td  >${ orderInfo.craft.crumpleMoney}</td>
							<td  >${ orderInfo.craft.openVersionMoney}</td>
							</tr>
							
							<tr>
		                        
		                        <td class="title">工艺图片</td>
		                        <td colspan="5"><c:if test="${orderInfo.craft.craftFileUrl!=null}">
				                <img src="${ctx}/common/getPic.do?type=craftFileUrl&orderId=${orderInfo.order.orderId}"
					                 style="max-height: 300px;" alt="工艺图片"></img>
			                    </c:if></td>
	                        </tr>
							
						</table>
						
					<div class="action">
						<button class="btn btn-primary" onclick="history.back();">返回</button>						
						<a
							href="${ctx}/design/needCraftProductSubmit.do?orderId=${orderInfo.order.orderId}&taskId=${orderInfo.task.id}"
							class="btn btn-primary">完成工艺制作</a> 
			        </div>
						
				 
 				</div>				
			</div>

		</div>
		<!--row-fluid-->

		<div class="footer">
			<div class="footer-left">
				<span>&copy; 2014. 江苏南通智造链有限公司.</span>
			</div>
		</div>
		<!--footer-->

	</div>
	<!--maincontentinner-->
</div>
<!--maincontent-->


<%@include file="/common/js_file.jsp"%>
<%@include file="/common/js_form_file.jsp"%>
<link rel="stylesheet" href="${ctx}/css/order/add_order.css">
<script type="text/javascript" src="${ctx}/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="${ctx}/js/order/add_order.js"></script>
<link rel="stylesheet" href="${ctx}/css/fmc/table.css">
<script type="text/javascript" src="${ctx}/js/fmc/table.js"></script>
<link rel="stylesheet" href="${ctx}/css/fmc/detail.css">
<link rel="stylesheet" href="${ctx}/css/order/add_order.css">
<script type="text/javascript" src="${ctx }/js/custom.js"></script>
<script type="text/javascript">
$(document).ready(function() {
 var text=$("#pay").text();
	$("#pay").text(parseFloat(text).toFixed(2));
	 
});  
</script>
<%@include file="/common/footer.jsp"%>
