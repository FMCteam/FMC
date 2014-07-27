jQuery(document).ready(function(){
	jQuery("#agree").click(function(){
		if(confirm("确认操作？")){
			jQuery("#verifyQuoteSuccess_val").val("true");
			jQuery("#verifyQuoteDetailForm").submit();
		}
	});
	
	jQuery("#disagree").click(function(){
		var suggestion = $("form.verify textarea").val();
		if (suggestion == "") {
			alert("拒绝意见不能为空");
			return;
		}
		if(confirm("确认操作？")){
			jQuery("#verifyQuoteSuccess_val").val("false");
			jQuery("#verifyQuoteDetailForm").submit();
		}
	});
});