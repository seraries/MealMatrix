/* This file handles the functionality of the app via ajax calls after the displayMatrix.jsp response
 *@version v.15 1-18-2017 
 *@author Sarah Richardson
 */ 

 // this var is initialized with generate meals button and is utilized to print meals 
var mealData; 

// This handles the submit button of the generate meals form below and appends a text area to this page with meals listed
$(document).ready(function() {
 	// hide the "download meals" button until they've been generated
 	$("#printMeals").hide();

    $("#generateMeals").on("submit", function(event) {             
    // When HTML DOM "submit" event is invoked on form element with ID "generateMeals", execute the following function...
    	
    	$.post("MakeMeals.do", $("#generateMeals").serialize(), function(responseXml) {              
    	// Execute Ajax POST request on URL of "MakeMeals.do" ("id".serialize() sends generateMeals form with the request)
    	// and execute the following function with Ajax response XML (since displayMeals.jsp is just XML, not http)
        $("#mealDisplay").html($(responseXml).find("data").html()); 
        // Parse XML, find <data> element and append its HTML to HTML DOM element below with ID "mealDisplay".
        $mealData = $(responseXml).find("data"); // I'll use this to print meals (grab them from jsp's data tags)

        $("#printMeals").show(); // now that the generate meals button has been clicked, I can show this one
		});
		
		 event.preventDefault(); // Important! Prevents submitting the form with the action declared in html
	});
	
    $(document).on("click", "#printMeals", function() { 
		 // start text file data string with info about the file
		var textFileData = "data:text/plain;charset=utf-8,";
		textFileData += $mealData.text();
		var filename = Date() + "MyMeals.txt" // adding Date to filename makes each filename unique
		// convert the data string into a txt download file
		var data = encodeURI(textFileData);

		// make the txt file available on the webpage via html
		link = document.createElement('a');
   		link.setAttribute('href', data);
    	link.setAttribute('download', filename);
    	link.click();
    });

   $("#modifyMatrix").on("submit", function(event) {             
        
        $.post("EditMatrix.do", $("#modifyMatrix").serialize(), function(responseXml) {               
        // Execute Ajax POST request on URL of "EditMatrix.do" ("id".serialize() sends modifyMatrix form with the request)
        // and execute the following function with Ajax response (updateMatrix.jsp)
        
            var foodAreas = new Array ("Veggies", "Proteins", "Fruits", "Fats", "Seasonings");

            for (var index = 0; index < foodAreas.length; index++){
                var divId = "#" + foodAreas[index]; // these vars work fine 
                var dataId = foodAreas[index];
                $(divId).html($(responseXml).find(dataId).html()); 
            }
            $("#modifyMatrix")[0].reset(); // clear form values
     
        }); 
        
         event.preventDefault(); 
    });

     $(document).on("click", "#resetMatrix", function() { 
        // had to add sending the modifyMatrix form in order to set the content type
        // so that I wouldn't get 403 error from GoDaddy site. Fix this later (use .ajax)
        $.post("ResetMatrix.do", $("#modifyMatrix").serialize(), function(responseXml) {               
        
            var foodAreas = new Array ("Veggies", "Proteins", "Fruits", "Fats", "Seasonings"); 

            for (var index = 0; index < foodAreas.length; index++){
                var divId = "#" + foodAreas[index];
                var dataId = foodAreas[index];
                $(divId).html($(responseXml).find(dataId).html()); 
            }
        }); 
    });
});
