/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.buscupcake.Controller;

import com.mycompany.buscupcake.Modeller.Cupcake;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Sean
 */
@WebServlet(name = "CartProcessServlet", urlPatterns = {"/CartProcessServlet"})
public class CartProcessServlet extends HttpServlet {
    cupcakeDAO dao;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
       
        HttpSession session = request.getSession();
        List<Cupcake> cartlist = null;
        int usid = (Integer) request.getSession().getAttribute("uid");
        try {
            dao = new cupcakeDAO();
        } catch (Exception ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        try 
        {
            cartlist = dao.getPendingCupcakes(usid);
        } catch (SQLException ex) 
        {
            Logger.getLogger(purchasehistoryservlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("cartlist", cartlist);     
        getServletContext().getRequestDispatcher("/cart.jsp").forward(request, response);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException 
    {
     response.setContentType("text/html;charset=UTF-8");
       
        HttpSession session = request.getSession();
        List<Cupcake> cartlist = null;
        int usid = (Integer) request.getSession().getAttribute("uid");
        try {
            dao = new cupcakeDAO();
        } catch (Exception ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        try 
        {
            cartlist = dao.getPendingCupcakes(usid);
        } catch (SQLException ex) 
        {
            Logger.getLogger(purchasehistoryservlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("cartlist", cartlist);     
        //response.sendRedirect("cart.jsp");
        getServletContext().getRequestDispatcher("/cart.jsp").forward(request, response);      
    }
}
