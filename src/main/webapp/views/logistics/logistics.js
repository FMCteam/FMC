function confirmReceiveSampleSubmit() {
	return confirm("确认操作？");
}

function confirmSendSampleSubmit() {
//	if(isNull("time")||isNull("name")||isNull("number")){
//		alert("请填写完整快递信息~");
//		return false;
//	}else{
		return confirm("确认操作？");
//	}
}

function isNull(name){
	return $("input[name='"+name+"']").val()=="";
}


function confirmPackageSubmit(){
	if($("table#pack tr").length==1){
		alert("至少输入一箱数据~");
		return false;
	}else{
		return confirm("确认操作？");
	}
}

$(document).ready(function() {
 var text=$("#pay").text();
	$("#pay").text(parseFloat(text).toFixed(2));
 var text=$("#pay2").text();
	$("#pay2").text(parseFloat(text).toFixed(2));
	 
});  