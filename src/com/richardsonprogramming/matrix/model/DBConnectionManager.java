/* This class gets a mysql database connection.
 * It is called by the appContextListener. 
 *
 * @version v.14 1-3-2017
 * @author Sarah Richardson
 */

package com.richardsonprogramming.matrix.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionManager {

	private Connection connection;
	// changed for aws so just takes in dburl and sends only that to drivermanager
	public DBConnectionManager(String dbURL) 
								throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		this.connection = DriverManager.getConnection(dbURL);
	}
	
	public Connection getConnection(){
		return this.connection;
	}
}