<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" import="nju.software.filter.AccessFilter"%>
<%@ page language="java" import="nju.software.dataobject.Account"%>
<%@ include file="/common/taglibs.jsp"%>

<!doctype html>
<head>
<title>智造链</title>
<meta http-equiv="content-type" content="text/html;charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@ include file="/common/css_file.jsp"%>
<script type="text/javascript" src="${ctx }/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.tablesorter.min.js"></script>
<script type="text/javascript"
	src="${ctx}/js/jquery.noty.packaged.min.js"></script>
<script>
$(document).ready(function() {
	if ('${notify}' != null && '${notify}' != "") {
		noty({
			text : '${notify}',
			layout : 'topCenter',
			timeout : 2000
		});
	}
});
</script>
<script type="text/javascript">
	$(document).ready(function() {
		//	if($("table.tablesorter tbody tr").length!=0){
		$("table.tablesorter").tablesorter();
		//	}

		$.ajax({
			url : "${ctx}/common/getTaskNumber.do",
			success : function(msg) {
				var json = eval("(" + msg + ")");
				//$("span.count:eq(0)").text(json.list);
				//alert(msg);
				for ( var key in json) {
					//alert(key+json[key]);
					$("span." + key).text("  (" + json[key] + ")");
				}
			}
		});
		//$("li span.task").css("color","white");
	});
</script>
<style type="text/css">
table.tablesorter thead th {
	height: 20px;
}

table.tablesorter thead tr .header {
	background-image: url(${ctx}/images/blueimages/bg.gif);
	background-repeat: no-repeat;
	background-position: center right;
	cursor: pointer;
}

table.tablesorter thead tr .headerSortUp {
	background-image: url(${ctx}/images/blueimages/asc.gif);
}

table.tablesorter thead tr .headerSortDown {
	background-image: url(${ctx}/images/blueimages/desc.gif);
}
</style>

