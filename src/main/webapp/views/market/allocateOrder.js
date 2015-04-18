jQuery(document).ready(function(){
	

	jQuery("#agreeChange").click(function(){
		if(confirm("确认操作？")){
			//jQuery("#verifyAlterSuccess_val").val("agree");
			jQuery("#AllocateOrderForm").submit();
		}
		
	});
	
});


 