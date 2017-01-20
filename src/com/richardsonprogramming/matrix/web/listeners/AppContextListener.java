/* This listener gets the JNDI look up for the datasource and stores
 * it in a servlet context attribute.
 *
 * @version v.15 1-18-2017
 * @author Sarah Richardson
 */
package com.richardsonprogramming.matrix.web.listeners;

import java.io.File;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


@WebListener
public class AppContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            // Obtain our environment naming context
            Context initContext = new InitialContext();
            Context webContext = (Context)initContext.lookup("java:/comp/env");
            // Look up our data source
            DataSource ds = (DataSource) webContext.lookup("jdbc/myDB");

        	ServletContext ctx = servletContextEvent.getServletContext();
        	
        	ctx.setAttribute("DBCPool", ds);
			
		} 
        catch (NamingException e) {
			e.printStackTrace();
        }
    }
    	

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    
    }
	
}