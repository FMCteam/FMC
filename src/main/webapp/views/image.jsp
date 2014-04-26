
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%@include file="/common/header.jsp"%>

<div class="pageheader">

	<div class="pageicon">
		<span class="iconfa-laptop"></span>
	</div>
	<div class="pagetitle">
		<h5>服装快速响应供应链</h5>
		<h1>智造链</h1>
	</div>
</div>
<!--pageheader-->

<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
			这里是公司介绍等。
			<!--  如果是其它页面，这里是填充具体的内容。 -->
			<c:forEach var="node" items="${fmc.nodes}">
			${node} <br/>
			</c:forEach>
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

<!-- 这里引入你需要的js文件 -->
<script type="text/javascript" src="${ctx }/js/custom.js"></script>


<%@include file="/common/footer.jsp"%>
