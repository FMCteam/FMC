<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>

<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
			<!--  如果是其它页面，这里是填充具体的内容。 -->
			<div class="widget">
				<h4 class="widgettitle">采购验证</h4>
				<div class="widgetcontent">

						<table class="table table-striped table-bordered table-hover">
							<tr>
								<td rowspan="3">客户信息</td>
								<td>客户编号</td>
								<td>姓名</td>
								<td>公司</td>
								<td>传真</td>
								<td>手机1</td>
								<td>手机2</td>
							</tr>
							<tr>
								<td>${orderModel.order.customerId }</td>
								<td>${orderModel.order.customerName }</td>
								<td>${orderModel.order.customerCompany }</td>
								<td>${orderModel.order.customerCompanyFax}</td>
								<td>${orderModel.order.customerPhone1}</td>
								<td>${orderModel.order.customerPhone2}</td>
							</tr>
							<tr>
								<td>公司地址</td>
								<td colspan="5">${orderModel.order.customerCompanyAddress}</td>
							</tr>
							<tr>
								<td rowspan="6">款式信息</td>
								<td><label>款式名称<span class="required">*</span></label></td>
								<td colspan="2">款式性别<span class="required">*</span></td>
								<td colspan="2">款式季节<span class="required">*</span></td>
								<td>订单来源<span class="required">*</span></td>
							</tr>
							<tr>
								<td>${orderModel.order.styleName }</td>
								<td colspan="2">${orderModel.order.styleSex }</td>
								<td colspan="2">${orderModel.order.styleSeason }</td>
								<td>${orderModel.order.orderSource }</td>
							</tr>
							<tr>
								<td>面料类型</td>
								<td colspan="5">${orderModel.order.fabricType }</td>
							</tr>
							<tr>
								<td>特殊工艺</td>
								<td colspan="5">${orderModel.order.specialProcess }</td>
							</tr>
							<tr>
								<td>其他说明</td>
								<td colspan="5">${orderModel.order.otherRequirements }</td>
							</tr>
							<tr>
								<td>参考链接</td>
								<td colspan="5"></td>
							</tr>
							<tr>
								<td rowspan="2">加工信息</td>
								<td>加工件数<span class="required">*</span></td>
								<td colspan="2">最迟交货时间</td>
								<td colspan="2">完工时间（天）</td>
								<td>码数</td>
							</tr>
							<tr>
								<td>${orderModel.order.askAmount }</td>
								<td colspan="2">${orderModel.order.askDeliverDate }</td>
								<td colspan="2">${orderModel.order.askProducePeriod }</td>
								<td>${orderModel.order.askCodeNumber }</td>
							</tr>
							<tr>
								<td rowspan="2">面料</td>
								<td colspan="2">面料名称</td>
								<td colspan="2">面料克重</td>
								<td colspan="2">操作</td>
								<input id="fabric_name" name="fabric_name" type="hidden" />
								<input id="fabric_amount" name="fabric_amount" type="hidden" />
							</tr>
							<tr>
								<td colspan="6" class="innertable"><table
										class="span12 table fabric_table">
										<tr class="addrow">
											<td><input class="span12" type="text" /></td>
											<td><input class="span12" type="text" /></td>
											<td><a>添加</a></td>
										</tr>
									</table></td>
							</tr>
							<tr>
								<td rowspan="2">辅料</td>
								<td colspan="2">辅料名称</td>
								<td colspan="2">辅料要求</td>
								<td colspan="2">操作</td>
								<input id="accessory_name" name="accessory_name" type="hidden" />
								<input id="accessory_query" name="accessory_query" type="hidden" />
							</tr>
							<tr>
								<td colspan="6" class="innertable"><table table
										class="span12 table accessory_table">
										<tr class="addrow">
											<td><input class="span12" type="text" /></td>
											<td><input class="span12" type="text" /></td>
											<td><a>添加</a></td>
										</tr>
									</table></td>
							</tr>
							<tr>
								<td rowspan="2">客户样衣</td>
								<td>提供样衣</td>
								<td colspan="2">邮寄时间</td>
								<td>快递名称</td>
								<td colspan="2">快递单号</td>
							</tr>
							<tr>
								<td>${orderModel.order.hasPostedSampleClothes }</td>
								<td colspan="2"><input class="span6" type="date"
									name="in_post_sample_clothes_time" /></td>
								<td><input class="span12" type="text"
									name="in_post_sample_clothes_type" /></td>
								<td colspan="2"><input class="span12" type="text"
									name="in_post_sample_clothes_number" /></td>
							</tr>
							<tr>
								<td rowspan="5">生产样衣</td>
								<td>制作样衣</td>
								<td colspan="2">邮寄时间</td>
								<td>快递名称</td>
								<td colspan="2">快递单号</td>
							</tr>
							<tr>
								<td>${orderModel.order.isNeedSampleClothes }</td>
								<td colspan="2"><input class="span6" type="date"
									name="sample_clothes_time" /></td>
								<td><input class="span12" type="text"
									name="sample_clothes_type" /></td>
								<td colspan="2"><input class="span12" type="text"
									name="sample_clothes_number" /></td>
							</tr>
							<tr>
								<td>邮寄人</td>
								<td>手机</td>
								<td colspan="4">邮寄地址</td>
							</tr>
							<tr>
								<td><input class="span12" type="text"
									name="sample_clothes_name" /></td>
								<td><input class="span12" type="text"
									name="sample_clothes_phone" /></td>
								<td colspan="4"><input class="span12" type="text"
									name="sample_clothes_address" /></td>
							</tr>
							<tr>
								<td>其他备注</td>
								<td colspan="5"><input class="span12" type="text"
									name="sample_clothes_remark" /></td>
							</tr>
							<tr>
								<td>样衣信息</td>
								<td>样衣图片</td>
								<td colspan="2">${orderModel.order.sampleClothesPicture }</td>
								<td>参考图片</td>
								<td colspan="2">${orderModel.order.referencePicture }</td>
							</tr>
							<tr>
								<td>意见</td>
								<td colspan="6"><input type="text" name="suggestion" /></td>
							</tr>
							<tr>
								<td>操作</td>	
								<td colspan="3">
									<form method="post" action="${ctx }/buy/doVerify.do">
										<input type="hidden" name="orderId" value="${orderModel.order.orderId }" />
										<input type="hidden" name="taskId" value="${orderModel.taskId }" />
										<input type="hidden" name="pinId" value="${orderModel.processInstanceId }" />
										<input type="hidden" name="buyVal" value="true" />
										<button class="btn btn-primary btn-rounded"><i class="icon-ok icon-white"></i> 同意</button>
									</form>
								</td>
								<td colspan="3">
									<form method="post" action="${ctx }/buy/doVerify.do">
										<input type="hidden" name="orderId" value="${orderModel.order.orderId }" />
										<input type="hidden" name="taskId" value="${orderModel.taskId }" />
										<input type="hidden" name="pinId" value="${orderModel.processInstanceId }" />
										<input type="hidden" name="buyVal" value="false" />
										<button class="btn btn-danger btn-rounded"><i class="icon-remove icon-white"></i> 拒绝</button>
									</form>
								</td>
							</tr>
						</table>

				</div>
				<!--widgetcontent-->
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
<script type="text/javascript" src="${ctx}/js/order/add_order.js"></script>
<script type="text/javascript" src="${ctx }/js/custom.js"></script>
<%@include file="/common/footer.jsp"%>
