<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>

<div class="maincontent">
	<div class="maincontentinner">
	<form onSubmit="return verify()" method="post"
				action="${ctx }/market/addOrderSubmit.do" enctype="multipart/form-data">
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
					<table class="table table-striped table-bordered table-hover">
						<tr>
							<td>业务信息</td>
							<td>业务编号</td>
							<td><input class="span12" type="text" value="" readonly /></td>
							<td>接单时间</td>
							<td><input class="span12" type="date" /></td>
							<td>接单业务员</td>
							<td>${employee_name }</td>
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
							<td colspan="2">款式性别<span class="required">*</span></td>
							<td colspan="2">款式季节<span class="required">*</span></td>
							<td>订单来源<span class="required">*</span></td>
						</tr>
						<tr>
							<td><input type="text" class="span12" name="style_name" /></td>
							<td colspan="2"><input type="radio" name="style_sex"
								checked="checked" value="男" /> <span>男</span> <input
								type="radio" name="style_sex" value="女" /> <span>女</span> <input
								type="radio" name="style_sex" value="儿童" /> <span>儿童</span></td>
							<td colspan="2"><input type="radio" name="style_season"
								checked="checked" value="春夏" /> <span>春夏</span> <input
								type="radio" name="style_season" value="秋冬" /> <span>秋冬</span></td>
							<td><input type="text" class="span12" name="order_source" /></td>
						</tr>
						<tr>
							<td>面料类型</td>
							<td colspan="5"><input type="radio" name="fabric_type"
								checked="checked" value="梭织" /> <span>梭织</span> <input
								type="radio" name="fabric_type" value="梭织" /> <span>针织</span> <input
								type="radio" name="fabric_type" value="编织"> <span>编织</span>
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
								有吊牌 <input type="checkbox" name="other_requirements" value="有水洗" />
								有水洗 <input type="checkbox" name="other_requirements" value="其他" />
								<input type="text" class="span2" name="other_other_requirements"
								placeholder="其他" /></td>
						</tr>
						<tr>
							<td>参考链接</td>
							<td colspan="5"><input class="span12" type="url" /></td>
						</tr>
					</table>
				</div>
				<div class="tab-pane" id="material">
					<table class="table table-striped table-bordered table-hover">
						<tr>
							<td>面料<input id="fabric_name" type="hidden"
								name="fabric_name" /> <input id="fabric_amount" type="hidden"
								name="fabric_amount" /></td>
							<td colspan="6" class="innertable"><table
									class="span12 table fabric_table">
									<tr>
										<td>面料名称</td>
										<td>面料克重</td>
										<td>操作</td>
									</tr>
									<tr class="addrow">
										<td><input class="span12" type="text" /></td>
										<td><input class="span12" type="text" /></td>
										<td><a>添加</a></td>
									</tr>
								</table></td>
						</tr>
						<tr>
							<td>辅料<input id="accessory_name" type="hidden"
								name="accessory_name" /> <input id="accessory_query"
								type="hidden" name="accessory_query" /></td>
							<td colspan="6" class="innertable"><table
									class="span12 table accessory_table">
									<tr>
										<td>辅料名称</td>
										<td>辅料要求</td>
										<td>操作</td>
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
					<table class="table table-striped table-bordered table-hover">
						<tr>
							<td rowspan="2">客户样衣</td>
							<td>提供样衣</td>
							<td colspan="2">邮寄时间</td>
							<td>快递名称</td>
							<td colspan="2">快递单号</td>
						</tr>
						<tr>
							<td><input type="radio" name="has_posted_sample_clothes"
								checked="checked" value="1" /> 是 <input type="radio"
								name="has_posted_sample_clothes" value="0" /> 否</td>
							<td colspan="2"><input class="span12" type="date"
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
							<td><input type="radio" name="is_need_sample_clothes"
								checked="checked" value="1" /> 是 <input type="radio"
								name="is_need_sample_clothes" value="0" /> 否</td>
							<td colspan="2"><input class="span12" type="date"
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
							<td colspan="2"><input type="file" value="选择文件"
								name="sample_clothes_picture" /></td>
							<td>参考图片</td>
							<td colspan="2"><input type="file" value="选择文件"
								name="reference_picture" /></td>
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
							<td><input class="span6" type="number" name="ask_amount" /></td>
							<td colspan="2"><input class="span12" type="date"
								name="ask_deliver_date" /></td>
							<td colspan="2"><input class="span3" type="number"
								name="ask_produce_period" /></td>
							<td><select class="span6" name="ask_code_number">
									<option value="XS">XS</option>
									<option value="S">S</option>
									<option value="L">L</option>
									<option value="XL">XL</option>
									<option value="XXL">XXL</option>
							</select></td>
						</tr>
						<tr>
							<td colspan="8">大货加工具体要求 <input id="produce_color"
								type="hidden" name="produce_color" /> <input id="produce_xs"
								type="hidden" name="produce_xs" /> <input id="produce_s"
								type="hidden" name="produce_s" /> <input id="produce_m"
								type="hidden" name="produce_m" /> <input id="produce_l"
								type="hidden" name="produce_l" /> <input id="produce_xl"
								type="hidden" name="produce_xl" /> <input id="produce_xxl"
								type="hidden" name="produce_xxl" /></td>
						</tr>
						<tr>
							<td colspan="8" class="innertable">
								<table class="span12 table produce_table">
									<tr>
										<td>颜色</td>
										<td>XS</td>
										<td>S</td>
										<td>M</td>
										<td>L</td>
										<td>XL</td>
										<td>XXL</td>
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
										<td><a>添加</a></td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td colspan="8">样衣加工具体要求 <input id="sample_produce_color"
								type="hidden" name="sample_produce_color" /> <input id="sample_produce_xs"
								type="hidden" name="sample_produce_xs" /> <input id="sample_produce_s"
								type="hidden" name="sample_produce_s" /> <input id="sample_produce_m"
								type="hidden" name="sample_produce_m" /> <input id="sample_produce_l"
								type="hidden" name="sample_produce_l" /> <input id="sample_produce_xl"
								type="hidden" name="sample_produce_xl" /> <input id="sample_produce_xxl"
								type="hidden" name="sample_produce_xxl" /></td>
						</tr>
						<tr>
							<td colspan="8" class="innertable">
								<table class="span12 table sample_produce_table">
									<tr>
										<td>颜色</td>
										<td>XS</td>
										<td>S</td>
										<td>M</td>
										<td>L</td>
										<td>XL</td>
										<td>XXL</td>
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
										<td><a>添加</a></td>
									</tr>
								</table>
							</td>
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
				<input type="hidden" name="customerId"
					value="${customer.customerId}" />
				
		<!--row-fluid-->
</div>
<div class="action">
			<button class="btn btn-primary btn-rounded"><i
				class="icon-ok icon-white"></i>确定</button> 
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
<link rel="stylesheet" href="${ctx}/css/order/add_order.css">
<script type="text/javascript" src="${ctx}/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="${ctx}/js/order/add_order.js"></script>
<script type="text/javascript" src="${ctx }/js/custom.js"></script>
<script>
	$(function() {
		$('#tab a:first').tab('show');
		init();
	});
</script>
<%@include file="/common/footer.jsp"%>
