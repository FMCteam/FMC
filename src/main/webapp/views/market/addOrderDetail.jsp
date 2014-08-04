<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>
<style>
 
</style>


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
								<td class="span2 title" rowspan="2">业务信息</td>
								
								<!-- 
								<td class="span2 title">业务编号</td>
								 -->
								<td class="span2 title"  ><span style="color:red ;font-weight:bold">请选择是否为好多衣客户</span></td>
								<td class="span2 title" colspan="2">接单时间<span class="required">*</span></td>
								<td class="span2 title" >订单来源<span class="required">*</span></td>
								<td class="span2 title" >接单业务员</td>
								<td class="span2 title"style="color:red;">翻单</td>
							</tr>
							
							<tr>
							<!-- 
								<td>待生成</td>
							 -->
								<td ><input type="radio" name="is_haoduoyi" id="is_haoduoyi"
									 value="1" required="required" /> 是 <input type="radio"
									name="is_haoduoyi" value="0" checked="checked" /> 否</td>
								<td colspan="2"><input class="span8" type="date"required="required"
									required="required" /></td>
								<td><input type="text" class="span12" name="order_source"
									required="required" /></td>
								<td>${employee_name}</td>
								<td>否</td>
							</tr>

							<tr>
								<td class="title" rowspan="3">客户信息</td>
								<td class="title">客户编号</td>
								<td class="span2 title">姓名</td>
								<td class="span2 title">公司</td>
								<td class="title">传真</td>
								<td class="title">手机1</td>
								<td class="title">手机2</td>
							</tr>
							<tr>
								<td>${customer.customerId}</td>
								<td>${customer.customerName}</td>
								<td>${customer.companyName}</td>
								<td>${customer.companyFax}</td>
								<td>${customer.customerPhone}</td>
								<td>${customer.contactPhone2}</td>
							</tr>
							<tr>
								<td class="title">公司地址</td>
								<td colspan="5">${customer.companyAddress}</td>
							</tr>
							<tr>
								<td class="title" rowspan="6">款式信息</td>
								<td class="title">款式名称<span class="required">*</span></td>
								<td class="title">衣服类型<span class="required">*</span></td>
								<td class="title" colspan="2">款式性别<span class="required">*</span></td>
								<td class="title" colspan="2">款式季节<span class="required">*</span></td>
							</tr>
							<tr>
								<td><input type="text" class="span12" name="style_name"
									required="required" /></td>
								<td><input type="text" class="span12" name="clothes_type"
									required="required" /></td>	
								<td colspan="2"><input type="radio" name="style_sex"
									 value="男" required="required" /> <span>男</span> <input
									type="radio" name="style_sex" value="女" checked="checked" /> <span>女</span> <input
									type="radio" name="style_sex" value="儿童" /> <span>儿童</span></td>
								<td colspan="2"><input type="radio" name="style_season"
									checked="checked" value="春夏" required="required" /> <span>春夏</span> <input
									type="radio" name="style_season" value="秋冬" /> <span>秋冬</span></td>
							</tr>
							<tr>
								<td class="title">面料类型</td>
								<td colspan="5"><input type="radio" name="fabric_type"
									checked="checked" value="梭织" required="required" /> <span>梭织</span> <input
									type="radio" name="fabric_type" value="梭织" /> <span>针织</span>
									<input type="radio" name="fabric_type" value="编织"> <span>编织</span>
									<input type="radio" name="fabric_type" value="梭针混合" /> <span>梭针混合</span>
									<input type="radio" name="fabric_type" value="针编混合" /> <span>针编混合</span>
									<input type="radio" name="fabric_type" value="梭编混合" /> <span>梭编混合</span></td>
							</tr>
							<tr>
								<td class="title">特殊工艺</td>
								<td colspan="5"><input type="checkbox"
									name="special_process" value="水洗" checked="checked" /> <span>水洗</span>
									<input type="checkbox" name="special_process" value="激光" /> <span>激光</span>
									<input type="checkbox" name="special_process" value="压皱" /> <span>压皱</span>
									<input type="checkbox" name="special_process" value="其他" /> <input
									type="text" name="other_special_process" class="span2"
									placeholder="其他" /></td>
							</tr>
							<tr>
								<td class="title">其他说明</td>
								<td colspan="5"><input type="checkbox"
									name="other_requirements" value="有主标" checked="checked" /> 有主标
									<input type="checkbox" name="other_requirements" value="有吊牌" />
									有吊牌 <input type="checkbox" name="other_requirements"
									value="有水洗" /> 有水洗 <input type="checkbox"
									name="other_requirements" value="其他" /> <input type="text"
									class="span2" name="other_other_requirements" placeholder="其他" /></td>
							</tr>
							<tr>
								<td class="title"><a style="color: red;">*</a>参考链接</td>
								<td colspan="5"><input class="span12" type="url" name="reference_url" required="required" /></td>
							</tr>
						</table>
					</div>
					<div class="tab-pane" id="material">
						<table
							class="table table-striped table-bordered table-hover detail">
							<tr>
								<td class="span4 title">面料<input id="fabric_name" type="hidden"
									name="fabric_name" /> <input id="fabric_amount" type="hidden"
									name="fabric_amount" /></td>
								<td class="innertable span12"><table
										class="span12 table fabric_table span12">
										<tr>
											<td class="span5 title">面料名称</td>
											<td class="span5 title">面料克重</td>
											<td class="span3 title">操作</td>
										</tr>
										<tr class="addrow">
											<td><input class="span12" type="text" /></td>
											<td><input class="span12" type="text" /></td>
											<td><a>添加</a><span class="required">（点击添加之后数据生效）</span></td>
										</tr>
									</table></td>
							</tr>
							<tr>
								<td class="span2 title">辅料<input id="accessory_name"
									type="hidden" name="accessory_name" /> <input
									id="accessory_query" type="hidden" name="accessory_query" /></td>
								<td class="innertable span12"><table
										class="span12 table accessory_table">
										<tr>
											<td class="span5 title">辅料名称</td>
											<td class="span5 title">辅料要求</td>
											<td class="span3 title">操作</td>
										</tr>
										<tr class="addrow">
											<td><input class="span12" type="text" /></td>
											<td><input class="span12" type="text" /></td>
											<td><a>添加</a><span class="required">（点击添加之后数据生效）</span></td>
										</tr>
									</table></td>
							</tr>
						</table>
					</div>
					<div class="tab-pane" id="sample">
						<table
							class="table table-striped table-bordered table-hover detail">
							<tr>
								<td class="span2 title" rowspan="2">客户样衣</td>
								<td class="span2 title">提供样衣<span class="required">*</span></td>
								<td class="span3 title">邮寄时间</td>
								<td class="span2 title">快递名称</td>
								<td class="span3 title">快递单号</td>
							</tr>
							<tr>
								<td><input type="radio" name="has_posted_sample_clothes"
									checked="checked" value="1" required="required" /> 是 <input type="radio"
									name="has_posted_sample_clothes" value="0" /> 否</td>
								<td><input class="span12" type="date"
									name="in_post_sample_clothes_time" required="required" /></td>
								<td><select name="in_post_sample_clothes_type"
									style="margin: 0px">
										<option value="顺丰">顺丰</option>
										<option value="韵达">韵达</option>
										<option value="圆通">圆通</option>
										<option value="中通">中通</option>
										<option value="申通">申通</option>
										<option value="汇通">汇通</option>
										<option value="EMS">EMS</option>
								</select></td>
								<td><input class="span12" type="text"
									name="in_post_sample_clothes_number" required="required" /></td>
							</tr>
							<tr>
								<td class="title" rowspan="3">生产样衣</td>
								<td class="title" style="color:red;">制作样衣</td>
								<td class="title">邮寄人<span class="required">*</span></td>
								<td class="title">手机<span class="required">*</span></td>
								<td class="title">邮寄地址<span class="required">*</span></td>
							</tr>
							<tr>
								<td><input type="radio" name="is_need_sample_clothes"
									checked="checked" value="1" required="required" /> 是 <input type="radio"
									name="is_need_sample_clothes" value="0" /> 否</td>
								<td><input class="span12" type="text"
									name="sample_clothes_name" required="required" /></td>
								<td><input class="span12" type="text"
									name="sample_clothes_phone" required="required" /></td>
								<td><input class="span12" type="text"
									name="sample_clothes_address" required="required" /></td>
							</tr>
							<tr>
								<td class="title">其他备注<span class="required">*</span></td>
								<td colspan="3"><input class="span12" type="text"
									name="sample_clothes_remark" required="required" /></td>
							</tr>
							<tr>
								<td class="title">样衣信息</td>
								<td class="title">样衣图片<span class="required">*</span></td>
								<td><input type="file" value="选择文件"
									name="sample_clothes_picture" id="sample_clothes_picture" required="required" /></td>
								<td class="title">参考图片<span class="required">*</span></td>
								<td><input type="file" value="选择文件"
									name="reference_picture" id="reference_picture" required="required" /></td>
							</tr>
						</table>
					</div>
					<div class="tab-pane" id="produce">
						<table
							class="table table-striped table-bordered table-hover detail">
							<tr>
								<td class="title" rowspan="2">加工信息</td>
								<td class="span2 title" colspan="2">大货总数<span class="required">*</span></td>
								<td class="span2 title" colspan="2">最迟交货时间<span class="required">*</span></td>
								<td class="span2 title" colspan="2">完工时间（天）<span class="required">*</span></td>
							</tr>
							<tr>
								<td class="span2" colspan="2">
									<input class="span6" type="number" name="ask_amount" required="required" readonly="readonly"/>
									<input class="span6" type="hidden" name="sample_amount" />
								</td>
								<td class="span2" colspan="2"><input class="span8"
									id="datepicker" type="text" name="ask_deliver_date" required="required"/></td>
								<td class="span2" colspan="2"><input class="span4"
									type="number" min="1" name="ask_produce_period" required="required"/></td>
							</tr>

						</table>
						<table
							class="table table-striped table-bordered table-hover detail">
							<tr>
								<td class="span2 title">大货加工 <input id="produce_color"
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
											<td class="span1 title">颜色</td>
											<td class="span1 title">XS</td>
											<td class="span1 title">S</td>
											<td class="span1 title">M</td>
											<td class="span1 title">L</td>
											<td class="span1 title">XL</td>
											<td class="span1 title">XXL</td>
											<td class="span1 title">操作</td>
										</tr>
										<tr class="addrow">
											<td><input type="text" class="span12" /></td>
											<td><input type="text" class="span12" value="0" /></td>
											<td><input type="text" class="span12" value="0" /></td>
											<td><input type="text" class="span12" value="0" /></td>
											<td><input type="text" class="span12" value="0" /></td>
											<td><input type="text" class="span12" value="0" /></td>
											<td><input type="text" class="span12" value="0" /></td>
											<td><a>添加</a><span class="required">（点击添加之后数据生效）</span></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="span2 title">样衣加工 <input id="sample_produce_color"
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
											<td class="span1 title">颜色</td>
											<td class="span1 title">XS</td>
											<td class="span1 title">S</td>
											<td class="span1 title">M</td>
											<td class="span1 title">L</td>
											<td class="span1 title">XL</td>
											<td class="span1 title">XXL</td>
											<td class="span1 title">操作</td>
										</tr>
										<tr class="addrow">
											<td><input type="text" class="span12" /></td>
											<td><input type="text" class="span12" value="0" /></td>
											<td><input type="text" class="span12" value="0" /></td>
											<td><input type="text" class="span12" value="0" /></td>
											<td><input type="text" class="span12" value="0" /></td>
											<td><input type="text" class="span12" value="0" /></td>
											<td><input type="text" class="span12" value="0" /></td>
											<td><a>添加</a><span class="required">（点击添加之后数据生效）</span></td>
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
												<td class="title" colspan="11">版型数据 <input id="version_size"
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
												<td class="span1 title">尺寸表/部位</td>
												<td class="span1 title">后中长</td>
												<td class="span1 title">胸围</td>
												<td class="span1 title">腰围</td>
												<td class="span1 title">肩宽</td>
												<td class="span1 title">臀围</td>
												<td class="span1 title">下摆</td>
												<td class="span1 title">裤长</td>
												<td class="span1 title">裙长</td>
												<td class="span1 title">袖长</td>
												<td class="span1 title">操作</td>
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
												<td><a>添加</a><span class="required">（点击添加之后数据生效）</span></td>
											</tr>
										</tbody>
									</table>
								</td>
							</tr>
						</table>
						<table
							class="table table-striped table-bordered table-hover detail">
							<tr>
								<td class="span1 title" rowspan="4">版型数据</td>
								<td class="span1 title">面料</td>
								<td class="span3"><textarea class="span12" name="cadFabric"
										style="resize:none" rows="2"></textarea></td>
								<td class="span1 title">包装</td>
								<td class="span3"><textarea class="span12" name="cadBox"
										style="resize:none" rows="2"></textarea></td>
							</tr>
							<tr>
								<td class="span1 title">版型</td>
								<td class="span3"><textarea class="span12"
										name="cadVersionData" style="resize:none" rows="2"></textarea></td>
								<td class="span1 title">装箱</td>
								<td class="span3"><textarea class="span12"
										name="cadPackage" style="resize:none" rows="2"></textarea></td>

							</tr>
							<tr>
								<td class="span1 title">工艺</td>
								<td class="span3"><textarea class="span12" name="cadTech"
										style="resize:none" rows="2"></textarea></td>
								<td class="span1 title">其他</td>
								<td class="span3"><textarea class="span12" name="cadOther"
										style="resize:none" rows="2"></textarea></td>
							</tr>
						</table>
					</div>
				</div>
				<input type="hidden" name="customerId"
					value="${customer.customerId}" />

				<!--row-fluid-->
			</div>
			<button class="btn btn-primary" onclick="history.back();">返回</button>
			<div class="action" style="float:right">
				<button class="btn btn-primary btn-rounded norepeat"  >
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
