<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %> 
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<head>	
<title>智造链</title>
<meta http-equiv="content-type" content="text/html;charset=utf-8" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="css/style.default.css" type="text/css" />

<link rel="stylesheet" href="css/responsive-tables.css">
<script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="js/jquery-migrate-1.1.1.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.9.2.min.js"></script>
<script type="text/javascript" src="js/modernizr.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/jquery.cookie.js"></script>
<script type="text/javascript" src="js/jquery.uniform.min.js"></script>
<script type="text/javascript" src="js/flot/jquery.flot.min.js"></script>
<script type="text/javascript" src="js/flot/jquery.flot.resize.min.js"></script>
<script type="text/javascript" src="js/responsive-tables.js"></script>
<script type="text/javascript" src="js/jquery.slimscroll.js"></script>
<script type="text/javascript" src="js/custom.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
<script type="text/javascript">
    jQuery(document).ready(function(){
        // dynamic table
        jQuery('#dyntable').dataTable({
            "sPaginationType": "full_numbers",
            "aaSortingFixed": [[0,'asc']],
            "fnDrawCallback": function(oSettings) {
                jQuery.uniform.update();
            }
        });
        
    });
</script>
<!--[if lte IE 8]><script language="javascript" type="text/javascript" src="js/excanvas.min.js"></script><![endif]-->
</head>
<body>
	<div id="mainwrapper" class="mainwrapper">
    
    <div class="header">
        <div class="logo">
            <a href="#" style="color:white;font-size:55px;font-family:"Miscrosoft YaHei""><!-- <img src="images/logo.png" alt="" /> -->智造链</a>
        </div>
        <div class="headerinner">
            <ul class="headmenu">
                <li class="odd">
                    <a href="#">
                        <span class="count">4</span>
                        <span class="head-icon head-message"></span>
                        <span class="headmenu-label">新任务</span>
                    </a>
                </li>
                <li>
                    <a>
                    <span class="count">10</span>
                    <span class="head-icon head-users"></span>
                    <span class="headmenu-label">新客户</span>
                    </a>
                
                </li>
               
                <li class="right">
                    <div class="userloggedinfo">
