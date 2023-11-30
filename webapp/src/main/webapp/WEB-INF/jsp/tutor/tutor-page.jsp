<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Анкеты</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style><%@include file="../../../resources/css/style.css"%></style>
</head>
<body>
<%@include file="../elements/header.jsp"%>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
<script>
    function sendData(id){
        $.ajax({
            type: "POST",
            url: "/comparison/add/"+id, // Map your servlet here.
            data: {}
        }).done(function( msg ) {
            console.log("ok "+msg);
            console.log($('#comparingCount').text());
            $('#comparingCount').text(parseInt($('#comparingCount').text()) + 1);
        });
    }
</script>
<div class="container my-5">
    <div class="card text-dark mt-1">
        <div class="card-body p-4">
            <div class="row">
                <div class="col-md-2 justify-content-center">
                    <img class="rounded-circle shadow-1-strong me-3"
                         src="${tutor.getPhotoUrl()==null? "/resources/defaulticon.svg": tutor.getPhotoUrl()}"
                         alt="avatar" width="100%"/>
                    <p class="mt-1 text-center">Рейтинг: <fmt:formatNumber type="number" maxFractionDigits="2" value="${tutor.getRating()}"/></p>
                    <a class="text-dark" href="/comments/${tutor.id}/list"><p class="text-center">отзывы</p></a>
                </div>
                <div class="col-md-9">
                    <a class="text-dark"href="/tutors/details/${tutor.id}"><h6 class="fw-bold mb-1">${tutor.account.getFirstName()} ${tutor.account.getMiddleName()} ${tutor.account.getLastName()}</h6></a>
                    <div class="d-flex align-items-center mb-3">
                        <c:if test="${tutor.experience != null}">
                            <p class="mx-2">
                                Опыт(лет): ${tutor.experience}
                            </p>
                        </c:if>
                        <c:if test="${tutor.scienceDegree != null}">
                            <p class="mx-2">
                                Степень: ${tutor.scienceDegree.name}
                            </p>
                        </c:if>
                        <c:if test="${tutor.sex != null}">
                            <p class="mx-2">
                                Пол: ${tutor.sex.name}
                            </p>
                        </c:if>
                    </div>
                    <p class="mb-0">${tutor.bio}</p>
                </div>
                <div class="col-md-1">
                    <div class="row">
                        <div class="col-auto px-0">
                            <a class="btn text-dark" href="/phones/${tutor.id}">
                                <i class="fa fa-phone" aria-hidden="true"></i>
                            </a>
                        </div>
                        <div class="col-auto px-0">
                            <button onclick="sendData(${tutor.id})" class="btn " style="background-color:transparent" type="submit" >
                                <i class="fa fa-plus" aria-hidden="true"></i>
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row justify-content-center">
                <table class="table w-auto">
                    <thead class="thead-dark">
                    <tr>
                        <th scope="col">Предмет</th>
                        <th scope="col">Тип</th>
                        <th scope="col">Стоимость</th>
                        <th scope="col">Длительность</th>
                    </tr>
                    </thead>
                    <c:forEach var="item" items="${tutor.getPriceListItems()}" varStatus="status">
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
                        </tr>
                        </tr>
                        </tbody>
                    </c:forEach>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>