		<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
		
		<%@include file="/common/header.jsp" %>

		<style   type="text/css">
			.iconfa-laptop:before{content:"\f109"}
		</style>
		
        <div class="pageheader">
           
            <div class="pageicon"><span class="iconfa-laptop" ></span></div>
            <div class="pagetitle">
                <h5>服装快速响应供应链</h5>
                <h1>智造链</h1>
            </div>
        </div><!--pageheader-->
        	
        <div class="maincontent">
            <div class="maincontentinner">
                <div class="row-fluid" style="min-height:300px;">
                <tr>
                <a class="btn btn-primary btn-rounded">公用支付宝帐号：36933145@qq.com</a>
                </tr>
                <tr>
                <a class="btn btn-primary btn-rounded">公用农业银行帐号：6228480424649506013 &nbsp&nbsp&nbsp  开户人:葛祖民</a>
                </tr>
                 <tr>
                <a class="btn btn-primary btn-rounded">公用的PAYPAL帐号：872104037@qq.com</a>
                </tr>
                <tr>
                <form action="${ctx }/downloadCadSubmit.do" method="post" 
				nctype="multipart/form-data">
				<span style="margin-right:80px;font-size: 20px">智造链询价单下载</span>
				<span>上传时间:2014-06-01</span>
				<input style="margin-left: 80px" type="submit" class="btn btn-primary btn-rounded" value="下载"/>
				<input type="hidden" name="cadUrl" value="D:/fmc/message/inquirySheet.doc" />
				</form>
                </tr>
                 <tr>
                <form action="${ctx }/downloadCadSubmit.do" method="post" 
				nctype="multipart/form-data">
				<span style="margin-right: 80px;font-size: 20px">智造链报价单下载</span>
				<span>上传时间:2014-06-01</span>
				<input style="margin-left: 80px" type="submit" class="btn btn-primary btn-rounded" value="下载"/>
				<input type="hidden" name="cadUrl" value="D:/fmc/message/priceSheet.doc" />
				</form>
                </tr>
                    <!--  如果是其它页面，这里是填充具体的内容。 -->
                </div><!--row-fluid-->
                
                <div class="footer">
                    <div class="footer-left">
                        <span>&copy; 2014. 江苏南通智造链有限公司.</span>
                    </div>
                  
                </div><!--footer-->
                
            </div><!--maincontentinner-->
        </div><!--maincontent-->
        
        <%@include file="/common/js_file.jsp" %>
        
        <!-- 这里引入你需要的js文件 -->
        <script type="text/javascript" src="${ctx }/js/custom.js"></script>
        <script type="text/javascript">

		</script>
        
        <%@include file="/common/footer.jsp" %>
    