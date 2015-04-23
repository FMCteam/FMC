<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>

<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
			<!--  如果是其它页面，这里是填充具体的内容。 -->

			<ul class="nav nav-tabs detail" id="tab">
				<li class="task-name">${orderInfo.taskName}</li>
				<li class="active"><a href="#finance" data-toggle="tab">${orderInfo.tabName}</a></li>
				<li><a href="#quote" data-toggle="tab">报价信息</a></li>
				<li><a href="#cad" data-toggle="tab">版型信息</a></li>
				<li><a href="#produce" data-toggle="tab">加工信息</a></li>
				<li><a href="#sample" data-toggle="tab">样衣信息</a></li>
				<li><a href="#material" data-toggle="tab">面辅信息</a></li>
				<li><a href="#basic" data-toggle="tab">基本信息</a></li>
			</ul>

			<div class="tab-content">
				<div class="tab-pane" id="basic">
					<%@include file="/views/common/basic.jsp"%>
				</div>
				<div class="tab-pane" id="material">
					<%@include file="/views/common/material.jsp"%>
				</div>
				<div class="tab-pane" id="sample">
					<%@include file="/views/common/sample.jsp"%>
				</div>
				<div class="tab-pane" id="produce">
					<%@include file="/views/common/produce.jsp"%>
				</div>
				<div class="tab-pane" id="cad">
					<%@include file="/views/common/cad.jsp"%>
				</div>
				<div class="tab-pane" id="quote">
					<%@include file="/views/common/quote.jsp"%>
				</div>
				<div class="tab-pane  active" id="finance">
					<form id="verify_form" action="${ctx}${orderInfo.url}"
						method="post" onsubmit="return verifyFinance();">
						<input type="hidden" name="money_state" value="已收到" /> <input
							id="verify_val" type="hidden" name="val" value="已收到" /> <input
							type="hidden" name="money_type" value="${orderInfo.type}" /> <input
							type="hidden" name="orderId" value="${orderInfo.order.orderId}" />
						<input type="hidden" name="taskId" value="${orderInfo.taskId}" /><input
							type="hidden" name="result" value="1" />
                         <input name="orderInfoOrderDiscount" type="hidden" value="${orderInfo.order.discount}"/>
                         <input name="orderInfoOrderSampleAmount" type="hidden" value="${orderInfo.order.sampleAmount}"/>
                         <input name="orderInfoSamplePrice" type="hidden" value="${orderInfo.samplePrice}"/>
                         
						<table class="table table-bordered detail finance">
							<tr>
								<td class="span2 title" rowspan="6">费用信息</td>
								<td class="title">金额类型</td>
								<td class="title">优惠金额</td>
								<td class="title" style="color: red;">应收金额</td>
							</tr>
							<tr>
								<td>${orderInfo.moneyName}</td>
								<td>${orderInfo.order.discount}</td>
