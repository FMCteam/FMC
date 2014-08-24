<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<table class="table table-striped table-bordered table-hover detail">
	<tr>
		<td class="span2 title" rowspan="2">客户样衣</td>
		<td class="span2 title">提供样衣</td>
		<td class="span3 title">邮寄时间</td>
		<td class="span2 title">快递名称</td>
		<td class="span3 title">快递单号</td>
	</tr>
	<tr>
		<td>${orderInfo.order.hasPostedSampleClothes==0?'没有样衣':''}
			${orderInfo.order.hasPostedSampleClothes==1?'未收到样衣':''}
			${orderInfo.order.hasPostedSampleClothes==2?'收到样衣':''}</td>
		<td>${fn:substring(orderInfo.logistics.inPostSampleClothesTime,0,10) }</td>
		<td>${orderInfo.logistics.inPostSampleClothesType }</td>
		<td>${orderInfo.logistics.inPostSampleClothesNumber }</td>
	</tr>
	<tr>
		<td class="title" rowspan="5">生产样衣</td>
		<td class="title" rowspan="2">制作样衣</td>
		<td class="title">邮寄人</td>
		<td class="title">手机</td>
		<td class="title">邮寄地址</td>
	</tr>
	<tr>
		<td>${orderInfo.logistics.sampleClothesName }</td>
		<td>${orderInfo.logistics.sampleClothesPhone }</td>
		<td>${orderInfo.logistics.sampleClothesAddress }</td>

	</tr>
	<tr>
		<td rowspan="2">${orderInfo.order.isNeedSampleClothes==0?'否':'是' }</td>
		<td class="title">邮寄时间</td>
		<td class="title">快递名称</td>
		<td class="title">快递单号</td>
	</tr>
	<tr>
		<td>${fn:substring(orderInfo.logistics.sampleClothesTime,0,10)}&nbsp</td>
		<td>${orderInfo.logistics.sampleClothesType}</td>
		<td>${orderInfo.logistics.sampleClothesNumber}</td>
	</tr>
	<tr>
		<td class="title">其他备注</td>
		<td colspan="3">${orderInfo.logistics.sampleClothesRemark }</td>
	</tr>
	<tr>
		<td class="title">样衣信息</td>
		<td class="title">样衣图片</td>
		<td><c:if test="${orderInfo.order.sampleClothesPicture!=null}">
				<img class="pimg" src="${ctx}/common/getPic.do?type=sample&orderId=${orderInfo.order.orderId}"
					style="max-height: 300px;" alt="样衣图片"></img>
			</c:if></td>
		<td class="title">参考图片</td>
		<td colspan="2"><c:if
				test="${orderInfo.order.referencePicture!=null}">
				<img id="fdd"
					src="${ctx}/common/getPic.do?type=reference&orderId=${orderInfo.order.orderId}"
					style="max-height: 300px;" value="dfd" alt="参考图片"></img>
			</c:if></td>
	</tr>
</table>
<div id="outerdiv" style="position:fixed;top:0;left:0;background:rgba(0,0,0,0.7);z-index:2;width:100%;height:100%;display:none;"><div id="innerdiv" style="position:absolute;"><img id="bigimg" style="border:5px solid #fff;" src="" /></div></div>
