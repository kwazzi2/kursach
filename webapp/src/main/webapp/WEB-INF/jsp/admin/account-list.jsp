<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Админка</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style><%@include file="../../../resources/css/style.css"%></style>
</head>
<body>
<%@include file="../elements/header.jsp"%>
<div class="container my-5">
    <div class="mainContainer p-3">
        <table class="table w-auto">
            <thead class="thead-dark">
            <tr>
                <th scope="col">Email</th>
                <th scope="col">Роль</th>
                <th scope="col">Блокировка</th>
            </tr>
            </thead>
            <%--@elvariable id="accounts" type="java.util.List<by.bsuir.webapp.model.Account>"--%>
            <c:forEach var="item" items="${accounts}" varStatus="status">
                <tbody>
                <tr>
                    <td>
                        <c:out value="${item.email}"/>
                    </td>
                    <td>
                        <c:out value="${item.role.name()}"/>
                    </td>
                    <td>
                        <i class="fa ${item.locked?'fa-times-circle':'fa-check-circle-o'}" aria-hidden="true"></i>
                    </td>
                    <td>
                        <c:if test="${!item.locked}">
                            <form action="/admin/account/${item.id}/lock" method="post">
                                <button class="btn " style="background-color:transparent" type="submit" >
                                    <i class="fa fa-times-circle" aria-hidden="true"></i>
                                </button>
                            </form>
                        </c:if>
                        <c:if test="${item.locked}">
                            <form action="/admin/account/${item.id}/unlock" method="post">
                                <button class="btn " style="background-color:transparent" type="submit" >
                                    <i class="fa fa-check-circle-o" aria-hidden="true"></i>
                                </button>
                            </form>
                        </c:if>
                    </td>
                </tr>
                </tr>
                </tbody>
            </c:forEach>
        </table>
    </div>
</div>
</body>
</html>