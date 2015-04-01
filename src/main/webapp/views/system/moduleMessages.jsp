<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
<%-- <jsp:include page="/common/header.jsp" flush="true"></jsp:include> --%>
<%@include file="/common/js_file.jsp"%>
<%@include file="/common/js_form_file.jsp"%>
<link rel="stylesheet" href="${ctx }/css/uiframe/ztree/css/demo.css"
			type="text/css">
<link rel="stylesheet" href="${ctx }/css/uiframe/ztree/css/zTreeStyle.css"
			type="text/css">
<link rel="stylesheet" href="${ctx }/css/uiframe/bootstrap/css/bootstrap.css"
			type="text/css">
<link rel="stylesheet" type="text/css" href="${ctx }/css/uiframe/dhtmlx/dhtmlxMessage/codebase/skins/dhtmlxmessage_dhx_skyblue.css">

<script type="text/javascript" src="${ctx }/js/jquery/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${ctx }/css/uiframe/ztree/js/jquery.ztree.all-3.5.js"></script>
<script type="text/javascript" src="${ctx }/css/uiframe/dhtmlx/dhtmlxMessage/codebase/dhtmlxmessage.js"></script>
<script type="text/javascript">
	var setting = {
	    view: {
	        dblClickExpand: false
	    },
	    data: {  
	        simpleData: {
	            enable: true
	        },
	        key: {
	        url:"xxx"
	        }
	    },
	    callback: {
	    	onClick:OnClick,
	    	onRightClick:OnRightClick
		},
	    async: {
	        enable: true,
	        type:"post",
	        dataType:"json",
			url:"${ctx }/system/findSystemMenu.do"
	    }
	};
	
	function OnClick(event, treeId, treeNode){
		$.ajax({
               			url:'${ctx }/system/editSystemMenu.do',
               			type:'POST',
               			data:"ep.permissionId="+treeNode.id,
	               		success:function(data){
	               			$("#pname").val(data['pname']);
	               			$("#permissionId").val(data['permissionId']);
	               			$("#sort").val(data['sort']);
	               			$("#url").val(data['url']);
	               			$("#description").val(data['description']);
	               			$("#name").val(data['name']);
	               			$("#myid").val(data['myid']);
	               			$("#pid").val(data['pid']);
	               			$("#isused").val(data['isused']);
	               			$("#isAdd").val("");
	               			
	               		
	               		}
               		});
		}
		function OnRightClick(event, treeId, treeNode) {
			if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
				zTree.cancelSelectedNode();
				showRMenu("root", event.clientX, event.clientY);
			} else if (treeNode && !treeNode.noR) {
				if(treeNode.pId!=null&&treeNode.pId!=""){
				var s =zTree.selectNode(treeNode);
				showRMenu("pnode", event.clientX, event.clientY);
				}else{
				zTree.selectNode(treeNode);
				showRMenu("node", event.clientX, event.clientY);
				}
			}
		}	
		function showRMenu(type, x, y) {
			$("#rMenu ul").show();
			if (type=="root") {
				$("#m_add").hide();
			}else if(type=="pnode"){
				$("#m_add").show();
			} 
			rMenu.css({"top":y+"px", "left":x+"px", "visibility":"visible"});

			$("body").bind("mousedown", onBodyMouseDown);
		}
		function addTreeNode(){
			   hideRMenu();
			   var nodes=zTree.getSelectedNodes();
			   //alert(nodes);
			 // alert(JSON.stringify(nodes[0]))
			  //alert(nodes[0].pId);
			  //alert(nodes[0].children);
			   if(nodes[0].level=='2'&&!nodes[0].children){
			    dhtmlx.alert({
			   				    title:"Important!",
	                            type:"alert-error",
	                            text:"此节点下不能再新增子节点！"
			    })
			   }else{
			   $("#pid").val(nodes[0].pId);
			   $.ajax({
	               			url:'${ctx }/system/editSystemMenu.do',
	               			type:'POST',
	               			data:"ep.permissionId="+nodes[0].id,
		               		success:function(data){
		               			$("#permissionId").val(data['permissionId']);
	                			$("#pname").val(data['name']);
		               			$("#sort").val("");
		               			$("#url").val("");
		               			$("#description").val("");
		               			$("#name").val("");
		               			$("#isused").val("");
		               			$("#myid").val("");
		               			$("#isAdd").val("Add");
		               		}
	               		});
			   }
			 }
	function submit1(){
			$.ajax({
				url:'${ctx }/system/updateSystemMessage.do',
				type:'POST',
				data:$("#form1").serialize(),
				success:function (msg){
					if(msg == 1){
						alert("保存成功");
						//window.location.href = "${ctx }/system/moduleMessages.do";
					}
				}
			})
		}
	function cancel(){
		//alert($("#form1").serialize());
		history.back(-1);
	}
	function hideRMenu() {
		if (rMenu) rMenu.css({"visibility": "hidden"});
		$("body").unbind("mousedown", onBodyMouseDown);
	}
	function onBodyMouseDown(event){
		if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length>0)) {
			rMenu.css({"visibility" : "hidden"});
		}
	}
	var zTree,rMenu;
	$(document).ready(function(){
		$.fn.zTree.init($("#treeDemo"), setting);
		zTree = $.fn.zTree.getZTreeObj("treeDemo");
		rMenu = $("#rMenu");
		
	});
