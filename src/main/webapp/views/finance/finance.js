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
			|| money_amount == "" || isNaN(money_amount)) {
		//alert("请填入正确数据内容");
		noty({
			text : '请填入正确数据内容',
			layout : 'topCenter',
			timeout : 2000
		});
		return false;
	}
	return confirmFinanceSubmit();
}