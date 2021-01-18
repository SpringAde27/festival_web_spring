$(document).ready(function () {
  // 페이지 이동에 따른 메뉴 강조
  var navItem = ['notice', 'festival', 'gallery', 'market'];
  var currentPagePath = document.location.pathname.split('/')[2];
  // console.log("currentPagePath >> " , currentPagePath);
  
  $(navItem).each(function(i, el) {
    if(el == currentPagePath) {
      $('.nav-item a').eq(i).parents('li').addClass('active').siblings().removeClass('active');
    }
  });

/*
  document.querySelectorAll('.nav-item a').forEach( function(el, idx) {
    el.addEventListener("click", function(e) {
    // $('.nav-item').find('a[href *="' + document.location.pathname + '"]').parents('li').addClass('active').siblings().removeClass('active');
      if( el['href'].split('/')[4] === currentPagePath[4] ) {
         $(this).parents('li').addClass('active').siblings().removeClass('active');
      }
    }, false);
  });
*/

  // Bootstrap Alert Close Button
  $('.alert').on('close.bs.alert', function (e) {
    e.preventDefault();
    $(this).fadeOut(500);
  });
}); // DOMContentLoaded