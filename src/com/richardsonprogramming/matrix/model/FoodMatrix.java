/* This class creates food objects from the Users_Foods table for a given user 
 * and makes them available in a 2D food list--list of foods by type then 
 * master list containing all lists by type, to the calling servlet. 
 * It's utilized by all the servlets except Logout and Register.
 *
 * @version v.15 1-18-2017
 * @author Sarah Richardson
 */

package com.richardsonprogramming.matrix.model;

import java.sql.*;
import javax.sql.DataSource;
import java.util.*;

public class FoodMatrix {
	// arrayList containing all food object lists by type (empty to start)
	private List<List<Food>> foodObjectLists;
	private DataSource ds;
	private int userId;

	public FoodMatrix(DataSource ds, int userId) {
		this.foodObjectLists = makeEmptyFoodLists();
		this.ds = ds;
		this.userId = userId;
	}
	
	private List<List<Food>> makeEmptyFoodLists(){
		List<List<Food>> emptyFoodLists = new ArrayList<List<Food>>();
		// food object lists containing food items, 1 list per food type
		List<Food> veggies = new ArrayList<Food>();
		List<Food> proteins = new ArrayList<Food>();
		List<Food> fruits = new ArrayList<Food>();
		List<Food> fats = new ArrayList<Food>();
		List<Food> seasonings = new ArrayList<Food>();
		// food type lists are added in same order as types are accessed in DB 
		emptyFoodLists.add(veggies);
		emptyFoodLists.add(proteins);
		emptyFoodLists.add(fruits);
		emptyFoodLists.add(fats);
		emptyFoodLists.add(seasonings);

		return emptyFoodLists;
	}

	public List<List<Food>> getFoodLists() {
		
		String selectSql = "SELECT type, name, servingSize, default_food FROM Food f "
						+ "JOIN Users_Foods uf on f.id = uf.food_id " 
						+ "WHERE type = ? AND user_id = ?";

		try (Connection con = ds.getConnection(); 
				PreparedStatement ps = con.prepareStatement(selectSql)) {
			
			String [] types = {"veggies", "proteins", "fruits", "fats", "seasonings"};
			// to index into appropriate food object list
			int index = 0;
			// for each food type, make food objects corresponding to data in db
			for (String eachType : types) {
				ps.setString(1, eachType);
				ps.setInt(2, userId);
		    	ResultSet rs = ps.executeQuery();

		    	//Extract data from result set
		    	while(rs.next()) {
			       	//Retrieve by column name
			        String typeName = rs.getString("type");
			        String name = rs.getString("name");
			        String servingSize = rs.getString("servingSize");
			        String isDefault = rs.getString("default_food");

			        //Add new food to the relevant food type list as a Food object
			        foodObjectLists.get(index).add(new Food(typeName, name, servingSize, isDefault));
			    }
			    rs.close();
				index++;		    			
			}		
		}	 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return foodObjectLists;
	}	
}

