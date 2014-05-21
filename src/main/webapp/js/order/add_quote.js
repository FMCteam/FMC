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
					$("input[name='outer_price']").val(inner_price+profit);
				}
			}
		}
	});
	
})(jQuery);

function calculate(){
	var s_inner = $("input[name='inner_price']").val();
	var s_profit = $("input[name='inner_price']").val();
	
}