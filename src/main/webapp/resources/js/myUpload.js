$(document).ready(function() {
  // 즉시 실행 함수
  if (currentPage !== 'register') {
    (function(){
      var fNo = no;
      // 첨부파일 목록 조회
      $.getJSON("getAttachList", {fNo : no}, function(arr) {
        showAttachFileResult(arr);
      });
    }());
  }

  // 첨부파일 등록
  $('#attachFile').on("change", function(e) {
    var myFiles = [];
    var formData = new FormData();
    var inputAttachFile = $("input[name='attachFile']");
    var files = inputAttachFile[0].files;
    
    for(var i=0; i < files.length; i++) {
      if( !checkAttachFile(files[i].name, files[i].size) ) {
        return false;
      }
      myFiles.push(files[i]);
    }
    
    // 파일 데이터
    for(var i = 0, size = myFiles.length; i < size; i++) {
       formData.append("attachFile", myFiles[i]);
    }
    
    $.ajax({
      url: "attachFile",
      type: "POST",
      processData: false, // query string 변환 여부 (기본값; application/x-www-form-urlencoded)
      contentType: false, // multipart/form-data 방식으로 전송하기 위해 false 지정
      data: formData,     // data transfer
      dataType: "json",   // return type
      success: function(data) {
        showAttachFileResult(data);
        $("input[name='attachFile']+label>img").animate({ height: '30px' }, 500);
      },
      error: function(e) {
        // console.log(e.responseText());
      }
    });
    
     /*
      // FormData의 value 확인
      for(let value of formData.values()) {
        console.log(value);
      }
     */
  }); // 첨부파일 등록

  // 첨부파일 템플릿
  var template = Handlebars.compile($("#imgPreviewTemplate").html());

  function showAttachFileResult(data) {
    if(!data || data.length == 0) {
      return;
    }
    
    // $("#attachFileResult ul li").remove();
    
    var fileInfo = getFileInfo(data);
    var html = template(fileInfo);
    
    $("#attachFileResult ul").append(html);
    $("#attachFileResult").addClass("show").removeClass("hide");
  }

  function getFileInfo(data) {
    var fileName, imgSrc, uploadPath;
    var ctx = window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
    var files = { item:[] };
    
    data.forEach(function(obj, i, arr) {
      fileName = obj.uploadPath.replace("s_", "");
      fileName = fileName.substr(fileName.indexOf("_") + 1);
      
      if(checkImageType(obj.fileName)) {
        imgSrc = "displayFile?fileName=" + obj.uploadPath;
      } else {
        imgSrc = ctx + "/resources/images/icons/document.png";
      }

      files.item.push({
        uuid: obj.uuid,
        fileName: fileName,
        imgSrc: imgSrc,
        uploadPath: obj.uploadPath
      });
    });
    
    return files;
  }
  
  // 첨부파일 삭제
  $("#attachFileResult").on("click", "button", function(e) {
    var targetFile = $(this).data("file");
    var targetLi = $(this).closest("li");
    
    $("#myDeleteModal").modal("show");
    
    $("#myDeleteModal button[data-operate='delete']").unbind('click').on('click', function(el) {
      el.stopPropagation();
      $("#myDeleteModal").modal('hide');
      effectFadeInOut(targetLi); // 화면에서만 삭제, 확인 버튼 시 삭제 완료
    });
    
    /*$.ajax({
      url: "deleteAttachFile",
      type: "POST",
      data: {fileName : targetFile},
      dataType: "text",
      success: function(data) {
        effectFadeInOut(targetLi);
      },
      error: function(request, status, error) {
        console.log(error);
      }
    });*/
  }); // 첨부파일 삭제

  // input File Form Upload
  $("label[for='attachImg'] + .custom-file > input").on("change", function(e) {
    var files = [];
    var targetFiles = e.target.files; // $(this)[0].files[i]
    
    for(var i = 0; i < targetFiles.length; i++) {
      if( !checkImageExtension(targetFiles[i].name, targetFiles[i].size, targetFiles[i].type) ) {
        resetInputFile();
        $('.custom-file-preview').addClass("hide disappear").removeClass("show appear");
        return false;
      }
    }
    
    if (e.target.files.length) {
      for (var i = 0; i < $(this)[0].files.length; i++) {
        files.push($(this)[0].files[i].name);
      }
      $(this).siblings(".custom-file-label:first-child").addClass("selected").html(files.join(', '));
      loadingPreview(this);
    }
  }); // input File Form Upload

}); // document ready


