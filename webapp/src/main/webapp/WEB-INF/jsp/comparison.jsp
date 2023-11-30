<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Сравнение</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style><%@include file="../../resources/css/style.css"%></style>
</head>
<body>
<%@include file="./elements/header.jsp"%>
<div class="mainContainer justify-content-center px-3 py-5">
        <c:if test="${tutors.isEmpty()}">
            <div class="row justify-content-center">
                <div class="col-auto">
                    <h2>Нет элементов для сравнения</h2>
                </div>
            </div>
        </c:if>
        <c:if test="${!tutors.isEmpty()}">
            <div class="col w-auto">
                <table class="table w-auto">
                    <thead class="thead-dark">
                    <tr>
                        <th scope="col">#</th>
                        <c:forEach var="tutor" items="${tutors}" varStatus="status">
                            <th scope="col">
                                <div class="row">
                                    <div class="col">${tutor.account.getLastName()} ${tutor.account.getFirstName().substring(0,1)}. ${tutor.account.getMiddleName().substring(0,1)}.</div>
                                    <div class="col-auto">
                                        <form action="/comparison/delete/${tutor.id}" method="post">
                                            <button class="btn " style="background-color:transparent" type="submit" >
                                                <i class="fa fa-trash" aria-hidden="true"></i>
                                            </button>
                                        </form>
                                    </div>
                                </div>
                            </th>
                        </c:forEach>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>Опыт(лет)</td>
                        <c:forEach var="tutor" items="${tutors}" varStatus="status">
                            <td><c:out value="${tutor.experience}"/></td>
                        </c:forEach>
                    </tr>
                    <tr>
                        <td>Степень</td>
                        <c:forEach var="tutor" items="${tutors}" varStatus="status">
                            <td><c:out value="${tutor.scienceDegree.name}"/></td>
                        </c:forEach>
                    </tr>

                    <tr>
                        <td>Пол</td>
                        <c:forEach var="tutor" items="${tutors}" varStatus="status">
                            <td><c:out value="${tutor.sex.name}"/></td>
                        </c:forEach>
                    </tr>
                    <tr>
                        <td>Рейтинг</td>
                        <c:forEach var="tutor" items="${tutors}" varStatus="status">
                            <td><c:out value="${tutor.rating}"/></td>
                        </c:forEach>
                    </tr>

                    <tr>
                        <td>Услуги</td>
                        <c:forEach var="tutor" items="${tutors}" varStatus="status">
                            <td>
                                <div class="col">
                                    <c:forEach var="item" items="${tutor.getPriceListItems()}" varStatus="status">
                                        <p>${item.getSubject().getName()} | ${item.getType().getName()} | ${item.getRate()} руб/час</p>
                                        <hr/>
                                    </c:forEach>
                                </div>
                            </td>
                        </c:forEach>
                    </tr>

                    </tbody>
                </table>
            </div>
        </c:if>
</div>
</body>
</html>