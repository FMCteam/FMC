<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" import="nju.software.filter.AccessFilter"%>
<%@ page language="java" import="nju.software.dataobject.Account"%>
<%@ include file="/common/taglibs.jsp"%>
<!doctype html>
<head>
<title>智造链</title>
<meta http-equiv="content-type" content="text/html;charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@include file="/common/css_file.jsp"%>
<link rel="stylesheet" href="${ctx}/css/fmc/table.css">
<script type="text/javascript" src="${ctx}/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.tablesorter.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.noty.packaged.min.js"></script>

<!--  <script type="text/javascript" src="${ctx }/js/custom.js"></script>-->
<script>
$(function() {
	if ('${notify}' != null && '${notify}' != "") {
		noty({
			text : '${notify}',
			layout : 'topCenter',
			timeout : 2000
		});
	}
});
</script>
<script>
$(function() {
	if ('${orderStateMessage}' != null && '${orderStateMessage}' != "") {
		noty({
			text : '${orderStateMessage}',
			layout : 'topCenter'
		});
	}
});
</script>
<script type="text/javascript">
	$(function() {
		//	if($("table.tablesorter tbody tr").length!=0){
		$("table.tablesorter").tablesorter();
		//	}

		//getTaskNumber();
		//$("li span.task").css("color","white");
		
	});
	
	function getTaskNumber() {
		$.ajax({
			url : "${ctx}/common/getTaskNumber.do",
			success : function(msg) {
				var json = eval("(" + msg + ")");
				for ( var key in json) {
					$("span." + key).text("  (" + json[key] + ")");
				}
			}
		});
	}

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
					<a href="${ctx}/default.do"><img src="${ctx}/images/logo2.png" alt=""  /></a>	<!-- <span>智造链</span> -->
			
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

		<div class="leftpanel" style="overflow: auto;">

			<div class="leftmenu" style="height: 1000px;">
				<ul class="nav nav-tabs nav-stacked">
					<li class="nav-header">导航栏</li>
					<li class="active"><a href="${ctx}/defaultContent.do" target="maincontent"><span class="iconfa-laptop"></span>
							欢迎</a></li>


					<c:if test="${ROLE_customer==true}">
						<li class="dropdown"><a href=""><span
								class="iconfa-pencil"></span> 客户管理</a>
							<ul>
								<li><a href="${ctx}/account/addCustomerDetail.do" target="maincontent">添加客户</a></li>
								<li><a href="${ctx}/account/customerList.do" target="maincontent">查看客户</a></li>
							</ul></li>
					</c:if>
					<c:if test="${ROLE_order==true}">
						<li class="dropdown"><a href=""><span
								class="iconfa-pencil"></span> 订单管理</a>
							<ul><c:if test="${ROlE_addOrder==true}">
							    <li><a href="${ctx}/market/addOrder.do" target="maincontent">客户下单</a></li></c:if>
								<li><a href="${ctx}/order/orderList.do" target="maincontent">查看所有订单</a></li>
								<li><a href="${ctx}/order/orderListDoing.do" target="maincontent">查看正在进行订单</a></li>
								<li><a href="${ctx}/order/orderListDone.do" target="maincontent">已经完成订单</a></li>
								<li><a href="${ctx}/order/endList.do" target="maincontent">被终止订单</a></li>
								<c:if test="${USER_user_name eq 'admin'}">
									<li><a href="${ctx}/account/modifyOrderList.do" target="maincontent">修改订单</a></li>
								</c:if>
							</ul></li>
					</c:if>
					<c:if test="${ROLE_IS_MARKET_MANAGER_OR_ADMIN==true}">
						<li class="dropdown"><a href=""><span
								class="iconfa-briefcase"></span> 市场主管<span
								class="marketManager2 task"></span></a>
							<ul>
								<li><a href="${ctx}/market/verifyQuoteList.do" target="maincontent">审核报价<span
										class="verifyQuote"></span></a></li>
								<li><a href="${ctx}/market/verifyAlterList.do" target="maincontent">审核变更专员申请<span
										class="verifyAlter"></span></a></li>		
							</ul></li>
					</c:if>
	
					<c:if test="${ROLE_IS_MARKET_STAFF_OR_ADMIN==true}">
						<li class="dropdown"><a href=""><span
								class="iconfa-briefcase"></span> 市场部<span
								class="marketManager task"></span></a>
							<ul>
								<li><a href="${ctx}/market/addOrderList.do" target="maincontent">客户下单</a></li>
								<li><a href="${ctx}/market/modifyOrderList.do" target="maincontent">修改询单<span
										class="modifyOrder"></span></a></li>
								<li><a href="${ctx}/market/mergeQuoteList.do" target="maincontent">合并报价<span
										class="mergeQuote"></span></a></li>
								<!-- 
								<c:if test="${CAN_VERIFY_QUOTE==true}">		
								<li><a href="${ctx }/market/verifyQuoteList.do">审核报价<span
										class="verifyQuote"></span></a></li>
								</c:if>
								 -->
								<li><a href="${ctx}/market/confirmQuoteList.do" target="maincontent">报价商定<span
										class="confirmQuote"></span></a></li>
								<li><a href="${ctx}/market/modifyQuoteList.do" target="maincontent">修改报价<span
										class="modifyQuote"></span></a></li>
										
										
								<li><a href="${ctx}/market/confirmProduceOrderList.do" target="maincontent">确认大货加工单并签订合同<span
										class="confirmProduceOrder"></span></a></li>
								<li><a href="${ctx}/market/getPushRestOrderList.do" target="maincontent">催尾款<span
										class="pushRest"></span></a></li>
								<li><a href="${ctx}/market/modifyProductList.do" target="maincontent">修改大货合同<span
										class="modifyProduceOrder"></span></a></li>
								<li><a href="${ctx}/market/applyForAlterMarketStaff.do" target="maincontent">申请更换专员</a></li>
							</ul></li>
					</c:if>
					<c:if test="${ROLE_design==true}">
						<li class="dropdown"><a href=""><span
								class="iconfa-th-list"></span> 设计部<span
								class="designManager task"></span></a>
							<ul>
								<li><a href="${ctx}/design/verifyDesignList.do" target="maincontent">设计验证<span
										class="verifyDesign"></span></a></li>
								
								<!-- 
								<li><a href="${ctx}/design/computeDesignCostList.do">设计工艺验证<span
										class="computeDesignCost"></span></a></li>
								
								<li><a href="${ctx}/design/getUploadDesignList.do">样衣版型<span
										class="uploadDegisn"></span></a></li>
								-->
								<li><a href="${ctx}/design/getUploadDesignList.do" target="maincontent">样衣版型录入及生产<span
										class="uploadDegisn"></span></a></li>										
								<li><a href="${ctx}/design/getTypeSettingSliceList.do" target="maincontent">排版切片<span
										class="typeSettingSlice"></span></a></li>
