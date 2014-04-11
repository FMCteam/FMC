<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>

<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
			<!--  如果是其它页面，这里是填充具体的内容。 -->
			<div class="widget">
				<h4 class="widgettitle">确认样衣制作金</h4>
				<div class="widgetcontent">
					<form>
						<table class="table table-bordered">
							<tr>
								<td rowspan="2">样衣信息</td>
								<td>样衣件数</td>
								<td>样衣单价</td>
								<td>应收金额</td>
								<td>金额类型</td>
							</tr>
							<tr>
								<td>10</td>
								<td>10</td>
								<td>100</td>
								<td>样衣制作金</td>
							</tr>
							<tr>
								<td rowspan="3">汇款信息</td>
								<td>汇款人</td>
								<td>汇款卡号</td>
								<td>汇款银行</td>
								<td>汇款金额</td>
							</tr>
							<tr>
								<td><input type="text" name="money_name" /></td>
								<td><input type="text" name="money_number" /></td>
								<td><input type="text" name="money_bank" /></td>
								<td><input type="text" name="money_amount" /></td>
							</tr>
							<tr>
								<td>备注</td>
								<td colspan="3"><input type="text" name="money_remark"
									class="span12" /></td>
							</tr>
							<tr>
								<td>操作</td>
								<td colspan="4"><input type="submit" value="确认收款" /> <input
									type="submit" value="款项未收到" /></td>
							</tr>
						</table>
					</form>
				</div>
				<!--widgetcontent-->
			</div>

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
<link rel="stylesheet" href="${ctx}/css/order/add_order.css">
<script type="text/javascript" src="${ctx}/js/order/add_order.js"></script>
<script type="text/javascript" src="${ctx }/js/custom.js"></script>
<%@include file="/common/footer.jsp"%>
