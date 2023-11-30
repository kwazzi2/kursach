<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Логин</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>

<body>
<section class="vh-100">
    <div class="container py-5 h-100">
        <div class="row d-flex justify-content-center align-items-center h-100">
            <div class="col-12 col-md-8 col-lg-6 col-xl-5">
                <div class="card bg-white text-dark" style="border-radius: 1rem;">
                    <div class="card-body p-5 text-center">

                        <form method="POST" action="/login" class="mb-md-5 mt-md-4">

                            <h2 class="fw-bold mb-2 text-uppercase">Вход</h2>
                            <p class="text-black-50 mb-4">Ведите ваш email и пароль!</p>

                            <div class="form-outline mb-3">
                                <input type="email"  name="username" id="username" class="form-control form-control-lg" />
                                <label class="form-label" for="username">Email</label>
                            </div>

                            <div class="form-outline mb-3">
                                <input type="password" name="password" id="password" class="form-control form-control-lg" />
                                <label class="form-label" for="password">Пароль</label>
                            </div>
                            <c:if test="${errorMsg != null && !errorMsg.isEmpty()}">
                                <p class="text-danger mb-5">${errorMsg}</p>
                            </c:if>

                            <button class="btn btn-outline-dark btn-lg px-5" type="submit">Войти</button>
                        </form>

                        <div>
                            <p class="mb-0">Нет аккаунта? <a href="/registration" class="text-black-50 fw-bold">Регистрация</a>
                            </p>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
</body>
</html>