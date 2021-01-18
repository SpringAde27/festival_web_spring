var myRoute = uri.substring(uri.lastIndexOf("/") + 1, uri.length);

openModal(myRoute);

history.replaceState({}, null, null);

function openModal(route) {
  if (uri === '' || history.state) {
    return; // 이후의 문장을 실행하지 않고, 종료
  }

  if (uri !== '' && flag == "success") {
    if (route === "register") {
      $(".modal-body").text("새글이 등록되었습니다.");
    } else if (route === "modify") {
      $(".modal-body").text("수정되었습니다.");
    } else if (route === "remove") {
      $(".modal-body").text("삭제되었습니다.");
    }

    $("#myModal").modal("show");
    timer();
  }

  if (uri !== '' && flag == "fail") {
    $("#alertModal .modal-body").text("요청하신 작업을 처리할 수 없습니다.");
    $("#alertModal").modal("show");
  }
}

function timer() {
  setTimeout(function() {
    $("#myModal").modal("hide");
    clearTimeout(timer);
  }, 1000);
}

// keyboard event
$("#myModal, #deleteModal, #alertModal").on("keyup", function(e) {
  if (e.key == "Enter" || e.keyCode == '13') {
    $(e.target).modal('hide');
    $("button[data-operate='confirm']").click();
  }
  if (e.key == "Escape" || e.keyCode == '27') {
    $(e.target).modal('hide');
  }
});

