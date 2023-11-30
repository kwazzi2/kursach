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
<div class="row justify-content-center mt-5">
    <div class="col-auto">
        <h2>На почту ${user.email} было отправлено письмо.</h2>
        <h2>Для завершения регистрации перейдите по ссылке в письме</h2>
        <div class="card-body mx-auto">
            <a type="button" href="/"
               class="btn btn-sm btn-outline-dark"> На главную </a>
        </div>
    </div>
</div>
</body>
</html>