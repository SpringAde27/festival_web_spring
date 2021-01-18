<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>모두의 축제</title>
<meta content="" name="descriptison">
<meta content="" name="keywords">
<%@ include file="../include/top_path.jsp" %>
<link href="${pageContext.request.contextPath}/resources/css/festival.css" rel="stylesheet" type="text/css" />
<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/4.7.6/handlebars.min.js"></script>
<style type="text/css">
/* owl-carousel */
.owl-next:focus,
.owl-prev:focus {
  outline: none;
}
.owl-carousel .owl-nav button.owl-prev {
  position: absolute;
  left: 5px;
  top: 40%;
}
.owl-carousel .owl-nav button.owl-next {
  position: absolute;
  right: 5px;
  top: 40%;
}
.owl-carousel .owl-nav button.owl-prev>img,
.owl-carousel .owl-nav button.owl-next>img {
  margin: 0.2rem 0.1rem;
}
.owl-theme .owl-nav [class*=owl-]:hover {
  line-height: 1rem !important;
  border-radius: 0.2rem !important;
  background: none !important;
}
.owl-carousel .owl-item img {
  min-height: 250px !important;
  max-height: 300px !important;
  object-fit: contain ;
}

/* bootstrap table */
.table td, .table th {
  border-bottom: 1px solid #dee2e6;
}
.table td {
  text-align: left;
  padding-left: 2rem;
}

/* 해상도 992이상 1199이하 */
@media all and (min-width:992px) and (max-width:1199px) {
  .table td, .table th {
    padding: .7rem;
  }
}

