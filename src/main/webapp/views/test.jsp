<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp" %>

<a href="${ctx}/common/getPic.do?type=sample&orderId=${orderInfo.order.orderId}" class="fancybox" title="查看大图">
					<img src="${ctx}/common/getPic.do?type=sample&orderId=${orderInfo.order.orderId}"
						style="max-height: 300px;" alt="样衣图片"></img></a>

<link rel="stylesheet" type="text/css" href="${ctx}/css/zoom_img/jquery.fancybox.css" media="screen" />
<script type="text/javascript" src="${ctx}/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${ctx}/js/zoom_img/jquery.fancybox.js"></script>


<script type="text/javascript">
$(document).ready(function() {
	$('.fancybox').fancybox();
});
</script>


<%@include file="/common/footer.jsp" %>
    