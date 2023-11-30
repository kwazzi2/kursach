<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<link rel="shortcut icon" href="/resources/favicon.ico" type="image/x-icon" />
<script src="https://kit.fontawesome.com/e4dbc476a3.js" crossorigin="anonymous"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
<header>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container-fluid">
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <div class="navbar-nav me-auto mb-2 mb-lg-0">
                    <div class="nav-item">
                        <a class="nav-link text-light" href="/">Главная</a>
                    </div>
                    <sec:authorize access="!hasAuthority('TUTOR')">
                        <div class="nav-item">
                            <a class="nav-link text-light" href="/tutor-registration">Для репетиторов</a>
                        </div>
                    </sec:authorize>
                    <div class="nav-item">
                        <a class="nav-link text-light" href="/chart">Граффик</a>
                    </div>
                    <form action="/download" method="get" style="margin-top: auto; margin-bottom: auto" class="px-3">
                        <button class="btn btn-outline-light" type="submit">Файл</button>
                    </form>
                    <form action="/tutors/search"method="get" class="d-flex input-group w-auto">
                        <input
                                name="query"
                                type="search"
                                class="form-control rounded"
                                placeholder="Поиск"
                                aria-label="Search"
                                aria-describedby="search-addon"
                        />
                        <button class="btn " style="background-color:transparent" type="submit" >
                            <i class="fas fa-search text-white"></i>
                        </button>
                    </form>
                </div>
<%--                right buttons--%>
                <div class="d-flex align-items-center">
                    <a class="text-light me-3" href="/comparison">
                        <i class="fa fa-balance-scale" aria-hidden="true"></i>
                        <span id="comparingCount" class="badge rounded-pill badge-notification bg-danger">
                        <c:if test="${sessionScope.comparisonList == null}">
                            0
                        </c:if>
                        <c:if test="${sessionScope.comparisonList != null}">
                            ${sessionScope.comparisonList.size()}
                        </c:if>
                    </span>
                    </a>
                    <sec:authorize access="!isAuthenticated()">
                        <a href="/login"><button class="btn btn-outline-light px-3" type="submit">Войти</button></a>
                    </sec:authorize>

                    <sec:authorize access="isAuthenticated() && !hasAuthority('TUTOR')">
                        <form action="/logout" method="post" style="margin-top: auto; margin-bottom: auto">
                            <button class="btn btn-outline-light px-3" type="submit">Выйти</button>
                        </form>
                    </sec:authorize>
                    <sec:authorize access="isAuthenticated() && hasAuthority('TUTOR')">
                        <div class="dropdown">
                            <a
                                    class="dropdown-toggle d-flex align-items-center hidden-arrow text-light"
                                    href="#"
                                    id="navbarDropdownMenuAvatar"
                                    role="button"
                                    data-bs-toggle="dropdown"
                                    aria-expanded="false"
                            >
                                <i class="fa fa-bars" aria-hidden="true"></i>
                            </a>
                            <ul
                                    class="dropdown-menu dropdown-menu-end"
                                    aria-labelledby="navbarDropdownMenuAvatar"
                            >
                                <li>
                                    <a class="dropdown-item" href="/tutor/profile">Анкета</a>
                                </li>
                                <li>
                                    <a class="dropdown-item" href="/tutor/price-list">Список услуг</a>
                                </li>
                                <li>
                                    <a class="dropdown-item" href="/tutor/phones">Телефоны</a>
                                </li>
                                <li>
                                    <a class="dropdown-item" href="/tutor/settings">Настройки</a>
                                </li>
                                <li>
                                    <a class="dropdown-item" href="/tutor/stat">Статистика</a>
                                </li>
                                <li>
                                    <a class="dropdown-item" href="/logout">Выход</a>
                                </li>
                            </ul>
                        </div>
                    </sec:authorize>
                </div>

            </div>
        </div>
    </nav>
</header>