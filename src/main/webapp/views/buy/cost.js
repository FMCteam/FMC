jQuery(document).ready(function(){
	jQuery("div#quote form").submit(function(){
		var unqualified=false;
		jQuery("input:text").each(function(index){ 
			unqualified=unqualified||isNaN(this.value);
			unqualified=unqualified||(this.value=="");
		});
		if(!unqualified){
			return confirm("确认操作？");
		}else{
			alert("报价必须填写并且是数字哦~喵");
			return false;
		}
	});
});