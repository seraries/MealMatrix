<?xml version="1.0" encoding="UTF-8"?>
<%@page contentType="application/xml" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!--Note: I don't actually want to go to a different page after pressing "generate meals" button, I just want to add a text area to displayMatrix.jsp/current html page that will show the random meals. So I changed this jsp so it's just an xml that the html of the other jsp (displayMatrix) can add to its html via javascript ajax -->
<data>
<c:forEach var="meal" items="${randomMeals}">${meal}&#13;&#10;</c:forEach>
</data>

