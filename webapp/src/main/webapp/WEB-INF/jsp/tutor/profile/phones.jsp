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
<div class="container py-3">
    <div class="p-3">
        <div class="px-3">
            <a type="button" class="btn btn-outline-dark" href="/phone/editor">Добавить</a>
        </div>
        <c:if test="${exception != null && !exception.isEmpty()}" >
            <div class="px-3">
                <span class="text-danger">${exception}</span>
            </div>
        </c:if>
        <div class="mainContainer p-3">
            <table class="table w-auto">
                <thead class="thead-dark">
                <tr>
                    <th scope="col">Номер</th>
                    <th scope="col">Действия</th>
                </tr>
                </thead>
                <c:forEach var="phone" items="${phones}" varStatus="status">
                    <tbody>
                    <tr>
                        <td>
                            <c:out value="${phone.getValue()}"></c:out>
                        </td>
                        <td>
                            <div class="row">
                                <div class="col">
                                    <a class="btn text-dark" href="/phone/editor?phoneId=${phone.id}">
                                        <i class="fa fa-pencil" aria-hidden="true"></i>
                                    </a>
                                </div>
                                <div class="col">
                                    <form action="/phone/${phone.id}/delete" method="post">
                                        <button class="btn " style="background-color:transparent" type="submit" >
                                            <i class="fa fa-trash" aria-hidden="true"></i>
                                        </button>
                                    </form>
                                </div>
                            </div>
                        </td>
                    </tr>
                    </tr>
                    </tbody>
                </c:forEach>
            </table>
        </div>
    </div>
</div>
</body>
</html>