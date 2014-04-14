
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%@include file="/common/header.jsp"%>


<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">

			<!--  如果是其它页面，这里是填充具体的内容。 -->
			<h4 class="widgettitle">入库订单详情</h4>
			<form onsubmit="return verify()"
				action="${ctx }/logistics/warehouseSubmit.do" method="post">
				<table id="dyntable" class="table table-bordered responsive">

					<tr>
						<td rowspan=${product_list.size()+1 }>生产情况</td>
						<td>生产编号</td>
						<td>订单要求</td>
						<td>实际生产</td>
						<td>自检合格</td>
						<td>颜色</td>
						<td>款式</td>

					</tr>
					<c:forEach var="product" items="${product_list}">
						<tr>


							<td>${product.id }</td>
							<td>${product.askAmount }</td>
							<td>${product.produceAmount }</td>

							<td>${product.qualifiedAmount }</td>
							<td>${product.color }</td>
							<td>${product.style }</td>


						</tr>
					</c:forEach>
					<tr>
						<td rowspan="3">包</td>
						<td>衣服款式</td>
						<td>衣服颜色</td>
						<td>衣服数量</td>
						<td>操作</td>
						<input id="clothes_amount" name="clothes_amount" type="hidden" />
						<input id="clothes_style_color" name="clothes_style_color"
							type="hidden" />
						<input id="clothes_style_name" name="clothes_style_name"
							type="hidden" />
					</tr>

					<tr>
						<td colspan="6" class="innertable"><table
								class="span12 table package_table">
								<tr class="addrow">
									<td><input class="span12" type="text" /></td>
									<td><input class="span12" type="text" /></td>
									<td><input class="span12" type="text" /></td>
									<td><a>添加</a></td>
								</tr>
							</table></td>
					</tr>
					<tr>
						<td>打包时间</td>
						<td><input id="datepicker" type="text" name="package_date"
							class="input-medium "></td>
					</tr>
					<tr>
						<td><a id="addpackage">添加包</a></td>
					</tr>

				</table>
				<input name="task_id" type="hidden" value=${task_id }> <input
					name="order_id" type="hidden" value=${order_id }> <input
					name="process_id" type="hidden" value=${process_id }>
				<p class="stdformbutton">
					<button class="btn btn-primary">添加</button>
				</p>
			</form>


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
<script type="text/javascript" src="${ctx}/js/order/add_package.js"></script>
<script type="text/javascript" src="${ctx}/js/form.js"></script>
<%@include file="/common/footer.jsp"%>

