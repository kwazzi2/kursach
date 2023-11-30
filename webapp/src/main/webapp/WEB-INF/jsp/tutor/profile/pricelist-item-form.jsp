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
<%@include file="../../elements/header.jsp"%>
<div class="container">
    <div class="row justify-content-center">
        <div class="col-auto card mx-2">
            <div class="card-body">
                <div class="row">
                    <%--@elvariable id="form" type="by.bsuir.webapp.dto.PriceListItemDto"--%>
                    <form:form action="/save-pricelist-item${item == null? '': ('?itemId='.concat(item.getId()))}" 
                               method="post" modelAttribute="form" cssClass="mt-md-3">
                        <div class="form-group">
                            <form:label path="duration" cssClass="form-label">Длительность (мин)</form:label>
                            <form:errors path="duration" cssClass="text-danger" />
                            <form:input path="duration" type="number" cssClass="form-control"
                                        value="${item == null? '': item.getDuration()}"/>
                        </div>
                        <div class="form-group">
                            <form:label path="rate" cssClass="form-label">Стоимость (руб/час)</form:label>
                            <form:errors path="rate" cssClass="text-danger" />
                            <form:input path="rate" type="number" cssClass="form-control"
                                        value="${item == null? '': item.getRate()}"/>
                        </div>
                        <div class="form-group">
                            <form:label path="type" cssClass="form-label">Тип</form:label>
                            <form:errors path="type" cssClass="text-danger" />
                            <form:select path="type" cssClass="form-control">
                                <c:forEach items="${typeList}" var="type">
                                    <option value="${type.getOrder()}" ${type.getOrder() == item.getType().getOrder()? 'selected' : ''}
                                    >${type.getName()}</option>
                                </c:forEach>
                            </form:select>
                        </div>
                        <div class="form-group">
                            <form:label path="subject" cssClass="form-label">Тип</form:label>
                            <form:errors path="subject" cssClass="text-danger" />
                            <form:select path="subject" cssClass="form-control">
                                <c:forEach items="${subjectList}" var="subject">
                                    <option value="${subject.getId()}" ${subject.getId() == item.getSubject().getId()? 'selected' : ''}
                                    >${subject.getName()}</option>
                                </c:forEach>
                            </form:select>
                        </div>
                        <form:button class="btn btn-outline-dark mt-2">Сохранить</form:button>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>