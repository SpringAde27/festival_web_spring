(function() {
  'use strict';
  window.addEventListener('load', function() {
    // Form Initialization
    if (currentPage === "register") {
      document.forms["myForm"].reset();
      document.getElementById("map").style.display = "none";
      document.getElementById("map").nextElementSibling.style.display = "none";
    }
/*
    // Fetch all the forms we want to apply custom style
    var inputs = document.getElementsByClassName('form-control');

    // Loop over each input and watch blur event
    var validation = Array.prototype.filter.call(inputs, function(input) {
      input.addEventListener('input', function(event) {
        // reset
        input.classList.remove('is-invalid');
        input.classList.remove('is-valid');

        if (input.checkValidity() === false) {
          input.classList.add('is-invalid');
        }
        else {
          input.classList.add('is-valid');
        }
      }, false);
    });
*/
    // Fetch all the forms we want to apply custom Bootstrap validation styles
    var forms = document.getElementsByClassName('needs-validation');

    // Loop over them and prevent submission
    var validation = Array.prototype.filter.call(forms, function(form) {
      // submit event
      form.addEventListener('submit', function(event) {
        if (form.checkValidity() === true) {
          event.preventDefault(); // 기본 이벤트 방지
          event.stopPropagation(); // 상위 엘리먼트로 전파되는 이벤트 방지

          $('#myModal').modal('show');

          // modal mouse event
          $("button[data-operate='confirm']").on('click', function(e) {
            $('#myModal').modal('hide');
            
            var str = "";
            
            $("#attachFileResult ul li").each(function(i, obj) {
              var obj = $(obj);
              str +="<input type='hidden' name='attachList["+i+"].uuid' value='"+obj.data("uuid")+"'/>";
              str +="<input type='hidden' name='attachList["+i+"].uploadPath' value='"+obj.data("path")+"'/>";
              str +="<input type='hidden' name='attachList["+i+"].fileName' value='"+obj.data("name")+"'/>";
            });
            
            $("#myForm").append(str).submit();
          });
          
          // modal keyboard event
          $("#myModal").on("keyup", function(e) {
            if (e.key == "Enter" || e.keyCode == '13') {
              $("button[data-operate='confirm']").click();
            }
            if (e.key == "Escape" || e.keyCode == '27') {
              $('#myModal').modal('hide');
            }
          });
        }
        
        if (form.checkValidity() === false) {
          event.preventDefault(); // 기본 이벤트 방지
          event.stopPropagation(); // 상위 엘리먼트로 전파되는 이벤트 방지
        }
        
        form.classList.add('was-validated');
      }, false);
      
      // reset event
      form.addEventListener('reset', function(event) {
        if (currentPage === 'register') {
          self.location = "list";
        } else if (currentPage === 'modify') {
          self.location = "read?page=" + page + "&amount=" + amount + "&searchType="+ searchType  + "&keyword=" + keyword + "&no=" + no;
        }
      }, false);
    });
  }, false); // addEventListener(load)
})();