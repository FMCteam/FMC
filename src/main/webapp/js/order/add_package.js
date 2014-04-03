// JavaScript Document

(function($) {
	
	$(function(){
		//$.log("添加事件");
		$("table.package_table a").click(function(){
			table_addrow_onclick("package_table","clothes_amount","clothes_style_color","clothes_style_name");
		});

		$("#addpackage").click(function(){
			//table_addrow_onclick("package_table","clothes_amount","clothes_style_color","clothes_style_name");
			//alert('function');
			/*var item="	<tr>
								<td rowspan='3'>包</td>
								<td >衣服款式</td>
								<td >衣服颜色</td>
								<td >衣服数量</td>
								<td>操作</td>
								<input id='clothes_amount' name='clothes_amount' type=''hidden'/>
								<input id='clothes_style_color' name='clothes_style_color' type=''hidden' />
								<input id='clothes_style_name' name='clothes_style_name' type=''hidden' />
							</tr>
							
							<tr>
								<td colspan='6' class='innertable'><table
										class='span12 table package_table'>
										<tr class="addrow">
											<td><input class='span12' type='text' /></td>
											<td><input class='span12' type=''text' /></td>
											<td><input class='span12' type='text' /></td>
											<td><a>添加</a></td>
										</tr>
									</table></td>
			</tr>
			<tr>
							<td>打包时间</td>
							<td><input id='datepicker' type='text' name='package_date'  class='input-medium'></td>
							</tr>"
							*/
		});
		
		
	
		

		
		
	

		init();
		
		
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
			//检查第二列是否为空
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
	$("#clothes_amount").val(getTdString("clothes_amount"));
	$("#clothes_style_color").val(getTdString("clothes_style_color"));
	$("#clothes_style_name").val(getTdString("clothes_style_name"));
	return true;
}

function init(){
	
}