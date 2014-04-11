<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>

<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
			<!--  如果是其它页面，这里是填充具体的内容。 -->

			<ul class="nav nav-tabs" id="tab">
				<li><a class="tab-pane active" href="#basic" data-toggle="tab">基本信息</a></li>
				<li><a href="#material" data-toggle="tab">面辅信息</a></li>
				<li><a href="#sample" data-toggle="tab">样衣信息</a></li>
				<li><a href="#produce" data-toggle="tab">加工信息</a></li>
				<li><a href="#cad" data-toggle="tab">版型信息</a></li>
			</ul>
			
			<div class="tab-content">
				<div class="tab-pane active" id="basic">
					<table class="table table-striped table-bordered ">
					
						<tr>
							<td>业务信息</td>
							<td>业务编号</td>
							<td>${orderInfo.order.orderId}</td>
							<td>接单时间</td>
							<td></td>
							<td>接单业务员</td>
							<td>${orderInfo.employee.employeeName}</td>
						</tr>
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
							<td>${orderInfo.order.customerId }</td>
							<td>${orderInfo.order.customerName }</td>
							<td>${orderInfo.order.customerCompany }</td>
							<td>${orderInfo.order.customerCompanyFax}</td>
							<td>${orderInfo.order.customerPhone1}</td>
							<td>${orderInfo.order.customerPhone2}</td>
						</tr>
						<tr>
							<td>公司地址</td>
							<td colspan="5">${orderInfo.order.customerCompanyAddress}</td>
						</tr>
						<tr>
							<td rowspan="6">款式信息</td>
							<td><label>款式名称</label></td>
							<td colspan="2">款式性别</td>
							<td colspan="2">款式季节</td>
							<td>订单来源</td>
						</tr>
						<tr>
							<td>${orderInfo.order.styleName }</td>
							<td colspan="2">${orderInfo.order.styleSex }</td>
							<td colspan="2">${orderInfo.order.styleSeason}</td>
							<td>${orderInfo.order.orderSource }</td>
						</tr>
						<tr>
							<td>面料类型</td>
							<td colspan="5">${orderInfo.order.fabricType}</td>
						</tr>
						<tr>
							<td>特殊工艺</td>
							<td colspan="5">${orderInfo.order.specialProcess}</td>
						</tr>
						<tr>
							<td>其他说明</td>
							<td colspan="5">${orderInfo.order.otherRequirements}</td>
						</tr>
						<tr>
							<td>参考链接</td>
							<td colspan="5"></td>
						</tr>
					</table>
				</div>
				<div class="tab-pane" id="material">
					<table class="table table-striped table-bordered table-hover">
						<tr>
							<td rowspan="${fn:length(orderInfo.fabrics)+1}">面料</td>
							<td colspan="3">面料名称</td>
							<td colspan="3">面料克重</td>
						</tr>
						<c:forEach var="fabric" items="${orderInfo.fabrics}">
							<tr>
								<td colspan="3">${fabric.fabricName}</td>
								<td colspan="3">${fabric.fabricAmount}</td>
							</tr>
						</c:forEach>
						<tr>
							<td rowspan="${fn:length(orderInfo.accessorys)+1}">辅料</td>
							<td colspan="3">辅料名称</td>
							<td colspan="3">辅料要求</td>
						</tr>


						<c:forEach var="accessory" items="${orderInfo.accessorys}">
							<tr>
								<td colspan="3">${accessory.accessoryName}</td>
								<td colspan="3">${accessory.accessoryQuery}</td>
							</tr>
						</c:forEach>
					</table>
				</div>
				<div class="tab-pane" id="sample">
					<table class="table table-striped table-bordered table-hover">
						<tr>
							<td rowspan="2">客户样衣</td>
							<td>提供样衣</td>
							<td colspan="2">邮寄时间</td>
							<td>快递名称</td>
							<td colspan="2">快递单号</td>
						</tr>
						<tr>
							<td>${orderInfo.order.hasPostedSampleClothes==0?'没有样衣':'' }
								${orderInfo.order.hasPostedSampleClothes==1?'未收到样衣':'' }
								${orderInfo.order.hasPostedSampleClothes==2?'收到样衣':'' }</td>
							<td colspan="2">${fn:substring(orderInfo.logistics.inPostSampleClothesTime,0,10) }</td>
							<td>${orderInfo.logistics.inPostSampleClothesType }</td>
							<td colspan="2">${orderInfo.logistics.inPostSampleClothesNumber }</td>
						</tr>
						<tr>
							<td rowspan="5">生产样衣</td>
							<td>制作样衣</td>
							<td colspan="2">邮寄时间</td>
							<td>快递名称</td>
							<td colspan="2">快递单号</td>
						</tr>
						<tr>
							<td>${orderInfo.order.isNeedSampleClothes==0?'否':'是' }</td>
							<td colspan="2">${fn:substring(orderInfo.logistics.sampleClothesTime,0,10) }</td>
							<td>${orderInfo.logistics.sampleClothesType }</td>
							<td colspan="2">${orderInfo.logistics.sampleClothesNumber }</td>
						</tr>
						<tr>
							<td>邮寄人</td>
							<td>手机</td>
							<td colspan="4">邮寄地址</td>
						</tr>
						<tr>
							<td>${orderInfo.logistics.sampleClothesName }</td>
							<td>${orderInfo.logistics.sampleClothesPhone }</td>
							<td colspan="4">${orderInfo.logistics.sampleClothesAddress }</td>
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
					</table>
				</div>
				<div class="tab-pane" id="produce">
					<table class="table table-striped table-bordered table-hover">

						<tr>
							<td rowspan="2">加工信息</td>
							<td>加工件数<span class="required">*</span></td>
							<td colspan="2">最迟交货时间</td>
							<td colspan="2">完工时间（天）</td>
							<td>码数</td>
						</tr>
						<tr>
							<td>${orderInfo.order.askAmount}</td>
							<td colspan="2">${fn:substring(orderInfo.order.askDeliverDate,0,10)}</td>
							<td colspan="2">${orderInfo.order.askProducePeriod }</td>
							<td>${orderInfo.order.askCodeNumber }</td>
						</tr>
					</table>
				</div>
				<div class="tab-pane" id="cad">
					<table class="table table-striped table-bordered table-hover">
						<tr>
							<td colspan="7" class="innertable">
								<table class="span12 table version_table">
									<tbody>
										<tr>
											<td colspan="11">版型数据信息 <input id="version_size"
												type="hidden" name="version_size" /> <input
												id="version_centerBackLength" type="hidden"
												name="version_centerBackLength" /> <input id="version_bust"
												type="hidden" name="version_bust" /> <input
												id="version_waistLine" type="hidden"
												name="version_waistLine" /> <input id="version_shoulder"
												type="hidden" name="version_shoulder" /> <input
												id="version_buttock" type="hidden" name="version_buttock" />
												<input id="version_hem" type="hidden" name="version_hem" />
												<input id="version_trousers" type="hidden"
												name="version_trousers" /> <input id="version_skirt"
												type="hidden" name="version_skirt" /> <input
												id="version_sleeves" type="hidden" name="version_sleeves" /></td>
										</tr>
										<tr>
											<td>尺寸表/部位</td>
											<td>后中长</td>
											<td>胸围</td>
											<td>腰围</td>
											<td>肩宽</td>
											<td>臀围</td>
											<td>下摆</td>
											<td>裤长</td>
											<td>裙长</td>
											<td>袖长</td>
											<td>操作</td>
										</tr>
										<tr class="addrow">
											<td><input type="text" class="span12" /></td>
											<td><input type="text" class="span12" /></td>
											<td><input type="text" class="span12" /></td>
											<td><input type="text" class="span12" /></td>
											<td><input type="text" class="span12" /></td>
											<td><input type="text" class="span12" /></td>
											<td><input type="text" class="span12" /></td>
											<td><input type="text" class="span12" /></td>
											<td><input type="text" class="span12" /></td>
											<td><input type="text" class="span12" /></td>
											<td><a>添加</a></td>
										</tr>
									</tbody>
								</table>
							</td>
						</tr>
						<tr>
							<td colspan="7">版型数据信息</td>
						</tr>
						<tr>
							<td>面料</td>
							<td colspan="3"><input type="text" class="span12" /></td>
							<td>包装</td>
							<td colspan="2"><input type="text" class="span12" /></td>
						</tr>
						<tr>
							<td>版型</td>
							<td colspan="3"><input type="text" class="span12" /></td>
							<td>装箱</td>
							<td colspan="2"><input type="text" class="span12" /></td>
						</tr>
						<tr>
							<td>工艺</td>
							<td colspan="6"><input type="text" class="span12" /></td>
						</tr>
					</table>
				</div>
			</div>
		</div>
		<div class="widget action">
			<a class="btn btn-primary btn-rounded"
				href="${ctx}/logistics/receiveSampleSubmit.do?result=1&taskId=${orderInfo.task.id}"><i
				class="icon-ok icon-white"></i>已收到样衣</a> <a
				class="btn btn-primary btn-rounded"
				href="${ctx}/logistics/receiveSampleSubmit.do?result=2&taskId=${orderInfo.task.id}"><i
				class="icon-remove icon-white"></i>未收到样衣</a>
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
<link rel="stylesheet" href="${ctx}/css/order/add_order.css">
<link rel="stylesheet" href="${ctx}/css/fmc/table.css">
<script type="text/javascript" src="${ctx}/js/fmc/table.js"></script>
<link rel="stylesheet" href="${ctx}/css/fmc/detail.css">
<script type="text/javascript" src="${ctx}/js/order/add_order.js"></script>
<script type="text/javascript" src="${ctx }/js/custom.js"></script>
<%@include file="/common/footer.jsp"%>
