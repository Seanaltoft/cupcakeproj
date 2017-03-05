/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.buscupcake.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Sean
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {
    loginDAO dao;
   

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)  
        throws ServletException, IOException {  
    
        try {
            dao = new loginDAO();
        } catch (Exception ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    response.setContentType("text/html");  
    PrintWriter out = response.getWriter();  
       
    String n=request.getParameter("username");  
    String p=request.getParameter("userpass");  
    
    int testuserid = 0;
        try {
            testuserid = dao.getUser(n);
            
        } catch (Exception ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
          
        
       request.setAttribute("userid", testuserid);
        try {
            if(dao.validate(n, p)){
                HttpSession session = request.getSession();
                session.setAttribute("user", p);
                session.setAttribute("uid", testuserid);
                Cookie userName = new Cookie("user", p);
                //setting session to expiry in 30 mins
		session.setMaxInactiveInterval(30*60);
		response.addCookie(userName);
                //Get the encoded URL string
                String encodedURL = response.encodeRedirectURL("home.jsp");
                response.sendRedirect(encodedURL);
            }
            else{
                out.print("<font color=red>Username or Password Error</font>");
                RequestDispatcher rd=request.getRequestDispatcher("login.html");
                rd.include(request,response);  
            }   } catch (SQLException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
          
    out.close();  
    }  
   
}
