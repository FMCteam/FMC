<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<table class="table table-striped table-bordered table-hover detail">
	<tr>
		<td class="span1 title" rowspan="2">加工信息</td>
		<td class="span1 title" colspan="2">样衣总数</td>
		<td class="span1 title" colspan="2">大货总数</td>
		<td class="span1 title" colspan="2">最迟交货时间</td>
		<td class="span1 title" colspan="3">完工时间（天）</td>
	</tr>
	<tr>
		<td class="span1" colspan="2">${orderInfo.order.sampleAmount}</td>
		<td class="span1" colspan="2">${orderInfo.order.askAmount}</td>
		<td class="span1" colspan="2">${fn:substring(orderInfo.order.askDeliverDate,0,10)}</td>
		<td class="span1" colspan="3">${orderInfo.order.askProducePeriod}</td>
	</tr>
	<c:if test="${orderInfo.order.reorder==0 }">
		<tr>
			<td class="span1 title" rowspan="${fn:length(orderInfo.sample)+1}">样衣加工</td>
			<td class="span1 title">颜色</td>
			<td class="span1 title">XS</td>
			<td class="span1 title">S</td>
			<td class="span1 title">M</td>
			<td class="span1 title">L</td>
			<td class="span1 title">XL</td>
			<td class="span1 title">XXL</td>
			<td class="span1 title">均码</td>
			<td class="span1 title">总计</td>
		</tr>
		<c:forEach var="sample" items="${orderInfo.sample}">
			<tr>
				<td>${sample.color}</td>
				<td>${sample.xs}</td>
				<td>${sample.s}</td>
				<td>${sample.m}</td>
				<td>${sample.l}</td>
				<td>${sample.xl}</td>
				<td>${sample.xxl}</td>
				<td>${sample.j}</td>
				<td>${sample.produceAmount}</td>
			</tr>
		</c:forEach>
	</c:if>
	
	<tr>
		<td class="span1 title" rowspan="${fn:length(orderInfo.produce)+1}">大货加工</td>
		<td class="span1 title">颜色</td>
		<td class="span1 title">XS</td>
		<td class="span1 title">S</td>
		<td class="span1 title">M</td>
		<td class="span1 title">L</td>
		<td class="span1 title">XL</td>
		<td class="span1 title">XXL</td>
		<td class="span1 title">均码</td>
		<td class="span1 title">总计</td>
	</tr>
	<c:forEach var="produce" items="${orderInfo.produce}">
		<tr>
			<td>${produce.color}</td>
			<td>${produce.xs}</td>
			<td>${produce.s}</td>
			<td>${produce.m}</td>
			<td>${produce.l}</td>
			<td>${produce.xl}</td>
			<td>${produce.xxl}</td>
			<td>${produce.j}</td>
			<td>${produce.produceAmount}</td>
		</tr>
	</c:forEach>


	<tr>
	<td>加工方：</td>
	<td colspan="9"><input class="span12 " type="text"
	    value="${orderInfo.order.payAccountInfo}" readonly="readonly"/></td>
	</tr>	
</table>
 <table class="table table-striped table-bordered table-hover detail">
							<c:if test="${empty orderInfo.repairRecord}">
								<tr>
									<td class="title" style="width:22%;background: red;">实际生产数</td>
									<td>暂无</td>
								</tr>
							</c:if>
							<c:if test="${!empty orderInfo.repairRecord}">
								<tr>
									<td class="title" rowspan="${fn:length(orderInfo.repairRecord)+1}" style="width:22%;background: #ff0000;">实际生产数</td>
									<td class="title">日期</td>
									<td class="title">加工方</td>
									<td class="title">合格实收数量</td>
								</tr>
								<c:forEach var="repairRecord" items="${orderInfo.repairRecord}">
									<tr>
										<td>${repairRecord.repairTime}</td>
										<td>${repairRecord.repairSide}</td>
										<td>${repairRecord.qualifiedAmount}</td>
									</tr>
								</c:forEach>
							</c:if>
						</table>