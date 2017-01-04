 /* This file handles the beginning functionality of the app via ajax call 
 * from the index.html page (the Use Meal Generator button)
 * @version v.14 1-3-2017 
 * @author Sarah Richardson
 */ 

 // handles "Use Meal Generator" button--first ajax call to AWS
$(document).on("submit", "#startMatrix", function(event) {
 	var xhr = new XMLHttpRequest();

	xhr.open('POST', "http://mealmatrix.us-west-2.elasticbeanstalk.com/FillMatrix.do");
	// avoid 403 error by setting request header
	xhr.setRequestHeader('Content-type', 'text/html');
	
	xhr.onload = function(){
		var responseText = xhr.responseText;
		// not ideal, but gets the job done for now--same url, but shows jsp 
		// contents on the page "mealmatrix.richardsonprogramming.com". 
		// Since it appends the html to that page.
		$("#main").append(responseText); 
		$("#startMatrix").remove(); // don't need this button anymore
	}
	xhr.onerror = function() {
		console.log("there was an error");
	}
	xhr.send();

    event.preventDefault(); // Important! 
});