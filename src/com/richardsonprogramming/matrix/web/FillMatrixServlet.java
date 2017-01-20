/* This servlet is called at Login from index.html button click. It checks 
 * that user is valid and if so gets the user's food items from the database, 
 * stores these in lists, and passes the lists on to the displayMatrix.jsp which 
 * fills all the textareas with foods from these lists as well as displaying
 * forms by which users can generate random meals and edit their food choices.
 *
 * @version v.15 1-18-2017
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
		
		DataSource ds = (DataSource)getServletContext().getAttribute("DBCPool");
		String userName = request.getParameter("userName");
		NewUser newUser = new NewUser(ds, userName);

		int userId = newUser.getUserId();
		// if I get back a valid user Id, add it to a session attribute
		// and proceed to fill the user's matrix with their food items
		if (userId != -1) {
			HttpSession session = request.getSession();
			session.setAttribute("UserId", userId);
			makeFoodLists(request, ds, userId, response);
		}
		// else give error message
		else {
			RequestDispatcher rd = request.getRequestDispatcher("index.html");
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("<font color=red>No user found with given username, please register first. </font>");
			rd.include(request, response);
		}
	}

	private void makeFoodLists(HttpServletRequest request, DataSource ds, 
							int userId, HttpServletResponse response) 
							throws IOException, ServletException {

		// The FoodMatrix accesses the database to get food items
		FoodMatrix fm = new FoodMatrix(ds, userId);	
		List<List<Food>> foodLists = fm.getFoodLists();

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