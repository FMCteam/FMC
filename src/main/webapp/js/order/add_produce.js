// JavaScript Document

function getInputString(col){
	var inputString="";
	for(var i=0;i<$("input."+col).length-1;i++){
		inputString+=$("input."+col).eq(i).val()+",";
	}
	inputString+=$("input."+col).eq(i).val();
//	alert(inputString);
	return inputString;
}


function getProduce(){
	$("#produce_color").val(getInputString("produce_color"));
	$("#produce_xs").val(getInputString("produce_xs"));
	$("#produce_s").val(getInputString("produce_s"));
	$("#produce_m").val(getInputString("produce_m"));
	$("#produce_l").val(getInputString("produce_l"));
	$("#produce_xl").val(getInputString("produce_xl"));
	$("#produce_xxl").val(getInputString("produce_xxl"));
	$("#produce_j").val(getInputString("produce_j"));
}

function init(){

}
