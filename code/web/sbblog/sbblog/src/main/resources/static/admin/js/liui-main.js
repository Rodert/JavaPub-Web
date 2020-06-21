/**
 * liui-main.js
 * author:liyt
 * version:1.0
 * 
 * 2017-05-01
 */

/**
 * 页面加载和大小变化时，计算页面高度 根据宽度控制左侧菜单隐藏、显示
 */
$(window).bind("load resize", function() {

	var neg = $('.main-header').outerHeight();
	var window_height = $(window).height();
	var sidebar_height = $(".left-sidebar").height();
	// if (window_height >= sidebar_height) {
	// 	$(".main-content").css('min-height', window_height - neg);
	// } else {
	// 	$(".main-content").css('min-height', sidebar_height);
	// }
	if ($(window).width() < 720) {
		$(".main-content ,.main-footer").css({
			'margin-left' : '0px'
		});
		$('.main-left').css({
			'margin-left' : '-230px'
		});
		$('.left-sidebar').hide();
	} else {
		$(".main-content ,.main-footer").css({
			'margin-left' : '230px'
		});
		$('.main-left').css({
			'margin-left' : '0px'
		});

		$('.left-sidebar').show();
	}
});

function selectMenuByID(id){
	var element = $("#" + id).addClass('active').parent();
	 while (true) {
	        if (element.is('li')) {
				element.parent().removeClass("hidden");
	            element = element.parent().addClass('in').parent().addClass("active");
	        } else {
	            break;
	        }
	    }
}
var Script = function() {
	$('#left-menu').metisMenu();
	// $(function() {
	// 	var url = window.location;
	// 	var element = $('ul.nav a').filter(function() {
	// 		return this.href == url;
	// 	}).addClass('active').parent();
	//
	// 	while (true) {
	// 		if (element.is('li')) {
	// 			element = element.parent().addClass('in').parent();
	// 			element.addClass('active');
	// 		} else {
	// 			break;
	// 		}
	// 	}
	//
	// 	$(window).resize();
	// });
	/* panel工具栏中，折叠/关闭按钮事件 */
	jQuery('.panel .tools .fa-chevron-down')
			.click(
					function() {
						var el = jQuery(this).parents(".panel").children(
								".panel-body");
						if (jQuery(this).hasClass("fa-chevron-down")) {
							jQuery(this).removeClass("fa-chevron-down")
									.addClass("fa-chevron-up");
							el.slideUp(200);
						} else {
							jQuery(this).removeClass("fa-chevron-up").addClass(
									"fa-chevron-down");
							el.slideDown(200);
						}
					});
	jQuery('.panel .tools .fa-times').click(function() {
		jQuery(this).parents(".panel").parent().remove();
	});
	/* 左侧折叠展开按钮控制 */
	$('.sidebar-toggle').click(function() {
		if ($(".left-sidebar").is(":visible") == true) {
			$(".main-content, .main-footer").css({
				'margin-left' : '0px'
			});
			$('.main-left').css({
				'margin-left' : '-230px'
			});

			$('.left-sidebar').hide();
		} else {
			$(".main-content ,.main-footer").css({
				'margin-left' : '230px'
			});
			$('.main-left').css({
				'margin-left' : '0px'
			});

			$('.left-sidebar').show();
		}
	});
	/* 设置栏目 */
	$('#config-tool .config-tool-bar').click(function() {
		if ($("#config-tool").hasClass('closed')) {
			$("#config-tool").removeClass("closed");
		} else {
			$("#config-tool").addClass("closed");
		}
	});
	$("#config-fixed-header").click(function() {
		if ($(this).is(':checked')) {
			$('body').addClass('fixed-header');
		} else {
			$('body').removeClass('fixed-header');
			$("#config-fixed-left").prop("checked", false);
			$('body').removeClass('fixed-left');
		}
	});
	$("#config-fixed-left").click(function() {
		if ($(this).is(':checked')) {
			$("#config-fixed-header").prop('checked', true);
			$('body').addClass('fixed-header');
			$('body').addClass('fixed-left');
		} else {
			$('body').removeClass('fixed-left');
		}
	});
	$("#config-fixed-footer").click(function() {
		if ($(this).is(':checked')) {
			$('body').addClass('fixed-footer');
		} else {
			$('body').removeClass('fixed-footer');
		}
	});

	/* 皮肤颜色点击 */
	$("#skin-colors .skin-changer").click(function() {
		var skin = $(this).data('skin');
		$('body').removeClassPrefix('skin-');
		$('body').addClass(skin);
	});

	$.fn.removeClassPrefix = function(prefix) {
		this.each(function(i, el) {
			var classes = el.className.split(" ").filter(function(c) {
				return c.lastIndexOf(prefix, 0) !== 0;
			});
			el.className = classes.join(" ");
		});
		return this;
	};

	/* 左侧菜单滚动条 */
	$(function() {
		$('.left-sidebar').slimScroll({
			height : '100%'
		});
	})
}();