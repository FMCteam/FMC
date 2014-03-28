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


</head>
<body>
	<div id="mainwrapper" class="mainwrapper">

		<div class="header">
			<div class="logo">
				<a href="#" style="color:white;font-size:55px;font-family:"
					MiscrosoftYaHei"">
					<!-- <img src="images/logo.png" alt="" /> -->智造链
				</a>
			</div>
			<div class="headerinner">
				<ul class="headmenu">
					<li class="odd"><a href="#"> <span class="count">4</span>
							<span class="head-icon head-message"></span> <span
							class="headmenu-label">新任务</span>
					</a></li>
					<li><a> <span class="count">10</span> <span
							class="head-icon head-users"></span> <span class="headmenu-label">新客户</span>
					</a></li>

					<li class="right">
						<div class="userloggedinfo">
							<!--                         <img src="images/photos/thumb1.png" alt="" /> -->
							<div class="userinfo">
								<h5>${USER_nick_name }<small>-
										${USER_user_name }</small>
								</h5>
								<ul>
									<li><a href="${ctx }/account.do">查看详情</a></li>
									<li><a href="${ctx }/account/modify.do">修改账户</a></li>
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
					<li class="active"><a href="dashboard.html"><span
							class="iconfa-laptop"></span> 欢迎</a></li>

					
						<c:if test="${ROLE_customer==true}">
							<li class="dropdown"><a href=""><span
									class="iconfa-pencil"></span> 客户管理</a>
								<ul>
									<li><a href="${ctx }/customer/add.do">新建客户</a></li>
									<li><a href="${ctx }/customer/search.do">查看客户</a></li>
								</ul>
							</li>
						</c:if>
						<c:if test="${ROLE_order==true}">
							<li class="dropdown"><a href=""><span
								class="iconfa-pencil"></span> 订单管理</a>
							<ul>
								<li><a href="#">查看订单</a></li>
							</ul>
							</li>
						</c:if>
						<c:if test="${ROLE_market==true}">
							<li class="dropdown"><a href=""><span
								class="iconfa-briefcase"></span> 市场部</a>
								<ul>
									<li><a href="${ctx }/market/customerOrder.do">客户下单</a></li>
									<li><a href="${ctx }/market/sampleOrderList.do">修改询单</a></li>
									<li><a href="${ctx }/market/computerOrderSumList.do">合并报价</a></li>
									<li><a href="${ctx }/market/checkOrderSumList.do">审核报价</a></li>
									<li><a href="#">报价商定</a></li>
									<li><a href="#">修改报价</a></li>
									<li><a href="#">商定合同</a></li>
									<li><a href="#">修改合同</a></li>
									<li><a href="#">签订合同</a></li>
									<li><a href="#">订单回访</a></li>
									<li><a href="#">提醒缴费</a></li>
								</ul>
							</li>
						</c:if>
						<c:if test="${ROLE_design==true}">
						<li class="dropdown"><a href=""><span
							class="iconfa-th-list"></span> 设计部</a>
						<ul>
							<li><a href="${ctx }/design/verify.do">样衣验证</a></li>
							<li><a href="#">成本合算</a></li>
							<li><a href="#">样衣版型</a></li>
							<li><a href="#">生产验证</a></li>
							<li><a href="#">生产版型</a></li>
						</ul></li>
						</c:if>
						<c:if test="${ROLE_buy==true}">
						<li class="dropdown"><a href=""><span
							class="iconfa-th-list"></span> 采购部</a>
						<ul>
							<li><a href="${ctx }/buy/verify.do">样衣验证</a></li>
							<li><a href="#">成本合算</a></li>
							<li><a href="#">样衣采购</a></li>
							<li><a href="#">生产验证</a></li>
							<li><a href="#">生产采购</a></li>
						</ul></li>
						</c:if>
						<c:if test="${ROLE_produce==true}">
						<li class="dropdown"><a href=""><span
							class="iconfa-th-list"></span> 生产部</a>
						<ul>
							<li><a href="${ctx }/produce/verify.do">样衣验证</a></li>
							<li><a href="#">成本合算</a></li>
							<li><a href="#">样衣生产</a></li>
							<li><a href="#">批量生产</a></li>
						</ul></li>
						</c:if>
						<c:if test="${ROLE_finance==true}">
						<li class="dropdown"><a href=""><span
							class="iconfa-th-list"></span> 财务部</a>
						<ul>
							<li><a href="#">样衣金确认</a></li>
							<li><a href="#">首定金确认</a></li>
							<li><a href="#">生产金确认</a></li>
							<li><a href="#">尾金确认</a></li>
						</ul></li>
						</c:if>
						<c:if test="${ROLE_logistics==true}">
						<li class="dropdown"><a href=""><span
							class="iconfa-th-list"></span> 物流部</a>
						<ul>
							<li><a href="${ctx }/logistics/sampleOrderList.do">收取样衣</a></li>
							<li><a href="${ctx }/logistics/add.do">样衣发货</a></li>
							<li><a href="${ctx }/logistics/add.do">产品入库</a></li>
							<li><a href="${ctx }/logistics/add.do">产品发货</a></li>
						</ul></li>
						</c:if>
						<c:if test="${ROLE_quality==true}">
						<li class="dropdown"><a href=""><span
							class="iconfa-th-list"></span> 质检部</a>
						<ul>
							<li><a href="#">质量检查</a></li>
						</ul></li>
						</c:if>
						<c:if test="${ROLE_employee==true}">
						<li class="dropdown"><a href=""><span
							class="iconfa-th-list"></span> 人事部</a>
						<ul>
							<li><a href="${ctx }/employee/add.do">添加员工</a></li>
							<li><a href="${ctx }/employee/search.do">查看员工</a></li>
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