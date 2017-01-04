<?xml version="1.0" encoding="UTF-8"?>
<%@page contentType="application/xml" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!--this jsp is used by EditMatrixServlet and matrix.js to update the matrix textareas when user adds or deletes a food. Need to have this outer/root node of "foods" in order to successfully access the inner food type tags/nodes -->
<foods>
<c:forEach var="foodType" items="${foodLists}" varStatus="count"><${foodTypes[count.count - 1]}><c:out value="${foodTypes[count.count - 1]}"/>:&#13;&#10;<c:forEach var="food" items="${foodType}">${food}&#13;&#10;</c:forEach></${foodTypes[count.count - 1]}></c:forEach>
</foods>

