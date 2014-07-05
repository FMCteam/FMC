<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div>
	<form id="orderSearch"  method="post" action="${ctx}/market/mergeQuoteListSearch.do">
	<table class="list tablesorter">
		<caption>
			<span class="text-vertical">${taskName}:<span class="number">${fn:length(list)}</span>件任务
			</span>
			<br>
						<span >输入起始日期:</span>
						<input class="search-query" type="text" name="startdate" placeholder="输入订单起始日期">
						<span >输入截止日期:</span>
						<input class="search-query" type="text" name="enddate" placeholder="输入订单截止日期">
			<br>
						<input class="btn btn-primary" type="submit" value="查询" style="float:right;">
						<span >输入订单编号:</span>
						<input type="text" class="search-query " name="ordernumber" placeholder="输入订单编号">
						<span >市场专员名称:</span>
						<input type="text" class="search-query " name="employeename" placeholder="输入市场专员名称">
						<span >款式名称:</span>
						<input type="text" class="search-query " name="stylename" placeholder="输入款式名称">
						<span >客户名称:</span>
						<input type="text" class="search-query " name="customername" placeholder="输入客户名称">
			 
		</caption>
    </table>
  </form>
 </div>
