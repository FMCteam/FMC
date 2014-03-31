<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>

<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
			<!--  如果是其它页面，这里是填充具体的内容。 -->


			<form>
				<ul>
					<li><span>Step 1</span><span>款式信息</span></li>
					<li><span>Step 2</span><span>面料辅料</span></li>
					<li><span>Step 3</span><span>样衣信息</span></li>
				</ul>


				<div>

					<div id="step1">
						<table class="">
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
									type="radio" name="fabric_type" value="梭织" /> <span>针织</span>
									<input type="radio" name="fabric_type" /value="编织"> <span>编织</span>
									<input type="radio" name="fabric_type" value="梭针混合" /> <span>梭针混合</span>
									<input type="radio" name="fabric_type" value="阵编混合" /> <span>阵编混合</span>
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
							<tr>
								<td rowspan="2">加工信息</td>
								<td>加工件数<span class="required">*</span></td>
								<td colspan="2">最迟交货时间</td>
								<td colspan="2">完工时间（天）</td>
								<td>码数</td>
							</tr>
							<tr>
								<td><input class="span6" type="number" name="ask_amount" /></td>
								<td colspan="2"><input class="span6" type="date"
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
						</table>
					</div>

				</div>


				<div></div>

			</form>

		</div>

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
