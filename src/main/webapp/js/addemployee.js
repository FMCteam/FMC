jQuery(document).ready(function(){
	initProv("#department", "#role", "市场部","市场主管");
});

var _cityInfo = [{ "n": "市场部", "c": ["市场主管", "市场专员"] },

		{ "n": "设计部", "c": ["设计主管"] },

		{ "n": "采购部", "c": ["采购主管"] },

		{ "n": "生产部", "c": ["生产主管"] },

		{ "n": "财务部", "c": ["财务主管"] },

		{ "n": "物流部", "c": ["物流主管"] },

		{ "n": "质检部", "c": ["质检主管"] },
        
		
		{ "n": "毛衣制作部", "c": ["毛衣部主管"] },

		];



        function initProv(prov, city, defaultProv, defaultCity) {

            var provEl = jQuery("#department");

            var cityEl = jQuery("#role");

            var hasDefaultProv = (typeof (defaultCity) != 'undefined');

            var provHtml = '';

            //provHtml += '<option value=""></option>';

            for (var i = 0; i < _cityInfo.length; i++) {

                provHtml += '<option value="' + _cityInfo[i].n + '"' + ((hasDefaultProv && _cityInfo[i].n == defaultProv) ? ' selected="selected"' : '') + '>' + _cityInfo[i].n + '</option>';

            }

            provEl.html(provHtml);

            initCities(provEl, cityEl);

            provEl.change(function () {

                initCities(provEl, cityEl);

            });

        }



        function initCities(provEl, cityEl) {
		
            if (provEl.val() != '') {
				
				for(var j = 0; j < _cityInfo.length; j++){
					if(provEl.val() == _cityInfo[j].n){
						var cities = _cityInfo[j].c;
						var cityHtml = '';
						//cityHtml += '<option value=""></option>';
						for (var i = 0; i < cities.length; i++) {
							cityHtml += '<option value="' + getOptionValue(cities[i]) + '">' + cities[i] + '</option>';
						}
						cityEl.html(cityHtml);
					}
				}

            } else {

                cityEl.html('<option value=""></option>');

            }

        }
        
        function getOptionValue(role){
        	switch(role){
        		case "市场主管": return "marketManager";
        		case "市场专员": return "marketStaff";
        		case "设计主管": return "designManager";
        		case "采购主管": return "purchaseManager";
        		case "生产主管": return "produceManager";
        		case "物流主管": return "logisticsManager";
        		case "财务主管": return "financeManager";
        		case "质检主管": return "qualityManager";
        		case "毛衣部主管": return "SweaterMakeManager";
        		
        		//case "人事主管": return "humanManager";
        	}
        }
		
		

	