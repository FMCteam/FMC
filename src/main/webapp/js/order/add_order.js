// JavaScript Document

(function($) {
	
	$(function(){
		//$.log("添加事件");
		$("table.fabric_table a").click(function(){
			var colName = ["fabric_name","fabric_amount"];
			table_addrow_onclick("fabric_table",colName,2);
		});
		$("table.fabric_cost_table a").click(function(){
			var colName = ["fabric_cost_name","cost_per_meter","tearpermeter"];
			table_addrow_onclick2("fabric_cost_table",colName,3);
		});		
		$("table.accessory_table a").click(function(){
			var colName = ["accessory_name","accessory_query"];
			table_addrow_onclick("accessory_table",colName,2);
		});
		$("table.accessory_cost_table a").click(function(){
			var colName = ["accessory_cost_name","costperpiece","tearperpiece"];
			table_addrow_onclick2("accessory_cost_table",colName,3);
		});	
		//大货加工
		$("table.produce_table a").click(function(){
			var colName = ["produce_color","produce_xs","produce_s","produce_m","produce_l","produce_xl","produce_xxl"];
			if(checkNum("produce_table",colName,7)){
				table_addrow_onclick("produce_table",colName,7);
				$("input[name='ask_amount']").val(calculate("produce_table",colName,7));
				for(var i=0;i<$("td.ask_amount").length;i++){
					$("td.ask_amount").eq(i).text(calculate("produce_table",colName,7));
				}
			}
		});
		//样衣加工
		$("table.sample_produce_table a").click(function(){
			var colName = ["sample_produce_color","sample_produce_xs","sample_produce_s","sample_produce_m","sample_produce_l","sample_produce_xl","sample_produce_xxl"];
			if(checkNum("sample_produce_table",colName,7)){
				table_addrow_onclick("sample_produce_table",colName,7);
				$("input[name='sample_amount']").val(calculate("produce_table",colName,7));
			}
		});
		
		$("table.version_table a").click(function(){
			var colName = ["version_size","version_centerBackLength","version_bust","version_waistLine","version_shoulder",
			               "version_buttock","version_hem","version_trousers","version_skirt","version_sleeves"];
			if(checkFloat("version_table",colName,10)){
				table_addrow_onclick("version_table",colName,10);
			}
		});
		
		$("#sample_clothes_picture_button").click(function(){
			$("input[name='sample_clothes_picture']").click();
		});
		
		
		$("#reference_picture_button").click(function(){
			$("input[name='reference_picture']").click();
		});
		
		$("input[name='has_posted_sample_clothes']").change(function(){
			if($('input:radio[name="has_posted_sample_clothes"]:checked').val()=="0"){
				$("input[name='in_post_sample_clothes_time']").attr("disabled","disabled");
				$("select[name='in_post_sample_clothes_type']").attr("disabled","disabled");
				$("input[name='in_post_sample_clothes_number']").attr("disabled","disabled");
			}else{
				$("input[name='in_post_sample_clothes_time']").removeAttr("disabled");
				$("select[name='in_post_sample_clothes_type']").removeAttr("disabled");
				$("input[name='in_post_sample_clothes_number']").removeAttr("disabled");
			}
		});

		$("input[name='ask_deliver_date']").change(function(){ 
			var date = new Date();
			var input_date = new Date($("input[name='ask_deliver_date']").val());
			if(input_date.valueOf()<date.valueOf()){
				alert("输入日期不能在今天之前");
				$("input[name='ask_deliver_date']").val("");
				return;
			}
			var max = parseInt(Math.abs((input_date - date)/86400000));
			$("input[name='ask_produce_period']").attr("max",""+parseInt(max));
		});
		/*$("input[name='is_need_sample_clothes']").change(function(){
			if($('input:radio[name="is_need_sample_clothes"]:checked').val()=="0"){
				$("input[name='sample_clothes_name']").attr("disabled","disabled");
				$("input[name='sample_clothes_phone']").attr("disabled","disabled");
				$("input[name='sample_clothes_address']").attr("disabled","disabled");
				$("input[name='sample_clothes_remark']").attr("disabled","disabled");
			}else{
				$("input[name='sample_clothes_name']").removeAttr("disabled");
				$("input[name='sample_clothes_phone']").removeAttr("disabled");
				$("input[name='sample_clothes_address']").removeAttr("disabled");
				$("input[name='sample_clothes_remark']").removeAttr("disabled");
			}
		});*/

		init();
		
		
	});

	function table_addrow_onclick(table_name,col_name,col_sum){	
		var content = new Array();
		for (var i = 0; i < col_sum; i++) {
			var col = $("table." + table_name + " tr.addrow input").eq(i).val();
			content[i] = col;
			if (col == "") {
				alert("请把信息填写完整");
				return;
			}
		}

		var item = "";
		//如果是添加大货和样衣数量，需要设置默认值0
		if (table_name == "produce_table" || table_name == "sample_produce_table") {
			for (var j = 0; j < col_sum; j++) {
				//第1列是颜色，不需要置为0
				if(j == 0){
					$("table." + table_name + " tr.addrow input").eq(j).val("");
				}else{
					$("table." + table_name + " tr.addrow input").eq(j).val("0");
				}
				item += "<td class='" + col_name[j] + "'>" + content[j] + "</td>";
			}
		}else{
			for (var j = 0; j < col_sum; j++) {
				$("table." + table_name + " tr.addrow input").eq(j).val("");
				item += "<td class='" + col_name[j] + "'>" + content[j] + "</td>";
			}
		}

		item += "<td><a onclick=\"deleteRow(this,'" + table_name + "')\">删除</a></td>";
		item = "<tr>" + item + "</tr>";
		$("table." + table_name + " tr.addrow").after(item);
			
	}
	
	function table_addrow_onclick2(table_name,col_name,col_sum){
		
		var content = new Array();
		for(var i=0;i<col_sum;i++){
			var col=$("table."+table_name+" tr.addrow input").eq(i).val();
			content[i] = col;
			if(col==""){
				alert("请把信息填写完整");
				return;
			}
		}
		

		var item = "";
		for(var j=0;j<col_sum;j++){
			if(j==0){
				$("table."+table_name+" tr.addrow input").eq(j).val("");
				item+="<td class='"+col_name[j]+"'>"+
				"<input class=\"span12\" name=  \""+col_name[j]+"\" type=\"text\" value="+content[j]+ " required=\"required\"  readonly=\"readonly\"/>"+"</td>";

			}else{
				$("table."+table_name+" tr.addrow input").eq(j).val("");
				item+="<td class='"+col_name[j]+"'>"+
				"<input class=\"span12\" name= \""+col_name[j]+"\" type=\"text\" value="+content[j]+ " required=\"required\" />"+"</td>";

			}
			
/*			
			$("table."+table_name+" tr.addrow input").eq(j).val("");
			item+="<td class='"+col_name[j]+"'>"+
			"<input class=\"span12\" name="+col_name[j]+" type=\"text\" value="+content[j]+ " required=\"required\"  readonly=\"readonly\"/>"+"</td>";
			*/
		}
		item+="<td><a onclick=\"deleteRow(this,'"+table_name+"')\">删除</a></td>";
		item="<tr>"+item+"</tr>";
		$("table."+table_name+" tr.addrow").after(item);
		
}
	
})(jQuery);

