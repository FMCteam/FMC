$(function() {
	$("table.list .search-query").keyup( //change
			function() {
				
				if ($(this).val() != "") {
					$("table tbody tr").hide().filter(
							":contains('" + ($(this).val()) + "')").show();
					var i=$("table tbody tr:not(:hidden)").length;
					$("table.list span.number").text(i);
				}else{
					$("table tbody tr").show();
					var i=$("table tbody tr:not(hidden)").length;
					$("table.list span.number").text(i);
				}
			});

});
