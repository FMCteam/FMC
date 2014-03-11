<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
		
		<%@include file="/common/header.jsp" %>
        
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
			            <h4 class="widgettitle">修改账户</h4>
			            <div class="widgetcontent">
				            <form class="stdform" action="${ctx }/account/doModify.do" method="post">
								<p>
		                            <label>用户名</label>
		                            <span class="field"><input type="text" name="employee_id" class="input-large" value="${account_to_modify.userName }" readonly/>
		                            &nbsp;&nbsp;<a id="modify_key" style="text-decoration:none; cursor:pointer;" >修改密码</a></span>
	                        	</p>
	                        	<div id="password_block" style="display:none">
		                        	<p>
			                            <label>新密码</label>
			                            <span class="field"><input type="password" name="password1" class="input-large" /></span>
		                        	</p>
		                        	<p>
			                            <label>确认新密码</label>
			                            <span class="field"><input type="password" name="password2" class="input-large" /></span>
		                        	</p>
	                        	</div>
					            <p>
		                            <label>姓名</label>
		                            <span class="field"><input type="text" name="employee_name" class="input-large" value="${account_to_modify.nickName }" /></span>
	                        	</p>
	                        	<p>
		                            <label>性别</label>
		                            <span class="formwrapper">
		                            	<input type="radio" name="radiofield" value="1" ${employee_to_modify.sex==1?'checked="checked"':'' } /> 男 &nbsp; &nbsp;
		                            	<input type="radio" name="radiofield" value="2" ${employee_to_modify.sex==2?'checked="checked"':'' } /> 女 &nbsp; &nbsp;
		                            	<input type="radio" name="radiofield" value="3" ${employee_to_modify.sex==3?'checked="checked"':'' } /> 未知 &nbsp; &nbsp;
		                            </span>
                        		</p>
                        		<p>
		                            <label>部门</label>
		                            <span class="field"><input type="text" name="department" class="input-large" value="${employee_to_modify.department }" /></span>
	                        	</p>
                        		<p>
		                            <label>年龄</label>
		                            <span class="field"><input type="text" id="spinner" name="employee_age" class="input-small input-spinner" value="${employee_to_modify.age }" /></span>
		                        </p>
		                        <p>
		                            <label>角色</label>
		                            <span class="field">
		                            <select name="role" class="uniformselect">
		                            	<option value="SHICHANGZHUGUAN" ${account_to_modify.userRole eq 'SHICHANGZHUGUAN'?'selected':'' }>市场主管</option>
		                            	<option value="SHICHANGZHUANYUAN" ${account_to_modify.userRole eq 'SHICHANGZHUANYUAN'?'selected':'' }>市场专员</option>
		                                <option value="SHENGCHANZHUGUAN" ${account_to_modify.userRole eq 'SHENGCHANZHUGUAN'?'selected':'' }>生产主管</option>
		                                <option value="CAIGOUZHUGUAN" ${account_to_modify.userRole eq 'CAIGOUZHUGUAN'?'selected':'' }>采购主管</option>
		                                <option value="ZHIJIANZHUGUAN" ${account_to_modify.userRole eq 'ZHIJIANZHUGUAN'?'selected':'' }>质检主管</option>
		                                <option value="CAIWUZHUGUAN" ${account_to_modify.userRole eq 'CAIWUZHUGUAN'?'selected':'' }>财务主管</option>
		                                <option value="WULIUZHUGUAN" ${account_to_modify.userRole eq 'WULIUZHUGUAN'?'selected':'' }>物流主管</option>
		                                <option value="SHEJIZHUGUAN" ${account_to_modify.userRole eq 'SHEJIZHUGUAN'?'selected':'' }>设计主管</option>
		                            </select>
		                            </span>
		                        </p>
		                        <div class="par">
		                            <label>入职时间</label>
		                            <span class="field"><input id="datepicker" type="text" name="in_date" class="input-small" value="${fn:substring(employee_to_modify.entryTime,0,10) }" /></span>
		                        </div>
		                        <p>
		                            <label>直接领导</label>
		                            <span class="field"><input type="text" name="direct_leader" class="input-large" value="${employee_to_modify.directLeader }" /></span>
	                        	</p>
	                        	<p>
		                            <label>手机1</label>
		                            <span class="field"><input type="text" name="phone1" class="input-large" value="${employee_to_modify.phone1 }" /></span>
	                        	</p>
	                        	<p>
		                            <label>手机2</label>
		                            <span class="field"><input type="text" name="phone2" class="input-large" value="${employee_to_modify.phone2 }" /></span>
	                        	</p>
	                        	<div class="par">
		                            <label>离职时间</label>
		                            <span class="field"><input id="datepicker1" type="text" name="out_date" class="input-small" value="${fn:substring(employee_to_modify.leaveTime,0,10) }" /></span>
		                        </div>
		                        <p>
		                            <label>当前状态</label>
		                            <span class="field">
		                            <select name="state" class="uniformselect">
		                            	<option value="正常" ${employee_to_modify.employeeState eq '正常'?'selected':'' } >正常</option>
		                                <option value="离职" ${employee_to_modify.employeeState eq '离职'?'selected':'' } >离职</option>
		                                <option value="停职" ${employee_to_modify.employeeState eq '停职'?'selected':'' } >停职</option> 
		                            </select>
		                            </span>
		                        </p>
		                        <p>
		                            <label>入职前公司</label>
		                            <span class="field"><input type="text" name="pre_company" class="input-large" value="${employee_to_modify.exCompany }" /></span>
	                        	</p>
	                        	<p>
		                            <label>入职前行业</label>
		                            <span class="field"><input type="text" name="pre_job" class="input-large" value="${employee_to_modify.exBusiness }" /></span>
	                        	</p>
	                        	<p>
		                            <label>入职前职务</label>
		                            <span class="field"><input type="text" name="pre_role" class="input-large" value="${employee_to_modify.exJob }" /></span>
	                        	</p>
	                        	<p>
		                            <label>学历</label>
		                            <span class="field">
		                            <select name="edu_background" class="uniformselect">
		                            	<option value="初中" ${employee_to_modify.eduBackground eq '初中'?'selected':'' } >初中</option>
		                                <option value="高中" ${employee_to_modify.eduBackground eq '高中'?'selected':'' } >高中</option>
		                                <option value="专科" ${employee_to_modify.eduBackground eq '专科'?'selected':'' } >专科</option> 
		                                <option value="本科" ${employee_to_modify.eduBackground eq '本科'?'selected':'' } >本科</option> 
		                                <option value="硕士" ${employee_to_modify.eduBackground eq '硕士'?'selected':'' } >硕士</option> 
		                                <option value="博士" ${employee_to_modify.eduBackground eq '博士'?'selected':'' } >博士</option>  
		                            </select>
		                            </span>
		                        </p>
		                        <p>
		                            <label>毕业院校</label>
		                            <span class="field"><input type="text" name="school" class="input-large" value="${employee_to_modify.eduSchool }" /></span>
	                        	</p>
	                        	<p>
		                            <label>专业</label>
		                            <span class="field"><input type="text" name="major" class="input-large" value="${employee_to_modify.eduField }" /></span>
	                        	</p>
	                        	<p>
		                            <label>籍贯</label>
		                            <span class="field"><input type="text" name="hometown" class="input-large" value="${employee_to_modify.hometown }" /></span>
	                        	</p>
	                        	<p>
		                            <label>南通住址</label>
		                            <span class="field"><input type="text" name="address" class="input-large" value="${employee_to_modify.address }" /></span>
	                        	</p>
	                        	<p>
		                            <label>身份证号码</label>
		                            <span class="field"><input type="text" name="id_card" class="input-large" value="${employee_to_modify.chinaId }" /></span>
	                        	</p>

	                        	<p class="stdformbutton">
                                    <button class="btn btn-primary">修改</button>
                            	</p>

				            </form>
			            </div>
		            </div>

             </div><!--row-fluid-->
                
                <div class="footer">
                    <div class="footer-left">
                        <span>&copy; 2014. 江苏南通智造链有限公司.</span>
                    </div>
                  
                </div><!--footer-->
                
            </div><!--maincontentinner-->
        </div><!--maincontent-->
        
        <%@include file="/common/js_file.jsp" %>
        <%@include file="/common/js_form_file.jsp" %>
        
        <!-- 这里引入你需要的js文件 -->
        <script type="text/javascript" src="${ctx }/js/custom.js"></script>
        
        
        <%@include file="/common/footer.jsp" %>
    