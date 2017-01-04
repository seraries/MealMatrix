/* This class modifies the Foods DB by adding items to it or deleting items
 * from it. It is controlled by EditMatrixServlet. 
 *
 * @version v.14 1-3-2017
 * @author Sarah Richardson
 */

package com.richardsonprogramming.matrix.model;

import java.sql.*;
import java.util.*;
import javax.servlet.ServletContext;

public class EditFood {

	// needed to access dabatase
	private ServletContext sc;

	public EditFood(ServletContext sc) {
		this.sc = sc;
	}

	// Takes user's input of a food name and food type to insert food in DB
	public void addFood(String foodType, String foodName) {
		// create food object first so constructor assigns default servingSize
		Food newFood = new Food(foodType, foodName);

		// Don't put in try with resources since doing so closes the connection
		Connection con = (Connection) sc.getAttribute("DBConnection"); 

		// to check if food is not already in db before inserting
		String queryCheck = "SELECT * FROM Food WHERE name = ?"; 
		// null for auto incremented id column
		String insertSql = "INSERT INTO Food values (NULL, ?, ?, ?)"; 
		
		try (PreparedStatement psQuery = con.prepareStatement(queryCheck); 
				PreparedStatement psInsert = con.prepareStatement(insertSql)) {

			psQuery.setString(1, newFood.getName());
			ResultSet rs = psQuery.executeQuery();

			// if this name does NOT already exist, insert the food data
			if(!rs.next()) {
				psInsert.setString(1, newFood.getType());
				psInsert.setString(2, newFood.getName()); 
				psInsert.setString(3, newFood.getServingSize());
	    		psInsert.executeUpdate();
			}
			rs.close();
		}	 
		catch (SQLException e) {
			e.printStackTrace();
		}		
	}

	// Takes user's input of a food name and deletes that food from DB
	public void deleteFood(String foodName) {
		Connection con = (Connection) sc.getAttribute("DBConnection"); 
		String deleteSql = "DELETE FROM Food WHERE name = ?";

		try (PreparedStatement psDelete = con.prepareStatement(deleteSql)) {
			psDelete.setString(1, foodName);
	    	psDelete.executeUpdate();					
		}	 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
}