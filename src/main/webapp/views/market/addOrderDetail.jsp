<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>

<div class="maincontent">
	<div class="maincontentinner">
		<form onSubmit="return verify()" method="post"
			action="${ctx }/market/addOrderSubmit.do"
			enctype="multipart/form-data">
			<div class="row-fluid" style="min-height:300px;">
				<!--  如果是其它页面，这里是填充具体的内容。 -->
				<ul class="nav nav-tabs detail" id="tab">
					<li class="task-name">客户下单</li>
					<li><a href="#cad" data-toggle="tab">版型信息</a></li>
					<li><a href="#produce" data-toggle="tab">加工信息</a></li>
					<li><a href="#sample" data-toggle="tab">样衣信息</a></li>
					<li><a href="#material" data-toggle="tab">面辅信息</a></li>
					<li class="active"><a href="#basic" data-toggle="tab">基本信息</a></li>
				</ul>

				<div class="tab-content">
					<div class="tab-pane active" id="basic">
						<table
							class="table table-striped table-bordered table-hover detail">
							<tr>
								<td class="span2" rowspan="2">业务信息</td>
								<td class="span2">业务编号</td>
								<td class="span2" colspan="2">接单时间<span class="required">*</span></td>
								<td class="span2">接单业务员</td>
								<td class="span2">订单来源<span class="required">*</span></td>
								<td class="span2">翻单</td>
							</tr>
							<tr>
								<td>待生成</td>
								<td colspan="2"><input class="span8" type="date"
									required="required" /></td>
								<td>${employee_name}</td>
								<td><input type="text" class="span12" name="order_source"
									required="required" /></td>
								<td>否</td>
							</tr>

							<tr>
								<td rowspan="3">客户信息</td>
								<td>客户编号</td>
								<td class="span2">姓名</td>
								<td class="span2">公司</td>
								<td>传真</td>
								<td>手机1</td>
								<td>手机2</td>
							</tr>
							<tr>
								<td>${customer.customerId}</td>
								<td>${customer.customerName}</td>
								<td>${customer.companyName}</td>
								<td>${customer.companyFax}</td>
								<td>${customer.contactPhone1}</td>
								<td>${customer.contactPhone2}</td>
							</tr>
							<tr>
								<td>公司地址</td>
								<td colspan="5">${customer.companyAddress}</td>
							</tr>
							<tr>
								<td rowspan="6">款式信息</td>
								<td><label>款式名称<span class="required">*</span></label></td>
								<td colspan="3">款式性别<span class="required">*</span></td>
								<td colspan="2">款式季节<span class="required">*</span></td>
							</tr>
							<tr>
								<td><input type="text" class="span12" name="style_name"
									required="required" /></td>
								<td colspan="3"><input type="radio" name="style_sex"
									checked="checked" value="男" /> <span>男</span> <input
									type="radio" name="style_sex" value="女" /> <span>女</span> <input
									type="radio" name="style_sex" value="儿童" /> <span>儿童</span></td>
								<td colspan="2"><input type="radio" name="style_season"
									checked="checked" value="春夏" /> <span>春夏</span> <input
									type="radio" name="style_season" value="秋冬" /> <span>秋冬</span></td>
							</tr>
							<tr>
								<td>面料类型</td>
								<td colspan="5"><input type="radio" name="fabric_type"
									checked="checked" value="梭织" /> <span>梭织</span> <input
									type="radio" name="fabric_type" value="梭织" /> <span>针织</span>
									<input type="radio" name="fabric_type" value="编织"> <span>编织</span>
									<input type="radio" name="fabric_type" value="梭针混合" /> <span>梭针混合</span>
									<input type="radio" name="fabric_type" value="阵编混合" /> <span>针编混合</span>
									<input type="radio" name="fabric_type" value="梭编混合" /> <span>梭编混合</span></td>
							</tr>
							<tr>
								<td>特殊工艺</td>
								<td colspan="5"><input type="checkbox"
									name="special_process" value="水洗" checked="checked" /> <span>水洗</span>
									<input type="checkbox" name="special_process" value="激光" /> <span>激光</span>
									<input type="checkbox" name="special_process" value="压皱" /> <span>压皱</span>
									<input type="checkbox" name="special_process" value="其他" /> <input
									type="text" name="other_special_process" class="span2"
									placeholder="其他" /></td>
							</tr>
							<tr>
								<td>其他说明</td>
								<td colspan="5"><input type="checkbox"
									name="other_requirements" value="有主标" checked="checked" /> 有主标
									<input type="checkbox" name="other_requirements" value="有吊牌" />
									有吊牌 <input type="checkbox" name="other_requirements"
									value="有水洗" /> 有水洗 <input type="checkbox"
									name="other_requirements" value="其他" /> <input type="text"
									class="span2" name="other_other_requirements" placeholder="其他" /></td>
							</tr>
							<tr>
								<td>参考链接</td>
								<td colspan="5"><input class="span12" type="url" /></td>
							</tr>
						</table>
					</div>
					<div class="tab-pane" id="material">
						<table
							class="table table-striped table-bordered table-hover detail">
							<tr>
								<td class="span2">面料<input id="fabric_name" type="hidden"
									name="fabric_name" /> <input id="fabric_amount" type="hidden"
									name="fabric_amount" /></td>
								<td class="innertable span12"><table
										class="span12 table fabric_table">
										<tr>
											<td class="span5">面料名称</td>
											<td class="span5">面料克重</td>
											<td class="span3">操作</td>
										</tr>
										<tr class="addrow">
											<td><input class="span12" type="text" /></td>
											<td><input class="span12" type="text" /></td>
											<td><a>添加</a></td>
										</tr>
									</table></td>
							</tr>
							<tr>
								<td class="span2">辅料<input id="accessory_name"
									type="hidden" name="accessory_name" /> <input
									id="accessory_query" type="hidden" name="accessory_query" /></td>
								<td class="innertable span12"><table
										class="span12 table accessory_table">
										<tr>
											<td class="span5">辅料名称</td>
											<td class="span5">辅料要求</td>
											<td class="span3">操作</td>
										</tr>
										<tr class="addrow">
											<td><input class="span12" type="text" /></td>
											<td><input class="span12" type="text" /></td>
											<td><a>添加</a></td>
										</tr>
									</table></td>
							</tr>
						</table>
					</div>
					<div class="tab-pane" id="sample">
						<table
							class="table table-striped table-bordered table-hover detail">
							<tr>
								<td class="span2" rowspan="2">客户样衣</td>
								<td class="span2">提供样衣</td>
								<td class="span3">邮寄时间</td>
								<td class="span2">快递名称</td>
								<td class="span3">快递单号</td>
							</tr>
							<tr>
								<td><input type="radio" name="has_posted_sample_clothes"
									checked="checked" value="1" /> 是 <input type="radio"
									name="has_posted_sample_clothes" value="0" /> 否</td>
								<td><input class="span12" type="date"
									name="in_post_sample_clothes_time" /></td>
								<td><input class="span12" type="text"
									name="in_post_sample_clothes_type" /></td>
								<td><input class="span12" type="text"
									name="in_post_sample_clothes_number" /></td>
							</tr>
							<tr>
								<td rowspan="5">生产样衣</td>
								<td>制作样衣</td>
								<td>邮寄时间</td>
								<td>快递名称</td>
								<td>快递单号</td>
							</tr>
							<tr>
								<td><input type="radio" name="is_need_sample_clothes"
									checked="checked" value="1" /> 是 <input type="radio"
									name="is_need_sample_clothes" value="0" /> 否</td>
								<td><input class="span12" type="date"
									name="sample_clothes_time" /></td>
								<td><input class="span12" type="text"
									name="sample_clothes_type" /></td>
								<td><input class="span12" type="text"
									name="sample_clothes_number" /></td>
							</tr>
							<tr>
								<td>邮寄人</td>
								<td>手机</td>
								<td colspan="2">邮寄地址</td>
							</tr>
							<tr>
								<td><input class="span12" type="text"
									name="sample_clothes_name" /></td>
								<td><input class="span12" type="text"
									name="sample_clothes_phone" /></td>
								<td colspan="2"><input class="span12" type="text"
									name="sample_clothes_address" /></td>
							</tr>
							<tr>
								<td>其他备注</td>
								<td colspan="3"><input class="span12" type="text"
									name="sample_clothes_remark" /></td>
							</tr>
							<tr>
								<td>样衣信息</td>
								<td>样衣图片</td>
								<td><input type="file" value="选择文件"
									name="sample_clothes_picture" /></td>
								<td>参考图片</td>
								<td><input type="file" value="选择文件"
									name="reference_picture" /></td>
							</tr>
						</table>
					</div>
					<div class="tab-pane" id="produce">
						<table
							class="table table-striped table-bordered table-hover detail">
							<tr>
								<td rowspan="2">加工信息</td>
								<td class="span2" colspan="2">样衣总数<span class="required">*</span></td>
								<td class="span2" colspan="2">大货总数<span class="required">*</span></td>
								<td class="span2" colspan="2">最迟交货时间</td>
								<td class="span2" colspan="2">完工时间（天）</td>
							</tr>
							<tr>
								<td class="span2" colspan="2"><input class="span6"
									type="number" name="sample_amount" /></td>
								<td class="span2" colspan="2"><input class="span6"
									type="number" name="ask_amount" /></td>
								<td class="span2" colspan="2"><input class="span8"
									type="date" name="ask_deliver_date" /></td>
								<td class="span2" colspan="2"><input class="span4"
									type="number" name="ask_produce_period" /></td>
							</tr>
							
							</table>
							<table class="table table-striped table-bordered table-hover detail">
							<tr>
								<td class="span2">大货加工 <input id="produce_color"
									type="hidden" name="produce_color" /> <input id="produce_xs"
									type="hidden" name="produce_xs" /> <input id="produce_s"
									type="hidden" name="produce_s" /> <input id="produce_m"
									type="hidden" name="produce_m" /> <input id="produce_l"
									type="hidden" name="produce_l" /> <input id="produce_xl"
									type="hidden" name="produce_xl" /> <input id="produce_xxl"
									type="hidden" name="produce_xxl" /></td>
								<td colspan="8" class="innertable">
									<table class="span12 table produce_table">
										<tr>
											<td class="span1">颜色</td>
											<td class="span1">XS</td>
											<td class="span1">S</td>
											<td class="span1">M</td>
											<td class="span1">L</td>
											<td class="span1">XL</td>
											<td class="span1">XXL</td>
											<td class="span1">操作</td>
										</tr>
										<tr class="addrow">
											<td><input type="text" class="span12" /></td>
											<td><input type="text" class="span12" /></td>
											<td><input type="text" class="span12" /></td>
											<td><input type="text" class="span12" /></td>
											<td><input type="text" class="span12" /></td>
											<td><input type="text" class="span12" /></td>
											<td><input type="text" class="span12" /></td>
											<td><a>添加</a></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="span2">样衣加工 <input id="sample_produce_color"
									type="hidden" name="sample_produce_color" /> <input
									id="sample_produce_xs" type="hidden" name="sample_produce_xs" />
									<input id="sample_produce_s" type="hidden"
									name="sample_produce_s" /> <input id="sample_produce_m"
									type="hidden" name="sample_produce_m" /> <input
									id="sample_produce_l" type="hidden" name="sample_produce_l" />
									<input id="sample_produce_xl" type="hidden"
									name="sample_produce_xl" /> <input id="sample_produce_xxl"
									type="hidden" name="sample_produce_xxl" /></td>
								<td colspan="8" class="innertable">
									<table class="span12 table sample_produce_table">
										<tr>
											<td class="span1">颜色</td>
											<td class="span1">XS</td>
											<td class="span1">S</td>
											<td class="span1">M</td>
											<td class="span1">L</td>
											<td class="span1">XL</td>
											<td class="span1">XXL</td>
											<td class="span1">操作</td>
										</tr>
										<tr class="addrow">
											<td><input type="text" class="span12" /></td>
											<td><input type="text" class="span12" /></td>
											<td><input type="text" class="span12" /></td>
											<td><input type="text" class="span12" /></td>
											<td><input type="text" class="span12" /></td>
											<td><input type="text" class="span12" /></td>
											<td><input type="text" class="span12" /></td>
											<td><a>添加</a></td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</div>
					<div class="tab-pane" id="cad">
						<table
							class="table table-striped table-bordered table-hover detail">
							<tr>
								<td colspan="7" class="innertable">
									<table
										class="span12 table table-striped table-hover detail version_table">
										<tbody>
											<tr>
												<td colspan="11">版型数据 <input id="version_size"
													type="hidden" name="version_size" /> <input
													id="version_centerBackLength" type="hidden"
													name="version_centerBackLength" /> <input
													id="version_bust" type="hidden" name="version_bust" /> <input
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
												<td class="span1">尺寸表/部位</td>
												<td class="span1">后中长</td>
												<td class="span1">胸围</td>
												<td class="span1">腰围</td>
												<td class="span1">肩宽</td>
												<td class="span1">臀围</td>
												<td class="span1">下摆</td>
												<td class="span1">裤长</td>
												<td class="span1">裙长</td>
												<td class="span1">袖长</td>
												<td class="span1">操作</td>
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
						</table>
						<table
							class="table table-striped table-bordered table-hover detail">
							<tr>
								<td class="span1" rowspan="4">版型数据</td>
								<td class="span1">面料</td>
								<td class="span3"><textarea class="span12"
										style="resize:none" rows="2"></textarea></td>
								<td class="span1">包装</td>
								<td class="span3"><textarea class="span12"
										style="resize:none" rows="2"></textarea></td>
							</tr>
							<tr>
								<td class="span1">版型</td>
								<td class="span3"><textarea class="span12"
										style="resize:none" rows="2"></textarea></td>
								<td class="span1">装箱</td>
								<td class="span3"><textarea class="span12"
										style="resize:none" rows="2"></textarea></td>

							</tr>
							<tr>
								<td class="span1">工艺</td>
								<td class="span3"><textarea class="span12"
										style="resize:none" rows="2"></textarea></td>
								<td class="span1">其他</td>
								<td class="span3"><textarea class="span12"
										style="resize:none" rows="2"></textarea></td>
							</tr>
						</table>
					</div>
				</div>
				<input type="hidden" name="customerId"
					value="${customer.customerId}" />

				<!--row-fluid-->
			</div>
			<div class="action">
				<button class="btn btn-primary btn-rounded">
					<i class="icon-ok icon-white"></i>提交询单
				</button>
			</div>
		</form>

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
<link rel="stylesheet" href="${ctx}/css/fmc/detail.css">
<link rel="stylesheet" href="${ctx}/css/order/add_order.css">
<script type="text/javascript" src="${ctx}/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="${ctx}/js/order/add_order.js"></script>
<script type="text/javascript" src="${ctx }/js/custom.js"></script>
<%@include file="/common/footer.jsp"%>
