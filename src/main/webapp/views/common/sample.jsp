<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<table class="table table-striped table-bordered table-hover">
	<tr>
		<td rowspan="2">客户样衣</td>
		<td>提供样衣</td>
		<td colspan="2">邮寄时间</td>
		<td>快递名称</td>
		<td colspan="2">快递单号</td>
	</tr>
	<tr>
		<td>${orderInfo.order.hasPostedSampleClothes==0?'没有样衣':'' }
			${orderInfo.order.hasPostedSampleClothes==1?'未收到样衣':'' }
			${orderInfo.order.hasPostedSampleClothes==2?'收到样衣':'' }</td>
		<td colspan="2">${fn:substring(orderInfo.logistics.inPostSampleClothesTime,0,10) }</td>
		<td>${orderInfo.logistics.inPostSampleClothesType }</td>
		<td colspan="2">${orderInfo.logistics.inPostSampleClothesNumber }</td>
	</tr>
	<tr>
		<td rowspan="5">生产样衣</td>
		<td>制作样衣</td>
		<td colspan="2">邮寄时间</td>
		<td>快递名称</td>
		<td colspan="2">快递单号</td>
	</tr>
	<tr>
		<td>${orderInfo.order.isNeedSampleClothes==0?'否':'是' }</td>
		<td colspan="2">${fn:substring(orderInfo.logistics.sampleClothesTime,0,10) }</td>
		<td>${orderInfo.logistics.sampleClothesType }</td>
		<td colspan="2">${orderInfo.logistics.sampleClothesNumber }</td>
	</tr>
	<tr>
		<td>邮寄人</td>
		<td>手机</td>
		<td colspan="4">邮寄地址</td>
	</tr>
	<tr>
		<td>${orderInfo.logistics.sampleClothesName }</td>
		<td>${orderInfo.logistics.sampleClothesPhone }</td>
		<td colspan="4">${orderInfo.logistics.sampleClothesAddress }</td>
	</tr>
	<tr>
		<td>其他备注</td>
		<td colspan="5">${orderInfo.logistics.sampleClothesRemark }</td>

	</tr>
	<tr>
		<td>样衣信息</td>
		<td>样衣图片</td>
		<td colspan="2"><img
			src="${ctx}/${orderInfo.order.sampleClothesPicture}" alt="没有图片"></img></td>
		<td>参考图片</td>
		<td colspan="2"><img
			src="${ctx}/${orderInfo.order.referencePicture}" alt="没有图片"></img></td>
	</tr>
</table>