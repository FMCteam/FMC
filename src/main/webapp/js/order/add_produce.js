// JavaScript Document

function getInputString(col){
	var inputString="";
	var i=0;
	for(;i<$("input."+col).length-1;i++){
		inputString+=$("input."+col).eq(i).val()+",";
	}
	inputString+=$("input."+col).eq(i).val();
	return inputString;
}


function verify(){
	$("#produce_color").val(getInputString("produce_color"));
	$("#produce_xs").val(getInputString("produce_xs"));
	$("#produce_s").val(getInputString("produce_s"));
	$("#produce_m").val(getInputString("produce_m"));
	$("#produce_l").val(getInputString("produce_l"));
	$("#produce_xl").val(getInputString("produce_xl"));
	$("#produce_xxl").val(getInputString("produce_xxl"));

	return true;
}

function init(){

}