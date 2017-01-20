/* This servlet gets the number of meals (and number of items per food type in 
 * each meal) to create from the user and generates (via the MealMaker model 
 * class) that number of random-ingredient meals. It puts the list of meals 
 * in a request attribute and forwards it to a jsp that displays these meals in 
 * a textarea.
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

public class MakeRandomMealsServlet extends HttpServlet {

	private static final long SERIAL_VERSION_UID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) 
						throws IOException, ServletException {

		// get connection pool to pass in to model layer so it can get connection
		DataSource ds = (DataSource)getServletContext().getAttribute("DBCPool");
		HttpSession session = request.getSession();
		int userId = (Integer) session.getAttribute("UserId");

		FoodMatrix fm = new FoodMatrix(ds, userId);	
		List<List<Food>> foodLists = fm.getFoodLists();
		
		int numberOfMeals = 0; // default value in case form input is empty
		String [] foodTypes = {"Veggies", "Proteins", "Fruits", "Fats", 
								"Seasonings"};
		List<Integer> numberOfItemsByFoodTypeList = new ArrayList<Integer>();
		List<String> allMeals = new ArrayList<String>();

		// get number of meals to generate and check if form input is empty
		if(request.getParameter("numMeals").isEmpty() == false) {
			numberOfMeals = Integer.parseInt(request.getParameter("numMeals"));
		}

		// get number of items by food type to include per meal 
		for (String type : foodTypes) {
			int numberOfItems = 0;
			if(request.getParameter(type).isEmpty() == false) {
				numberOfItems = Integer.parseInt(request.getParameter(type));
			}
			numberOfItemsByFoodTypeList.add(numberOfItems);
		}

		// get list of randomly generated meals
		allMeals = MealMaker.getMealsList(foodLists, numberOfItemsByFoodTypeList, 
											numberOfMeals);
		request.setAttribute("randomMeals", allMeals);

		RequestDispatcher view = request.getRequestDispatcher("displayMeals.jsp");
		view.forward(request, response);
	}
}	
 