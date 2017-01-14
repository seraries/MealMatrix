/* This servlet resets the database to default food items and then gets new
 * food lists from the updated DB. It then passes on the reset food lists to 
 * the jsp which will fill the textareas that display the user's food choices so
 * that they only show the default items, thereby discarding any edits--additions 
 * or deletions--the user has made.
 *
 * @version v.14 1-3-2017
 * @author Sarah Richardson
 */

package com.richardsonprogramming.matrix.web;

import com.richardsonprogramming.matrix.model.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import javax.sql.DataSource;

public class ResetMatrixServlet extends HttpServlet {

	private static final long SERIAL_VERSION_UID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) 
						throws IOException, ServletException {

		// get connection pool to pass in to model layer so it can get connection
		DataSource ds = (DataSource)getServletContext().getAttribute("DBCPool");
		// fill the database with foods listed in the FillFoodDB class; 
		FillFoodDB ffdb = new FillFoodDB();
		ffdb.resetDB(ds);

		// now that the Food DB has been reset with default foods, get an updated 
		// list of the foods that are in the DB via FoodMatrix and set this as a
		// request attribute that the jsp response can use to fill the textareas
		FoodMatrix fm = new FoodMatrix();	
		List<List<Food>> foodLists = fm.getFoodLists(ds);

		// use this list to add headers to textareas that state the food types
		List<String> foodTypes = new ArrayList<String>(); 
		foodTypes.add("Veggies");
		foodTypes.add("Proteins");
		foodTypes.add("Fruits");
		foodTypes.add("Fats");
		foodTypes.add("Seasonings");
		
		request.setAttribute("foodLists", foodLists);
		request.setAttribute("foodTypes", foodTypes);

		RequestDispatcher view = request.getRequestDispatcher("updateMatrix.jsp");
		view.forward(request, response);	
	}
}