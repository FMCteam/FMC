$(function() {
	$("table.list .search-query").keyup( //change
			function() {
				
				if ($(this).val() != "") {
					$("table tbody tr").hide().filter(
							":contains('" + ($(this).val()) + "')").show();
				}else{
					$("table tbody tr").show();
				}
			});

});
