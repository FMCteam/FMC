<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>智造链 - 找回密码</title>
    
    <script type="text/javascript">
	jQuery(document).ready(function() {
		jQuery('#reset_pass_form').submit(function() {
			//alert("as");
			var p1 = jQuery('#new_pass').val().trim();
			var p2 = jQuery('#cfm_new_pass').val().trim();
			if (p1 == '' || p2 == '') {
				jQuery('.reset-alert-1').fadeIn();
				alert("两次输入的密码均不能为空");
				return false;
			} else {
				if (p1 != p2) {
					jQuery('.reset-alert-2').fadeIn();
				    alert("两次输入的密码不一样");
				return false;
				}
			}
		});
	});
  </script>
    
  </head>
  <body>
    <div class="header"></div>
    <div class="main">
      <div class="container container-custom">
        <div class="register-info">
          <h3><span class="icon"></span>找回密码</h3>
          <div class="user-info">
            <form id="reset_pass_form" action="${ctx}/account/resetPassword.do" method="post" class="form">
			  <div class="inputwrapper reset-alert-1" style="display: block;">
				<div class="alert alert-error">新密码和确认密码均不能为空</div>
			  </div>
			  <div class="inputwrapper reset-alert-2" style="display: block;">
				<div class="alert alert-error">两次输入的密码不相同</div>
			  </div>

			  <div class="inputwrapper animate1 bounceIn">
				<input type="password" name="new_pass" id="new_pass" placeholder="新密码" />
			  </div>
			  <div class="inputwrapper animate1 bounceIn">
				<input type="password" name="cfm_new_pass" id="cfm_new_pass" placeholder="确认新密码" />
			  </div>
				
			  <div class="inputwrapper animate3 bounceIn">
				<button name="submit">修改密码</button>
			  </div>
            </form>
          </div>
        </div>
      </div>
    </div>
    <%@include file="/common/js_file.jsp"%>
    <script type="text/javascript" src="${ctx }/js/custom.js"></script>
    <script type="text/javascript" src="${ctx}/js/fmc/table.js"></script>
    <%@include file="/common/footer.jsp"%>
  </body> 
</html>