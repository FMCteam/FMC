<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>智造链 - 找回密码</title>
  </head>
  <body>
    <div class="header"></div>
    <div class="main">
      <div class="container container-custom">
        <div class="register-info">
          <h3><span class="icon"></span>输入登录名</h3>
          <div class="user-info">
            <form action="${ctx}/account/sendResetPassMail.do" method="post">
              <c:if test="${state != null}">
				<div class="inputwrapper login-alert" style="display: block;">
				  <div class="alert alert-error">登录名不存在</div>
				</div>
			  </c:if>

			  <div class="inputwrapper animate1 bounceIn">
				<input type="text" name="username" id="username" placeholder="登录名" />
			  </div>
				
			  <div class="inputwrapper animate3 bounceIn">
				<input type="button" value="发送重置密码邮件" onclick="check()" />
			  </div>
            </form>
          </div>
        </div>
      
        <div class="register-info">
          <div class="user-info">
                               已向您注册时绑定的邮箱发送重置密码邮件，请在30分钟内修改密码
          </div>
        </div>      
      </div>
    </div>
    <%@include file="/common/js_file.jsp"%>
    <script type="text/javascript" src="${ctx }/js/custom.js"></script>
    <script type="text/javascript" src="${ctx}/js/fmc/table.js"></script>
    <%@include file="/common/footer.jsp"%>
    
    <script>
    	var flag = false;
        function check(){
        	if (flag == true){
            	return false;
            }
            flag = true;
            document.forms[0].submit();
		}
    </script>
    
  </body> 
</html>