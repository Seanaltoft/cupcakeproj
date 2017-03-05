/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.buscupcake.Controller;

import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "OrderServlet", urlPatterns = {"/OrderServlet"})
public class OrderServlet extends HttpServlet {
    cupcakeDAO dao;

   @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        
        int sTop = Integer.parseInt(request.getParameter("sTopping"));
        int sBot = Integer.parseInt(request.getParameter("sBottom"));
        int sAmt = Integer.parseInt(request.getParameter("sAmount"));
        int usid = (Integer) request.getSession().getAttribute("uid");
        
        out.print("Muffin "+ sTop+" "+sBot+". Amount ordered:"+sAmt+"userid:"+usid);
        
        try 
        {
            dao = new cupcakeDAO();
        } catch (Exception ex) {
            Logger.getLogger(OrderServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        dao.createCupcakeRecord(sTop, sBot, sAmt, usid);
        response.sendRedirect("home.jsp");
        out.close();
    }
}
