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
	<c:if test="${orderInfo.craft.needCraft == 1}">
		<tr>
			<td class="title" rowspan="3">工艺报价</td>
			<td class="title">印花费（单件）</td>
			<td class="title">水洗吊染费（单件）</td>
			<td class="title">激光费（单件）</td>
			<td class="title">刺绣费（单件）</td>
		</tr>
		<tr>
			<td>${orderInfo.craft.stampDutyMoney}</td>
			<td>${orderInfo.craft.washHangDyeMoney}</td>
			<td>${orderInfo.craft.laserMoney}</td>
			<td>${orderInfo.craft.embroideryMoney}</td>
		</tr>
		<tr>
			<td class="title">压皱费（单件）</td>
			<td>${orderInfo.craft.crumpleMoney}</td>
			<td class="title">开版费（整体）</td>
			<td>${orderInfo.craft.openVersionMoney}</td>
		</tr>
	</c:if>
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
		<input class="span12" type="hidden" name="merge_w" value="${merge_w }" />
		<input class="span12" type="hidden" name="orderInfoquoteFabricCost" value="${orderInfo.quote.fabricCost }" />
		<input class="span12" type="hidden" name="orderInfoquoteAccessoryCost" value="${orderInfo.quote.accessoryCost }" />
		<input class="span12" type="hidden" name="orderInfoquoteCutCost" value="${orderInfo.quote.cutCost }" />
		<input class="span12" type="hidden" name="orderInfoquoteManageCost" value="${orderInfo.quote.manageCost }" />
		<input class="span12" type="hidden" name="orderInfoquoteSwingCost" value="${orderInfo.quote.swingCost }" />
		<input class="span12" type="hidden" name="orderInfoquoteIroningCost" value="${orderInfo.quote.ironingCost }" />
		<input class="span12" type="hidden" name="orderInfoquoteNailCost" value="${orderInfo.quote.nailCost }" />
		<input class="span12" type="hidden" name="orderInfoquotePackageCost" value="${orderInfo.quote.packageCost }" />
		<input class="span12" type="hidden" name="orderInfoquoteOtherCost" value="${orderInfo.quote.otherCost }" />
		<input class="span12" type="hidden" name="orderInfoquoteCraftCost" value="${orderInfo.quote.craftCost }" />
		<input class="span12" type="hidden" name="orderInfoquoteSingleCost" value="${orderInfo.quote.singleCost }" />
		<input class="span12" type="hidden" name="orderInfoquoteInnerPrice" value="${orderInfo.quote.innerPrice }" />
		  
		<input class="span12" type="text" name="single_cost" required="required"  readonly="readonly" />
		</td>
		<td><input class="span12" type="text" name="inner_price" required="required"  readonly="readonly" />
		</td>
		<!-- 
		<input class="span12" type="text" name="single_cost" required="required" value="${merge_w eq null ? orderInfo.quote.singleCost :  
			(orderInfo.quote.fabricCost + orderInfo.quote.accessoryCost + orderInfo.quote.cutCost + orderInfo.quote.manageCost +
			orderInfo.quote.swingCost + orderInfo.quote.ironingCost + orderInfo.quote.nailCost + orderInfo.quote.packageCost +
			orderInfo.quote.otherCost + 0.0) }" readonly="readonly" />
		</td>
		<td><input class="span12" type="text" name="inner_price" required="required" value="${merge_w eq null ? orderInfo.quote.innerPrice : 
			orderInfo.quote.fabricCost + orderInfo.quote.accessoryCost + orderInfo.quote.cutCost + orderInfo.quote.manageCost +
			orderInfo.quote.swingCost + orderInfo.quote.ironingCost + orderInfo.quote.nailCost + orderInfo.quote.packageCost +
			orderInfo.quote.otherCost }" readonly="readonly" />
		</td> />
		 -->                                                          
		<td><input class="span12" type="text" name="profitPerPiece" required="required" value="${merge_w eq null ? orderInfo.quote.profitPerPiece : '' }" />	
		</td>
		<td><input class="span12" type="text" name="outer_price" required="required" value="${merge_w eq null ? orderInfo.quote.outerPrice : '' }" readonly="readonly" />	
		</td>
	</tr>

</table>