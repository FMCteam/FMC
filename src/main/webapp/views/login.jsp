<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<title>智造链 - 登录</title>
<link rel="stylesheet" href="${ctx }/css/style.default.css"
	type="text/css" />
<link rel="stylesheet" href="${ctx }/css/style.shinyblue.css"
	type="text/css" />
<script type="text/javascript" src="${ctx }/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript"
	src="${ctx }/js/jquery-migrate-1.1.1.min.js"></script>
<script type="text/javascript" src="${ctx }/js/jquery-ui-1.10.3.min.js"></script>
<script type="text/javascript" src="${ctx }/js/modernizr.min.js"></script>
<script type="text/javascript" src="${ctx }/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctx }/js/jquery.cookie.js"></script>
<script type="text/javascript" src="${ctx }/js/custom.js"></script>
<script type="text/javascript">
	jQuery(document).ready(function() {
			jQuery('#login').submit(function() {
				//alert("as");
				var u = jQuery('#username').val().trim();
				var p = jQuery('#password').val().trim();
				if (u == '' || p == '') {
					jQuery('.login-alert').fadeIn();
					//alert("as");
					return false;
				}
			});
	});
	function checkExplorer(){
		if(window.navigator.userAgent.indexOf("Chrome")>=0){
			return true;
		}else{
			return false;
		}
	}
</script>
</head>

<body class="loginpage">
			<div class="headerinner">
				<ul class="headmenu">
					<li class="right">
						<div class="userloggedinfo">
							<div class="userinfo">
								<ul>
									<li><a href="${ctx }/account/registCustomer.do">客户注册</a></li>
									<li><a href="${ctx }/account/forgetPassword.do">找回密码</a></li>
								</ul>
							</div>
						</div>
					</li>
				</ul>
				<!--headmenu-->
			</div>
    <div class="loginfooter">
		<p>&copy; 2014. 江苏南通智造链有限公司.</p>
	</div>
	<div class="loginpanel">
		<div class="loginpanelinner">
			<div class="logo animate0 bounceIn"
				style="font-size:35px;color:white;">
				<!-- <img src="images/logo.png" alt="" /> -->
				智造链
			</div>
			<form id="login" action="doLogin.do" method="post" onsubmit="return checkExplorer();">
				<c:if test="${state != null}">
					<div class="inputwrapper login-alert" style="display: block;">
						<div class="alert alert-error">账户名或密码错误</div>
					</div>
				</c:if>
				<c:if test="${state == null}">
					<div class="inputwrapper login-alert" style="display: none;">
						<div class="alert alert-error">账户名或密码错误</div>
					</div>
				</c:if>


				<div class="inputwrapper animate1 bounceIn">
					<input type="text" name="user_name" id="username" placeholder="登录名" />
				</div>
				<div class="inputwrapper animate2 bounceIn">
					<input type="password" name="user_password" id="password"
						placeholder="密码" />
				</div>
				
				<div class="inputwrapper animate3 bounceIn">
					<button name="submit">登录</button>
				</div>
				<!--             <div class="inputwrapper animate4 bounceIn"> -->
				<!--                 <div class="pull-right">Not a member? <a href="registration.html">Sign Up</a></div> -->
				<!--                 <label><input type="checkbox" class="remember" name="signin" /> Keep me sign in</label> -->
				<!--             </div> -->

			</form>
		</div>
		<!--loginpanelinner-->
	</div>
	<!--loginpanel-->

	<div class="loginfooter">
		<p>&copy; 2014. 江苏南通智造链有限公司.</p>
	</div>
</body>
<style type="text/css">
div.inputwrapper * {
	border-radius: 2px;
	font-size: 14px;
	font-family: Tahoma, Verdana, STHeiTi, simsun, sans-serif;
}
</style>
</html>
