<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Настройки</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style><%@include file="../../../../resources/css/style.css"%></style>
</head>
<body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
<script>
</script>
<%@include file="../../elements/header.jsp"%>
<div class="container py-3">
    <div class="row justify-content-md-center">
        <div class="col-auto justify-content-md-center card mx-2">
            <span>Всего узнавали номер: ${allEventsCount}</span>
            <span>Уникальных пользователей узнавали номер: ${unicUsersCount}</span>
        </div>
    </div>
</div>
</body>
</html>