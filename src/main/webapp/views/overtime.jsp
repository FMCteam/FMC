<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>超时信息</title>
<%@ include file="/common/taglibs.jsp"%>
<link rel="stylesheet" href="${ctx}/css/style.default.css"
	type="text/css" />
<script type="text/javascript" src="${ctx}/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript"
	src="${ctx}/js/jquery-migrate-1.1.1.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-ui-1.10.3.min.js"></script>
<script type="text/javascript" src="${ctx}/js/modernizr.min.js"></script>
<script type="text/javascript" src="${ctx}/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.cookie.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.slimscroll.js"></script>
<script type="text/javascript" src="${ctx}/js/custom.js"></script>
<script type="text/javascript">
	var time = 5;
	var limitTime = window.setInterval(limit, 1000);
	function limit() {
		time--;
		$("span.time").text(time);
		if (time<=0) {
			window.parent.location="${ctx}/login.do";
		}
	}
</script>
</head>

<body class="errorpage" style="margin:0px;background: none">
	<div id="mainwrapper" class="mainwrapper">
		<div class="errortitle"
			style="background-color: white;margin-top: 10%">
			<h4 class="animate0 fadeInUp">您的身份验证已经超时，请重新登陆!</h4>
			<span class="animate1 bounceIn">4</span> <span
				class="animate2 bounceIn">0</span> <span class="animate3 bounceIn">1</span>
			<div class="errorbtns animate4 fadeInUp">
				<a id="relogin" href="${ctx}/login.do" class="btn btn-large">重新登陆</a>
			</div>
			<div class="errorbtns animate4 fadeInUp">
				将在倒计时 <span class="time"
					style="font-size:12px;background: white;color: red;padding: 0px;margin: 0px;">5</span>
				秒后自动跳转到登陆页面
			</div>
		</div>
	</div>
</body>
</html>
