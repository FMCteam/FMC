/*
 * Additional function for forms.html
 *	Written by ThemePixels	
 *	http://themepixels.com/
 *
 *	Copyright (c) 2012 ThemePixels (http://themepixels.com)
 *	
 *	Built for Katniss Premium Responsive Admin Template
 *  http://themeforest.net/category/site-templates/admin-templates
 */

jQuery(document).ready(function(){
	
	// Transform upload file
	jQuery('.uniform-file').uniform();
	
	// Date Picker
	jQuery("#datepicker").datepicker({dateFormat: 'yy-mm-dd',
		monthNames:['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
		dayNamesMin:['日','一', '二', '三', '四', '五', '六'] });
	
	jQuery("#datepicker1").datepicker({dateFormat: 'yy-mm-dd',
		monthNames:['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
		dayNamesMin:['日','一', '二', '三', '四', '五', '六'] });
	
	jQuery("#modify_key").click(function(){
		jQuery("#password_block").css("display","block");
	});
	
	jQuery("#agree_detail").click(function(){
		jQuery("#verify_val").val("true");
		jQuery("#verify_form").submit();
	});
	
	jQuery("#disagree_detail").click(function(){
		jQuery("#verify_val").val("false");
		jQuery("#verify_form").submit();
	});
	
	// Dual Box Select
	var db = jQuery('#dualselect').find('.ds_arrow button');	//get arrows of dual select
	var sel1 = jQuery('#dualselect select:first-child');		//get first select element
	var sel2 = jQuery('#dualselect select:last-child');			//get second select element
	
	sel2.empty(); //empty it first from dom.
	
	db.click(function(){
		var t = (jQuery(this).hasClass('ds_prev'))? 0 : 1;	// 0 if arrow prev otherwise arrow next
		if(t) {
			sel1.find('option').each(function(){
				if(jQuery(this).is(':selected')) {
					jQuery(this).attr('selected',false);
					var op = sel2.find('option:first-child');
					sel2.append(jQuery(this));
				}
			});	
		} else {
			sel2.find('option').each(function(){
				if(jQuery(this).is(':selected')) {
					jQuery(this).attr('selected',false);
					sel1.append(jQuery(this));
				}
			});		
		}
		return false;
	});	
	
	// Tags Input
	jQuery('#tags').tagsInput();

	// Spinner
	jQuery("#spinner").spinner({min: 16, max: 65, increment: 2});
	
	// Character Counter
	jQuery("#textarea2").charCount({
		allowed: 120,		
		warning: 20,
		counterText: 'Characters left: '	
	});
	
	
	// Select with Search
	jQuery(".chzn-select").chosen();

	// Textarea Autogrow
	jQuery('#autoResizeTA').autogrow();	
	
	
	// With Form Validation
	jQuery("#form1").validate({
		rules: {
			firstname: "required",
			lastname: "required",
			email: {
				required: true,
				email: true,
			},
			location: "required",
			selection: "required"
		},
		messages: {
			firstname: "Please enter your first name",
			lastname: "Please enter your last name",
			email: "Please enter a valid email address",
			location: "Please enter your location"
		},
		highlight: function(label) {
			jQuery(label).closest('.control-group').addClass('error');
	    },
	    success: function(label) {
	    	label
	    		.text('Ok!').addClass('valid')
	    		.closest('.control-group').addClass('success');
	    }
	});
	
	jQuery('#timepicker1').timepicker();
	
	
	// color picker
	if(jQuery('#colorpicker').length > 0) {
		jQuery('#colorSelector').ColorPicker({
			onShow: function (colpkr) {
				jQuery(colpkr).fadeIn(500);
				return false;
			},
			onHide: function (colpkr) {
				jQuery(colpkr).fadeOut(500);
				return false;
			},
			onChange: function (hsb, hex, rgb) {
				jQuery('#colorSelector span').css('backgroundColor', '#' + hex);
				jQuery('#colorpicker').val('#'+hex);
			}
		});
	}
	
});