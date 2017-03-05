/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.buscupcake.Controller;

import com.mycompany.buscupcake.Modeller.Cupcake;
import com.mycompany.buscupcake.Modeller.Item;
import com.mycompany.buscupcake.Modeller.Purchase;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Sean
 */
public class main {
    
    public static void main(String[] args) throws Exception 
    {
        loginDAO dao = new loginDAO();
        //dao.registerUser("sean", "sean");
        userDAO dao2 = new userDAO();
        dao2.addUserCredit(2, 50);
        int dog = dao2.getUserCredit(1);
        System.out.println("balance = "+dog);
        
        cupcakeDAO dao3 = new cupcakeDAO();
       
      
        List<Item> bottoms = new ArrayList<>();
        bottoms = dao3.getBottoms();
        for (Item d: bottoms )
        {
            System.out.println(d.toString());
        }
        
        List<Item> toppings = new ArrayList<>();
        toppings = dao3.getToppings();
        for (Item t: toppings )
        {
            System.out.println(t.toString());
        }
        dao3.createCupcakeRecord(1, 1, 3, 1);
               
          
        List<Cupcake> cupcakes = dao3.getPendingCupcakes(1);
        for (Cupcake c: cupcakes)
        {
            System.out.println("test it " + c);
        }
        
        dao3.createPurchaseCupcake(1,1);
        int cv= dao3.createPurchase(1);
        System.out.println("Purchaseid: "+ cv);
        
        int userbl = dao2.getUserCredit(1);
        System.out.println("UserBalance =" +userbl );
        System.out.println("end");
        List<Purchase> histories = new ArrayList<>();
        histories = dao3.getPurchaseHistory(1);
        for (Purchase p: histories)
        {
            System.out.println("histories:" + p);
        }
    }
}
