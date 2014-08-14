<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>

<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
			<!--  如果是其它页面，这里是填充具体的内容。 -->
			<ul class="nav nav-tabs detail" id="tab">
				<li class="task-name">质量检查</li>
				<li class="active"><a href="#quality" data-toggle="tab">质量检查</a></li>
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
				<div class="tab-pane active" id="quality">
					<form id="check_quality_form" method="post" onSubmit="return getQuality()" 
					action="${ctx }/quality/checkQualitySubmit.do">
						<table class="table table-striped table-bordered table-hover detail">
							<tr>
								<td class="title" rowspan="${fn:length(orderInfo.produced)+1}">应收数量</td>
								<td class="title">颜色</td>
								<td class="title">XS</td>
								<td class="title">S</td>
								<td class="title">M</td>
								<td class="title">L</td>
								<td class="title">XL</td>
								<td class="title">XXL</td>
							</tr>
							<c:forEach var="produced" items="${orderInfo.produced}">
								<tr>
									<td>${produced.color}</td>
									<td>${produced.xs}</td>
									<td>${produced.s}</td>
									<td>${produced.m}</td>
									<td>${produced.l}</td>
									<td>${produced.xl}</td>
									<td>${produced.xxl}</td>
								</tr>
							</c:forEach>
							<tr>
								<td class="title">加工方</td>
								<td colspan="7">${orderInfo.order.payAccountInfo}</td>
							</tr>
							<tr>
								<td class="title" rowspan="${fn:length(orderInfo.produced)+1}">本次实收合格数量</td>
								<td class="title">颜色</td>
								<td class="title">XS</td>
								<td class="title">S</td>
								<td class="title">M</td>
								<td class="title">L</td>
								<td class="title">XL</td>
								<td class="title">XXL</td>
							</tr>
							<c:forEach var="produced" items="${orderInfo.produced}">
								<tr>
									<td><input class="span12 good_color" type="text"
										value="${produced.color}" readonly="readonly"/></td>
									<td><input class="span12 good_xs" type="number"
										min="0" required="required"  value="0"/></td>
									<td><input class="span12 good_s" type="number"
										min="0" required="required"   value="0"/></td>
									<td><input class="span12 good_m" type="number"
										min="0" required="required"   value="0"/></td>
									<td><input class="span12 good_l" type="number"
										min="0" required="required"   value="0"/></td>
									<td><input class="span12 good_xl" type="number"
										min="0" required="required"   value="0"/></td>
									<td><input class="span12 good_xxl" type="number"
										min="0" required="required"   value="0"/></td>
								</tr>
							</c:forEach>
							<!-- 
							<tr>
								<td class="title" rowspan="${fn:length(orderInfo.produced)+1}">质检不合格数量表</td>
								<td class="title">颜色</td>
								<td class="title">XS</td>
								<td class="title">S</td>
								<td class="title">M</td>
								<td class="title">L</td>
								<td class="title">XL</td>
								<td class="title">XXL</td>
							</tr>
							<c:forEach var="produced" items="${orderInfo.produced}">
								<tr>
									<td><input class="span12 bad_color" type="text"
										value="${produced.color}" readonly="readonly"/></td>
									<td><input class="span12 bad_xs" type="number" onkeyup="computeRepairAmount()"
										min="0" value="0" required="required"/></td>
									<td><input class="span12 bad_s" type="number" onkeyup="computeRepairAmount()"
										min="0" value="0" required="required"/></td>
									<td><input class="span12 bad_m" type="number" onkeyup="computeRepairAmount()"
										min="0" value="0" required="required"/></td>
									<td><input class="span12 bad_l" type="number" onkeyup="computeRepairAmount()"
										min="0" value="0" required="required"/></td>
									<td><input class="span12 bad_xl" type="number" onkeyup="computeRepairAmount()"
										min="0" value="0" required="required"/></td>
									<td><input class="span12 bad_xxl" type="number" onkeyup="computeRepairAmount()"
										min="0" value="0" required="required"/></td>
								</tr>
							</c:forEach>
							-->
						    
							<tr>
								<td class="title" colspan="1">加工方</td>
								<td>
									<input name="repair_side" class="span12" type="text"  required="required"/>
								</td>
								<td class="title" colspan="1">本次回修数量</td>
								<td>
									<input name="repair_number" class="span12" type="number" min="0"  value="0"/>
								</td>
								<td class="title" colspan="1">日期</td>
								<td>
									<input name="repair_time" class="span12" type="datetime-local" required="required"   id="input_day"/>
								</td>
								<td class="title" colspan="1">报废数量</td>
								<td>
									<input name="invalid_number" class="span12" value="0" type="number" min="0" />
								</td>
							</tr>
							
						</table>
						<table class="table table-striped table-bordered table-hover detail">
							<c:if test="${empty orderInfo.repairRecord}">
								<tr>
									<td class="title" style="width:22%;background: red;">收货记录</td>
									<td>无</td>
								</tr>
							</c:if>
							<c:if test="${!empty orderInfo.repairRecord}">
								<tr>
									<td class="title" rowspan="${fn:length(orderInfo.repairRecord)+1}" style="width:22%;background: #ff0000;">收货记录</td>
									<td class="title">日期</td>
									<td class="title">加工方</td>
									<td class="title">回修数量</td>
									<td class="title">合格实收数量</td>
									<td class="title">报废数量</td>
								</tr>
								<c:forEach var="repairRecord" items="${orderInfo.repairRecord}">
									<tr>
										<td>${repairRecord.repairTime}</td>
										<td>${repairRecord.repairSide}</td>
										<td>${repairRecord.repairAmount}</td>
										<td>${repairRecord.qualifiedAmount}</td>
										<td>${repairRecord.invalidAmount}</td>
									</tr>
								</c:forEach>
							</c:if>
						</table>
						
						<input type="hidden" name="orderId" value="${orderInfo.order.orderId}" />
						<input type="hidden" name="taskId" value="${orderInfo.taskId}" />
						
						<input id="good_color" name="good_color" type="hidden" /> 
						<input id="good_xs" name="good_xs" type="hidden" /> 
						<input id="good_s" name="good_s" type="hidden" /> 
						<input id="good_m" name="good_m" type="hidden" /> 
						<input id="good_l" name="good_l" type="hidden" /> 
						<input id="good_xl" name="good_xl" type="hidden" /> 
						<input id="good_xxl" name="good_xxl" type="hidden" />
						
						<input id="bad_color" name="bad_color" type="hidden" /> 
						<input id="bad_xs" name="bad_xs" type="hidden" /> 
						<input id="bad_s" name="bad_s" type="hidden" /> 
						<input id="bad_m" name="bad_m" type="hidden" /> 
						<input id="bad_l" name="bad_l" type="hidden" /> 
						<input id="bad_xl" name="bad_xl" type="hidden" /> 
						<input id="bad_xxl" name="bad_xxl" type="hidden" />
					
						<button class="btn btn-primary" onclick="history.back();">返回</button>
						<div class="action" style="float:right">
							<input id="save_this_check" type="submit" class="btn btn-primary btn-rounded" value="保存本次质检" style="background-color:#1E90FF" />
							<input id="complete_final_check" type="submit" class="btn btn-primary btn-rounded" value="完成最终质检" />
							<!-- 隐藏标签，判断是否是最终的质检 -->
							<input id="is_final" type="hidden" name="isFinal" value="false" />
						</div>
					</form>
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
<script type="text/javascript" src="${ctx}/js/order/add_quality.js"></script>
<script type="text/javascript" src="${ctx }/js/custom.js"></script>
<script>
jQuery(document).ready(function(){
	//保存此次质检
	jQuery("#save_this_check").click(function(){
		jQuery("#is_final").val("false");
		//alert("保存此次质检");
		//jQuery("#send_sample_form").submit();
	});
	//完成最终质检
	jQuery("#complete_final_check").click(function(){
		jQuery("#is_final").val("true");
		//jQuery("#send_sample_form").submit();
		//alert("完成最终质检");
	});
});
</script>

<%@include file="/common/footer.jsp"%>

