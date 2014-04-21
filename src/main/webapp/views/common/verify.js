jQuery(document).ready(function(){
	jQuery("#agree").click(function(){
		if(confirm("确认操作？")){
			jQuery("#verify_val").val("true");
			jQuery("#verify_form").submit();
		}
	});
	
	jQuery("#disagree").click(function(){
		var suggestion = $("form.verify textarea").val();
		if (suggestion == "") {
			alert("拒绝意见不能为空");
			return;
		}
		if(confirm("确认操作？")){
			jQuery("#verify_val").val("false");
			jQuery("#verify_form").submit();
		}
	});
});