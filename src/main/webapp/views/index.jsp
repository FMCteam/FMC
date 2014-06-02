		<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
		
		<%@include file="/common/header.jsp" %>
        
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
                <tr>
                <form action="${ctx }/order/downloadCadSubmit.do" method="post" 
				nctype="multipart/form-data">
				<span style="margin-right: 20px;font-size: 20px">智造链询价单原始单据</span>
				<span>上传时间:2014-06-01</span>
				<input style="margin-left: 80px" type="submit" class="btn btn-primary btn-rounded" value="下载"/>
				</form>
                </tr>
                 <tr>
                <form action="${ctx }/order/downloadCadSubmit.do" method="post" 
				nctype="multipart/form-data">
				<span style="margin-right: 20px;font-size: 20px">智造链</span>
				<span>上传时间:2014-06-01</span>
				<input style="margin-left: 80px" type="submit" class="btn btn-primary btn-rounded" value="下载"/>
				<input type="hidden" name="cadUrl" value="${orderInfo.designCad.cadUrl}" />
				</form>
                </tr>
        			这里是公司介绍等。
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
        
        
        <%@include file="/common/footer.jsp" %>
    