<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<script type="text/javascript" src="${ctx }/js/jquery-1.9.1.min.js"></script>
<title>登录</title>
<style>
body {
	padding-top: 80px;
}
</style>
</head>
<body>
<h3>智造链-登录</h3>
<c:if test="${state != null}" >
     <p style='color:red;'>账户名或密码错误</p>
</c:if>
<form action="doLogin.do" method="post">
<table>
<tr><td>用户名：</td><td><input type="text" name="user_name" id="username"/></td></tr>
<tr><td>密码：</td><td><input type="password" name="user_password" id="password"/></td></tr>
</table>
<div><button name="submit" type="submit">登录</button></div>
</form>
</body>
</html>