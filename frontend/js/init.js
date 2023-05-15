/*-----------------------------------------------------------------------------------*/
/*  PORTFOLIO
/*-----------------------------------------------------------------------------------*/
$(window).load(function(){
  'use strict';
  var portfolio_selectors = $('.portfolio-filter li a');
  if(portfolio_selectors!='undefined'){
    var portfolio = $('.portfolio-items');
    portfolio.isotope({
      itemSelector : 'li',
      layoutMode : 'fitRows'
    });
    portfolio_selectors.on('click', function(){
      portfolio_selectors.removeClass('active');
      $(this).addClass('active');
      var selector = $(this).attr('data-filter');
      portfolio.isotope({ filter: selector });
      return false;
    });
  }
});

jQuery(function($) {
'use strict';
	$('.tile-progress .tile-header').matchHeight();

	var footerHeight = jQuery('#footer-wrapper').outerHeight();
	jQuery('#content-wrapper').css('margin-bottom', footerHeight );

	var windowsHeight = jQuery(window).height();
	var navHeight = jQuery('navbar-fixed-top').outerHeight();
	var newHeight = windowsHeight - navHeight + 30;
    jQuery('#main-slider').css('height', newHeight + 'px');
    jQuery('#single-page-slider').css('min-height', windowsHeight/3 + 'px');

	//goto top
	$('.gototop').click(function(event) {
		event.preventDefault();
		$('html, body').animate({
			scrollTop: $("body").offset().top
		}, 500);
	});	

	//Pretty Photo
	$("a[rel^='prettyPhoto']").prettyPhoto({
		social_tools: false,
		theme: 'light_square'
	});	

	jQuery('.prevbg').click(function(x) { x.preventDefault(); jQuery('body').data('backstretch').prev(); });
  	jQuery('.nextbg').click(function(x) { x.preventDefault(); jQuery('body').data('backstretch').prev(); });
});

/*-----------------------------------------------------------------------------------*/
/*  FANCY NAV
/*-----------------------------------------------------------------------------------*/
$(window).scroll(function() {
'use strict';
    let scroll_pos = 0;
    $(document).scroll(function() {
        let windowsHeight = $(window).height();
        let scroll_pos = $(this).scrollTop();
        let path = window.location.pathname.split("/");
        let [pathValue] = path.slice(-1);

        if (pathValue === "calculator.html" || pathValue === "report.html" || pathValue === "tip.html" || pathValue === "graph.html") {
            if (scroll_pos > 0) {
                $('.navbar-fixed-top').removeClass('nav-2');
            } else {
                $('.navbar-fixed-top').addClass('nav-2');
            }
        }
        else {
            if (scroll_pos > windowsHeight) {
                $('.navbar-fixed-top').removeClass('opaqued');
            } else {
                $('.navbar-fixed-top').addClass('opaqued');
            }
        }

    });

  	if  ( ($(document).height() - $(window).height()) - $(window).scrollTop() < 900 ){
	    $('#footer-wrapper').css('z-index','4');
	} else {
		$('#footer-wrapper').css('z-index','1');
	}

});

jQuery(document).ready(function($){
'use strict';
    let windowsHeight = $(window).height();
    let scroll_pos = $(this).scrollTop();
    let path = window.location.pathname.split("/");
    let [pathValue] = path.slice(-1);

    if (pathValue === "calculator.html" || pathValue === "report.html" || pathValue === "tip.html" || pathValue === "graph.html") {
        $('.navbar-fixed-top').addClass('nav-2');
    }
    else {
        $('.navbar-fixed-top').addClass('opaqued');
    }

    if  ( ($(document).height() - $(window).height()) - $(window).scrollTop() < 900 ){
        $('#footer-wrapper').css('z-index','4');
    } else {
        $('#footer-wrapper').css('z-index','1');
    }
});

// jQuery(document).ready(function($){
//     'use strict';
//     var windowsHeight = $(window).height();
//     var scroll_pos = $(this).scrollTop();
//     if(scroll_pos > windowsHeight) {
//         $('.navbar-fixed-top').removeClass('opaqued');
//     } else {
//         $('.navbar-fixed-top').addClass('opaqued');
//     }
//     if  ( ($(document).height() - $(window).height()) - $(window).scrollTop() < 1000 ){
//         $('#footer-wrapper').css('z-index','4');
//     } else {
//         $('#footer-wrapper').css('z-index','1');
//     }
// });


