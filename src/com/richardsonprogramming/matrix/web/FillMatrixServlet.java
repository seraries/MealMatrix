/* This servlet is called at the start of a session, initiates a DB connection in
 * session scope (since context is never destroyed on AWS), gets the user's food
 * items from the database, stores these in lists, and passes the lists on to 
 * the jsp which fills all the textareas with foods from these lists as well as 
 * displaying forms by which users can generate random meals and can edit their 
 * food choices.
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

public class FillMatrixServlet extends HttpServlet {

	private static final long SERIAL_VERSION_UID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) 
						throws IOException, ServletException {
		// get connection pool to pass in to model layer so it can get connection
		DataSource ds = (DataSource)getServletContext().getAttribute("DBCPool");

		// The FoodMatrix accesses the database to get food items
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

		RequestDispatcher view = request.getRequestDispatcher("displayMatrix.jsp");
		view.forward(request, response);
		
	}

}