</head>
<body>
	<div id="mainwrapper" class="mainwrapper">
		<div class="header">
			<div class="logo">
				<a href="#" style="color:white;font-size:55px;font-family:"MiscrosoftYaHei"">
					<!-- <img src="images/logo.png" alt="" /> --> <span>智造链</span>
				</a>
			</div>
			<div class="headerinner">
				<ul class="headmenu">
					<li class="odd"><a href="#"> <span
							class="count taskNumber"></span> <span
							class="head-icon head-message"></span> <span
							class="headmenu-label">任务</span>
					</a></li>
					<li><a> <span class="count">${taskNumber}</span> <span
							class="head-icon head-users"></span> <span class="headmenu-label">新客户</span>
					</a></li>

					<li class="right">
						<div class="userloggedinfo">
							<!--                         <img src="images/photos/thumb1.png" alt="" /> -->
							<div class="userinfo">
								<h5>${USER_nick_name }<small>- ${USER_user_name }</small>
								</h5>
								<ul>
									<li><a href="${ctx }/account/modifyEmployeeDetail.do">修改账户</a></li>
									<li><a href="${ctx }/logout.do">退出</a></li>
								</ul>
							</div>
						</div>
					</li>
				</ul>
				<!--headmenu-->
			</div>
		</div>

		<div class="leftpanel">

			<div class="leftmenu">
				<ul class="nav nav-tabs nav-stacked">
					<li class="nav-header">Navigation</li>
					<li class="active"><a href="#"><span class="iconfa-laptop"></span>
							欢迎</a></li>


					<c:if test="${ROLE_customer==true}">
						<li class="dropdown"><a href=""><span
								class="iconfa-pencil"></span> 客户管理</a>
							<ul>
								<li><a href="${ctx}/account/addCustomerDetail.do">新建客户</a></li>
								<li><a href="${ctx}/account/customerList.do">查看客户</a></li>
							</ul></li>
					</c:if>
					<c:if test="${ROLE_order==true}">
						<li class="dropdown"><a href=""><span
								class="iconfa-pencil"></span> 订单管理</a>
							<ul>
								<li><a href="${ctx}/order/orderList.do">查看订单</a></li>
							</ul></li>
					</c:if>
					<c:if test="${ROLE_market==true}">
						<li class="dropdown"><a href=""><span
								class="iconfa-briefcase"></span> 市场部<span
								class="marketManager task"></span></a>
							<ul>
								<li><a href="${ctx }/market/addOrderList.do">客户下单</a></li>
								<li><a href="${ctx }/market/modifyOrderList.do">修改询单<span
										class="modifyOrder"></span></a></li>

								<li><a href="${ctx }/market/mergeQuoteList.do">合并报价<span
										class="mergeQuote"></span></a></li>
								<li><a href="${ctx }/market/verifyQuoteList.do">审核报价<span
										class="verifyQuote"></span></a></li>

								<li><a href="${ctx }/market/confirmQuoteList.do">报价商定<span
										class="confirmQuote"></span></a></li>

								<li><a href="${ctx }/market/modifyQuoteList.do">修改报价<span
										class="modifyQuote"></span></a></li>
								<li><a href="${ctx }/market/confirmProduceOrderList.do">商定合同<span
										class="confirmProduceOrder"></span></a></li>

								<li><a href="${ctx }/market/modifyProductList.do">修改合同<span
										class="modifyProduceOrder"></span></a></li>
								<li><a href="${ctx }/market/signContractList.do">签订合同<span
										class="signContract"></span></a></li>

								<li><a href="#">订单回访</a></li>
								<li><a href="#">提醒缴费</a></li>
							</ul></li>
					</c:if>
					<c:if test="${ROLE_design==true}">
						<li class="dropdown"><a href=""><span
								class="iconfa-th-list"></span> 设计部<span
								class="designManager task"></span></a>
							<ul>
								<li><a href="${ctx}/design/verifyDesignList.do">设计验证<span
										class="verifyDesign"></span></a></li>
								<li><a href="${ctx}/design/getUploadDesignList.do">样衣版型<span
										class="uploadDegisn"></span></a></li>
								<li><a href="${ctx}/design/getModifyDesignList.do">大货生产验证<span
										class="modifyDesign"></span></a></li>
								<li><a href="${ctx}/design/getConfirmDesignList.do">大货生产版型<span
										class="confirmDesign"></span></a></li>
							</ul></li>
					</c:if>
					<c:if test="${ROLE_buy==true}">
						<li class="dropdown"><a href=""><span
								class="iconfa-th-list"></span> 采购部<span
								class="purchaseManager task"></span></a>
							<ul>
								<li><a href="${ctx }/buy/verifyPurchaseList.do">采购验证<span
										class="verifyPurchase"></span></a></li>
								<li><a href="${ctx }/buy/computePurchaseCostList.do">成本合算<span
										class="computePurchaseCost"></span></a></li>
								<li><a href="${ctx }/buy/purchaseSampleMaterialList.do">样衣采购<span
										class="purchaseSampleMaterial"></span></a></li>
								<li><a href="${ctx }/buy/confirmPurchaseList.do">大货采购确认<span
										class="confirmPurchase"></span></a></li>
								<li><a href="${ctx }/buy/purchaseMaterialList.do">大货生产原料采购<span
										class="purchaseMaterial"></span></a></li>
							</ul></li>
					</c:if>
					<c:if test="${ROLE_produce==true}">
						<li class="dropdown"><a href=""><span
								class="iconfa-th-list"></span> 生产部<span
								class="produceManager task"></span></a>
							<ul>
								<li><a href="${ctx }/produce/verifyProduceList.do">生产验证<span
										class="verifyProduce"></span></a></li>
								<li><a href="${ctx }/produce/computeProduceCostList.do">成本合算<span
										class="computeProduceCost"></span></a></li>
								<li><a href="${ctx }/produce/produceSampleList.do">样衣生产<span
										class="produceSample"></span></a></li>
								<li><a href="${ctx }/produce/produceList.do">大货批量生产<span
										class="produce"></span></a></li>
							</ul></li>
					</c:if>
					<c:if test="${ROLE_finance==true}">
						<li class="dropdown"><a href=""><span
								class="iconfa-th-list"></span> 财务部<span
								class="financeManager task"></span></a>
							<ul>
								<li><a href="${ctx}/finance/confirmSampleMoneyList.do">样衣费确认<span
										class="confirmSampleMoney"></span></a></li>
								<li><a href="${ctx}/finance/confirmDepositList.do">首定金确认<span
										class="confirmDeposit"></span></a></li>
								<li><a href="${ctx}/finance/confirmFinalPaymentList.do">尾款金确认<span
										class="confirmFinalPayment"></span></a></li>
							</ul></li>
					</c:if>
					<c:if test="${ROLE_logistics==true}">
						<li class="dropdown"><a href=""><span
								class="iconfa-th-list"></span> 物流部<span
								class="logisticsManager task"></span></a>
							<ul>
								<li><a href="${ctx}/logistics/receiveSampleList.do">样衣收取<span
										class="receiveSample"></span></a></li>
								<li><a href="${ctx}/logistics/sendSampleList.do">样衣发货<span
										class="sendSample"></span></a></li>
								<li><a href="${ctx}/logistics/warehouseList.do">产品入库<span
										class="warehouse"></span></a></li>
								<li><a href="${ctx}/logistics/sendClothesList.do">产品发货<span
										class="sendClothes"></span></a></li>
							</ul></li>
					</c:if>
					<c:if test="${ROLE_quality==true}">
						<li class="dropdown"><a href=""><span
								class="iconfa-th-list"></span> 质检部<span
								class="qualityManager task"></span></a>
							<ul>
								<li><a href="${ctx }/quality/checkQualityList.do">质量检查<span
										class="checkQuality"></span></a></li>
							</ul></li>
					</c:if>
					<c:if test="${ROLE_employee==true}">
						<li class="dropdown"><a href=""><span
								class="iconfa-th-list"></span> 人事部</a>
							<ul>
								<li><a href="${ctx}/account/addEmployeeDetail.do">添加员工</a></li>
								<li><a href="${ctx}/account/employeeList.do">查看员工</a></li>
							</ul></li>
					</c:if>

				</ul>
			</div>
			<!--leftmenu-->

		</div>
		<!-- leftpanel -->

		<div class="rightpanel">

			<ul class="breadcrumbs">
				<li><a href="dashboard.html"><i class="iconfa-home"></i></a> <span
					class="separator"></span></li>
				<li>欢迎！</li>
			</ul>