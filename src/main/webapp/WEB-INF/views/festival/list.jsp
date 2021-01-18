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
</head>
<body>
  <%@ include file="../include/nav.jsp" %>

  <div class="container py-3">
    <section class="page-header">
      <div class="row">
        <div class="col-sm-6">
          <h1 class="pointer-color d-inline-block" onclick="self.location = '${pageContext.request.contextPath}/festival/list'">Festival</h1>
        </div>
        <div class="col-sm-6 d-flex flex-row align-items-center justify-content-end">
          <button class="btn btn-primary btnRegister" type="button">글쓰기</button>
        </div>
      </div>
    </section>

    <!-- 검색 -->
    <section class="row">
      <div class="col-md-4 form-group input-group">
        <div class="input-group-prepend">
          <span class="input-group-text">항목</span>
        </div>
        <select name="searchType" class="custom-select">
          <option value="n" ${cri.searchType == null? 'selected':''}>---</option>
          <option value="t" ${cri.searchType eq 't'? 'selected':''}>축제</option>
          <option value="ct" ${cri.searchType eq 'ct'? 'selected':''}>주제 또는 내용</option>
        </select>
      </div>

      <div class="col form-group input-group">
        <input type="text" value="${cri.keyword }" name="keyword" class="form-control" placeholder="Search..." style="padding-right: 2rem">
        <div class="input-group-append" style="position: relative;">
          <i class="fa fa-times-circle hide pointer" aria-hidden="true" id="clearBtn" style="position: absolute; left: -23px; top: 11px; z-index: 10;"></i>
          <button class="btn btn-success" type="button" id="searchBtn">
            <i class="fa fa-search" aria-hidden="true"></i> Search
          </button>
        </div>
      </div>

      <!-- toast -->
      <div class="col-12" style="position: relative;">
        <aside class="toast" role="alert" data-delay="2000" data-autohide="true" style="position: absolute; right: 15px; top: 0px; z-index: 10;">
          <div class="toast-header">
            <span class="mr-auto text-info"><i class="fa fa-info-circle" aria-hidden="true"></i> 알림</span> <span class="toast-body text-body">...</span>
            <button type="button" class="ml-5 mb-1 close" data-dismiss="toast">&times;</button>
          </div>
        </aside>
      </div>
    </section>
    <!-- 검색 -->

    <c:if test="${ empty list }">
      <section class="row">
        <div class="d-flex flex-column align-items-center justify-content-center w-100" style="min-height: calc(100vh - 23.6rem);">
          <img src="${pageContext.request.contextPath}/resources/images/icons/default-02.png" class="d-inline-block img-fluid"/>
          <h4 class="my-5">데이터가 없습니다.</h4>
        </div>
      </section>
    </c:if>

    <c:if test="${ !empty list }">
      <section class="row">
        <c:forEach items="${ list }" var="vo">
          <div class="col-md-6 col-lg-4 py-2">
            <!-- card start -->
            <article class="card pointer" onclick="getDetail(${vo.fNo})">
              <div class="card-img-top text-center p-3" style="height: 17rem">
                <c:if test="${ !empty vo.fImg }">
                  <img src="displayFile?fileName=${ vo.fImg }" class="imgError" style="object-fit: contain; height: 200px; width: 100%;" alt="대표 이미지" />
                </c:if>
                <c:if test="${ empty vo.fImg }">
	                <img src="${pageContext.request.contextPath}/resources/images/icons/default-01.png" style="object-fit: contain; height: 200px; width: 100%;" alt="기본 이미지" />
                </c:if>
              </div>

              <div class="card-body">
                <h4 class="card-title ellipsis-1">${ !empty vo.fTitle ? vo.fTitle : "-" }</h4>
                <p class="card-text text-truncate mb-2">${ !empty vo.fTheme ? vo.fTheme : "-" }</p>
                <c:choose>
                  <c:when test="${ vo.fStartdate != null }">
                    <p class="mb-2 font-weight-bold">
                      <fmt:formatDate value="${vo.fStartdate}" pattern="yyyy년 MM월 dd일" />
                      ~
                      <fmt:formatDate value="${vo.fEnddate}" pattern="yyyy년 MM월 dd일" />
                    </p>
                  </c:when>
                  <c:otherwise>
                    <p class="mb-2">-</p>
                  </c:otherwise>
                </c:choose>
                <p class="mb-2">${ !empty vo.fAddr1 ? vo.fAddr1 : "-" }${ !empty vo.fAddr2 ? vo.fAddr2 : "" }</p>
                <button type="button" class="btn btn-outline-info mt-4 btnLike" data-fNo="${ vo.fNo }" data-fLike="${ vo.fLike }">
                  <img src="${pageContext.request.contextPath}/resources/images/icons/heart-off.png" alt="좋아요" style="width: 20px" />
                  <span class="px-2">좋아요</span>
                  <span class="badge badge-pill badge-dark">${vo.fLike}</span>
                </button>
              </div>
            </article>
            <!-- card end -->
          </div>
        </c:forEach>
      </section>

      <!-- pagination -->
      <nav aria-label="Page navigation" class="my-3">
        <ul class="pagination justify-content-center">
          <!-- 이전 -->
          <c:if test="${ pageMaker.prev }">
            <li class="page-item">
              <a class="page-link" href="list${ pageMaker.makeSearch(pageMaker.startPage - 1) }" aria-label="Previous">
                <span aria-hidden="true">&laquo;</span>
                <span class="sr-only">Previous</span>
              </a>
            </li>
          </c:if>

          <!-- 페이지 번호 -->
          <c:forEach var="idx" begin="${ pageMaker.startPage }" end="${ pageMaker.endPage }">
            <li class="page-item ${ pageMaker.cri.page == idx ? "active" : "" }">
              <a class="page-link" href="list${ pageMaker.makeSearch(idx) }">${ idx }</a>
            </li>
          </c:forEach>

          <!-- 다음 -->
          <c:if test="${ pageMaker.next && pageMaker.endPage > 0 }">
            <li class="page-item">
              <a class="page-link" href="list${ pageMaker.makeSearch(pageMaker.endPage + 1) }" aria-label="Next">
                <span aria-hidden="true">&raquo;</span>
                <span class="sr-only">Next</span>
              </a>
            </li>
          </c:if>
        </ul>
      </nav>
      <!-- pagination end -->
    </c:if>

    <!-- Modal -->
    <div class="modal fade hide" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static" data-keyboard="false">
      <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
          <div class="inner-content">
            <div class="text-center py-5 px-3 modal-body">...</div>
            <div class="d-flex justify-content-between text-center my-button-group">
              <button type="button" class="my-button col-sm p-3" data-dismiss="modal">확인</button>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- Modal End -->
  </div>

  <%@ include file="../include/footer.jsp" %>

  <%@ include file="../include/bottom_path.jsp" %>

  <script type="text/javascript">
    var flag = '<c:out value="${ result.flag }"/>';
    var uri = '<c:out value="${ result.uri }"/>';
  </script>

  <script src="${pageContext.request.contextPath}/resources/js/myModal.js" type="text/javascript"></script>

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
      image.style = "height: 100%; width: auto;";
      return true;
    }

    // 검색창 - button event
    $("#searchBtn").on("click", function() {
      var keyword = document.querySelector("input[name='keyword']").value;
      var el = document.querySelector("select[name='searchType']");
      var searchType = el.options[el.selectedIndex].value;
      
      if (searchType === "n") {
        $(".toast-body").text('검색 항목을 선택하세요.');
        $('.toast').toast('show');
        return false;
      }
      
      if (keyword === "") {
        document.querySelector("input[name='keyword']").focus();
        $(".toast-body").text('검색 키워드를 입력하세요.');
        $('.toast').toast('show');
        return false;
      }
      
      self.location = "list${ pageMaker.makeQuery(1) }&searchType=" + searchType + "&keyword=" + keyword;
    });

    document.querySelector("input[name='keyword']").addEventListener("keydown", function(e) {
      if (e.key == "Enter" || e.keyCode == '13') {
        document.getElementById("searchBtn").click();
      }
    }, false);

    // 검색창 - input event
    var clearBtn = document.getElementById("clearBtn");
    var inputKeyword = document.querySelector("input[name='keyword']");
  
    clearBtn.addEventListener("click", function(e) {
      inputKeyword.value = "";
      inputKeyword.focus();
    }, false);
  
    inputKeyword.addEventListener('input', updateValue);
    inputKeyword.addEventListener('focus', updateValue);
  
    function updateValue(e) {
      if (e.target.value.length === 0) {
        clearBtn.classList.add("hide");
        clearBtn.classList.remove("show");
      } else {
        clearBtn.classList.add("show");
        clearBtn.classList.remove("hide");
      }
    }

    // 상세페이지 이동
    function getDetail(no) {
      // self.location="read?no=" + no + "&page=${pageMaker.cri.page}&amount=${pageMaker.cri.amount}";
      self.location = "read${pageMaker.makeSearch(pageMaker.cri.page)}&no=" + no;
    }

    // 글쓰기 버튼
    $(".btnRegister").on("click", function() {
      location.href = "${pageContext.request.contextPath}/festival/register";
    });

    // [좋아요] 이벤트 리스너
    document.querySelectorAll('.btnLike').forEach( function(el) {
      el.addEventListener("click", function(e) {
        e.stopPropagation();
        
        var userNo = 1;
        var fNo = parseInt(this.getAttribute('data-fNo'));
        var sendData = {fNo:fNo, userNo:userNo};
        
        var likeCount = parseInt(this.lastChild.previousSibling.innerHTML);
        var likeImg = this.firstChild.nextSibling;
        var _this = this;
        
        var img_like = [
          "${pageContext.request.contextPath}/resources/images/icons/heart-off.png",
          "${pageContext.request.contextPath}/resources/images/icons/heart-on.png"
        ];
        
        $.ajax({
          type: "post",
          url: "${pageContext.request.contextPath}/festival/vote/new/"+ userNo +"/"+ fNo,
          data: JSON.stringify(sendData),
          headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST",
          },
          success: function(data) {
            // console.log(data);
            _this.lastChild.previousSibling.innerHTML = likeCount += 1;
            likeImg.src = img_like[1];
          },
          error: function(request, status, error) {
            // console.log("code:"+request.status+"\n"+"status:"+status+"\n");
            $("#my-alert .alert-message").text("요청하신 작업을 수행할 수 없습니다.");
            $("#my-alert").fadeIn(500);
            setTimeout(function() {
              $("#my-alert").fadeOut(1000);
            }, 2500);
          },
        });
      }, false);
    });
  </script>
</body>
</html>