<!-- 
								<span id="pay">${((orderInfo.number)*orderInfo.price-orderInfo.order.discount)*0.3}</span>
 -->
								<td>
								(
								<a id="totalNumber" style="color: black;">
								</a>
								-${orderInfo.order.discount})*0.3=
								<input name="amountReceivable" type="text" readonly="readonly" />
								</td>
							</tr>
							<tr>
								<td class="title">大货件数</td>
								<td class="title">大货单价</td>
								<td class="title">大货总价</td>
							</tr>
							<tr>
								<td>
								<input name="orderInfoNumber" type="text" readonly="readonly"  value="${orderInfo.number}"/>
								 </td>
								<td>
								<input type="text" name="orderInfoPrice" readonly="readonly" value="${orderInfo.price}"/> 			
								</td>
								<td>
								<!-- 
                                   <span id="pay">${orderInfo.number*orderInfo.price}</span>
								 -->
								<input type="text" name="orderInfoNumberPriceProduct" readonly="readonly" /> 			
								
								</td>
							</tr>
							<tr>
								<td class="title">样衣件数</td>
								<td class="title">样衣单价</td>
								<td class="title">样衣总价</td>
							</tr>
							<tr>
								<td>${orderInfo.order.sampleAmount}</td>
								<td>${orderInfo.samplePrice}</td>
								<td>${orderInfo.order.sampleAmount*orderInfo.samplePrice}</td>
							</tr>
							<tr>
								<td class="title" rowspan="5">汇款信息</td>
								<td class="title">汇款人<span style="color: red">*</span></td>
								<td class="title">汇款卡号</td>
								<td class="title">汇款银行<span style="color: red">*</span></td>

							</tr>
							<tr>
								<td><input type="text" required="required" name="money_name" />
									</td>
								<td>
								    <input type="text"  name="money_number" />
									</td>
								<td>
									<input type="text" required="required" name="money_bank" />
									</td>

							</tr>
							<tr>
								<td class="title" style="color: red;">汇款金额</td>
								<td class="title">收款账号<span style="color: red">*</span></td>
								<td class="title">收款时间<span style="color: red">*</span></td>

							</tr>
							<tr>
							<!-- 
							value="${((orderInfo.number+orderInfo.order.sampleAmount)*orderInfo.price-orderInfo.order.sampleAmount*orderInfo.samplePrice-orderInfo.order.discount)*0.3}"
							 -->
								<td><input type="text" name="money_amount"
									readonly="readonly" /></td>
								<td>
									<select name="account" required="required" >
										<option selected="selected">36933145@qq.com</option>
										<option>6228480424649506013</option>
										<option>872104037@qq.com</option>
									</select>
								</td>
								<td><input type="text" required="required" name="time" id="input_day" readonly="readonly"/></td>

							</tr>
							<tr>
								<td class="title" colspan="1">备注</td>
								<td colspan="2"><!-- <input type="text" name="money_remark"
									class="span12" /> -->
									<textarea  name="money_remark"  id="money_remark"  class="span12"   readonly="readonly"></textarea>
								</td>
							</tr>
		                    <tr>
		                        <td class="title">收款信息</td>
		                        <td class="title">收款图片</td>
		                        <td colspan="3"><c:if test="${orderInfo.order.confirmDepositFile!=null}">
				                <img src="${orderInfo.order.confirmDepositFile}"
					                 style="max-height: 300px;" alt="收款图片"></img>
			                    </c:if></td>
	                        </tr>
						</table>
						
						<a href="${ctx}${orderInfo.url}?orderId=${orderInfo.order.orderId}&taskId=${orderInfo.taskId}&result=0"
								onclick="return confirm('确认未收到汇款？')"
								class="btn btn-danger btn-rounded"
								style="color: white"><i class="icon-remove icon-white"></i>未收到汇款</a>
						<div class="action" style="float:right">
							<input type="submit" id="financeSubmit" hidden="hidden" /> 
							
							<a id="financeButton" class="btn btn-primary btn-rounded">
								<i class="icon-ok icon-white"></i>已确认收款</a>
						</div>
						<br>
					</form>
					<button class="btn btn-primary" onclick="history.back();">返回</button>
				</div>
			</div>
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
<!--maincontent-->
<script type="text/javascript">
	$("#money_remark").val("${orderInfo.order.moneyremark}");
</script>

<%@include file="/common/js_file.jsp"%>
<%@include file="/common/js_form_file.jsp"%>
<link rel="stylesheet" href="${ctx}/css/order/add_order.css">
<link rel="stylesheet" href="${ctx}/views/finance/finance.css">
<link rel="stylesheet" href="${ctx}/css/fmc/detail.css">
<script type="text/javascript" src="${ctx }/js/custom.js"></script>
<script type="text/javascript" src="${ctx}/js/order/add_order.js"></script>
<!-- 汇款金额操作js -->
<script type="text/javascript" src="${ctx}/views/finance/remittanceFinance.js"></script>
<%@include file="/common/footer.jsp"%>
