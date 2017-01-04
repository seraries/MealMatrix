/* This file handles the functionality of the app via ajax calls after the 
 * initial displayMatrix.jsp response (containing the html referenced below)
 * @version v.14 1-3-2017 
 * @author Sarah Richardson
 */ 

 // this var is initialized with generate meals button and is used to print meals 
var mealData;

$(document).ready(function() {
 	// hide the "download/print meals" button until they've been generated
 	$("#printMeals").hide();

	// handle the submit button of the generate meals form below and 
	// append a text area to this page with meals listed
    $("#generateMeals").on("submit", function(event) {             	
    	$.post("http://mealmatrix.us-west-2.elasticbeanstalk.com/MakeMeals.do",
    		 $("#generateMeals").serialize(), function(responseXml) {               
        	// Parse XML, find <data> element and append its HTML to HTML DOM 
        	// element below with ID "mealDisplay".
        	$("#mealDisplay").html($(responseXml).find("data").html()); 
        	// use this to print meals (grab the data from jsp's data tags)
        	$mealData = $(responseXml).find("data"); 
	        // since generate meals button was clicked, show this one	
	        $("#printMeals").show(); 
		});
		// Important! Prevents submitting form with action declared in html
		 event.preventDefault(); 
	});
	
	// handle "download meals" button
    $(document).on("click", "#printMeals", function() { 
		 // start text file data string with info about the file
		var textFileData = "data:text/plain;charset=utf-8,";
		textFileData += $mealData.text();
		// adding Date to filename makes each filename unique
		var filename = Date() + "MyMeals.txt" 
		// convert the data string into a text download file
		var data = encodeURI(textFileData);

		// make the text file available on the webpage via html
		link = document.createElement('a');
   		link.setAttribute('href', data);
    	link.setAttribute('download', filename);
    	link.click();
    });

    // handle "Submit Changes" button/"Modify Matrix" form (add/delete food)
   $("#modifyMatrix").on("submit", function(event) {  
        $.post("http://mealmatrix.us-west-2.elasticbeanstalk.com/EditMatrix.do", 
        		$("#modifyMatrix").serialize(), function(responseXml) {         
        	updateMatrix(responseXml);
     	    $("#modifyMatrix")[0].reset(); // clear form values	
        });       
         event.preventDefault(); 
    });

   // handle "reset matrix to default foods" button
	$(document).on("click", "#resetMatrix", function() {  
	// sending the modifyMatrix form in post in order to set the content type
    // so I won't get 403 error from GoDaddy site. Fix this later (use $.ajax)
        $.post("http://mealmatrix.us-west-2.elasticbeanstalk.com/ResetMatrix.do",
        		$("#modifyMatrix").serialize(), function(responseXml) {               
        	updateMatrix(responseXml);
        }); 
    });
});

function updateMatrix(responseXml) {
    var foodAreas = new Array ("Veggies", "Proteins", "Fruits", "Fats", "Seasonings"); 

    for (var index = 0; index < foodAreas.length; index++){
        var divId = "#" + foodAreas[index];
        var dataId = foodAreas[index];
        $(divId).html($(responseXml).find(dataId).html()); 
    }
}