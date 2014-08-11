// JavaScript Document

function getInputString(col){
	var inputString="";
	var i=0;
	for(;i<$("input."+col).length-1;i++){
		inputString+=$("input."+col).eq(i).val()+",";
	}
	inputString+=$("input."+col).eq(i).val();
//	alert(inputString);
	return inputString;
}


function getQuality(){
	var repairAmount = $("input[name='repair_number']").val();
	var repairTime = $("input[name='repair_time']").val();
	if((repairAmount != 0 && repairAmount != "") && (repairTime == "")){
		alert("请填写回修日期");
		return false;
	}
	
	$("#good_color").val(getInputString("good_color"));
	$("#good_xs").val(getInputString("good_xs"));
	$("#good_s").val(getInputString("good_s"));
	$("#good_m").val(getInputString("good_m"));
	$("#good_l").val(getInputString("good_l"));
	$("#good_xl").val(getInputString("good_xl"));
	$("#good_xxl").val(getInputString("good_xxl"));
	
	/*$("#bad_color").val(getInputString("bad_color"));
	$("#bad_xs").val(getInputString("bad_xs"));
	$("#bad_s").val(getInputString("bad_s"));
	$("#bad_m").val(getInputString("bad_m"));
	$("#bad_l").val(getInputString("bad_l"));
	$("#bad_xl").val(getInputString("bad_xl"));
	$("#bad_xxl").val(getInputString("bad_xxl"));*/
	
/*	alert($("#good_color").val());
	alert($("#good_xs").val());
	alert($("#good_s").val());
	alert($("#good_m").val());
	alert($("#good_l").val());
	alert($("#good_xl").val());
	alert($("#good_xxl").val());
	alert($("#bad_color").val());
	alert($("#bad_xs").val());
	alert($("#bad_s").val());
	alert($("#bad_m").val());
	alert($("#bad_l").val());
	alert($("#bad_xl").val());
	alert($("#bad_xxl").val());*/

	return confirm('确认提交？');
}

//根据衣服型号计算质检数量
function getCheckNumberViaSize(col){
	var checkNumber = 0;
	var i = 0;
	var temp = 0;
	for (; i < $("input." + col).length; i++) {
		temp = Number($("input." + col).eq(i).val());
		if(isNaN(temp)){
			alert("质检数量必须为数字");
			return;
		}
		checkNumber += temp;
	}
	// alert(checkNumber);
	return checkNumber;
}

//计算回修数量（不合格总数）
function computeRepairAmount(){
	var xs = getCheckNumberViaSize("bad_xs");
	var s = getCheckNumberViaSize("bad_s");
	var m = getCheckNumberViaSize("bad_m");
	var l = getCheckNumberViaSize("bad_l");
	var xl = getCheckNumberViaSize("bad_xl");
	var xxl = getCheckNumberViaSize("bad_xxl");
	
	if(isNaN(xs) || isNaN(s) || isNaN(m) || isNaN(l) || isNaN(xl) || isNaN(xxl)){
		alert("不合格数量必须为数字");
		return;
	}
		
	var repairAmount = xs + s + m + l + xl + xxl;
	$("input[name='repair_number']").val(repairAmount);
	
}

function init(){

}