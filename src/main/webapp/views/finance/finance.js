function confirmFinanceSubmit() {
	return confirm("确认操作？");
}

function verifyFinance() {
	//var money_name = $("input[name='money_name']").val();
	//var money_number = $("input[name='money_number']").val();
	//var money_bank = $("input[name='money_bank']").val();
	var money_amount = $("input[name='money_amount']").val();
	//var money_remark = $("input[name='money_remark']").val();
	//if (money_name == "" || money_number == "" || money_bank == ""
	//		|| money_amount == "" || isNaN(money_amount)) {
		// alert("请填入正确数据内容");
	if(isNaN(money_amount)){
		noty({
			text : '汇款金额必须是数字',
			layout : 'topCenter',
			timeout : 2000
		});
		return false;
	}
	return confirmFinanceSubmit();
}

$(document).ready(function() {
	$("a#financeButton").click(function() {
		$("input#financeSubmit").click();
	});
//	var text=$("#pay").text();
//	$("#pay").val(parseFloat(text).toFixed(2));
//	$("input[name='money_amount']").val(parseFloat(text).toFixed(2));
	
	var text=$("#pay").text();
	$("#pay").text(parseFloat(text).toFixed(2));
//	$("input[name='money_amount']").val(parseFloat(text).toFixed(2));

//	$("input[name='orderNumberPriceProduct']").val(parseFloat(text).toFixed(2));	
	var orderInfoNumber = $("input[name='orderInfoNumber']").val();
	var orderInfoPrice = $("input[name='orderInfoPrice']").val();
	var orderInfoOrderDiscount = $("input[name='orderInfoOrderDiscount']").val();
	var orderInfoOrderSampleAmount = $("input[name='orderInfoOrderSampleAmount']").val();
	var orderInfoSamplePrice = $("input[name='orderInfoSamplePrice']").val();

	var orderInfoNumberPriceProduct = orderInfoNumber * orderInfoPrice;
	var amountReceivable = (orderInfoNumberPriceProduct - orderInfoOrderDiscount)*0.3;
	$("input[name='amountReceivable']").val(amountReceivable.toFixed(2));

	$("input[name='orderInfoNumberPriceProduct']").val(orderInfoNumberPriceProduct.toFixed(2));
//	value="${((orderInfo.number+orderInfo.order.sampleAmount)*orderInfo.price-orderInfo.order.sampleAmount*orderInfo.samplePrice-orderInfo.order.discount)*0.3}
	var moneyAmount = ((orderInfoNumber+orderInfoOrderSampleAmount)*orderInfoPrice-orderInfoOrderSampleAmount*orderInfoSamplePrice-orderInfoOrderDiscount)*0.3;
//	$("input[name='money_amount']").val(moneyAmount.toFixed(2));
	$("input[name='money_amount']").val(amountReceivable.toFixed(2));


});