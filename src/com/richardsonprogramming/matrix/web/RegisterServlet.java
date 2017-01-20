/* This servlet handles the registration of new users, adding them to 
 * User table and creating a default food matrix for them in Users_Foods
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
import javax.sql.DataSource;

public class RegisterServlet extends HttpServlet {

	private static final long SERIAL_VERSION_UID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) 
						throws IOException, ServletException {

		DataSource ds = (DataSource)getServletContext().getAttribute("DBCPool");
		String userName = request.getParameter("userName");
		NewUser newUser = new NewUser(ds, userName);

		// check if username exists
		Boolean userExists = newUser.checkUser();
		// if username already exists, give error message
		if(userExists) {
			RequestDispatcher rd = request.getRequestDispatcher("register.html");
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("<font color=red>Username not available, please choose a different username. </font>");
			rd.include(request, response);
		}	
		// else create new user with default food matrix and redirect to login 
		else {
			newUser.createUser();
			RequestDispatcher view = request.getRequestDispatcher("index.html");
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("<font color=green>Registration successful, please login below.</font>");
			view.include(request, response);
		}
	}	
}