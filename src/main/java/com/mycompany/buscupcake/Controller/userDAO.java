/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.buscupcake.Controller;

import com.mycompany.buscupcake.Modeller.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sean
 */
public class userDAO {
    private final DBConnector conn;
    
    public userDAO () throws Exception
    {
        this.conn = new DBConnector();
    }
    
//    add credit to user table.user
    public void addUserCredit(int userid, int amt)
    {
        Statement statement = null;
        String insertTableSQL = "UPDATE user "
                        + "SET balance = balance + "+amt+" "
                        + "WHERE userid = "+userid;
        try {
		statement = conn.getConnection().createStatement();
		System.out.println(insertTableSQL);
		// execute insert SQL stetement
		statement.executeUpdate(insertTableSQL);
		System.out.println("Added "+amt+" to users balance");
	} catch (SQLException e) 
        {
            System.out.println(e.getMessage());
	} 
    }
    
//    gets current credit amount in user account
    public int getUserCredit(int userid) throws SQLException
    {
        Statement stmt = conn.getConnection().createStatement();
        String sql = "select * from user where userid = "+userid;
        User user = null;
        int userbalance = 0;
        try 
        {
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next())
            {
                int balc = rs.getInt("balance");
                userbalance = balc;
            }
            } catch (SQLException ex) 
            {
                Logger.getLogger(loginDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        return userbalance;
    }
}
