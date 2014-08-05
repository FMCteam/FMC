//JavaScript document

(function($) {
	
//	$("input[name='inner_price']").keyup(function(){
//		var s_inner = $("input[name='inner_price']").val();
//		if(s_inner == ""){
//			$("input[name='single_cost']").val("");
//			return;
//		}
//		var inner_price = parseFloat(s_inner);
//		if(!isNaN(inner_price)){
//			$("input[name='single_cost']").val(inner_price+0);
//		}
//	});


	var text=$("#pay").text();
	$("#pay").text(parseFloat(text).toFixed(2));
/*
	var fabricCostTearPerMeter = $("input[name='fabricCostTearPerMeter']").val();
	var orderInfoOrderSampleAmount = $("input[name='orderInfoOrderSampleAmount']").val();
	var allpurchasemeters = fabricCostTearPerMeter * orderInfoOrderSampleAmount;
	$("input[name='allpurchasemeters']").val(allpurchasemeters.toFixed(2));

 */	
	var orderInfoQuoteSingleCost = $("input[name='orderInfoQuoteSingleCost']").val();
	var orderInfoOrderAskAmount = $("input[name='orderInfoOrderAskAmount']").val();
	var quoteSingleCostOrderAskAmountProduct = orderInfoQuoteSingleCost * orderInfoOrderAskAmount;
	$("input[name='quoteSingleCostOrderAskAmountProduct']").val(quoteSingleCostOrderAskAmountProduct.toFixed(2));
	
	var merge_w =  $("input[name='merge_w']").val();
	var orderInfoquoteFabricCost = $("input[name='orderInfoquoteFabricCost']").val();
	var orderInfoquoteAccessoryCost = $("input[name='orderInfoquoteAccessoryCost']").val();
	var orderInfoquoteCutCost = $("input[name='orderInfoquoteCutCost']").val();
	var orderInfoquoteManageCost = $("input[name='orderInfoquoteManageCost']").val();
	var orderInfoquoteSwingCost = $("input[name='orderInfoquoteSwingCost']").val();
	var orderInfoquoteIroningCost = $("input[name='orderInfoquoteIroningCost']").val();
	var orderInfoquoteNailCost = $("input[name='orderInfoquoteNailCost']").val();
	var orderInfoquotePackageCost = $("input[name='orderInfoquotePackageCost']").val();
	var orderInfoquoteOtherCost = $("input[name='orderInfoquoteOtherCost']").val();
	var orderInfoquoteSingleCost = $("input[name='orderInfoquoteSingleCost']").val();
	var orderInfoquoteInnerPrice = $("input[name='orderInfoquoteInnerPrice']").val();

 	if(merge_w==null){

		$("input[name='single_cost']").val(orderInfoquoteSingleCost);
		$("input[name='inner_price']").val(orderInfoquoteInnerPrice);

	}else{
		
		var number1 = accAdd(orderInfoquoteFabricCost,orderInfoquoteAccessoryCost);
		var number2 = accAdd(number1,orderInfoquoteCutCost);
		var number3 = accAdd(number2,orderInfoquoteManageCost);
		var number4 = accAdd(number3,orderInfoquoteSwingCost);
		var number5 = accAdd(number4,orderInfoquoteIroningCost);
		var number6 = accAdd(number5,orderInfoquoteNailCost);
        var number7 = accAdd(number6,orderInfoquotePackageCost);
        var number = accAdd(number7,orderInfoquoteOtherCost);
//        var number = orderInfoquoteFabricCost + orderInfoquoteAccessoryCost + orderInfoquoteCutCost + orderInfoquoteManageCost +
//        orderInfoquoteSwingCost + orderInfoquoteIroningCost +orderInfoquoteNailCost + orderInfoquotePackageCost +
//        orderInfoquoteOtherCost + 0.0;
//        alert(number);
        $("input[name='single_cost']").val(number.toFixed(2));
		$("input[name='inner_price']").val(number.toFixed(2));
	}
	
	$("input[name='profitPerPiece']").keyup(function(){
		var s_profit = $("input[name='profitPerPiece']").val();
		var s_inner = $("input[name='inner_price']").val();
		if(s_inner == ""){
			$("input[name='outer_price']").val("");
			return;
		}else{
			var inner_price = parseFloat(s_inner);
			if(s_profit == "" && !isNaN(inner_price)){
				$("input[name='outer_price']").val(inner_price);
			}else if(s_profit != ""){
				var profit = parseFloat(s_profit);
				if(!(isNaN(inner_price)||isNaN(profit))){
					$("input[name='outer_price']").val((inner_price+profit).toFixed(2));
				}
			}
		}
	});
	
})(jQuery);

function calculate(){
	var s_inner = $("input[name='inner_price']").val();
	var s_profit = $("input[name='inner_price']").val();
	
}

function checkNum(input){
	var re = /^[0-9]+.?[0-9]*$/; 
	return re.test(input);
}

function quote_verify(){
	var s_inner = $("input[name='inner_price']").val();
	var s_profit = $("input[name='profitPerPiece']").val();
	var s_cost = $("input[name='single_cost']").val();
	var s_outer = $("input[name='outer_price']").val();
	
	if(checkNum(s_inner) && checkNum(s_profit) && checkNum(s_cost) && checkNum(s_outer)){
		return true;
	}else{
		alert("请填写数字");
		return false;
	}
}

function accAdd(arg1,arg2){ 
	var r1,r2,m; 
	try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0} 
	try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0} 
	m=Math.pow(10,Math.max(r1,r2)) 
	return (arg1*m+arg2*m)/m 
} 
