function confirmReceiveSampleSubmit() {
	return confirm("确认操作？");
}

function confirmSendSampleSubmit() {
	if(isNull("time")||isNull("name")||isNull("number")){
		alert("请填写完整快递信息~");
		return false;
	}else{
		return confirm("确认操作？");
	}
}

function isNull(name){
	return $("input[name='"+name+"']").val()=="";
}
