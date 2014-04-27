<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>

<!--  <div class="pageheader">
           
            <div class="pageicon"><span class="iconfa-laptop"></span></div>
            <div class="pagetitle">
                <h5>服装快速响应供应链</h5>
                <h1>智造链</h1>
            </div>
        </div>pageheader -->

<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">

			<div class="widget">
				<h4 class="widgettitle">添加客户</h4>
				<div class="widgetcontent">
					<form id="customerForm" class="stdform"
						action="${ctx}/account/addCustomerSubmit.do" method="post">

						<table>
							<tr>
								<td><label for="user_name">客户登录名：</label> <span
									class="field"><input type="text" name="user_name"
										class="input-medium"></span></td>
								<td><label>客户密码：</label> <span class="field"><input
										type="text" name="user_password" value="123456"
										class="input-medium"></span></td>
							</tr>


							<tr>
								<td><label>客户姓名：</label> <span class="field"><input
										type="text" name="customer_name" class="input-medium"
										></span></td>
								<td><label>客户电话：</label> <span class="field"><input
										type="text" name="customer_phone" class="input-medium"
										></span></td>
							</tr>


							<tr>
								<td><label>客户邮箱：</label> <span class="field"><input
										type="text" name="email" class="input-medium"
										></span></td>
								<td><label>客户QQ：</label> <span class="field"><input
										type="text" name="qq" class="input-medium"></span>
								</td>
							</tr>


							<tr>
								<td><label>省份</label> <span class="field"><input
										type="text" name="province" class="input-medium"
										></span></td>
								<td><label>城市</label> <span class="field"><input
										type="text" name="city" class="input-medium"
										></span></td>
							</tr>


							<tr>
								<td><label>公司编号：</label> <span class="field"><input
										type="text" name="company_id" class="input-medium"
										></span></td>
								<td><label>公司名称</label> <span class="field"><input
										type="text" name="company_name" class="input-large"
										></span></td>

							</tr>
							<tr>
								<td><label>公司电话：</label> <span class="field"><input
										type="text" name="company_phone" class="input-medium"
										></span></td>
								<td><label>公司传真：</label> <span class="field"><input
										type="text" name="company_fax" class="input-medium"
										></span></td>
							</tr>


							<tr>
								<td><label>公司地址</label> <span class="field"><input
										type="text" name="company_address" class="input-xlarge"
										></span></td>
								<td><label>采购联系人</label> <span class="field"><input
										type="text" name="buy_contact" class="input-medium"
										></span></td>
							</tr>


							<tr>
								<td><label>采购联系人电话<b>1</b></label> <span class="field"><input
										type="text" name="contact_phone_1" class="input-medium"
										></span></td>
								<td><label>采购联系人电话<b>2</b></label> <span class="field"><input
										type="text" name="contact_phone_2" class="input-medium"
										></span></td>
							</tr>
							<tr>
								<td><label>老板姓名</label> <span class="field"><input
										type="text" name="boss_name" class="input-medium"
										></span></td>
								<td><label>老板电话</label> <span class="field"><input
										type="text" name="boss_phone" class="input-medium"
										></span></td>
							</tr>

							<tr>
								<td><label>网址</label> <span class="field"><input
										type="text" name="website_url" class="input-large"
										></span></td>
								<td><label>网站类型</label> <span class="field"> <select
										name="website_type" class="uniformselect">
											<option value="独立网站">独立网站</option>
											<option value="天猫店">天猫店</option>
											<option value="淘宝店">淘宝店</option>
											<option value="京东">京东</option>
											<option value="优衣库">优衣库</option>
											<option value="零号店">零号店</option>
									</select>
								</span></td>
							</tr>


							<tr>
								<td>
									<div class="par">
										<label>注册日期</label> <span class="field"><input
											id="datepicker" type="text" name="register_date"
											class="input-medium "></span>
									</div>
								</td>
								<td><label>注册业务员</label> <span class="field"><input
										type="text" disabled="disabled" name="register_employee_id"
										class="input-large" ></span>
								</td>
							</tr>
							<tr>
								<td>
									<p class="stdformbutton">
										<button class="btn btn-primary">添加</button>
									</p>
								</td>
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

<!-- 这里引入你需要的js文件 -->
<script type="text/javascript" src="${ctx }/js/custom.js"></script>
<script src="${ctx }/js/messages_cn.js" type="text/javascript"></script>
<script type="text/javascript">
	$().ready(function() {

		$("#customerForm").validate({

			rules : {
				user_name : "required",
				customer_name : "required",
				company_id : "required",
				company_name : "required",
				province : "required",

				register_date : {
					date : true,
					required : true
				},

			},

			messages : {
				user_name : "请输入用户登录名称",
				customer_name : "请输入用户真实姓名",
				company_id : "请输入公司编号",
				company_name : "请输入公司姓名",
				province : "请输入客户的省份",
				register_date : "请输入正确的注册日期"

			}

		});

	});
	
</script>
<%@include file="/common/footer.jsp"%>