function checkNum(table_name, col_name, col_sum) {
	var totalNum = 0;
	for (var i = 0; i < col_sum; i++) {
		var col = $("table." + table_name + " tr.addrow input").eq(i).val();
		
		if (isNaN(parseInt(col))) {
			if (i > 0) {
				alert("请正确填写数量");
				return false;
			}
		} else {
			if (i > 0) {
				totalNum += parseInt(col);
			}
		}
	}
	
	if (totalNum > 0){
		return true;
	}else{
		alert("总数必须大于0，请重新填写");
		return false;
	}
}

function checkFloat(table_name,col_name,col_sum){
	for(var i=0;i<col_sum;i++){
		var col=$("table."+table_name+" tr.addrow input").eq(i).val();
		if(isNaN(parseFloat(col))){
			if(i>0){
				alert("请正确填写数量");
				return false;
			}
		}
	}
	return true;
}

function calculate(table_name,col_name,col_sum){
	var sum = 0;
	for(var i = 1; i < col_sum; i++){
		for(var j = 0; j < $("td."+col_name[i]).length; j++){
			sum += parseInt($("td."+col_name[i]).eq(j).text());
		}
	}
	return sum;
}

function deleteRow(a,table){
	//alert($(a).parents('.'+table+' tr').length);
	$(a).parents('.'+table+' tr').remove();
	if(table=="produce_table"){
		var colName = ["produce_color","produce_xs","produce_s","produce_m","produce_l","produce_xl","produce_xxl"];
		$("input[name='ask_amount']").val(calculate("produce_table",colName,7));
	}
}

