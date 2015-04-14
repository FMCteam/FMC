jQuery(document).ready(function(){
	
	jQuery("#changeStaff").hide();
	jQuery("#agree").click(function(){
		jQuery("#changeStaff").show();
		alert("suggestion:");
		/*alert("suggestion:"+1);
		alert("suggestion:"+$("#suggestion_v").text());
		alert("suggestion:"+$("#suggestion_v").val());
		alert("suggestion:"+$("#suggestion_v").html());*/
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
		//var suggestion =$("#suggestion_v").text();
		alert("suggestion_v:");
	/*	alert("suggestion_v:"+1);
		alert("suggestion_v:"+$("#suggestion_v").text());
		alert("suggestion_v2:"+$("#suggestion_v").val());
		alert("suggestion_v3:"+$("#suggestion_v").html());*/
		if (suggestion == "") {
			//alert("拒绝意见不能为空");
			return;
		}
		if(confirm("确认操作？")){
			jQuery("#verifyAlterSuccess_val").val("false");
			jQuery("#verifyState_val").val("Disagree");
			jQuery("#verifyAlterDetailForm").submit();
		}
	});
});


 