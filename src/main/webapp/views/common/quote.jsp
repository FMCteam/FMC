<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<table class="table table-striped table-bordered detail">
 
	<tr>
		<td class="title" rowspan="${fn:length(orderInfo.fabricCosts)+1}">面料报价</td>
		<td class="title">面料名</td>
		<td class="title">单件米耗</td>
		<td class="title">价格</td>
		<td class="title">单件成本</td>
	</tr>

	<c:forEach var="fabric" items="${orderInfo.fabricCosts}">
		<tr>
			<td>${fabric.fabricName}</td>
			<td>${fabric.tearPerMeter}</td>
			<td>${fabric.costPerMeter}</td>
			<td>${fabric.price}</td>
		</tr>
	</c:forEach>
	<tr>
		<td class="title" rowspan="${fn:length(orderInfo.accessoryCosts)+1}">辅料报价</td>
		<td class="title">辅料名</td>
		<td class="title">单件耗数</td>
		<td class="title">价格</td>
		<td class="title">单件成本</td>
	</tr>
	<c:forEach var="accessory" items="${orderInfo.accessoryCosts}">
		<tr>
			<td>${accessory.accessoryName}</td>
			<td>${accessory.tearPerPiece}</td>
			<td>${accessory.costPerPiece}</td>
			<td>${accessory.price}</td>
		</tr>
	</c:forEach>
	<tr>
		<td class="title">面辅总计</td>
		<td class="title">面料总计</td>
		<td>${orderInfo.quote.fabricCost}</td>
		<td class="title">辅料总计</td>
		<td>${orderInfo.quote.accessoryCost}</td>
	</tr>
	<tr>
		<td class="title" rowspan="4">其他成本</td>
		<td class="title">裁剪费用（单件）</td>
		<td class="title">管理费用（单件）</td>
		<td class="title">缝制费用（单件）</td>
		<td class="title">整烫费用（单件）</td>
	</tr>

	<tr>
		<td>${orderInfo.quote.cutCost}</td>
		<td>${orderInfo.quote.manageCost}</td>
		<td>${orderInfo.quote.nailCost}</td>
		<td>${orderInfo.quote.ironingCost}</td>
	</tr>
	<tr>
		<td class="title">锁订费用（单件）</td>
		<td class="title">包装费用（单件）</td>
		<td class="title">其他费用（单件）</td>
		<td class="title">设计费用（整体）</td>
	</tr>
	<tr>
		<td>${orderInfo.quote.swingCost}</td>
		<td>${orderInfo.quote.packageCost}</td>
		<td>${orderInfo.quote.otherCost}</td>
		<td>${orderInfo.quote.designCost}</td>
	</tr>
	<tr>
		<td class="title" rowspan="2">费用核算</td>
		<td class="title">成本总计</td>
		<td class="title">生产报价</td>
		<td class="title">单件利润</td>
		<td class="title">客户报价</td>
	</tr>
	<tr>
		<td>
<!-- 
 -->		
		    <span id="pay">
		    ${orderInfo.quote.singleCost*orderInfo.order.askAmount}
		    </span>
		</td>
		<td>
<!-- 
 -->
             <span id="pay2">
            ${orderInfo.quote.singleCost*orderInfo.order.askAmount}
            </span>		
		</td>
		<td>${orderInfo.quote.profitPerPiece}</td>
		<td>${orderInfo.quote.outerPrice}</td>
	</tr>

</table>
<script type="text/javascript">
$(document).ready(function() {
 var text=$("#pay").text();
	$("#pay").text(parseFloat(text).toFixed(2));
 var text=$("#pay2").text();
	$("#pay2").text(parseFloat(text).toFixed(2));
	 
});  
</script>