function getTdString(col){
	var tdString="";
	var i=0;
	for(;i<$("td."+col).length-1;i++){
		tdString+=$("td."+col).eq(i).text()+",";
	}
	tdString+=$("td."+col).eq(i).text();
	return tdString;
}


function verify(){
	var ask_amount = jQuery("input[name='ask_amount']").val();
	if(ask_amount == "" || ask_amount <= 0){
		alert("大货总数必须大于0");
		return false;
	}
	
	var is_need_sample = jQuery("input[name='is_need_sample_clothes']").val();
	//如果需要样衣
	if(is_need_sample == 1){
		var sample_amount = jQuery("input[name='sample_amount']").val();
		if(sample_amount == "" || sample_amount <= 0){
			alert("请填写样衣数量");
			return false;
		}
	}
	
	
	$("#fabric_name").val(getTdString("fabric_name"));
	$("#fabric_amount").val(getTdString("fabric_amount"));
	$("#accessory_name").val(getTdString("accessory_name"));
	$("#accessory_query").val(getTdString("accessory_query"));
	$("#produce_color").val(getTdString("produce_color"));
	$("#produce_xs").val(getTdString("produce_xs"));
	$("#produce_s").val(getTdString("produce_s"));
	$("#produce_m").val(getTdString("produce_m"));
	$("#produce_l").val(getTdString("produce_l"));
	$("#produce_xl").val(getTdString("produce_xl"));
	$("#produce_xxl").val(getTdString("produce_xxl"));
	$("#sample_produce_color").val(getTdString("sample_produce_color"));
	$("#sample_produce_xs").val(getTdString("sample_produce_xs"));
	$("#sample_produce_s").val(getTdString("sample_produce_s"));
	$("#sample_produce_m").val(getTdString("sample_produce_m"));
	$("#sample_produce_l").val(getTdString("sample_produce_l"));
	$("#sample_produce_xl").val(getTdString("sample_produce_xl"));
	$("#sample_produce_xxl").val(getTdString("sample_produce_xxl"));
	$("#version_size").val(getTdString("version_size"));
	$("#version_centerBackLength").val(getTdString("version_centerBackLength"));
	$("#version_bust").val(getTdString("version_bust"));
	$("#version_waistLine").val(getTdString("version_waistLine"));
	$("#version_shoulder").val(getTdString("version_shoulder"));
	$("#version_buttock").val(getTdString("version_buttock"));
	$("#version_hem").val(getTdString("version_hem"));
	$("#version_trousers").val(getTdString("version_trousers"));
	$("#version_skirt").val(getTdString("version_skirt"));
	$("#version_sleeves").val(getTdString("version_sleeves"));
	
/*	var employee_id=jQuery("input[name='employee_id']").val();
	var order_source=jQuery("input[name='order_source']").val();
	var customer_id=jQuery("input[name='customer_id']").val();
	var style_name=jQuery("input[name='style_name']").val();
	var ask_amount=jQuery("input[name='ask_amount']").val();
	var ask_deliver_date=jQuery("input[name='ask_deliver_date']").val();
	var ask_produce_period=jQuery("input[name='ask_produce_period']").val();
	var hint="employee_id:"+employee_id+"\n";
	hint+="order_source:"+order_source+"\n";
	hint+="customer_id:"+customer_id+"\n";
	hint+="style_name:"+style_name+"\n";
	hint+="ask_amount:"+ask_amount+"\n";
	hint+="ask_deliver_date:"+ask_deliver_date+"\n";
	hint+="ask_produce_period:"+ask_produce_period+"\n";
	alert(hint);*/

	$("button.norepeat").attr("disabled","disabled");
	return true;
}