<%-- 								<li><a href="${ctx}/design/getModifyDesignList.do">大货生产验证<span
										class="modifyDesign"></span></a></li>
								<li><a href="${ctx}/design/getConfirmDesignList.do">大货生产版型<span
										class="confirmDesign"></span></a></li> --%>
								<!--
								<li><a href="${ctx}/design/getNeedCraftSampleList.do">样衣工艺制作<span
										class="craftSample"></span></a></li>
								<li><a href="${ctx}/design/getNeedCraftProductList.do">大货工艺制作<span
										class="craft"></span></a></li>
								-->
								<li><a href="${ctx}/design/getConfirmCadList.do" target="maincontent">确认版型<span
										class="confirmCad"></span></a></li>
							</ul></li>
					</c:if>
					<c:if test="${ROLE_design==true}">
						<li class="dropdown"><a href=""><span
								class="iconfa-th-list"></span> 工艺部<span
								class="craftManager task"></span></a>
							<ul>
								<li><a href="${ctx}/design/computeDesignCostList.do" target="maincontent">设计工艺验证<span
										class="computeDesignCost"></span></a></li>
								<li><a href="${ctx}/design/getNeedCraftSampleList.do" target="maincontent">样衣工艺制作<span
										class="craftSample"></span></a></li>
								<li><a href="${ctx}/design/getNeedCraftProductList.do" target="maincontent">大货工艺制作<span
										class="craft"></span></a></li>
							</ul>
						</li>
					
					</c:if>
					<c:if test="${ROLE_buy==true}">
						<li class="dropdown"><a href=""><span
								class="iconfa-th-list"></span> 采购部<span
								class="purchaseManager task"></span></a>
							<ul>
								<!--  
								<li><a href="${ctx }/buy/verifyPurchaseList.do">采购验证<span
										class="verifyPurchase"></span></a></li>
								-->
								<li><a href="${ctx}/buy/computePurchaseCostList.do" target="maincontent">采购成本验证并核算<span
										class="computePurchaseCost"></span></a></li>
								<li><a href="${ctx}/buy/purchaseSampleMaterialList.do" target="maincontent">样衣采购<span
										class="purchaseSampleMaterial"></span></a></li>
						<%-- 		<li><a href="${ctx }/buy/confirmPurchaseList.do">大货采购确认<span
										class="confirmPurchase"></span></a></li> --%>
								<li><a href="${ctx}//buy/purchaseMaterialList.do" target="maincontent">大货面料采购确认<span
										class="purchaseMaterial"></span></a></li>
								<li><a href="${ctx}//buy/purchaseSweaterMaterialList.do" target="maincontent">毛衣面料采购<span
										class="buySweaterMaterial"></span></a></li>		
								
							</ul></li>
					</c:if>
					<c:if test="${ROLE_produce==true}">
						<li class="dropdown"><a href=""><span
								class="iconfa-th-list"></span> 生产部<span
								class="produceManager task"></span></a>
							<ul>
								<!--  
								<li><a href="${ctx }/produce/verifyProduceList.do">生产验证<span
										class="verifyProduce"></span></a></li>
								-->
								<li><a href="${ctx }/produce/computeProduceCostList.do" target="maincontent">生产成本验证并核算<span
										class="computeProduceCost"></span></a></li>
								<%-- <li><a href="${ctx }/produce/produceSampleList.do">样衣生产<span
										class="produceSample"></span></a></li> --%>
								<li><a href="${ctx }/produce/produceList.do" target="maincontent">大货批量生产<span
										class="produce"></span></a></li>
							</ul></li>
					</c:if>
					<c:if test="${ROLE_sweater==true}">
						<li class="dropdown"><a href=""><span
								class="iconfa-th-list"></span>毛衣制作部<span
								class="SweaterMakeManager task"></span></a>
							<ul>
								<li><a href="${ctx}/sweater/sweaterSampleAndCraftList.do" target="maincontent">样衣确认和工艺制作<span
										class="confirmSweaterSampleAndCraft"></span></a></li>
								<li><a href="${ctx}/sweater/sendSweaterList.do" target="maincontent">毛衣外发<span
										class="sendSweater"></span></a></li>
							</ul></li>
					</c:if>

					
					<c:if test="${ROLE_finance==true}">
						<li class="dropdown"><a href=""><span
								class="iconfa-th-list"></span> 财务部<span
								class="financeManager task"></span></a>
							<ul>
								<li><a href="${ctx}/finance/confirmSampleMoneyList.do" target="maincontent">样衣费确认<span
										class="confirmSampleMoney"></span></a></li>
								<li><a href="${ctx}/finance/confirmDepositList.do" target="maincontent">首定金确认<span
										class="confirmDeposit"></span></a></li>
								<li><a href="${ctx}/finance/returnDepositList.do" target="maincontent">退还定金<span
										class="returnDeposit"></span></a></li>
								<li><a href="${ctx}/finance/confirmFinalPaymentList.do" target="maincontent">尾款金确认<span
										class="confirmFinalPayment"></span></a></li>
								
							</ul></li>
					</c:if>
					<c:if test="${ROLE_logistics==true}">
						<li class="dropdown"><a href=""><span
								class="iconfa-th-list"></span> 物流部<span
								class="logisticsManager task"></span></a>
							<ul>
								<li><a href="${ctx}/logistics/receiveSampleList.do" target="maincontent">样衣收取<span
										class="receiveSample"></span></a></li>
								<li><a href="${ctx}/logistics/sendSampleList.do" target="maincontent">样衣发货<span
										class="sendSample"></span></a></li>
								<li><a href="${ctx}/logistics/warehouseList.do" target="maincontent">产品入库<span
										class="warehouse"></span></a></li>
								<li><a href="${ctx}/logistics/sendClothesList.do" target="maincontent">产品发货<span
										class="sendClothes"></span></a></li>
							</ul></li>
					</c:if>
					<c:if test="${ROLE_quality==true}">
						<li class="dropdown"><a href=""><span
								class="iconfa-th-list"></span> 质检部<span
								class="qualityManager task"></span></a>
							<ul>
								<li><a href="${ctx}/quality/checkQualityList.do" target="maincontent">质量检查<span
										class="checkQuality"></span></a></li>
							</ul></li>
					</c:if>
					<c:if test="${ROLE_employee==true}">
						<li class="dropdown"><a href=""><span
								class="iconfa-th-list"></span> 人事部</a>
							<ul>
								<li><a href="${ctx}/account/addEmployeeDetail.do" target="maincontent">添加员工</a></li>
								<li><a href="${ctx}/account/employeeList.do" target="maincontent">查看员工</a></li>
							</ul></li>
					</c:if>
					<c:if test="${ROLE_employee==true}">
						<li class="dropdown"><a href=""><span
								class="iconfa-th-list"></span> 系统管理</a>
							<ul>
								<li><a href="${ctx}/system/moduleMessages.do" target="maincontent">模组资料</a></li>
								<li><a href="${ctx}/system/rolePrivilege.do" target="maincontent">权限设置</a></li>
								<li><a href="${ctx}/system/roleAss.do" target="maincontent">角色分配</a></li>
							</ul></li>
					</c:if>
				</ul>
			</div>
			<!--leftmenu-->

		</div>
		<!-- leftpanel -->
		<div class="rightpanel">
			<ul class="breadcrumbs">
				<li><a href="${ctx}/defaultContent.do" target="maincontent"><i class="iconfa-home"></i></a> <span
					class="separator"></span></li>
				<li>欢迎！</li>
			</ul>
			<iframe id="maincontent" class="maincontent" name="maincontent" width="100%" frameborder="0" scrolling="auto" src="${ctx}/defaultContent.do" onload="reinitIframe()"></iframe>
			<script type="text/javascript">
				function reinitIframe() {
					var iframe = document.getElementById("maincontent");
					try {
						var bHeight = iframe.contentWindow.document.body.scrollHeight;
						var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
						var height = Math.max(bHeight, dHeight);
						iframe.height = height;
					} catch (ex) {
					}
					//刷新导航栏的任务数量
					getTaskNumber();
				}
			</script>
