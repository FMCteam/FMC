// JavaScript Document

(function($) {
	
	$(function(){
		//$.log("添加事件");
		$("table.fabric_table a").click(function(){
			table_addrow_onclick("fabric_table","fabric_name","fabric_amount");
		});

		$("table.accessory_table a").click(function(){
			table_addrow_onclick("accessory_table","accessory_name","accessory_query");
		});
		
		init();
	});


	function table_addrow_onclick(table_name,col1_name,col2_name){
		    //检查第一列是否为空
			var col1=$("table."+table_name+" tr.addrow input").eq(0).val();
			if(col1==""){
				alert("不能为空");
				return;
			}
			
			//检查第二列是否为空
			var col2=$("table."+table_name+" tr.addrow input").eq(1).val();
			if(col2==""){
				alert("不能为空");
				return;
			}
			
			//清空第一二列
			$("table."+table_name+" tr.addrow input").eq(0).val("");
			$("table."+table_name+" tr.addrow input").eq(1).val("");
			
			
			//添加行
			var item="<td class='span12 "+col1_name+"'>"+col1+"</td>";
			item+="<td class='span12 "+col2_name+"'>"+col2+"</td>";
			item+="<td class='span12'><a onclick=\"deleteRow(this,'"+table_name+"')\">删除</a></td>";
			item="<tr>"+item+"</tr>";
			$("table."+table_name+" tr.addrow").after(item);
	}


	
})(jQuery);

function deleteRow(a,table){
	alert($(a).parents('.'+table+' tr').length);
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


function verify(){
	//alert(getTdString("fabric_name"));
	//alert(getTdString("fabric_amount"));
	//alert(getTdString("accessory_name"));
	//alert(getTdString("accessory_query"));
	//$("#fabric_name").val(getTdString("fabric_name"));
	//$("#fabric_amount").val(getTdString("fabric_amount"));
	//$("#accessory_name").val(getTdString("accessory_name"));
	//$("#accessory_query").val(getTdString("accessory_query"));

	
/*	var employee_id=jQuery("select[name='employee_id']").val();
	var order_source=jQuery("input[name='order_source']").val();
	var customer_id=jQuery("select[name='customer_id']").val();
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

	
	return true;
}

function init(){
	$("#fabric_name").val("name1,name2,name3");
	$("#fabric_amount").val("11,12,13");
	$("#accessory_name").val("name1,name2,name3");
	$("#accessory_query").val("name1,name2,name3");
	
	
	
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