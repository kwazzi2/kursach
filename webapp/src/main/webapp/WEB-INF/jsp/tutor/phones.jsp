<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE HTML>
<html>
<head>
  <title>Контакты</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
<%@include file="../elements/header.jsp"%>
<div class="row justify-content-center">
    <div class="col">
        <c:if test="${phones.isEmpty()}">
            <span>ничего не найдено</span>
        </c:if>
        <c:forEach var="phone" items="${phones}">
            <span>${phone.getValue()}</span>
        </c:forEach>
    </div>
</div>
</body>
</html>