<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
	<script src="http://code.jquery.com/jquery-latest.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<link href="css/bootstrap.min.css" rel="stylesheet">
	<link href="css/matrix.css" rel="stylesheet">
	<script src="js/matrix.js"></script>
	
</head>
<body>
	<h2 align="center">Paleo Matrix Food Selections</h2>
	<br><!--This displays all the text areas with the ingredients listed for each food type with a header of the food type, based on what count we're on in the loop (-1 to make it zero-based) and using arraylist attribute "foodTypes"-->
	<form id="generateMeals" method="POST" action="MakeMeals.do"><div class="container"><div class="row"><c:forEach var="foodType" items="${foodLists}" varStatus="count"><div class="col-xs-9 col-md-2"><textarea id="${foodTypes[count.count - 1]}"><c:out value="${foodTypes[count.count - 1]}"/>:&#13;&#10;<c:forEach var="food" items="${foodType}">${food}&#13;&#10;</c:forEach></textarea><label for="${foodTypes[count.count - 1]}"># of <c:out value="${foodTypes[count.count - 1]}"/>/meal: </label><input type="number" min="0" max="10" step="1" name="${foodTypes[count.count - 1]}" id="${foodTypes[count.count - 1]}" value="0"></div>&nbsp</c:forEach></div></div> <!--13 and 10 format the new line, put this all on one line so no spaces in textareas <--><br><label for="numMeals">How many meals would you like to generate?</label>
		<input type="number" min="1" max="10" step="1" id="numMeals" name="numMeals" value="3" required="true">
		<input type="submit" name="submit" value="Generate Meals" id="genMeals">
	</form>
	<!--The form generated above lets user pick how many items per food type per meal, and how many random meals to generate a list of -->
	
		
	<textarea id="mealDisplay" class="randomMeals">
	</textarea>
	<br>
	<input type="button" id="printMeals" name="printMeals" value="Download Meals!">
	<h3 align="center">Customize Matrix</h3>
	<div class="editForm">
		<form id="modifyMatrix" method="POST" action="EditMatrix.do">
			<label for="foodEdit">Food: </label>
			<input type="text" name="foodEdit" id="foodEdit" placeholder="name of food" required="true">
			<input type="radio" name="typeOfEdit" id="addFood" value="add" required="true">Add
			<input type="radio" name="typeOfEdit" id="deleteFood" value="delete">Delete
			<br>
			<label for="veggie">Type Of Food: </label>
			<input type="radio" name="typeOfFood" id="veggie" value="veggies" required="true">Veggie
			<input type="radio" name="typeOfFood" id="protein" value="proteins" required="true">Protein
			<input type="radio" name="typeOfFood" id="fruit" value="fruits" required="true">Fruit
			<input type="radio" name="typeOfFood" id="fat" value="fats" required="true">Fat
			<input type="radio" name="typeOfFood" id="seasoning" value="seasonings" required="true">Seasoning	
			&nbsp
			<input type="submit" name="editSubmit" value="Submit Changes" id="editFoodsButton">
		</form>
	</div>
	<br>
	<input type="button" id="resetMatrix" name="resetMatrix" value="Reset Food Lists To Default Options">
	<br>
</body>
</html>