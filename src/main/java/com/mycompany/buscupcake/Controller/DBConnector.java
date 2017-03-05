/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.buscupcake.Controller;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Sean
 */
public class DBConnector {
    private Connection connection = null;
	
	//Constants
	private static final String IP	     = "localhost";
	private static final int    PORT     = 3306;
	public static final String DATABASE  = "cupcake";
	private static final String USERNAME = "root"; 
	private static final String PASSWORD = "sean666";	     	
	
	public DBConnector() throws Exception {
   		Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
   		String url = "jdbc:mysql://" + IP + ":" + PORT + "/" + DATABASE;
   		this.connection = (Connection) DriverManager.getConnection(url, USERNAME, PASSWORD);
	}
	
	public Connection getConnection() {
   		return this.connection;
	}
}
