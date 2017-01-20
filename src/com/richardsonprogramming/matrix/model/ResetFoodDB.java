/* This class removes all user-added foods from the Users_Foods table
 * for a particular user so that only default foods remain in their matrix.
 * It is controlled by ResetMatrixServlet, in response to reset button click.
 *
 * @version v.15 1-18-2017
 * @author Sarah Richardson
 */

package com.richardsonprogramming.matrix.model;

import java.sql.*;
import java.util.*;
import javax.sql.DataSource;

public class ResetFoodDB {

	private DataSource ds; 
	private int userId;
	
	public ResetFoodDB(DataSource ds, int userId) {
		this.ds = ds;
		this.userId = userId;
	}

	public void resetDB() {
		deleteUserAddedFoods();
		addUserDeletedFoods();
	}

	// Delete foods that aren't default ones from this user's table
	private void deleteUserAddedFoods() {
		String selectSql = "SELECT id FROM Food WHERE default_food = 'no'";
		String deleteSql = "DELETE FROM Users_Foods where user_id = ? AND food_id = ?";
		
		try (Connection con = ds.getConnection(); 
			PreparedStatement psSelectFood = con.prepareStatement(selectSql);
			PreparedStatement psDeleteFood = con.prepareStatement(deleteSql)) {

			ResultSet rs = psSelectFood.executeQuery();
			int foodId = 0;
			while (rs.next()){
				foodId = rs.getInt("id");
				psDeleteFood.setInt(1, userId);
				psDeleteFood.setInt(2, foodId);
				psDeleteFood.executeUpdate();
			}
			rs.close();
		}
	   catch(SQLException se) {
	      //Handle errors for JDBC
	      se.printStackTrace();
	   }
	}

	// Add back default foods that user previously removed to user's table
	// Use "IGNORE" to not insert again foods that are already in Users_Foods
	// and to avoid an error for duplicate key
	private void addUserDeletedFoods() {
		String selectSql = "SELECT id FROM Food WHERE default_food = 'yes'";
		String insertSql = "INSERT IGNORE INTO Users_Foods values (?, ?)";
		
		try (Connection con = ds.getConnection(); 
			PreparedStatement psSelect = con.prepareStatement(selectSql);
			PreparedStatement psInsertFood = con.prepareStatement(insertSql)) {

			ResultSet rs = psSelect.executeQuery();
			int foodId = 0;
			while (rs.next()){
				foodId = rs.getInt("id");
				psInsertFood.setInt(1, userId);
				psInsertFood.setInt(2, foodId);
				psInsertFood.executeUpdate();
			}
			rs.close();
		}
	   catch(SQLException se) {
	      //Handle errors for JDBC
	      se.printStackTrace();
	   }
	}	   	
}