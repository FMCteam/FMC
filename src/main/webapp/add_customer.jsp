<!DOCTYPE html>

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
    <script type="text/javascript" src="js/bootstrap.min.js"></script>
    <script type="text/javascript" src="js/bootstrap-timepicker.min.js"></script>
    <script type="text/javascript" src="js/jquery.uniform.min.js"></script>
    <script type="text/javascript" src="js/jquery.validate.min.js"></script>
    <script type="text/javascript" src="js/jquery.tagsinput.min.js"></script>
    <script type="text/javascript" src="js/jquery.autogrow-textarea.js"></script>
    <script type="text/javascript" src="js/charCount.js"></script>
    <script type="text/javascript" src="js/colorpicker.js"></script>
    <script type="text/javascript" src="js/ui.spinner.min.js"></script>
    <script type="text/javascript" src="js/chosen.jquery.min.js"></script>
    <script type="text/javascript" src="js/jquery.cookie.js"></script>
    <script type="text/javascript" src="js/modernizr.min.js"></script>
    <script type="text/javascript" src="js/custom.js"></script>
    <script type="text/javascript" src="js/forms.js"></script>




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
                <li class="dropdown"><a href=""><span class="iconfa-th-list"></span> 人事部</a>
                    <ul>
                        <li><a href="#">添加员工</a></li>
                        <li><a href="#">查看员工</a></li>
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
                    添加用户页面
                    <!--  如果是其它页面，这里是填充具体的内容。 -->






                    <div class="widget">
                    <h4 class="widgettitle">Form Elements</h4>
                    <div class="widgetcontent">
                    <form class="stdform" action="customer/add.do  " method="post">


                        <p>
                            <label>客户登录名：</label>
                            <span class="field"><input type="text" name="user_name"   class="input-medium" placeholder="user_name"></span>

                        </p>

                        <p>
                            <label>客户密码：</label>
                            <span class="field"><input type="text" name="user_password" value="123456"  class="input-medium" placeholder="user_password"></span>

                        </p>


                    <p>
                        <label>客户姓名：</label>
                        <span class="field"><input type="text" name="customer_name"   class="input-medium" placeholder="customer_name"></span>

                    </p>

                    <p>
                        <label>客户电话：</label>
                        <span class="field"><input type="text" name="customer_phone"   class="input-medium" placeholder="customer_phone"></span>

                    </p>


                    <p>
                        <label>客户邮箱：</label>
                        <span class="field"><input type="text" name="email"   class="input-medium" placeholder="email"></span>

                    </p>

                    <p>
                        <label>客户QQ：</label>
                        <span class="field"><input type="text" name="qq"   class="input-medium" placeholder="qq"></span>

                    </p>


                    <p>
                        <label>省份</label>
                        <span class="field"><input type="text" name="province"  class="input-medium" placeholder="province"></span>
                    </p>

                    <p>
                        <label>城市</label>
                        <span class="field"><input type="text" name="city"   class="input-medium"  placeholder="city"></span>
                    </p>


                    <p>
                        <label>公司编号：</label>
                        <span class="field"><input type="text" name="company_id"  class="input-medium" placeholder="company_id"></span>
                    </p>

                    <p>
                        <label>公司名称</label>
                        <span class="field"><input type="text" name="company_name"   class="input-large" placeholder="company_name"></span>
                    </p>

                    <p>
                        <label>公司电话：</label>

                        <span class="field"><input type="text" name="company_phone" class="input-medium" placeholder="company_phone"></span>
                    </p>

                    <p>
                        <label>公司传真：</label>

                        <span class="field"><input type="text" name="company_fax"  class="input-medium" placeholder="company_fax"></span>
                    </p>


                    <p>
                        <label>公司地址</label>
                        <span class="field"><input type="text" name="company_address"  class="input-xlarge" placeholder="company_address"></span>
                    </p>



                    <p>
                        <label>采购联系人</label>
                        <span class="field"><input type="text" name="buy_contact"   class="input-medium" placeholder="buy_contact"></span>
                    </p>


                    <p>
                        <label>采购联系人电话<b>1</b></label>
                        <span class="field"><input type="text" name="contact_phone_1"  class="input-medium" placeholder="contact_phone_1"></span>
                    </p>


                    <p>
                        <label>采购联系人电话<b>2</b></label>
                        <span class="field"><input type="text" name="contact_phone_2"   class="input-medium" placeholder="contact_phone_2"></span>
                    </p>

                    <p>
                        <label>老板姓名</label>
                        <span class="field"><input type="text" name="boss_name"  class="input-medium" placeholder="boss_name"></span>
                    </p>

                    <p>
                        <label>老板电话</label>
                        <span class="field"><input type="text" name="boss_phone"    class="input-medium" placeholder="boss_phone"></span>
                    </p>

                    <p>
                        <label>网址</label>
                        <span class="field"><input type="text" name="website_url"  class="input-large" placeholder="website_url"></span>
                    </p>

                    <p>
                        <label>网站类型</label>
                            <span class="field">
                            <select name="website_type"   class="uniformselect">
                                <option value="">独立网站</option>
                                <option value="">天猫店</option>
                                <option value="">淘宝店</option>
                                <option value="">京东</option>
                                <option value="">优衣库</option>
                                <option value="">零号店</option>
                            </select>
                            </span>
                    </p>



                    <div class="par">
                        <label>注册日期</label>
                        <span class="field"><input id="datepicker" type="text" name="register_date"  class="input-medium "></span>
                    </div>


                        <p>
                            <label>注册业务员</label>
                            <span class="field"><input type="text" disabled="disabled"  name="register_employee_id"  class="input-large" placeholder="register_employee_id"></span>
                        </p>


                    <p class="stdformbutton">
                        <button class="btn btn-primary">Submit Button</button>
                    </p>















                    </form>
                    </div><!--widgetcontent-->
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
