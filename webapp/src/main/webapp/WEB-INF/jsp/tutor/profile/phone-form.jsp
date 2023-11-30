<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Телефоны</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style><%@include file="../../../../resources/css/style.css"%></style>
</head>
<body>
<%@include file="../../elements/header.jsp"%>
<div class="container">
    <div class="row justify-content-center">
        <div class="col-auto card mx-2">
            <div class="card-body">
                <div class="row">
                    <form:form action="/save-phone${phoneId == null? '': ('?phoneId='.concat(phoneId))}" method="post" modelAttribute="form" cssClass="mt-md-3">

                        <div class="form-outline mb-3">
                            <form:input path="value" cssClass="form-control" value="${phoneValue == null? '': phoneValue}"/>
                            <form:errors path="value" cssClass="text-danger" />
                        </div>
                        <form:button class="btn btn-outline-dark">Сохранить</form:button>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>