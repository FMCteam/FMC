</div><!--rightpanel-->
    
</div><!--mainwrapper-->

<%@include file="/common/js_file.jsp" %>
<%@include file="/common/js_form_file.jsp"%>

<script type="text/javascript" src="${ctx}/js/fmc/table.js"></script>
<script type="text/javascript">
function confirmDeleteCustomer(id){
	if(confirm("确定删除客户？")) {
		window.location.href="${ctx }/account/deleteCustomerSubmit.do?customer_id="+id;
	}
}
</script>
</body>
</html>