/*-----------------------------------------------------------------------------------*/
/*  SEARCH BAR
/*-----------------------------------------------------------------------------------*/
jQuery(document).ready(function($){
'use strict';
  jQuery('#search-wrapper, #search-wrapper input').hide();

	jQuery('span.search-trigger').click(function(){
		jQuery('#search-wrapper').slideToggle(0, function() {
			var check=$(this).is(":hidden");
			if(check == true) {
		  		jQuery('#search-wrapper input').fadeOut(600);
			} else {
				jQuery("#search-wrapper input").focus();
				jQuery('#search-wrapper input').slideDown(200);
			}
		});
	});

  $('#main-slider .carousel-content').flexVerticalCenter({ cssAttribute: 'padding-top' });

});

/*-----------------------------------------------------------------------------------*/
/*  NICESCROLL
/*-----------------------------------------------------------------------------------*/
 

/*-----------------------------------------------------------------------------------*/
/*  ANIMATE
/*-----------------------------------------------------------------------------------*/
jQuery(document).ready(function($){
'use strict';
  jQuery('.fade-up, .fade-down, .bounce-in, .flip-in').addClass('no-display');
  jQuery('.bounce-in').one('inview', function() { 
    jQuery(this).addClass('animated bounceIn appear');
  });
  jQuery('.flip-in').one('inview', function() { 
    jQuery(this).addClass('animated flipInY appear');
  });
  jQuery('.counter').counterUp({
    delay: 10,
    time: 1000
  });
  jQuery('.fade-up').one('inview', function() {
    jQuery(this).addClass('animated fadeInUp appear');
  });
  jQuery('.fade-down').one('inview', function() {
    jQuery(this).addClass('animated fadeInDown appear');
  });

});

