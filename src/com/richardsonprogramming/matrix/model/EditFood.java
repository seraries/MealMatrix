/* This class modifies the Users_Foods table for a given user by adding
 * items to it or deleting items from it. If a food being added is not already
 * in the Food table, it adds it there as well. 
 * It is controlled by EditMatrixServlet. 
 *
 * @version v.15 1-18-2017
 * @author Sarah Richardson
 */

package com.richardsonprogramming.matrix.model;

import java.sql.*;
import java.util.*;
import javax.sql.DataSource;

public class EditFood {

	// datasource I'll use for connection pooling
	private DataSource ds;
	private int userId;

	public EditFood(DataSource ds, int userId) {
		this.ds = ds;
		this.userId = userId;
	}

	// Takes user's input of a food name and food type to insert food in DB
	public void addFood(String foodType, String foodName) {
		
		// create food object first so constructor assigns default servingSize
		Food newFood = new Food(foodType, foodName);
		
		String queryCheck = "SELECT id FROM Food WHERE name = ?"; 
		String insertSql = "INSERT INTO Food values (NULL, ?, ?, ?, ?)"; 
		String addToUsersFoods = "INSERT INTO Users_Foods values (?, ?)";
		
		try (Connection con = ds.getConnection(); 
			PreparedStatement psQuery = con.prepareStatement(queryCheck); 
			PreparedStatement psInsert = con.prepareStatement(insertSql);
			PreparedStatement psAddUsersFoods = con.prepareStatement(addToUsersFoods)) {

			psQuery.setString(1, newFood.getName());
			ResultSet rs = psQuery.executeQuery();
			int foodId = 0;

			// if this name does NOT already exist, insert the food data into Food table
			if(!rs.next()) {
				psInsert.setString(1, newFood.getType());
				psInsert.setString(2, newFood.getName()); 
				psInsert.setString(3, newFood.getServingSize());
				psInsert.setString(4, newFood.getIsDefault());
	    		psInsert.executeUpdate();
	    	}
	    	rs.close();
    		// use same query as before to get the id for the food
    		// whether it is newly created or not
    		// and then add that food to the Users_Foods table
    		ResultSet rs1 = psQuery.executeQuery();
    		while(rs1.next()) {
    			foodId = rs1.getInt("id");
    			psAddUsersFoods.setInt(1, userId);
    			psAddUsersFoods.setInt(2, foodId);
    			psAddUsersFoods.executeUpdate();
    		}
    		rs1.close();			
		}	 
		catch (SQLException e) {
			e.printStackTrace();
		}		
	}

	// Takes user's input of a food name and deletes that food from users matrix
	public void deleteFood(String foodName) { 
		String selectSql = "SELECT id FROM Food WHERE name = ?";
		String deleteSql = "DELETE FROM Users_Foods WHERE food_id = ? AND user_id = ?";

		try (Connection con = ds.getConnection(); 
			PreparedStatement psSelect = con.prepareStatement(selectSql);
			PreparedStatement psDelete = con.prepareStatement(deleteSql)) {

			psSelect.setString(1, foodName);
			ResultSet rs = psSelect.executeQuery();
			int foodId = 0;
			if(rs!=null){
				while(rs.next()){
					foodId = rs.getInt("id");	
					psDelete.setInt(1, foodId);
					psDelete.setInt(2, userId);
					psDelete.executeUpdate();
				}
			}
			rs.close();						
		}	 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
}