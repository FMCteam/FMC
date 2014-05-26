jQuery(document).ready(function(){
	jQuery("div#quote form").submit(function(){
		
		
		
		 if((FabricName== "")||(FabricAmount== "")||(TearPerMeter== "")||(CostPerMeter== "")||(AccessoryName== "")
				 ||(AccessoryQuery== "")||(TearPerPiece== "")||(CostPerPiece== "")){
			 
			alert("请将面辅信息填写完整");
			  return false;
			
		 }
			
		 else {
			 
			 return confirm("确认操作？");
		 }
	
		
		
		
	});
});