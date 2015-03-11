function checkForm() {
	if (checkIsNumber("cut_cost") && checkIsNumber("swing_cost")
			&& checkIsNumber("manage_cost") && checkIsNumber("package_cost")
			&& checkIsNumber("nail_cost") && checkIsNumber("other_cost")
			&& checkIsNumber("ironing_cost") && checkIsNumber("design_cost")) {
		return true;
	} else {
		alert("报价必须填写并且是数字");
		return false;
	}
}

function checkIsNumber(name) {
	return !isNaN($("input[name='" + name + "']").val())
			&& ($("input[name='" + name + "']").val() != "");
}

// jQuery(document).ready(function(){
// jQuery("div#quote form").submit(function(){
// if(checkIsNumber("cut_cost")&&checkIsNumber("swing_cost")
// &&checkIsNumber("manage_cost")&&checkIsNumber("package_cost")
// &&checkIsNumber("nail_cost")&&checkIsNumber("other_cost")
// &&checkIsNumber("ironing_cost")&&checkIsNumber("design_cost")){
// return confirm("确认操作？");
// }else{
// alert("报价必须填写并且是数字");
// return false;
// }
// });
//	
//	
// function checkIsNumber(name){
// return
// !isNaN($("input[name='"+name+"']").val())&&($("input[name='"+name+"']").val()!="");
// }
// });
