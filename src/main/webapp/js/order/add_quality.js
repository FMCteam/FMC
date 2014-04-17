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
	$("#good_color").val(getInputString("good_color"));
	$("#good_xs").val(getInputString("good_xs"));
	$("#good_s").val(getInputString("good_s"));
	$("#good_m").val(getInputString("good_m"));
	$("#good_l").val(getInputString("good_l"));
	$("#good_xl").val(getInputString("good_xl"));
	$("#good_xxl").val(getInputString("good_xxl"));
	
	$("#bad_color").val(getInputString("bad_color"));
	$("#bad_xs").val(getInputString("bad_xs"));
	$("#bad_s").val(getInputString("bad_s"));
	$("#bad_m").val(getInputString("bad_m"));
	$("#bad_l").val(getInputString("bad_l"));
	$("#bad_xl").val(getInputString("bad_xl"));
	$("#bad_xxl").val(getInputString("bad_xxl"));

	return true;
}

function init(){

}