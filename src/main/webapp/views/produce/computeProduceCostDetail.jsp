<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>

<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
			<!--  如果是其它页面，这里是填充具体的内容。 -->
			<div class="widget">
				<h4 class="widgettitle">采购成本核算</h4>
				<div class="widgetcontent">
					<form id="costAccounting_form" method="post" action="${ctx }/produce/computeProduceCostSubmit.do">
						<table class="table table-striped table-bordered table-hover">
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
							<tr>
								<td rowspan="2">加工信息</td>
								<td>加工件数<span class="required">*</span></td>
								<td colspan="2">最迟交货时间</td>
								<td colspan="2">完工时间（天）</td>
								<td>码数</td>
							</tr>
							<tr>
								<td>${orderInfo.order.askAmount}</td>
								<td colspan="2">${fn:substring(orderInfo.order.askDeliverDate,0,10) }</td>
								<td colspan="2">${orderInfo.order.askProducePeriod }</td>
								<td>${orderInfo.order.askCodeNumber }</td>
							</tr>
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
								<td colspan="2">
								<img src="${ctx}/${orderInfo.order.sampleClothesPicture}" alt="没有图片"></img></td>
								<td>参考图片</td>
								<td colspan="2">
								<img src="${ctx}/${orderInfo.order.referencePicture}" alt="没有图片"></img></td>
							</tr>
							<tr>
								<td>意见</td>
								<td colspan="6"><input class="span12" type="text" name="suggestion" /></td>
							</tr>
							
                            <tr>
	                            <td rowspan="4">其他成本</td>
	                            <td>裁剪费用</td>
	                            <td>管理费用</td>
	                            <td>缝制费用</td>
	                            <td>整烫费用</td>
                            </tr>
							
							<tr>
								<td><input class="span12" name="cut_cost" id="cut_cost" type="text"></td>
								<td><input class="span12" name="manage_cost" id="manage_cost" type="text"></td>
								<td><input class="span12" name="nail_cost" id="nail_cost" type="text"></td>
								<td><input class="span12" name="ironing_cost" id="ironing_cost" type="text"></td>		
				 				<input type="hidden" name="orderId" value="${orderInfo.order.orderId }" />
	              				<input type="hidden" name="taskId" value="${orderInfo.taskId }" />
							</tr>
							
                            <tr>
                             	<td>锁订费用</td>
                             	<td>包装费用</td>
                             	<td>其他费用</td>
                             	<td>设计费用</td>
                            </tr>
					
							<tr>
								<td><input class="span12" name="swing_cost" id="swing_cost" type="text"></td>
								<td><input class="span12" name="package_cost" id="package_cost" type="text"></td>
								<td><input class="span12" name="other_cost" id="other_cost" type="text"></td>
								<td><input class="span12" name="design_cost" id="design_cost" type="text"></td>
							</tr>
							<tr>
								<td colspan="3"><input type="submit" style="float: right;"/></td>
                    		</tr>
						</table>
					</form>
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
