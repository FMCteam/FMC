<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@include file="/common/header.jsp"%>
<%@include file="/common/js_file.jsp"%>
<%@include file="/common/js_form_file.jsp"%>

<link rel="STYLESHEET" type="text/css"
			href="${ctx }/css/uiframe/dhtmlx/grid/codebase/dhtmlxgrid.css">
<link rel="STYLESHEET" type="text/css"
			href="${ctx }/css/uiframe/dhtmlx/dhtmlxTree/codebase/dhtmlxtree.css">
<link rel="stylesheet" type="text/css"
			href="${ctx }/css/uiframe/dhtmlx/grid/codebase/skins/dhtmlxgrid_dhx_skyblue.css">
<link rel="stylesheet" type="text/css" href="${ctx }/css/uiframe/dhtmlx/dhtmlxMessage/codebase/skins/dhtmlxmessage_dhx_skyblue.css">
<script type="text/javascript" src="${ctx }/js/jquery/jquery-1.8.3.js"></script>
<script type="text/javascript"
			src="${ctx }/css/uiframe/dhtmlx/grid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript"
			src="${ctx }/css/uiframe/dhtmlx/grid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript"
			src="${ctx }/css/uiframe/dhtmlx/dhtmlxTree/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript"
			src="${ctx }/css/uiframe/dhtmlx/dhtmlxTree/codebase/dhtmlxtree.js"></script>
<script type="text/javascript"
			src="${ctx }/css/uiframe/dhtmlx/dhtmlxTree/codebase/ext/dhtmlxtree_json.js"></script>
<script type="text/javascript"
			src="${ctx }/css/uiframe/dhtmlx/grid/codebase/dhtmlxgridcell.js"></script>
<script src="${ctx }/css/uiframe/dhtmlx/grid/codebase/ext/dhtmlxgrid_filter.js "></script>
<script type="text/javascript" src="${ctx }/css/uiframe/dhtmlx/dhtmlxMessage/codebase/dhtmlxmessage.js"></script>
<script type="text/javascript" src="${ctx }/css/js/json2.js"></script>
<style type="text/css">
.btn-danger {
  color: #ffffff;
  text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.25);
  background-color: #da4f49;
  *background-color: #bd362f;
  border-color: rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.25);
}
.btn-primary {
  color: #ffffff;
  text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.25);
  background-color: #006dcc;
  *background-color: #0044cc;
  border-color: rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.25);
}

		</style>
	</head>
<script type="text/javascript">

var userNew="";
var userDe="";
var userNa="";

window.onload=function (){
	//角色模组
	roleGrid= new dhtmlXGridObject("rolebox");
	roleGrid.setImagePath("${ctx }/css/uiframe/dhtmlx/grid/codebase/imgs/");
	roleGrid.setHeader("roleId,角色名称,角色描述");
	roleGrid.setInitWidths("0,150,200");
	roleGrid.setColAlign("center,center,left");
	roleGrid.setColTypes("ro,ro,ro");
	roleGrid.setColSorting("str,str,str");
	roleGrid.setColumnIds("roleId,name,description");
	roleGrid.setSkin("dhx_skyblue");
	roleGrid.init();
	roleGrid.setColumnHidden(0,true);
	roleGrid.load("${ctx }/system/findRole.do", "js");
	
	//权限模组
	tree = new dhtmlXTreeObject("treeboxbox_tree", "100%", "100%", 0);
	tree.setSkin('dhx_skyblue');
	tree.setImagePath("${ctx }/css/uiframe/dhtmlx/dhtmlxTree/codebase/imgs/csh_bluebooks/");
	tree.enableDragAndDrop(true);
	tree.enableCheckBoxes(1);
	tree.enableThreeStateCheckboxes(true);
	tree.enableSmartXMLParsing(true);
	tree.loadJSON("${ctx }/system/findDreeMapNew.do");
	
	$.ajax({
		url : "${ctx}/common/getTaskNumber.do",
		success : function(msg) {
			var json = eval("(" + msg + ")");
			//$("span.count:eq(0)").text(json.list);
			//alert(msg);
			for ( var key in json) {
				//alert(key+json[key]);
				$("span." + key).text("  (" + json[key] + ")");
			}
		}
	});
	
	//单击事件 
	roleGrid.attachEvent("onRowSelect",function (id,cell){
		var list=tree.getAllChecked().split(",");
		for(var i=0;i<list.length;i++){
			tree.setCheck(list[i],false);
		}
		var user=roleGrid.cells(id,0).getValue();
		userNew=roleGrid.cells(id,0).getValue();
		userNa=roleGrid.cells(id,1).getValue();
		userDe=roleGrid.cells(id,2).getValue(); 
		//alert(user+"----"+userNew+"----"+userNa+"----"+userDe);
		$.ajax({
			url:'${ctx }/system/findRolePriv.do',
			type:'post',
			data:'rpId='+user,
			success:function(data){
				if(data!=""&&JSON.stringify(data)!=null){
					 for(var i in data){
						tree.setCheck(data[i],true);
					 }
				 }
			}
		});
	});
}


