jQuery(document).ready(function(){
	jQuery("div#quote form").submit(function(){
		
		
		
		
		if(checkIsNumber("fabric_amount")&&checkIsNumber("tear_per_meter")
				&&checkIsNumber("cost_per_meter")&&checkIsNumber("tear_per_piece")
				&&checkIsNumber("cost_per_piece")){
				return confirm("确认操作？");
			}else{
				alert("面辅所有信息必须填写，其中面料克重、单件米耗、每米价格、单件耗数、辅料单价必须是数字");
				return false;
			}
		
		
		
		function checkIsNumber(name){
			return !isNaN($("input[name='"+name+"']").val())&&($("input[name='"+name+"']").val()!="");
		}
		
		
		
	
		
		
		
	});
});