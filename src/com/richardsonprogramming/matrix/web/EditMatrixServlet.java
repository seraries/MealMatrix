/* This servlet takes user-entered foods and either adds them to or deletes 
 * them from the database via the EditFood model class. It then creates a new 
 * FoodMatrix and gets a new list of foods for that user from the DB and sets
 * a request attribute with those food lists so the jsp can update the textareas
 * with the new, modified food lists.
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

public class EditMatrixServlet extends HttpServlet {

	private static final long SERIAL_VERSION_UID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) 
						throws IOException, ServletException {
		// get connection pool to pass in to model layer so it can get connection
		DataSource ds = (DataSource)getServletContext().getAttribute("DBCPool");

		// get userId
		HttpSession session = request.getSession();
		int userId = (Integer) session.getAttribute("UserId");
		EditFood ef = new EditFood(ds, userId);

		// pass in user input string in lower case to add or delete method
		// since all food items in the database are lower case 
		if (request.getParameter("typeOfEdit").equals("delete")) {
			ef.deleteFood(request.getParameter("foodEdit").toLowerCase());
		}

		else if (request.getParameter("typeOfEdit").equals("add")) {
			ef.addFood(request.getParameter("typeOfFood"), 
						request.getParameter("foodEdit").toLowerCase());
		}
		// TO DO: else send error. need radio button selected (browser html
		// "required" attribute is handling this for now.)

		// now that food DB has been updated, get an updated list of foods 
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

		RequestDispatcher view = request.getRequestDispatcher("updateMatrix.jsp");
		view.forward(request, response);	
	}
}