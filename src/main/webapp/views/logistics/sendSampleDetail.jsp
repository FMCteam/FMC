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
								<td rowspan="2">客户样衣</td>
								<td>提供样衣</td>
								<td>邮寄时间</td>
								<td>快递名称</td>
								<td colspan="3">快递单号</td>
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
								<td rowspan="5">生产样衣</td>
								<td rowspan="2">制作样衣</td>
								<td>邮寄人</td>
								<td>手机</td>
								<td colspan="3">邮寄地址</td>
							</tr>
							<tr>
								<td>${orderInfo.logistics.sampleClothesName }</td>
								<td>${orderInfo.logistics.sampleClothesPhone }</td>
								<td colspan="3">${orderInfo.logistics.sampleClothesAddress }</td>

							</tr>
							<tr>
								<td rowspan="2">${orderInfo.order.isNeedSampleClothes==0?'否':'是' }</td>
								<td>邮寄时间</td>
								<td>快递名称</td>
								<td colspan="3">快递单号</td>
							</tr>
							<tr>
								<td><input type="date" name="time" /></td>
								<td><input type="text" name="name" /></td>
								<td colspan="3"><input type="text" name="number" /></td>
							</tr>
							<tr>
								<td>其他备注</td>
								<td colspan="5">${orderInfo.logistics.sampleClothesRemark }</td>
							</tr>
							<tr>
								<td>样衣信息</td>
								<td>样衣图片</td>
								<td colspan="2"><img
									src="${ctx}/${orderInfo.order.sampleClothesPicture}" alt="没有图片"></img></td>
								<td>参考图片</td>
								<td colspan="2"><img
									src="${ctx}/${orderInfo.order.referencePicture}" alt="没有图片"></img></td>
							</tr>
							<tr>
								<td>操作</td>
								<td><input type="submit" value="确认发货"/></td>
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
