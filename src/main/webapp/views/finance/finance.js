function confirmFinanceSubmit() {
	return confirm("确认操作？");
}

function verifyFinance() {
	var money_name = $("input[name='money_name']").val();
	var money_number = $("input[name='money_number']").val();
	var money_bank = $("input[name='money_bank']").val();
	var money_amount = $("input[name='money_amount']").val();
	var money_remark = $("input[name='money_remark']").val();
	if (money_name == "" || money_number == "" || money_bank == ""
			|| money_amount == "" || money_remark == "") {
		alert("数据不能为空");
		return false;
	}
	return confirmFinanceSubmit();
}