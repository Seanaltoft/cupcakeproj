/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.buscupcake.Modeller;

/**
 *
 * @author Sean
 */
public class User {
    private int userid;
    private String username;
    private String password;
    private int balance;
    
    public User (int id, String usern, String passw, int balance)
    {
        this.userid = id;
        this.username = usern;
        this.password = passw;
        this.balance = balance;
    }
    
    public int getuserid()
    {
        return userid;
    }
    
    public String getusername()
    {
        return username;
    }
    
    public String getpassword()
    {
        return password;
    }
    
    public int getbalance()
    {
        return balance;
    }
    
    @Override
    public String toString() 
    {
        return "User{" + "ID=" + userid + ", username=" + username + ", password= " + password + '}';
    }
}

