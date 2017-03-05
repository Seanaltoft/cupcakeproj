/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.buscupcake.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mycompany.buscupcake.Modeller.Cupcake;
import java.sql.SQLException;

/**
 *
 * @author Sean
 */
@WebServlet(name = "CartServlet", urlPatterns = {"/CartServlet"})
public class CartServlet extends HttpServlet {
    cupcakeDAO dao;
    userDAO udao;

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        int usid = (Integer) request.getSession().getAttribute("uid");
        try 
        {
            dao = new cupcakeDAO();
            udao = new userDAO();
        } catch (Exception ex) 
        {
            Logger.getLogger(CartServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (request.getParameter("Remove") != null)
        {
            // Invoke Remove. Delete Selected Cart Item
           int cartid = Integer.parseInt(request.getParameter("Remove"));
           dao.setCartStatusAborted(cartid);
           out.print("Cupcake, topping: bottom: . Removed");
           RequestDispatcher rd=request.getRequestDispatcher("cart.jsp");
           rd.include(request,response);
        }
        else if (request.getParameter("RemoveAll") != null)
        {
            // Invoke RemoveAll. Abort all objects in user cart.
            dao.setAllCartStatusAborted(usid);
            out.print("Emptied your cart!");
            RequestDispatcher rd=request.getRequestDispatcher("cart.jsp");
            rd.include(request,response);
        }
        else if (request.getParameter("Checkout") != null) 
        {
            List<Cupcake> purchaseditems = null;
            int receipttotal = 0;
            int userbalance = 0;
                try {
                    // Get total price for receipt
                     receipttotal = dao.receiptCost(usid);
                     userbalance = udao.getUserCredit(usid);
                     request.setAttribute("receipttotal", receipttotal);
                    // Invoke action purchase.
                    purchaseditems = dao.purchaseCart(usid);
                } catch (SQLException ex) {
                    Logger.getLogger(CartServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (receipttotal>userbalance)
                {
                    out.print("Not enough balance to purchase items!");
                    RequestDispatcher rd=request.getRequestDispatcher("cart.jsp");
                    rd.include(request,response);
                }
                else if (purchaseditems.size() > 0)
                {
                    request.setAttribute("purchasedlist", purchaseditems);
                    out.print("Purchased Items in Cart!");
                    RequestDispatcher rd=request.getRequestDispatcher("receipt.jsp");
                    rd.include(request,response);
                }
                else{
                    out.print("Cart is empty!");
                    RequestDispatcher rd=request.getRequestDispatcher("cart.jsp");
                    rd.include(request,response);
                }
        }
                        out.close();
    }
}
