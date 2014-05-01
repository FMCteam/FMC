// JavaScript Document

(function($) {
	
	$(function(){
		//$.log("添加事件");
		$("table.fabric_table a").click(function(){
			var colName = ["fabric_name","tear_per_meter","cost_per_meter"];
			table_addrow_onclick("fabric_table",colName,3);
		});

		$("table.accessory_table a").click(function(){
			var colName = ["accessory_name","tear_per_piece","cost_per_piece"];
			table_addrow_onclick("accessory_table",colName,3);
		});
		
	


		init();
		
		
	});


	function table_addrow_onclick(table_name,col_name,col_sum){
//		    //检查第一列是否为空
//			var col1=$("table."+table_name+" tr.addrow input").eq(0).val();
//			if(col1==""){
//				alert("不能为空");
//				return;
//			}
//			
//			//检查第二列是否为空
//			var col2=$("table."+table_name+" tr.addrow input").eq(1).val();
//			if(col2==""){
//				alert("不能为空");
//				return;
//			}
//			
			var content = new Array();
			for(var i=0;i<col_sum;i++){
				var col=$("table."+table_name+" tr.addrow input").eq(i).val();
				content[i] = col;
				if(col==""){
					alert("不能为空");
					return;
				}
			}
			//清空第一二列
//			$("table."+table_name+" tr.addrow input").eq(0).val("");
//			$("table."+table_name+" tr.addrow input").eq(1).val("");

			//添加行
//			var item="<td class='span12 "+col1_name+"'>"+col1+"</td>";
//			item+="<td class='span12 "+col2_name+"'>"+col2+"</td>";

			var item = "";
			for(var j=0;j<col_sum;j++){
				$("table."+table_name+" tr.addrow input").eq(j).val("");
				item+="<td class='"+col_name[j]+"'>"+content[j]+"</td>";
			}
			item+="<td><a onclick=\"deleteRow(this,'"+table_name+"')\">删除</a></td>";
			item="<tr>"+item+"</tr>";
			$("table."+table_name+" tr.addrow").after(item);
			
			
	}


	
})(jQuery);

function deleteRow(a,table){
	//alert($(a).parents('.'+table+' tr').length);
	$(a).parents('.'+table+' tr').remove();
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




function init(){
	
	$("#produce_color").val("红,黄,绿");
	$("#produce_xs").val("1,2,3");
	$("#produce_s").val("1,2,3");
	$("#produce_m").val("1,2,3");
	$("#produce_l").val("1,2,3");
	$("#produce_xl").val("1,2,3");
	$("#produce_xxl").val("1,2,3");
	$("#sample_produce_color").val("红,黄,绿");
	$("#sample_produce_xs").val("1,1,2");
	$("#sample_produce_s").val("1,1,2");
	$("#sample_produce_m").val("1,1,2");
	$("#sample_produce_l").val("1,1,2");
	$("#sample_produce_xl").val("1,1,2");
	$("#sample_produce_xxl").val("1,1,2");
	$("#version_size").val("21,22,23");
	$("#version_centerBackLength").val("21,22,23");
	$("#version_bust").val("21,22,23");
	$("#version_waistLine").val("21,22,23");
	$("#version_shoulder").val("21,22,23");
	$("#version_buttock").val("21,22,23");
	$("#version_hem").val("21,22,23");
	$("#version_trousers").val("21,22,23");
	$("#version_skirt").val("21,22,23");
	$("#version_sleeves").val("21,22,23");
	
	
	
	
	$("input[name='cut_cost']").val("0");
	$("input[name='manage_cost']").val("0");
	$("input[name='nail_cost']").val("0");
	$("input[name='ironing_cost']").val("0");
	
	
	$("input[name='swing_cost']").val("0");
	$("input[name='package_cost']").val("0");
	$("input[name='other_cost']").val("0");
	$("input[name='design_cost']").val("0");
	
	
	
	$("input[name='style_name']").val("杀马特3000");
	$("input[name='order_source']").val("杀马特3000");
	$("input[name='ask_amount']").val("20");
	$("input[name='ask_produce_period']").val("10");
	$("input[name='ask_deliver_date']").val("2012-12-12");
	
	
	$("input[name='in_post_sample_clothes_time']").val("2012-12-12");
	$("input[name='in_post_sample_clothes_type']").val("顺丰");
	$("input[name='in_post_sample_clothes_number']").val("435435345435345");
	
	
	$("input[name='sample_clothes_time']").val("2012-12-12");
	$("input[name='sample_clothes_type']").val("申通");
	$("input[name='sample_clothes_number']").val("657567567567567567");
	$("input[name='sample_clothes_name']").val("黑撒旦躬");
	$("input[name='sample_clothes_phone']").val("1599546546");
	$("input[name='sample_clothes_address']").val("江苏南京");
	$("input[name='sample_clothes_remark']").val("ggggggggggggggggggg");
	
}