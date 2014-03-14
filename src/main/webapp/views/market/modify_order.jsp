<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>

<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
			<!--  如果是其它页面，这里是填充具体的内容。 -->
			<div class="widget">
				<h4 class="widgettitle">修改订单</h4>
				<div class="widgetcontent">

					<form onSubmit="return verify()" method="post"
						action="${ctx }/market/addMarketOrder.do">
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
								<td>${orderALL.getOrder().getCustomerId()}</td>
								<td>${orderALL.getOrder().getCustomerName()}</td>
								<td>${orderALL.getOrder().getCompanyName()}</td>
								<td>${orderALL.getOrder().getComapnyFax()}</td>
								<td>${orderALL.getOrder().getContactPhone1()}</td>
								<td>${orderALL.getOrder().getContactPhone2()}</td>
							</tr>
							<tr>
								<td>公司地址</td>
								<td colspan="5">${orderALL.getOrder().getCompanyAddress()}</td>
							</tr>

							<tr>
								<td rowspan="6">款式信息</td>
								<td><label>款式名称<span class="required">*</span></label></td>
								<td colspan="2">款式性别<span class="required">*</span></td>
								<td colspan="2">款式季节<span class="required">*</span></td>
								<td>订单来源<span class="required">*</span></td>
							</tr>
							<tr>
								<td><input type="text" class="span12" name="style_name" />${orderALL.getOrder().getStyleName() }</td>
								<td colspan="2"><input type="radio" name="style_sex"
									value="男"
									${orderALL.getOrder().getStyleSex() eq '男'?'checked="checked"':'' } />
									<span>男</span> <input type="radio" name="style_sex" value="女"
									${orderALL.getOrder().getStyleSex() eq '女'?'checked="checked"':'' } />
									<span>女</span> <input type="radio" name="style_sex" value="儿童"
									${orderALL.getOrder().getStyleSex() eq '儿童'?'checked="checked"':'' } />
									<span>儿童</span></td>
								<td colspan="2"><input type="radio" name="style_season"
									checked="checked" value="春夏"
									${orderALL.getOrder().getStyleSeason() eq '春夏'?'checked="checked"':'' } />
									<span>春夏</span> <input type="radio" name="style_season"
									value="秋冬"
									${orderALL.getOrder().getStypleSeason() eq '秋冬'?'checked="checked"':'' } />
									<span>秋冬</span></td>
								<td><input type="text" class="span12" name="order_source"
									value=${orderALL.getOrder().getOrderSource() } /></td>
							</tr>
							<tr>
								<td>面料类型</td>
								<td colspan="5"><input type="radio" name="fabric_type"
									checked="checked" value="梭织"
									${orderALL.getOrder().getFbricType() eq '梭织'?'checked="checked"':'' } />
									<span>梭织</span> <input type="radio" name="fabric_type"
									value="梭织"
									${orderALL.getOrder().getFbricType() eq '梭织'?'checked="checked"':'' } />
									<span>针织</span> <input type="radio" name="fabric_type"
									value="编织"
									${orderALL.getOrder().getFbricType() eq '编织'?'checked="checked"':'' } />
									<span>编织</span> <input type="radio" name="fabric_type"
									value="梭针混合"
									${orderALL.getOrder().getFbricType() eq '梭针混合'?'checked="checked"':'' } />
									<span>梭针混合</span> <input type="radio" name="fabric_type"
									value="阵编混合"
									${orderALL.getOrder().getFbricType() eq '阵编混合'?'checked="checked"':'' } />
									<span>阵编混合</span> <input type="radio" name="fabric_type"
									value="梭编混合"
									${orderALL.getOrder().getFbricType() eq '梭编混合'?'checked="checked"':'' } />
									<span>梭编混合</span></td>
							</tr>
							<tr>
								<td>特殊工艺</td>
								<td colspan="5"><input type="checkbox"
									name="special_process" value="水洗" checked="checked"
									${fn:contains(orderALL.getOrder().getSpeciaProcess(), "水洗")?'checked="checked"':'' } />
									<span>水洗</span> <input type="checkbox" name="special_process"
									value="激光"
									${fn:contains(orderALL.getOrder().getSpeciaProcess(), "激光")?'checked="checked"':'' } />
									<span>激光</span> <input type="checkbox" name="special_process"
									value="压皱"
									${fn:contains(orderALL.getOrder().getSpeciaProcess(), "压皱")?'checked="checked"':'' } />
									<span>压皱</span> <input type="checkbox" name="special_process"
									value="其他"
									${fn:contains(orderALL.getOrder().getSpeciaProcess(), "其他")?'checked="checked"':'' } />
									<input type="text" name="other_special_process" class="span2"
									placeholder="其他" /></td>
							</tr>
							<tr>
								<td>其他说明</td>
								<td colspan="5"><input type="checkbox"
									name="other_requirements" value="有主标"
									${fn:contains(orderALL.getOrder().getOtherRequirements(), "有主标")?'checked="checked"':'' } />
									有主标 <input type="checkbox" name="other_requirements"
									value="有吊牌"
									${fn:contains(orderALL.getOrder().getOtherRequirements(), "有吊牌")?'checked="checked"':'' } />
									有吊牌 <input type="checkbox" name="other_requirements"
									value="有水洗"
									${fn:contains(orderALL.getOrder().getOtherRequirements(), "有水洗")?'checked="checked"':'' } />
									有水洗 <input type="checkbox" name="other_requirements" value="其他"
									${fn:contains(orderALL.getOrder().getOtherRequirements(), "其他")?'checked="checked"':'' } />
									<input type="text" class="span2"
									name="other_other_requirements" placeholder="其他" /></td>
							</tr>
							<tr>
								<td>参考链接</td>
								<td colspan="5"><input class="span12" type="url" /></td>
							</tr>
							<tr>
								<td rowspan="2">加工信息</td>
								<td>加工件数<span class="required">*</span></td>
								<td colspan="2">最迟交货时间</td>
								<td colspan="2">完工时间（天）</td>
								<td>码数</td>
							</tr>
							<tr>
								<td><input class="span6" type="number" name="ask_amount"
									value=${orderALL.getOrder().getAskAmount() } /></td>
								<td colspan="2"><input class="span6" type="date"
									name="ask_deliver_date"
									value=${orderALL.getOrder().getAskAmount() } "/></td>
								<td colspan="2"><input class="span3" type="number"
									name="ask_produce_period"
									value=${orderALL.getOrder().getAskProducePeriod() } /></td>
								<td><select class="span6" name="ask_code_number">
										<option value="XS"
											${orderALL.getOrder().getAskCodeNUmber() eq 'XS'?'selected':''}>XS</option>
										<option value="S"
											${orderALL.getOrder().getAskCodeNUmber() eq 'S'?'selected':''}>S</option>
										<option value="L"
											${orderALL.getOrder().getAskCodeNUmber() eq 'L'?'selected':''}>L</option>
										<option value="XL"
											${orderALL.getOrder().getAskCodeNUmber() eq 'XL'?'selected':''}>XL</option>
										<option value="XXL"
											${orderALL.getOrder().getAskCodeNUmber() eq 'XXL'?'selected':''}>XXL</option>
								</select></td>
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
									</table>
									<c:forEach var="fabric" items="${orderALL.getOrder().getFabricList()}">
										<table table class="span12 table farbric_table">
											<tr class="addrow">
												<td><input class="span12" type="text" value=${fabric.getName() } /></td>
												<td><input class="span12" type="text" value=${fabric.getAmount() } /></td>
												<td><a>删除</a></td>
											</tr>
										</table>
									</c:forEach></td>
								
									
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
									</table> 
									<c:forEach var="accessory" items="${orderALL.getOrder().getAccessoryList()}">
										<table table class="span12 table accessory_table">
											<tr class="addrow">
												<td><input class="span12" type="text" value=${accessory.getName() } /></td>
												<td><input class="span12" type="text" value=${accessory.getQuery() } /></td>
												<td><a>删除</a></td>
											</tr>
										</table>
									</c:forEach>
									</td>
									
								
							</tr>
							<tr>
								<td rowspan="2">客户样衣</td>
								<td>提供样衣</td>
								<td colspan="2">邮寄时间</td>
								<td>快递名称</td>
								<td colspan="2">快递单号</td>
							</tr>
							<tr>
								<td><input type="radio" name="has_posted_sample_clothes"
									checked="checked" value="1" ${orderALL.getOrder().getIsNeedSampleClothes() eq 1?'checked="checked"':'' }/> 是 <input type="radio"
									name="has_posted_sample_clothes" value="0" ${orderALL.getOrder().getIsNeedSampleClothes() eq 0?'checked="checked"':'' }/> 否</td>
								<td colspan="2"><input class="span6" type="date"
									name="in_post_sample_clothes_time" value=${orderALL.getLogistics().getInPostSampleClothesType() }/></td>
								<td><input class="span12" type="text"
									name="in_post_sample_clothes_type" value=${orderALL.getLogistics().getInPostSampleClothesType() }/></td>
								<td colspan="2"><input class="span12" type="text"
									name="in_post_sample_clothes_number" value=${orderALL.getLogistics().getInPostSampleClothesNumber() }/></td>
							</tr>
							<tr>
								<td rowspan="5">生产样衣</td>
								<td>制作样衣</td>
								<td colspan="2">邮寄时间</td>
								<td>快递名称</td>
								<td colspan="2">快递单号</td>
							</tr>
							<tr>
								<td><input type="radio" name="is_need_sample_clothes"
									checked="checked" value="1" ${orderALL.getOrder().getIsNeedSampleClothes() eq 1?'checked="checked"':'' }/> 是 <input type="radio"
									name="is_need_sample_clothes" value="0" ${orderALL.getOrder().getIsNeedSampleCloths() eq 0?'checked'="checked"':'' }/> 否</td>
								<td colspan="2"><input class="span6" type="date"
									name="sample_clothes_time" value=${orderALL.getLogistic().getSampleClothesTime() }/></td>
								<td><input class="span12" type="text"
									name="sample_clothes_type" value=${orderALL.getLogistic().getSampleClothesType() }/></td>
								<td colspan="2"><input class="span12" type="text"
									name="sample_clothes_number" value=${orderALL.getLogistic().getSampleClothesNumber() } /></td>
							</tr>
							<tr>
								<td>邮寄人</td>
								<td>手机</td>
								<td colspan="4">邮寄地址</td>
							</tr>
							<tr>
								<td><input class="span12" type="text"
									name="sample_clothes_name" value=${orderALL.getLogistics().getSampleClothesName() }/></td>
								<td><input class="span12" type="text"
									name="sample_clothes_phone" value=${orderALL.getLogistics().getSampleClothesPhone() }/></td>
								<td colspan="4"><input class="span12" type="text"
									name="sample_clothes_address" value=${orderALL.getLogistics().getSampleClothesAddress() }/></td>
							</tr>
							<tr>
								<td>其他备注</td>
								<td colspan="5"><input class="span12" type="text"
									name="sample_clothes_remark" value=${orderALL.getLogistics().getSampleClothesRemark() } /></td>
							</tr>
							<tr>
								<td>样衣信息</td>
								<td>样衣图片</td>
								<td colspan="2"><input type="file"
									name="sample_clothes_picture" /></td>
								<td>参考图片</td>
								<td colspan="2"><input type="file" name="reference_picture" /></td>
							</tr>
							<tr>
								<td>操作</td>
								<td colspan="3"><input type="submit" /></td>
								<td colspan="3"><input type="reset" /></td>
							</tr>
						</table>
						<input type="hidden" name="customerId"
							value="" />
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
