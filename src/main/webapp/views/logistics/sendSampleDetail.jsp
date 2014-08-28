<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>

<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
			<!--  如果是其它页面，这里是填充具体的内容。 -->
			<ul class="nav nav-tabs detail" id="tab">
				<li class="task-name">样衣发货</li>
				<li><a href="#quote" data-toggle="tab">报价信息</a></li>
				<li><a href="#cad" data-toggle="tab">版型信息</a></li>
				<li><a href="#produce" data-toggle="tab">加工信息</a></li>
				<li class="active"><a href="#sample" data-toggle="tab">样衣信息</a></li>
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
				<div class="tab-pane  active" id="sample">
					<form id="send_sample_form" action="${ctx}/logistics/sendSampleSubmit.do" method="post"
					onSubmit="return confirmSendSampleSubmit();">
						<input type="hidden" name="orderId"
							value="${orderInfo.order.orderId}" /> <input type="hidden"
							name="taskId" value="${orderInfo.task.id}" />
						<table
							class="table table-striped table-bordered table-hover detail">
							<tr>
								<td class="span2 title" rowspan="2">客户样衣</td>
								<td class="span2 title">提供样衣</td>
								<td class="span3 title">邮寄时间</td>
								<td class="span2 title">快递名称</td>
								<td class="span3 title">快递单号</td>
							</tr>
							<tr>
								<td>${orderInfo.order.hasPostedSampleClothes==0?'没有样衣':''}
									${orderInfo.order.hasPostedSampleClothes==1?'未收到样衣':''}
									${orderInfo.order.hasPostedSampleClothes==2?'收到样衣':''}</td>
								<td>${fn:substring(orderInfo.logistics.inPostSampleClothesTime,0,10) }</td>
								<td>${orderInfo.logistics.inPostSampleClothesType }</td>
								<td colspan="3">${orderInfo.logistics.inPostSampleClothesNumber }</td>
							</tr>
							<tr>
								<td class="title" rowspan="5">生产样衣</td>
								<td class="title" rowspan="1">制作样衣</td>
								<td class="title">收件人</td>
								<td class="title">手机</td>
								<td class="title" colspan="3">收件地址</td>
							</tr>
							<tr>
								<td rowspan="1">${orderInfo.order.isNeedSampleClothes==0?'否':'是' }</td>
								<td>${orderInfo.logistics.sampleClothesName}&nbsp</td>
								<td>${orderInfo.logistics.sampleClothesPhone}&nbsp</td>
								<td colspan="3">${orderInfo.logistics.sampleClothesAddress}&nbsp</td>
							</tr>
							<tr>
								<td class="title">快递名称<span style="color: red">*</span></td>
								<td class="title">快递单号<span style="color: red">*</span></td>
								<td class="title">快递价格（元）<span style="color: red">*</span></td>
								<td class="title">邮寄时间<span style="color: red">*</span></td>
							</tr>
							<tr>
								<td><select name="name" style="margin: 0px">
										<option value="顺丰">顺丰</option>
										<option value="韵达">韵达</option>
										<option value="圆通">圆通</option>
										<option value="中通">中通</option>
										<option value="申通">申通</option>
										<option value="汇通">汇通</option>
										<option value="EMS">EMS</option>
								</select></td>
								<td><input type="text"
									name="number" required="required" /></td>
								<td><input type="text" name="price" required="required"/></td>
								<td><input type="text" name="time" required="required" id="input_day"  readonly="readonly"/></td>
							</tr>
							<tr>
								<td class="title">其他备注</td>
								<td colspan="5">${orderInfo.logistics.sampleClothesRemark }</td>
							</tr>
							<tr>
								<td class="title">样衣信息</td>
								<td class="title">样衣图片</td>
								<td><c:if test="${orderInfo.order.sampleClothesPicture!=null}">
										<a class="fancybox" href="${orderInfo.order.sampleClothesPicture}" title="样衣图片">
											<img src="${orderInfo.order.sampleClothesPicture}"
												style="max-height: 300px;" alt="样衣图片"></img></a>
									</c:if></td>
								<td class="title">参考图片</td>
								<td colspan="2">
									<c:if test="${orderInfo.order.referencePicture!=null}">
										<a class="fancybox" href="${orderInfo.order.referencePicture}" title="样衣图片">
											<img src="${orderInfo.order.referencePicture}"
												style="max-height: 300px;" alt="参考图片"></img></a>
									</c:if></td>
							</tr>
						</table>
						
						<table class="table table-striped table-bordered table-hover detail">
							<c:if test="${empty orderInfo.deliveryRecord}">
								<tr>
									<td class="title" style="width:22%;background: red;">样衣发货记录</td>
									<td>无</td>
								</tr>
							</c:if>
							<c:if test="${!empty orderInfo.deliveryRecord}">
								<tr>
									<td class="title" rowspan="${fn:length(orderInfo.deliveryRecord) + 1}" style="width:18%;background: red;">发货记录</td>
									<td class="title">快递名称</td>
									<td class="title">快递单号</td>
									<td class="title">快递价格</td>
									<td class="title">发货时间</td>
								</tr>
								<c:forEach var="deliveryRecord" items="${orderInfo.deliveryRecord}">
									<tr>
										<td>${deliveryRecord.expressName}</td>
										<td>${deliveryRecord.expressNumber}</td>
										<td>${deliveryRecord.expressPrice}</td>
										<td>${deliveryRecord.sendTime}</td>
									</tr>
								</c:forEach>
							</c:if>
						</table>
						<button class="btn btn-primary" onclick="history.back();">返回</button>
						<div class="action" style="float:right">
							<input id="save_this_send" class="btn btn-primary" type="submit" value="保存此次发货" style="background-color:#1E90FF" />
							<!-- 
							<input id="complete_final_send" class="btn btn-primary" type="submit" value="完成最终发货" />
							-->
							
							<a href="${ctx}/logistics/sendSampleSubmit.do?orderId=${orderInfo.order.orderId}&taskId=${orderInfo.task.id}&isFinal=true"
							   onclick="return confirmSendSampleSubmit();"
							   class="btn btn-primary">完成最终发货</a>
							
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
<script type="text/javascript" src="${ctx}/views/logistics/logistics.js"></script>
<script type="text/javascript" src="${ctx }/js/custom.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/css/zoom_img/jquery.fancybox.css" media="screen" />
<script type="text/javascript" src="${ctx}/js/zoom_img/jquery.fancybox.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	$('.fancybox').fancybox();	
});
</script>

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
