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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Sean
 */
@WebServlet(name = "RegisterServlet", urlPatterns = {"/RegisterServlet"})
public class RegisterServlet extends HttpServlet {
loginDAO dao;
    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        
        PrintWriter out = response.getWriter();
        response.setContentType("text/html;charset=UTF-8");
        String usern = request.getParameter("name");
        String passw = request.getParameter("pass");
        String passw2 = request.getParameter("pass2");
        
        try {
            dao = new loginDAO();
        } catch (Exception ex) {
            Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
        }     
        if (passw == null ? passw2 == null : passw.equals(passw2))
        {
            try {
                if (!dao.userExists(usern))
                {
                    try {
                        dao.registerUser(usern, passw);
                        out.print("Account Registration Success!");
                        RequestDispatcher rd=request.getRequestDispatcher("login.html");
                        rd.include(request,response);

                    } catch (SQLException ex) {
                    Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
                    out.print("Sorry username or password error");
                       RequestDispatcher rd=request.getRequestDispatcher("register.html");
                        rd.include(request,response); 
                    } 
                }
                else 
                {
                    out.print("User Already Exists");
                       RequestDispatcher rd=request.getRequestDispatcher("register.html");
                        rd.include(request,response); 
                }
            } catch (SQLException ex) 
            {
                Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            out.print("Password Mismatch");
            RequestDispatcher rd=request.getRequestDispatcher("register.html");
            rd.include(request,response); 
        }
        out.close();
    }
}   
    
    
