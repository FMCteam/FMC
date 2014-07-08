//JavaScript document

(function($) {
    var orderInfoArraySize = $("input[name='orderInfoArraySize']").val();
    /*
    while(orderInfoArraySize>0){
    	
    	
    	
    	orderInfoArraySize--;
    }
     */
	var fabricCostTearPerMeter = $("input[name='fabricCostTearPerMeter']").val();
	alert(fabricCostTearPerMeter);
	var orderInfoOrderAskAmount = $("input[name='orderInfoOrderAskAmount']").val();
	var allpurchasemeters = fabricCostTearPerMeter * orderInfoOrderAskAmount;
	$("input[name='allpurchasemeters']").val(allpurchasemeters.toFixed(2));

	var accessoryCostTearPerPiece = $("input[name='accessoryCostTearPerPiece']").val();
	var orderInfoOrderAskAmount2 = $("input[name='orderInfoOrderAskAmount2']").val();
	var allpurchasepieces = accessoryCostTearPerPiece * orderInfoOrderAskAmount2;
	$("input[name='allpurchasepieces']").val(allpurchasepieces.toFixed(2));
})(jQuery);
 
function accAdd(arg1,arg2){ 
	var r1,r2,m; 
	try{r1=arg1.toString().split(".")[1].length;}catch(e){r1=0;} 
	try{r2=arg2.toString().split(".")[1].length;}catch(e){r2=0;} 
	m=Math.pow(10,Math.max(r1,r2)); 
	return (arg1*m+arg2*m)/m; 
} 
