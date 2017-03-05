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
public class Purchase {
    private String purchasedate;
    private String toppingname;
    private String bottomname;
    private int totalcost;
    
    public Purchase(String pdate, String tname, String bname, int tcost)
    {
        this.purchasedate = pdate;
        this.toppingname = tname;
        this.bottomname = bname;
        this.totalcost = tcost;
    }

    /**
     * @return the purchasedate
     */
    public String getPurchasedate() 
    {
        return purchasedate;
    }

    /**
     * @return the toppingname
     */
    public String getToppingname() 
    {
        return toppingname;
    }

    /**
     * @return the bottomname
     */
    public String getBottomname() 
    {
        return bottomname;
    }

    /**
     * @return the totalcost
     */
    public int getTotalcost() 
    {
        return totalcost;
    }
    
    @Override
    public String toString() 
    {
        return "Purchase{" + "date=" + purchasedate + ", toppingname=" + toppingname + ", bottomname= " + bottomname + ", price= " + totalcost + '}';
    }
}
