<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/header.jsp"%>
    <div class="maincontent">
      <div class="maincontentinner">
        <div class="row-fluid" style="min-height:300px;"> 
          <!--  如果是其它页面，这里是填充具体的内容。 -->
          
          <table class="table table-striped">
            <tr>
              <th>生产验证</th>
            </tr>
            <tr>
              <td rowspan="2">业务信息</td>
              <td colspan="2">业务编号</td>
              <td>业务时间</td>
              <td>接单业务员</td>
              <td colspan="2">订单来源</td>
            </tr>
            <tr>
              <td colspan="2">22222222</td>
              <td class="order_time">2013-12-12</td>
              <td>李狗蛋</td>
              <td colspan="2"></td>
            </tr>
            <tr>
              <td rowspan="3">客户信息</td>
              <td>客户编号</td>
              <td>姓名</td>
              <td>公司</td>
              <td>传真</td>
              <td>手机1</td>
              <td>手机2</td>
            </tr>
            <tr>
              <td>222222222</td>
              <td>不要叫我大王</td>
              <td>公不公司</td>
              <td>5435-45435-4324</td>
              <td>1523525325325</td>
              <td>1154352345434</td>
            </tr>
            <tr>
              <td>公司地址</td>
              <td colspan="5">XX省XX市XX区XX路XX号</td>
            </tr>
            <tr>
              <td rowspan="6">款式信息</td>
              <td><label>款式名称</label></td>
              <td colspan="2">款式性别</td>
              <td colspan="2">款式季节</td>
              <td colspan="2"></td>
            </tr>
            <tr>
              <td></td>
              <td colspan="2"></td>
              <td colspan="2"></td>
              <td colspan="2"></td>
            </tr>
            <tr>
              <td>面料类型</td>
              <td colspan="5"></td>
            </tr>
            <tr>
              <td>特殊工艺</td>
              <td colspan="5"></td>
            </tr>
            <tr>
              <td>其他说明</td>
              <td colspan="5"></td>
            </tr>
            <tr>
              <td>参考链接</td>
              <td colspan="5"></td>
            </tr>
            <tr>
              <td rowspan="2">加工信息</td>
              <td><span class="required">*</span>加工件数</td>
              <td colspan="2">最迟交货时间</td>
              <td colspan="2">完工时间（天）</td>
              <td>码数</td>
            </tr>
            <tr>
              <td></td>
              <td colspan="2"></td>
              <td colspan="2"></td>
              <td></td>
            </tr>
            <tr>
              <td rowspan="2">面料</td>
              <td colspan="2">面料名称</td>
              <td colspan="2">面料克重</td>
              <td colspan="2">操作</td>
            </tr>
            <tr>
              <td colspan="6" class="innertable"></td>
            </tr>
            <tr>
              <td rowspan="2">辅料</td>
              <td colspan="2">辅料名称</td>
              <td colspan="2">辅料要求</td>
              <td colspan="2">操作</td>
            </tr>
            <tr>
              <td colspan="6" class="innertable"></td>
            </tr>
            <tr>
              <td rowspan="2">客户样衣</td>
              <td>提供样衣</td>
              <td colspan="2">邮寄时间</td>
              <td>快递名称</td>
              <td colspan="2">快递单号</td>
            </tr>
            <tr>
              <td></td>
              <td colspan="2"></td>
              <td></td>
              <td colspan="2"></td>
            </tr>
            <tr>
              <td rowspan="5">生产样衣</td>
              <td>制作样衣</td>
              <td colspan="2">邮寄时间</td>
              <td>快递名称</td>
              <td colspan="2">快递单号</td>
            </tr>
            <tr>
              <td></td>
              <td colspan="2"></td>
              <td></td>
              <td colspan="2"></td>
            </tr>
            <tr>
              <td>邮寄人</td>
              <td>手机</td>
              <td colspan="4">邮寄地址</td>
            </tr>
            <tr>
              <td></td>
              <td></td>
              <td colspan="4"></td>
            </tr>
            <tr>
              <td>其他备注</td>
              <td colspan="5"></td>
            </tr>
            <tr>
              <td>样衣信息</td>
              <td>样衣图片</td>
              <td colspan="2"></td>
              <td>参考图片</td>
              <td colspan="2"></td>
            </tr>
            <tr>
              <td rowspan="2">生产验证</td>
              <td colspan="1">备注信息</td>
              <td colspan="5"><form method="post" action="">
                  <textarea class="span12 vertical" rows="5" >  </textarea>
                  <input type="hidden" value="通过" name="state"  />
                </form></td>
            </tr>
            <tr>
              <td colspan="1">操作结果</td>
              <td colspan="5"><a onClick="approved()">通过</a><span>&nbsp&nbsp&nbsp </span><a onClick="noapproved()">不通过</a></td>
            </tr>
          </table>
        </div>
        <!--row-fluid-->
        
        <div class="footer">
          <div class="footer-left"> <span>&copy; 2014. 江苏南通智造链有限公司.</span> </div>
        </div>
        <!--footer--> 
      </div>
      <!--maincontentinner--> 
    </div>
    <!--maincontent-->
    
    <%@include file="/common/js_file.jsp"%>
    <%@include file="/common/js_form_file.jsp"%>
    <link rel="stylesheet" href="${ctx}/css/order/add_order.css">
    <script type="text/javascript">
	function approved(){
		$("form").submit();
	}
	function noapproved(){
		$("input[name='state']").val("不通过");
		$("form").submit();
	}
</script>
    <%@include file="/common/footer.jsp"%>
