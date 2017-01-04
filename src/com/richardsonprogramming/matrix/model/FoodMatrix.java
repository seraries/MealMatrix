/* This class creates food objects from food data in the database (FoodDB)
 * and makes them available in a 2D food list--list of foods by type then 
 * master list containing all lists by type, to the calling servlet. 
 * It's utilized by all the servlets.
 *
 * @version v.14 1-3-2017
 * @author Sarah Richardson
 */

package com.richardsonprogramming.matrix.model;

import java.sql.*;
import java.util.*;
import javax.servlet.ServletContext;

public class FoodMatrix {

	public List<List<Food>> getFoodLists(ServletContext sc) {
		
		// food object lists containing food items, 1 list per food type
		List<Food> veggies = new ArrayList<Food>();
		List<Food> proteins = new ArrayList<Food>();
		List<Food> fruits = new ArrayList<Food>();
		List<Food> fats = new ArrayList<Food>();
		List<Food> seasonings = new ArrayList<Food>();

		// arrayList containing all food object lists by type 
		List<List<Food>> foodObjectLists = new ArrayList<List<Food>>();

		// food type lists are added in same order as types are accessed in DB 
		foodObjectLists.add(veggies);
		foodObjectLists.add(proteins);
		foodObjectLists.add(fruits);
		foodObjectLists.add(fats);
		foodObjectLists.add(seasonings);

		Connection con = (Connection) sc.getAttribute("DBConnection"); 
		String selectSql = "SELECT type, name, servingSize FROM Food WHERE type = ?";

		try (PreparedStatement ps = con.prepareStatement(selectSql)) {
			
			String [] types = {"veggies", "proteins", "fruits", "fats", "seasonings"};
			// to index into appropriate food object list
			int index = 0;
			// for each food type, make food objects corresponding to data in db
			for (String eachType : types) {
				ps.setString(1, eachType);
		    	ResultSet rs = ps.executeQuery();

		    	//Extract data from result set
		    	while(rs.next()) {
			       	//Retrieve by column name
			        String typeName = rs.getString("type");
			        String name = rs.getString("name");
			        String servingSize = rs.getString("servingSize");

			        //Add new food to the relevant food type list as a Food object
			        foodObjectLists.get(index).add(new Food(typeName, name, servingSize));
			    }
			    index++;
			    // clean up resources
		    	rs.close();
			}		
		}	 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return foodObjectLists;
	}	
}

