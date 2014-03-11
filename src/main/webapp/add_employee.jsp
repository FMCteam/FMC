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

<script type="text/javascript" src="js/charCount.js"></script>
<script type="text/javascript" src="js/custom.js"></script>
<script type="text/javascript" src="js/forms.js"></script>
<script type="text/javascript" src="js/jquery.validate.min.js"></script>
<script type="text/javascript" src="js/jquery.tagsinput.min.js"></script>
<script type="text/javascript" src="js/jquery.autogrow-textarea.js"></script>
<script type="text/javascript" src="js/jquery.slimscroll.js"></script>


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
                 <li class="dropdown"><a href=""><span class="iconfa-pencil"></span> 订单管理</a>
                	<ul>
                    	<li><a href="forms.html">新建订单</a></li>
                        <li><a href="wizards.html">查看订单</a></li>
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
                	<ul style="display: block">
                    	<li class="active"><a href="employee/add.do">添加员工</a></li>
                        <li><a href="employee/search.do">查看员工</a></li>

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

        		
                    <div class="widget">
			            <h4 class="widgettitle">添加员工</h4>
			            <div class="widgetcontent">
				            <form class="stdform" action="employee/doAdd.do" method="post">
								<p>
		                            <label>用户名</label>
		                            <span class="field"><input type="text" name="employee_id" class="input-large" placeholder="用户名" /></span>
	                        	</p>
					            <p>
		                            <label>姓名</label>
		                            <span class="field"><input type="text" name="employee_name" class="input-large" placeholder="姓名" /></span>
	                        	</p>
	                        	<p>
		                            <label>性别</label>
		                            <span class="formwrapper">
		                            	<input type="radio" name="radiofield" value="1" checked="checked" /> 男 &nbsp; &nbsp;
		                            	<input type="radio" name="radiofield" value="2"/> 女 &nbsp; &nbsp;
		                            	<input type="radio" name="radiofield" value="3"/> 未知 &nbsp; &nbsp;
		                            </span>
                        		</p>
                        		<p>
		                            <label>部门</label>
		                            <span class="field"><input type="text" name="department" class="input-large" placeholder=部门" /></span>
	                        	</p>
                        		<p>
		                            <label>年龄</label>
		                            <span class="field"><input type="text" id="spinner" name="employee_age" class="input-small input-spinner" /></span>
		                            <small class="desc">尝试使用鼠标滚轮。</small>
		                        </p>
		                        <p>
		                            <label>角色</label>
		                            <span class="field">
		                            <select name="role" class="uniformselect">
		                            	<option value="SHICHANGZHUGUAN">市场主管</option>
		                            	<option value="SHICHANGZHUANYUAN">市场专员</option>
		                                <option value="SHENGCHANZHUGUAN">生产主管</option>
		                                <option value="CAIGOUZHUGUAN">采购主管</option>
		                                <option value="ZHIJIANZHUGUAN">质检主管</option>
		                                <option value="CAIWUZHUGUAN">财务主管</option>
		                                <option value="WULIUZHUGUAN">物流主管</option>
		                                <option value="SHEJIZHUGUAN">设计主管</option>
		                            </select>
		                            </span>
		                        </p>
		                        <div class="par">
		                            <label>入职时间</label>
		                            <span class="field"><input id="datepicker" type="text" name="in_date" class="input-small" /></span>
		                        </div>
		                        <p>
		                            <label>直接领导</label>
		                            <span class="field"><input type="text" name="direct_leader" class="input-large" placeholder="直接领导" /></span>
	                        	</p>
	                        	<p>
		                            <label>手机1</label>
		                            <span class="field"><input type="text" name="phone1" class="input-large" placeholder="手机1" /></span>
	                        	</p>
	                        	<p>
		                            <label>手机2</label>
		                            <span class="field"><input type="text" name="phone2" class="input-large" placeholder="手机2" /></span>
	                        	</p>
	                        	<div class="par">
		                            <label>离职时间</label>
		                            <span class="field"><input id="datepicker1" type="text" name="out_date" class="input-small" /></span>
		                        </div>
		                        <p>
		                            <label>当前状态</label>
		                            <span class="field">
		                            <select name="state" class="uniformselect">
		                            	<option value="正常">正常</option>
		                                <option value="离职">离职</option>
		                                <option value="停职">停职</option> 
		                            </select>
		                            </span>
		                        </p>
		                        <p>
		                            <label>入职前公司</label>
		                            <span class="field"><input type="text" name="pre_company" class="input-large" placeholder="入职前公司" /></span>
	                        	</p>
	                        	<p>
		                            <label>入职前行业</label>
		                            <span class="field"><input type="text" name="pre_job" class="input-large" placeholder="入职前行业" /></span>
	                        	</p>
	                        	<p>
		                            <label>入职前职务</label>
		                            <span class="field"><input type="text" name="pre_role" class="input-large" placeholder="入职前职务" /></span>
	                        	</p>
	                        	<p>
		                            <label>学历</label>
		                            <span class="field">
		                            <select name="edu_background" class="uniformselect">
		                            	<option value="初中">初中</option>
		                                <option value="高中">高中</option>
		                                <option value="专科">专科</option> 
		                                <option value="本科">本科</option> 
		                                <option value="硕士">硕士</option> 
		                                <option value="博士">博士</option>  
		                            </select>
		                            </span>
		                        </p>
		                        <p>
		                            <label>毕业院校</label>
		                            <span class="field"><input type="text" name="school" class="input-large" placeholder="毕业院校" /></span>
	                        	</p>
	                        	<p>
		                            <label>专业</label>
		                            <span class="field"><input type="text" name="major" class="input-large" placeholder="专业" /></span>
	                        	</p>
	                        	<p>
		                            <label>籍贯</label>
		                            <span class="field"><input type="text" name="hometown" class="input-large" placeholder="籍贯" /></span>
	                        	</p>
	                        	<p>
		                            <label>南通住址</label>
		                            <span class="field"><input type="text" name="address" class="input-large" placeholder="南通住址" /></span>
	                        	</p>
	                        	<p>
		                            <label>身份证号码</label>
		                            <span class="field"><input type="text" name="id_card" class="input-large" placeholder="身份证号码" /></span>
	                        	</p>
				            </form>
			            </div>
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