/* 해상도 1200이상 */
@media all and (min-width:1200px) {
  table td, .table th {
    padding: 1rem;
  }
}
</style>
</head>
<body>
  <%@ include file="../include/nav.jsp" %>

  <div class="container py-3">
    <section class="page-header">
      <h1>축제 정보</h1>
    </section>

    <section class="row justify-content-center">
      <div class="col-md-10">
        <article class="row mb-3">
          <!-- left info -->
          <div class="col-lg-6 col-md-12 text-center">
            <div class="owl-carousel owl-theme mb-3">
              <c:if test="${ empty festivalVO.fImg }">
	              <div class="item">
	                <img src="${pageContext.request.contextPath}/resources/images/icons/default-01.png" style="object-fit: contain; height: 200px; width: 100%;"/>
	              </div>
              </c:if>
              
              <c:if test="${ !empty festivalVO.fImg }">
                <div class="item">
                  <img src="displayFile?fileName=${ festivalVO.fImg }" class="imgError"/>
                </div>
                <c:if test="${ !empty attachments }">
                  <c:forEach var="attachment" items="${ attachments }">
                    <div class="item">
                      <img src="displayFile?fileName=${ attachment }" class="imgError"/>
                    </div>
                  </c:forEach>
                </c:if>
                
              </c:if>
            </div>
          </div>
          <!-- right info -->
          <div class="col-lg-6 col-md-12 text-center">
            <table class="table">
              <colgroup>
                <col class="bg-light" width="30%" />
                <col />
              </colgroup>
              <tr>
                <th>축제</th>
                <td>${ festivalVO.fTitle }</td>
              </tr>
              <tr>
                <th>주제</th>
                <td>${ festivalVO.fTheme }</td>
              </tr>
              <tr>
                <th>기간</th>
                <td>
                  <fmt:formatDate value="${festivalVO.fStartdate}" pattern="yyyy.MM.dd" /> - <fmt:formatDate value="${festivalVO.fEnddate}" pattern="yyyy.MM.dd"/>
                </td>
              </tr>
              <tr>
                <th>주관</th>
                <td>${ festivalVO.fHost }</td>
              </tr>
              <tr>
                <th>문의</th>
                <td>${ festivalVO.fPhone }</td>
              </tr>
              <tr>
                <th>위치</th>
                <td>${ festivalVO.fAddr1 }${ festivalVO.fAddr2 }</td>
              </tr>
            </table>
          </div>
        </article>

        <article class="row justify-content-center">
          <div class="col-sm-12 py-2 form-group">
            <strong class="d-block pb-1" style="border-bottom: 1px solid #dee2e6;">
              <img src="${pageContext.request.contextPath}/resources/images/icons/bullet-check.png" style="display: inline-block; height: 1.5rem; padding-bottom: 0.25rem;" />
              <span>&nbsp;&nbsp;상세정보</span>
            </strong>
            <div class="mt-3 ${ !empty festivalVO.fContent ? 'd-block' : 'd-none' }">${ festivalVO.fContent }</div>
          </div>

          <div class="col-sm-12 py-2 form-group">
            <strong class="d-block pb-1" style="border-bottom: 1px solid #dee2e6;" >
              <img src="${pageContext.request.contextPath}/resources/images/icons/bullet-check.png" style="display: inline-block; height: 1.5rem; padding-bottom: 0.25rem;" />
              <span>&nbsp;&nbsp;찾아오시는 길</span>
            </strong>
            <div id="map" class="mt-3" style="height: 300px; display: none;" ></div>
            <button type="button" class="btn btn-outline-info btn-sm" onclick="setBounds()" style="margin-top: 0.2rem; display: none;" >지도 범위 재설정</button>
          </div>

          <!-- 첨부파일 -->
          <div class="col-sm-12 py-2 form-group">
            <strong class="d-block pb-1" style="border-bottom: 1px solid #dee2e6;" >
              <img src="${pageContext.request.contextPath}/resources/images/icons/bullet-check.png" style="display: inline-block; height: 1.5rem; padding-bottom: 0.25rem;" />
              <span>&nbsp;&nbsp;첨부파일</span>
            </strong>
            <div class="mt-3" id="attachFileResult">
              <ul class="list-unstyled row"></ul>
            </div>
          </div>
        </article>

        <article class="form-group text-center" id="button-group">
          <button type="button" class="btn btn-success" data-oper="modify" >수정</button>
          <button type="button" class="btn btn-danger" data-oper="remove" >삭제</button>
          <button type="button" class="btn btn-primary" data-oper="list">목록</button>
        </article>
      </div>

      <!-- 정보유지 -->
      <form name="hiddenForm" id="hiddenForm" method="post">
        <input type="hidden" name="page" value="${ cri.page }" />
        <input type="hidden" name="amount" value="${ cri.amount }" />
        <input type="hidden" name="searchType" value="${ cri.searchType }" />
        <input type="hidden" name="keyword" value="${ cri.keyword }" />
        <input type="hidden" name="no" value="${ festivalVO.fNo }" />
        <input type="hidden" name="fImg" value="${ festivalVO.fImg }" />
      </form>
    </section>

    <!-- Delete Modal -->
    <div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="deleteModalLabel" aria-hidden="true" data-backdrop="static" data-keyboard="false" >
      <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
          <div class="inner-content">
            <div class="text-center py-5 px-3">삭제하시겠습니까?</div>
            <div class="d-flex justify-content-between text-center my-button-group" >
              <button type="button" class="my-button col-sm p-3" data-dismiss="modal" >취소</button>
              <button type="button" class="my-button col-sm p-3" data-operate="confirm" >확인</button>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- Delete Modal End -->

    <!-- Alert Modal -->
    <div class="modal fade" id="alertModal" tabindex="-1" role="dialog" aria-labelledby="alertModalLabel" aria-hidden="true" data-backdrop="static" data-keyboard="false" >
      <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
          <div class="inner-content">
            <div class="text-center py-5 px-3 modal-body">요청하신 작업을 처리할 수 없습니다.</div>
            <div class="d-flex justify-content-between text-center my-button-group" >
              <button type="button" class="my-button col-sm p-3" data-dismiss="modal" >확인</button>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- Alert Modal End -->

    <!-- Modal -->
    <div class="modal fade hide" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static" data-keyboard="false" >
      <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
          <div class="inner-content">
            <div class="text-center py-5 px-3 modal-body">...</div>
            <div class="d-flex justify-content-between text-center my-button-group" >
              <button type="button" class="my-button col-sm p-3" data-dismiss="modal" >확인</button>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- Modal End -->

    <!-- image Modal -->
    <div class="modal fade" id="imageModal" tabindex="-1" role="dialog" aria-labelledby="imageModalLabel" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
        <div class="modal-content">
          <div class="modal-header border-0 pb-0">
            <h5 class="modal-title" data-whatever="whatever">...</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true" style="font-size: 1.6rem;">&times;</span>
            </button>
          </div>
          <div class="modal-body text-center">
            ...
          </div>
        </div>
      </div>
    </div>
    <!-- image Modal -->
  </div>

  <%@ include file="../include/footer.jsp" %>

  <%@ include file="../include/bottom_path.jsp" %>

  <script type="text/javascript">
    var flag = '<c:out value="${ result.flag }"/>';
    var uri = '<c:out value="${ result.requestUri }"/>';
    var currentPage = uri.substring(uri.lastIndexOf("/") + 1, uri.length);
    var no = '<c:out value="${ festivalVO.fNo }"/>';
  </script>

  <script src="${pageContext.request.contextPath}/resources/js/myModal.js" type="text/javascript"></script>
  <script src="${pageContext.request.contextPath}/resources/js/myUpload.js" type="text/javascript"></script>

  <!-- owl.carousel -->
  <script src="${pageContext.request.contextPath}/resources/lib/owlcarousel/owl.carousel.min.js" type="text/javascript"></script>

  <script type="text/javascript">
    // 이미지 에러 처리
    $('.imgError').each(function() {
      $(this).on('error', function(e) {
        imgError(e.target);
      });
    });
    
    function imgError(image) {
      image.onerror = "";
      image.src = "${pageContext.request.contextPath}/resources/images/icons/default-01.png";
      image.style = "object-fit: contain; height: 200px; width: 100%;";
      return true;
    }
    
    // 버튼에 따른 동작
    $('#button-group button').on("click", function(e) {
      e.preventDefault();
      var operation = $(this).data('oper');
  
      switch (operation) {
        case "modify":
          $("#hiddenForm").attr("action", "modify").attr("method", "get").submit();
          break;
        case "remove":
          $("#deleteModal").modal("show");
          $("button[data-operate='confirm']").on('click', function() {
            $('#deleteModal').modal('hide');
            $("#hiddenForm").attr("action", "remove").attr("method", "post").submit();
          });
          break;
        case "list":
          $("#hiddenForm").find("input[name='no']").remove();
          $("#hiddenForm").attr("action", "list").attr("method", "get").submit();
          break;
        default:
          break;
      }
    });

    // 이미지 슬라이더
    $('.owl-carousel').owlCarousel({
      items : 1,
      margin : 10,
      nav : true,
      navText : [
        "<img src='${pageContext.request.contextPath}/resources/images/icons/btn-back.png'>",
        "<img src='${pageContext.request.contextPath}/resources/images/icons/btn-next.png'>",
      ],
      dots : true,
      center : true,
      loop : $(".owl-carousel > .item").length <= 1 ? false : true,
      rewind : true,
      autoplay : true,
      autoplayTimeout : 2500,
      autoplayHoverPause : true,
      responsive : {
        0 : {
          items : 1,
          nav : false
        },
        600 : {
          items : 1,
          nav : true
        },
        1000 : {
          items : 1,
          nav : true
        }
      }
    });

    // 첨부파일 목록 클릭 이벤트
    $("#attachFileResult ul").on("click", "li", function(e) {
      var liObj = $(this);
      var path = liObj.data("path").replace("s_", "");
      
      if(checkImageType(path)) {
        var fileTitle = path.substr( path.indexOf("_") + 1 );
        showImageModal(path.replace(new RegExp(/\\/g), "/"), fileTitle);
      } else {
        window.self.location = "downloadFile?fileName=" + path;
      }
    });
    
    function showImageModal(imagePath, fileTitle) {
      $('#imageModal').modal('show');
      $('#imageModal .modal-title').text(fileTitle);
      $('#imageModal .modal-body').html("<a href='downloadFile?fileName="+imagePath+"'><img src='displayFile?fileName="+imagePath+"' class='image-responsive'/></a>");
    }
  </script>

  <script id="imgPreviewTemplate" type="text/x-handlebars-template">
    {{#each item}}
    <li class="col-sm-4 mt-2 appear" data-uuid="{{uuid}}" data-path="{{uploadPath}}" data-name="{{fileName}}">
      <div class="border rounded">
        <div class="d-inline-block text-center w-100 py-2 position-relative pointer">
          <img class="image-thumbnail" src={{imgSrc}}>
          <div class="image-overlay-caption"></div>
        </div>
        <div class="p-2 position-relative" style="background-color: #f2f2f2;">
          <span class="text-truncate text-left d-inline-block" style="color: #212529; width: 85%;">{{fileName}}</span>
        </div>
      </div>
    </li>
    {{/each}}
  </script>

  <!-- 카카오 지도 API  -->
  <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=1407aaa5d590aeea3d09d9b61837d203"></script>
  <script type="text/javascript">
    var latitude = "${ festivalVO.fLatitude }";
    var longitude = "${ festivalVO.fLongitude }";
  
    if (latitude !== "" && longitude !== "") {
      document.getElementById("map").style.display = "block";
      document.getElementById("map").nextElementSibling.style.display = "block";
      
      // 지도 표시 영역, 중심좌표
      var mapContainer = document.getElementById('map'),
        mapOption = {
          center : new kakao.maps.LatLng(latitude, longitude),
          level : 5,
        };

      // 지도 생성
      var map = new kakao.maps.Map(mapContainer, mapOption);

      // 지도에 마커 표시
      var marker = new kakao.maps.Marker({
        map : map,
        position : new kakao.maps.LatLng(latitude, longitude)
      });

      // 버튼을 클릭하면 아래 배열의 좌표들이 모두 보이게 지도 범위를 재설정합니다 
      var points = [ new kakao.maps.LatLng(latitude, longitude), ];

      // 지도를 재설정할 범위정보를 가지고 있을 LatLngBounds 객체를 생성합니다
      var bounds = new kakao.maps.LatLngBounds();
  
      var i, marker;
      for (i = 0; i < points.length; i++) {
        // 배열의 좌표들이 잘 보이게 마커를 지도에 추가합니다
        marker = new kakao.maps.Marker({
          position : points[i]
        });
        marker.setMap(map);
        // LatLngBounds 객체에 좌표를 추가합니다
        bounds.extend(points[i]);
      }

      function setBounds() {
        // LatLngBounds 객체에 추가된 좌표들을 기준으로 지도의 범위를 재설정합니다
        // 이때 지도의 중심좌표와 레벨이 변경될 수 있습니다 : setLevel()추가
        map.setBounds(bounds);
        map.setLevel(5);
        document.getElementById("map").nextElementSibling.style.boxShadow = "none";
      }
    };
  </script>
</body>
</html>