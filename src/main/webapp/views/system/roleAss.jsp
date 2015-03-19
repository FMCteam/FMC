<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@include file="/common/header.jsp"%>
<%@include file="/common/js_file.jsp"%>
<%@include file="/common/js_form_file.jsp"%>

<link rel="STYLESHEET" type="text/css" href="${ctx }/css/uiframe/bootstrap/css/bootstrap.css">
<link rel="STYLESHEET" type="text/css" href="${ctx }/css/uiframe/dhtmlx/grid/codebase/dhtmlxgrid.css">
<link rel="stylesheet" type="text/css"href="${ctx }/css/uiframe/dhtmlx/grid/codebase/skins/dhtmlxgrid_dhx_skyblue.css">
<link rel="stylesheet" type="text/css"href="${ctx }/css/uiframe/dhtmlx/toolbar/codebase/skins/dhtmlxtoolbar_dhx_skyblue.css"></link>
<link rel="stylesheet" type="text/css" href="${ctx }/css/uiframe/dhtmlx/dhtmlxMessage/codebase/skins/dhtmlxmessage_dhx_skyblue.css">
<!-- <script type="text/javascript"
	src="uiframe/dhtmlx/grid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="uiframe/dhtmlx/grid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript"
	src="uiframe/dhtmlx/grid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="js/jquery/jquery-1.8.3.js"></script>
<script type="text/javascript" src="uiframe/dhtmlx/dhtmlxMessage/codebase/dhtmlxmessage.js"></script> -->
<script type="text/javascript" src="${ctx }/js/jquery/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${ctx }/css/uiframe/dhtmlx/grid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="${ctx }/css/uiframe/dhtmlx/grid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="${ctx }/css/uiframe/dhtmlx/grid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="${ctx }/css/uiframe/dhtmlx/grid/codebase/ext/dhtmlxgrid_pgn.js"></script>
<script type="text/javascript" src="${ctx }/css/uiframe/dhtmlx/toolbar/codebase/dhtmlxtoolbar.js"></script>
<script type="text/javascript" src="${ctx }/css/uiframe/dhtmlx/dhtmlxMessage/codebase/dhtmlxmessage.js"></script>

<script type="text/javascript">
		var userNew="";
		window.onload=function (){
			mygrid = new dhtmlXGridObject('userbox');
			mygrid.setImagePath("${ctx }/css/uiframe/dhtmlx/grid/codebase/imgs/");
			mygrid.setHeader("用户的id,用户帐号,用户名,用户角色");
			mygrid.setInitWidths("0,200,*,*");
			mygrid.setColAlign("center,left,left,left");
			mygrid.setColTypes("ro,ro,ro,ro");
			mygrid.setColSorting("str,str,str,str");
			mygrid.setColumnIds("accountId,userName,nickName,userRole");
			mygrid.setSkin("dhx_skyblue");
			mygrid.init();
			//开始分页
		    mygrid.attachFooter(["<div id='gridbox_recinfoArea' style='width:100%;height:100%'></div>","#cspan"],['height:25px;text-align:left;background:transparent;border-color:white;padding:0px;']);
		    mygrid.enablePaging(true,20,  3,'recinfoArea');
		    mygrid.setPagingSkin("toolbar", "dhx_skyblue");
			mygrid.setColumnHidden(0,true);
			mygrid.load("${ctx }/system/findAccountMes.do", "js");
			
			//单击事件 
			mygrid.attachEvent("onRowSelect",function (id,cell){
				var user=mygrid.cells(id,0).getValue();
				userNew=mygrid.cells(id,0).getValue();
				roleGrid.forEachRow(function(id){ 
					roleGrid.cells(id,1).setValue('');
				});
				$.ajax({
					url:'${ctx }/system/findAccountRole.do',
					type:'post',
					data:'urViewUserId='+user,
					success:function(data){
						if(data!=""){
							for(var i in data){
								roleGrid.forEachRow(function(id){ 
									if(data[i]['roleId']==roleGrid.cells(id,0).getValue()){
										roleGrid.cells(id,1).setValue(1);
									};
						  	    });
							}
					  }else{
					  	roleGrid.forEachRow(function(id){ roleGrid.cells(id,1).setValue('');});
					  }
					}
				});
			});
			roleGrid= new dhtmlXGridObject("rolebox");
			roleGrid.setImagePath("${ctx }/css/uiframe/dhtmlx/grid/codebase/imgs/");
			roleGrid.setHeader("roleId,Y/N,角色名称,角色描述");
			roleGrid.setInitWidths("0,50,*,*");
			roleGrid.setColAlign("center,center,left,left");
			roleGrid.setColTypes("ro,ch,ro,ro");
			roleGrid.setColSorting("str,str,str,str");
			roleGrid.setColumnIds("roleId,userId,name,description");
			roleGrid.setSkin("dhx_skyblue");
			roleGrid.init();
			roleGrid.setColumnHidden(0,true);
			roleGrid.load("${ctx }/system/findRole.do", "js");
			
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
		}
		function doSave(){
			var temp="";
			roleGrid.forEachRow(function(id){
				if(roleGrid.cells(id,1).getValue()!=0){
					var roleNew=roleGrid.cells(id,0).getValue();
					temp=temp+roleNew+",";
				}
			});
			if(userNew==""||temp==""){
				dhtmlx.alert({
					 title:"Important!",
					 type:"alert-error",
					 text:"请先选择用户和角色！"
				})
			}else{
				$.ajax({
					url:'${ctx }/system/updateAccountRole.do',
					type:'post',
					data:'urViewUserId='+userNew+'&temp='+temp,
					success:function(msg){
						if(msg==1){
							dhtmlx.alert("成功！！")
						}else{
							dhtmlx.alert({
							 	title:"Important!",
							 	type:"alert-error",
							 	text:"失败！"
							})
						}
					}
				});
			}
		}

</script>
<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
			 <div>
				<shiro:hasPermission name="RoleAdd">
				<button class="btn btn-danger" onclick="doSave()">保存</button>
				</shiro:hasPermission>
			 </div>
			<div style="float: left; ">
				 <div id="userbox"style="float: left; width: 520px; height: 442px; background-color: white;"></div><br>	
				<div id="recinfoArea"  style=" width: 512px; height: 29px;"></div>
			</div>
      	
      		<div id="rolebox"style="float: left; width: 350px; height: 470px; background-color: white;"></div>
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
