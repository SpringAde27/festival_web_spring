/*
 * window.addEventListener("DOMContentLoaded", function() {}
 * - DOM 트리 완성-제어가능, 외부 리소스(img, style, script, etc) 로드 전
 * - 리소스를 기다릴 필요가 없는 경우가 많기에, 단순히 빠른 실행을 위함. (load 이벤트 보다 먼저 실행)
 * - ie8 이하에서 지원하지 않는다.
 * - jQuery >> $(document).ready(function() {}); 동일
 * 
 * window.onload = function () {}
 * - HTML 로드 완료 / DOM 트리 완성 / 모든 리소스(img, style, script, etc) 로드 완료 시점에 실행
 * 
 */

window.addEventListener("DOMContentLoaded", function() {
  // Bootstrap-datepicker Setting
  $('#fStartdate, #fEnddate').datepicker({
    format : 'yyyy-mm-dd',
    language : "ko",
    todayHighlight : true,
    autoclose : true,
  });

  // Bootstrap-datepicker Event
  $('#fStartdate, #fEnddate').datepicker().on('changeDate input', function(e) {
    checkTheDate();
  });

}); // DOMContentLoaded


// 시작날짜와 종료날짜 체크
function checkTheDate() {
  var startDate = new Date(document.getElementById("fStartdate").value);
  var endDate = new Date(document.getElementById("fEnddate").value);

  if (startDate.getTime() > endDate.getTime()) {
    document.getElementById("fEnddate").setCustomValidity($('.end-date-feedback').html("종료날짜가 시작날짜보다 빠를 수 없습니다."));
    document.getElementById("fEnddate").classList.add("is-invalid");
  } else if (startDate <= endDate) {
    document.getElementById("fEnddate").setCustomValidity("");
    document.getElementById("fEnddate").classList.remove("is-invalid");
  }
}
