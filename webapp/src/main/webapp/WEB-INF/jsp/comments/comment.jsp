<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE HTML>
<html>
<head>
  <title>Добавить отзыв</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <script src="https://kit.fontawesome.com/e4dbc476a3.js" crossorigin="anonymous"></script>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
  <style><%@include file="../../../resources/css/style.css"%></style>
</head>
<body>
<section>
  <div class="container my-5 py-5 text-dark">
    <div class="row d-flex justify-content-center">
      <div class="col-md-10 col-lg-8 col-xl-6">
        <div class="card">
          <div class="card-body p-4">
            <div class="d-flex flex-start w-100">
              <div class="w-100">
                <h5>Добавьте отзыв</h5>
                <form:form action="add" method="post" modelAttribute="comment">
                  <div class="rating">
                    <div><form:errors path="rating" cssClass="text-danger" /></div>
                    <form:radiobutton path="rating" id="star5" name="rating" value="5"/>
                    <label class="star fa-solid" for="star5" aria-hidden="true"></label>
                    <form:radiobutton path="rating" id="star4" name="rating" value="4" />
                    <label class="star fa-solid" for="star4" aria-hidden="true"></label>
                    <form:radiobutton path="rating" id="star3" name="rating" value="3" />
                    <label class="star fa-solid" for="star3" aria-hidden="true"></label>
                    <form:radiobutton path="rating" id="star2" name="rating" value="2" />
                    <label class="star fa-solid" for="star2" aria-hidden="true"></label>
                    <form:radiobutton path="rating" id="star1" name="rating" value="1" />
                    <label class="star fa-solid" for="star1" aria-hidden="true"></label>
                  </div>
                  <div class="form-outline">
                    <form:textarea path="body" rows="4" cssClass="form-control"></form:textarea>
                    <form:label path="body" cssClass="form-label">Каково ваше мнение?</form:label>
                  </div>
                  <form:button class="btn btn-outline-dark px-5">Отправить</form:button>
                </form:form>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</section>
</body>
</html>