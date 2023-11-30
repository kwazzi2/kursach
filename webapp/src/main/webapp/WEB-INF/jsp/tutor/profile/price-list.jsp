<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Услуги</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style><%@include file="../../../../resources/css/style.css"%></style>
</head>
<body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
<%@include file="../../elements/header.jsp"%>
<div class="container py-3">
    <div class="p-3">
        <div class="px-3">
            <a type="button" class="btn btn-outline-dark" href="/pricelist-item/editor">Добавить</a>
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
                    <th scope="col">Предмет</th>
                    <th scope="col">Тип</th>
                    <th scope="col">Стоимость</th>
                    <th scope="col">Длительность</th>
                    <th scope="col">Действия</th>
                </tr>
                </thead>
                <c:forEach var="item" items="${priceList}" varStatus="status">
                    <tbody>
                    <tr>
                        <td>
                            <c:out value="${item.getSubject().getName()}"/>
                        </td>
                        <td>
                            <c:out value="${item.getType().getName()}"/>
                        </td>
                        <td>
                            <c:out value="${item.getRate()} руб/час"/>
                        </td>
                        <td>
                            <c:out value="${item.getDuration()} мин"/>
                        </td>
                        <td>
                            <div class="row">
                                <div class="col">
                                    <a class="btn text-dark" href="/pricelist-item/editor?itemId=${item.id}">
                                        <i class="fa fa-pencil" aria-hidden="true"></i>
                                    </a>
                                </div>
                                <div class="col">
                                    <form action="/pricelist-item/${item.id}/delete" method="post">
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