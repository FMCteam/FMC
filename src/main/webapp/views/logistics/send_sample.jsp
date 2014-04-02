<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>

<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
			<!--  如果是其它页面，这里是填充具体的内容。 -->
			<div class="widget">
				<h4 class="widgettitle">物流发货</h4>
				<div class="widgetcontent">

					<form method="post"
						action="${ctx }/logistics/sendSampleOrder.do">
						<table class="table table-striped table-bordered table-hover">
							<tr>
								<td rowspan="2">订单信息</td>
								<td>单号</td>
								<td>生成时间</td>
								<td>该批次质检完成时间</td>
								<td>入库时间</td>
							</tr>
							<tr>
								<td>log.o.orderId</td>
								<td>log.o.orderTime</td>
								<td>1</td>
								<td>1</td>
							</tr>
							<tr>
								<td rowspan="3">客户信息</td>
								<td>公司名称</td>
								<td>log.o.customerCompany</td>
								<td>联系电话</td>
								<td>log.o.customerPhone1</td>
							</tr>
							<tr>
								<td>采购人姓名</td>
								<td>1</td>
								<td>固定电话</td>
								<td>1</td>
							</tr>
							<tr>
								<td>QQ</td>
								<td>1</td>
								<td>EMAIL</td>
								<td>1</td>
							</tr>
							<tr>
								<td rowspan="2">时间要求</td>
								<td colspan="4">最后发货时间</td>
							</tr>
							<tr>
								<td colspan="4">1</td>
							</tr>
							<tr>
								<td rowspan="2">物流要求</td>
								<td colspan="2">物流类型</td>
								<td colspan="2">物流公司</td>
							</tr>
							<tr>
								<td colspan="2">1</td>
								<td colspan="2">1</td>
							</tr>
							<tr>
								<td rowspan="2">专员信息</td>
								<td colspan="2">姓名</td>
								<td colspan="2">电话</td>
							</tr>
							<tr>
								<td colspan="2">1</td>
								<td colspan="2">1</td>
							</tr>
							<tr>
								<td colspan="5">具体数量表</td>
							</tr>
							<tr>
								<td colspan="5">
								<table class=" table table-striped table-bordered">
								<tbody>
								<tr>
									<td>颜色</td>
									<td>XS</td>
									<td>S</td>
									<td>M</td>
									<td>L</td>
									<td>XL</td>
									<td>XXL</td>
								</tr>
								<tr>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
								</tr>
								<tr>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								</tbody>
								</table>
								</td>
							</tr>
							<tr>
								<td colspan="5">具体货箱数量及装箱条码表对应物流单号表</td>
							</tr>
							<tr>
								<td>总货箱数</td>
								<td colspan="4">1</td>
							</tr>
							<tr>
								<td>条码列表</td>
								<td colspan="2">物流单号</td>
								<td colspan="2">物流公司</td>
							</tr>
							<tr>
								<td>1</td>
								<td colspan="2">1</td>
								<td colspan="2">1</td>
							</tr>
							<tr>
								<td rowspan="2">发货信息</td>
								<td>物流部发货人</td>
								<td>发货时间</td>
								<td colspan="2">发货备注</td>
							</tr>
							<tr>
								<td>1</td>
								<td>1</td>
								<td colspan="2">1</td>
							</tr>
							<tr>
								<td rowspan="4">物流公司信息</td>
								<td>公司名称</td>
								<td>网址</td>
								<td>查询网址</td>
								<td>客服电话</td>
							</tr>
							<tr>
								<td>1</td>
								<td>1</td>
								<td>1</td>
								<td>1</td>
							</tr>
							<tr>
								<td>物流取货人</td>
								<td>承诺到货日期</td>
								<td colspan="2">运输方式</td>
							</tr>
							<tr>
								<td>1</td>
								<td>1</td>
								<td colspan="2">1</td>
							</tr>
							<tr>
								<td>操作</td>
								<td colspan="4"><button class="btn btn-primary btn-rounded">确认发货</button></td>
							</tr>
						</table>
						<input type="hidden" name="customerId"
							value="${customer.customerId}" />
							<input type="hidden" name="taskId"
							value="${log.taskId}" />
							<input type="hidden" name="processId"
							value="${log.processId}" />
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
<script type="text/javascript" src="${ctx}/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="${ctx}/js/order/add_order.js"></script>
<script type="text/javascript" src="${ctx }/js/custom.js"></script>
<%@include file="/common/footer.jsp"%>

