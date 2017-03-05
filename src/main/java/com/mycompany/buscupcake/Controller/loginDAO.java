/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.buscupcake.Controller;
import java.sql.*; 
import java.util.logging.Level;
import java.util.logging.Logger;
import com.mycompany.buscupcake.Modeller.User;
/**
 *
 * @author Sean
 */
public class loginDAO {
    private final DBConnector conn;
    
    public loginDAO () throws Exception
    {
        this.conn = new DBConnector();
    }
    
//    Checks user and password input is correct for login
    public boolean validate(String name,String pass) throws SQLException
    {  
        boolean status=false;  
        Connection stmt = conn.getConnection();
        try
        {    
            PreparedStatement ps=stmt.prepareStatement(  
                "select * from user where username=? and password=?");  
            ps.setString(1,name);  
            ps.setString(2,pass);  
            ResultSet rs=ps.executeQuery();  
            status=rs.next();  
        }catch(Exception e){System.out.println(e);}  
            return status;  
    } 
    
    //check if user exists
    public boolean userExists(String name) throws SQLException
    {
        boolean status=false;  
        Connection stmt = conn.getConnection();
        try
        {    
            PreparedStatement ps=stmt.prepareStatement(  
                "select * from user where username=?");  
            ps.setString(1,name);   
            ResultSet rs=ps.executeQuery();  
            status=rs.next();  
        }catch(Exception e){System.out.println(e);}  
            return status;  
    } 
 
//    Registers a user
    public void registerUser(String usern, String passw) throws SQLException 
    {
        Statement statement = null;
        String insertTableSQL = "INSERT INTO user "
                        + "VALUES"
                        + "(NULL,'"+usern+"','"+passw+"', 0)";
        try {
                statement = conn.getConnection().createStatement();
                System.out.println(insertTableSQL);
                // execute insert SQL stetement
                statement.executeUpdate(insertTableSQL);
                System.out.println("Record is inserted into User table!");
        } catch (SQLException e) {
                System.out.println(e.getMessage());
        } 
    }
        
//    Returns the userid for a user using 'username'
    public int getUser(String username) throws Exception
    {
        Statement stmt = conn.getConnection().createStatement();
        String sql = "select * from user where username = " + "'"+username+"'";
        User user = null;
        int userid = 0;
        try 
        {
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next())
            {
                int user_id = rs.getInt("userid");
                String user_name = rs.getString("username");
                String password = rs.getString("password");
                int balc = rs.getInt("balance");
                user = new User(user_id, user_name, password, balc);
                userid = user_id;
            }
            } catch (SQLException ex) 
            {
                Logger.getLogger(loginDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        return userid;
    }
    
//    Returns users balance
    public int getUserBalance(int userid) throws SQLException
    {
        Statement stmt = conn.getConnection().createStatement();
        String sql = "select * from user where userid = "+userid;
        User user = null;
        int userbalc = 0;
        try 
        {
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next())
            {
                int user_id = rs.getInt("userid");
                String user_name = rs.getString("username");
                String password = rs.getString("password");
                int balc = rs.getInt("balance");
                user = new User(user_id, user_name, password, balc);
                userbalc = user_id;
            }
            } catch (SQLException ex) 
            {
                Logger.getLogger(loginDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        return userbalc;
    }
    
}


        