// JavaScript Document

(function($) {
	
	$(function(){
		//$.log("添加事件");
		$("table.fabric_table a").click(function(){
			var colName = ["fabric_name","fabric_amount","tear_per_meter","cost_per_meter"];
			if(checkNum("fabric_table",colName,4)){
			table_addrow_onclick("fabric_table",colName,4);
			}
		});
		
		$("table.accessory_table a").click(function(){
			var colName = ["accessory_name","accessory_query","tear_per_piece","cost_per_piece"];
			if(checkNum2("accessory_table",colName,4)){
			table_addrow_onclick("accessory_table",colName,4);
			}
		});
		
		

		init();
		
		
	});


	function table_addrow_onclick(table_name,col_name,col_sum){
	
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
				$("table."+table_name+" tr.addrow input").eq(j).val("");
				item+="<td class='"+col_name[j]+"'>"+content[j]+"</td>";
			}
			item+="<td><a onclick=\"deleteRow(this,'"+table_name+"')\">删除</a></td>";
			item="<tr>"+item+"</tr>";
			$("table."+table_name+" tr.addrow").after(item);
			
	}


	
})(jQuery);

function checkNum(table_name,col_name,col_sum){
	for(var i=0;i<col_sum;i++){
		var col=$("table."+table_name+" tr.addrow input").eq(i).val();
		if(isNaN(parseInt(col))){
			if(i>0){
				alert("请正确填写数量");
				return false;
			}
		}
	}
	return true;
}

function checkNum2(table_name,col_name,col_sum){
	for(var i=2;i<col_sum;i++){
		var col=$("table."+table_name+" tr.addrow input").eq(i).val();
		if(isNaN(parseInt(col))){
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
	
	
	$("#fabric_name").val(getTdString("fabric_name"));
	$("#fabric_amount").val(getTdString("fabric_amount"));
	$("#tear_per_meter").val(getTdString("tear_per_meter"));
	$("#cost_per_meter").val(getTdString("cost_per_meter"));
	$("#accessory_name").val(getTdString("accessory_name"));
	$("#accessory_query").val(getTdString("accessory_query"));
	$("#tear_per_piece").val(getTdString("tear_per_piece"));
	$("#cost_per_piece").val(getTdString("cost_per_piece"));
	
	
	
	
	var FabricName=jQuery("input[name='fabric_name']").val();
	var FabricAmount=jQuery("input[name='fabric_amount']").val();
	var TearPerMeter=jQuery("input[name='tear_per_meter']").val();
	var CostPerMeter=jQuery("input[name='cost_per_meter']").val();
	var AccessoryName=jQuery("input[name='accessory_name']").val();
	var AccessoryQuery=jQuery("input[name='accessory_query']").val();
	var TearPerPiece=jQuery("input[name='tear_per_piece']").val();
	var CostPerPiece=jQuery("input[name='cost_per_piece']").val();

 if((FabricName== "")||(FabricAmount== "")||(TearPerMeter== "")||(CostPerMeter== "")||(AccessoryName== "")
		 ||(AccessoryQuery== "")||(TearPerPiece== "")||(CostPerPiece== "")){
	 
//	alert("我是魏恒");
	  return false;
	
 }
	
 
 else{
	 
	 return true;
 }
	
}

function init(){

	
}