</script>
<style type="text/css">
#main li {
	float: left;
	line-height: 50px;
}

div#rMenu {
	position: absolute;
	visibility: hidden;
	top: 0;
	text-align: left;
	padding: 2px;
}

div#rMenu ul li {
	margin: 1px 0;
	padding: 0 5px;
	cursor: pointer;
	list-style: none outside none;
	background-color: #DFDFDF;
}


li{list-style-type:none;}

</style>
<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
			<div style="float: left;width:220px;height: 500px;">
				<div class="zTreeDemoBackground left">
					<ul id="treeDemo" style="margin-top: 0px;" class="ztree"></ul>
				</div>
			</div>
			<div style="float: left; margin-left: 0px;width:750px;">
				<div align="center" style="height: 50px;">
					<h2>
						<span>基本资料</span>
					</h2>
				</div>
				<form action="${ctx }/system/updateSystemMessage.do" method="post" name="form1" id="form1">
					<div class="navbar-inner" >
						<ul id="Main">
							<li style="width: 300px; display: none;">
								<input id="permissionId" name="ep.permissionId" />
								<input id="pid" name="ep.pid" type="hidden"/>
								<input id="isAdd" name="isAdd" value="Add" type="hidden"/>
							</li>
							<li style="width: 300px">
								上层程式：
								<input id="pname" name="ep.pname" readonly type="text" style="height: 30px"/>
							</li>
							<li>
								程式名称：
								<input id="name" name="ep.name"  type="text" style="height: 30px"/>
							</li>
						</ul>
						<ul id="Main">
							<li style="width: 300px">
								排列序号：
								<input id="sort" name="ep.sort"
									onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9-]+/,'');}).call(this)"
									onblur="this.v();" placeholder="仅能填数字" type="text" style="height: 30px"/>
							</li>
							<li style="width: 300px">
								程式路径：
								<input id="url" name="ep.url" type="text" style="height: 30px"/>
							</li>
						</ul>
						<ul id="Main">
							<li style="width: 300px">
								任务样式：
								<input id="myid" name="ep.myid" width="600px;" type="text" style="height: 30px"/>
							</li>
							<li style="width: 300px">
								是否启用：
								<select id="isused" name="ep.isused" style="width: 205px">
								<option value="">请选择</option>
								<option value="Y">Yes</option>
								<option value="N">NO</option>
								</select>
							</li>
						</ul>
						<ul id="Main">
						<li style="width: 600px">
								程式说明：
								<input id="description" name="ep.description" type="text" style="height: 30px;width: 505px"/>
							</li>
						</ul>
			
						<div style="text-align: center;width: 600px"">
							<input class="btn btn-danger" type="submit" value="提交">
							<!-- <button class="btn btn-danger" type="button" onclick="submit1()">
								提交
							</button> -->
							<button class="btn btn-primary" type="button" onclick="cancel()">
								取消
							</button>
						</div>
					</div>
				</form>
			</div>
			<div id="rMenu">
				<ul>
					<li id="m_add" onclick="addTreeNode();">
						增加子节点
					</li>
					<!-- <li id="m_remove" onclick="removeTreeNode()">
						删除节点
					</li> -->
				</ul>
			</div>
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