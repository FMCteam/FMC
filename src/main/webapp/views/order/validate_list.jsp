<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>


<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
			<!--  如果是其它页面，这里是填充具体的内容。 -->
			
			
			<form class="form-search text-center">
			<div class="input-append">
				<input type="text" class="span12 search-query" id="search" />
				<button type="button" class="btn" id="filter">搜索</button>
			</div>
			</form>
			
			<table class="table" id="list">
				<thead>
				<tr>
					<th>客户编号</th>
					<th>客户姓名</th>
					<th>款式名称</th>
					<th>完工时间</th>
					<th>最迟交货时间</th>
					<th>操作</th>
				</tr>
				</thead>
				

				<tr>
					<td>0000000001</td>
					<td>第三方</td>
					<td>杀马特</td>
					<td>20天</td>
					<td>2013-12-13</td>
					<td><a href="${ctx }/views/order/validate.jsp">详情</a></td>
				</tr>
				<tr>
					<td>075400001</td>
					<td>第f方</td>
					<td>杀马特</td>
					<td>88天</td>
					<td>2013-12-13</td>
					<td><a href="">详情</a></td>
				</tr>
				<tr>
					<td>054654600001</td>
					<td>第j方</td>
					<td>杀马特</td>
					<td>66天</td>
					<td>2013-12-13</td>
					<td><a href="">详情</a></td>
				</tr>
			</table>
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
<link rel="stylesheet" href="${ctx }/css/bootstrap.min.css">

<script type="text/javascript">
$("#search").keyup(function(){ 
	$("#list tbody tr").hide().filter(":contains('"+$(this).val()+"')").show(); 
	}).keyup(); 
</script>
<%@include file="/common/footer.jsp"%>
