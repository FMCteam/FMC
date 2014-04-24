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
				<h4 class="widgettitle">添加员工</h4>
				<div class="widgetcontent">
					<form class="stdform"
						action="${ctx}/account/addEmployeeSubmit.do" method="post">
						<p>
							<label>用户名</label> <span class="field"><input type="text"
								name="employee_id" class="input-large" placeholder="用户名" />&nbsp;<span
								style="color:#ff0000;">*</span>(必填)</span>
						</p>
						<p>
							<label>姓名</label> <span class="field"><input type="text"
								name="employee_name" class="input-large" placeholder="姓名" />&nbsp;<span
								style="color:#ff0000;">*</span>(必填)</span>
						</p>
						<p>
							<label>性别</label> <span class="formwrapper"> <input
								type="radio" name="radiofield" value="1" checked="checked" /> 男
								&nbsp; &nbsp; <input type="radio" name="radiofield" value="2" />
								女 &nbsp; &nbsp; <input type="radio" name="radiofield" value="3" />
								保密 &nbsp; &nbsp;<span style=" color:#ff0000;">*</span>(必填)
							</span>
						</p>
						<p>
							<label>部门</label> <span class="field"><input type="text"
								name="department" class="input-large" placeholder="部门" />&nbsp;<span
								style="color:#ff0000;">*</span>(必填)</span>
						</p>
						<p>
							<label>年龄</label> <span class="field"><input type="text"
								id="spinner" name="employee_age"
								class="input-small input-spinner" />&nbsp;<span
								style="color:#ff0000;">*</span>(必填)</span>
						</p>
						<p>
							<label>身份证号码</label> <span class="field"><input
								type="text" name="id_card" class="input-large"
								placeholder="身份证号码" />&nbsp;<span style="color:#ff0000;">*</span>(必填)</span>
						</p>
						<p>
							<label>角色</label> <span class="field"> <select name="role"
								class="uniformselect">
									<option value="marketManager">市场主管</option>
									<option value="marketStaff">市场专员</option>
									<option value="produceManager">生产主管</option>
									<option value="purchaseManager">采购主管</option>
									<option value="qualityManager">质检主管</option>
									<option value="financeManager">财务主管</option>
									<option value="logisticsManager">物流主管</option>
									<option value="designManager">设计主管</option>
							</select> &nbsp;<span style="color:#ff0000;">*</span>(必填)
							</span>
						</p>
						<div class="par">
							<label>入职时间</label> <span class="field"><input
								id="datepicker" type="text" name="in_date" class="input-small" />&nbsp;<span
								style="color:#ff0000;">*</span>(必填)</span>
						</div>
						<p>
							<label>直接领导</label> <span class="field"><input type="text"
								name="direct_leader" class="input-large" placeholder="直接领导" /></span>
						</p>
						<p>
							<label>手机1</label> <span class="field"><input type="text"
								name="phone1" class="input-large" placeholder="手机1" /></span>
						</p>
						<p>
							<label>手机2</label> <span class="field"><input type="text"
								name="phone2" class="input-large" placeholder="手机2" /></span>
						</p>
						<div class="par">
							<label>离职时间</label> <span class="field"><input
								id="datepicker1" type="text" name="out_date" class="input-small" /></span>
						</div>
						<p>
							<label>当前状态</label> <span class="field"> <select
								name="state" class="uniformselect">
									<option value="正常">正常</option>
									<option value="离职">离职</option>
									<option value="停职">停职</option>
							</select>
							</span>
						</p>
						<p>
							<label>入职前公司</label> <span class="field"><input
								type="text" name="pre_company" class="input-large"
								placeholder="入职前公司" /></span>
						</p>
						<p>
							<label>入职前行业</label> <span class="field"><input
								type="text" name="pre_job" class="input-large"
								placeholder="入职前行业" /></span>
						</p>
						<p>
							<label>入职前职务</label> <span class="field"><input
								type="text" name="pre_role" class="input-large"
								placeholder="入职前职务" /></span>
						</p>
						<p>
							<label>学历</label> <span class="field"> <select
								name="edu_background" class="uniformselect">
									<option value="初中">初中</option>
									<option value="高中">高中</option>
									<option value="专科">专科</option>
									<option value="本科">本科</option>
									<option value="硕士">硕士</option>
									<option value="博士">博士</option>
							</select>
							</span>
						</p>
						<p>
							<label>毕业院校</label> <span class="field"><input type="text"
								name="school" class="input-large" placeholder="毕业院校" /></span>
						</p>
						<p>
							<label>专业</label> <span class="field"><input type="text"
								name="major" class="input-large" placeholder="专业" /></span>
						</p>
						<p>
							<label>籍贯</label> <span class="field"><input type="text"
								name="hometown" class="input-large" placeholder="籍贯" /></span>
						</p>
						<p>
							<label>南通住址</label> <span class="field"><input type="text"
								name="address" class="input-large" placeholder="南通住址" /></span>
						</p>
						<p class="stdformbutton">
							<button class="btn btn-primary">添加</button>
						</p>
					</form>
				</div>
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
<%@include file="/common/footer.jsp"%>
