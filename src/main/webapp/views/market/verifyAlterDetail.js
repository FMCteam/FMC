jQuery(document).ready(function(){
	
	jQuery("#changeStaff").hide();
	jQuery("#agree").click(function(){
		jQuery("#changeStaff").show();
		
		
	});
	jQuery("#agreeChange").click(function(){
		if(confirm("确认操作？")){
			jQuery("#verifyAlterSuccess_val").val("true");
			jQuery("#verifyState_val").val("Agree");
			jQuery("#verifyAlterDetailForm").submit();
		}
		
	});
	
	jQuery("#disagree").click(function(){
		/*var suggestion = $("form.verify textarea").val();*/
		var suggestion =$("#suggestion_v").val();
		//alert("suggestion_v2:"+$("#suggestion_v").val());
		
		
		if (suggestion == "") {
			alert("拒绝意见不能为空");
			return;
		}
		if(confirm("确认操作？")){
			jQuery("#verifyAlterSuccess_val").val("false");
			jQuery("#verifyState_val").val("Disagree");
			jQuery("#verifyAlterDetailForm").submit();
		}
	});
});


 