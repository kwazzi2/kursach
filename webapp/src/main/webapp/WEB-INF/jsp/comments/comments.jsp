<%@ page import="java.util.Map" %>
<%@ page import="by.bsuir.webapp.model.comment.Rating" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Отзывы</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style><%@include file="../../../resources/css/style.css"%></style>
</head>
<body>
<script>
    function applyFilters() {
        const params = new URLSearchParams();

        const ratingCheckBoxCollection = document.getElementsByName("ratingFilter");

        for(let i=0; i<ratingCheckBoxCollection.length; i++){
            if(ratingCheckBoxCollection[i].checked) {
                params.append('rating', ratingCheckBoxCollection[i].value);
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
<%
    Map<String, String[]> map =request.getParameterMap() ;
    List<String> ratingFromParam = new ArrayList<>();
    if(map.get("rating")!=null ){
        ratingFromParam = List.of(map.get("rating"));
    }
    Rating[] ratingList = Rating.values();
%>
<div class="d-flex justify-content-center align-items-center m-4 px-4">

    <div class="dropdown mx-1">
        <button class="btn btn-outline-dark dropdown-toggle" type="button" id="dropdownMenu"
                data-bs-toggle="dropdown" aria-expanded="false">
            Рейтинг
        </button>
        <ul class="dropdown-menu scrollable-menu" aria-labelledby="dropdownMenu">
            <%for (Rating rating : ratingList){%>
            <li>
                <div class="dropdown-item">
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" id="<%=rating%>" name="ratingFilter" value=<%=rating.getOrder()%>  <%=ratingFromParam.contains(String.valueOf(rating.getOrder()))?"checked":""%>>
                        <label class="form-check-label" for="<%=rating%>">
                            <%for (int i=0;i<rating.getOrder();i++){%>
                                <i class="fa-solid fa-star yellow"></i>
                            <%}%>
                        </label>
                    </div>
                </div>
            </li>
            <%}%>
        </ul>
    </div>
    <button class="btn btn-outline-dark mx-1" onclick="applyFilters()">Применить</button>
</div>
<div class="container pb-3 text-dark">
    <div class="row d-flex justify-content-center">
        <div class="col-md-12 col-lg-10 col-xl-8">
            <div class="mb-3">
                <a class="text-black-50 fw-bold" href="${addCommentUrl}">Добавить отзыв</a>
            </div>
            <c:if test="${comments.isEmpty()}">
                <div class="card mb-3">
                    <div class="card-body">
                        <div class="d-flex flex-start">
                            <h5 class="fw-bold mb-1">Ничего не найдено. Попробуйте другие параметры запроса.</h5>
                        </div>
                    </div>
                </div>
            </c:if>
            <c:forEach var="comment" items="${comments}" varStatus="status">
                <div class="card mb-3">
                    <div class="card-body">
                        <div class="d-flex flex-start">
                            <div>
                                <h6 class="fw-bold mb-1">
                                        ${comment.getAuthor().getFirstName()}
                                        ${comment.getAuthor().getMiddleName()}
                                        ${comment.getAuthor().getLastName()}
                                </h6>
                                <div class="d-flex align-items-center mb-3">
                                    <p class="mb-0"><fmt:formatDate value="${comment.getCreatedAt()}" pattern="yyyy MM dd" /></p>
                                    <c:forEach begin="1" end="${comment.getRating().getOrder()}" var="i">
                                        <i class="fa-solid fa-star yellow"></i>
                                    </c:forEach>
                                </div>
                                <p class="mb-0">${comment.getBody()}</p>
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
