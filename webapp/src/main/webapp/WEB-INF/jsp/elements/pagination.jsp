<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<c:url value="${pageContext.request.queryString}" var="query"/>
<ul class="pagination justify-content-end">
    <c:if test="${currentPage != 1}">
        <c:choose>
            <c:when test="${query == null || query.isEmpty()}">
                <li class="page-item"><a class="page-link" href="?page=${currentPage - 1}">Предыдущая</a></li>
            </c:when>
            <c:otherwise>
                <c:choose>
                    <c:when test="${query.contains('page=')}">
                        <li class="page-item"><a class="page-link" href="?${query.substring(0,query.indexOf("page="))}page=${currentPage - 1}">Предыдущая</a></li>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item"><a class="page-link" href="?${query}&page=${currentPage - 1}">Предыдущая</a></li>
                    </c:otherwise>
                </c:choose>
            </c:otherwise>
        </c:choose>
    </c:if>

    <%--For displaying Page numbers.
    The when condition does not display a link for the current page--%>
    <c:forEach begin="1" end="${totalPages}" var="i">
        <c:choose>
            <c:when test="${currentPage eq i}">
                <li class="page-item active">
                          <span class="page-link">
                            ${i}
                            <span class="sr-only">(current)</span>
                          </span>
                </li>
            </c:when>
            <c:otherwise>
                <c:choose>
                    <c:when test="${query == null && query.isEmpty()}">
                        <li class="page-item"><a class="page-link" href="?page=${i}">${i}</a></li>
                    </c:when>
                    <c:otherwise>
                        <c:choose>
                            <c:when test="${query.contains('page=')}">
                                <li class="page-item"><a class="page-link" href="?${query.substring(0,query.indexOf("page="))}page=${i}">${i}</a></li>
                            </c:when>
                            <c:otherwise>
                                <li class="page-item"><a class="page-link" href="?${query}&page=${i}">${i}</a></li>
                            </c:otherwise>
                        </c:choose>
                    </c:otherwise>
                </c:choose>
            </c:otherwise>
        </c:choose>
    </c:forEach>
    <%--For displaying Next link --%>
    <c:if test="${currentPage lt totalPages}">
        <c:choose>
            <c:when test="${query == null || query.isEmpty()}">
                <li class="page-item"><a class="page-link" href="?page=${currentPage + 1}">Следующая</a></li>
            </c:when>
            <c:otherwise>
                <c:choose>
                    <c:when test="${query.contains('page=')}">
                        <li class="page-item"><a class="page-link" href="?${query.substring(0,query.indexOf("page="))}page=${currentPage + 1}">Следующая</a></li>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item"><a class="page-link" href="?${query}&page=${currentPage + 1}">Следующая</a></li>
                    </c:otherwise>
                </c:choose>
            </c:otherwise>
        </c:choose>
    </c:if>
</ul>