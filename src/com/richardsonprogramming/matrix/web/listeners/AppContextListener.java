/* This listener starts a database connection when the context is initialized
 * and closes it when the context is destroyed.
 *
 * @version v.14 1-3-2017
 * @author Sarah Richardson
 */

package com.richardsonprogramming.matrix.web.listeners;

import com.richardsonprogramming.matrix.model.DBConnectionManager;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent servletContextEvent) {
    	ServletContext context = servletContextEvent.getServletContext();
    	
    	// get info from web.xml (For AWS, just get from environ the connection string)
    	String dbURL = System.getProperty("JDBC_CONNECTION_STRING");
    	// String user = context.getInitParameter("dbUser");
    	// String pwd = context.getInitParameter("dbPassword");
    	
        // initialize database Connection via connectionManager model class
    	try {
			DBConnectionManager conManager = new DBConnectionManager(dbURL); // took out user and pwd arguments
			context.setAttribute("DBConnection", conManager.getConnection());
		} 
        catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
        catch (SQLException e) {
			e.printStackTrace();
		}
    }
    	
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    	Connection con = (Connection) servletContextEvent.getServletContext().getAttribute("DBConnection");

    	try {
			con.close();
		} 
        catch (SQLException e) {
			e.printStackTrace();
		}
    }
	
}