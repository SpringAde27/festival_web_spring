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
</head>
<body>
  <%@ include file="../include/nav.jsp" %>

  <!-- Alert -->
  <div id="myAlert" class="alert alert-danger alert-dismissible" style="position: fixed; left: 0; right: 0; z-index: 10; display:none;">
    <strong class="alert-body"> ... </strong>
    <button type="button" class="close" data-dismiss="alert" aria-hidden="true" style="font-size: 1.5rem">&times;</button>
  </div>

  <div class="container py-3">
    <section class="page-header">
      <h1>축제 수정</h1>
    </section>

    <!-- Form -->
    <section class="row justify-content-center">
      <div class="col-md-10 col-lg-8">
        <form name="myForm" id="myForm" action="modify" method="post"  enctype="multipart/form-data" class="needs-validation" novalidate>
          <div class="form-group">
            <label for="fNo">축제 번호</label>
            <input type="text" class="form-control" name="fNo" value="${ festivalVO.fNo }" readonly>
          </div>

          <!-- 정보 유지 -->
          <div class="form-group">
            <input type="hidden" name="page" value="${cri.page }">
            <input type="hidden" name="amount" value="${cri.amount }">
            <input type="hidden" name="searchType" value="${cri.searchType }">
            <input type="hidden" name="keyword" value="${cri.keyword }" >
          </div>

          <div class="form-group">
            <label for="fTitle">축제명</label>
            <input type="text" class="form-control" name="fTitle" placeholder="축제명을 입력하세요." required value="${ festivalVO.fTitle }">
            <div class="invalid-feedback">필수 입력 사항입니다.</div>
          </div>

          <div class="form-group">
            <label for="fTheme">주제</label>
            <input type="text" class="form-control" name="fTheme" placeholder="축제의 주제를 입력하세요." value="${ festivalVO.fTheme }">
          </div>

          <div class="form-group">
            <label for="f_postcode">장소</label>
            <div class="input-group mb-1">
              <div class="input-group-prepend">
                <span class="input-group-text">우편번호</span>
              </div>
              <input type="text" class="form-control" id="fPostcode" name="fPostcode" placeholder="우편번호  검색" onclick="getKakaoPostcode()" value="${ festivalVO.fPostcode }">
            </div>
            <div class="form-row">
              <div class="col-md-12 my-1">
                <input type="text" class="form-control" id="fAddr1" name="fAddr1" placeholder="주소" value="${ festivalVO.fAddr1 }">
              </div>
              <div class="col-md-6 my-1">
                <input type="text" class="form-control" id="fAddr2" name="fAddr2" placeholder="상세주소" value="${ festivalVO.fAddr2 }">
              </div>
              <div class="col-md-6 my-1">
                <input type="text" class="form-control" id="fAddr3" name="fAddr3" placeholder="참고항목" value="${ festivalVO.fAddr3 }">
              </div>
            </div>
          </div>

          <!-- map -->
          <aside class="form-group">
            <div id="map" style="height: 300px; display: none;" class="mb-2"></div>
            <div class="form-row" style="display: none;">
              <div class="col-md-6">
                <label for="fLatitude">위도</label>
                <input type="text" class="form-control" name="fLatitude" value="${ festivalVO.fLatitude }" />
              </div>
              <div class="col-md-6">
                <label for="fLongitude">경도</label>
                <input type="text" class="form-control" name="fLongitude" value="${ festivalVO.fLongitude }" />
              </div>
            </div>
          </aside>

          <div class="form-group">
            <div class="form-row">
              <div class="col-md-6">
                <label for="fStartdate">시작날짜</label>
                <div class="input-group">
                  <input type="text" class="form-control" id="fStartdate" name="fStartdate" placeholder="시작날짜를 선택하세요." 
                         value='<fmt:formatDate value="${festivalVO.fStartdate}" pattern="yyyy-MM-dd"/>'
                   />
                  <div class="input-group-append">
                    <div class="input-group-text rounded-right">
                      <i class="fa fa-calendar"></i>
                    </div>
                  </div>
                </div>
              </div>
              <div class="col-md-6">
                <label for="fEnddate">종료날짜</label>
                <div class="input-group">
                  <input type="text" class="form-control" id="fEnddate" name="fEnddate" placeholder="종료날짜를 선택하세요." 
                         value='<fmt:formatDate value="${festivalVO.fEnddate}" pattern="yyyy-MM-dd"/>'
                  />
                  <div class="input-group-append">
                    <div class="input-group-text rounded-right">
                      <i class="fa fa-calendar"></i>
                    </div>
                  </div>
                  <div class="invalid-feedback end-date-feedback">필수 입력 사항입니다.</div>
                </div>
              </div>
            </div>
          </div>

          <div class="form-group">
            <label for="fHost">주관</label>
            <input type="text" class="form-control" name="fHost" placeholder="축제 주관명을 입력하세요." value="${ festivalVO.fHost }">
          </div>

          <div class="form-group">
            <label for="fPhone">문의</label>
            <input type="text" class="form-control" name="fPhone" placeholder="문의 관련 전화번호를 입력하세요." value="${ festivalVO.fPhone }">
          </div>

          <div class="form-group">
            <label for="fContent">세부내용</label>
            <textarea rows="10" cols="50" class="form-control" name="fContent" placeholder="축제 세부 사항을 입력하세요.">${ festivalVO.fContent }</textarea>
          </div>

          <div class="form-group">
            <label for="attachImg">대표 이미지</label>
            <div class="custom-file">
              <input type="file" class="custom-file-input" name="attachImg" id="attachImg" accept="image/*" capture="camera" />
              <label class="custom-file-label" for="attachImg">대표로 사용할 이미지를 선택하세요.</label>
            </div>
            <input type="hidden" value="${ festivalVO.fImg }" name="fImg" id="fImgHidden" />

            <!-- 이미지 미리보기 -->
            <div class="custom-file-preview ${ !empty festivalVO.fImg ? 'show appear' : 'hide' }">
              <div class="preview-image" onclick="resetInputFile()">
                <c:if test="${ !empty festivalVO.fImg }">
	                <img src="displayFile?fileName=${ festivalVO.fImg }" alt="미리보기">
                </c:if>
                <c:if test="${ empty festivalVO.fImg }">
                  <img src="" alt="미리보기">
                </c:if>
                <div class="overlay pointer">
                 <img src="${pageContext.request.contextPath}/resources/images/icons/cancel-02.png"/>
                </div>
              </div>
            </div>
          </div>

          <!-- 첨부파일 업로드 -->
          <div class="form-group">
            <label>첨부파일</label>
            <div class="my-custom-attach-file">
              <div class="custom-file" style="height: auto">
                <input type="file" id="attachFile" name="attachFile" multiple="multiple" class="custom-file-input" style="height: auto">
                <label class="custom-file-label m-0 w-100 text-center" for="attachFile" style="height: auto; position: static;">
                  <img src="${pageContext.request.contextPath}/resources/images/icons/upload-01.png" alt="파일업로드" />
                </label>
              </div>
              <div id="attachFileResult" class="hide">
                <ul class="list-unstyled row"></ul>
              </div>
            </div>
          </div>

          <div class="form-group text-center">
            <button type="button" class="btn btn-outline-dark" data-button="list">목록</button>
            <button type="reset" class="btn btn-secondary">취소</button>
            <button type="submit" class="btn btn-primary">저장</button>
          </div>
        </form>
      </div>

      <!-- Modal -->
      <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static" data-keyboard="false">
        <div class="modal-dialog modal-dialog-centered" role="document">
          <div class="modal-content">
            <div class="inner-content">
              <div class="text-center py-5 px-3 modal-body">수정하시겠습니까?</div>
              <div class="d-flex justify-content-between text-center my-button-group">
                <button type="button" class="my-button col-sm p-3" data-dismiss="modal">취소</button>
                <button type="submit" class="my-button col-sm p-3" data-operate="confirm">확인</button>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!-- Modal End -->
      
      <!-- Attach Delete Modal -->
      <div class="modal fade" id="myDeleteModal" tabindex="-1" role="dialog" aria-labelledby="myDeleteModalLabel" aria-hidden="true" data-backdrop="static" data-keyboard="false">
        <div class="modal-dialog modal-dialog-centered" role="document">
          <div class="modal-content">
            <div class="inner-content">
              <div class="text-center py-5 px-3 modal-body">첨부파일을 삭제 하시겠습니까?</div>
              <div class="d-flex justify-content-between text-center my-button-group">
                <button type="button" class="my-button col-sm p-3" data-dismiss="modal">취소</button>
                <button type="button" class="my-button col-sm p-3" data-operate="delete">삭제</button>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!-- Attach Delete Modal -->
    </section>
  </div>

  <%@ include file="../include/footer.jsp" %>

  <%@ include file="../include/bottom_path.jsp" %>

  <script type="text/javascript">
    var requestUri = '${requestScope["javax.servlet.forward.request_uri"]}';
    var currentPage = requestUri.substring(requestUri.lastIndexOf("/") + 1, requestUri.length);
    var page = "${ cri.page }";
    var amount = "${ cri.amount }";
    var searchType = "${ cri.searchType }";
    var keyword = "${ cri.keyword }";
    var no = "${ festivalVO.fNo }";
    
    $("button[data-button='list']").on('click', function() {
      self.location = "list?page=" + page + "&amount=" + amount + "&searchType=" + searchType + "&keyword=" + keyword;
    });
  </script>

  <script src="${pageContext.request.contextPath}/resources/js/myForm.js" type="text/javascript"></script>
  <script src="${pageContext.request.contextPath}/resources/js/validation.js" type="text/javascript"></script>
  <script src="${pageContext.request.contextPath}/resources/js/myUpload.js" type="text/javascript"></script>

  <script id="imgPreviewTemplate" type="text/x-handlebars-template">
    {{#each item}}
    <li class="col-sm-4 mt-2 appear" data-uuid="{{uuid}}" data-path="{{uploadPath}}" data-name="{{fileName}}">
      <div class="border rounded">
        <div class="d-inline-block text-center w-100 py-2 position-relative">
          <img class="image-thumbnail" src={{imgSrc}}>
        </div>
        <div class="p-2 position-relative" style="background-color: #f2f2f2;">
          <span class="text-truncate text-left d-inline-block" style="color: #212529; width: 85%;">{{fileName}}</span>
          <button type="button" data-file="{{uploadPath}}" class="btn btn-light btn-sm position-absolute" style="right: 0.5rem;">
            <i class="fa fa-times" aria-hidden="true"></i>
          </button>
        </div>
      </div>
    </li>
    {{/each}}
  </script>

  <!-- 카카오 우편번호 API  -->
  <script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
  <script src="//dapi.kakao.com/v2/maps/sdk.js?appkey=1407aaa5d590aeea3d09d9b61837d203&libraries=services"></script>
  <script src="${pageContext.request.contextPath}/resources/js/postcode.js" type="text/javascript"></script>
</body>
</html>