<!--                         <img src="images/photos/thumb1.png" alt="" /> -->
                        <div class="userinfo">
                            <h5>葛羽航<small>- 15195908816</small></h5>
                            <ul>
                                <li><a href="#">查看详情</a></li>
                                <li><a href="#">修改账户</a></li>
                                <li><a href="#">退出</a></li>
                            </ul>
                        </div>
                    </div>
                </li>
            </ul><!--headmenu-->
        </div>
    </div>
    
    <div class="leftpanel">
        
        <div class="leftmenu">        
            <ul class="nav nav-tabs nav-stacked">
            	<li class="nav-header">Navigation</li>
                <li class="active"><a href="dashboard.html"><span class="iconfa-laptop"></span> 欢迎</a></li>
                <li class="dropdown"><a href=""><span class="iconfa-pencil"></span> 客户管理</a>
                	<ul>
                    	<li><a href="forms.html">新建客户</a></li>
                        <li><a href="wizards.html">查看客户</a></li>
                    </ul>
                </li>
                <li class="dropdown"><a href=""><span class="iconfa-briefcase"></span> 市场部</a>
                	<ul>
                    	<li><a href="#">客户下单</a></li>
                        <li><a href="#">修改询单</a></li>
                        <li><a href="#">合并报价</a></li>
                        <li><a href="#">审核报价</a></li>
                        <li><a href="#">报价商定</a></li>
                        <li><a href="#">修改报价</a></li>
                        <li><a href="#">商定合同</a></li>
                        <li><a href="#">修改合同</a></li>
                        <li><a href="#">签订合同</a></li>
                        <li><a href="#">订单回访</a></li>
                        <li><a href="#">提醒缴费</a></li>
                    </ul>
                </li>
                <li class="dropdown"><a href=""><span class="iconfa-th-list"></span> 设计部</a>
                	<ul>
                    	<li><a href="#">样衣验证</a></li>
                        <li><a href="#">成本合算</a></li>
                        <li><a href="#">样衣版型</a></li>
                        <li><a href="#">生产验证</a></li>
                        <li><a href="#">生产版型</a></li>
                    </ul>
                </li>
                <li class="dropdown"><a href=""><span class="iconfa-th-list"></span> 采购部</a>
                	<ul>
                    	<li><a href="#">样衣验证</a></li>
                        <li><a href="#">成本合算</a></li>
                        <li><a href="#">样衣采购</a></li>
                        <li><a href="#">生产验证</a></li>
                        <li><a href="#">生产采购</a></li>
                    </ul>
                </li>
                <li class="dropdown"><a href=""><span class="iconfa-th-list"></span> 生产部</a>
                	<ul>
                    	<li><a href="#">样衣验证</a></li>
                        <li><a href="#">成本合算</a></li>
                        <li><a href="#">样衣生产</a></li>
                        <li><a href="#">批量生产</a></li>
                    </ul>
                </li>
                <li class="dropdown"><a href=""><span class="iconfa-th-list"></span> 财务部</a>
                	<ul>
                    	<li><a href="#">样衣金确认</a></li>
                        <li><a href="#">首定金确认</a></li>
                        <li><a href="#">生产金确认</a></li>
                        <li><a href="#">尾金确认</a></li>
                    </ul>
                </li>
                <li class="dropdown"><a href=""><span class="iconfa-th-list"></span> 物流部</a>
                	<ul>
                    	<li><a href="#">收取样衣</a></li>
                        <li><a href="#">样衣发货</a></li>
                        <li><a href="#">产品入库</a></li>
                        <li><a href="#">产品发货</a></li>
                    </ul>
                </li>
                <li class="dropdown"><a href=""><span class="iconfa-th-list"></span> 质检部</a>
                	<ul>
                    	<li><a href="#">质量检查</a></li>
                    </ul>
                </li>
                <li class="dropdown active"><a href=""><span class="iconfa-th-list"></span> 人事部</a>
                	<ul style="display:block">
                    	<li><a href="#">添加员工</a></li>
                        <li class="avtive"><a href="#">查看员工</a></li>
                    </ul>
                </li>
            </ul>
        </div><!--leftmenu-->
        
    </div><!-- leftpanel -->
    
    <div class="rightpanel">
        
        <ul class="breadcrumbs">
            <li><a href="dashboard.html"><i class="iconfa-home"></i></a> <span class="separator"></span></li>
            <li>欢迎！</li>
        </ul>
        
        <div class="pageheader">
           
            <div class="pageicon"><span class="iconfa-laptop"></span></div>
            <div class="pagetitle">
                <h5>服装快速响应供应链</h5>
                <h1>智造链</h1>
            </div>
        </div><!--pageheader-->
        
        <div class="maincontent">
            <div class="maincontentinner">
                <div class="row-fluid" style="min-height:300px;">
                    <!--  如果是其它页面，这里是填充具体的内容。 -->
                    <h4 class="widgettitle">查看员工</h4>
                <table class="table responsive">
                    <thead>
                        <tr>
                            <th>姓名</th>
                            <th>性别</th>
                            <th>年龄</th>
                            <th>部门</th>
                            <th>地址</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>Trident</td>
                            <td>Internet Explorer 4.0</td>
                            <td>Win 95+</td>
                            <td class="center">4</td>
                            <td class="center">X</td>
                        </tr>
	                    <!--<c:forEach var="employee" items="${employee_list}" >
	                        <tr>
	                            <td>${employee.getEmployeeName()}</td>
								<td>${employee.getEmployeeName()}</td>
								<td>${employee.getEmployeeName()}</td>
								<td>${employee.getEmployeeName()}</td>
								<td>${employee.getEmployeeName()}</td>
	                        </tr>
                        </c:forEach>-->
                    </tbody>
                </table>
                <div class="dataTables_paginate paging_full_numbers" id="dyntable_paginate" style="float:right">
                	<c:if test="${page==1 }">
	                	<a tabindex="0" class="first paginate_button paginate_button_disabled" id="dyntable_first">首页</a>
						<a tabindex="0" class="previous paginate_button paginate_button_disabled" id="dyntable_previous">上一页</a>
                	</c:if>
					<c:if test="${page>1 }">
	                	<a tabindex="0" class="first paginate_button" id="dyntable_first" href="employee/search.do?page=1&number_per_page=10">首页</a>
						<a tabindex="0" class="previous paginate_button" id="dyntable_previous" href="employee/search.do?page=${page-1 }&number_per_page=10">上一页</a>
                	</c:if>
					<span><a tabindex="0" class="paginate_active">1</a>
						<a tabindex="0" class="paginate_button">2</a>
						<a tabindex="0" class="paginate_button">3</a>
						<a tabindex="0" class="paginate_button">4</a>
						<a tabindex="0" class="paginate_button">5</a>
					</span>
					<c:if test="${page<page_number }">
	                	<a tabindex="0" class="next paginate_button" id="dyntable_next" href="employee/search.do?page=${page+1 }&number_per_page=10">下一页</a>
						<a tabindex="0" class="last paginate_button" id="dyntable_last" href="employee/search.do?page=${page_number }&number_per_page=10">尾页</a>
                	</c:if>
					<c:if test="${page==page_number }">
	                	<a tabindex="0" class="next paginate_button paginate_button_disabled" id="dyntable_next">下一页</a>
						<a tabindex="0" class="last paginate_button paginate_button_disabled" id="dyntable_last">尾页</a>
                	</c:if>
				</div>
                </div><!--row-fluid-->
                
                <div class="footer">
                    <div class="footer-left">
                        <span>&copy; 2014. 江苏南通智造链有限公司.</span>
                    </div>
                  
                </div><!--footer-->
                
            </div><!--maincontentinner-->
        </div><!--maincontent-->
        
    </div><!--rightpanel-->
    
</div><!--mainwrapper-->
<script type="text/javascript">

</script>
</body>
</html>
