/* This class checks if a new user has entered a valid (non-duplicate)
 * username and also creates new users as well as filling their initial
 * food matrix with the foods marked as default in the Food table.
 * It is controlled by RegisterServlet, in response to "Sign Up" button click
 * of index.html (AWS home page).
 *
 * @version v.15 1-18-2017
 * @author Sarah Richardson
 */

package com.richardsonprogramming.matrix.model;

import java.sql.*;
import java.util.*;
import javax.sql.DataSource;

public class NewUser {
	private String userName;
	private DataSource ds;

	public NewUser(DataSource ds, String userName) {
		this.ds = ds;
		this.userName = userName;
	}
		
	// check if username already exists in User table
	public boolean checkUser() {
		String getIdSql = "SELECT user_id FROM User WHERE username = ?";
		boolean userExists = true;

		try(Connection con = ds.getConnection(); 
				PreparedStatement checkUser = con.prepareStatement(getIdSql)) {
		
			checkUser.setString(1, userName);
			ResultSet rs = checkUser.executeQuery();
			// if there's a result set, username exists
			if(rs.next()) {
				userExists = true;
			}
			else {
				userExists = false;
			}
			rs.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return userExists;
	}	

	/*
	create new user, then get result set of all default foods, 
	then fill up the users_foods table for this new user 
	(after getting her new user id) with the default foods
	*/
	public void createUser() {
		String newUserSql = "INSERT INTO User(user_id, username) values (NULL, ?)";
		try (Connection con = ds.getConnection(); 
			PreparedStatement psNewUser = con.prepareStatement(newUserSql)) {

			psNewUser.setString(1, userName);
			psNewUser.execute();	
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		setDefaultFoodMatrix();
	}

	public void setDefaultFoodMatrix() {
		int userId = getUserId();
		String setFoodsSql = "INSERT INTO Users_Foods (user_id, food_id) values (?, ?)";
		String getFoodsSql = "SELECT id FROM Food WHERE default_food = 'yes'";
		try (Connection con = ds.getConnection(); 
			PreparedStatement psGetFoods = con.prepareStatement(getFoodsSql);
			PreparedStatement psSetFoods = con.prepareStatement(setFoodsSql)) {

			ResultSet rs = psGetFoods.executeQuery();
			while (rs.next()) {
				int foodId = rs.getInt("id");
				psSetFoods.setInt(1, userId);
				psSetFoods.setInt(2, foodId);
				psSetFoods.executeUpdate();
			}
			rs.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int getUserId() {
		int userId = -1; // caller checks for this value to see if rs was empty
		String getIdSql = "SELECT user_id FROM User WHERE username = ?";
		try (Connection con = ds.getConnection(); 
					PreparedStatement psGetId = con.prepareStatement(getIdSql)) {
			psGetId.setString(1, userName);
			ResultSet rs = psGetId.executeQuery();
			while (rs.next()) {
				userId = rs.getInt("user_id");
			}
			rs.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return userId;
	}
}