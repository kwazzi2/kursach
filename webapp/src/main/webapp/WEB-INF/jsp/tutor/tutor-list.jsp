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
<script>
    function applyFilters() {
        const params = new URLSearchParams();

        const subCheckBoxCollection = document.getElementsByName("subject");
        for(let i=0; i<subCheckBoxCollection.length; i++){
            if(subCheckBoxCollection[i].checked) {
                params.append('subject', subCheckBoxCollection[i].value);
            }
        }

        const degCheckBoxCollection = document.getElementsByName("degree");
        for(let i=0; i<degCheckBoxCollection.length; i++){
            if(degCheckBoxCollection[i].checked) {
                params.append('degree', degCheckBoxCollection[i].value);
            }
        }

        const sexCheckBoxCollection = document.getElementsByName("sex");
        for(let i=0; i<sexCheckBoxCollection.length; i++){
            if(sexCheckBoxCollection[i].checked) {
                params.append('sex', sexCheckBoxCollection[i].value);
            }
        }

        const sortRadioCollection = document.getElementsByName("sort");
        for(let i=0; i<sortRadioCollection.length; i++){
            if(sortRadioCollection[i].checked) {
                params.append('sort', sortRadioCollection[i].value);
            }
        }

        if(params.toString().length == 0) {
            window.location = window.location.pathname;
        } else {
            window.location.href = '?' + params.toString();
        }
    }
</script>
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
<c:if test="${showFilters}">
    <div class="d-flex justify-content-center align-items-center mx-4 mt-4 px-4">

        <c:forEach var="entry" items="${checkboxFilters}">
            <div class="dropdown mx-1">
                <button class="btn btn-outline-dark dropdown-toggle" type="button" id="${entry.getKey()}dropdownMenu"
                        data-bs-toggle="dropdown" aria-expanded="false">
                    ${filterNames.get(entry.getKey())}
                </button>
                <ul class="dropdown-menu scrollable-menu" aria-labelledby="${entry.getKey()}dropdownMenu">
                    <c:forEach var="value" items="${entry.getValue()}">
                        <li>
                            <div class="dropdown-item">
                                <div class="form-check">
                                    <c:choose>
                                        <c:when test="${value.isChecked()}">
                                            <input class="form-check-input" type="checkbox" name="${entry.getKey()}" value="${value.getId()}" checked>
                                            <label class="form-check-label">${value.getName()}</label>
                                        </c:when>
                                        <c:otherwise>
                                            <input class="form-check-input" type="checkbox" name="${entry.getKey()}" value="${value.getId()}">
                                            <label class="form-check-label" >${value.getName()}</label>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </c:forEach>

        <c:forEach var="entry" items="${radioFilters}">
            <div class="dropdown mx-1">
                <button class="btn btn-outline-dark dropdown-toggle" type="button" id="${entry.getKey()}dropdownMenu"
                        data-bs-toggle="dropdown" aria-expanded="false">
                        ${filterNames.get(entry.getKey())}
                </button>
                <ul class="dropdown-menu scrollable-menu" aria-labelledby="${entry.getKey()}dropdownMenu">
                    <c:forEach var="value" items="${entry.getValue()}">
                        <li>
                            <div class="dropdown-item">
                                <div class="form-check">
                                    <c:choose>
                                        <c:when test="${value.isChecked()}">
                                            <input class="form-check-input" type="radio" name="${entry.getKey()}" value="${value.getId()}" checked>
                                            <label class="form-check-label">${value.getName()}</label>
                                        </c:when>
                                        <c:otherwise>
                                            <input class="form-check-input" type="radio" name="${entry.getKey()}" value="${value.getId()}">
                                            <label class="form-check-label" >${value.getName()}</label>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </c:forEach>

        <button class="btn btn-outline-dark mx-1" onclick="applyFilters()">Применить</button>
    </div>
</c:if>
<div class="container my-5">
    <c:if test="${tutors.isEmpty()}">
        <div class="row justify-content-center">
            <div class="col">
                <span>Ничего не найдено. Попробуйте другие параметры запроса.</span>
            </div>
        </div>
    </c:if>
    <div class="row">
        <div class="col">
            <c:forEach var="tutor" items="${tutors}" varStatus="status">
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
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>
<%@include file="../elements/pagination.jsp"%>
</body>
</html>