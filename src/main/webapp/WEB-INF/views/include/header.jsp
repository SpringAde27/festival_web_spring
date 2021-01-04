<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<header>
  <div class="text-center">
    <div id="myCarousel" class="carousel slide" data-ride="carousel">
      <!-- Indicators -->
      <ul class="carousel-indicators">
        <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
        <li data-target="#myCarousel" data-slide-to="1"></li>
      </ul>

      <!-- The slideshow -->
      <div class="carousel-inner">
        <div class="carousel-item active">
          <img src="${pageContext.request.contextPath}/resources/images/sample/sm-bg1.jpg" alt="sample_image">
          <div class="carousel-caption">
            <h3>K1</h3>
            <p class="text-break">
              Lorem ipsum dolor sit amet,
              consectetur adipiscing elit, sed do eiusmod tempor
              incididunt ut labore et dolore magna aliqua.
            </p>
          </div>
        </div>
        <div class="carousel-item">
          <img src="${pageContext.request.contextPath}/resources/images/sample/sm-bg1.jpg" alt="sample_image">
          <div class="carousel-caption">
            <h3>K2</h3>
            <p class="text-break">
              Lorem ipsum dolor sit amet,
              consectetur adipiscing elit, sed do eiusmod tempor
              incididunt ut labore et dolore magna aliqua.
            </p>
          </div>
        </div>
      </div>

      <!-- Carousel controls -->
      <a class="carousel-control-prev" href="#myCarousel" data-slide="prev">
        <span class="carousel-control-prev-icon"></span>
      </a>
      <a class="carousel-control-next" href="#myCarousel" data-slide="next">
        <span class="carousel-control-next-icon"></span>
      </a>
    </div>
  </div>
</header>