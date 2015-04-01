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
                    <h4 class="widgettitle">修改账户</h4>
                    <div class="widgetcontent">
                    <form class="stdform" action="${ctx }/account/modifyCustomerSubmit.do" method="post">
                        <p>
                            <label>客户登录名</label>
                            <span class="field"><input type="text" name="user_name"   class="input-medium" value="${account_to_modify.userName }" readonly>
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
	                        <label>客户姓名</label>
	                        <span class="field"><input type="text" name="customer_name"   class="input-medium" value="${account_to_modify.nickName }"></span>
	
	                    </p>
	                   
	                   <p>
	                        <label>客户ID</label>
	                        <span class="field"><input type="text" name="customer_id"   class="input-medium" value="${customer_to_modify.customerId }"></span>
	
	                    </p>
	                    <p>
	                        <label>客户电话</label>
	                        <span class="field"><input type="text" name="customer_phone"   class="input-medium" value="${customer_to_modify.customerPhone }"></span>
	
	                    </p>
	
	
	                    <p>
	                        <label>客户邮箱</label>
	                        <span class="field"><input type="text" name="email"   class="input-medium" value="${customer_to_modify.email }"></span>
	
	                    </p>
	
	                    <p>
	                        <label>客户QQ</label>
	                        <span class="field"><input type="text" name="qq"   class="input-medium" value="${customer_to_modify.qq }"></span>
	
	                    </p>
	
	
	                    <p>
	                        <label>省份</label>
	                        <span class="field"><input type="text" name="province"  class="input-medium" value="${customer_to_modify.province }"></span>
	                    </p>
	
	                    <p>
	                        <label>城市</label>
	                        <span class="field"><input type="text" name="city"   class="input-medium"  value="${customer_to_modify.city }"></span>
	                    </p>
	
	
	                    <p>
	                        <label>公司编号</label>
	                        <span class="field"><input type="text" name="company_id"  class="input-medium" value="${customer_to_modify.companyId }"></span>
	                    </p>
	
	                    <p>
	                        <label>公司名称</label>
	                        <span class="field"><input type="text" name="company_name"   class="input-large" value="${customer_to_modify.companyName }"></span>
	                    </p>
	
	                    <p>
	                        <label>公司电话</label>
	
	                        <span class="field"><input type="text" name="company_phone" class="input-medium" value="${customer_to_modify.companyPhone }"></span>
	                    </p>
	
	                    <p>
	                        <label>公司传真</label>
	
	                        <span class="field"><input type="text" name="company_fax"  class="input-medium" value="${customer_to_modify.companyFax }"></span>
	                    </p>
	
	
	                    <p>
	                        <label>公司地址</label>
	                        <span class="field"><input type="text" name="company_address"  class="input-xlarge" value="${customer_to_modify.companyAddress }"></span>
	                    </p>
	
	
	
	                    <p>
	                        <label>采购联系人</label>
	                        <span class="field"><input type="text" name="buy_contact"   class="input-medium" value="${customer_to_modify.buyContact }"></span>
	                    </p>
	
	
	                    <p>
	                        <label>采购联系人电话<b>1</b></label>
	                        <span class="field"><input type="text" name="contact_phone_1"  class="input-medium" value="${customer_to_modify.contactPhone1 }"></span>
	                    </p>
	
	
	                    <p>
	                        <label>采购联系人电话<b>2</b></label>
	                        <span class="field"><input type="text" name="contact_phone_2"   class="input-medium" value="${customer_to_modify.contactPhone2 }"></span>
	                    </p>
	
	                    <p>
	                        <label>老板姓名</label>
	                        <span class="field"><input type="text" name="boss_name"  class="input-medium" value="${customer_to_modify.bossName }"></span>
	                    </p>
	
	                    <p>
	                        <label>老板电话</label>
	                        <span class="field"><input type="text" name="boss_phone"    class="input-medium" value="${customer_to_modify.bossPhone }"></span>
	                    </p>
	
	                    <p>
	                        <label>网址</label>
	                        <span class="field"><input type="text" name="website_url"  class="input-large" value="${customer_to_modify.websiteUrl }"></span>
	                    </p>
	
	                    <p>
	                        <label>网站类型</label>
	                            <span class="field">
	                            <select name="website_type"   class="uniformselect">
	                                <option value="独立网站" ${customer_to_modify.websiteType eq '独立网站'?'selected':'' }>独立网站</option>
	                                <option value="天猫店" ${customer_to_modify.websiteType eq '天猫店'?'selected':'' }>天猫店</option>
	                                <option value="淘宝店" ${customer_to_modify.websiteType eq '淘宝店'?'selected':'' }>淘宝店</option>
	                                <option value="京东" ${customer_to_modify.websiteType eq '京东'?'selected':'' }>京东</option>
	                                <option value="优衣库" ${customer_to_modify.websiteType eq '优衣库'?'selected':'' }>优衣库</option>
	                                <option value="零号店" ${customer_to_modify.websiteType eq '零号店'?'selected':'' }>零号店</option>
	                            </select>
	                            </span>
	                    </p>
	
	                    <p class="stdformbutton">
	                        <button class="btn btn-primary">修改</button>
	                    </p>

                    </form>
                    </div><!--widgetcontent-->
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
    