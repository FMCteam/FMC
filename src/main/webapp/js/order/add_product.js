
// JavaScript Document

(function($) {
	
	$(function(){
		//$.log("添加事件");
		$("table.product_table a").click(function(){
			table_addrow_onclick("product_table","product_amount","product_color","product_style");
		});
		
		$("a.btn-danger").click(function(){
			$("input[name='tof']").val("false");
			$("form").submit();
		});
		
		$("button.btn-primary").click(function(){
			$("input[name='tof']").val("true");
			$("form").submit();
		});
		$("input[name='discount']").keyup(function(){
			var originOld = $("input[name='sum']").val();
			var dis = $("input[name='discount']").val();
			if(!checkDiscountNum(dis)){
				//alert("优惠金额必须是数字");
				$("input[name='discount']").val("0");
				$("input[name='totalmoney']").val(originOld);
				return;
			}
			var origin = parseFloat($("input[name='sum']").val());
			var discount = parseFloat(dis);
			var now = origin - discount;
			now = now.toFixed(2);
 
			if(now<0){
				alert("优惠金额不能大于总金额");
				$("input[name='discount']").val("");
				$("input[name='totalmoney']").val(origin);
				return;
			}else{
				$("input[name='totalmoney']").val(now);
			}
			
		});
//		init();
	});
		


	function table_addrow_onclick(table_name,col1_name,col2_name,col3_name){
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
			
			//检查第三列是否为空
			var col3=$("table."+table_name+" tr.addrow input").eq(2).val();
			if(col3==""){
				alert("不能为空");
				return;
			}
			
			//清空第一二列
			$("table."+table_name+" tr.addrow input").eq(0).val("");
			$("table."+table_name+" tr.addrow input").eq(1).val("");
			$("table."+table_name+" tr.addrow input").eq(2).val("");
			
			
			//添加行
			var item="<td class='span12 "+col1_name+"'>"+col1+"</td>";
			item+="<td class='span12 "+col2_name+"'>"+col2+"</td>";
			item+="<td class='span12 "+col3_name+"'>"+col3+"</td>";
			item+="<td class='span12'><a onclick='deleteRow(this,\""+table_name+"\")'>删除</a></td>";
			item="<tr>"+item+"</tr>";
			$("table."+table_name+" tr.addrow").after(item);
	}

	function deleteRow(a,table){
		$(a).parents('.'+table+' tr').remove();
	}
	
})(jQuery);


function calDiscountMoney(){
	var dis = $("input[name='discount']").val();
	if(!checkDiscountNum(dis)){
		alert("优惠金额必须是数字");
		$("input[name='discount']").val("");
		return;
	}
	var origin = parseFloat($("input[name='sum']").val());
	var discount = parseFloat(dis);
	var now = origin - discount;
	now = now.toFixed(2);

	if(now<0){
		alert("优惠金额不能大于总金额");
		$("input[name='discount']").val("");
		$("input[name='totalmoney']").val(origin);
		return;
	}else{
		$("input[name='totalmoney']").val(now);
	}
}

function checkDiscountNum(input){
	var re = /^[0-9]+.?[0-9]*$/; 
	return re.test(input);
}
function checkNum(input){
	var re = /^[0-9]+.?[0-9]*$/; 
	return re.test(input);
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
	$("#product_amount").val(getTdString("product_amount"));
	$("#product_color").val(getTdString("product_color"));
	$("#product_style").val(getTdString("product_style"));

	
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

function produce_verify(){
	if(confirm("确认操作？")){
		$("#produce_color").val(getTdString("produce_color"));
		$("#produce_xs").val(getTdString("produce_xs"));
		$("#produce_s").val(getTdString("produce_s"));
		$("#produce_m").val(getTdString("produce_m"));
		$("#produce_l").val(getTdString("produce_l"));
		$("#produce_xl").val(getTdString("produce_xl"));
		$("#produce_xxl").val(getTdString("produce_xxl"));
		
		return true;
	}else{
		return false;
	}
}

function init(){
//	$("#fabric_name").val("name1,name2,name3");
//	$("#fabric_amount").val("11,12,13");
//	$("#accessory_name").val("name1,name2,name3");
//	$("#accessory_query").val("name1,name2,name3");
//	
//	
//	
//	$("input[name='style_name']").val("杀马特3000");
//	$("input[name='order_source']").val("杀马特3000");
//	$("input[name='ask_amount']").val("20");
//	$("input[name='ask_produce_period']").val("10");
//	$("input[name='ask_deliver_date']").val("2012-12-12");
//	
//	
//	$("input[name='in_post_sample_clothes_time']").val("2012-12-12");
//	$("input[name='in_post_sample_clothes_type']").val("顺丰");
//	$("input[name='in_post_sample_clothes_number']").val("435435345435345");
//	
//	
//	$("input[name='sample_clothes_time']").val("2012-12-12");
//	$("input[name='sample_clothes_type']").val("申通");
//	$("input[name='sample_clothes_number']").val("657567567567567567");
//	$("input[name='sample_clothes_name']").val("黑撒旦躬");
//	$("input[name='sample_clothes_phone']").val("1599546546");
//	$("input[name='sample_clothes_address']").val("江苏南京");
//	$("input[name='sample_clothes_remark']").val("ggggggggggggggggggg");
}