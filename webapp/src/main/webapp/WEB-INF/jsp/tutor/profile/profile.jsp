<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Профиль</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style><%@include file="../../../../resources/css/style.css"%></style>
</head>
<body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
<script>
    $(function () {
        // handle upload file
        $('#userCoverPhoto').change(function() {
            $("#frmUploadPhoto").submit();
        });
    });
</script>
<%@include file="../../elements/header.jsp"%>
<div class="container py-3">
    <div class="card mb-3">
        <div class="card-body">
            <div class="e-profile">
                <div class="row justify-content-md-center mb-3">
                    <div class="col-auto">
                        <c:if test="${tutor.getPhotoUrl() == null}">
                            <img src="/resources/defaulticon.svg"
                                 class="rounded-circle"
                                 height="140"
                                 loading="lazy">
                        </c:if>
                        <c:if test="${tutor.getPhotoUrl() != null}">
                            <img src="${tutor.getPhotoUrl()}"
                                 class="rounded-circle"
                                 height="140"
                                 loading="lazy">
                        </c:if>
                    </div>
                    <div class="col-auto justify-content-between">
                        <div class="text-center text-sm-left mb-2 mb-sm-0">
                            <h4 class="pt-sm-2 pb-1 mb-0 text-nowrap">${tutor.getAccount().getFirstName()} ${tutor.getAccount().getLastName()}</h4>
                            <p class="mb-0">${tutor.getAccount().getEmail()}</p>
                            <div class="mt-2">
                                <form action="upload-photo" id="frmUploadPhoto" enctype="multipart/form-data" method="post">
                                    <div class="file-input">
                                        <input
                                                type="file"
                                                name="userCoverPhoto"
                                                id="userCoverPhoto"
                                                class="file-input__input"
                                        />
                                        <label class="file-input__label" for="userCoverPhoto">
                                            <i class="fa fa-fw fa-camera" aria-hidden="true"></i>
                                            <span>Загрузить файл</span></label
                                        >
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="tab-content pt-3">
                    <form:form action="/tutor/profile" method="post" modelAttribute="tutorDto">
                        <div class="row">
                            <div class="col">
                                <div class="row">
                                    <div class="col">
                                        <div class="form-group">
                                            <form:label path="firstName" cssClass="form-label">Имя</form:label>
                                            <form:errors path="firstName" cssClass="text-danger" />
                                            <form:input path="firstName" cssClass="form-control" value="${tutor.getAccount().getFirstName()}"/>
                                        </div>
                                    </div>
                                    <div class="col">
                                        <div class="form-group">
                                            <form:label path="middleName" cssClass="form-label">Отчество</form:label>
                                            <form:errors path="middleName" cssClass="text-danger" />
                                            <form:input path="middleName" cssClass="form-control" value="${tutor.getAccount().getMiddleName()}"/>
                                        </div>
                                    </div>
                                    <div class="col">
                                        <div class="form-group">
                                            <form:label path="lastName" cssClass="form-label">Фамилия</form:label>
                                            <form:errors path="lastName" cssClass="text-danger" />
                                            <form:input path="lastName" cssClass="form-control" value="${tutor.getAccount().getLastName()}"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col mb-3">
                                        <div class="form-group">
                                            <form:label path="bio" cssClass="form-label">Описание</form:label>
                                            <form:input path="bio" cssClass="form-control" rows="5" value="${tutor.getBio()}"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col">
                                        <div class="form-group">
                                            <form:label path="experience" cssClass="form-label">Опыт</form:label>
                                            <form:errors path="experience" cssClass="text-danger" />
                                            <form:input path="experience" type="number" cssClass="form-control" value="${tutor.getExperience()}"/>
                                        </div>
                                    </div>
                                    <div class="col">
                                        <div class="form-group">
                                            <form:label path="sex" cssClass="form-label">Пол</form:label>
                                            <form:select path="sex" cssClass="form-control">
                                                <c:if test="${tutor.getSex() == null}">
                                                    <option value="" selected>Не выбрано</option>
                                                </c:if>
                                                <c:if test="${tutor.getSex() != null}">
                                                    <option value="">Не выбрано</option>
                                                </c:if>

                                                <c:forEach items="${sexList}" var="sex">
                                                    <option value="${sex.getOrder()}" ${sex.getOrder() == tutor.getSex().getOrder()? 'selected' : ''}
                                                    >${sex.getName()}</option>
                                                </c:forEach>
                                            </form:select>
                                        </div>
                                    </div>
                                    <div class="col">
                                        <div class="form-group">
                                            <form:label path="scienceDegree" cssClass="form-label">Ученая степень</form:label>
                                            <form:select path="scienceDegree" cssClass="form-control">
                                                <c:if test="${tutor.getScienceDegree() == null}">
                                                    <option value="" selected>Не выбрано</option>
                                                </c:if>
                                                <c:if test="${tutor.getScienceDegree() != null}">
                                                    <option value="">Не выбрано</option>
                                                </c:if>

                                                <c:forEach items="${degreeList}" var="degree">
                                                    <option value="${degree.getId()}" ${degree.getId().equals(tutor.getScienceDegree().getId())? 'selected' : ''}>${degree.getName()}</option>
                                                </c:forEach>
                                            </form:select>
                                        </div>
                                    </div>
                                </div>

                            </div>
                        </div>
                        <div class="row mt-2">
                            <div class="col d-flex justify-content-end">
                                <button class="btn btn-outline-dark" type="submit">Сохранить</button>
                            </div>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