function effectFadeInOut(target) {
  if (target.hasClass('appear')) {
    target.addClass('disappear').removeClass('appear');
    setTimeout(function(){ target.remove() } ,500);
  }
}

// 업로드 가능한 파일 타입과 크기
var regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
var maxSize = 5242880; // 5MB

// ajax 첨부파일 유효성 체크
function checkAttachFile(fileName, fileSize) {
  if(fileSize >= maxSize) {
    $("#myAlert .alert-body").text('파일 사이즈를 초과하였습니다.');
    showAlert();
    return false;
  }
  
  if(regex.test(fileName)) {
    $("#myAlert .alert-body").text('업로드할 수 없는 파일이 포함되어 있습니다.');
    showAlert();
    return false;
  }
  return true;
}

// form 첨부파일 유효성 체크
function checkImageExtension(fileName, fileSize, fileType) {
  if(fileSize >= maxSize) {
    $("#myAlert .alert-body").text('파일 사이즈를 초과하였습니다.');
    showAlert();
    return false;
  }
  
  if(regex.test(fileName)) {
    $("#myAlert .alert-body").text('업로드할 수 없는 파일이 포함되어 있습니다.');
    showAlert();
    return false;
  }
  
  if(fileType.indexOf("image") < 0) {
    $("#myAlert .alert-body").text('이미지 파일만 업로드 할 수 있습니다.');
    showAlert();
    return false;
  }
  return true;
}

// 이미지 파일 확장자 체크
function checkImageType(fileName) {
  var pattern = /jpg|jpeg|png|gif/i;
  return fileName.match(pattern);
}

// loading preview
function loadingPreview(input) {
  if (input.getAttribute('id') === 'attachImg') {
    if (input.files && input.files[0]) {
      var reader = new FileReader();
      reader.onload = function (e) {
        $('.custom-file-preview .preview-image > img').attr('src', e.target.result);
        $('.custom-file-preview').addClass("show appear").removeClass("hide disappear");
      }
      reader.readAsDataURL(input.files[0]);
    }
  }
}

// remove preview
function removePreview() {
  var target = $('.custom-file-preview');
  
  if (target.hasClass('show appear')) {
    target.removeClass("show appear").addClass("disappear");
    setTimeout(function(){ target.addClass("hide") } , 300);
  } else {
    target.addClass("show appear").removeClass("hide disappear");
  }
  
  $('.custom-file-preview .preview-image > img').attr('src', '');
  $('input#fImgHidden').val('');
}

// reset input file form
function resetInputFile() {
  if (/(MSIE|Trident)/.test(navigator.userAgent)) {
    // ie 일때 input[type=file] init.
    $("input[name='attachImg']").replaceWith($("input[name='attachImg']").clone(true));
    $("input[name='attachImg'] + label").text("대표로 사용할 이미지를 선택하세요.");
  } else {
     // other browser 일때 input[type=file] init.
    $("input[name='attachImg']").val("");
    $("input[name='attachImg'] + label").text("대표로 사용할 이미지를 선택하세요.");
  }
  
  removePreview();
}

// show alert
function showAlert() {
  $('#myAlert').fadeTo(300, 1).slideDown(1000, function() {
    $('#myAlert').show();
  });
  
  setTimeout(function() {
    $("#myAlert").fadeTo(1000, 0).slideUp(1000, function () {
      $('#myAlert').hide();
    });
  }, 3000);
}
