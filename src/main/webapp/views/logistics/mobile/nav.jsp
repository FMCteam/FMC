<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<div class="navbar navbar-inverse navbar-fixed-top">
	<div class="navbar-inner">
		<div class="container">
			<button type="button" class="btn btn-navbar" data-toggle="collapse"
				data-target=".nav-collapse">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="brand" href="${ctx}/logistics/mobile/index.do">制造链FMC</a>
			<div class="nav-collapse collapse">
				<ul class="nav">
					<li class=""><a
						href="${ctx}/logistics/mobile/warehouseList.do">入库登记</a></li>
					<li class=""><a href="${ctx}/logistics/mobile/sendClothesList.do">发货扫描</a></li>
				</ul>
			</div>
		</div>
	</div>
</div>