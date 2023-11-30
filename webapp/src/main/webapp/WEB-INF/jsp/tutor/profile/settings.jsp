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
    $(function () {
        // handle upload file
        $('#isHidden').change(function() {
            $("#frmSetIsHidden").submit();
        });
    });
</script>
<%@include file="../../elements/header.jsp"%>
<div class="container py-3">
    <div class="row justify-content-md-center">
        <div class="col-auto card mx-2">
            <div class="card-body">
                <div class="row">
                    <form:form action="change-password" method="post" modelAttribute="form" cssClass="mt-md-3">
                        <h4 class="fw-bold mb-3 text-uppercase">Сменить пароль</h4>

                        <div class="form-outline mb-3">
                            <form:password path="oldPassword" cssClass="form-control form-control-lg"/>
                            <form:label path="oldPassword" cssClass="form-label">Старый пароль</form:label>
                            <form:errors path="oldPassword" cssClass="text-danger" />
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

                        <form:button class="btn btn-outline-dark">Сменить</form:button>
                    </form:form>
                </div>
            </div>
        </div>
        <div class="col-auto justify-content-md-center card mx-2">
            <form action="set-isHidden" id="frmSetIsHidden" class="my-2" method="post">
                <div>
                    <input type="checkbox" name="isHidden" id="isHidden" ${tutor.isHidden()? 'checked':''}>
                    <label class="form-label" for="isHidden">Скрыть анкету</label>
                </div>
            </form>

            <form action="update-rating" class="my-2" method="post">
                <button type="submit" class="btn btn-outline-dark">Обновить рейтинг</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>