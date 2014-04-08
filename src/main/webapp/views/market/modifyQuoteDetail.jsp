<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%@include file="/common/header.jsp"%>


<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid" style="min-height:300px;">

			<!--  如果是其它页面，这里是填充具体的内容。 -->
			<h4 class="widgettitle">市场专员修改报价</h4>
			<div class="widgetcontent">
	            <form class="stdform" action="${ctx}/market/modifyQuoteSubmit.do" method="post">
	            	<p>
                        <label>订单号</label>
                        <span class="field"><input type="text" name="order_id" class="input-large" value="${quoteModel.quote.orderId }" readonly /></span>
                   	</p>
                   	<p>
                        <label>内部报价</label>
                        <span class="field"><input type="text" name="inner_price" class="input-large" value="${quoteModel.quote.innerPrice}" readonly /></span>
                   	</p>
                   	<p>
                        <label>外部报价</label>
                        <span class="field"><input type="text" name="outer_price" class="input-large" value="${quoteModel.quote.outerPrice}" /></span>
                   	</p>
                   	<input type="hidden" name="taskId" value="${quoteModel.task.id }" />
                   	<input type="hidden" name="processId" value="${quoteModel.task.processInstanceId }" />
                   	<p class="stdformbutton">
						<button class="btn btn-primary">提交修改</button>
					</p>
	            </form>
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

<%@include file="/common/js_file.jsp"%>


<!-- 这里引入你需要的js文件 -->
<script type="text/javascript" src="${ctx }/js/custom.js"></script>


<%@include file="/common/footer.jsp"%>
			