function doSave(){
	var temp=tree.getAllCheckedBranches();
	if(userNew==""||temp.size==0){
		dhtmlx.alert({
			title:"Important!",
			type:"alert-error",
			text:"请先选择用户和权限！"
		})
	}else{
		$.ajax({
		url:'${ctx }/system/saveRolePriv.do',      
		type:'post',
		data:'rpId='+userNew+'&temp='+temp,
		success:function(msg){
				if(msg==1){
					dhtmlx.alert("用户权限保存成功！！");
				}else{
					dhtmlx.alert({
					title:"Important!",  
					type:"alert-error",
					text:"用户权限保存失败！！"});
				}
			}
		});
	}
}

function doAddRole(){
	var ind1 = window.prompt('角色名称', userNa).trim();
	if (ind1 == null || typeof ind1 == "undefined"||ind1==""){
		dhtmlx.alert({
			title:"Important!",
			type:"alert-error",
			text:"角色名称不可为空！"
		});
		return;
	}else{
		var ind2 = window.prompt('角色描述', userDe).trim();
		if (ind2 == null || typeof ind2 == "undefined"||ind2==""){
		 	dhtmlx.alert({
			 	title:"Important!",
				type:"alert-error",
				text:"角色描述不可为空！"
		 	});
		    return;
		}else{
			$.ajax({
				url:'${ctx }/system/addRole.do',
				type:'post',
				data:'urViewName='+ind1+'&urViewDescription='+ind2+"&urViewId="+userNew,
				success:function(msg){
					if(msg=1){
						window.location = "${ctx }/system/rolePrivilege.do";
					}else{
						dhtmlx.alert({
							title:"Important!",
							type:"alert-error",
							text:"新增失败！"
						});
						return;
					}
				}
			});
		}
	}

    
}
	

</script>
<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
			<div>
				<button class="btn-danger" onclick="doSave()">保存权限分配</button>
				<button class="btn-primary" onclick="doAddRole()">新增/修改角色</button>			
			</div>
			
			<div id="rolebox"style="float: left; width: 350px; height: 500px; background-color: white;"></div>
			
			<table>
				<tr>
					<td valign="top">
						<div id="treeboxbox_tree"style="width: 750px; height: 500px; overflow: auto;" />
					</td>
				</tr>
			</table>
		</div>
		<!--row-fluid-->

		<div class="footer">
			<div class="footer-left">
				<span>&copy; 2014. 江苏南通智造链有限公司.</span>
			</div>

		</div>
		<!--footer-->

	</div>
	<!--maincontentinner-->
</div>
<!--maincontent-->

<script type="text/javascript" src="${ctx }/js/custom.js"></script>

<%@include file="/common/footer.jsp"%>
