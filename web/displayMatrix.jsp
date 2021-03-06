<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
	<head>
		<meta charset="utf-8">
    <!--make it responsive on mobile devices-->
    <meta name="viewport" content="width=device-width, initial-scale=1">
		<script src="http://code.jquery.com/jquery-latest.min.js"></script>
		<script src="js/bootstrap.min.js"></script>
		<link href="css/bootstrap.min.css" rel="stylesheet">
		<link href="css/matrix.css" rel="stylesheet">
		<script src="js/matrix.js"></script>	
	</head>

	<body>
		<nav class="navbar navbar-default">  
	  	<div class="container-fluid">
	   		<div class="navbar-header">
	     		<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#main-navbar" aria-expanded="false">
		     		<span class="sr-only">Toggle navigation</span>
		        <span class="icon-bar"></span>
		        <span class="icon-bar"></span>
		        <span class="icon-bar"></span>
	      	</button>
	   		</div> 
	   		<div class="collapse navbar-collapse"  id="main-navbar">
	     		<ul class="nav navbar-nav">
	       		<li ><a href="http://www.richardsonprogramming.com">RichardsonProgramming</a></li>
	       		<li class="active"><a href="http://mealmatrix.richardsonprogramming.com">Meal Matrix</a></li>
	       		<!--<li ><a href="html/video.html">Video Demo</a></li>-->
	     		</ul>
	   		</div>
	   	</div>
	  </nav>

 		<header>
 			<h1>Paleo Matrix Food Selections</h1>
 		</header>
 
	  <ol class="breadcrumb">
	  	<li><a href="http://www.richardsonprogramming.com">RichardsonProgramming</a></li>
	  	<li><a href="http://mealmatrix.richardsonprogramming.com">Meal Matrix</a></li>
	  	<li class="active">Use Matrix</li>
	  </ol>
		<br><!--This displays all the text areas with the ingredients listed for each food type with a header of the food type, based on what count we're on in the loop (-1 to make it zero-based) and using arraylist attribute "foodTypes"-->
		<form id="generateMeals" method="POST" action="MakeMeals.do"><div class="container"><div class="row"><c:forEach var="foodType" items="${foodLists}" varStatus="count"><div class="col-xs-9 col-md-2"><textarea id="${foodTypes[count.count - 1]}"><c:out value="${foodTypes[count.count - 1]}"/>:&#13;&#10;<c:forEach var="food" items="${foodType}">${food}&#13;&#10;</c:forEach></textarea><label for="${foodTypes[count.count - 1]}"># of <c:out value="${foodTypes[count.count - 1]}"/>/meal: </label><input type="number" min="0" max="10" step="1" name="${foodTypes[count.count - 1]}" id="${foodTypes[count.count - 1]}" value="0"></div>&nbsp</c:forEach></div></div> <!--13 and 10 format the new line, put this all on one line so no spaces in textareas <--><br><label for="numMeals">How many meals would you like to generate?</label>
		<input type="number" min="1" max="10" step="1" id="numMeals" name="numMeals" value="3" required="true">
		<input type="submit" name="submit" value="Generate Meals" id="genMeals">
		</form>
		<!--The form generated above lets user pick how many items per food type per meal, 
		and how many random meals to generate a list of -->
	
		<textarea id="mealDisplay" class="randomMeals">
		</textarea>
		<br>
		<input type="button" id="printMeals" name="printMeals" value="Download Meals!">
		<h2>Customize Matrix</h2>
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
		&nbsp
		<form id="logoutForm" method="POST" action="Logout.do">
			<input type="Submit" id="logout" name="logout" value="Logout">
		</form>
		<br>
	</body>
</html>