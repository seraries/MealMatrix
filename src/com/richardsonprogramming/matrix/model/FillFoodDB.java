/* This class creates food objects from hard-coded data arrays. It then uses
 * these food objects to fill the table it creates, Food, with their data,
 * in the Foods database.
 * It is controlled by ResetMatrixServlet, in response to reset button click.
 *
 * @version v.14 1-3-2017
 * @author Sarah Richardson
 */

package com.richardsonprogramming.matrix.model;

import java.sql.*;
import java.util.*;
import javax.servlet.ServletContext;

public class FillFoodDB {

	public void resetDB(ServletContext sc) {
		Connection con = (Connection) sc.getAttribute("DBConnection"); 
		// raw data arrays (initial foods to put in the database)
		String [][] foodData = {{"veggies", "celery", "1/2 cup"}, {"veggies", 
		"mushrooms", "1 cup"}, {"veggies", "tomatoes", "1/2 cup"}, {"veggies", 
		"spinach", "1 cup"}, {"veggies", "zucchini", "1/2 cup"}, {"veggies", 
		"brussel sprouts", "1 cup"}, {"veggies", "broccoli", "1 cup"}, {"veggies", 
		"sweet potato", "1/2 cup"}, {"veggies", "carrots", "1/2 cup"}, {"veggies", 
		"kale", "1 cup"}, {"veggies", "romaine", "1 cup"}, {"veggies", 
		"winter squash", "1/2 cup"}, {"veggies", "cabbage", "1/2 cup"}, {"veggies", 
		"onions", "1/4 cup"}, {"veggies", "cauliflower", "1/2 cup"}, {"veggies", 
		"green beans", "1 cup"}, {"veggies", "peas", "1/2 cup"}, {"veggies", 
		"turnip greens", "1 cup"}, {"veggies", "okra", "1/2 cup"}, {"veggies", 
		"sauerkraut", "1/4 cup"}, {"veggies", "artichoke hearts", "1/2 cup"}, 
		{"veggies", "peppers", "1/2 cup"}, {"veggies", "swiss chard", "1/2 cup"}, 
		{"veggies", "bok choy", "1/2 cup"}, {"veggies", "collard greens", "1/2 cup"}, 
		{"veggies", "eggplant", "1/2 cup"},
		{"proteins", "lamb chop", "4 oz"}, {"proteins", "ground beef", "4 oz"}, 
		{"proteins", "pork steak", "5 oz"}, {"proteins", "turkey breast", "4 oz"},
		{"proteins", "salmon", "5 oz"}, {"proteins", "eggs", "2 whole"}, 
		{"proteins", "steak", "5 oz"}, {"proteins", "tuna", "4 oz"}, 
		{"proteins", "chicken thighs", "5 oz"}, {"proteins", "shrimp", "4 oz"}, 
		{"proteins", "pork chop", "4 oz"}, {"proteins", "chicken breast", "4 oz"}, 
		{"proteins", "tilapia", "4 oz"}, {"proteins", "mahi mahi", "4 oz"}, 
		{"proteins", "pork loin", "4 oz"}, {"proteins", "steak", "5 oz"}, 
		{"proteins", "chicken legs", "5 oz"}, {"proteins", "egg whites", "3 tbsp"}, 
		{"proteins", "swordfish", "4 oz"}, {"proteins", "trout", "5 oz"},
		{"proteins", "ground turkey", "4 oz"}, {"proteins", "mussels", "3 oz"}, 
		{"proteins", "leg of lamb", "5 oz"}, {"proteins", "cod", "4 oz"}, 
		{"proteins", "bison", "4 oz"}, 
		{"fruits", "blueberries", "1/4 cup"}, {"fruits", "cherries", "1/4 cup"}, 
		{"fruits", "watermelon", "1/2 cup"}, {"fruits", "apples", "1/2 cup"}, 
		{"fruits", "raspberries", "1/2 cup"}, {"fruits", "mango", "1/2 cup"}, 
		{"fruits", "oranges", "1/3 cup"}, {"fruits", "blackberries", "1/2 cup"},
		{"fruits", "pears", "1/2 cup"}, {"fruits", "strawberries", "1/2 cup"}, 
		{"fruits", "plums", "1/2 cup"}, {"fruits", "cantaloupe", "1/2 cup"}, 
		{"fruits", "nectarines", "1/2 cup"}, {"fruits", "kiwifruit", "1/2 cup"}, 
		{"fruits", "pomegranate", "1/2 cup"}, 
		{"fats", "olive oil", "1 tbsp"}, {"fats", "ghee", "1 tbsp"}, {"fats", 
		"avocado oil", "1 tbsp"}, {"fats", "almonds", "1/4 cup"}, {"fats", 
		"coconut oil", "1 tbsp"}, {"fats", "mayonnaise", "2 tbsp"}, {"fats", 
		"peanut butter", "2 tbsp"}, {"fats", "sesame oil", "1 tbsp"}, {"fats", 
		"walnut oil", "1 tbsp"}, {"fats", "sunflower seeds", "1 tbsp"}, {"fats", 
		"salad dressing", "1 tbsp"}, {"fats", "heavy cream", "2 tbsp"}, {"fats", 
		"lard", "1 tbsp"}, {"fats", "olives", "1/4 cup"}, {"fats", 
		"pecans", "1/4 cup"}, {"fats", "whole milk kefir", "1/2 cup"},
		{"fats", "duck fat", "1 tbsp"},
		{"seasonings", "basil", "1 tsp"}, {"seasonings", "rosemary", "1/2 tbsp"},
		{"seasonings", "thyme", "1 tsp"}, {"seasonings", "hot sauce", "1 tbsp"},
		{"seasonings", "pepper", "1 tsp"}, {"seasonings", "lime juice", "1 tbsp"}, 
		{"seasonings", "lemon juice", "1 tbsp"}, {"seasonings", "garlic", "1 tbsp"}, 
		{"seasonings", "cayenne", "1 tsp"}, {"seasonings", "ginger", "1 tsp"}, 
		{"seasonings", "turmeric", "1 tsp"}, {"seasonings", "oregano", "1 tsp"}, 
		{"seasonings", "chives", "1 tbsp"}, {"seasonings", "mustard", "1 tbsp"}, 
		{"seasonings", "ketchup", "2 tbsp"}, {"seasonings", "cumin", "1 tbsp"}, 
		{"seasonings", "curry", "1 tbsp"}, {"seasonings", "cilantro", "1 tsp"},
		{"seasonings", "vinegar", "1 tbsp"}, {"seasonings", "bbq", "1 tbsp" }, 
		{"seasonings", "capers", "1 tbsp"}, {"seasonings", "savory", "1 tsp"}, 
		{"seasonings", "dill", "1 tsp"}, {"seasonings", "herbs de provence", "1 tsp"}, 
		{"seasonings", "tandoori", "1 tbsp"}, {"seasonings", "tarragon", "1 tsp"}, 
		{"seasonings", "allspice", "1 tsp"}, {"seasonings", "cinnamon", "1 tsp"},
		{"seasonings", "chili powder", "2 tsp"}, {"seasonings", "coriander", "1 tsp"}, 
		{"seasonings", "marjoram", "1 tsp"}, {"seasonings", "garlic salt", "1 tsp"}, 
		{"seasonings", "paprika", "1 tsp"}, {"seasonings", "cooking wine", "1/4 cup"}, 
		{"seasonings", "sage", "1 tsp"}, {"seasonings", "coconut aminos", "1 tbsp"},  
		{"seasonings", "worcestershire sauce", "1 tsp"}};

		// food object list [will create food objects corresponding to raw data array 
		// and use these objects to fill DB]
		List<Food> foodsList = new ArrayList<Food>();
		foodsList = createObjectList(foodData);
		
		// connect with DB and store items there
		String dropSql = "DROP TABLE IF EXISTS Food";
		String createSql = "CREATE TABLE Food (id INT AUTO_INCREMENT PRIMARY KEY, "
						+ "type varchar(20), name varchar(45), servingSize varchar(45))";
		String insertSql = "insert into Food values (NULL, ?, ?, ?)";

		try (PreparedStatement psDrop = con.prepareStatement(dropSql); 
			PreparedStatement psCreate = con.prepareStatement(createSql);
			PreparedStatement psInsert = con.prepareStatement(insertSql)) {

			// drop table if exists and create new Foods table
			psDrop.executeUpdate();
			psCreate.executeUpdate();

			// populate table using food objects
			for (Food item : foodsList) {
				psInsert.setString(1, item.getType());
				psInsert.setString(2, item.getName()); 
				psInsert.setString(3, item.getServingSize()); 
				psInsert.executeUpdate();
			}	
		}
	   catch(SQLException se) {
	      //Handle errors for JDBC
	      se.printStackTrace();
	   }
	} 

	public List<Food> createObjectList(String [][] dataArray){
		List<Food> objectList = new ArrayList<Food>();
		for (int index = 0; index < dataArray.length; index++) {
			objectList.add(new Food(dataArray[index][0], dataArray[index][1], 
							dataArray[index][2])); 
		}
		return objectList;
	}
}