/*-----------------------------------------------------------------------------------*/
/*  SNOOOOOOOOTH SCROLL - SO SMOOTH
/*-----------------------------------------------------------------------------------*/
$(function() {
'use strict';
	$('a[href*=#tip]:not([href=#])').click(function() {
        var path = window.location.pathname.split("/");
        var [pathValue] = path.slice(-1);


        if (location.pathname.replace(/^\//, '') == this.pathname.replace(/^\//, '') && location.hostname == this.hostname) {
            var target = $(this.hash);
            target = target.length ? target : $('[name=' + this.hash.slice(1) + ']');
            if (target.length) {
                $('html,body').animate({
                    scrollTop: target.offset().top
                }, 1000);
                return false;
            }
        }

	});
});

/*-----------------------------------------------------------------------------------*/
/*  CAROUSEL
/*-----------------------------------------------------------------------------------*/
$(document).ready(function() {
'use strict';
  //Set the carousel options
  $('#quote-carousel').carousel({
    pause: true,
    interval: 4000,
  });

	$('#scroller').carousel({
	    pause: true,
	    interval: 4000,
	});

});

/*-----------------------------------------------------------------------------------*/
/*  CONTACT FORM
/*-----------------------------------------------------------------------------------*/
jQuery(document).ready(function($){
'use strict';

  $('#contactform').submit(function(){
    var action = $(this).attr('action');
    $("#message").slideUp(750,function() {
    $('#message').hide();
    $('#submit').attr('disabled','disabled');
    $.post(action, {
      name: $('#name').val(),
      email: $('#email').val(),
      website: $('#website').val(),
      comments: $('#comments').val()
    },
      function(data){
        document.getElementById('message').innerHTML = data;
        $('#message').slideDown('slow');
        $('#submit').removeAttr('disabled');
        if(data.match('success') != null) $('#contactform').slideUp('slow');
        $(window).trigger('resize');
      }
    );
    });
    return false;
  });
  
});

/*-----------------------------------------------------------------------------------*/
/*  PRELOADER
/*-----------------------------------------------------------------------------------*/
jQuery(document).ready(function($){
'use strict';
  $(window).load(function(){
    $('#preloader').fadeOut('slow',function(){$(this).remove();});
  });
});

// window.alert = function(msg, callback) {
//     var div = document.createElement("div");
//     div.innerHTML = "<style type=\"text/css\">"
//         + ".nbaMask { position: fixed; z-index: 1000; top: 0; right: 0; left: 0; bottom: 0; background: rgba(0, 0, 0, 0.5); }                                                                                                                                                                       "
//         + ".nbaMaskTransparent { position: fixed; z-index: 1000; top: 0; right: 0; left: 0; bottom: 0; }                                                                                                                                                                                            "
//         + ".nbaDialog { position: fixed; z-index: 5000; width: 80%; max-width: 300px; top: 20%; left: 50%; box-shadow: rgba(0, 0, 0, 0.18) 0px 2px 4px; -webkit-transform: translate(-50%, -50%); transform: translate(-50%, -50%); background-color: #fff; text-align: center; border-radius: 8px; overflow: hidden; opacity: 1; color: white; }"
//         + ".nbaDialog .nbaDialogHd { padding: .2rem .27rem .08rem .27rem; background: #626E64;}                                                                                                                                                                                                                         "
//         + ".nbaDialog .nbaDialogHd .nbaDialogTitle { font-size: 28px; font-weight: 400; }                                                                                                                                                                                                           "
//         + ".nbaDialog .nbaDialogBd { padding: .8rem .8rem .8rem .8rem; font-size: 18px; line-height: 1.3; font-weight: normal; word-wrap: break-word; word-break: break-all; color: #FFFFFF; background: #626E64}                                                                                                                                          "
//         + ".nbaDialog .nbaDialogFt { position: relative; line-height: 48px; font-size: 17px; display: -webkit-box; display: -webkit-flex; display: flex; }                                                                                                                                          "
//         + ".nbaDialog .nbaDialogFt:after { content: \" \"; position: absolute; left: 0; top: 0; right: 0; height: 1px; border-top: 1px solid #e6e6e6; color: #e6e6e6; -webkit-transform-origin: 0 0; transform-origin: 0 0; -webkit-transform: scaleY(0.5); transform: scaleY(0.5); }               "
//         + ".nbaDialog .nbaDialogBtn { display: block; -webkit-box-flex: 1; -webkit-flex: 1; flex: 1; color: #464F48; font-weight: bold; text-decoration: none; -webkit-tap-highlight-color: transparent; position: relative; margin-bottom: 0; }                                                                       "
//         + ".nbaDialog .nbaDialogBtn:after { content: \" \"; position: absolute; left: 0; top: 0; width: 1px; bottom: 0; border-left: 1px solid #e6e6e6; color: #e6e6e6; -webkit-transform-origin: 0 0; transform-origin: 0 0; -webkit-transform: scaleX(0.5); transform: scaleX(0.5); }             "
//         + ".nbaDialog a { text-decoration: none; -webkit-tap-highlight-color: transparent; }"
//         + "</style>"
//         + "<div id=\"dialogs2\" style=\"display: none\">"
//         + "<div class=\"nbaMask\"></div>"
//         + "<div class=\"nbaDialog\">"
//         // + "    <div class=\"nbaDialogHd\">"
//         // + "        <strong class=\"nbaDialogTitle\"></strong>"
//         // + "    </div>"
//         + "    <div class=\"nbaDialogBd\" id=\"dialog_msg2\">Information</div>"
//             // + "    <div class=\"nbaDialogHd\">"
//             // + "        <strong class=\"nbaDialogTitle\"></strong>"
//             // + "    </div>"
//         + "    <div class=\"nbaDialogFt\">"
//         + "        <a href=\"javascript:;\" class=\"nbaDialogBtn nbaDialogBtnPrimary\" id=\"dialog_ok2\">YES</a>"
//         + "    </div></div></div>";
//     document.body.appendChild(div);
//
//     var dialogs2 = document.getElementById("dialogs2");
//     dialogs2.style.display = 'block';
//
//     var dialog_msg2 = document.getElementById("dialog_msg2");
//     dialog_msg2.innerHTML = msg;
//
//     var dialog_ok2 = document.getElementById("dialog_ok2");
//     dialog_ok2.onclick = function() {
//         dialogs2.style.display = 'none';
//         callback();
//     };
// };

$(window).load(function() {
    'use strict';
    let path = window.location.pathname.split("/");
    let [pathValue] = path.slice(-1);
    if (pathValue === 'report.html') {
        let submitStatus = window.sessionStorage.getItem('submit-status');
        let emission = window.sessionStorage.getItem('total_emission');
        if (emission === null && submitStatus === null) {
            // window.location.href = "calculator.html";
            Swal.fire({
                title: 'Warning!',
                text: 'You should fill up the survey before moving to the recommendations',
                icon: 'warning',
                confirmButtonText: 'GO TO SURVEY',
                confirmButtonColor: '#BCD1BF'
            }).then((result) => {
                if (result.isConfirmed) {
                    window.location.href = "calculator.html";
                }
                else {
                    window.location.href = "index.html";
                }
            })
        }
        else if (submitStatus ===null) {
            window.location.href = "report.html";
        }
    }

});

function setRecommendationAlert() {
    let emission = window.sessionStorage.getItem('total_emission');
    if (emission === null) {
        // window.location.href = "calculator.html";
        Swal.fire({
            title: 'Warning!',
            text: 'You should fill up the survey before moving to the recommendations',
            icon: 'warning',
            confirmButtonText: 'GO TO SURVEY',
            confirmButtonColor: '#BCD1BF'
        }).then((result) => {
            if (result.isConfirmed) {
                window.location.href = "calculator.html";
            }
        })
    }
    else {
        window.location.href = "report.html";
    }

}