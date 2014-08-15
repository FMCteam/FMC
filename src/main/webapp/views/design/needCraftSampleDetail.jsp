<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>

<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">
			<!--  如果是其它页面，这里是填充具体的内容。 -->
			<ul class="nav nav-tabs detail" id="tab">
				<li class="task-name">样衣工艺加工</li>
				<li class="active"><a href="#craftSample" data-toggle="tab">工艺报价</a></li>
				<li><a href="#quote" data-toggle="tab">报价信息</a></li>
				<li><a href="#cad" data-toggle="tab">版型信息</a></li>
				<li><a href="#produce" data-toggle="tab">加工信息</a></li>
				<li><a href="#sample" data-toggle="tab">样衣信息</a></li>
				<li><a href="#material" data-toggle="tab">面辅信息</a></li>
				<li><a href="#basic" data-toggle="tab">基本信息</a></li>
			</ul>

			<div class="tab-content">
				<div class="tab-pane" id="basic">
					<%@include file="/views/common/basic.jsp"%>
				</div>
				<div class="tab-pane" id="material">
					<%@include file="/views/common/material.jsp"%>
				</div>
				<div class="tab-pane" id="sample">
					<%@include file="/views/common/sample.jsp"%>
				</div>
				<div class="tab-pane" id="produce">
					<%@include file="/views/common/produce.jsp"%>
				</div>
				<div class="tab-pane" id="cad">
					<%@include file="/views/common/cad.jsp"%>
				</div>
				<div class="tab-pane" id="quote">
					<%@include file="/views/common/quote.jsp"%>
				</div>
			<div class="tab-pane active" id="craftSample">
				<form action="${ctx}/design/uploadCraftFileSubmit.do?orderId=${orderInfo.order.orderId}" method="post" enctype="multipart/form-data">
					<table class="table table-striped table-bordered table-hover detail">
						<tr>
							<td class="title">上传工艺文件</td>
							<td colspan="6">
				                <input type="hidden" name="processId" value="${orderInfo.task.processInstanceId}" />
								<input name="craftFile" id="craftFile" type="file" required="required"/> 
								<input class="btn btn-primary btn-rounded" type="submit" value="上传工艺制作图"  onclick="return check()"/>						
								<a style="color: red;">点击后,上传图片生效！</a>
							</td>
						</tr>
						<tr>
			                <td class="title">工艺图片</td>
			                <td colspan="6">
			                	<c:if test="${orderInfo.sampleCraft.craftFileUrl!=null}">
					            	<img src="${ctx}/common/getPic.do?type=craftFileUrl&orderId=${orderInfo.order.orderId}"
						                 style="max-height: 300px;" alt="工艺图片"></img>
				                </c:if>
				            </td>
		                </tr>
		            </table>
	            </form>
			
				<form action="${ctx}/design/needCraftSampleSubmit.do?orderId=${orderInfo.order.orderId}&taskId=${orderInfo.task.id}" method="post"
						onsubmit="return confirm('确认完成工艺制作？')">
					<table class="table table-striped table-bordered table-hover detail">
					    <tr>
							<td class="title">客户工艺要求</td>
							<td colspan="2">
							 ${orderInfo.designCadTech}
							</td>
							<td class="title">工艺负责人<span style="color: red">*</span></td>
							<td><input id="craft_leader" type="text" name="craftLeader" required="required"  value="${USER_nick_name }"/></td>
							<td class="title">工艺完成时间<span style="color: red">*</span></td>
							<td><input id="input_day" type="text" name="completeTime" required="required" /></td>
						</tr>		
						<tr>
							<td class="title" rowspan="2">工艺报价</td>
							<td class="title">印花费（元/件）
							</td>
							<td class="title">水洗吊染费（元/件）
							</td>
							<td class="title">激光费（元/件）
							</td>
							<td class="title">刺绣费（元/件）
							</td>
							<td class="title">压皱费（元/件）
							</td>
							<td class="title">开版费用（元/件）
							</td>
						</tr>
						<tr>
							<td>${orderInfo.sampleCraft.stampDutyMoney}</td>
							<td>${orderInfo.sampleCraft.washHangDyeMoney}</td>
							<td>${orderInfo.sampleCraft.laserMoney}</td>
							<td>${orderInfo.sampleCraft.embroideryMoney}</td>
							<td>${orderInfo.sampleCraft.crumpleMoney}</td>
							<td>${orderInfo.sampleCraft.openVersionMoney}</td>
						</tr>
					</table>
					<div class="action" style="float:right">
						<input type="submit" class="btn btn-primary" value="完成工艺制作"/>
					</div>
				</form>
					
				<button class="btn btn-primary" onclick="history.back();">返回</button>
				
			</div>	
		</div>

		</div>
		<!--row-fluid-->
		<input type="hidden"  id="imgtest"  value="${orderInfo.sampleCraft.craftFileUrl}" />
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


<%@include file="/common/js_file.jsp"%>
<%@include file="/common/js_form_file.jsp"%>
<link rel="stylesheet" href="${ctx}/css/order/add_order.css">
<script type="text/javascript" src="${ctx}/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="${ctx}/js/order/add_order.js"></script>
<link rel="stylesheet" href="${ctx}/css/fmc/table.css">
<script type="text/javascript" src="${ctx}/js/fmc/table.js"></script>
<link rel="stylesheet" href="${ctx}/css/fmc/detail.css">
<link rel="stylesheet" href="${ctx}/css/order/add_order.css">
<script type="text/javascript" src="${ctx }/js/custom.js"></script>
<script type="text/javascript">
$(document).ready(function() {
 var text=$("#pay").text();
	$("#pay").text(parseFloat(text).toFixed(2));

});  

function check(){
	var craftFile = document.getElementById("craftFile").value;
	var craftFilestr = craftFile.substr(craftFile.indexOf(".")).toLowerCase();		
	if(craftFile.length != 0){
		if(craftFilestr == ".jpg" || craftFilestr == ".png"){	
			if(confirm('确认上传工艺图片？')){
				return true;
			}else{
				return false;
			}
		}else{
			alert("工艺图片格式不对，请上传jpg或png格式的图片！");
			return false;
		}
	}else{
		alert("请上传工艺图片");
		return false;
	}
}

function checkimg(){
	var img = $("#imgtest").val();
	 if(img != ""){
		 if(confirm('确认完成工艺制作？')){
				return true;
			}else{
				return false;
			}
	}else{ 
			alert("请上传工艺图片");
			return false;
	 } 
	
}
</script>
<%@include file="/common/footer.jsp"%>

