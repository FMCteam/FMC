(function($) {
	$(function() {
		$("table.package a").click(function() {
			var col_name = [ "color", "size", "number" ];
			table_addrow_onclick("package", col_name, 3);
		});

	});
})(jQuery);


function table_addrow_onclick(table_name, col_name, col_sum) {

	var content = new Array();
	for (var i = 0; i < col_sum; i++) {
		var col = $("table." + table_name + " tr.addrow input").eq(i).val();
		content[i] = col;
		if (col == "") {
			alert("请正确填写数据");
			return;
		}
	}
	var item = "";
	for (var j = 0; j < col_sum; j++) {
		$("table." + table_name + " tr.addrow input").eq(j).val("");
		item += "<td class='span12 " + col_name[j] + "'>" + content[j]
				+ "</td>";
	}
	item += "<td class='span12'><a onclick=\"deleteRow(this,'" + table_name
			+ "')\">删除</a></td>";
	item = "<tr>" + item + "</tr>";
	$("table." + table_name + " tr.addrow").after(item);
}



function dealString(){
	$("input[name='color']").val(getTdString("color"));
	$("input[name='size']").val(getTdString("size"));
	$("input[name='number']").val(getTdString("number"));
	if($("input[name='color']").val()==""){
		alert("请先输入正确数据在点击添加箱号~");
		return false;
	}else{
		return true;
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