function init(){
//	$("#fabric_name").val("name1,name2,name3");
//	$("#fabric_amount").val("11,12,13");
//	$("#accessory_name").val("name1,name2,name3");
//	$("#accessory_query").val("name1,name2,name3");
//	$("#produce_color").val("红,黄,绿");
//	$("#produce_xs").val("1,2,3");
//	$("#produce_s").val("1,2,3");
//	$("#produce_m").val("1,2,3");
//	$("#produce_l").val("1,2,3");
//	$("#produce_xl").val("1,2,3");
//	$("#produce_xxl").val("1,2,3");
//	$("#sample_produce_color").val("红,黄,绿");
//	$("#sample_produce_xs").val("1,1,2");
//	$("#sample_produce_s").val("1,1,2");
//	$("#sample_produce_m").val("1,1,2");
//	$("#sample_produce_l").val("1,1,2");
//	$("#sample_produce_xl").val("1,1,2");
//	$("#sample_produce_xxl").val("1,1,2");
//	$("#version_size").val("21,22,23");
//	$("#version_centerBackLength").val("21,22,23");
//	$("#version_bust").val("21,22,23");
//	$("#version_waistLine").val("21,22,23");
//	$("#version_shoulder").val("21,22,23");
//	$("#version_buttock").val("21,22,23");
//	$("#version_hem").val("21,22,23");
//	$("#version_trousers").val("21,22,23");
//	$("#version_skirt").val("21,22,23");
//	$("#version_sleeves").val("21,22,23");
//	
//	$("input[name='tear_per_meter']").val("1");
//	$("input[name='cost_per_meter']").val("2");
//	$("input[name='fabric_price']").val("3");
//	
//	$("input[name='tear_per_piece']").val("4");
//	$("input[name='cost_per_piece']").val("5");
//	$("input[name='accessory_price']").val("6");
//	
//	
//	$("input[name='cut_cost']").val("0");
//	$("input[name='manage_cost']").val("0");
//	$("input[name='nail_cost']").val("0");
//	$("input[name='ironing_cost']").val("0");
//	
//	
//	$("input[name='swing_cost']").val("0");
//	$("input[name='package_cost']").val("0");
//	$("input[name='other_cost']").val("0");
//	$("input[name='design_cost']").val("0");
//	
//	
//	
//	$("input[name='style_name']").val("");
//	$("input[name='order_source']").val("");
//	$("input[name='ask_amount']").val("20");
//	$("input[name='ask_produce_period']").val("10");
//	$("input[name='ask_deliver_date']").val("");
//
//	
//	
//	
//	$("input[name='in_post_sample_clothes_time']").val();
//	$("input[name='in_post_sample_clothes_type']").val("顺丰");
//	$("input[name='in_post_sample_clothes_number']").val("");
//	
//	$("input[name='sample_clothes_time']").val("");
//	$("input[name='sample_clothes_type']").val("申通");
//	$("input[name='sample_clothes_number']").val("");
//	$("input[name='sample_clothes_name']").val("");
//	$("input[name='sample_clothes_phone']").val("");
//	$("input[name='sample_clothes_address']").val("");
//	$("input[name='sample_clothes_remark']").val("");
//	
	if($('input:radio[name="has_posted_sample_clothes"]:checked').val()=="0"){
		$("input[name='in_post_sample_clothes_time']").attr("disabled","disabled");
		$("select[name='in_post_sample_clothes_type']").attr("disabled","disabled");
		$("input[name='in_post_sample_clothes_number']").attr("disabled","disabled");
	}
	
	/*if($('input:radio[name="is_need_sample_clothes"]:checked').val()=="0"){
		$("input[name='sample_clothes_name']").attr("disabled","disabled");
		$("input[name='sample_clothes_phone']").attr("disabled","disabled");
		$("input[name='sample_clothes_address']").attr("disabled","disabled");
		$("input[name='sample_clothes_remark']").attr("disabled","disabled");
	}*/
}