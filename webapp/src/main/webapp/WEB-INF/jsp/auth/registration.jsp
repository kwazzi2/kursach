<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE HTML>
<html>
<head>
  <title>Регистарция</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body>
<section class="vh-100">
  <div class="container py-5 h-100">
    <div class="row d-flex justify-content-center align-items-center h-100">
      <div class="col-12 col-md-8 col-lg-6 col-xl-5">
        <div class="card bg-white text-dark" style="border-radius: 1rem;">
          <div class="card-body p-5 text-center">

            <%--@elvariable id="user" type="by.bsuir.webapp.dto.UserDto"--%>
            <form:form action="registration" method="post" modelAttribute="user" cssClass="mt-md-3">
              <h2 class="fw-bold mb-2 text-uppercase">Регистрация</h2>

              <div class="form-outline mb-3">
                <form:input path="firstName" cssClass="form-control form-control-lg"/>
                <form:label path="firstName" cssClass="form-label">Имя</form:label>
                <form:errors path="firstName" cssClass="text-danger" />
              </div>
              <div class="form-outline mb-3">
                <form:input path="lastName" cssClass="form-control form-control-lg"/>
                <form:label path="lastName" cssClass="form-label">Фамилия</form:label>
                <form:errors path="lastName" cssClass="text-danger"/>
              </div>

              <div class="form-outline mb-3">
                <form:input path="email" cssClass="form-control form-control-lg"/>
                <form:label path="email" cssClass="form-label">E-mail</form:label>
                <form:errors path="email" cssClass="text-danger" />
              </div>

              <div class="form-outline mb-3">
                <form:password path="password" cssClass="form-control form-control-lg"/>
                <form:label path="password" cssClass="form-label">Пароль</form:label>
                <form:errors path="password" cssClass="text-danger" />
              </div>

              <div class="form-outline mb-3">
                <form:password path="matchingPassword" cssClass="form-control form-control-lg"/>
                <form:label path="matchingPassword" cssClass="form-label">Повторите пароль</form:label>
                <form:errors path="" cssClass="text-danger" />
              </div>

              <c:if test="${exception != null && !exception.isEmpty()}">
                <p class="text-danger mb-4">${exception}</p>
              </c:if>

              <form:button class="btn btn-outline-dark btn-lg px-5">Регистрация</form:button>
            </form:form>
          </div>
        </div>
      </div>
    </div>
  </div>
</section>
</body>
</html>