/* This file handles the functionality of the app via ajax calls after the displayMatrix.jsp response
 *@version v.15 1-18-2017 
 *@author Sarah Richardson
 */ 

 // this var is initialized with generate meals button and is utilized to print meals 
var mealData; 

$(document).ready(function() {
 	// hide the "download meals" button until they've been generated
 	$("#printMeals").hide();

    // handle the submit button of the generate meals form below and 
    // append a text area to this page with meals listed
    $("#generateMeals").on("submit", function(event) {             
        $.post("MakeMeals.do", $("#generateMeals").serialize(), function(responseXml) {              
        	// Parse XML, find <data> element and append its HTML to HTML DOM 
            // element below with ID "mealDisplay".
            $("#mealDisplay").html($(responseXml).find("data").html()); 
            // use this to print meals (grab the data from jsp's data tags)
            $mealData = $(responseXml).find("data");
            // since generate meals button has been clicked, show print option
            $("#printMeals").show();
		});		
		 event.preventDefault(); 
	});

	// handle "download meals" button
    $(document).on("click", "#printMeals", function() { 
		// start text file data string with info about the file
		var textFileData = "data:text/plain;charset=utf-8,";
		textFileData += $mealData.text();
        // adding Date to filename makes each filename unique
		var filename = Date() + "MyMeals.txt" 
		// convert the data string into a txt download file
		var data = encodeURI(textFileData);

		// make the txt file available on the webpage via html
		link = document.createElement('a');
   		link.setAttribute('href', data);
    	link.setAttribute('download', filename);
    	link.click();
    });

    // handle "Submit Changes" button/"Modify Matrix" form (add/delete food)
   $("#modifyMatrix").on("submit", function(event) {             
        $.post("EditMatrix.do", $("#modifyMatrix").serialize(), function(responseXml) {               
            updateMatrix(responseXml);
            $("#modifyMatrix")[0].reset(); // clear form values    
        }); 
        event.preventDefault(); 
    });

    // handle "reset matrix to default foods" button
    $(document).on("click", "#resetMatrix", function() { 
        // had to add sending the modifyMatrix form in order to set the content type
        // so that I wouldn't get 403 error from GoDaddy site. Fix this later (use .ajax)
        $.post("ResetMatrix.do", $("#modifyMatrix").serialize(), function(responseXml) {               
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